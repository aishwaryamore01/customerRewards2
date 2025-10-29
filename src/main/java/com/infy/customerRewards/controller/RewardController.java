package com.infy.customerRewards.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.infy.customerRewards.dto.CustomerDTO;
import com.infy.customerRewards.dto.CustomerResponseDTO;
import com.infy.customerRewards.dto.RewardResponseDTO;
import com.infy.customerRewards.dto.TransactionDTO;
import com.infy.customerRewards.service.RewardService;

import java.time.LocalDate; import java.util.List;

/**
 * REST Controller for handling customer rewards and transactions.
 * Provides endpoints for customer management, transaction retrieval, and reward calculations.
 * 
 * @author Infy
 * @version 1.0
 * @since 2024
 */
@RestController
@RequestMapping("/api/rewards")
public class RewardController {
    
    @Autowired
    private RewardService rewardService;
    
    /**
     * Creates a new customer with their associated transactions.
     * 
     * @param customerDTO the customer data transfer object containing customer details and transactions
     * @return ResponseEntity containing the created customer response with generated ID and rewards summary
     * @throws IllegalArgumentException if customer data is invalid
     */
    @PostMapping("/customers")
    public ResponseEntity<CustomerResponseDTO> createCustomer(@RequestBody CustomerDTO customerDTO) {
        CustomerResponseDTO response = rewardService.createCustomer(customerDTO);
        return ResponseEntity.ok(response);
    }
    
    /**
     * Retrieves all transactions for a specific customer.
     * 
     * @param customerId the unique identifier of the customer
     * @return ResponseEntity containing a list of transaction DTOs for the specified customer
     * @throws ResourceNotFoundException if no customer is found with the given ID
     */
    @GetMapping("/customers/{customerId}/transactions")
    public ResponseEntity<List<TransactionDTO>> getCustomerTransactions(@PathVariable Long customerId) {
        return ResponseEntity.ok(rewardService.getCustomerTransactions(customerId));
    }
    
    /**
     * Calculates and retrieves rewards for a customer within a specified date range.
     * The rewards are calculated based on transactions that fall within the given timeframe.
     * 
     * @param customerId the unique identifier of the customer
     * @param startDate the start date of the period for reward calculation (inclusive)
     * @param endDate the end date of the period for reward calculation (inclusive)
     * @return ResponseEntity containing the reward response with total points and monthly breakdown
     * @throws IllegalArgumentException if dates are invalid or startDate is after endDate
     * @throws ResourceNotFoundException if no customer is found with the given ID
     */
    @GetMapping("/customers/{customerId}/rewards")
    public ResponseEntity<RewardResponseDTO> getRewardsForCustomer(
            @PathVariable Long customerId,
            @RequestParam("startDate") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate startDate,
            @RequestParam("endDate") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate endDate) {
        return ResponseEntity.ok(rewardService.getRewardsForCustomer(customerId, startDate, endDate));
    }
}
