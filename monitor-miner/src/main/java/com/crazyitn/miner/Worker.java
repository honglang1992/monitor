package com.crazyitn.miner;

import com.alibaba.fastjson.JSONObject;
import com.vrv.monitor.core.util.HttpUtil;
import lombok.Data;
import org.bitcoinj.core.ECKey;
import org.bitcoinj.params.MainNetParams;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigInteger;

@Data
public class Worker implements Runnable {
    private final static Logger logger = LoggerFactory.getLogger(Worker.class);

    public static MainNetParams mainNetParams = new MainNetParams();

    private BigInteger start;

    public synchronized BigInteger getSeed() {
        BigInteger seed = start.add(new BigInteger("1"));
        start = new BigInteger(seed.toString());
        return seed;
    }

    @Override
    public void run() {
        while (true) {
            Wallet wallet = new Wallet();
            BigInteger seed = getSeed();

            wallet.setSeed(seed);
            ECKey ecKey = ECKey.fromPrivate(seed);
            wallet.setPrivateKey(ecKey.getPrivateKeyAsWiF(mainNetParams));
            wallet.setPublicKey(ecKey.getPublicKeyAsHex());
            wallet.setAddress(ecKey.toAddress(mainNetParams).toString());
            String firstSeeResult = null;
            boolean flag = false;

            while (!flag) {
                try {
                    firstSeeResult = HttpUtil.getInstance().get("https://blockchain.info/q/addressfirstseen/" + wallet.getAddress());
                    String balanceResult = HttpUtil.getInstance().get("https://blockchain.info/q/addressbalance/" + wallet.getAddress());
                    wallet.setFirstSeen(firstSeeResult);
                    wallet.setBalance(balanceResult);
                    flag = true;
                } catch (Exception e) {
                    try {
                        Thread.sleep(5000);
                    } catch (InterruptedException e1) {
                        e1.printStackTrace();
                    }
                }
            }
            if (firstSeeResult.equals("0")) {
                logger.debug("钱包:" + JSONObject.toJSONString(wallet));
            } else {
                logger.warn("发现有效钱包:" + JSONObject.toJSONString(wallet));
            }
        }
    }
}
