package dev.brunoalves.financialtransactionsscheduler.resource;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import dev.brunoalves.financialtransactionsscheduler.model.FinancialTransaction;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;

import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(ScheduleResource.class)
public class ScheduleResourceTest {

    @Autowired
    private MockMvc consumer;

    private final ObjectMapper mapper = new ObjectMapper();

    private final String RESOURCE_PATH = "/financial-transactions/schedule";
    private final String ISO_DATE_PATTERN = "yyyy-MM-dd'T'HH:mm:ss.sss'Z'";
    private final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat(ISO_DATE_PATTERN);

    private static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void testScheduleCreation() throws Exception {
        FinancialTransaction ft = new FinancialTransaction();
        ft.setAccount("a");
        ft.setDestination("b");
        ft.setTransactionDate(DATE_FORMAT.parse("2019-07-20T12:00:00.000Z"));
        ft.setValue(new BigDecimal(107));
        consumer.perform(
            post(RESOURCE_PATH)
            .content(asJsonString(ft))
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON))
            .andDo(print())
            .andExpect(status().isOk());
    }

    @Test
    public void testScheduleCreationValidations() throws Exception {
        FinancialTransaction ft = new FinancialTransaction();
        ft.setAccount("a");
        ft.setDestination("b");
        ft.setTransactionDate(DATE_FORMAT.parse("2019-07-20T12:00:00.000Z"));
        // Test without value
        consumer.perform(
            post(RESOURCE_PATH)
            .content(asJsonString(ft))
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON))
            .andDo(print())
            .andExpect(status().isBadRequest());
    }

    @Test
    public void testScheduleReadAll() throws Exception {
        consumer.perform(
            get(RESOURCE_PATH))
            .andDo(print())
            .andExpect(status().isOk());
    }

    @Test
    public void testScheduleRead() throws Exception {
        FinancialTransaction ft = new FinancialTransaction();
        ft.setAccount("a");
        ft.setDestination("b");
        ft.setTransactionDate(DATE_FORMAT.parse("2019-07-20T12:00:00.000Z"));
        ft.setValue(new BigDecimal(107));
        MvcResult expectedResult = consumer.perform(
            post(RESOURCE_PATH)
            .content(asJsonString(ft))
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON))
            .andDo(print())
            .andExpect(status().isOk())
            .andReturn();
        JsonNode expected = mapper.readTree(expectedResult.getResponse().getContentAsString());
        MvcResult actualResult = consumer.perform(
            get(RESOURCE_PATH + "/" + expected.get("id").asText()))
            .andDo(print())
            .andExpect(status().isOk())
            .andReturn();
        JsonNode actual = mapper.readTree(actualResult.getResponse().getContentAsString());
        assertEquals(expected.get("id").asText(), actual.get("id").asText());
    }

    @Test
    public void testScheduleUpdate() throws Exception {
        FinancialTransaction ft = new FinancialTransaction();
        ft.setAccount("a");
        ft.setDestination("b");
        ft.setTransactionDate(DATE_FORMAT.parse("2019-07-20T12:00:00.000Z"));
        ft.setValue(new BigDecimal(107));
        MvcResult expectedResult = consumer.perform(
            post(RESOURCE_PATH)
            .content(asJsonString(ft))
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON))
            .andDo(print())
            .andExpect(status().isOk())
            .andReturn();
        JsonNode expectedJson = mapper.readTree(expectedResult.getResponse().getContentAsString());
        ft.setDestination("d");
        MvcResult actualResult = consumer.perform(
            put(RESOURCE_PATH + "/" + expectedJson.get("id").asText())
            .content(asJsonString(ft))
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON))
            .andDo(print())
            .andExpect(status().isOk())
            .andReturn();
        JsonNode actual = mapper.readTree(actualResult.getResponse().getContentAsString());
        assertEquals("d", actual.get("destination").asText());
    }


}
