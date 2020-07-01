import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import com.sun.org.apache.xml.internal.security.utils.Base64;

import java.security.spec.AlgorithmParameterSpec;

public class AesDesDemo {
    public static void main(String[] args) throws Exception{
        //明文
        String input="helloworld";
        //定义key，密钥必须为8个字节
        String key="12345678";//AES为16位，DES为8位
        //算法
        //transfomation 为“DES/ECB/PKCS5Padding”表示ECB填充模式,PKCS5Padding加密模式
        String transfomation="DES";
        //加密类型
        String algorithm="DES";
        //原文输出
        System.out.println("input:"+input);
        //创建加密对象
        String encryptDES=DESencrypt(input,key,transfomation,algorithm);
        System.out.println("加密："+encryptDES);

        String s=decryptDES(encryptDES,key,transfomation,algorithm);
        System.out.println("解密："+s);
    }
    /**
     * @Author Visional Flame
     * @Date 17:15 2020/6/12
     * @Param [input, key, transfomation, algorithm]
     * @return java.lang.String
     **/
    public static String DESencrypt(String input,String key,String transfomation,String algorithm)throws Exception{
        Cipher cipher= Cipher.getInstance(transfomation);
        //创建加密规则
        //参数1：key的字节
        //参数2：加密类型
        SecretKeySpec secretKeySpec= new SecretKeySpec(key.getBytes(),transfomation);
        //创建iv向量，使用到CBC加密模式
        //iv必须为8个字节
        IvParameterSpec iv=new IvParameterSpec("12345678".getBytes());
        //进行加密初始化
        //第一个参数表示模式
        //第二个参数：加密规则
        cipher.init(Cipher.ENCRYPT_MODE,secretKeySpec);
        //调用加密方法
        //参数为原文字节数组
        byte[] bytes=cipher.doFinal(input.getBytes());
        //打印密文
        //如果直接打印密文会出现乱码
        //编码表上找不到对应字符
        //创建base64对象
        String encode =Base64.encode(bytes);
        return encode;
    }
    /**
     * @Author Visional Flame
     * @Date 17:14 2020/6/12
     * @Param [encryptDES, key, transformation, algorithm]
     * @return java.lang.String
     **/
    private static String decryptDES(String encryptDES,String key,String transformation,String algorithm)throws Exception{
        Cipher cipher = Cipher.getInstance(transformation);
        SecretKeySpec secretKeySpec = new SecretKeySpec(key.getBytes(),algorithm);
        //创建iv 向量
        IvParameterSpec iv= new IvParameterSpec(key.getBytes());
        //Cipher.DECRYPT_MODE表示解密
        //解密规则
        cipher.init(Cipher.DECRYPT_MODE,secretKeySpec);
        //解密，传入密文
        byte[] bytes=cipher.doFinal(Base64.decode(encryptDES));
        return new String(bytes);
    }
}

