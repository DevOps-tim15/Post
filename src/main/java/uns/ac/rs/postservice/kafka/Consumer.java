package uns.ac.rs.postservice.kafka;

import java.io.UnsupportedEncodingException;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.mail.MailException;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import uns.ac.rs.postservice.kafka.domain.UserMessage;
import uns.ac.rs.postservice.service.UserService;
import uns.ac.rs.postservice.util.InvalidDataException;


@Service
public class Consumer {
	
	@Autowired
	ObjectMapper objectMapper;
	
	@Autowired
	UserService userService;
	
	@Autowired
	Producer producer;
	
	@Autowired 
	private KafkaTemplate<String, String> kafkaTemp;
	
	@KafkaListener(topics="auth-topic", groupId="mygroup-post")
	public void consumeFromTopic(ConsumerRecord<String, String> consumerRecord) throws JsonProcessingException, InvalidDataException {
		String value = consumerRecord.value();
		System.out.println("Consummed message " + value);
		
		UserMessage message = null;
		try {
			message = objectMapper.readValue(value, UserMessage.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("Consumer has read message!");
		if(message.getType().equals("registration")) {
			try {
				userService.saveRegisteredUser(message.getUser());
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
		}else if(message.getType().equals("update")) {
			userService.updateUser(message.getUser(), message.getOldUsername(), message.getRole());
		}

	}
	
	@KafkaListener(topics="user-topic", groupId="mygroup-post")
	public void consumeFromTopicUser(ConsumerRecord<String, String> consumerRecord) throws JsonProcessingException, MailException, UnsupportedEncodingException, InterruptedException {
		String value = consumerRecord.value();
		System.out.println("Consummed message " + value);
		
		UserMessage message = null;
		try {
			message = objectMapper.readValue(value, UserMessage.class);
		} catch (Exception e) {
		}
		if(message.getType().equals("remove")) {
			userService.deleteUser(message.getUser());
		} 
	}
}
