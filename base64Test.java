import com.sun.org.apache.xml.internal.security.utils.Base64;

import java.io.UnsupportedEncodingException;
public class base64Test {
    public static void main(String[] args) throws Exception{
        String s="123";
        //一个中文为3个字节
        String sp=Base64.encode(s.getBytes());
        System.out.println(sp);
        byte[] sol=Base64.decode(sp.getBytes());
        System.out.println(new String(sol));
        //注意不要用toString，toString是调用了这个object对象的类的toString方法。一般是返回这么一个String：[class name]@[hashCode]
        //toString()用于对象打印
        //new String()用于编码解码
    }
}
