package com.mwl.sms;

import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @author mawenlong
 * @date 2018/8/26
 * describe: 
 */
@Component
public class SmsListener {

	@JmsListener(destination="sms")
	public void sendSms(Map<String,String> map){
		
		try {
			System.out.println("发送短信:"+map.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
}
