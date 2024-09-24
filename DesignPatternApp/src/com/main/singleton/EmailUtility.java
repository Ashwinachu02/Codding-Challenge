package com.main.singleton;

//singleton design pattern
//singelton class that manages of sending emails
public class EmailUtility {
	private static EmailUtility emailUtility; 
	private EmailUtility(){ //step 1: make constructor private

	}

	static {
		emailUtility = new EmailUtility(); //in static mem : 10X 
	}

	public void sendEmail(String message ) {
		//logic code of email send
		System.out.println("Email sent successfully... " + message);
	}

	public static EmailUtility getInstance() {
		return emailUtility;
	}



}
