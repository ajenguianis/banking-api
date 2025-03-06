package com.example.bankingapi.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TransferRequest {
    @NotBlank
    private String fromAccountNumber;
    @NotBlank
    private String toAccountNumber;
    @Positive
    private double amount;
}