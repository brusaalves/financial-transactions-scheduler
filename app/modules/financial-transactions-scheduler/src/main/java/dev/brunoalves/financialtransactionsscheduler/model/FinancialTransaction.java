package dev.brunoalves.financialtransactionsscheduler.model;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.util.Date;

@Data
public final class FinancialTransaction {

    // Provided by application
    private String id;
    private BigDecimal operationCost;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private Date scheduleDate;

    // Parametrized by user
    private String account;
    private String destination;
    private BigDecimal value;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private Date transactionDate;

}
