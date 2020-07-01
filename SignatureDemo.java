package rsaDemo;

import com.sun.org.apache.xml.internal.security.utils.Base64;

import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;

/**
 * @ClassName SignatureDemo
 * @Description TODO
 * @Author MR.Zhou
 * @Date 2020/6/26 1:25
 * @Version 1.0
 * 数字签名
 **/
public class SignatureDemo {
    public static void main(String[] args) throws Exception {
        String a="123";
        String algorithm="sha256withrsa";
        PublicKey publicKey=RSAdemo.getPublicKey("a.pub","RSA");
        PrivateKey privateKey=RSAdemo.getPrivateKey("a.pri","RSA");
        //获取数字签名
        String signatureData = getSignature(a, algorithm, privateKey);
        System.out.println(signatureData);

        //校验签名
        boolean b= verifySignature(a, algorithm, publicKey, signatureData);
        System.out.println(b);
    }
    /**
     * @Author Visional Flame
     * @Date 1:46 2020/6/26
     * @Param [a, algorithm, publicKey, signatureData]
     * @return boolean
     * 校验签名
     **/
    public static boolean verifySignature(String input, String algorithm, PublicKey publicKey, String signatureData) throws  Exception{
        Signature signature=Signature.getInstance(algorithm);
        //初始化校验
        signature.initVerify(publicKey);
        //传入原文
        signature.update(input.getBytes());
        //校验数据
        return signature.verify(Base64.decode(signatureData));

    }

    /**
     * @Author Visional Flame
     * @Date 1:36 2020/6/26
     * @Param [a, algorithm, privateKey]
     * @return java.lang.String
     * 生成数字签名
     **/
    public static String getSignature(String input, String algorithm, PrivateKey privateKey)throws Exception {
        //获取签名对象
        Signature signature=Signature.getInstance(algorithm);
        //初始化签名
        signature.initSign(privateKey);
        //传入原文
        signature.update(input.getBytes());
        //开始签名
        byte[] sign = signature.sign();
        //base64编码
        String encode = Base64.encode(sign);
        return encode;
    }
}
