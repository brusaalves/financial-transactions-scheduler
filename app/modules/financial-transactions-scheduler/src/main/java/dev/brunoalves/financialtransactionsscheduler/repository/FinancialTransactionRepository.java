package dev.brunoalves.financialtransactionsscheduler.repository;

import dev.brunoalves.financialtransactionsscheduler.model.FinancialTransaction;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class FinancialTransactionRepository {

    private List<FinancialTransaction> db = new ArrayList<>();

    public FinancialTransaction save(FinancialTransaction ft) {
        db.add(ft);
        return ft;
    }

    public FinancialTransaction read(String id) {
        return db.stream()
            .filter(el -> el.getId().equals(id))
            .collect(Collectors.toList())
            .get(0);
    }

    public List<FinancialTransaction> readAll() {
        return db;
    }

    public void delete(String id) {
        db = db.stream()
            .filter(el -> !el.getId().equals(id))
            .collect(Collectors.toList());
    }

    public FinancialTransaction update(FinancialTransaction ft) {
        delete(ft.getId());
        db.add(ft);
        return ft;
    }
}
