import com.sun.org.apache.xml.internal.security.utils.Base64;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


/**
 * @ClassName DigestDemo
 * @Description TODO
 * @Author MR.Zhou
 * @Date 2020/6/14 0:15
 * @Version 1.0
 *
 * 消息摘要方法，防止篡改
 * 常见加密算法，md5，sha1，sha256，sha512
 **/

public class DigestDemo {
    public static void main(String[] args) throws  Exception{
        //原文
        String input="aa";
        String algotithm = "MD5";
        //4124bc0a9335c27f086f24ba207a4912
        //创建消息摘要对象
        System.out.println("MD5:");
        DGencode(input, algotithm);
        String a1="SHA-1";
        System.out.println("sha1");
        DGencode(input,a1);
        System.out.println("sha256");
        String a2="SHA-256";
        DGencode(input,a2);
        System.out.println("sha512");
        String a3="SHA-512";
        DGencode(input,a3);
    }
    /**
     * @Author Visional Flame
     * @Date 14:28 2020/6/17
     * @Param [input, algotithm]
     * @return void
     **/
    public static void DGencode(String input, String algotithm) throws NoSuchAlgorithmException {
        MessageDigest digest=MessageDigest.getInstance(algotithm);
        //执行算法
        byte[] encode=digest.digest(input.getBytes());
        StringBuilder sb=new StringBuilder();
        //base64转码
        System.out.println(Base64.encode(encode));
        //手动转换
        for (byte b:
             encode) {
            //把密文转换成16进制
            String s=Integer.toHexString(b&0xff);
            //密文长度为一个字节，需要在高位补0
            if (s.length() == 1) {
                s="0"+s;
            }
            sb.append(s);
        }
        System.out.println(sb.toString());
    }
}
