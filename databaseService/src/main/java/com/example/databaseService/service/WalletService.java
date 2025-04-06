package com.example.databaseService.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.databaseService.entity.Wallet;
import com.example.databaseService.repository.WalletRepository;

@Service
public class WalletService {
    private final WalletRepository walletRepository;

    @Autowired
    public WalletService(WalletRepository walletRepository) {
        this.walletRepository = walletRepository;
    }

    public String create(Wallet wallet) {
        try {
            walletRepository.save(wallet);
            return "success";
        } catch (Exception e) {
            System.out.println("Error in WalletService create");
            System.out.println(e.getMessage());
            return "failed";
        }
    }

    public Wallet getWalletById(Long wallet_id) {
        return walletRepository.findById(wallet_id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy Wallet với ID: " + wallet_id));
    }

    public String lockWallet(Long wallet_id) {
        try {
            int updatedRows = walletRepository.lockWallet(wallet_id);

            if (updatedRows > 0) {
                return "Wallet " + wallet_id + " đã được khóa thành công!";
            } else {
                return "Wallet không tồn tại hoặc không ở trạng thái ACTIVE!";
            }
        } catch (Exception e) {
            return "Lỗi khi khóa Wallet: " + e.getMessage();
        }
    }

    public String unlockWallet(Long wallet_id) {
        try {
            int updatedRows = walletRepository.unlockWallet(wallet_id);

            if (updatedRows > 0) {
                return "Wallet " + wallet_id + " đã được mở khóa thành công!";
            } else {
                return "Wallet không tồn tại hoặc không ở trạng thái LOCKED!";
            }
        } catch (Exception e) {
            return "Lỗi khi mở khóa Wallet: " + e.getMessage();
        }
    }

    public Page<Wallet> getAllWallets(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return walletRepository.findAll(pageable);
    }

    public String updatePassword(Long wallet_id, String password) {
        try {
            int updatedRows = walletRepository.updatePasswordByWalletId(wallet_id, password);

            if (updatedRows > 0) {
                return "Wallet " + wallet_id + " đã được cập nhật mật khẩu thành công!";
            } else {
                return "Wallet không tồn tại";
            }
        } catch (Exception e) {
            return "Lỗi khi cập nhật mật khẩu Wallet: " + e.getMessage();
        }
    }

    public Wallet getWalletByUserId(Long user_id) {
        return walletRepository.getWalletByUserId(user_id);
    }
}
