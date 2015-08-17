Feature: As a bank customer, I need to open accounts

  Scenario: Open a new checking account 
    Given customer Joe has no account in the bank 
    When a checking account is opened for Joe
    Then Joe has 1 checking account