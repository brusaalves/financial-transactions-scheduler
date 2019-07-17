package dev.brunoalves.financialtransactionsscheduler.resource;

import dev.brunoalves.financialtransactionsscheduler.business.OperationCost;
import dev.brunoalves.financialtransactionsscheduler.model.FinancialTransaction;
import dev.brunoalves.financialtransactionsscheduler.repository.FinancialTransactionRepository;
import dev.brunoalves.financialtransactionsscheduler.util.DateDiff;
import org.springframework.beans.BeanUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/financial-transactions/schedule")
public class ScheduleResource {

    private FinancialTransactionRepository repository = new FinancialTransactionRepository();

    @PostMapping
    public ResponseEntity<FinancialTransaction> create(@Valid @RequestBody FinancialTransaction ft) throws Exception {
        Date now = new Date();
        if (ft.getTransferDate() == null)
            throw new Exception("A transfer date is required");
        if (DateDiff.of(ft.getTransferDate(), now).days() < 0)
            throw new Exception("Transfer date must be greater than or equal to schedule date");
        if (ft.getValue() == null)
            throw new Exception("A value is required");
        if (ft.getDestination() == null)
            throw new Exception("A destination account is required");
        if (ft.getAccount() == null)
            throw new Exception("A origin account is required");
        ft.setId(UUID.randomUUID().toString());
        ft.setScheduleDate(now);
        ft.setOperationCost(OperationCost.getCost(now, ft.getTransferDate(), ft.getValue()));
        repository.save(ft);
        return ResponseEntity.ok(ft);
    }

    @GetMapping("/{id}")
    public ResponseEntity<FinancialTransaction> read(@PathVariable String id) {
        return ResponseEntity.ok(repository.read(id));
    }

    @GetMapping
    public ResponseEntity<List<FinancialTransaction>> readAll() {
        return ResponseEntity.ok(repository.readAll());
    }

    @PutMapping("/{id}")
    public ResponseEntity<FinancialTransaction> update(@PathVariable String id, @Valid @RequestBody FinancialTransaction ft) throws Exception {
        FinancialTransaction old = repository.read(id);
        BeanUtils.copyProperties(ft, old, "id", "scheduleDate", "operationCost");
        ft.setOperationCost(OperationCost.getCost(ft.getScheduleDate(), ft.getTransferDate(), ft.getValue()));
        return ResponseEntity.ok(repository.update(ft));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        repository.delete(id);
        return ResponseEntity.noContent().build();
    }
}
