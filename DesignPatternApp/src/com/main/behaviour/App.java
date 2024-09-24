package com.main.behaviour;

public class App {
	public static void main(String[] args) {
		Manager manager = new Manager(); 
		AssistantManager assistantManager = new AssistantManager();
		Executive executive = new Executive();

		//rules of forward handling 
		executive.forwardHandler(assistantManager); 
		assistantManager.forwardHandler(manager);

		executive.applyLoan("AERBVG", 650000);
	}

}
