package com.example.bankingapi.controller;

import com.example.bankingapi.dto.AccountRequest;
import com.example.bankingapi.dto.TransferRequest;
import com.example.bankingapi.entity.Account;
import com.example.bankingapi.entity.Transaction;
import com.example.bankingapi.service.BankingService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class BankingController {

    private final BankingService bankingService;

    public BankingController(BankingService bankingService) {
        this.bankingService = bankingService;
    }

    @PostMapping("/accounts")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Create a new bank account")
    @ApiResponse(responseCode = "201", description = "Account created")
    public Account createAccount(@Valid @RequestBody AccountRequest request) {
        return bankingService.createAccount(request);
    }

    @PostMapping("/transfers")
    @Operation(summary = "Transfer funds between accounts")
    @ApiResponse(responseCode = "200", description = "Transfer successful")
    public Transaction transferFunds(@Valid @RequestBody TransferRequest request) {
        return bankingService.transferFunds(request);
    }

    @GetMapping("/accounts/{accountNumber}/balance")
    @Operation(summary = "Retrieve account balance")
    public double getBalance(@PathVariable String accountNumber) {
        return bankingService.getBalance(accountNumber);
    }

    @GetMapping("/accounts/{accountNumber}/transactions")
    @Operation(summary = "Retrieve transaction history")
    public List<Transaction> getTransactionHistory(@PathVariable String accountNumber) {
        return bankingService.getTransactionHistory(accountNumber);
    }
}