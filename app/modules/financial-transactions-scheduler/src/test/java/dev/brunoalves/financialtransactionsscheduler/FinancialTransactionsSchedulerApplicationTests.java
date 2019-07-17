package dev.brunoalves.financialtransactionsscheduler;

import static org.assertj.core.api.Assertions.assertThat;

import dev.brunoalves.financialtransactionsscheduler.controller.SchedulesController;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class FinancialTransactionsSchedulerApplicationTests {

    @Autowired
    SchedulesController scheduleController;

	@Test
	public void contextLoads() {
        assertThat(scheduleController).isNotNull();
	}

}
