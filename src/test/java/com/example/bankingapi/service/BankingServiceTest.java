package com.example.bankingapi.service;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.example.bankingapi.dto.TransferRequest;
import com.example.bankingapi.entity.Account;
import com.example.bankingapi.exception.InsufficientFundsException;
import com.example.bankingapi.repository.AccountRepository;
import com.example.bankingapi.repository.TransactionRepository;

class BankingServiceTest {

    @Mock
    private AccountRepository accountRepository;

    @Mock
    private TransactionRepository transactionRepository;

    @InjectMocks
    private BankingService bankingService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void transferFunds_insufficientFunds_throwsException() {
        TransferRequest request = new TransferRequest("ACC001", "ACC002", 1000.0);
        Account fromAccount = new Account();
        fromAccount.setAccountNumber("ACC001");
        fromAccount.setBalance(500.0);
        Account toAccount = new Account();
        toAccount.setAccountNumber("ACC002");

        when(accountRepository.findByAccountNumber("ACC001")).thenReturn(Optional.of(fromAccount));
        when(accountRepository.findByAccountNumber("ACC002")).thenReturn(Optional.of(toAccount));

        assertThrows(InsufficientFundsException.class, () -> bankingService.transferFunds(request));
        verify(accountRepository, never()).save(any(Account.class));
    }
}