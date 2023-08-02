package com.example.rewards.service;

import com.example.rewards.model.Result;
import com.example.rewards.model.Transaction;
import com.example.rewards.repository.RewardsRepository;
import org.junit.Before;

import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import org.junit.jupiter.api.Test;

@ExtendWith(MockitoExtension.class)
public class RewardsServiceTest {


    @Mock
    private RewardsRepository rewardsRepository;

    @InjectMocks
    private RewardsService rewardsService;

    private Transaction transaction;


    @Test
    public void calculateTotalRewardPointsTest(){
        List<Transaction> transactions= new ArrayList<>();
        Transaction firstTransaction = new Transaction();
        firstTransaction.setCustomerId(1);
        firstTransaction.setTotal(120);
        firstTransaction.setPurchasedDate("07/05/2023");


        Transaction secondTransaction = new Transaction();
        secondTransaction.setCustomerId(1);
        secondTransaction.setTotal(60);
        secondTransaction.setPurchasedDate("06/05/2023");
        transactions.add(firstTransaction);
        transactions.add(secondTransaction);
        given(rewardsRepository.findTransactionByCustomerId(1))
                .willReturn(transactions);
        ResponseEntity<Result> output = rewardsService.calculateTotalRewardPoints(1);
        assertThat(output.getBody().getTotalRewards()).isEqualTo(0);

    }
}
