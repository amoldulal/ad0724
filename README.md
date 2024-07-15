# Tool Rental Service

This project is a simple tool rental service application written in Java. It allows users to rent tools, calculate rental charges, apply discounts, and generate rental agreements.

## Project Structure

The project consists of the following packages and classes:

- **com.company**: Contains the main entry point of the application.
  - `Main`: The main class that handles user input and interacts with the rental service.

- **com.company.service**: Contains the service classes for handling rental operations.
  - `RentalService`: Handles the business logic for tool rentals.
  - `ToolService`: Manages tool data.

- **com.company.model**: Contains the data models.
  - `RentalAgreement`: Represents the rental agreement.
  - `Tool`: Represents a tool available for rent.
  - `ToolType`: Enum representing types of tools.

- **com.company.util**: Contains utility classes.
  - `DateUtil`: Provides utility methods for date-related operations.

- **com.company.exception**: Contains custom exceptions for validation.
  - `InvalidRentalDaysException`: Thrown when rental days are invalid.
  - `InvalidDiscountException`: Thrown when discount percent is invalid.
  - `InvalidToolCodeException`: Thrown when tool code is invalid.

## How to Run

1. **Compile the project**:
- After compiling the files, run the Main class.
- Follow the prompts to complete the rental process. The application will prompt you to enter the tool code, rental days, discount percent, and checkout date.

