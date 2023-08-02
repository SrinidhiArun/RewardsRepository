package com.example.rewards.controller;

import com.example.rewards.model.Result;
import com.example.rewards.service.RewardsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RewardsResource {

    @Autowired
    RewardsService rewardsService;

    public Logger logger = LoggerFactory.getLogger(RewardsResource.class);
    @GetMapping("/rewards/{id}")
    public ResponseEntity<Result> calculateRewards(@PathVariable int id){
        logger.info("calculating rewards for customer : {}", id);
        return rewardsService.calculateTotalRewardPoints(id);
    }
}
