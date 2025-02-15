Feature: Professional Manager API

  Scenario Template: List all professionals
    Given that I create a professional with "<document>" and "<name>"
    When I look for all professionals
    Then the response status code should be "200"
    Examples:
      |  document |name  |
      |1234567890111|Teste111|

  Scenario Template: Find a professional by ID
    Given that I create a professional with "<document>" and "<name>"
    When I look for a client by id
    Then the response status code should be "200"
    Examples:
      |  document |name  |
      |1234567890211|Teste211|

  Scenario Template: Create a new professional
    Given that I create a professional with "<document>" and "<name>"
    Then the response status code should be "201"
    Examples:
      |  document |name  |
      |1234567890311|Teste311|

  Scenario Template: Attempt to create a professional with invalid data
    Given that I create a professional with "<document>" and "<name>" error
    Then the response status code should be "404"
    Examples:
      |  document |name  |
      |1234567890411|Teste411|

  Scenario Template: Delete a professional
    Given that I create a professional with "<document>" and "<name>"
    When I delete the client
    Then the response status code should be "204"
    Examples:
      |  document |name  |
      |1234567890511|Teste511|