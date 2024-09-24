package com.main.factory;

public class SavingsAccount implements Account{

	private double rateOfInterest; 
	private double annualCharges; 


	public SavingsAccount() {
		super();
		this.rateOfInterest = 4.0;
		this.annualCharges = 200;
	}


	@Override
	public String getAccountDetails() {
		 String details = "Account Details: ROI: Rs." + rateOfInterest + " Annual Charge Rs." + annualCharges; 
		return details;
	}



	public double getRateOfInterest() {
		return rateOfInterest;
	}


	public double getAnnualCharges() {
		return annualCharges;
	}

}