package com.example.rewards.service;

import com.example.rewards.model.Result;
import com.example.rewards.model.Transaction;
import com.example.rewards.repository.RewardsRepository;
import org.hibernate.result.Output;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.time.*;

import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

@Service
public class RewardsService {
    @Autowired
    RewardsRepository rewardsRepository;

    @Value("${rewardPoints.OverHundred}")
    private int rewardPointsOverHundred;

    @Value("${rewardPoints.OverFifty}")
    private int rewardPointsOverFifty;

    @Value("${check.Hundred}")
    private int checkHundred;

    @Value("${check.Fifty}")
    private int checkFifty;

    public Logger logger= LoggerFactory.getLogger(RewardsService.class);

    // Assuming only three months of data is stored in database.
    public ResponseEntity<Result> calculateTotalRewardPoints(int id) {
        int firstMonthRewards =0;
        int secondMonthRewards =0;
        int thirdMonthRewards =0;
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        List<Transaction> transactions = rewardsRepository.findTransactionByCustomerId(id);
        if(!transactions.isEmpty()){
            for(Transaction t: transactions){
                LocalDate today = LocalDate.now(); today.format(dateTimeFormatter);
                LocalDate transactionDate = LocalDate.parse(t.getPurchasedDate(),dateTimeFormatter);

                if(today.getMonthValue()-transactionDate.getMonthValue() == 2){
                    thirdMonthRewards += calculateRewardsPerTransaction(t);

                }else if(today.getMonthValue()-transactionDate.getMonthValue() == 1){
                    secondMonthRewards += calculateRewardsPerTransaction(t);

                }else if(today.getMonthValue()-transactionDate.getMonthValue() ==0){
                    firstMonthRewards += calculateRewardsPerTransaction(t);

                }

            }
            logger.info("customer found: {}", id);
            Result r= new Result();
            r.setFirstMonthRewards(firstMonthRewards);
            r.setSecondMonthRewards(secondMonthRewards);
            r.setThirdMonthRewards(thirdMonthRewards);

            r.setTotalRewards(firstMonthRewards +secondMonthRewards +thirdMonthRewards);
            return ResponseEntity.ok().body(r);
        }
        logger.error("customer not found: {}", id);Result r= new Result();
        return ResponseEntity.badRequest().contentType(MediaType.APPLICATION_JSON).body(r);

    }

    private int calculateRewardsPerTransaction(Transaction t) {
        int rewards=0;
        int total = Math.round(t.getTotal());
        if(total> checkHundred){
            return (rewardPointsOverHundred*(total-checkHundred))+(rewardPointsOverFifty*checkFifty);
        }else if(t.getTotal() >checkFifty){
           return (rewardPointsOverFifty*checkFifty);
        }
        return rewards;
    }
}
