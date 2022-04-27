package com.zdww.contract.sdk.contractservice;

import org.junit.Test;

import java.util.Map;

public class ECCutilsTest {


    @Test
    public void testECCencry(){
        //待加密字符串
        String source = "{\"name\":\"yz\"}";
        //生成一个公私钥对用于测试
        Map<String, String> keyPair = ContractOperate.createKeyPair();
        String publicKey = "045f44f938043d15c7620ec811cd80d93de80667946e5baab92e7bcad2e9b188fbdba32632e54840849ad5b74a1547ede5ffa35b1ae741dd45bdcda0ee55716eb0";
        System.out.println("publicKey : " + publicKey);
        System.out.println("publicKey : " + publicKey.length());
        String privateKey = "759a0fca3c246b4c1017db582bf67f8fef586d0dfc984398769d16f44e979615";
        System.out.println("privateKey : " + privateKey);
//        String address = keyPair.get("address");
//        System.out.println("address : " + address);
        String enCoded = ECCutils.encrypt(source, publicKey);

        String deCoded = ECCutils.decrypt(enCoded,privateKey);
    }

    @Test
    public void test(){
      String publicKey = "04d1921e01c953d5da4ebb923ab4a8ef64436774ae6d7613fd77840aee2ba91e9e67e44e6b7911062b8058bd60a26dcb02c09b46ea0726a1d106d5d55f6850d875";
        System.out.println(publicKey.length());
        String source = "{\"name\":\"name contect 20220425 1615\"}";
        //Map<String, String> keyPair = ContractOperate.createKeyPair();
        ECCutils.encrypt(source, publicKey);

    }

    @Test
    public void TestEncryDataByPublicKey(){
        //待加密字符串
        String source = "{\"name\":\"yz\"}";
        String publicKey = "04d1921e01c953d5da4ebb923ab4a8ef64436774ae6d7613fd77840aee2ba91e9e67e44e6b7911062b8058bd60a26dcb02c09b46ea0726a1d106d5d55f6850d875";
        String enCoded = ECCutils.encrypt(source, publicKey);
    }

    @Test
    public void testDE(){

        String enCoded = "BIJgyzC3Csu+310TW1a/FbwZXT9eXZRwoz6tpWLqXYDNd5o3Pg3oNk6mGjwit3F33ZnqFsWNWk8waTD+ivyPfGoXaAcPGCGFUrfeciDBMGA7ucA9GRnk8EEqMAfmphXOpuVHcgXIL+4OnjBMa7W0lQSGnPZd2d0EVW1NSWiBO1c=";
        String enc = "BMqMQriYHruwkcNaF19LStOq4iDfKynk3E1xJ98NwdooW4dzXsfuEGTHlBfNDEXkknyJ8rF5vzyEP/dguVGj81FLXX7zxGjJn1udCcl1Cj7m9Vhu5bofdcmIOpMwqLbKIDg=";
        String privateKey = "759a0fca3c246b4c1017db582bf67f8fef586d0dfc984398769d16f44e979615";
        String deCoded = ECCutils.decrypt(enCoded,privateKey);

    }

}
