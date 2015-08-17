package com.abc.bdd;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.abc.Account;
import com.abc.AccountType;
import com.abc.Bank;
import com.abc.Customer;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import static org.junit.Assert.*;

public class Steps {
	private Bank bank = new Bank();
	private Map<String, Customer> customerMap = new HashMap<String, Customer>();

	@Given("^customer (.*) has no account in the bank$")
	public void newCustomer(String customerName) {
		Customer customer = new Customer(customerName);
		bank.addCustomer(customer);
		customerMap.put(customerName, customer);

	}

	@When("^a (.*) account is opened for (.*)$")
	public void openAccountforCustomer(String accountTypeStr, String customerName) {
		AccountType accountType = null;
		if (accountTypeStr.equals("checking"))
			accountType = AccountType.CHECKING;
		else if (accountTypeStr.equals("saving"))
			accountType = AccountType.SAVINGS;
		Customer customer = customerMap.get(customerName);
		if (customer == null)
			fail("Customer " + customerName + " not found");
		else {
			Account account = new Account(accountType);
			customer.openAccount(account);
		}
	}

	@Then("^(.*) has (\\d+) checking account$")
	public void checkForCheckingAccountNumber(String customerName,int accountNumber) {
		Customer customer = customerMap.get(customerName);
		List<Account> accounts=null;
		if (customer == null)
			fail("Customer " + customerName + " not found");
		else {
			accounts=customer.getAccounts();
		}
		int checkingAccountNumber=0;
		for (Account account:accounts){
			if(account.getAccountType().equals(AccountType.CHECKING))
				checkingAccountNumber++;
		}

		assertEquals(accountNumber,checkingAccountNumber);
	}
}
