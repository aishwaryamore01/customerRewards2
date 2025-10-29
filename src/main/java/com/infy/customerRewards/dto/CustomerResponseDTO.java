package com.infy.customerRewards.dto;

import java.util.List;

import com.infy.customerRewards.dto.TransactionDTO.TransactionDTOBuilder;

import lombok.Data;

@Data
public class CustomerResponseDTO {
	private Long id;
    private String custName;
    private String phoneNo;
    private List<TransactionResponseDTO> transactions;
    
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getCustName() {
		return custName;
	}
	public void setCustName(String custName) {
		this.custName = custName;
	}
	public String getPhoneNo() {
		return phoneNo;
	}
	public void setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
	}
	public List<TransactionResponseDTO> getTransactions() {
		return transactions;
	}
	public void setTransactions(List<TransactionResponseDTO> transactions) {
		this.transactions = transactions;
	}
    
	/**
     * Creates a new builder instance for constructing CustomerResponseDTO objects.
     * 
     * @return a new Builder instance
     */
    public static Builder builder() {
        return new Builder();
    }

    /**
     * Builder class for constructing CustomerResponseDTO instances using the builder pattern.
     * Provides a fluent interface for setting properties and building the DTO.
     */
    public static class Builder {
        private Long id;
        private String custName;
        private String phoneNo;
        private List<TransactionResponseDTO> transactions;

        /**
         * Sets the customer ID.
         * 
         * @param id the customer ID
         * @return the builder instance for method chaining
         */
        public Builder id(Long id) {
            this.id = id;
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
         * 
         * @param phoneNo the customer phone number
         * @return the builder instance for method chaining
         */
        public Builder phoneNo(String phoneNo) {
            this.phoneNo = phoneNo;
            return this;
        }

        /**
         * Sets the list of transactions for the customer.
         * 
         * @param transactions the list of transaction response DTOs
         * @return the builder instance for method chaining
         */
        public Builder transactions(List<TransactionResponseDTO> transactions) {
            this.transactions = transactions;
            return this;
        }

        /**
         * Builds and returns the CustomerResponseDTO instance with the configured properties.
         * 
         * @return the fully constructed CustomerResponseDTO
         */
        public CustomerResponseDTO build() {
            return new CustomerResponseDTO();
        }
    }

    @Override
    public String toString() {
        return "CustomerResponseDTO{" +
                "id=" + id +
                ", custName='" + custName + '\'' +
                ", phoneNo='" + phoneNo + '\'' +
                ", transactions=" + transactions +
                '}';
    }
}
	
    

