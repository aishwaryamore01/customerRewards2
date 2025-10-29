package com.infy.customerRewards.dto;

import java.util.List;

import com.infy.customerRewards.dto.RewardResponseDTO.Builder;

import lombok.Data;

@Data
public class CustomerDTO {
	    private String custName;
	    private String phoneNo;
	    private List<TransactionDTO> transactions;
	    
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
		public List<TransactionDTO> getTransactions() {
			return transactions;
		}
		public void setTransactions(List<TransactionDTO> transactions) {
			this.transactions = transactions;
		}
		
		 public static Builder builder() {
		        return new Builder();
		    }

		    /**
		     * Builder class for constructing CustomerDTO instances using the builder pattern.
		     * Provides a fluent interface for setting properties and building the DTO.
		     */
		    public static class Builder {
		        private String custName;
		        private String phoneNo;
		        private List<TransactionDTO> transactions;

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
		         * @param transactions the list of transaction DTOs
		         * @return the builder instance for method chaining
		         */
		        public Builder transactions(List<TransactionDTO> transactions) {
		            this.transactions = transactions;
		            return this;
		        }

		        /**
		         * Builds and returns the CustomerDTO instance with the configured properties.
		         * 
		         * @return the fully constructed CustomerDTO
		         */
		        public CustomerDTO build() {
		            return new CustomerDTO();
		        }
		    }

		    @Override
		    public String toString() {
		        return "CustomerDTO{" +
		                "custName='" + custName + '\'' +
		                ", phoneNo='" + phoneNo + '\'' +
		                ", transactions=" + transactions +
		                '}';
		    }
		}
