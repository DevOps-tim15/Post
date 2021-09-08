package uns.ac.rs.postservice.kafka;

import java.util.concurrent.ExecutionException;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.requestreply.ReplyingKafkaTemplate;
import org.springframework.kafka.requestreply.RequestReplyFuture;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;

@Service
public class Producer {

	  @Autowired
	  private ReplyingKafkaTemplate<String, String, String> replyingKafkaTemplate;
	  
	  public String checkUser(String authToken) throws JsonMappingException, JsonProcessingException, InterruptedException, ExecutionException{
	     	System.out.println("HI!");   
	     	ProducerRecord<String, String> producerRecord = new ProducerRecord<>("logged-in", authToken);
	        RequestReplyFuture<String, String, String> future = replyingKafkaTemplate.sendAndReceive(producerRecord);
	        ConsumerRecord<String, String> consumerRecord = future.get();
	        String username = consumerRecord.value();
	        System.out.println(username);
	        return username;
	   }
}
