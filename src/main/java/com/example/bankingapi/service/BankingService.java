package com.example.bankingapi.service;

import com.example.bankingapi.dto.AccountRequest;
import com.example.bankingapi.dto.TransferRequest;
import com.example.bankingapi.entity.Account;
import com.example.bankingapi.entity.Customer;
import com.example.bankingapi.entity.Transaction;
import com.example.bankingapi.exception.InsufficientFundsException;
import com.example.bankingapi.exception.ResourceNotFoundException;
import com.example.bankingapi.repository.AccountRepository;
import com.example.bankingapi.repository.CustomerRepository;
import com.example.bankingapi.repository.TransactionRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class BankingService {

    private final CustomerRepository customerRepository;
    private final AccountRepository accountRepository;
    private final TransactionRepository transactionRepository;

    public BankingService(CustomerRepository customerRepository, AccountRepository accountRepository,
                          TransactionRepository transactionRepository) {
        this.customerRepository = customerRepository;
        this.accountRepository = accountRepository;
        this.transactionRepository = transactionRepository;
    }

    @Transactional
    public Account createAccount(AccountRequest request) {
        Customer customer = customerRepository.findById(request.getCustomerId())
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found"));
        
        Account account = new Account();
        account.setAccountNumber("ACC" + UUID.randomUUID().toString().substring(0, 8));
        account.setBalance(request.getInitialDeposit());
        account.setCustomer(customer);
        
        return accountRepository.save(account);
    }

    @Transactional
    public Transaction transferFunds(TransferRequest request) {
        Account fromAccount = accountRepository.findByAccountNumber(request.getFromAccountNumber())
                .orElseThrow(() -> new ResourceNotFoundException("Source account not found"));
        Account toAccount = accountRepository.findByAccountNumber(request.getToAccountNumber())
                .orElseThrow(() -> new ResourceNotFoundException("Target account not found"));

        if (fromAccount.getBalance() < request.getAmount()) {
            throw new InsufficientFundsException("Insufficient funds in account " + request.getFromAccountNumber());
        }

        fromAccount.setBalance(fromAccount.getBalance() - request.getAmount());
        toAccount.setBalance(toAccount.getBalance() + request.getAmount());

        accountRepository.save(fromAccount);
        accountRepository.save(toAccount);

        Transaction transaction = new Transaction();
        transaction.setFromAccount(fromAccount);
        transaction.setToAccount(toAccount);
        transaction.setAmount(request.getAmount());
        transaction.setTimestamp(LocalDateTime.now());

        return transactionRepository.save(transaction);
    }

    public double getBalance(String accountNumber) {
        Account account = accountRepository.findByAccountNumber(accountNumber)
                .orElseThrow(() -> new ResourceNotFoundException("Account not found"));
        return account.getBalance();
    }

    public List<Transaction> getTransactionHistory(String accountNumber) {
        Account account = accountRepository.findByAccountNumber(accountNumber)
                .orElseThrow(() -> new ResourceNotFoundException("Account not found"));
        return transactionRepository.findByFromAccountOrToAccount(account, account);
    }
}