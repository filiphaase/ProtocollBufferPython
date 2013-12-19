package com.example.tutorial;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import com.example.tutorial.AddressBookProtos.Person;


public class TestRunner {

	public static void main(String args[]){
		Person.Builder pBuilder = Person.newBuilder()
				.setEmail("max@hotmail.de")
				.setName("MaxMuster")
				.setId(2);
		
		Person.PhoneNumber.Builder phoneBuilder = Person.PhoneNumber.newBuilder()
				//.setType(Person.PhoneType.HOME)
				.setNumber("0123456789");
		
		Person.PhoneNumber phoneNumber = phoneBuilder.build();
		pBuilder.addPhone(phoneNumber);
		
		try {
			pBuilder.build().writeTo(new FileOutputStream(new File("output.txt")));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println("Finished");
	}
	
	/*public static void main(String[] args) {
		Person.Builder pBuilder = Person.newBuilder();
		pBuilder.setEmail("Peter@web.de");
		pBuilder.setName("Peter");
		Person.PhoneNumber.Builder nBuilder = Person.PhoneNumber.newBuilder();
		nBuilder.setNumber("1234141");
		Person.PhoneNumber phoneNumber = nBuilder.build();
		pBuilder.addPhone(phoneNumber);
		pBuilder.setId(2);
		
		try {
			pBuilder.build().writeTo(new FileOutputStream(new File("output.txt")));
		} catch (IOException e) {
			e.printStackTrace();
		}	
	}*/
}
