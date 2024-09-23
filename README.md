# BankingApplication-project

### Project Overview
This repository contains the code for a banking application, developed as part of DePaul University's SE452 course. The project addresses various functionalities typically found in banking systems, and it is structured into different modules, each managed by a team member.

**Milestone 1:** Define the project, its structure, and assign areas of responsibility to each group member.

---

## Project Members and Responsibilities

| **Member**         | **Responsibilities**                                           |
|--------------------|----------------------------------------------------------------|
| Mohammad           | Account Management                                             |
| Noor               | Loan Management                                                |
| Narasimha Reddy    | Transaction Management, Notifications Management & Admin Panel |
| Shashank Datt      | Authentication & Authorization                                 |

---

## Conflict Resolution

In the event of conflicts or disagreements regarding technical or project-related decisions, the team will engage in a professional and collaborative discussion. All team members will present their perspectives, and the group will work toward a consensus. If no agreement is reached after thorough discussion, a decision will be made based on majority vote. This process ensures that all voices are heard, and decisions are made in the best interest of the project.

---

## Communication Channels

The team will communicate and collaborate using the following platforms:

- **Discord** for discussions and real-time communication.
- **Zoom** for virtual meetings and presentations.
- **GitHub** for code collaboration and version control.

---

## Key Technical Decisions

| **#** | **Area**                  | **Decision**        | **Alternative(s)**     | **Rationale**                                            |
|-------|---------------------------|---------------------|------------------------|----------------------------------------------------------|
| 1     | Integrated Development Environment (IDE) |  IntelliJ IDEA | VS Code or Eclipse     | IntelliJ IDEA was chosen as the primary IDE for this project due to its strong integration with Java technologies, especially Spring Boot, which is extensively used in banking applications. IntelliJ provides advanced refactoring, debugging, and testing tools that enhance productivity and code quality. Additionally, its built-in support for Maven and Lombok allows the team to work seamlessly with modern Java frameworks and libraries, making it the most suitable option. |
| 2     | Dependency Management      | Maven               | Gradle                 | Maven was selected for its widespread use in enterprise Java projects and its simplicity in managing project dependencies. It is well-supported by most Java IDEs, including IntelliJ IDEA, and provides a straightforward, declarative way to handle project builds, making it easier for the team to manage complex dependencies and plugins without needing to manually configure builds. |
| 3     | Code Template              | Lombok              | Standard Templates     | Lombok significantly reduces boilerplate code, which is common in Java applications. By automating repetitive tasks such as generating getters, setters, constructors, and other utility methods, Lombok improves code readability and maintainability. This enables the team to focus on implementing business logic without being bogged down by redundant code. |
| 4     | Configuration Management   | JSON                | YAML, Properties Files | JSON was selected for configuration management because it is human-readable, lightweight, and familiar to the team. It integrates well with various Java libraries and frameworks. JSON’s straightforward format ensures that configuration files remain easy to read and modify, making it ideal for handling configurations in the development environment. Additionally, JSON's widespread use in API development aligns with the needs of the project. |

---

## Coding Standards

The following coding standards have been adopted to ensure consistency, readability, and maintainability of the codebase:

1. **Java Naming Conventions**:
    - Classes and interfaces use CamelCase (e.g., `AccountService`).
    - Variables and methods use lowerCamelCase (e.g., `getAccountDetails`).
    - Constants are written in UPPERCASE with underscores separating words (e.g., `MAX_TRANSACTION_LIMIT`).

2. **Indentation and Formatting**:
    - Use 4 spaces for indentation (no tabs).
    - Properly format code to ensure readability, including consistent use of whitespace around operators and parentheses.

3. **Commenting and Documentation**:
    - All public methods must include Javadoc comments explaining their purpose and usage.
    - Inline comments should be used sparingly and only when the code’s purpose is not immediately obvious.

4. **Exception Handling**:
    - Use custom exception classes where applicable to handle business logic errors.
    - Avoid generic exception catches such as `catch (Exception e)` unless absolutely necessary.

5. **Unit Testing**:
    - All methods must be covered by unit tests using JUnit.
    - Test classes follow the same package structure as the main codebase (e.g., `src/test/java/...`).
    - Tests should follow the Arrange-Act-Assert (AAA) pattern to ensure clarity.

6. **Code Reviews**:
    - All pull requests must be reviewed by at least one team member before merging.
    - Code reviews focus on design, readability, maintainability, and adherence to coding standards.
