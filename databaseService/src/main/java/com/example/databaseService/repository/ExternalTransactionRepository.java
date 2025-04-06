package com.example.databaseService.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class ExternalTransactionRepository {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public void processExternalTransaction(
            Long walletId,
            String type,
            Double amount,
            String description,
            String branch,
            String metadata) {
        String sql = "CALL process_external_transaction(?, ?, ?, ?, ?, ?::json)";
        jdbcTemplate.update(sql, walletId, type, amount, description, branch, metadata);
    }
}
