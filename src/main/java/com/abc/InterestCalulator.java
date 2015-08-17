package com.abc;

import java.time.LocalDate;

public interface InterestCalulator {
	double accrueDailyInterest(Account account, LocalDate interestDate,double dailyBalance);
}
