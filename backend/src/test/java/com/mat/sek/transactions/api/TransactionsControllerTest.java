package com.mat.sek.transactions.api;

import com.mat.sek.transactions.api.db.TransactionDTO;
import com.mat.sek.transactions.api.db.TransactionsRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class TransactionsControllerTest {

    @MockBean
    private TransactionsRepository transactionsRepository;
    @Autowired
    private MockMvc mvc;

    @Test
    void getTransactions() throws Exception {
        SearchParams searchParams = new SearchParams(1, 50);
        List<TransactionDTO> transactions = List.of(
                createTransaction(1, "title 1"),
                createTransaction(2, "title 2"));

        when(transactionsRepository.getTransactions(searchParams)).thenReturn(transactions);

        mvc.perform(MockMvcRequestBuilders.get("/transactions?page=1&results=50"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andReturn();

    }

    @Test
    void getTransactionsCount() throws Exception {
        when(transactionsRepository.getTransactionsCount()).thenReturn(5);

        mvc.perform(MockMvcRequestBuilders.get("/transactions/count"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("count", is(5)))
                .andReturn();
    }

    private TransactionDTO createTransaction(int id, String title) {
        TransactionDTO transactionDTO = new TransactionDTO();
        transactionDTO.setId(id);
        transactionDTO.setTitle(title);
        return transactionDTO;
    }

}