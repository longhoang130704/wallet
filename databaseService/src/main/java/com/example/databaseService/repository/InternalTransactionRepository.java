package com.example.databaseService.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class InternalTransactionRepository {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public void processInternalTransaction(
            Long from_wallet,
            String from_branch,
            Long to_wallet,
            String to_branch,
            Double amount,
            String description,
            String metadata) {
        String sql = "CALL process_internal_transaction(?, ?, ?, ?, ?, ?, ?::json)";
        jdbcTemplate.update(sql, from_wallet, from_branch, to_wallet, to_branch, amount, description, metadata);
    }
}
