package com.abc;
import org.junit.*;
import static org.junit.Assert.*;

import java.time.LocalDate;

import org.junit.Test;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.MatcherAssert.assertThat;


public class AccountTest {
	private Account checkingAccount;
	private Account savingAccount;
	private Account maxiSavingAccount;
	private static final double DOUBLE_DELTA = 1e-15;
	
	@Before
	public void setUp(){
        checkingAccount = new Account(AccountType.CHECKING);
        savingAccount = new Account(AccountType.SAVINGS);
        maxiSavingAccount = new Account(AccountType.MAXI_SAVINGS);
	}

//=====Transaction tests======
	@Test
	public void ValidAccountDepositTest(){
		assertThat(0.0d,is(checkingAccount.getCurrentBalance()));
		checkingAccount.deposit(100.0d);
		assertThat(100.0d,is(checkingAccount.getCurrentBalance()));
		
	}
	
//similar test to saving and maxi-saving, ignored here
	
	@Test(expected=IllegalArgumentException.class)
	public void InvalidAmountAccountDepositTest(){
		assertThat(0.0d,is(checkingAccount.getCurrentBalance()));
		checkingAccount.deposit(-100.0d);
	}
	
//similar test for deposit 0d ...
//similar tests for other account types...

	@Test
	public void ValidAccountWithDrawTest(){
		assertThat(0.0d,is(checkingAccount.getCurrentBalance()));
		checkingAccount.deposit(100.0d);
		checkingAccount.withdraw(20.0d);
		assertThat(80.0d,is(checkingAccount.getCurrentBalance()));
		
	}
	
//similar test to saving and maxi-saving, ignored here
	
	@Test(expected=IllegalArgumentException.class)
	public void InvalidAmountWithdrawTest(){
		assertThat(0.0d,is(checkingAccount.getCurrentBalance()));
		checkingAccount.withdraw(-100.0d);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void OverdraftNotAllowedinWithdrawTest(){
		assertThat(0.0d,is(checkingAccount.getCurrentBalance()));
		checkingAccount.deposit(99.0d);
		checkingAccount.withdraw(100.0d);
	}
	//similar test to saving and maxi-saving, ignored here
	
	@Test
	public void validTransferTest(){
		assertThat(0.0d,is(checkingAccount.getCurrentBalance()));
		checkingAccount.deposit(100.0d);
		savingAccount.transferTo(checkingAccount, 20.0d);
		assertThat(80.0d,is(checkingAccount.getCurrentBalance()));
		assertThat(20.0d,is(savingAccount.getCurrentBalance()));
		
	}
	
//====Interest Calculation Test
	@Test
	public void chequingAccountEarnCorrectInterestTest(){
		assertThat(0.0d,is(checkingAccount.getCurrentBalance()));
		checkingAccount.deposit(100.0d);
		assertEquals(0.1/365.0,checkingAccount.interestEarned(LocalDate.now()),DOUBLE_DELTA);
	}
	
	@Test
	public void maxiSavingAccountEarnCorrectInterestTest(){
		assertThat(0.0d,is(maxiSavingAccount.getCurrentBalance()));
		maxiSavingAccount.deposit(100.0d);
		assertEquals(5.0/365.0,maxiSavingAccount.interestEarned(LocalDate.now()),DOUBLE_DELTA);
	}

}
