
import java.io.IOException;
import java.util.Map;

import com.google.common.collect.Maps;

import org.apache.commons.codec.digest.DigestUtils;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author 伟宏
 */
public class Test {
    public static void main(String[] args) throws IOException {
		String password  = DigestUtils.md5Hex("123456");
		System.out.println(password);
//        TomcatServerXml tomcat = TomcatServerXml.getInstance("D:\\uninstall\\tomcat\\conf\\server.xml");
//    	Rd.p(Math.pow(10, 4)-1);
//        for(int i=0;i<1000;i++){
//         Rd.p(StrUtils.getRandomNumber(4));
//        }
//    	File file = new File("E:\\tmp\\1.jpeg");
//    	byte[] bytes = FileUtils.readFileToByteArray(file);
//    	String hex = EncodeUtils.hexEncode(bytes);
//    	System.out.println(hex);

//    	Long id = 100L;
//    	long id_ = 100L;
//
//    	System.out.println(id==id_);
		Map map = Maps.newHashMap();

		map.put("1",1);
		System.out.println(map.toString());

	}
}
