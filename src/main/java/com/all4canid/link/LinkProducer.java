package com.all4canid.link;

import java.text.ParseException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutionException;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.MutablePair;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.kafka.common.serialization.StringSerializer;
import org.quartz.CronExpression;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.all4canid.link.consts.ActionType;

public class LinkProducer implements Runnable {

    private static final Logger log = LoggerFactory.getLogger(LinkProducer.class);

    private KafkaProducer<String, String> producer;
    private String topic;
    /**
     * api as key for map
     * cron expression and next run time Date pair as value for map
     */
    private volatile Map<String, MutablePair<CronExpression, Date>> map;

    /**
     * TPM Producer constructor for one topic
     * @param bootStrapServer boot strap (broker) server
     * @param topic topic stream
     */
    public LinkProducer(String bootStrapServer, String topic) {
        this.topic = topic;

        Properties props = new Properties();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootStrapServer);
        props.put(ProducerConfig.CLIENT_ID_CONFIG, topic + "-client");
        props.put(ProducerConfig.ACKS_CONFIG, "0");
        props.put(ProducerConfig.RETRIES_CONFIG, 5);
        props.put(ProducerConfig.BUFFER_MEMORY_CONFIG, 33554432);
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        producer = new KafkaProducer<>(props);

        map = new ConcurrentHashMap<>();
    }

    @Override
    public void run() {
        try {
            while (!Thread.currentThread().isInterrupted()) {
                // Build api to cron expression and next valid time pair map
                updateApiTimeMap();

                // get api set
                map.keySet()
                .forEach(api -> {
                    try {
                        if(isTimeForRun(api)) {
                            // send import message to adapter to start import job
                            String data = "{\"api\": \"import_contract\"}";
                            ProducerRecord<String, String> record = new ProducerRecord<>(topic, data);

                            RecordMetadata metadata = producer.send(record).get();
                            //TODO process metadata
                        }
                    } catch (InterruptedException | ExecutionException e) {
                        log.error(e.getMessage());
                    }
                });
                try {
                    Thread.sleep(60000);
                } catch (Exception e) {
                    log.warn(e.getMessage());
                }
            }
        } finally {
            producer.close();
        }
    }

    private void updateApiTimeMap() {
        //TODO read all api to cron expression configuration from database
        String cronExpression = "* 0/5 * ? * MON-SAT";
        MutablePair<CronExpression, Date> pair = map.getOrDefault(ActionType.IMPORT_NEW_CONTRACTS.toString(), new MutablePair<CronExpression, Date>(null, null));

        // disable status from database for each api
        boolean isDisabled = false;

        pair.setLeft(null);
        if(!isDisabled 
           && StringUtils.isNotBlank(cronExpression)) {
            try {
                CronExpression cronExp = new CronExpression(cronExpression);
                pair.setLeft(cronExp);
                if(null == pair.getValue()) {
                    // it wasn't run after restart, so set next valid time by current cron expression
                    pair.setValue(Date.from( LocalDateTime.now().plusMinutes(1).atZone(ZoneId.systemDefault()).toInstant()));  
                }
            } catch (ParseException e) {
                log.error(new StringBuilder("parse error: ")
                        .append(cronExpression)
                        .append(", cause: ")
                        .append(e.getMessage())
                        .toString());
            }
        }
        map.put(ActionType.IMPORT_NEW_CONTRACTS.toString(), pair);
    }

    private Date getNextValidTime(String api) {
        MutablePair<CronExpression, Date> pair = map.get(api);
        if(null != pair) {
            return pair.getValue();
        }
        return null;
    }

    private void setNextValidTime(String api, Date date) {
        MutablePair<CronExpression, Date> pair = map.get(api);
        if(null != pair) {
            pair.setValue(date);
        }
    }

    private CronExpression getCronExpression(String api) {
        MutablePair<CronExpression, Date> pair = map.get(api);
        if(null != pair) {
            return pair.getKey();
        }
        return null;
    }

    private boolean isTimeForRun(String api) {
        CronExpression cronExp = getCronExpression(api);
        if(null == cronExp) {
            return false;
        }

        LocalDateTime now = LocalDateTime.now();
        Date checkNow = Date.from( now.atZone(ZoneId.systemDefault()).toInstant().truncatedTo(ChronoUnit.MINUTES) );    // adjusted time with removing second
        Date minuteAhead = Date.from( now.plusMinutes(1).atZone(ZoneId.systemDefault()).toInstant() );                  // adjust time with adding a minute

        Date nextValidTime = getNextValidTime(api);

        // cron expression is satisfied by current time or current time is passed next valid time set from previous run
        boolean isTimeForRun = cronExp.isSatisfiedBy(checkNow) || ( nextValidTime != null && nextValidTime.before(checkNow));
        if(isTimeForRun) {
            setNextValidTime(api, cronExp.getNextValidTimeAfter(minuteAhead) );  // update next valid time
        }
        return isTimeForRun;
    }

}
