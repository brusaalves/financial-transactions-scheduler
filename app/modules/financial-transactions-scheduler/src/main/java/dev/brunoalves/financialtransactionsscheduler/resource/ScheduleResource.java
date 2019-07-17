package dev.brunoalves.financialtransactionsscheduler.resource;

import dev.brunoalves.financialtransactionsscheduler.business.OperationCost;
import dev.brunoalves.financialtransactionsscheduler.model.FinancialTransaction;
import dev.brunoalves.financialtransactionsscheduler.repository.FinancialTransactionRepository;
import dev.brunoalves.financialtransactionsscheduler.util.FinancialTransactionValidator;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
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
    public ResponseEntity<FinancialTransaction> create(@Valid @RequestBody FinancialTransaction ft) {
        try {
            Date now = new Date();
            ft.setScheduleDate(now);
            FinancialTransactionValidator.validateAllParameters(ft);
            ft.setId(UUID.randomUUID().toString());
            ft.setOperationCost(OperationCost.getCost(now, ft.getTransactionDate(), ft.getValue()));
            repository.save(ft);
            return ResponseEntity.ok(ft);
        } catch (Exception e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<FinancialTransaction> read(@PathVariable String id) {
        FinancialTransaction res;
        try {
            res = repository.read(id);
            return ResponseEntity.ok(res);
        } catch (Exception e) {
            return ResponseEntity.noContent().build();
        }
    }

    @GetMapping
    public ResponseEntity<List<FinancialTransaction>> readAll() {
        return ResponseEntity.ok(repository.readAll());
    }

    @PutMapping("/{id}")
    public ResponseEntity<FinancialTransaction> update(@PathVariable String id, @Valid @RequestBody FinancialTransaction ft) {
        try {
            FinancialTransaction old = repository.read(id);
            BeanUtils.copyProperties(ft, old, "id", "scheduleDate", "operationCost");
            FinancialTransactionValidator.validateTransactionDate(ft.getTransactionDate(), old.getScheduleDate());
            old.setOperationCost(OperationCost.getCost(old.getScheduleDate(), old.getTransactionDate(), old.getValue()));
            return ResponseEntity.ok(repository.update(old));
        } catch (IndexOutOfBoundsException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        try {
            repository.read(id);
            repository.delete(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
}
