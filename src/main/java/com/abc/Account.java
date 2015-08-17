package com.abc;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Basic account operation such as deposit, withdraw and transfer 
 * assuming transaction finished in an atomic way is not a concern
 * 
 * @author bill
 *
 */
public class Account {

	private AccountType accountType;
	private List<Transaction> transactions;
	private double currentBalance=0.0d;
	private double interestAccured=0;
	private DateProvider dateProvider=null;
	private InterestCalulator interestCalulator=null;

	public Account(AccountType accountType) {
		this(accountType,new SimpleDateProvider(),new SimpleInterestRateCalculator());
	}
	
	//allow easy test by inserting a mock date
	protected Account(AccountType accountType,DateProvider dateProvider,
			InterestCalulator interestCalulator) {
		this.accountType = accountType;
		this.transactions = new ArrayList<Transaction>();
		this.dateProvider=dateProvider;
		this.interestCalulator=interestCalulator;
	}
	

	public void deposit(double amount) {
		if (amount <= 0) {
			throw new IllegalArgumentException("amount must be greater than zero");
		} else {
			Transaction transaction= new Transaction(amount,dateProvider.now(),currentBalance);
			transactions.add(transaction);
			currentBalance=transaction.getCurrentBalance();
		}
	}

	public void withdraw(double amount) {
		if (amount <= 0) {
			throw new IllegalArgumentException("amount must be greater than zero");
			// suppose overdraft not allowed
		} else if (currentBalance < amount) {
			throw new IllegalArgumentException("amount must be greater than exzisting balance of account");
		} else {
			Transaction transaction= new Transaction(-amount,dateProvider.now(),currentBalance);
			transactions.add(transaction);
			currentBalance=transaction.getCurrentBalance();
		}
	}
	
	public void transferTo(Account fromAccount, double amount){
		if (amount <= 0)
			throw new IllegalArgumentException("amount must be greater than zero");
		this.deposit(amount);
		fromAccount.withdraw(amount);
		
	}
	
	public double getDailyInterest(LocalDate date,double dailyBalance){
		return interestCalulator.accrueDailyInterest(this, date,dailyBalance);
	}

	public double interestEarned(LocalDate interestUpToDate) {
		if (transactions==null||transactions.isEmpty())
			return 0.0d;
		double totalInterest=0.0d;
		Transaction dateEndTransaction=transactions.get(0);
		LocalDate nextTransactionDate=null;
		for (int i=0;i<transactions.size();i++){
			//same date transactions, move to find the last one of that day
			if(transactions.get(i).getTransactionDate().toLocalDate().equals(
					dateEndTransaction.getTransactionDate().toLocalDate())){
				dateEndTransaction=transactions.get(i);
			//transaction of new date. calculate interest of last date
			}else if(transactions.get(i).getTransactionDate().toLocalDate().isAfter(
					dateEndTransaction.getTransactionDate().toLocalDate())){
				nextTransactionDate=transactions.get(i).getTransactionDate().toLocalDate();
				//calculate interest up to the date
				LocalDate interestDate=dateEndTransaction.getTransactionDate().toLocalDate();
				for (int j=0;!interestDate.plusDays(j).isAfter(nextTransactionDate);j++)
					totalInterest+=getDailyInterest(interestDate.plusDays(j),dateEndTransaction.getCurrentBalance());
				dateEndTransaction=transactions.get(i);
			
			}
			
			
		}
		//interest from the last transaction to the interest date
		if(!interestUpToDate.isBefore(dateEndTransaction.getTransactionDate().toLocalDate())) {  //new date
			//calculate interest up to the date
			LocalDate interestDate=dateEndTransaction.getTransactionDate().toLocalDate();
			for (int j=0;!interestDate.plusDays(j).isAfter(interestUpToDate);j++)
				totalInterest+=getDailyInterest(interestDate.plusDays(j),dateEndTransaction.getCurrentBalance());
		}
		return totalInterest;
	}

	// Get balance of account
	public double sumTransactions() {
		return checkIfTransactionsExist(true);
	}

	private double checkIfTransactionsExist(boolean checkAll) {
		double amount = 0.0;
		for (Transaction t : transactions)
			amount += t.getAmount();
		return amount;
	}

	public AccountType getAccountType() {
		return accountType;
	}

	public double getCurrentBalance() {
		return currentBalance;
	}

	public List<Transaction> getTransactions() {
		return transactions;
	}

	public double getInterestAccured() {
		return interestAccured;
	}

	public void setInterestAccured(double interestAccured) {
		this.interestAccured = interestAccured;
	}
	

	

}
