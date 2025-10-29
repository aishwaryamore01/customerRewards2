package com.infy.customerRewards.utility;

import com.infy.customerRewards.dto.TransactionDTO;
import com.infy.customerRewards.entity.Transaction;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.time.YearMonth;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Component responsible for calculating reward points based on transaction amounts.
 * Implements business logic for reward point calculation and provides monthly breakdown.
 * 
 * @author Infy
 * @version 1.0
 * @since 2024
 */
@Component
public class RewardCalculator {

    private final ModelMapper modelMapper;

    /**
     * Constructs a RewardCalculator with the specified ModelMapper.
     * 
     * @param modelMapper the ModelMapper instance used for entity-DTO conversion
     */
    public RewardCalculator(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    /**
     * Calculates reward points for a list of transactions and provides monthly breakdown.
     * Processes each transaction to calculate points, aggregates monthly totals, and converts
     * transactions to DTOs with their respective reward points.
     * 
     * @param transactions the list of transactions to calculate rewards for
     * @return RewardCalculationResult containing transaction DTOs, monthly rewards, and total rewards
     * @throws IllegalArgumentException if transactions list is null
     */
    public RewardCalculationResult calculateRewards(List<Transaction> transactions) {
        Map<String, Integer> monthlyRewards = new HashMap<>();
        List<TransactionDTO> transactionDTOs = new ArrayList<>();
        int totalRewards = 0;

        for (Transaction transaction : transactions) {
            int points = calculatePoints(transaction.getAmount());
            totalRewards += points;

            String monthKey = YearMonth.from(transaction.getDate()).toString();
            monthlyRewards.put(monthKey, monthlyRewards.getOrDefault(monthKey, 0) + points);

            TransactionDTO dto = modelMapper.map(transaction, TransactionDTO.class);
            dto.setRewardPoints(points);
            transactionDTOs.add(dto);
        }

        return new RewardCalculationResult(transactionDTOs, monthlyRewards, totalRewards);
    }

    /**
     * Calculates reward points for a single transaction amount based on the reward rules:
     * - 0 points for amounts $50 and under
     * - 1 point per dollar spent over $50 up to $100
     * - 2 points per dollar spent over $100 (plus 50 points for the $51-100 range)
     * 
     * @param amount the transaction amount
     * @return the calculated reward points for the transaction
     * @throws IllegalArgumentException if amount is negative
     */
    public int calculatePoints(double amount) {
        if (amount <= 50) return 0;
        if (amount <= 100) return (int) (amount - 50);
        return (int) ((amount - 100) * 2 + 50);
    }

    /**
     * Record representing the result of reward calculations.
     * Contains transaction DTOs with calculated points, monthly reward breakdown, and total rewards.
     * 
     * @param transactionDTOs list of transaction DTOs with calculated reward points
     * @param monthlyRewards map of monthly rewards where key is "YYYY-MM" and value is monthly points
     * @param totalRewards the total reward points across all transactions
     */
    public record RewardCalculationResult(
        List<TransactionDTO> transactionDTOs,
        Map<String, Integer> monthlyRewards,
        int totalRewards
    ) {}
}