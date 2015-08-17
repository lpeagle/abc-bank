package com.abc;

import java.time.LocalDateTime;
import java.time.Month;

/**
 * As a simple test, there is no need to add a mock framework. We use this mock the date of transactions
 * 
 * @author bill
 *
 */

public class MockDateProvider implements DateProvider{

	LocalDateTime datetime=LocalDateTime.of(2015,Month.JUNE,1,10,10,10);
	
	MockDateProvider(){
	}
	
	public LocalDateTime now() {
		// TODO Auto-generated method stub
		return datetime;
	}
	
	public void setDatetime(int year, Month month,int date, int hour, int minute, int second){
		datetime=LocalDateTime.of(year, month,date,hour,minute,second);
	}
		
	

}
