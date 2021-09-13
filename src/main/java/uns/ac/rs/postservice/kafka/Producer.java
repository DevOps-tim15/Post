package uns.ac.rs.postservice.kafka;

import java.util.List;
import java.util.concurrent.ExecutionException;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.requestreply.ReplyingKafkaTemplate;
import org.springframework.kafka.requestreply.RequestReplyFuture;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import uns.ac.rs.postservice.domain.User;
import uns.ac.rs.postservice.kafka.domain.UsersFollowBlockMute;
import uns.ac.rs.postservice.kafka.domain.UsersMessage;

@Service
public class Producer {

	  @Autowired
	  private ReplyingKafkaTemplate<String, String, String> replyingKafkaTemplate;
	  
	  @Autowired
	  private ObjectMapper objectMapper;
	  
	  public String checkUser(String authToken) throws JsonMappingException, JsonProcessingException, InterruptedException, ExecutionException{
	     	System.out.println("HI!");   
	     	ProducerRecord<String, String> producerRecord = new ProducerRecord<>("logged-in", authToken);
	        RequestReplyFuture<String, String, String> future = replyingKafkaTemplate.sendAndReceive(producerRecord);
	        ConsumerRecord<String, String> consumerRecord = future.get();
	        String username = consumerRecord.value();
	        System.out.println(username);
	        return username;
	   }
	  
	  public List<User> getFollowing(String username) throws JsonMappingException, JsonProcessingException, InterruptedException, ExecutionException{
	     	System.out.println("HI!");   
	     	ProducerRecord<String, String> producerRecord = new ProducerRecord<>("following", username);
	        RequestReplyFuture<String, String, String> future = replyingKafkaTemplate.sendAndReceive(producerRecord);
	        ConsumerRecord<String, String> consumerRecord = future.get();
	        String result = consumerRecord.value();
	        System.out.println(result);
	        UsersMessage usersMessage = objectMapper.readValue(result, UsersMessage.class);
	        return usersMessage.getUsers();
	   }
	  
	  public UsersFollowBlockMute getFollowBlockMute(String username) throws JsonMappingException, JsonProcessingException, InterruptedException, ExecutionException{
	     	System.out.println("HI blocked!");   
	     	ProducerRecord<String, String> producerRecord = new ProducerRecord<>("follow-block-mute", username);
	        RequestReplyFuture<String, String, String> future = replyingKafkaTemplate.sendAndReceive(producerRecord);
	        ConsumerRecord<String, String> consumerRecord = future.get();
	        String result = consumerRecord.value();
	        System.out.println(result);
	        UsersFollowBlockMute usersMessage = objectMapper.readValue(result, UsersFollowBlockMute.class);
	        return usersMessage;
	   }
	  
	  public List<User> getRequested(String username) throws JsonMappingException, JsonProcessingException, InterruptedException, ExecutionException{
	     	System.out.println("HI!");   
	     	ProducerRecord<String, String> producerRecord = new ProducerRecord<>("requested", username);
	        RequestReplyFuture<String, String, String> future = replyingKafkaTemplate.sendAndReceive(producerRecord);
	        ConsumerRecord<String, String> consumerRecord = future.get();
	        String result = consumerRecord.value();
	        System.out.println(result);
	        UsersMessage usersMessage = objectMapper.readValue(result, UsersMessage.class);
	        return usersMessage.getUsers();
	   }
	  
}
