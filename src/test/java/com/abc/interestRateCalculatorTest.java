package com.abc;

import java.time.LocalDate;
import java.time.Month;

import org.junit.*;
import static org.junit.Assert.*;

public class interestRateCalculatorTest {
	DateProvider dateProvider=null;
	InterestCalulator interestCalulator;
	private static final double DOUBLE_DELTA = 1e-15;
	
	@Before
	public void setUp(){
		dateProvider=new MockDateProvider();
		interestCalulator=new SimpleInterestRateCalculator();
	}
	
	@Test
	public void checkingAccrueDailyInterestTest(){
		LocalDate date=LocalDate.of(2015, Month.JUNE, 1);
		Account account=new Account(AccountType.CHECKING,dateProvider,interestCalulator);
		account.deposit(100.0d);
		assertEquals(0.1d/365.0,interestCalulator.accrueDailyInterest(account, date,100.0d),DOUBLE_DELTA);
	}
	
	@Test
	public void maxiSavingAccountAccrueDailyInterestTest(){
		LocalDate date=LocalDate.of(2015, Month.JUNE, 1);
		Account account=new Account(AccountType.MAXI_SAVINGS,dateProvider,interestCalulator);
		account.deposit(100.0d);
		assertEquals(5d/365.0,interestCalulator.accrueDailyInterest(account, date,100.0d),DOUBLE_DELTA);
	}
	
	@Test
	public void maxiSavingAccountAccrueDailyInterestWithWithdrawalTest(){
		LocalDate date=LocalDate.of(2015, Month.JUNE, 2);
		Account account=new Account(AccountType.MAXI_SAVINGS,dateProvider,interestCalulator);
		account.deposit(100.0d);
		((MockDateProvider)dateProvider).setDatetime(2015, Month.JUNE, 2, 10, 10, 1);
		account.withdraw(10.0d);
		assertEquals(90d*0.001/365.0,interestCalulator.accrueDailyInterest(account, date,90.0d),DOUBLE_DELTA);
	}


}
