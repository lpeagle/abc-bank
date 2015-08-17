package com.abc;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Calculate interest for different accounts To simplify, we assume daily
 * interest rate = annual interest rate/365
 * 
 * @author bill
 *
 */
public class SimpleInterestRateCalculator implements InterestCalulator {

	/**
	 * Calculate interest for an account daily. we assume that interest date need to
	 * be on or later than any transaction happened
	 * This method is used to add interest to account daily rather than do all the calculation in one shot at the end 
	 */
	public double accrueDailyInterest(Account account, LocalDate interestDate,double currentBalance) {
		if ((account.getTransactions() == null) || (account.getTransactions().isEmpty()))
			return 0;
		Transaction lastTransaction = account.getTransactions().get(account.getTransactions().size() - 1);
		if (interestDate.isBefore(lastTransaction.getTransactionDate().toLocalDate()))
			throw new RuntimeException("Account transactions invalid state:try to calculate interest on the past");
		double dailyInterest=0.0d;
		//double currentBalance = account.getCurrentBalance();
		if (account.getAccountType() == AccountType.SAVINGS) {
			if (currentBalance <= 1000)
				dailyInterest= currentBalance * 0.001 / 365.0;
			else
				dailyInterest = 1000 * 0.001 / 365.0 + (currentBalance - 1000) * 0.002 / 365.0;
			// to have an interest rate of 5% assuming no withdrawals in the
			// past 10 days otherwise 0.1%
		} else if (account.getAccountType() == AccountType.MAXI_SAVINGS) {
			List<Transaction> transactions = account.getTransactions();
			boolean isWithdrawalInLast10Days = false;
			for (int i = transactions.size() - 1; i >= 0; i--) {
				if (transactions.get(i).getAmount() < 0
						&& transactions.get(i).getTransactionDate().toLocalDate().plusDays(10).isAfter(interestDate)) {
					isWithdrawalInLast10Days = true;
					break;
				}
			}
			if (isWithdrawalInLast10Days)
				dailyInterest = currentBalance * 0.001 / 365.0;
			else
				dailyInterest = currentBalance * 0.05 / 365.0;
	
			// checking flat rate
		} else {
			dailyInterest = currentBalance * 0.001 / 365.0;
		}
		return dailyInterest;

	}

}
