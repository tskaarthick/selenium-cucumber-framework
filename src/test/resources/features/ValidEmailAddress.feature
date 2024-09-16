@flooidFT
Feature: Valid Email Address in Contact Details
  As a Flooid sales consultant, I need all contact details to contain a valid email address so that I can contact the potential customer.

  Background:
    Given I am on the Flooid contact page

  @validEmailAddress
  Scenario Outline: Contact form accepts valid email addresses
    When I enter a valid email address "<email>"
    Then I should not see an error message "Please enter a valid email address!"

    Examples:
      | email                     |
      | user@example.com          |
      | user.name@domain.com      |
      | user_name123@domain.co    |
      | user+mailbox@domain.org   |
      | user@subdomain.domain.com |
      | name@domain.travel        |
      | name@domain.international |
      | user@example.net          |
      | test@domain.edu           |
      | name@domain.co.uk         |

  @emailAddressRequired
  Scenario: Email address field is required
    When I leave the email address field empty
    Then I should see an error message "This is a required field."
