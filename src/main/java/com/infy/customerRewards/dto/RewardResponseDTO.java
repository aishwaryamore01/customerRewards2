package com.infy.customerRewards.dto;

import java.util.List;
import java.util.Map;

import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * Data Transfer Object for customer reward response.
 * Contains comprehensive reward information including structured monthly breakdown,
 * transaction details, and reward calculations for a specific time period.
 * @author Infy
 * @version 1.0
 * @since 2024
 */
public class RewardResponseDTO {
    
    private Long customerId;
    private String custName;
    private String phoneNo;
    private List<TransactionDTO> transactions;
    private List<MonthlyRewardDTO> monthlyRewards;
    private Integer totalRewards;
    private Map<String, String> timeFrame;

    /**
     * Default constructor.
     */
    public RewardResponseDTO() {
        // Default constructor
    }

    /**
     * Builder pattern constructor for creating RewardResponseDTO instances.
     * @param builder the builder containing all the field values
     */
    private RewardResponseDTO(Builder builder) {
        this.customerId = builder.customerId;
        this.custName = builder.custName;
        this.phoneNo = builder.phoneNo;
        this.transactions = builder.transactions;
        this.monthlyRewards = builder.monthlyRewards;
        this.totalRewards = builder.totalRewards;
        this.timeFrame = builder.timeFrame;
    }

    // GETTER METHODS

    /**
     * Gets the unique identifier of the customer.
     * @return the customer ID
     */
    public Long getCustomerId() { 
        return customerId; 
    }

    /**
     * Gets the name of the customer.
     * @return the customer name
     */
    public String getCustName() { 
        return custName; 
    }

    /**
     * Gets the phone number of the customer.
     * @return the customer phone number
     */
    public String getPhoneNo() { 
        return phoneNo; 
    }

    /**
     * Gets the list of transactions for the customer within the specified time frame.
     * @return list of transaction DTOs
     */
    public List<TransactionDTO> getTransactions() { 
        return transactions; 
    }

    /**
     * Gets the structured monthly rewards breakdown.
     * @return list of monthly reward DTOs with year, month name, and points
     */
    public List<MonthlyRewardDTO> getMonthlyRewards() { 
        return monthlyRewards; 
    }

    /**
     * Gets the total reward points earned across all transactions in the time frame.
     * @return total reward points
     */
    public Integer getTotalRewards() { 
        return totalRewards; 
    }

    /**
     * Gets the time frame for which rewards were calculated.
     * @return map containing startDate and endDate as strings
     */
    public Map<String, String> getTimeFrame() { 
        return timeFrame; 
    }

    // SETTER METHODS

    /**
     * Sets the unique identifier of the customer.
     * @param customerId the customer ID to set
     */
    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    /**
     * Sets the name of the customer.
     * @param custName the customer name to set
     */
    public void setCustName(String custName) {
        this.custName = custName;
    }

    /**
     * Sets the phone number of the customer.
     * @param phoneNo the customer phone number to set
     */
    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    /**
     * Sets the list of transactions for the customer.
     * 
     * @param transactions the list of transaction DTOs to set
     */
    public void setTransactions(List<TransactionDTO> transactions) {
        this.transactions = transactions;
    }

    /**
     * Sets the structured monthly rewards breakdown.
     * @param monthlyRewards the list of monthly reward DTOs to set
     */
    public void setMonthlyRewards(List<MonthlyRewardDTO> monthlyRewards) {
        this.monthlyRewards = monthlyRewards;
    }

    /**
     * Sets the total reward points earned.
     * @param totalRewards the total reward points to set
     */
    public void setTotalRewards(Integer totalRewards) {
        this.totalRewards = totalRewards;
    }

    /**
     * Sets the time frame for reward calculation.
     * @param timeFrame the map containing startDate and endDate as strings
     */
    public void setTimeFrame(Map<String, String> timeFrame) {
        this.timeFrame = timeFrame;
    }

    /**
     * Builder class for constructing RewardResponseDTO instances using the builder pattern.
     * Provides a fluent interface for setting properties and building the DTO.
     */
    public static class Builder {
        
        private Long customerId;
        private String custName;
        private String phoneNo;
        private List<TransactionDTO> transactions;
        private List<MonthlyRewardDTO> monthlyRewards;
        private Integer totalRewards;
        private Map<String, String> timeFrame;

        /**
         * Sets the customer ID.
         * @param customerId the customer ID
         * @return the builder instance for method chaining
         */
        public Builder customerId(Long customerId) {
            this.customerId = customerId;
            return this;
        }

        /**
         * Sets the customer name.
         * 
         * @param custName the customer name
         * @return the builder instance for method chaining
         */
        public Builder custName(String custName) {
            this.custName = custName;
            return this;
        }

        /**
         * Sets the customer phone number.
         * @param phoneNo the customer phone number
         * @return the builder instance for method chaining
         */
        public Builder phoneNo(String phoneNo) {
            this.phoneNo = phoneNo;
            return this;
        }

        /**
         * Sets the list of transactions.
         * @param transactions the list of transaction DTOs
         * @return the builder instance for method chaining
         */
        public Builder transactions(List<TransactionDTO> transactions) {
            this.transactions = transactions;
            return this;
        }

        /**
         * Sets the structured monthly rewards.
         * 
         * @param monthlyRewards the list of monthly reward DTOs
         * @return the builder instance for method chaining
         */
        public Builder monthlyRewards(List<MonthlyRewardDTO> monthlyRewards) {
            this.monthlyRewards = monthlyRewards;
            return this;
        }

        /**
         * Sets the total reward points.
         * @param totalRewards the total reward points
         * @return the builder instance for method chaining
         */
        public Builder totalRewards(Integer totalRewards) {
            this.totalRewards = totalRewards;
            return this;
        }

        /**
         * Sets the time frame for reward calculation.
         * @param timeFrame the map containing startDate and endDate
         * @return the builder instance for method chaining
         */
        public Builder timeFrame(Map<String, String> timeFrame) {
            this.timeFrame = timeFrame;
            return this;
        }

        /**
         * Builds and returns the RewardResponseDTO instance with the configured properties.
         * @return the fully constructed RewardResponseDTO
         */
        public RewardResponseDTO build() {
            return new RewardResponseDTO(this);
        }
    }

    /**
     * Creates a new builder instance for constructing RewardResponseDTO.
     * @return a new Builder instance
     */
    public static Builder builder() {
        return new Builder();
    }

    /**
     * Default setMonthlyRewards to write test cases
     */
	public void setMonthlyRewards(Map<String, Integer> of) {
		// TODO Auto-generated method stub
		
	}
}