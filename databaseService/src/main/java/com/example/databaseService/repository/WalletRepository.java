package com.example.databaseService.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.databaseService.entity.Wallet;

import jakarta.transaction.Transactional;

@Repository
public interface WalletRepository extends JpaRepository<Wallet, Long> {
    @Modifying
    @Transactional
    @Query("UPDATE Wallet w SET w.status = 'LOCKED' WHERE w.wallet_id = :wallet_id AND w.status = 'ACTIVE'")
    int lockWallet(@Param("wallet_id") Long wallet_id);

    @Modifying
    @Transactional
    @Query("UPDATE Wallet w SET w.status = 'ACTIVE' WHERE w.wallet_id = :wallet_id AND w.status = 'LOCKED'")
    int unlockWallet(@Param("wallet_id") Long wallet_id);

    @Modifying
    @Transactional
    @Query("UPDATE Wallet w SET w.password = :newPassword WHERE w.id = :walletId")
    int updatePasswordByWalletId(@Param("walletId") Long walletId, @Param("newPassword") String newPassword);

    @Query("SELECT w FROM Wallet w WHERE w.user_id = :user_id")
    Wallet getWalletByUserId(@Param("user_id") Long user_id);
}
