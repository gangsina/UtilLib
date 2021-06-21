package com.bentengwu.utillib;

import com.bentengwu.utillib.code.MD5;
import com.bentengwu.utillib.file.PathUtil;
import com.bentengwu.utillib.stream.StreamUtil;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Lists;
import com.bentengwu.utillib.date.DateUtil;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.*;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.*;

public class CommonUtils {
	private static Logger logger = LoggerFactory.getLogger(CommonUtils.class);
    public static final ObjectMapper mapper = new ObjectMapper();
    
	public final static char[] bigCaptchars = new char[] {'A','B','C', 'D', 'E', 'F', 'G',
		'H', 'I','J','K','L','M','N','P','Q','R','S','T','U','V','W','X','Y','Z' };
    public final static char[] nubmers = new char[] {'0','1','2', '3', '4', '5', '6',
		'7', '8','9' };
   
    static {
    	mapper.setDateFormat(new  SimpleDateFormat(DateUtil.LONG_DATE_FORMAT));
    }
    
    // 获取类实际路径
    public static String getClassAbsolutePath() {
        return PathUtil.getClassAbsolutePath("");
    }

    public static String getClassAbsolutePath(String suffix) {
        return PathUtil.getClassAbsolutePath(suffix);
    }


    
    /*
     * 取得stream中的内容，转化成字符串,utf-8
     */
    public static String getContentFromStream(InputStream in)
            throws IOException {
        return getContentFromStream(in, "UTF-8");
    }

    /*
     * 取得stream中的内容，转化成指定编码的字符串
     */
    public static String getContentFromStream(InputStream in, String encoding)
            throws IOException {
        String xml = new String(getBytesFromStream(in), encoding);
        return xml;
    }

