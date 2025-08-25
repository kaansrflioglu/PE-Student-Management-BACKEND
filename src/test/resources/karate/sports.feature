Feature: Student API tests

  Background:
    * url 'http://localhost:8080/api/sports'
    * def sports = read('classpath:sports.json')

  Scenario: Create a sport
    Given request sports[0]
    When method post
    Then status 200
    * print response

  Scenario: Get sport by id
    Given request sports[0]
    When method post
    Then status 200
    * def id = response.id

    Given path id
    When method get
    Then status 200
    And match response.id == id
    * print response

  Scenario: Get all sports
    When method get
    Then status 200
    And match response == '#[]' || '#notnull'
    * print response

  Scenario: Delete sport by id
    Given request sports[0]
    When method post
    Then status 200
    * def id = response.id

    Given path id
    When method delete
    Then status 204
    And match response.id != id