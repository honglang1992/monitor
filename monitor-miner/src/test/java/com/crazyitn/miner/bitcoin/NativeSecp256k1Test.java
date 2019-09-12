package com.crazyitn.miner.bitcoin;
import org.junit.Test;

public class NativeSecp256k1Test {

    @Test
    public void testVerify() throws NativeSecp256k1Util.AssertFailException {
        NativeSecp256k1 nativeSecp256k1 = new NativeSecp256k1();

        String a = "KxPDg3qyTCaLUdgnNyYF2D9E31MBC5LYV4gajQENRL6t7X92FVpQ";

        String result = new String(nativeSecp256k1.computePubkey(a.getBytes())) ;

        System.out.println(result);
    }
}

