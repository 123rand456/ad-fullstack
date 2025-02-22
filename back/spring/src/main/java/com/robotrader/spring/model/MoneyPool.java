package com.robotrader.spring.model;

import com.robotrader.spring.model.enums.PortfolioTypeEnum;
import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class MoneyPool {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, unique = true)
    private PortfolioTypeEnum portfolioType;

    @NotNull(message = "Amount cannot be null")
    @DecimalMin(value = "0", message = "Amount must be greater than or equal to 0")
    @DecimalMax(value = "1000000000.00", message = "Amount must be less than or equal to 1000000000.00")
    @Column(nullable = false, precision = 16, scale = 4)
    private BigDecimal poolBalance;

    @NotNull(message = "Amount cannot be null")
    @DecimalMin(value = "0", message = "Amount must be greater than or equal to 0")
    @Column(nullable = false, precision = 16, scale = 8)
    private BigDecimal unitPrice;

    @NotNull(message = "Unit quantity cannot be null")
    @DecimalMin(value = "0", message = "Amount must be greater than or equal to 0")
    @Column(nullable = false, precision = 16, scale = 4)
    private BigDecimal totalUnitQty;
}
