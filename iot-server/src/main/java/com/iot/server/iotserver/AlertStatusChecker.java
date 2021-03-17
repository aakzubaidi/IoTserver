package com.iot.server.iotserver;

import java.util.Optional;
import java.util.TimerTask;

import org.springframework.beans.factory.annotation.Autowired;


public class AlertStatusChecker extends TimerTask {
    private long alertID;

    @Autowired
    AlertRepo alertRepo;



    
    public AlertStatusChecker(long alertID) {
        this.alertID = alertID;
    }


    @Override
    public void run() {
        
        System.out.println(alertID);
        System.out.println("I am  thread whose name is: " + Thread.currentThread().getName() + " with ID: "
        + Thread.currentThread().getId() + " \n --- checking if the alert status has been confirmed ..........");

        Optional<Alert> alert = alertRepo.findById(alertID);

        System.out.println("From Runner: "+ alert.get().alertStatus.size());
        
    }
    
}
