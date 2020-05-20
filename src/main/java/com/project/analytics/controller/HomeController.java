package com.project.analytics.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.analytics.model.ClickAnalytics;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Properties;

@Controller
public class HomeController {

	private final static String TOPIC = "click_events";
	private final static String BOOTSTRAP_SERVERS = "localhost:9092";

	@RequestMapping(value = "/", method = RequestMethod.GET )

	public String hello(){

		System.out.println("Hello");
		return "index";
	}

	@PostMapping(value = {"/clickAnalytics"}, consumes = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
	public ResponseEntity storeClickEventJSON(@RequestBody ClickAnalytics clickAnalytics){

		System.out.println("Data logged! " + clickAnalytics.getX() + "  " + clickAnalytics.getY());

		KafkaProducer<String, String> kafkaProducer = produce();

		try {
			ObjectMapper mapper = new ObjectMapper();

			kafkaProducer.send(new ProducerRecord(TOPIC, "Key", mapper.writeValueAsString(clickAnalytics)));
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			kafkaProducer.close();
		}
		return ResponseEntity.ok().build();
	}

	@PostMapping(value = {"/clickAnalyticsxml"}, consumes = { MediaType.APPLICATION_XML_VALUE })
	public ResponseEntity storeDoubleClickEventXML(@RequestBody ClickAnalytics clickAnalytics){

		System.out.println("Data logged! " + clickAnalytics.getX() + "  " + clickAnalytics.getY());

		KafkaProducer<String, String> kafkaProducer = produce();

		try {
			ObjectMapper mapper = new ObjectMapper();

			kafkaProducer.send(new ProducerRecord(TOPIC, "Key", mapper.writeValueAsString(clickAnalytics)));
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			kafkaProducer.close();
		}
		return ResponseEntity.ok().build();
	}


	private static KafkaProducer<String, String> produce() {
		// Set properties used to configure the producer
		Properties properties = new Properties();
		// Set the brokers (bootstrap servers)
		properties.setProperty("bootstrap.servers", BOOTSTRAP_SERVERS);
		// Set how to serialize key/value pairs
		properties.setProperty("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
		properties.setProperty("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
		// specify the protocol for Domain Joined clusters
		//properties.setProperty(CommonClientConfigs.SECURITY_PROTOCOL_CONFIG, "SASL_PLAINTEXT");

		return new KafkaProducer<>(properties);

	}
}