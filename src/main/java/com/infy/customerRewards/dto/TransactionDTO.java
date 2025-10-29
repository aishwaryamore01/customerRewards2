package com.infy.customerRewards.dto;

import java.time.LocalDate;

import lombok.Data;

@Data
public class TransactionDTO {
	private Long id;
	private LocalDate date;
	private Double amount;
	private String product;
	private int rewardPoints;
	
	public LocalDate getDate() {
		return date;
	}
	public void setDate(LocalDate date) {
		this.date = date;
	}
	public Double getAmount() {
		return amount;
	}
	public void setAmount(Double amount) {
		this.amount = amount;
	}
	public String getProduct() {
		return product;
	}
	public void setProduct(String product) {
		this.product = product;
	}
	public int getRewardPoints() {
		return rewardPoints;
	}
	public void setRewardPoints(int rewardPoints) {
		this.rewardPoints = rewardPoints;
	}
	
	 public static TransactionDTOBuilder builder() {
	        return new TransactionDTOBuilder();
	    }

	    /**
	     * Builder class
	     */
	    public static class TransactionDTOBuilder {
	        private final TransactionDTO transactionDTO;

	        public TransactionDTOBuilder() {
	            this.transactionDTO = new TransactionDTO();
	        }

	        public TransactionDTOBuilder id(Long id) {
	            transactionDTO.id = id;
	            return this;
	        }

	        public TransactionDTOBuilder date(LocalDate date) {
	            transactionDTO.date = date;
	            return this;
	        }

	        public TransactionDTOBuilder product(String product) {
	            transactionDTO.product = product;
	            return this;
	        }

	        public TransactionDTOBuilder amount(Double amount) {
	            transactionDTO.amount = amount;
	            return this;
	        }

	        public TransactionDTOBuilder rewardPoints(Integer rewardPoints) {
	            transactionDTO.rewardPoints = rewardPoints;
	            return this;
	        }

	        public TransactionDTO build() {
	            return transactionDTO;
	        }
	    }

	    @Override
	    public String toString() {
	        return "TransactionDTO{" +
	                "id=" + id +
	                ", date=" + date +
	                ", product='" + product + '\'' +
	                ", amount=" + amount +
	                ", rewardPoints=" + rewardPoints +
	                '}';
	    }
	}
