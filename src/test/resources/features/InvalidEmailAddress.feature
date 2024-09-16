@flooidFT
Feature: Invalid Email Address Validation
  As a Flooid sales consultant, I need all contact details to contain a valid email address so that I can contact the potential customer.

  Background:
    Given I am on the Flooid contact page

  @invalidEmailAddress
  Scenario Outline: Contact form rejects invalid email addresses
    When I enter an invalid email address "<email>"
    Then I should see an error message "Please enter a valid email address!"

    Examples:
      | email                  |
      | invalid-email          |
      | user@                  |
      | @example.com           |
      | user@example           |
      | user@example..com      |
      | user@.com              |
      | user.@example.com      |
      | user@example,com       |
      | user@ example.com      |
      | user@example .com      |
      | user@domain@domain.com |

  @invalidEmailSpecialChars
  Scenario Outline: Contact form rejects emails with invalid special characters
    When I enter an invalid email address "<email>"
    Then I should see an error message "Please enter a valid email address!"

    Examples:
      | email             |
      | user@domain#com   |
      | user@domain!com   |
      | user@domain,com   |
      | user@domain;com   |
      | user@domain?com   |
      | user@example..com |

  @emailMaxLength
  Scenario Outline: Email address exceeds maximum length
    When I enter an invalid email address "<email>"
    Then I should see an error message "Email address is too long."

    Examples:
      | email                                                                             |
      | a_very_very_long_email_address_that_exceeds_max_length_beyond_standard@domain.com |
