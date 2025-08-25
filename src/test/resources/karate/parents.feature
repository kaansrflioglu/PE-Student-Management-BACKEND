Feature: Student API tests

  Background:
    * url 'http://localhost:8080/parents'
    * def parents = read('classpath:parents.json')
    * def sports = read('classpath:sports.json')

  Scenario: Create a parent
    Given url 'http://localhost:8080/sports'
    Given request sports[0]
    When method post
    Then status 200
    * def sportsId = response.id

    Given url 'http://localhost:8080/parents'
    * set parents[0].sportsBackground[0].id = sportsId
    Given request parents[0]
    When method post
    Then status 200
    * print response

  Scenario: Get parent by id
    Given url 'http://localhost:8080/sports'
    Given request sports[0]
    When method post
    Then status 200
    * def sportsId = response.id

    Given url 'http://localhost:8080/parents'
    * set parents[0].sportsBackground[0].id = sportsId
    Given request parents[0]
    When method post
    Then status 200
    * def id = response.id

    Given path id
    When method get
    Then status 200
    And match response.id == id
    * print response

  Scenario: Get all parents
    When method get
    Then status 200
    And match response == '#[]' || '#notnull'
    * print response

  Scenario: Delete parent by id
    Given url 'http://localhost:8080/sports'
    Given request sports[0]
    When method post
    Then status 200
    * def sportsId = response.id

    Given url 'http://localhost:8080/parents'
    * set parents[0].sportsBackground[0].id = sportsId
    Given request parents[0]
    When method post
    Then status 200
    * def id = response.id

    Given path id
    When method delete
    Then status 204
    And match response.id != id