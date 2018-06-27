package com.crazyitn.miner;


import org.bitcoinj.core.ECKey;
import org.bitcoinj.params.MainNetParams;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigInteger;

public class WalletTest {

    private Logger logger = LoggerFactory.getLogger(WalletTest.class);
    @Test
    public void testECKey(){
        BigInteger bigInteger = BigInteger.valueOf(1231006505);
        ECKey ecKey = ECKey.fromPrivate(bigInteger);

        logger.info("seed:{} privateKey:{} publicKey:{} address:{}",
                bigInteger,ecKey.getPrivateKeyAsWiF(new MainNetParams()),ecKey.getPublicKeyAsHex(),ecKey.toAddress(new MainNetParams()));

        //地址的初次交易时间: https://blockchain.info/q/addressfirstseen/17FeULm5XGaeDS6BZK25GmQQKcqX9qWwXb

        //地址的余额:https://blockchain.info/q/addressbalance/1A1zP1eP5QGefi2DMPTfTL5SLmv7DivfNa

    }
}