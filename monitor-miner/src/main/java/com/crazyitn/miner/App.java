package com.crazyitn.miner;

import org.bitcoinj.params.MainNetParams;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigInteger;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class App {
    private static Logger logger = LoggerFactory.getLogger(App.class);

    public static MainNetParams mainNetParams = new MainNetParams();

    public static void main(String[] args) {
        logger.info("任务开始");

        ExecutorService executorService = Executors.newFixedThreadPool(100);
        Worker worker = new Worker();
        worker.setStart(new BigInteger("1231006505000000"));
        for(int i =0 ;i<100;i++){
            executorService.execute(worker);
        }
        logger.info("任务结束");
    }
}
