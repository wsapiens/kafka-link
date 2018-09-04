package com.all4canid.link;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Application {

	private static final Logger log = LoggerFactory.getLogger(Application.class);

	public static void main(String[] args) throws Exception {
		log.info("Start TexturaLink2 Application");

		String groupId = "textura-link2-consumer";
		String bootStrapServer = "localhost:9092";
		List<String> inputTopics = Arrays.asList("tlink-streams-import");
		List<String> outputTopics = Arrays.asList("tlink-streams-export");
		int numberOfConsumers = 3;
		int numberOfProducers = 1;
		ExecutorService consumerExecutor = Executors.newFixedThreadPool(numberOfConsumers);
		ExecutorService producerExecutor = Executors.newFixedThreadPool(numberOfProducers);

		final List<LinkConsumer> consumers = new ArrayList<>();
		for(int i=0; i < numberOfConsumers; i++) {
		    LinkConsumer consumer = new LinkConsumer(bootStrapServer, groupId, inputTopics);
		    consumers.add(consumer);
		    consumerExecutor.submit(consumer);
		}
		final List<LinkProducer> producers = new ArrayList<>();
		outputTopics.forEach(topic -> {
		    LinkProducer producer = new LinkProducer(bootStrapServer, topic);
            producers.add(producer);
            producerExecutor.submit(producer);
		});

		// attach shutdown handler to catch control-c
		Runtime.getRuntime().addShutdownHook(new Thread("textura-link2-shutdown-hook") {
			@Override
			public void run() {
			    consumers.forEach(LinkConsumer::shutdown);
			    consumerExecutor.shutdown();
			    producerExecutor.shutdown();
			    try {
			        consumerExecutor.awaitTermination(5000, TimeUnit.MILLISECONDS);
			        producerExecutor.awaitTermination(5000, TimeUnit.MILLISECONDS);
			    } catch (InterruptedException e) {
			        log.warn(e.getMessage());
			        Thread.currentThread().interrupt();
			    }
			}
		});
	}
}
