package dev.brunoalves.financialtransactionsscheduler.service;

import dev.brunoalves.financialtransactionsscheduler.model.FinancialTransaction;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class ScheduleServiceTest {

    private ScheduleService service;

    private final String ISO_DATE_PATTERN = "yyyy-MM-dd'T'HH:mm:ss.sss'Z'";
    private final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat(ISO_DATE_PATTERN);

    @Before
    public void init() {
        service = new ScheduleService();
    }

    @After
    public void finish() {
        service = new ScheduleService();
    }

    @Test
    public void testScheduleCreation() throws Exception {
        FinancialTransaction ft = new FinancialTransaction();
        ft.setAccount("a");
        ft.setDestination("b");
        ft.setTransactionDate(DATE_FORMAT.parse("2020-07-20T12:00:00.000Z"));
        ft.setValue(new BigDecimal(107));
        FinancialTransaction saved = service.create(ft);
        assertNotNull(saved);
    }

    @Test(expected = Exception.class)
    public void testScheduleCreationValidations() throws Exception {
        FinancialTransaction ft = new FinancialTransaction();
        ft.setAccount("a");
        ft.setDestination("b");
        ft.setTransactionDate(DATE_FORMAT.parse("2020-07-20T12:00:00.000Z"));
        // Test without value
        service.create(ft);
    }

    @Test
    public void testScheduleReadAll() throws Exception {
        assertNotNull(service.readAll());
    }

    @Test
    public void testScheduleRead() throws Exception {
        FinancialTransaction ft = new FinancialTransaction();
        ft.setAccount("a");
        ft.setDestination("b");
        ft.setTransactionDate(DATE_FORMAT.parse("2020-07-20T12:00:00.000Z"));
        ft.setValue(new BigDecimal(107));
        FinancialTransaction saved = service.create(ft);
        assertEquals(saved, service.read(saved.getId()));
    }

    @Test
    public void testScheduleUpdate() throws Exception {
        FinancialTransaction ft = new FinancialTransaction();
        ft.setAccount("a");
        ft.setDestination("b");
        ft.setTransactionDate(DATE_FORMAT.parse("2020-07-20T12:00:00.000Z"));
        ft.setValue(new BigDecimal(107));
        FinancialTransaction saved = service.create(ft);
        saved.setDestination("c");
        service.update(saved.getId(), saved);
        assertEquals("c", service.read(saved.getId()).getDestination());
    }

    @Test
    public void testScheduleDelete() throws Exception {
        FinancialTransaction ft = new FinancialTransaction();
        ft.setAccount("a");
        ft.setDestination("b");
        ft.setTransactionDate(DATE_FORMAT.parse("2020-07-20T12:00:00.000Z"));
        ft.setValue(new BigDecimal(107));
        FinancialTransaction saved = service.create(ft);
        service.delete(saved.getId());
        assertEquals(0, service.readAll().size());
    }
}
