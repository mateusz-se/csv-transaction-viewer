package com.mat.sek.transactions.api;

import com.mat.sek.transactions.api.db.TransactionCountDTO;
import com.mat.sek.transactions.api.db.TransactionDTO;
import com.mat.sek.transactions.api.db.TransactionsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class TransactionsController {

    private final TransactionsRepository transactionsRepository;

    @Autowired
    public TransactionsController(TransactionsRepository transactionsRepository) {
        this.transactionsRepository = transactionsRepository;
    }

    @GetMapping("transactions")
    public List<TransactionDTO> getTransactions(@RequestParam(defaultValue = "1") Integer page,
                                               @RequestParam(defaultValue = "20") Integer results) {
        SearchParams searchParams = SearchParams.builder()
                .page(page)
                .results(results)
                .build();
        return transactionsRepository.getTransactions(searchParams);
    }

    @GetMapping("transactions/count")
    public TransactionCountDTO getTransactionsCount() {
        return new TransactionCountDTO(transactionsRepository.getTransactionsCount());
    }
}
