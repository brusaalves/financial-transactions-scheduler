package dev.brunoalves.financialtransactionsscheduler.service;

import dev.brunoalves.financialtransactionsscheduler.business.OperationCost;
import dev.brunoalves.financialtransactionsscheduler.model.FinancialTransaction;
import dev.brunoalves.financialtransactionsscheduler.repository.FinancialTransactionRepository;
import dev.brunoalves.financialtransactionsscheduler.util.FinancialTransactionValidator;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class ScheduleService {

    private FinancialTransactionRepository repository = new FinancialTransactionRepository();

    public FinancialTransaction create(FinancialTransaction ft) throws Exception {
        Date now = new Date();
        ft.setScheduleDate(now);
        FinancialTransactionValidator.validateAllParameters(ft);
        ft.setId(UUID.randomUUID().toString());
        ft.setOperationCost(OperationCost.getCost(now, ft.getTransactionDate(), ft.getValue()));
        return repository.save(ft);
    }

    public FinancialTransaction read(String id) {
        return repository.read(id);
    }

    public List<FinancialTransaction> readAll() {
        return repository.readAll();
    }

    public FinancialTransaction update(String id, FinancialTransaction ft) throws Exception {
        FinancialTransaction old = repository.read(id);
        BeanUtils.copyProperties(ft, old, "id", "scheduleDate", "operationCost");
        FinancialTransactionValidator.validateTransactionDate(ft.getTransactionDate(), old.getScheduleDate());
        old.setOperationCost(OperationCost.getCost(old.getScheduleDate(), old.getTransactionDate(), old.getValue()));
        return repository.update(old);
    }

    public void delete(String id) throws Exception {
        if (repository.read(id) == null) throw new Exception();
        repository.delete(id);
    }
}
