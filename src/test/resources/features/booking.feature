Feature: Booking API End to End flow


  @smoke
  Scenario: Create, Update, Get and Delete Booking
    Given user has a valid auth token
    When  user creates a new booking
    And user updates the booking using PATCH
    And user fetches the booking by id
    Then user deletes the booking
    And booking should not exist anymore

  @sanity
  Scenario Outline: Create booking with different name
    Given user has a valid auth token
    When user creates booking with "<firstname>" and "<lastname>"
    Then booking should be created succesfully

    Examples:
      | firstname | lastname  |
      | Anjali    | QA        |
      | Shubham   | Tester    |
      | Vaishnavi | Developer |




