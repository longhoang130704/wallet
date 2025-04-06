package com.example.databaseService.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.databaseService.repository.ExternalTransactionRepository;

@Service
public class ExternalService {
    private final ExternalTransactionRepository externalTransactionRepository;

    @Autowired
    public ExternalService(ExternalTransactionRepository externalTransactionRepository) {
        this.externalTransactionRepository = externalTransactionRepository;
    }

    public void processExternalTransaction(
            Long walletId,
            String type,
            Double amount,
            String branch,
            String description,
            String metadata) {
        externalTransactionRepository.processExternalTransaction(walletId, type, amount, description, branch, metadata);
    }
}
