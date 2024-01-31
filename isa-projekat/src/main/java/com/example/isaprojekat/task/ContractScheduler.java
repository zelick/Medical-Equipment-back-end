package com.example.isaprojekat.task;

import com.example.isaprojekat.model.Contract;
import com.example.isaprojekat.service.ContractService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ContractScheduler {

    @Autowired
    private ContractService contractService;

    @Scheduled(cron = "0 * * * * ?")
    public void checkContracts() {
        System.out.println("CHECKING FOR CONTRACTS...");
        contractService.checkAndUpdateExistingContracts();
    }
}
