# PriceCalculator
This is a recruitment task written in SpringBoot 3.x. Postgresql is used as a data storage. 
Project exposes some REST endpoints that can be used to manipulate the data and `/api/v1/products/price` endpoint 
that exposes main functionality from the task. It accepts `productId` and `amount` and returns final price after applying discounts

## Running the app
For development purposes application can be run using `src/test/java/com/challenge/discountcalculator/TestDiscountCalculatorApplication.java`
file. It adds devcontainers to the project and is the only way to run this application. After starting the app `requests.http` 
file can be used to play around with the endpoints. During startup `src/test/resources/data.sql` file is used to seed the database. 
The same file is used during integration tests.

## Testing
To narrow down the amount of test only integration tests were implemented. They can be found in `src/test/java/com/challenge/discountcalculator/DiscountCalculatorIntegrationTests.java`
file. In normal situation also unit tests should be implemented but considering the simplicity of the logic only integration tests were implemented.

## Assumptions
Key assumptions:
1. Discount is associated with single product
1. Product can have multiple discounts. In that case discount applicable for the highest possible products count is applied
1. It is assumed that discount will never reach more than products total value. There is no safety guards for that in the code as it was not specified in the requirements
1. REST endpoints were added to cover "configurable" part of the discounts requirements
1. Project uses hexagonal architecture
1. Exception handling is simplified
1. Error responses are following RFC 9457 specification

