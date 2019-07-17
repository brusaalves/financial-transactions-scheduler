package dev.brunoalves.financialtransactionsscheduler.resource;

import dev.brunoalves.financialtransactionsscheduler.model.FinancialTransaction;
import org.assertj.core.api.ObjectAssert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ScheduleResourceTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    private final String RESOURCE_PATH = "/financial-transactions/schedule";
    private String API_HOST = "http://localhost:";

    private final String ISO_DATE_PATTERN = "yyyy-MM-dd'T'HH:mm:ss.sss'Z'";
    private final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat(ISO_DATE_PATTERN);

    @Test
    public void testScheduleCreationSuccess() throws Exception {
        FinancialTransaction ft = new FinancialTransaction();
        ft.setAccount("a");
        ft.setDestination("b");
        ft.setTransferDate(DATE_FORMAT.parse("2019-07-20T12:00:00.000Z"));
        ft.setValue(new BigDecimal(107));
        assertThat(this.restTemplate
            .postForEntity(API_HOST + port + RESOURCE_PATH, ft, FinancialTransaction.class))
            .extracting(res -> res.getStatusCode().is2xxSuccessful() && !res.getBody().getId().isEmpty());
    }

    @Test
    public void testScheduleCreationFailure() throws Exception {
        FinancialTransaction ft = new FinancialTransaction();
        ft.setAccount("a");
        ft.setDestination("b");
        ft.setTransferDate(DATE_FORMAT.parse("2019-07-20T12:00:00.000Z"));
//        ft.setValue(new BigDecimal(107));
        assertThat(this.restTemplate
                .postForEntity(API_HOST + port + RESOURCE_PATH, ft, FinancialTransaction.class))
                .extracting(res -> !res.getStatusCode().is2xxSuccessful());
    }
}
