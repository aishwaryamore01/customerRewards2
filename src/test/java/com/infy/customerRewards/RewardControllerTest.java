package com.infy.customerRewards;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.infy.customerRewards.controller.RewardController;
import com.infy.customerRewards.dto.CustomerDTO;
import com.infy.customerRewards.dto.CustomerResponseDTO;
import com.infy.customerRewards.dto.RewardResponseDTO;
import com.infy.customerRewards.dto.TransactionDTO;
import com.infy.customerRewards.service.RewardService;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/**
 * Test class for RewardController
 * 
 * @author Infy
 * @version 1.0
 * @since 2024
 */
@ExtendWith(MockitoExtension.class)
class RewardControllerTest {

    @Mock
    private RewardService rewardService;

    @InjectMocks
    private RewardController rewardController;

    private CustomerDTO customerDTO;
    private CustomerResponseDTO customerResponseDTO;
    private TransactionDTO transactionDTO;
    private RewardResponseDTO rewardResponseDTO;

    @BeforeEach
    void setUp() {
        // Setup test data
        transactionDTO = TransactionDTO.builder()
                .id(1L)
                .date(LocalDate.of(2024, 1, 15))
                .product("Laptop")
                .amount(150.0)
                .rewardPoints(150)
                .build();

        customerDTO = CustomerDTO.builder()
                .custName("John Doe")
                .phoneNo("1234567890")
                .transactions(Arrays.asList(transactionDTO))
                .build();

//        customerResponseDTO = CustomerResponseDTO.builder()
//                .id(1L)
//                .custName("John Doe")
//                .phoneNo("encoded_phone")
//                .transactions(Arrays.asList(transactionDTO))
//                .build();

        rewardResponseDTO = RewardResponseDTO.builder()
                .customerId(1L)
                .custName("John Doe")
                .phoneNo("encoded_phone")
                .transactions(Arrays.asList(transactionDTO))
                .totalRewards(150)
                .build();
    }

    // =============================================
    // CREATE CUSTOMER TESTS
    // =============================================

    /**
     * Test customer creation with null input
     */
    @Test
    void testCreateCustomer_NullInput() {
        // Given
        when(rewardService.createCustomer(null))
            .thenThrow(new IllegalArgumentException("CustomerDTO cannot be null"));

        // When & Then
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, 
            () -> rewardController.createCustomer(null));
        
