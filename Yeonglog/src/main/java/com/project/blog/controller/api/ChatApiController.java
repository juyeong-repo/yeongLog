package com.project.blog.controller.api;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.project.blog.exception.YeongLogException;

import org.apache.tomcat.util.codec.binary.Base64;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;


import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
	
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Date;
import java.util.UUID;

@Controller
public class ChatApiController {
	

	private static final Logger log = LoggerFactory.getLogger(ChatApiController.class);
	
    private static String secretKey = "Q0hHekJOZVhMcGZ1aFdhemF3a3htV1BVeE1wTlpDTkg=";
    private static String apiUrl = "https://r1qcwstr4q.apigw.ntruss.com/custom/v1/8653/d743a2cd80fab02b7a0e4ab620b7b7941e08384211afd0ceca756d58493d5c35";
    
      @MessageMapping("/sendMessage")
      @SendTo("/topic/public")
   // @RequestMapping(value="/test", method = RequestMethod.GET)
    public String sendMessage (@Payload String chatMessage) throws IOException {
    	
    	URL url = new URL(apiUrl);
    	
    	try {
			String message = getReqMessage(chatMessage);
			String encodeBase64String = makeSignature(message, secretKey);
				
			//api 서버 접속
			HttpURLConnection con = (HttpURLConnection)url.openConnection();
			con.setRequestMethod("POST");
			con.setRequestProperty("content-Type", "application/json;UTF-8");
			con.setRequestProperty("X-NCP-CHATBOT_SIGNATURE", encodeBase64String);
			
			// post request
			con.setDoOutput(true);
			DataOutputStream wr = new DataOutputStream(con.getOutputStream());
			wr.write(message.getBytes("UTF-8"));
			wr.flush();
			wr.close();
			 
			int responseCode = con.getResponseCode();
			
			BufferedReader br;
			
			if(responseCode == 200) { // Normal call
				
				BufferedReader in = new BufferedReader(
					new InputStreamReader(con.getInputStream(), "UTF-8")
						);
				
				String decodedString;
				String jsonString = "";
				
				while ((decodedString = in.readLine()) != null) {
					jsonString = decodedString;
				}
		
				
				JSONParser jsonParser = new JSONParser();
				try {
					JSONObject json = (JSONObject)jsonParser.parse(jsonString);
					JSONArray bubbleArray = (JSONArray) json.get("bubbles");
					JSONObject bubbles = (JSONObject)bubbleArray.get(0);
					JSONObject data = (JSONObject)bubbles.get("data");
					
					String description = "";
					description = (String)data.get("description");
					chatMessage = description;
					
				} catch (ParseException e) {
					e.printStackTrace();
				}
				
				in.close();
				
			} else { //Error Occured
				chatMessage = con.getResponseMessage();
			}
			
		} catch (YeongLogException e) {
			e.printStackTrace();
		}
    	
    	return chatMessage;
    	
    }
	
	//voiceMessage 인자를 받았는데 이름을 chatMessage로 변경함
    public static String getReqMessage (String chatMessage) throws YeongLogException { 
    	
    	String requestBody = "";
    	
    	String uuid = UUID.randomUUID().toString();
    	log.debug(uuid);
    	
    	try {
    	       JSONObject obj = new JSONObject();

               long timestamp = new Date().getTime();
               System.out.println("timeStamp ::" + timestamp);
               
               obj.put("version", "Test version.");
               obj.put("userId", uuid);
               obj.put("timeStamp", timestamp);
               
               JSONObject bubbleObj = new JSONObject();
               bubbleObj.put("type", "text");
               
               JSONObject dataObj = new JSONObject();
               dataObj.put("description", chatMessage);
               
               bubbleObj.put("type", "text");
               bubbleObj.put("data", dataObj);
               
               
               //json데이터 모두 simple로 imported, check if it doesn't work and change it to json.org.
               JSONArray bubbleArray = new JSONArray();
               bubbleArray.add(bubbleObj);
               
               obj.put("bubbles", bubbleArray);
               obj.put("event", "send");
               
               requestBody = obj.toString();

               
    } catch (Exception e) {
		throw new YeongLogException("requestBody 포맷 변경 실패	");
	}
    	return requestBody;
    }
	
	
	
    public static String makeSignature (String message , String secretKey) throws YeongLogException{
    	
    	String encodeBase64String = "";
    	
    	try {
    		
    	   	//SecretSpec 클래스의 생성자를 사용해서 바이트 배열의 데이터키를 비밀키로 변환
        	byte [] secretKeyBytes = secretKey.getBytes("UTF-8");
        	SecretKeySpec signinKey = new SecretKeySpec(secretKeyBytes, "HmacSHA256");
        	Mac mac = Mac.getInstance("HmacSHA256");
        	mac.init(signinKey);
        	
        	
        	byte[] rawHmac = mac.doFinal(message.getBytes("UTF-8"));
        	encodeBase64String =Base64.encodeBase64String(rawHmac);
        	
        	return encodeBase64String;
        	
		} catch (Exception e) {
			throw new YeongLogException("메시지 암호화 실패");
		}
    	
    	//return encodeBase64String ;

    }
	
	
	
	

}
