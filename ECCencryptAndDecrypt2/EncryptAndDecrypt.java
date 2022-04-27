package com.zdww.biyi.ecies;

import org.ethereum.crypto.ECIESCoder;
import org.spongycastle.util.encoders.Hex;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class EncryptAndDecrypt {

    //对数据进行非对称加密
    public String encrypt(String source, String publicKey) {
        try {
            //加密前先把原始数据 转为bytes
            byte[] preEncryBytes = source.getBytes(StandardCharsets.UTF_8);
            //将 publicKey 转为 ECKey 类型的 pub
            ECKey pub = ECKey.fromPublicOnly(Hex.decode(publicKey));
            System.out.println("加密前字符串： " + new String(preEncryBytes));

            //加密后 bytes cipherText
            byte[] cipherText = ECIESCoder.encryptSimple(pub.getPubKeyPoint(), preEncryBytes);

            //将 加密后cipherText 进行 base64 编码
            byte[] encoded = Base64.getEncoder().encode(cipherText);
            //编码后转String
            System.out.println(" 加密之后 encrypt base64 后转string: " + new String(encoded));
            String encryptStr = new String(encoded);
            return encryptStr;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    //非对称加密后数据解密
    public String decrypt(String source, String privateKey) {
        try {
            // 加密数据先转为 bytes， 再base64 decode
            byte[] preDecryptBytes = Base64.getDecoder().decode(source.getBytes(StandardCharsets.UTF_8));
            //私钥转换为ECkey 类型
            ECKey priv = ECKey.fromPrivate(Hex.decode(privateKey));
            //解密
            byte[] decryptResult = ECIESCoder.decryptSimple(priv.getPrivKey(), preDecryptBytes);
            String deStr = new String(decryptResult);
            System.out.println("解密后字符串 " + deStr);
            return deStr;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }


}