    /*
     * 取得stream的内容，返回字节数组
     */
    public static byte[] getBytesFromStream(InputStream in) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try{
            byte[] buffer = new byte[1024];
            int bLen = 0;
            while ((bLen = in.read(buffer)) > 0) {
                baos.write(buffer, 0, bLen);
            }
            return baos.toByteArray();
        }finally {
            StreamUtil.close(in);
            StreamUtil.close(baos);
        }

    }

    /**
     * 把参数封装成对象
     *
     * @param object
     * @param parameterMap 欲封装的参数值
     * @param fieldMap     参数封装映射关系
     */
    public static void assembleParametersToObject(Object object,
                                                  Map<String, String> parameterMap, Map<String, String> fieldMap) {
        if (object == null || parameterMap == null) {
            return;
        }
        for (String parameter : fieldMap.keySet()) {
            try {
                BeanUtils.setProperty(object, fieldMap.get(parameter),
                        parameterMap.get(parameter.toLowerCase()));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static Map<String, String> convertMapKeyToLowerCase(
            Map<String, String> map) {
        Map<String, String> resultMap = new HashMap<String, String>();
        if (map != null) {
            for (String parameter : map.keySet()) {
                resultMap.put(parameter.toLowerCase(), map.get(parameter));
            }
        }
        return resultMap;
    }

    /**
     * @param source
     * @return
     */
    public static Map<String, String> parseParameter(String source) {
        Map<String, String> map = new HashMap<String, String>();
        if (StringUtils.isNotEmpty(source)) {
            String s[] = source.split("&");
            for (int i = 0; i < s.length; i++) {
                String s1[] = s[i].split("=");
                if (s1.length >= 2) {
                    map.put(s1[0].toLowerCase(), URLDecoder.decode(s1[1]));
                }
            }
        }
        return map;
    }

    public static String stripNonValidXMLCharacters(String in) {
        StringBuffer out = new StringBuffer(); // Used to hold the output.
        char current; // Used to reference the current character.

        if (in == null || ("".equals(in)))
            return ""; // vacancy test.
        for (int i = 0; i < in.length(); i++) {
            current = in.charAt(i); // NOTE: No IndexOutOfBoundsException caught
            // here; it should not happen.
            if ((current == 0x9) || (current == 0xA) || (current == 0xD)
                    || ((current >= 0x20) && (current <= 0xD7FF))
                    || ((current >= 0xE000) && (current <= 0xFFFD))
                    || ((current >= 0x10000) && (current <= 0x10FFFF)))
                out.append(current);
        }
        return out.toString().trim();
    }

    /*
     * 根据xml解析出所有节点与节点值的映射
     */
    public static HashMap<String, String> convertXmlToMap(String xml)
            throws Exception {
        HashMap<String, String> valueMap = new HashMap<String, String>();
        DocumentBuilderFactory dbf = null;
        DocumentBuilder db = null;
        BufferedReader br = null;
        Document doc = null;
        Element root = null;
        NodeList nodes = null;
        try {
            dbf = DocumentBuilderFactory.newInstance();
            db = dbf.newDocumentBuilder();
            br = new BufferedReader(new StringReader(xml));
            doc = db.parse(new InputSource(br));
            root = doc.getDocumentElement();
            nodes = root.getChildNodes();
            if (nodes != null) {
                for (int i = 0; i < nodes.getLength(); i++) {
                    Node node = nodes.item(i);
                    String nodeName = node.getNodeName().toLowerCase();
                    Node firstChild = node.getFirstChild();
                    String nodeValue = "";
                    if (firstChild != null) {
                        nodeValue = firstChild.getNodeValue();
                    }
                    valueMap.put(nodeName, nodeValue);
                }
            }
        } catch (Exception ex) {
            throw ex;
        } finally {
            nodes = null;
            root = null;
            doc = null;
            if (br != null) {
                try {
                    br.close();
                } catch (Exception e) {
                }
            }
            db = null;
            dbf = null;

        }
        return valueMap;
    }

    /**
     * 判断容器中是否包含某个对象。
     * 用toString()方法确定。
     *
     * @param list 容器
     * @param t    对象
     * @return 包含true  不包含false
     */
    public static boolean isContain(List list, Object t) {
        for (Object tt : list) {
            if (tt == null) {
                continue;
            }
            if (tt.toString().trim().equals(t.toString().trim())) {
                return true;
            }
        }
        return false;
    }
    
    
    public static List<Object> batchToChangeType(List<String> list, Class clazz) throws JsonParseException, JsonMappingException, IOException
    {
    	List<Object> oLists = Lists.newArrayList();
    	for(String s: list)
    	{
    		Object t = mapper.readValue(s, clazz);
    		oLists.add(t);
    	}
    	
    	return oLists;
    	
    }
    
    /**
     * 
     * @Title:        getRanCode 
     * @Description:  生成固定长度的随机字符或数字
     * @param:        @param type C:字符 	N:数字
     * @param:        @param len 长度
     * @param:        @return    
     * @return:       String    
     * @throws 
     * @author        cyb
     * @Date          2015-7-30 下午1:46:17
     */
    public static String getRanCode(String type,int len){
    	Random random = new Random();
    	
    	String result = "";
    	if(type.equals("C")){//字符
    		int bigCar = bigCaptchars.length - 1;
    		for (int i = 0; i < len; i++) {
    			result += bigCaptchars[random.nextInt(bigCar) + 1]+"";
    		}
    	}else if(type.equals("N")){//数字
    		int bigCar = nubmers.length - 1;
    		for (int i = 0; i < len; i++) {
    			result += nubmers[random.nextInt(bigCar) + 1]+"";
    		}
    	}
    	
    	//验证码测试写死
//    	if(len == 4){
//    		result = "1234";
//    	}
//    	
    	
    	return result;
    }

    /**
     * @description 执行结果输出方法
     * @createTime 2015年8月3日 下午2:31:52
     * @fileName RegisterController.java
     * @author yaojiamin
     */
    public static void printResult ( HttpServletResponse response , String message ) throws IOException {
        response.setContentType ( "text/html; charset=utf-8" );
        PrintWriter writer = response.getWriter ( );
        writer.println ( message );
        writer.flush ( );
    }

    /**
     * @description 获取客户端网络地址
     * @createTime 2015年8月3日 下午3:29:41
     * @fileName RegisterController.java
     * @author yaojiamin
     */
    public static String getRemoteAddr ( HttpServletRequest request ) {
        String ip = request.getHeader ( "x-forwarded-for" );
        if (ip != null) {
            ip = ip.split ( "," )[0];
        }
        if (ip == null || ip.length ( ) == 0 || "unknown".equalsIgnoreCase ( ip )) {
            ip = request.getHeader ( "Proxy-Client-IP" );
        }
        if (ip == null || ip.length ( ) == 0 || "unknown".equalsIgnoreCase ( ip )) {
            ip = request.getHeader ( "WL-Proxy-Client-IP" );
        }
        if (ip == null || ip.length ( ) == 0 || "unknown".equalsIgnoreCase ( ip )) {
            ip = request.getRemoteAddr ( );
        }
        return ip;
    }
    
    /**
	 * 产生一个PWD。 <br />
	 *  md5password = md5(userId+password+username+registertime+公掩)
	 *  
	 *  这里的password是明文的密码。
	 *  
	 *  md5password是存放在数据库中的密码。
	 * @date 2015-6-1 下午12:54:39
	 * @author <a href="xwh@ewppay.com">伟宏</a>
	 * @param memberId
	 *            用户编号
	 * @param username
	 *            用户名
	 * @param createDate
	 *            注册时间.注册时间精确到天
	 * @param password
	 *            密码密文(app传过来已经先md5加密的)
	 * @return 密码存储密文
	 * @throws Exception 
	 * @since 1.0
	 */
	public static String generatePwd(String memberId, String username,
			Date createDate, String password) throws Exception {
		StringBuilder before = new StringBuilder();
		before.append(memberId);
		before.append(username);
		before.append(DateUtil.convertDate2String(createDate, "yyyy-MM-dd"));
		before.append(password);
		return MD5.md5(before.toString(), "utf-8");
	}
	



    /**
     * 
    * @Title: StringFillLeftZero
    * @Description: 左补0
    * @param @param str
    * @param @param len
    * @param @return    
    * @return String    返回类型
    * @throws
     */
    public static String StringFillLeftZero(String str, int len) {
		if (str.length() < len) {
			StringBuffer sb = new StringBuffer(len);
			for (int i = 0; i < len - str.length(); i++)
				sb.append('0');
			sb.append(str);
			return new String(sb);
		} else
			return str;
	}
    

}
