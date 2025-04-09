package com.example.databaseService.mapper;

import com.example.databaseService.dto.request.internalPayment.InternalRequest;

public class InternalMapper {
    public static InternalRequest toInternalRequest(String input) {
        // Loại bỏ phần "InternalRequest(" và ")"
        input = input.substring(input.indexOf("(") + 1, input.indexOf(")"));

        // Tách các cặp key-value
        String[] pairs = input.split(", ");
        InternalRequest internalRequest = new InternalRequest();

        for (String pair : pairs) {
            String[] keyValue = pair.split("=");
            String key = keyValue[0].trim();
            String value = keyValue[1].trim();

            // Gán giá trị vào đối tượng tương ứng
            switch (key) {
                case "from_wallet":
                    internalRequest.setFrom_wallet(Long.parseLong(value));
                    break;
                case "from_branch":
                    internalRequest.setFrom_branch(value);
                    break;
                case "to_wallet":
                    internalRequest.setTo_wallet(Long.parseLong(value));
                    break;
                case "to_branch":
                    internalRequest.setTo_branch(value);
                    break;
                case "amount":
                    internalRequest.setAmount(Double.parseDouble(value));
                    break;
                case "description":
                    internalRequest.setDescription(value);
                    break;
                case "metadata":
                    internalRequest.setMetadata(value);
                    break;
                default:
                    System.out.println("Bỏ qua khóa không xác định: " + key);
            }
        }

        return internalRequest;
    }
}
