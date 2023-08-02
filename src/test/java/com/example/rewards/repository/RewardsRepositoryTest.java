package com.example.rewards.repository;

import com.example.rewards.model.Transaction;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


@RunWith(SpringRunner.class)
@DataJpaTest
public class RewardsRepositoryTest {
    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private RewardsRepository rewardsRepository;

    @Test
    public void whenFindById() {
        //given
        Transaction firstTransaction = new Transaction();
        firstTransaction.setCustomerId(1);
        firstTransaction.setTotal(120);
        firstTransaction.setPurchasedDate("07/05/2023");
        entityManager.persist(firstTransaction);
        entityManager.flush();

        Transaction secondTransaction = new Transaction();
        secondTransaction.setCustomerId(1);
        secondTransaction.setTotal(60);
        secondTransaction.setPurchasedDate("06/05/2023");
        entityManager.persist(secondTransaction);
        entityManager.flush();



        //when
        List<Transaction> transactions = rewardsRepository.findTransactionByCustomerId(1);

        //then
        assertThat(transactions.size()).isEqualTo(2);
        assertThat(transactions.get(0)).isEqualTo(firstTransaction);

    }
}
