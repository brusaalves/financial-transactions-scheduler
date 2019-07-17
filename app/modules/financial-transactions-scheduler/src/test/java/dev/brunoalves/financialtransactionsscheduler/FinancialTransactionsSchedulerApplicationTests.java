package dev.brunoalves.financialtransactionsscheduler;

import static org.assertj.core.api.Assertions.assertThat;

import dev.brunoalves.financialtransactionsscheduler.resource.ScheduleResource;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class FinancialTransactionsSchedulerApplicationTests {

    @Autowired
    ScheduleResource scheduleResource;

	@Test @Ignore
	public void contextLoads() {
        assertThat(scheduleResource).isNotNull();
	}

}
