package dev.brunoalves.financialtransactionsscheduler.business;

import dev.brunoalves.financialtransactionsscheduler.util.DateDiff;

import java.math.BigDecimal;
import java.util.Date;

public class OperationCost {

    public static BigDecimal getCost(Date scheduleDate, Date transferDate, BigDecimal value) throws Exception {
        long dateDiff = DateDiff.of(transferDate, scheduleDate).days();
        if (dateDiff == 0)
            return BigDecimal.valueOf(3).add(value.multiply(BigDecimal.valueOf(0.03))).setScale(2, BigDecimal.ROUND_HALF_UP);
        if (dateDiff <= 10)
            return BigDecimal.valueOf(12).multiply(BigDecimal.valueOf(dateDiff)).setScale(2, BigDecimal.ROUND_HALF_UP);;
        if (dateDiff > 10 && dateDiff <= 20)
            return value.multiply(BigDecimal.valueOf(0.08)).setScale(2, BigDecimal.ROUND_HALF_UP);;
        if (dateDiff > 20 && dateDiff <= 30)
            return value.multiply(BigDecimal.valueOf(0.06)).setScale(2, BigDecimal.ROUND_HALF_UP);;
        if (dateDiff > 30 && dateDiff <= 40)
            return value.multiply(BigDecimal.valueOf(0.04)).setScale(2, BigDecimal.ROUND_HALF_UP);;
        if (dateDiff > 40 && value.floatValue() > 100)
            return value.multiply(BigDecimal.valueOf(0.02)).setScale(2, BigDecimal.ROUND_HALF_UP);;
        if (value.floatValue() <= 100)
            throw new Exception("Transaction value must be at least $100 for transactions after 40 days");
        return BigDecimal.ZERO;
    }
}
