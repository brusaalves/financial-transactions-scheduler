package dev.brunoalves.financialtransactionsscheduler.business;

import static org.junit.Assert.assertEquals;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;

@RunWith(SpringRunner.class)
@SpringBootTest
public class OperationCostTest {

    private final String ISO_DATE_PATTERN = "yyyy-MM-dd'T'HH:mm:ss.sss'Z'";
    private final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat(ISO_DATE_PATTERN);

    @Test
    public void testCostToOperationA() throws Exception {
        Date scheduleDate = DATE_FORMAT.parse ("2019-07-17T12:00:00.000Z");
        Date transferDate = DATE_FORMAT.parse ("2019-07-17T12:00:00.000Z");
        BigDecimal value = BigDecimal.valueOf(100);
        BigDecimal expected = BigDecimal.valueOf(6).setScale(2, BigDecimal.ROUND_HALF_UP);
        BigDecimal actual = OperationCost.getCost(scheduleDate, transferDate, value);
        assertEquals(expected, actual);
    }

    @Test
    public void testCostToOperationB() throws Exception {
        Date scheduleDate = DATE_FORMAT.parse ("2019-07-17T12:00:00.000Z");
        Date transferDate = DATE_FORMAT.parse ("2019-07-20T12:00:00.000Z");
        BigDecimal value = BigDecimal.valueOf(100);
        BigDecimal expected = BigDecimal.valueOf(36).setScale(2, BigDecimal.ROUND_HALF_UP);
        BigDecimal actual = OperationCost.getCost(scheduleDate, transferDate, value);
        assertEquals(expected, actual);
    }

    @Test
    public void testCostToOperationC1() throws Exception {
        Date scheduleDate = DATE_FORMAT.parse ("2019-07-17T12:00:00.000Z");
        Date transferDate = DATE_FORMAT.parse ("2019-07-28T12:00:00.000Z");
        BigDecimal value = BigDecimal.valueOf(100);
        BigDecimal expected = BigDecimal.valueOf(8).setScale(2, BigDecimal.ROUND_HALF_UP);
        BigDecimal actual = OperationCost.getCost(scheduleDate, transferDate, value);
        assertEquals(expected, actual);
    }

    @Test
    public void testCostToOperationC2() throws Exception {
        Date scheduleDate = DATE_FORMAT.parse ("2019-07-17T12:00:00.000Z");
        Date transferDate = DATE_FORMAT.parse ("2019-08-08T12:00:00.000Z");
        BigDecimal value = BigDecimal.valueOf(100);
        BigDecimal expected = BigDecimal.valueOf(6).setScale(2, BigDecimal.ROUND_HALF_UP);
        BigDecimal actual = OperationCost.getCost(scheduleDate, transferDate, value);
        assertEquals(expected, actual);
    }

    @Test
    public void testCostToOperationC4() throws Exception {
        Date scheduleDate = DATE_FORMAT.parse ("2019-07-17T12:00:00.000Z");
        Date transferDate = DATE_FORMAT.parse ("2019-08-18T12:00:00.000Z");
        BigDecimal value = BigDecimal.valueOf(100);
        BigDecimal expected = BigDecimal.valueOf(4).setScale(2, BigDecimal.ROUND_HALF_UP);
        BigDecimal actual = OperationCost.getCost(scheduleDate, transferDate, value);
        assertEquals(expected, actual);
    }

    @Test(expected = Exception.class)
    public void testCostToOperationC5Failure() throws Exception {
        Date scheduleDate = DATE_FORMAT.parse ("2019-07-17T12:00:00.000Z");
        Date transferDate = DATE_FORMAT.parse ("2019-08-28T12:00:00.000Z");
        BigDecimal value = BigDecimal.valueOf(100);
        OperationCost.getCost(scheduleDate, transferDate, value);
    }

    public void testCostToOperationC5Success() throws Exception {
        Date scheduleDate = DATE_FORMAT.parse ("2019-07-17T12:00:00.000Z");
        Date transferDate = DATE_FORMAT.parse ("2019-08-28T12:00:00.000Z");
        BigDecimal value = BigDecimal.valueOf(150);
        OperationCost.getCost(scheduleDate, transferDate, value);
        BigDecimal expected = BigDecimal.valueOf(3).setScale(2, BigDecimal.ROUND_HALF_UP);
        BigDecimal actual = OperationCost.getCost(scheduleDate, transferDate, value);
        assertEquals(expected, actual);
    }

}
