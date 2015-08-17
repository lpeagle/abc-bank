Feature: As a bank customer, I need to do transactions including deposit, withdraw and transfer

  Scenario: Deposit into a checking account 
    Given customer Joe has new checking account
    When $100.00 is deposited into Joe's checking account
    Then Joe's checking account has a balance of $100.00