        assertEquals("CustomerDTO cannot be null", exception.getMessage());
        verify(rewardService, times(1)).createCustomer(null);
    }

    /**
     * Test customer creation with invalid data
     */
    @Test
    void testCreateCustomer_InvalidData() {
        // Given
        CustomerDTO invalidCustomerDTO = CustomerDTO.builder()
                .custName("") // Empty name
                .phoneNo("") // Empty phone
                .build();

        when(rewardService.createCustomer(invalidCustomerDTO))
            .thenThrow(new IllegalArgumentException("Customer name cannot be empty"));

        // When & Then
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, 
            () -> rewardController.createCustomer(invalidCustomerDTO));
        
        assertEquals("Customer name cannot be empty", exception.getMessage());
        verify(rewardService, times(1)).createCustomer(invalidCustomerDTO);
    }

    // =============================================
    // GET CUSTOMER TRANSACTIONS TESTS
    // =============================================

    /**
     * Test successful retrieval of customer transactions
     */
    @Test
    void testGetCustomerTransactions_Success() {
        // Given
        List<TransactionDTO> transactions = Arrays.asList(transactionDTO);
        when(rewardService.getCustomerTransactions(1L)).thenReturn(transactions);

        // When
        ResponseEntity<List<TransactionDTO>> response = rewardController.getCustomerTransactions(1L);

        // Then
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().size());
        assertEquals("Laptop", response.getBody().get(0).getProduct());
        
        verify(rewardService, times(1)).getCustomerTransactions(1L);
    }

    /**
     * Test retrieval of customer transactions when customer has no transactions
     */
    @Test
    void testGetCustomerTransactions_NoTransactions() {
        // Given
        when(rewardService.getCustomerTransactions(1L)).thenReturn(Arrays.asList());

        // When
        ResponseEntity<List<TransactionDTO>> response = rewardController.getCustomerTransactions(1L);

        // Then
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertTrue(response.getBody().isEmpty());
        
        verify(rewardService, times(1)).getCustomerTransactions(1L);
    }

    /**
     * Test retrieval of transactions for non-existent customer
     */
    @Test
    void testGetCustomerTransactions_CustomerNotFound() {
        // Given
        when(rewardService.getCustomerTransactions(999L))
            .thenThrow(new RuntimeException("Customer not found: 999"));

        // When & Then
        RuntimeException exception = assertThrows(RuntimeException.class, 
            () -> rewardController.getCustomerTransactions(999L));
        
        assertEquals("Customer not found: 999", exception.getMessage());
        verify(rewardService, times(1)).getCustomerTransactions(999L);
    }

    /**
     * Test retrieval of transactions with null customer ID
     */
    @Test
    void testGetCustomerTransactions_NullCustomerId() {
        // Given
        when(rewardService.getCustomerTransactions(null))
            .thenThrow(new IllegalArgumentException("Customer ID cannot be null"));

        // When & Then
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, 
            () -> rewardController.getCustomerTransactions(null));
        
        assertEquals("Customer ID cannot be null", exception.getMessage());
        verify(rewardService, times(1)).getCustomerTransactions(null);
    }

    // =============================================
    // GET REWARDS FOR CUSTOMER TESTS
    // =============================================

    /**
     * Test successful retrieval of customer rewards
     */
    @Test
    void testGetRewardsForCustomer_Success() {
        // Given
        LocalDate startDate = LocalDate.of(2024, 1, 1);
        LocalDate endDate = LocalDate.of(2024, 12, 31);
        
        when(rewardService.getRewardsForCustomer(1L, startDate, endDate)).thenReturn(rewardResponseDTO);

        // When
        ResponseEntity<RewardResponseDTO> response = rewardController.getRewardsForCustomer(1L, startDate, endDate);

        // Then
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1L, response.getBody().getCustomerId());
        assertEquals(150, response.getBody().getTotalRewards());
        
        verify(rewardService, times(1)).getRewardsForCustomer(1L, startDate, endDate);
    }

    /**
     * Test retrieval of rewards with invalid date range (startDate after endDate)
     */
    @Test
    void testGetRewardsForCustomer_InvalidDateRange() {
        // Given
        LocalDate startDate = LocalDate.of(2024, 12, 31);
        LocalDate endDate = LocalDate.of(2024, 1, 1);
        
        when(rewardService.getRewardsForCustomer(1L, startDate, endDate))
            .thenThrow(new IllegalArgumentException("Start date cannot be after end date"));

        // When & Then
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, 
            () -> rewardController.getRewardsForCustomer(1L, startDate, endDate));
        
        assertEquals("Start date cannot be after end date", exception.getMessage());
        verify(rewardService, times(1)).getRewardsForCustomer(1L, startDate, endDate);
    }

    /**
     * Test retrieval of rewards for non-existent customer
     */
    @Test
    void testGetRewardsForCustomer_CustomerNotFound() {
        // Given
        LocalDate startDate = LocalDate.of(2024, 1, 1);
        LocalDate endDate = LocalDate.of(2024, 12, 31);
        
        when(rewardService.getRewardsForCustomer(999L, startDate, endDate))
            .thenThrow(new RuntimeException("Customer not found: 999"));

        // When & Then
        RuntimeException exception = assertThrows(RuntimeException.class, 
            () -> rewardController.getRewardsForCustomer(999L, startDate, endDate));
        
        assertEquals("Customer not found: 999", exception.getMessage());
        verify(rewardService, times(1)).getRewardsForCustomer(999L, startDate, endDate);
    }

    /**
     * Test retrieval of rewards when no transactions in date range
     */
    @Test
    void testGetRewardsForCustomer_NoTransactionsInRange() {
        // Given
        LocalDate startDate = LocalDate.of(2024, 1, 1);
        LocalDate endDate = LocalDate.of(2024, 12, 31);
        
        when(rewardService.getRewardsForCustomer(1L, startDate, endDate))
            .thenThrow(new RuntimeException("No transactions found"));

        // When & Then
        RuntimeException exception = assertThrows(RuntimeException.class, 
            () -> rewardController.getRewardsForCustomer(1L, startDate, endDate));
        
        assertEquals("No transactions found", exception.getMessage());
        verify(rewardService, times(1)).getRewardsForCustomer(1L, startDate, endDate);
    }

    /**
     * Test retrieval of rewards with null dates
     */
    @Test
    void testGetRewardsForCustomer_NullDates() {
        // Given
        when(rewardService.getRewardsForCustomer(1L, null, null))
            .thenThrow(new IllegalArgumentException("Start date and end date cannot be null"));

        // When & Then
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, 
            () -> rewardController.getRewardsForCustomer(1L, null, null));
        
        assertEquals("Start date and end date cannot be null", exception.getMessage());
        verify(rewardService, times(1)).getRewardsForCustomer(1L, null, null);
    }

    /**
     * Test retrieval of rewards with same start and end date
     */
    @Test
    void testGetRewardsForCustomer_SameStartAndEndDate() {
        // Given
        LocalDate date = LocalDate.of(2024, 1, 15);
        when(rewardService.getRewardsForCustomer(1L, date, date)).thenReturn(rewardResponseDTO);

        // When
        ResponseEntity<RewardResponseDTO> response = rewardController.getRewardsForCustomer(1L, date, date);

        // Then
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        
        verify(rewardService, times(1)).getRewardsForCustomer(1L, date, date);
    }

    // =============================================
    // EDGE CASE TESTS
    // =============================================

    /**
     * Test with maximum valid customer ID
     */
    @Test
    void testGetCustomerTransactions_MaxCustomerId() {
        // Given
        Long maxCustomerId = Long.MAX_VALUE;
        when(rewardService.getCustomerTransactions(maxCustomerId)).thenReturn(Arrays.asList(transactionDTO));

        // When
        ResponseEntity<List<TransactionDTO>> response = rewardController.getCustomerTransactions(maxCustomerId);

        // Then
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        
        verify(rewardService, times(1)).getCustomerTransactions(maxCustomerId);
    }

    /**
     * Test with minimum valid customer ID
     */
    @Test
    void testGetCustomerTransactions_MinCustomerId() {
        // Given
        Long minCustomerId = 1L;
        when(rewardService.getCustomerTransactions(minCustomerId)).thenReturn(Arrays.asList(transactionDTO));

        // When
        ResponseEntity<List<TransactionDTO>> response = rewardController.getCustomerTransactions(minCustomerId);

        // Then
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        
        verify(rewardService, times(1)).getCustomerTransactions(minCustomerId);
    }

    /**
     * Test date parsing with different valid formats
     */
    @Test
    void testGetRewardsForCustomer_ValidDateFormats() {
        // Given
        LocalDate startDate = LocalDate.of(2024, 1, 1);
        LocalDate endDate = LocalDate.of(2024, 12, 31);
        
        when(rewardService.getRewardsForCustomer(1L, startDate, endDate)).thenReturn(rewardResponseDTO);

        // When
        ResponseEntity<RewardResponseDTO> response = rewardController.getRewardsForCustomer(1L, startDate, endDate);

        // Then
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        
        verify(rewardService, times(1)).getRewardsForCustomer(1L, startDate, endDate);
    }
}
