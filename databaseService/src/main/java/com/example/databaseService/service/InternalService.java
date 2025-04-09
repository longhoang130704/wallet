package com.example.databaseService.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.databaseService.repository.InternalTransactionRepository;

@Service
public class InternalService {
    private final InternalTransactionRepository internalTransactionRepository;

    @Autowired
    public InternalService(InternalTransactionRepository internalTransactionRepository) {
        this.internalTransactionRepository = internalTransactionRepository;
    }

    public Boolean processInternalTransaction(
            Long from_wallet,
            String from_branch,
            Long to_wallet,
            String to_branch,
            Double amount,
            String description,
            String metadata) {
        try {
            internalTransactionRepository.processInternalTransaction(
                    from_wallet,
                    from_branch,
                    to_wallet,
                    to_branch,
                    amount,
                    description,
                    metadata);
            return true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }

    }
}
