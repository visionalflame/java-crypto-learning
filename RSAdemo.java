package rsaDemo;
import com.sun.org.apache.bcel.internal.generic.ALOAD;
import com.sun.org.apache.xml.internal.security.utils.Base64;
import org.apache.commons.io.FileUtils;
import org.omg.PortableInterceptor.INACTIVE;

import javax.crypto.Cipher;
import java.io.File;
import java.nio.charset.Charset;
import java.security.*;
import java.security.spec.KeySpec;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

/**
 * @ClassName RSAdemo
 * @Description TODO
 * @Author MR.Zhou
 * @Date 2020/6/22 21:45
 * @Version 1.0
 **/
public class RSAdemo {
    public static void main(String[] args) throws  Exception{
        String input="Codesky";
        //创建密钥对
        // KeyPairGenerator密钥对生成器对象
        String algorithm="RSA";

        //生成密钥对保存在本地文件
        generateKeyToFile(algorithm,"a.pub","a.pri");
        //读取私钥key生成密文
        PrivateKey privateKeyString=getPrivateKey("a.pri",algorithm);
        String rsaencode= RSAencrypt(algorithm,input,privateKeyString);
        System.out.println(rsaencode);
        //读取公钥key解密
        PublicKey publicKey=getPublicKey("a.pub",algorithm);
        String rsadecode= RSAdecrypt(algorithm,rsaencode,publicKey);
        System.out.println(rsadecode);
    }
    /**
     * @Author Visional Flame
     * @Date 0:28 2020/6/23
     * @Param [pubPath, algorithm]
     * @return java.security.PublicKey
     **/
    public static PublicKey getPublicKey(String pubPath, String algorithm) throws Exception{
        String publicKeyString = FileUtils.readFileToString(new File(pubPath), Charset.defaultCharset());
        KeyFactory keyFactory = KeyFactory.getInstance(algorithm);
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(Base64.decode(publicKeyString));
        return keyFactory.generatePublic(keySpec);
    }


    /**
     * @Author Visional Flame
     * @Date 0:18 2020/6/23
     * @Param [priPath, algorithm]
     * @return java.security.PrivateKey
     **/
    public static PrivateKey getPrivateKey(String priPath, String algorithm) throws Exception{
        String privateKeyString = FileUtils.readFileToString(new File(priPath), Charset.defaultCharset());
        //创建Key工厂
        KeyFactory keyFactory=KeyFactory.getInstance(algorithm);
        //创建私钥key规则
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(Base64.decode(privateKeyString));
        return keyFactory.generatePrivate(keySpec);
    }

    /**
     * @Author Visional Flame
     * @Date 23:01 2020/6/22
     * @Param [algorithm, publicPath, PrivatePath]
     * @return void
     **/
    public static void generateKeyToFile(String algorithm,String publicPath,String PrivatePath)throws Exception{
        KeyPairGenerator keyPairGenerator= KeyPairGenerator.getInstance(algorithm);
        //生成密钥对
        KeyPair keyPair=keyPairGenerator.generateKeyPair();
        //生成公钥私钥
        PrivateKey privateKey=keyPair.getPrivate();
        PublicKey publicKey=keyPair.getPublic();
        //获取私钥字节数组
        byte[] privateKeyEncoded=privateKey.getEncoded();
        //获取公钥字节数组
        byte[] publicKeyEncode=publicKey.getEncoded();
        //base64编码
        String privateKeyencode = Base64.encode(privateKeyEncoded);
        String publicKeyencode= Base64.encode(publicKeyEncode);
        //打印公钥私钥
        /*System.out.println("privateKey      "+privateKeyencode);
        System.out.println("publicKey      "+publicKeyencode);
*/      FileUtils.writeStringToFile(new File(publicPath),publicKeyencode, Charset.forName("UTF-8"));
        FileUtils.writeStringToFile(new File(PrivatePath),privateKeyencode, Charset.forName("UTF-8"));
    };
    /**
     * @Author Visional Flame
     * @Date 23:48 2020/6/22
     * @Param [algorithm, input, privatekey]
     * @return void
     **/
    public static String RSAencrypt(String algorithm, String input, Key privatekey)throws Exception{
        //创建加密对象
        Cipher cipher = Cipher.getInstance(algorithm);
        //对加密进行初始化
        // 1.加密模式
        // 2.加密参数：公钥或者私钥
        //例如使用私钥加密
        cipher.init(Cipher.ENCRYPT_MODE,privatekey);
        //使用私钥加密
        byte[] bytes = cipher.doFinal(input.getBytes());
        return Base64.encode(bytes);
    }
    /**
     * @Author Visional Flame
     * @Date 0:04 2020/6/23
     * @Param [algorithm, encode, publicKey]
     * @return java.lang.String
     **/
    public static String RSAdecrypt(String algorithm, String encode, Key publicKey)throws Exception{
        Cipher cipher=Cipher.getInstance(algorithm);
        //使用公钥解密
        cipher.init(Cipher.DECRYPT_MODE,publicKey);
        //使用base64转码
        byte[] decode = Base64.decode(encode);

        byte[] bytes1 = cipher.doFinal(decode);
        return new String(bytes1);
    }

}
