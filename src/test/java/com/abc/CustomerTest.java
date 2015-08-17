package com.abc;

import org.junit.Ignore;
import org.junit.Test;
import static org.hamcrest.Matchers.*;

import static org.junit.Assert.assertEquals;
import static org.hamcrest.MatcherAssert.assertThat;

import org.junit.Before;

public class CustomerTest {
	
	private Customer customer=null;
	
	@Before
	public void setUp(){
		customer=new Customer("customer1");
	}

//====Account opening tests	
    @Test
    public void CustomerCanOpenAccountTest(){
        customer.openAccount(new Account(AccountType.SAVINGS));
        assertThat(1, is(customer.getNumberOfAccounts()));
        assertThat("customer1", is(customer.getName()));
        
    }

    @Test
    public void customerCanAddAccountTest(){
        customer.openAccount(new Account(AccountType.SAVINGS));
        customer.openAccount(new Account(AccountType.CHECKING));
        assertThat(2, is(customer.getNumberOfAccounts()));
        assertThat("customer1", is(customer.getName()));
    }

    @Ignore
    public void testThreeAcounts() {
        Customer oscar = new Customer("Oscar")
                .openAccount(new Account(AccountType.SAVINGS));
        oscar.openAccount(new Account(AccountType.CHECKING));
        assertEquals(3, oscar.getNumberOfAccounts());
    }
    
   
    @Test //Test customer statement generation
    public void testApp(){

        Account checkingAccount = new Account(AccountType.CHECKING);
        Account savingsAccount = new Account(AccountType.SAVINGS);

        Customer henry = new Customer("Henry").openAccount(checkingAccount).openAccount(savingsAccount);

        checkingAccount.deposit(100.0);
        savingsAccount.deposit(4000.0);
        savingsAccount.withdraw(200.0);

        assertEquals("Statement for Henry\n" +
                "\n" +
                "Checking Account\n" +
                "  deposit $100.00\n" +
                "Total $100.00\n" +
                "\n" +
                "Savings Account\n" +
                "  deposit $4,000.00\n" +
                "  withdrawal $200.00\n" +
                "Total $3,800.00\n" +
                "\n" +
                "Total In All Accounts $3,900.00", henry.getStatement());
    }

}
