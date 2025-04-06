package com.example.databaseService.entity;
// package com.example.databaseService.Entity;

// import org.hibernate.annotations.JdbcTypeCode;
// import org.hibernate.type.SqlTypes;
// import org.springframework.stereotype.Component;

// import com.example.databaseService.enums.TransactionStatus;

// import jakarta.persistence.Column;
// import lombok.AllArgsConstructor;
// import lombok.Getter;
// import lombok.NoArgsConstructor;
// import lombok.Setter;

// @Component
// @NoArgsConstructor
// @AllArgsConstructor
// @Getter
// @Setter
// public class ExternalTransaction {

// @Column(nullable = false)
// private Long from_wallet;

// @Column(nullable = false)
// private Long to_wallet;

// @Column(nullable = false)
// private Double amount;

// private TransactionStatus status = TransactionStatus.PENDING;
// private String description;

// @JdbcTypeCode(SqlTypes.JSON)
// @Column(columnDefinition = "jsonb")
// private String metadata;

// }
