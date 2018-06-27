package com.crazyitn.miner;

import lombok.Data;
import java.math.BigInteger;

@Data
public class Wallet {

    private BigInteger seed;

    private String privateKey;

    private String publicKey;

    private String address;

    private String firstSeen;

    private String balance;

}
