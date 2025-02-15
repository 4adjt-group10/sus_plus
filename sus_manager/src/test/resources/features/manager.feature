Feature: Professional Manager API

  Scenario Template: List all professionals
    Given that I create a professional with "<document>" and "<name>"
    When I look for all professionals
    Then the response status code should be "200"
    Examples:
      |  document |name  |
      |12345678901111|Teste1111|

  Scenario Template: Find a professional by ID
    Given that I create a professional with "<document>" and "<name>"
    When I look for a professional by id
    Then the response status code should be "200"
    Examples:
      |  document |name  |
      |12345678902111|Teste2111|

  Scenario Template: Create a new professional
    Given that I create a professional with "<document>" and "<name>"
    Then the response status code should be "201"
    Examples:
      |  document |name  |
      |12345678903111|Teste3111|

  Scenario Template: Attempt to create a professional with invalid data
    Given that I create a professional with "<document>" and "<name>" error
    Then the response status code should be "404"
    Examples:
      |  document |name  |
      |12345678904111|Teste4111|

  Scenario Template: Delete a professional
    Given that I create a professional with "<document>" and "<name>"
    When I delete the professional
    Then the response status code should be "204"
    Examples:
      |  document |name  |
      |12345678905111|Teste5111|