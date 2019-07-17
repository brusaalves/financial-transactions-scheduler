package dev.brunoalves.financialtransactionsscheduler.controller;

import dev.brunoalves.financialtransactionsscheduler.model.FinancialTransaction;
import dev.brunoalves.financialtransactionsscheduler.service.ScheduleService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/financial-transactions/schedule")
public class ScheduleController {

    private final ScheduleService service;

    public ScheduleController(ScheduleService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<FinancialTransaction> create(@Valid @RequestBody FinancialTransaction ft) {
        try {
            return ResponseEntity.ok(service.create(ft));
        } catch (Exception e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<FinancialTransaction> read(@PathVariable String id) {
        try {
            return ResponseEntity.ok(service.read(id));
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping
    public ResponseEntity<List<FinancialTransaction>> readAll() {
        return ResponseEntity.ok(service.readAll());
    }

    @PutMapping("/{id}")
    public ResponseEntity<FinancialTransaction> update(@PathVariable String id, @Valid @RequestBody FinancialTransaction ft) {
        try {
            return ResponseEntity.ok(service.update(id, ft));
        } catch (IndexOutOfBoundsException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        try {
            service.delete(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
}
