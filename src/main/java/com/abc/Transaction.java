package com.abc;

import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Date;
import java.util.function.DoublePredicate;

public class Transaction {
	//BigDecimal may be a better type for the money. I will leave it to simplify
    private final double amount;
    private double currentBalance;
    
    private LocalDateTime transactionDate;

    public Transaction(double amount, LocalDateTime transactionDate,double previousBalance) {  
        this.amount = amount;
        this.transactionDate = transactionDate;
        this.currentBalance=previousBalance+amount;
    }

	public double getCurrentBalance() {
		return currentBalance;
	}

	public void setCurrentBalance(double currentBalance) {
		this.currentBalance = currentBalance;
	}

	public LocalDateTime getTransactionDate() {
		return transactionDate;
	}

//	public void setTransactionDate(Date transactionDate) {
//		this.transactionDate = transactionDate;
//	}

	public double getAmount() {
		return amount;
	}
    
    

}
