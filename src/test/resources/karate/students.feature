Feature: Student API tests

  Background:
    * url 'http://localhost:8080/api/students'
    * def students = read('classpath:students.json')
    * def sports = read('classpath:sports.json')
    * def parents = read('classpath:parents.json')

  Scenario: Create a student
    Given url 'http://localhost:8080/api/sports'
    Given request sports[0]
    When method post
    Then status 200
    * def sportsId = response.id

    Given url 'http://localhost:8080/api/parents'
    * set parents[0].sportsBackground[0].id = sportsId
    Given request parents[0]
    When method post
    Then status 200
    * def parentsId = response.id

    * set students[0].parents[0].id = parentsId
    * set students[0].preferredSports[0].id = sportsId
    * set students[0].suitableSports[0].id = sportsId

    Given url 'http://localhost:8080/api/students'
    Given request students[0]
    When method post
    Then status 200
    * print response


  Scenario: Get student by id
    Given url 'http://localhost:8080/api/sports'
    Given request sports[0]
    When method post
    Then status 200
    * def sportsId = response.id

    Given url 'http://localhost:8080/api/parents'
    * set parents[0].sportsBackground[0].id = sportsId
    Given request parents[0]
    When method post
    Then status 200
    * def parentsId = response.id

    * set students[0].parents[0].id = parentsId
    * set students[0].preferredSports[0].id = sportsId
    * set students[0].suitableSports[0].id = sportsId

    Given url 'http://localhost:8080/api/students'
    Given request students[0]
    When method post
    Then status 200

    * def id = response.id
    Given path id
    When method get
    Then status 200
    And match response.id == id
    * print response

  Scenario: Get all students
    When method get
    Then status 200
    And match response == '#[]' || '#notnull'
    * print response

  Scenario: Delete student by id
    Given url 'http://localhost:8080/api/sports'
    Given request sports[0]
    When method post
    Then status 200
    * def sportsId = response.id

    Given url 'http://localhost:8080/api/parents'
    * set parents[0].sportsBackground[0].id = sportsId
    Given request parents[0]
    When method post
    Then status 200
    * def parentsId = response.id

    * set students[0].parents[0].id = parentsId
    * set students[0].preferredSports[0].id = sportsId
    * set students[0].suitableSports[0].id = sportsId

    Given url 'http://localhost:8080/api/students'
    Given request students[0]
    When method post
    Then status 200

    * def id = response.id
    Given path id
    When method delete
    Then status 204
    And match response.id != id