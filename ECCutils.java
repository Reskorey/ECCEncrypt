package com.zdww.contract.sdk.contractservice;

import org.bouncycastle.crypto.params.ECDomainParameters;
import org.bouncycastle.crypto.params.ECPublicKeyParameters;
import org.bouncycastle.jcajce.provider.asymmetric.ec.BCECPrivateKey;
import org.bouncycastle.jcajce.provider.asymmetric.ec.BCECPublicKey;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.jce.spec.ECNamedCurveSpec;
import org.bouncycastle.jce.spec.IESParameterSpec;
import org.bouncycastle.math.ec.ECCurve;
import org.bouncycastle.math.ec.ECFieldElement;
import org.bouncycastle.math.ec.custom.sec.SecP256K1Curve;
import org.bouncycastle.math.ec.custom.sec.SecP256K1FieldElement;
import org.bouncycastle.math.ec.custom.sec.SecP256K1Point;

import javax.crypto.Cipher;
import java.math.BigInteger;
import java.security.Security;
import java.security.spec.ECFieldFp;
import java.security.spec.ECPoint;
import java.security.spec.ECPrivateKeySpec;
import java.security.spec.EllipticCurve;
import java.util.Base64;

public class ECCutils {

    public static final BigInteger pointGPre = new BigInteger("79be667ef9dcbbac55a06295ce870b07029bfcdb2dce28d959f2815b16f81798", 16);
    public static final BigInteger pointGPost = new BigInteger("483ada7726a3c4655da4fbfc0e1108a8fd17b448a68554199c47d08ffb10d4b8", 16);
    public static final BigInteger factorN = new BigInteger("fffffffffffffffffffffffffffffffebaaedce6af48a03bbfd25e8cd0364141", 16);
    public static final BigInteger fieldP = new BigInteger("fffffffffffffffffffffffffffffffffffffffffffffffffffffffefffffc2f", 16);

    public void ECCutils(){
        Security.addProvider(new org.bouncycastle.jce.provider
                .BouncyCastleProvider());
    }

    public static String encrypt(String source, String publicKey){
        try {
            BCECPublicKey publicKeySelf = getPublicSelf(publicKey);
            IESParameterSpec  iesParams = new IESParameterSpec(null, null, 64);
            // begin encrypt
            Cipher cipher = Cipher.getInstance("ECIES", "BC");
            cipher.init(Cipher.ENCRYPT_MODE, publicKeySelf, iesParams);
            System.out.println("加密前数据 source : " + source);
            //机密后的bytes
            byte[] encryptBytes =  cipher.doFinal(source.getBytes());
            //Base64编码
            byte[] encoded =  Base64.getEncoder().encode(encryptBytes);
            System.out.println(" 加密之后 encrypt base64 后转string: " + new String(encoded));
            return new String(encoded);
        }
        catch (Exception e){
            System.out.println(e);
            return null;
        }
    }

    public static String decrypt(String encodedStr, String privateKey){
        try {
            Security.addProvider(new BouncyCastleProvider());
            EllipticCurve ellipticCurve = new EllipticCurve(new ECFieldFp(fieldP), new BigInteger("0"), new BigInteger("7"));
            ECPoint pointG = new ECPoint(pointGPre, pointGPost);
            ECNamedCurveSpec namedCurveSpec = new ECNamedCurveSpec("secp256k1", ellipticCurve, pointG, factorN);
            BigInteger privateKeyValue = new BigInteger(privateKey, 16);
            ECPrivateKeySpec privateKeySpec = new ECPrivateKeySpec(privateKeyValue, namedCurveSpec);
            BCECPrivateKey privateKeySelf = new BCECPrivateKey("ECDSA", privateKeySpec, BouncyCastleProvider.CONFIGURATION);
            // begin decrypt
            IESParameterSpec  iesParams = new IESParameterSpec(null, null, 64);
            Cipher cipher = Cipher.getInstance("ECIES", "BC");
            cipher.init(Cipher.DECRYPT_MODE, privateKeySelf, iesParams);
            //base64 decode
            byte[] preEncrypt = Base64.getDecoder().decode(encodedStr.getBytes());
            byte[] deEncrppt = cipher.doFinal(preEncrypt);
            System.out.println("解密之后的数据decrypt :" + new String(deEncrppt));
            return new String(deEncrppt);
        } catch (Exception e){
            System.out.println(e);
            return null;
        }
    }


    public static BCECPublicKey getPublicSelf(String publicKey) {
        try {
        Security.addProvider(new BouncyCastleProvider());
        // Encrypt with public key
        String prePublicKeyStr = publicKey.substring(2, 66);
        String postPublicKeyStr = publicKey.substring(66);

        EllipticCurve ellipticCurve = new EllipticCurve(new ECFieldFp(fieldP), new BigInteger("0"), new BigInteger("7"));
        ECPoint pointG = new ECPoint(pointGPre, pointGPost);
        ECNamedCurveSpec namedCurveSpec = new ECNamedCurveSpec("secp256k1", ellipticCurve, pointG, factorN);

        ECCurve secP256K1Curve = new SecP256K1Curve();
        ECFieldElement e1 = new SecP256K1FieldElement(new BigInteger(prePublicKeyStr, 16));
        ECFieldElement e2 =  new SecP256K1FieldElement(new BigInteger(postPublicKeyStr, 16));
        SecP256K1Point secP256K1Point = new SecP256K1Point(secP256K1Curve, e1, e2);
        SecP256K1Point secP256K1PointG = new SecP256K1Point(secP256K1Curve, new SecP256K1FieldElement(pointGPre), new SecP256K1FieldElement(pointGPost));

        ECDomainParameters domainParameters = new ECDomainParameters(secP256K1Curve, secP256K1PointG, factorN);
        ECPublicKeyParameters publicKeyParameters = new ECPublicKeyParameters(secP256K1Point, domainParameters);
        BCECPublicKey publicKeySelf = new BCECPublicKey("ECDSA", publicKeyParameters, namedCurveSpec, BouncyCastleProvider.CONFIGURATION);
        return  publicKeySelf;
        } catch (Exception e) {
            System.out.println(e);
            return null;
        }
    }
}
