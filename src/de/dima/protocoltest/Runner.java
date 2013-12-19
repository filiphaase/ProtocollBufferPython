package de.dima.protocoltest;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Reader;

import de.dima.protocoltest.KeyValueProtos.KeyValuePair;


public class Runner {

	public KeyValuePair randomKeyValuePair(){
		KeyValuePair.Builder kvb = KeyValuePair.newBuilder()
				.setKey("4")
				.setValue("6");
		
		return kvb.build();
	}
	public static void main(String[] args) {
		
		Process p;
		
		try {
			p = Runtime.getRuntime().exec("python test.py");
			
            BufferedReader input = new BufferedReader(new InputStreamReader(p.getInputStream()));
            BufferedReader err = new BufferedReader(new InputStreamReader(p.getErrorStream()));
            OutputStream processInput = p.getOutputStream(); // this thing is buffered with 8k
            
            //while(processInput);
            
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
