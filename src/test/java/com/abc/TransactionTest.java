package com.abc;

import org.junit.Test;

import static org.junit.Assert.assertTrue;

import java.time.LocalDateTime;

public class TransactionTest {
    @Test
    public void transaction() {
        Transaction t = new Transaction(5,LocalDateTime.now(),5.0d);
        assertTrue(t instanceof Transaction);
    }
}
