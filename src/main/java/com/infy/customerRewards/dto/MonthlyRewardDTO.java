package com.infy.customerRewards.dto;

import lombok.Builder;
import lombok.Data;

/**
 * Data Transfer Object for structured monthly reward information.
 * Provides a more organized format for monthly reward data compared to simple key-value pairs.
 * 
 * @author Infy
 * @version 1.0
 * @since 2024
 */
@Data
@Builder
public class MonthlyRewardDTO {
    private Integer year;
    private String month;
    private Integer points;

    // Private constructor
    private MonthlyRewardDTO(Builder builder) {
        this.year = builder.year;
        this.month = builder.month;
        this.points = builder.points;
    }

    // Getters
    public Integer getYear() { return year; }
    public String getMonth() { return month; }
    public Integer getPoints() { return points; }

    // Builder class
    public static class Builder {
        private Integer year;
        private String month;
        private Integer points;

        public Builder year(Integer year) {
            this.year = year;
            return this;
        }

        public Builder month(String month) {
            this.month = month;
            return this;
        }

        public Builder points(Integer points) {
            this.points = points;
            return this;
        }

        public MonthlyRewardDTO build() {
            return new MonthlyRewardDTO(this);
        }
    }

    public static Builder builder() {
        return new Builder();
    }
}