Feature: Student API tests

  Background:
    * url 'http://localhost:8080/students'
    * def students = read('classpath:students.json')

  Scenario: Create a student
    Given request students[0]
    When method post
    Then status 200
    * print response

  Scenario: Get student by id
    Given request students[1]
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

