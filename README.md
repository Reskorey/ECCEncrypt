# ECCEncrypt
java 实现椭圆曲线加密解密算法  (公钥加密，私钥解密)

两个文件夹：ECCencryptAndDecrypt1,ECCencryptAndDecrypt2 分别为两种实现方式



第一种ECCencryptAndDecrypt1 参照网上教程 https://stevenocean.github.io/2018/08/09/encrypt-and-decrypt-file-with-eth-key.html

第二种ECCencryptAndDecrypt2 参照以太坊[ethereum](https://github.com/ethereum)/**[ethereumj] (https://github.com/ethereum/ethereumj)** 项目。个人建议使用第二种方式。附带测试方法：https://www.tabnine.com/code/java/classes/org.ethereum.crypto.ECIESCoder 



第一种ECCencryptAndDecrypt1 

//需要添加 一下依赖
```json
 <dependency>
  <groupId>org.bouncycastle</groupId>
  <artifactId>bcprov-jdk15on</artifactId>
  <version>1.52</version>
 </dependency>
```



第二种需要添加依赖
```json
   <dependency>
   	<groupId>org.ethereum</groupId>
   	<artifactId>ethereumj-core</artifactId>
   	<version>1.5.0-RELEASE</version>
  </dependency>
```
