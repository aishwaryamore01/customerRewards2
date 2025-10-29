package com.infy.customerRewards.serviceImpl;


import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.infy.customerRewards.dto.CustomerDTO;
import com.infy.customerRewards.dto.CustomerResponseDTO;
import com.infy.customerRewards.dto.RewardResponseDTO;
import com.infy.customerRewards.dto.TransactionDTO;
import com.infy.customerRewards.entity.Customer;
import com.infy.customerRewards.entity.Transaction;
import com.infy.customerRewards.repository.CustomerRepository;
import com.infy.customerRewards.repository.TransactionRepository;
import com.infy.customerRewards.service.RewardService;
import com.infy.customerRewards.utility.RewardCalculator;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.infy.customerRewards.dto.CustomerDTO;
import com.infy.customerRewards.dto.CustomerResponseDTO;
import com.infy.customerRewards.dto.MonthlyRewardDTO;
import com.infy.customerRewards.dto.RewardResponseDTO;
import com.infy.customerRewards.dto.TransactionDTO;
import com.infy.customerRewards.entity.Customer;
import com.infy.customerRewards.entity.Transaction;
import com.infy.customerRewards.repository.CustomerRepository;
import com.infy.customerRewards.repository.TransactionRepository;
import com.infy.customerRewards.service.RewardService;
import com.infy.customerRewards.utility.RewardCalculator;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Service implementation for customer rewards management.
 * Handles business logic for customer creation, transaction retrieval, and reward calculations.
 * 
 * @author Infy
 * @version 1.0
 * @since 2024
 */
@Service
public class RewardServiceImpl implements RewardService {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private ModelMapper mapper;

    @Autowired
    private Environment env;
    
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private RewardCalculator rewardCalculator;

    /**
     * Creates a new customer with associated transactions and encodes sensitive information.
     * Establishes bidirectional relationship between customer and transactions.
     * 
     * @param customerDTO the customer data transfer object containing customer details and transactions
     * @return CustomerResponseDTO with saved customer information and generated ID
     * @throws RuntimeException if customer data validation fails or persistence error occurs
     */
    @Override
    public CustomerResponseDTO createCustomer(CustomerDTO customerDTO) {
        Customer customer = mapper.map(customerDTO, Customer.class);

        if (customer.getTransactions() != null) {
            customer.getTransactions().forEach(tx -> tx.setCustomer(customer));
        }
        customer.setPhoneNo(passwordEncoder.encode(customer.getPhoneNo()));
        Customer savedCustomer = customerRepository.save(customer);
        return mapper.map(savedCustomer, CustomerResponseDTO.class);
    }
    
    /**
     * Retrieves all transactions for a specific customer and calculates reward points for each transaction.
     * 
     * @param customerId the unique identifier of the customer
     * @return List of TransactionDTO objects with calculated reward points
     * @throws RuntimeException if customer is not found or data access error occurs
     */
    @Override
    public List<TransactionDTO> getCustomerTransactions(Long customerId) {
        List<Transaction> transactions = transactionRepository.findByCustomerId(customerId);

        return transactions.stream()
                .map(tx -> {
                    TransactionDTO dto = mapper.map(tx, TransactionDTO.class);
                    dto.setRewardPoints(rewardCalculator.calculatePoints(tx.getAmount()));
                    return dto;
                })
                .collect(Collectors.toList());
    }

    /**
     * Calculates rewards for a customer within a specified date range.
     * Validates customer existence and transaction availability before reward calculation.
     * 
     * @param customerId the unique identifier of the customer
     * @param startDate the start date of the reward calculation period (inclusive)
     * @param endDate the end date of the reward calculation period (inclusive)
     * @return RewardResponseDTO containing total rewards, monthly breakdown, and transaction details
     * @throws RuntimeException if customer not found, no transactions in date range, or calculation error
     */
    @Override
    public RewardResponseDTO getRewardsForCustomer(Long customerId, LocalDate startDate, LocalDate endDate) {
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new RuntimeException(
                        String.format("%s %d", env.getProperty("customer.notfound", "Customer not found:"), customerId)
                ));

        List<Transaction> transactions = transactionRepository
                .findByCustomerIdAndDateBetween(customerId, startDate, endDate);

        if (transactions.isEmpty()) {
            throw new RuntimeException(env.getProperty("transaction.notfound", "No transactions found"));
        }

        return buildRewardResponse(customer, transactions, startDate, endDate);
    }

    /**
     * Builds the reward response DTO by calculating rewards and organizing response data.
     * Transforms monthly rewards from simple key-value pairs to structured objects.
     * 
     * @param customer the customer entity for which rewards are calculated
     * @param transactions list of transactions within the specified date range
     * @param startDate the start date of the reward period
     * @param endDate the end date of the reward period
     * @return RewardResponseDTO containing structured reward information and time frame details
     */
    private RewardResponseDTO buildRewardResponse(Customer customer, List<Transaction> transactions, 
                                                 LocalDate startDate, LocalDate endDate) {
        RewardCalculator.RewardCalculationResult calculationResult = rewardCalculator.calculateRewards(transactions);
        
        // Transform the monthly rewards to structured format
        List<MonthlyRewardDTO> structuredMonthlyRewards = transformMonthlyRewards(calculationResult.monthlyRewards());
        
        Map<String, String> timeFrame = new HashMap<>();
        timeFrame.put("startDate", startDate.toString());
        timeFrame.put("endDate", endDate.toString());

        return RewardResponseDTO.builder()
                .customerId(customer.getId())
                .custName(customer.getCustName())
                .phoneNo(customer.getPhoneNo())
                .transactions(calculationResult.transactionDTOs())
                .monthlyRewards(structuredMonthlyRewards) // Use the transformed structured data
                .totalRewards(calculationResult.totalRewards())
                .timeFrame(timeFrame)
                .build();
    }

    /**
     * Transforms monthly rewards from Map<String, Integer> format to structured MonthlyRewardDTO objects.
     * Converts keys like "2025-07" to objects with year, month name, and points.
     * 
     * @param monthlyRewardsMap the original monthly rewards map with format {"YYYY-MM": points}
     * @return List of MonthlyRewardDTO objects with structured data
     */
    private List<MonthlyRewardDTO> transformMonthlyRewards(Map<String, Integer> monthlyRewardsMap) {
        return monthlyRewardsMap.entrySet().stream()
                .map(entry -> {
                    String[] yearMonthParts = entry.getKey().split("-");
                    int year = Integer.parseInt(yearMonthParts[0]);
                    int monthValue = Integer.parseInt(yearMonthParts[1]);
                    return MonthlyRewardDTO.builder()
                            .year(year)
                            .month(getMonthName(monthValue))
                            .points(entry.getValue())
                            .build();
                })
                .collect(Collectors.toList());
    }

    /**
     * Converts month number (1-12) to full month name.
     * 
     * @param monthValue the month number (1 for January, 12 for December)
     * @return the full month name as a string
     * @throws IllegalArgumentException if monthValue is not between 1 and 12
     */
    private String getMonthName(int monthValue) {
        switch (monthValue) {
            case 1: return "January";
            case 2: return "February";
            case 3: return "March";
            case 4: return "April";
            case 5: return "May";
            case 6: return "June";
            case 7: return "July";
            case 8: return "August";
            case 9: return "September";
            case 10: return "October";
            case 11: return "November";
            case 12: return "December";
            default: throw new IllegalArgumentException("Invalid month value: " + monthValue);
        }
    }
}