package com.infy.customerRewards.dto;

import java.time.LocalDate;

import lombok.Data;

@Data
public class TransactionResponseDTO {
    private Long id;
    private String product;
    private Double amount;
    private LocalDate date;

}
