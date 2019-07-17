package dev.brunoalves.financialtransactionsscheduler.util;

import dev.brunoalves.financialtransactionsscheduler.model.FinancialTransaction;

import java.util.Date;

public class FinancialTransactionValidator {

    public static void validateAllParameters(FinancialTransaction ft) throws Exception {
        if (ft.getTransactionDate() == null)
            throw new Exception("A transaction date is required");
        if (DateDiff.of(ft.getTransactionDate(), ft.getScheduleDate()).days() < 0)
            throw new Exception("Transaction date must be greater than or equal to schedule date");
        if (ft.getValue() == null)
            throw new Exception("A value is required");
        if (ft.getDestination() == null)
            throw new Exception("A destination account is required");
        if (ft.getAccount() == null)
            throw new Exception("A origin account is required");
    }

    public static void validateTransactionDate(Date transactionDate, Date scheduleDate) throws Exception {
        if (transactionDate == null)
            throw new Exception("A transaction date is required");
        if (DateDiff.of(transactionDate, scheduleDate).days() < 0)
            throw new Exception("Transaction date must be greater than or equal to schedule date");
    }
}
