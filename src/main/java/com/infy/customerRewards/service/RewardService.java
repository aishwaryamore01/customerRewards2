package com.infy.customerRewards.service;


import com.infy.customerRewards.dto.CustomerDTO;
import com.infy.customerRewards.dto.CustomerResponseDTO;
import com.infy.customerRewards.dto.RewardResponseDTO;
import com.infy.customerRewards.dto.TransactionDTO;

import java.time.LocalDate;
import java.util.List;

/**
 * Service interface for managing customer rewards and transactions.
 * Defines contract for customer creation, transaction retrieval, and reward calculation operations.
 * 
 * @author Infy
 * @version 1.0
 * @since 2024
 */
public interface RewardService {
    
    /**
     * Creates a new customer with associated transactions and calculates initial rewards.
     * 
     * @param customerDTO the customer data transfer object containing customer details 
     *                   and their transaction information
     * @return CustomerResponseDTO containing customer ID, transaction details, 
     *         and calculated rewards summary
     * @throws IllegalArgumentException if customerDTO is null, contains invalid data,
     *                                  or transactions are invalid
     */
    CustomerResponseDTO createCustomer(CustomerDTO customerDTO);
    
    /**
     * Retrieves all transactions associated with a specific customer.
     * 
     * @param customerId the unique identifier of the customer whose transactions are to be retrieved
     * @return List of TransactionDTO objects representing all transactions for the customer
     * @throws ResourceNotFoundException if no customer exists with the provided customerId
     */
    List<TransactionDTO> getCustomerTransactions(Long customerId);
    
    /**
     * Calculates and retrieves reward points for a customer within a specified date range.
     * Rewards are calculated based on transactions that occur between the start and end dates (inclusive).
     * 
     * @param customerId the unique identifier of the customer for whom rewards are calculated
     * @param startDate the start date of the reward calculation period (inclusive)
     * @param endDate the end date of the reward calculation period (inclusive)
     * @return RewardResponseDTO containing total reward points and optional monthly breakdown
     * @throws IllegalArgumentException if customerId is null, startDate is after endDate,
     *                                  or dates are null
     * @throws ResourceNotFoundException if no customer exists with the provided customerId
     */
    RewardResponseDTO getRewardsForCustomer(Long customerId, LocalDate startDate, LocalDate endDate);
}