package com.example.wallet.util;

public class WalletUtil {
    public static Double extractBalance(String walletString) {
        // Tìm vị trí của "balance=" và dấu phẩy tiếp theo
        String key = "balance=";
        int startIndex = walletString.indexOf(key);
        if (startIndex == -1) {
            return null; // Không tìm thấy từ khóa balance=
        }
        startIndex += key.length(); // Bỏ qua "balance="

        int endIndex = walletString.indexOf(",", startIndex);
        if (endIndex == -1) {
            endIndex = walletString.length(); // Nếu balance là cuối chuỗi
        }

        String balanceStr = walletString.substring(startIndex, endIndex).trim();
        try {
            return Double.parseDouble(balanceStr);
        } catch (NumberFormatException e) {
            System.err.println("Không thể chuyển đổi chuỗi balance sang số: " + balanceStr);
            return null;
        }
    }
}
