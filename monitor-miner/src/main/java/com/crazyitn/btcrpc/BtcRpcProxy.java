package com.crazyitn.btcrpc;


import com.alibaba.fastjson.JSONObject;
import wf.bitcoin.javabitcoindrpcclient.BitcoinJSONRPCClient;
import wf.bitcoin.javabitcoindrpcclient.BitcoindRpcClient;

public class BtcRpcProxy {
    public static void main(String[] args){
        try{
            BitcoinJSONRPCClient client = new BitcoinJSONRPCClient("http://dendi:123@localhost:8332/");

            System.out.println(client.getBalance());
            BitcoindRpcClient.Block block = client.getBlock(client.getBlockHash(500000));
            System.out.println(JSONObject.toJSONString(block));
//            System.out.println(client.getRawTransaction("2157b554dcfda405233906e461ee593875ae4b1b97615872db6a25130ecc1dd6"));

//            System.out.println(client.getRawTransaction("2157b554dcfda405233906e461ee593875ae4b1b97615872db6a25130ecc1dd6"));
//            System.out.println(client.getRawTransactionHex("2157b554dcfda405233906e461ee593875ae4b1b97615872db6a25130ecc1dd6"));
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }
}
