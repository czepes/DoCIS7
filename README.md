# DoCIS Lab #7

Development of Corporate Information Systems Laboratory #7 (based on lab #6).
Tasks 1-5 were implemented in the project.

## Install

### ActiveMQ Artemis

Install and run `ActiveMQ Artemis 2.27.0` server.

Configure `Broker URL` and `Queue` in `jms.properties` [(link)](src/main/resources/jms.properties).

Links:
- [Download](https://activemq.apache.org/components/artemis/download/)
- [Using the Server](https://activemq.apache.org/components/artemis/documentation/latest/using-server.html)


### Database

Run `init.sql` to create table and fill it with test values.

Configure Database connection in `data.properties` [(link)](src/main/resources/data.properties).

### Server

Create a local server configuration of `Apache Tomcat 10` server and deploy it.

### REST Client

At `ru.sfu.rest.RestClient.java` [(link)](src/main/java/ru/sfu/rest/RestClient.java),
set the `URL` variable to the URL of the configured server and run `RestClient.java`.

## Dependencies

Project is built with Maven on `maven-archetype-webapp`.

List of used [dependencies](pom.xml):
- Spring 6.0.0
    - Core
    - Beans
    - Web
    - Web MVC
    - JDBC
    - JMS
- Spring Data JPA 3.0.0
- ActiveMQ Artemis 2.27.0
  - Jakarta Client 
  - Spring Integration
- Thymeleaf 3.1.0.RELEASE
    - Thymeleaf Spring 6
- Jakarta
    - Persistence API 3.1.0
    - Servlet API 6.0.0
    - Annotation API 2.1.1
    - Validation API 3.0.2
    - JMS API 3.1.0
- Hibernate
    - Core 6.1.5.Final
    - Validator 8.0.0.Final
- PostgreSQL 42.5.0
- Jackson Databind 2.14.0

## Spring JMS
### Purpose of work
Become familiar with the mechanisms of JMS in Spring.
### General formulation
Modify the application from practical assignment #6, #5, or #4 (at the student's discretion) 
and add the following features (snowflake tasks are desirable, but not required):

1. Set up a queue (for ActiveMQ or any other JMS message broker) to receive messages for the administrator.
2. When performing operations of adding, removing or editing a resource via REST API / form, 
   create appropriate notifications and send them to the queue.
3. In any convenient way (you can use the console) to demonstrate the extraction of 
   administrative messages about the performed operations (from step 2).
4. Add a "buy" link button to the form. This sends a message to the message broker 
   about what entity the user wants to buy.
5. In step 4 the entity is marked as bought and will not be shown in the general list of entities. 
   You need to add the appropriate column, or just delete the record of the purchased entity from the database, 
   but before that do not forget to send information about the entity to the message broker.
6. Implement a message-receiving service application that receives a message and, 
   based on the contents of the message, sends an e-mail to an administrator at some address 
   (you can use the constant string of your mailbox in the SFU domain) that he wants to buy an entity.

Note: Sending e-mail using Spring is a self-study, there will be no questions at the defense on this topic. 
The mechanism of sending e-mail - any at the choice of the student.

Translated with www.DeepL.com/Translator (free version)
