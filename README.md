
# Online Food Order System

## Project Description
A Spring Boot REST API project for managing food orders using in-memory storage.

## Technologies Used
- Java 27
- Spring Boot
- Maven
- Spring Web
- Jakarta Bean Validation
- Lombok
- Postman

## Features
- Add a food order
- View all orders
- Find an order by ID
- Update an order using PUT
- Update quantity using PATCH
- Delete an order
- Search food by partial or exact name
- Filter orders by price
- Sort orders by price
- View available orders
- Compare prices
- Validate request data
- Handle exceptions

## Run the Project
1. Import the project into Spring Tool Suite.
2. Wait for Maven dependencies to download.
3. Run `OnlineFoodOrderApplication.java`.
4. The application starts on port 8080.

## Base URL
`http://localhost:8080/api/orders`

## Storage
Orders are stored in an in-memory list. Data is not persisted after an application restart.
