package com.all4canid.link;

import java.util.Collections;
import java.util.List;
import java.util.Properties;

import org.apache.commons.lang3.StringUtils;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.all4canid.link.domain.contract.SubContracts;
import com.all4canid.link.domain.job.LinkJob;
import com.all4canid.link.rest.ImportNewContractClientAdapter;
import com.all4canid.link.rest.LinkClient;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class LinkConsumer implements Runnable {

    private static final Logger log = LoggerFactory.getLogger(LinkConsumer.class);

    private final KafkaConsumer<String, String> consumer;
    private final List<String> topics;

    public LinkConsumer(String bootStrapServer, String groupId, List<String> topics) {
        this.topics = Collections.unmodifiableList(topics);

        Properties props = new Properties();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootStrapServer);
        props.put(ConsumerConfig.GROUP_ID_CONFIG, groupId);
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        this.consumer = new KafkaConsumer<>(props);
    }


    @SuppressWarnings("unchecked")
    @Override
    public void run() {
        try {
            consumer.subscribe(topics);

            while (!Thread.currentThread().isInterrupted()) {
                final ConsumerRecords<String, String> records = consumer.poll(1000);
                if(records.isEmpty()) {
                   continue; 
                }
                records.forEach(record -> {
                    log.info("Consumer Record:({}, {}, {}, {})", record.key(), record.value(), record.partition(), record.offset());
                    if(StringUtils.isNotBlank(record.value())) {
                        try {
                            log.info(record.value());
                            Gson gson = new GsonBuilder().serializeNulls().create();
                            SubContracts subContracts = gson.fromJson(record.value(), SubContracts.class);

                            // import filtering
                            LinkClient client = new ImportNewContractClientAdapter("proxyHost", "proxyPort");
                            client.setAuth("username", "password");
                            client.setBaseUrl("targetHostUrl");
                            client.setRequestContextPath("/endpoint/context");
                            client.setPayloadObject(subContracts);
                            LinkJob texturaJob = client.sendRequest();
                            log.info(texturaJob.getUri());
                            
                            ResponseEntity<?> response = null;
                            String responseBody = null;
                            int retry = 0;
                            do {
                                retry++;
                                Thread.sleep(1000L * 60);
                                response = client.getResults(texturaJob.getUri());
                            } while ( (null != response)
                                    && (response.getStatusCode().equals(HttpStatus.ACCEPTED))
                                    && (retry <= 10) );

                            if(null != response && response.getStatusCode().equals(HttpStatus.OK) && null != response.getBody()) {
                                responseBody = ((ResponseEntity<String>)response).getBody();
                                log.info(responseBody);
//                                TexturaAuditLog auditLog = gson.fromJson(responseBody, TexturaAuditLog.class);
                                // TODO process audit to database
                            }
                        } catch (Exception e) {
                            log.error(e.getMessage());
                        }
                    }
                });
                consumer.commitAsync();
            }
        } finally {
            consumer.close();
        }
    }

    public void shutdown() {
        consumer.wakeup();
    }

}
