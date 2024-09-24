/**
 * This package is responsible for managing all aspects of transaction-related operations 
 * within the banking application. It includes functionalities such as transferring funds, 
 * handling deposits and withdrawals, and maintaining transaction records.
 *
 * The {@code Transaction} class represents a financial transaction and stores critical 
 * information, including the transaction ID, involved accounts, amount, type, date, and 
 * description.
 *
 * Several Lombok annotations like {@code @NoArgsConstructor}, {@code @AllArgsConstructor}, 
 * and {@code @Builder} are used to reduce boilerplate code, making it easier to manage 
 * and build instances of the {@code Transaction} class.
 *
 * Key components:
 * - {@code TransactionType}: Enum to differentiate between transfer, deposit, and withdrawal types.
 *
 * This package simplifies the process of handling different types of transactions, 
 * offering a clean and maintainable structure.
 *
 * @author Narasimha Reddy
 */
package com.example.Banking.application.Transaction_Management;
