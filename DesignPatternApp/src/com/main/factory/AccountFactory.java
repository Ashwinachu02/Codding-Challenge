package com.main.factory;

public class AccountFactory {
	public Account getAccount(AccountType type) {
		if(type.equals(AccountType.SAVINGS))
			return new SavingsAccount(); 
		if(type.equals(AccountType.CURRENT))
			return new CurrentAccount(); 

		return null;
	}

}
