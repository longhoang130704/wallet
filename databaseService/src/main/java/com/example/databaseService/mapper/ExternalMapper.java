package com.example.databaseService.mapper;

import com.example.databaseService.dto.request.externalPayment.ExternalRequest;
import com.example.databaseService.enums.ExternalType;

public class ExternalMapper {
    public static ExternalRequest toExternalRequest(String input) {
        ExternalRequest request = new ExternalRequest();

        // Cắt chuỗi và loại bỏ dấu ngoặc và khoảng trắng
        input = input.replace("ExternalRequest(", "").replace(")", "");

        // Chia chuỗi theo dấu phẩy
        String[] fields = input.split(",\\s*");

        for (String field : fields) {
            String[] keyValue = field.split("=");

            switch (keyValue[0].trim()) {
                case "wallet_id":
                    request.setWallet_id(Long.parseLong(keyValue[1].trim()));
                    break;
                case "type":
                    request.setType(ExternalType.valueOf(keyValue[1].trim()));
                    break;
                case "amount":
                    request.setAmount(Double.parseDouble(keyValue[1].trim()));
                    break;
                case "branch":
                    request.setBranch(keyValue[1].trim());
                    break;
                case "description":
                    request.setDescription(keyValue[1].trim());
                    break;
                case "metadata":
                    // Nếu metadata là null, gán giá trị null
                    request.setMetadata(keyValue[1].trim().equals("null") ? null : keyValue[1].trim());
                    break;
                default:
                    break;
            }
        }

        return request;
    }
}
