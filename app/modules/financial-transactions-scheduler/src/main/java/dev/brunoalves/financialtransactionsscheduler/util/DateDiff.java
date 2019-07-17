package dev.brunoalves.financialtransactionsscheduler.util;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Date;

@Data
@Accessors(fluent = true)
public class DateDiff {

    private long days = 0;

    public static DateDiff of(Date fin, Date ini) {
        return new DateDiff()
            .days((fin.getTime() - ini.getTime()) / (1000 * 60 * 60 * 24));
    }
}
