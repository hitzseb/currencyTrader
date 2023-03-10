# currencyTrader
This is a Spring application that includes both a RESTful API and an administrator interface. The API provides currency conversion and exchange rate variation calculation functionality, while the administrator interface allows for the manipulation of the data used by the API.

## Getting Started
To get started with the application, follow these steps:

Clone the repository to your local machine.
Open the project in your IDE of choice.
Build and run the project.

## Running the Application with Maven
To run the application using Maven, navigate to the root directory of the project and run the following command:
```
mvn spring-boot:run
```
This will compile the code, package it into an executable JAR file, and run the application. You can then access the API and the administrator interface by visiting http://localhost:8080 in your web browser.

Note that you must have Maven installed on your machine in order to run this command. You can download Maven from the official website:
[https://maven.apache.org/download.cgi](https://maven.apache.org/download.cgi)

If you encounter any issues while running the application with Maven, try running the following command to clean the project:
```
mvn clean
```
This will remove any previously compiled files and dependencies, and should help to resolve any errors you may be experiencing.

## RESTful API
The API provides the following endpoints:

**/exchange**: This endpoint accepts the following parameters: an operation type, which can be either PURCHASE or SALE; a market code; a currency code for the currency being exchanged (currencyFrom); a currency code for the currency being received in the exchange (currencyTo); and an amount. The endpoint returns the converted currency and additional relevant data based on these parameters.  
Example: [http://localhost:8080/api/v1/exchange?operation=SALE&market=ARG&from=ARS&to=USD&amount=38000](http://localhost:8080/api/v1/exchange?operation=SALE&market=ARG&from=ARS&to=USD&amount=38000)  
**/variation**: This endpoint accepts a currency code, a market code, and a date. It then returns the exchange rate variation for that currency in that market on the specified date, based on the values registered in the database. The returned value includes the historical records for that currency and market up to the specified date.  
Example: [http://localhost:8080/api/v1/variation?currency=USD&market=ARG&date=2020-01-01](http://localhost:8080/api/v1/variation?currency=USD&market=ARG&date=2020-01-01)

## Administrator Interface
The administrator interface provides the following functionality:

List all currencies, markets or currency values.  
Add new currencies, markets, and currency values to the system.  
Edit and delete any currency, market or currency value.    

For accessing it you need to be authenticated. For that you can log in as 'user' or 'admin'. Each one has its corresponding authority. The password is 'trader' in both cases.

## Database Setup
This Spring application uses H2 database to store and manage data. The database is initialized using the schema.sql and data.sql files located in the /src/main/resources directory.

### schema.sql
The schema.sql file contains the SQL statements that create the database tables and define their structure. It creates the following tables:

currency: stores information about currencies, including name, code, and symbol.  
market: stores information about markets, including name, code and main currency.  
currency_value: stores information about the purchase and sale value of currencies in different markets on different dates.
users: stores information about the users, including a username, an encrypted password and a role.

### data.sql
The data.sql file contains the SQL statements that insert data into the database tables. It inserts initial data into the currency and market tables, as well as some sample data into the currency_values table. It also inserts a couple of users into the users table.

To run the application with the H2 database, simply build and run the project as described in the Getting Started section. When the application is started, Spring Boot will automatically execute the schema.sql and data.sql scripts to initialize the database.

If you need to make changes to the database data, you can modify the data.sql file accordingly. When you restart the application, Spring Boot will re-execute the scripts to update the database.

## Technologies Used
The application was built using the following technologies:

- Spring Boot
- Spring Data JPA
- Spring Security
- H2 Database
- Thymeleaf
- Bootstrap
- Ionicons
- OpenAPI
- Lombok
