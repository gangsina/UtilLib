package com.bentengwu.utillib.String;

import java.nio.charset.Charset;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.bentengwu.utillib.code.EncodeUtils;
import com.bentengwu.utillib.file.PathUtil;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.RandomUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;

import freemarker.template.utility.StringUtil;
/**
 *<br /> 请使用 UtilLib中的StrUtil.
 *@类名称：StrHelper.java
 *@文件路径：com.webframework_springmvc.core.utils
 *@内容摘要：
 *@author：email: <a href="bentengwu@163.com"> 伟宏 </a> 
 *@完成日期：
 *@Date 2013-11-14 下午1:28:32 
 *@comments:
 */
public class StrUtils {

	/**
	 * 是否为空字符串
	 *<br />
	 *@date 2013-11-14 下午1:38:47
	 *@author <a href="bentengwu@163.com">伟宏</a>
	 *@param str	被判断的字符串
	 *@return	
	 *			<br />	isEmpty("")		true
	 *			<br />  isEmpty(null) 	true
	 *			<br />  isEmpty("  ") 	true
	 */
	public static boolean isEmpty(String str) {
		return str == null || str.trim().length() == 0;
	}

	/**
	 * 是否不为空的字符串
	 *<br />
	 *@date 2013-11-14 下午1:40:06
	 *@author <a href="bentengwu@163.com">伟宏</a>
	 *@param str	被判断的字符串
	 *@return	<br />	isNotEmpty("null")  false
	 *			<br />	isNotEmpty("")		false
	 *			<br />  isNotEmpty(null) 	false
	 */
	public static boolean isNotEmpty(String str) {
		return !isEmpty(str);
	}

	public static String arrayObjToString(Collection collection) {
		return arrayObjToString(collection.toArray(), ",");
	}

	/**
	 *@author thender email: bentengwu@163.com
	 *@date 2019/6/28 12:12
	 *@param paramArrayOfString
	 *@param paramString
	 *@return java.lang.String
	 **/
	public static String arrayObjToString(Object[] paramArrayOfString,String paramString) {
		if ((paramArrayOfString == null) || (paramArrayOfString.length == 0)) {
			return "";
		}
		if (paramString == null) {
			paramString = "|";
		}
		StringBuilder localStringBuffer = new StringBuilder("");
		for(Object o : paramArrayOfString ){
			if(o!=null && isNotEmpty(o.toString())){
				localStringBuffer.append(o).append(paramString);
			}
		}
		if(localStringBuffer.toString().endsWith(paramString)){
			return localStringBuffer.toString().substring(0, localStringBuffer.toString().lastIndexOf(paramString));
		}
		return localStringBuffer.toString();
	}

	/**
	 * 将数组转化为字符串.
	 *<br />
	 *@date 2013-11-14 下午1:41:56
	 *@author <a href="bentengwu@163.com">伟宏</a>
	 *@param paramArrayOfString 字符串数组.
	 *@return		
	 *<br />		arrayToString({"1","2","3"}) 	====> "1|2|3"
	 *	<br />		arrayToString(null)				====> ""
	 */
	public static String arrayToString(String[] paramArrayOfString) {
		return arrayToString(paramArrayOfString, "|");
	}
	/**
	 * 将数组转化为字符串.
	 *<br />
	 *@date 2013-11-14 下午1:43:56
	 *@author <a href="bentengwu@163.com">伟宏</a>
	 *@param paramArrayOfString	字符串数组
	 *@param paramString		分割符
	 *@return	
	 *<br />			arrayToString({"1","2","3"},",")	====> "1,2,3"
	 *<br />			arrayToString(null,",")				====> "";
	 *<br />			arrayToString({"1","2","3"},null)	====> "";
	 */
	public static String arrayToString(String[] paramArrayOfString,
			String paramString) {
		return arrayObjToString(paramArrayOfString, paramString);
	}

        
	/**
	 * 判断字符串是否为数字函数，正则表达式
	 *<br />
	 *@date 2013-11-14 下午1:48:03
	 *@author <a href="bentengwu@163.com">伟宏</a>
	 *@param strNumber	字符串
	 *@return
	 *<br />		isDigitalChar(null)		====>		false
	 *<br />		isDigitalChar("")		====>		false
	 *<br />		isDigitalChar("12039")	====>		true
	 *<br />		isDigitalChar("1aa23")	====>		false	
	 */
	public static boolean isDigitalChar(String strNumber) {
		if (isEmpty(strNumber))
			return false;

		Pattern p = Pattern.compile("[^0-9]");
		Matcher m = p.matcher(strNumber);
		return !m.find();
	}
	
	/**
	 * 获取对象的字符串值.
	 *<br />
	 *@date 2013-11-14 下午1:50:43
	 *@author <a href="bentengwu@163.com">伟宏</a>
	 *@param strValue			对象
	 *@param replaceIfNull		默认值
	 *@return			
	 *<br />		getString(null)	return	""
	 *<br />		getString("")	return	""
	 *<br />		getString(t)	return t.toString().
	 */
	public static String getString(Object strValue) {
		return getString(strValue, "");
	}
	/**
	 * 获取对象的字符串值.
	 *<br />
	 *@date 2013-11-14 下午1:50:43
	 *@author <a href="bentengwu@163.com">伟宏</a>
	 *@param strValue			对象
	 *@param replaceIfNull		默认值
	 *@return			
	 *<br />		getString(null,"asdf")	return	"adsf"
	 *<br />		getString("","asdf")	return	""
	 *<br />		getString(null,null)	return	null
	 *<br />		getString(t,"asdf")		return t.toString().
	 */
	public static String getString(Object strValue, String replaceIfNull) {
		try {
			if (strValue == null)
				return replaceIfNull;
			else
				return strValue.toString();
		} catch (Exception ex) {
			return "";
		}
	}
	/**
	 * 推荐使用apache的字符串工具类中的方法.
	 *<br />
	 *@date 2013-11-14 下午1:55:20
	 *@author <a href="bentengwu@163.com">伟宏</a>
	 *@param str		需要被处理的字符串
	 *@param len		返回的字符串长度
	 *@param ch			补白的字符
	 *@param fillOnLeft	是否在左进行补白. 
	 *@return
	 *	处理字符串str
	 *<br /> 如果字符串长度大于len.则直接返回.
	 *<br /> 如果字符串null,则抛出异常
	 *<br /> 如果字符串长度小于len, 则在左边或者右边进行补白.
	 */
	public static String expandStr(String str, int len, char ch,
			boolean fillOnLeft) {
		if(str==null)
			return null;
		int nLen = str.length();
		if (len <= nLen)
			return str;
		String sRet = str;
		for (int i = 0; i < len - nLen; i++)
			sRet = fillOnLeft ? String.valueOf(ch) + String.valueOf(sRet)
					: String.valueOf(sRet) + String.valueOf(ch);

		return sRet;
	}
	
	/**
	 * 设置字符串的结尾符.
	 *<br />
	 *@date 2013-11-14 下午2:06:32
	 *@author <a href="bentengwu@163.com">伟宏</a>
	 *@param str	字符串
	 *@param ch		结尾的字符串.
	 *@return	
	 *<br />		设置结尾符
	 *<br />    setEndswith("asdfasdf","ender") return "asdfasdfender"
	 *<br />    setEndswith("asdfasdfender","ender") return "asdfasdfender"
	 *<br />    setEndswith(null,"ender") return null
	 */
	public static String setEndswith(String str, String ch) {
		if (str == null)
			return null;
		if (!str.endsWith(ch))
			return str + ch;
		else
			return str;
	}

	/**
	 * @param ch 特殊字符串
	 * @return
	 * 	trimStart("///asdfa/asdfa/asdf","/") return asdfa/asdfa/asdf
	 * 	trimStart("/asdfa/asdfa/asdf","/") return asdfa/asdfa/asdf
	 * 	trimStart("/asdfa/asdfa/asdf",null) return /asdfa/asdfa/asdf
	 * 	trimStart(null,"asdf") return null
	 */
	public static final String trimStart(final String str,final String ch) {
		if (ch == null || str==null) {
			return str;
		}

		char[] charArray = str.toCharArray();
		char[] chArray = ch.toCharArray();
		int startIndex = 0;
		boolean _break = false;
		for (int i = 0; i < charArray.length; i=i+chArray.length) {
			startIndex = i;
			for (int j = 0; j < chArray.length; j++) {
				if (chArray[j] != charArray[i + j]) {
					_break = true;
					break;
				}
			}
			if (_break) {
				break;
			}
		}

		return StringUtils.substring(str, startIndex);
	}


	/**
	 * 替换字符串
	 *<br />
	 *@date 2013-11-14 下午2:10:35
	 *@author <a href="bentengwu@163.com">伟宏</a>
	 *@param strSrc		字符串
	 *@param strOld		需要被换掉的旧字符串
	 *@param strNew		新字符串
	 *@return				
	 *<br />
	 *		replaceStr(null,"asdf","fdsa")		return 	null
	 *<br />
	 *		replaceStr("","asdf","fdsa")		return 	""
	 *<br />
	 *		replaceStr("aaa","asdf","fdsa")		return 	"aaa"
	 *<br />
	 *		replaceStr("asdfaaa","asdf","xxx")		return 	"xxxaaa"
	 *<br />
	 */
	public static String replaceStr(String strSrc, String strOld, String strNew) {
		if (strSrc == null)
			return null;
		char srcBuff[] = strSrc.toCharArray();
		int nSrcLen = srcBuff.length;
		if (nSrcLen == 0)
			return "";
		char oldStrBuff[] = strOld.toCharArray();
		int nOldStrLen = oldStrBuff.length;
		if (nOldStrLen == 0 || nOldStrLen > nSrcLen)
			return strSrc;
		StringBuffer retBuff = new StringBuffer(nSrcLen
				* (1 + strNew.length() / nOldStrLen));
		boolean bIsFound = false;
		for (int i = 0; i < nSrcLen;) {
			bIsFound = false;
			if (srcBuff[i] == oldStrBuff[0]) {
				int j;
				for (j = 1; j < nOldStrLen && i + j < nSrcLen
						&& srcBuff[i + j] == oldStrBuff[j]; j++)
					;
				bIsFound = (j == nOldStrLen);
			}
			if (bIsFound) {
				retBuff.append(strNew);
				i += nOldStrLen;
			} else {
				int nSkipTo;
				if (i + nOldStrLen >= nSrcLen)
					nSkipTo = nSrcLen - 1;
				else
					nSkipTo = i;
				while (i <= nSkipTo) {
					retBuff.append(srcBuff[i]);
					i++;
				}
			}
		}

		srcBuff = null;
		oldStrBuff = null;
		return retBuff.toString();
	}
	
	
	/**
	 * 替换字符串.
	 * 
	 *<br />
	 *@date 2014-11-26 下午12:42:35
	 *@author <a href="bentengwu@163.com">thender</a>
	 *@param srcStr  被操作的源字符串
	 *@param aimStr  需要被替换掉的字符串
	 *@param args  多个索引的字符串.按照下标依次替换
	 *@param start 开始替换的索引, 下标从0开始.
	 *@return  替换后的字符串
	 *   replaceStr("adsfa","a","xiaoming,xiaohong") = "xiaomingdsfxiaohong"
	 *@since 3.0
	 */
	public static String replaceStrByIndex(String srcStr,String aimStr,int start,String...args)
	{
		StringBuilder sb = new StringBuilder();
		if(StringUtils.isBlank(srcStr))
		{
			return "";
		}else {
			String[] srcStrs = StringUtils.splitByWholeSeparator(srcStr, aimStr);
			int tmp = start;
			for(String arg:args)
			{
				srcStrs[tmp]=srcStrs[tmp]+arg;
				tmp++;
			}
			for(int i=0;i<srcStrs.length;i++)
			{
				sb.append(srcStrs[i]);
				if(i<start){
					sb.append(aimStr);
				}
			}
			return sb.toString();
		}
	}

	/**
	 * 复用org.apache.commons.lang.StringUtils的方法.
	 * @param src 源字符串
	 * @param bf  需要被替换的字符串
	 * @param after 替换后的字符串
	 * @return 完成替换的字符串
	 */
	public static String replaceEach(String src, String[] bf, String[] after) {
		return StringUtils.replaceEach(src, bf, after);
	}
	
	public static void main(String[] args) {
		System.out.println(replaceStrByIndex("https://adsfa?adsfa=?", "?", 1, "小名"));

		String[] args1 = splitFirst("-1,asdf,asdfa,asdf", ",");
		for (String arg : args1) {
			System.out.println(arg);

		}

		args1 = splitFirst(",-1", ",");
		for (String arg : args1) {
			System.out.println(arg);

		}
	}
	/**
	 * 替换字符串
	 *<br />
	 *@date 2013-11-14 下午2:41:15
	 *@author <a href="bentengwu@163.com">伟宏</a>
	 *@param strSrc  被操作的字符串
	 *@param strOld	需要被替换的字符串
	 *@param strNew	新的字符串
	 *@return	返回操作后的字符串.
	 */
	public static String replaceStr(StringBuffer strSrc, String strOld,
			String strNew) {
		if (strSrc == null)
			return null;
		int nSrcLen = strSrc.length();
		if (nSrcLen == 0)
			return "";
		char oldStrBuff[] = strOld.toCharArray();
		int nOldStrLen = oldStrBuff.length;
		if (nOldStrLen == 0 || nOldStrLen > nSrcLen)
			return strSrc.toString();
		StringBuffer retBuff = new StringBuffer(nSrcLen
				* (1 + strNew.length() / nOldStrLen));
		boolean bIsFound = false;
		for (int i = 0; i < nSrcLen;) {
			bIsFound = false;
			if (strSrc.charAt(i) == oldStrBuff[0]) {
				int j;
				for (j = 1; j < nOldStrLen && i + j < nSrcLen
						&& strSrc.charAt(i + j) == oldStrBuff[j]; j++)
					;
				bIsFound = j == nOldStrLen;
			}
			if (bIsFound) {
				retBuff.append(strNew);
				i += nOldStrLen;
			} else {
				int nSkipTo;
				if (i + nOldStrLen >= nSrcLen)
					nSkipTo = nSrcLen - 1;
				else
					nSkipTo = i;
				while (i <= nSkipTo) {
					retBuff.append(strSrc.charAt(i));
					i++;
				}
			}
		}

		oldStrBuff = null;
		return retBuff.toString();
	}

        /**
         * 顾名思义
         * @param bytes
         * @param ch
         * @param radix
         * @return 返回字符串
         */
	public static String byteToString(byte bytes[], char ch, int radix) {
		String sRet = "";
		for (int i = 0; i < bytes.length; i++) {
			if (i > 0) sRet = String.valueOf(String.valueOf(sRet)).concat(",");
			sRet = String.valueOf(sRet)
					+ String.valueOf(Integer.toString(bytes[i], radix));
		}

		return sRet;
	}


	/**
	 * 将字符串转化为字节数组;
	 * @param data
	 * @return 按照utf-8的编码转化为字节数组;
	 */
	public static final byte[] getBytesUTF8(String data) {
		if (data == null) {
			return new byte[0];
		}
		return data.toString().getBytes(Charset.forName(EncodeUtils.UTF8));
	}

	/**
	 *
	 * @param str
	 * @return
	 */
	public static int getBytesLength(String str) {
		if (str == null)
			return 0;
		char srcBuff[] = str.toCharArray();
		int nGet = 0;
		for (int i = 0; i < srcBuff.length; i++) {
			char aChar = srcBuff[i];
			nGet += aChar > '\177' ? 2 : 1;
		}
		return nGet;
	}

	public static String[] split(String str, String regx) {
		if (isEmpty(str))
			return null;
		return  str.split(regx);
	}

	/**
	 *
	 * @param _srcStr 原字符串
	 * @param regx 分割符
	 * @return
	 * 	splitFirst("-1,asdf,asdf",",") == ["-1","asdf,asdf"]
	 * 	splitFirst(",-1,asdf,asdf",",") == ["-1","asdf,asdf"]
	 * 	splitFirst(",-1",",") == ["-1"]
	 * 	splitFirst(",-1",null) ==  null
	 * 	splitFirst(null,",") ==  null
	 */
	public static String[] splitFirst(String _srcStr, String regx) {
		if (isEmpty(_srcStr) || isEmpty(regx)) {
			return null;
		}

		String srcStr = trimStart(_srcStr, regx);
		int index = srcStr.indexOf(regx);
		if (index > 0)
			return new String[]{srcStr.substring(0, index), srcStr.substring(index + 1)};
		else
			return new String[]{srcStr};
	}


	public static String transDisplay(String content) {
		return transDisplay(content, true);
	}
	
	/**
	 * 转义请使用apache的转义方法.
	 *<br />
	 *@date 2013-11-14 下午2:39:43
	 *@author <a href="bentengwu@163.com">伟宏</a>
	 *@param content
	 *@param changeBlank
	 *@return
	 */
	@Deprecated
	public static String transDisplay(String content, boolean changeBlank) {
		if (content == null)
			return "";
		char srcBuff[] = content.toCharArray();
		int nSrcLen = srcBuff.length;
		StringBuffer retBuff = new StringBuffer(nSrcLen * 2);
		for (int i = 0; i < nSrcLen; i++) {
			char cTemp = srcBuff[i];
			switch (cTemp) {
			case 32: // ' '
				retBuff.append(changeBlank ? "&nbsp;" : " ");
				break;
			case 60: // '<'
				retBuff.append("&lt;");
				break;
			case 62: // '>'
				retBuff.append("&gt;");
				break;
			case 10: // '\n'
				retBuff.append("<br>");
				break;
			case 34: // '"'
				retBuff.append("&quot;");
				break;
			case 38: // '&'
				boolean bUnicode = false;
				for (int j = i + 1; j < nSrcLen && !bUnicode; j++) {
					cTemp = srcBuff[j];
					if (cTemp == '#' || cTemp == ';') {
						retBuff.append("&");
						bUnicode = true;
					}
				}

				if (!bUnicode)
					retBuff.append("&amp;");
				break;
			case 9: // '\t'
				retBuff.append(changeBlank ? "&nbsp;&nbsp;&nbsp;&nbsp;"
						: "    ");
				break;
			default:
				retBuff.append(cTemp);
				break;
			}
		}

		return retBuff.toString();
	}
	
	/**
	 * 判断字符串的值的相等 忽略大小写.
	 *<br />
	 *@date 2013-11-14 下午2:37:08
	 *@author <a href="bentengwu@163.com">伟宏</a>
	 *@param str1	进行对比的数据
	 *@param str2	进行对比的数据
	 *@return	containsIgnoreCase(null,null)		return false
	 *			containsIgnoreCase(null,"asdf")		return false
	 *			containsIgnoreCase("asdf",null)		return false
	 *			containsIgnoreCase("asdf","ASDF")	return true
	 */
	public static boolean containsIgnoreCase(String str1, String str2) {
		if (str1 == null || str2 == null)
			return false;
		if (str1.equalsIgnoreCase(str2))
			return true;
		return false;
	}

	/**
	 * 判断是否是包含中文
	 *<br />
	 *@date 2013-11-14 下午2:34:46
	 *@author <a href="bentengwu@163.com">伟宏</a>
	 *@param str  被判断的字符串
	 *@return	如果存在全角字符串 返回true.
	 *	<br /> 返回false.
	 */
	public static boolean hasFullChar(String str) {
		if (str.getBytes().length == str.length()) {
			return false;
		}
		return true;
	}
	
	/**
	 * 将字符串数组转化为集合
	 *<br />
	 *@date 2013-11-14 下午2:33:52
	 *@author <a href="bentengwu@163.com">伟宏</a>
	 *@param array	字符串
	 *@return	返回字符数组转化为集合并进行返回. 如果是空的字符串,不会被放到集合中.
	 */
	public static Collection<String> array2List(String array[]) {
		Collection<String> coll = new ArrayList<String>();
		for (int i = 0; i < array.length; i++) {
			if (!isEmpty(array[i]))
				coll.add(array[i]);
		}
		return coll;
	}

	/**
	 * 删除input字符串中的html格式
	 * @param input
	 * @return　　返回删除后的字符串
	 */
	public static String splitAndFilterString(String input) {
		if (input == null || input.trim().equals("")) {
			return "";
		}
		// 去掉所有html元素,
		String str = input.replaceAll("\\&[a-zA-Z]{1,10};", "").replaceAll(
				"<[^>]*>", "").replaceAll("[(/>)<]", "");
		return str;
	}
	
	/**
	 * 将数组转化为字符串 用逗号分隔.
	 *<br />
	 *@date 2013-11-18 下午3:58:40
	 *@author <a href="bentengwu@163.com">伟宏</a>
	 *@param array	字符串数组.
	 *@return	返回字符串,用都逗号分隔.
	 */
	public static String getStrbyarray(String[] array) {
		return getStrByArray(array,",");
	}

	public static String getStrbyarray(String[] array,String split) {
		return getStrByArray(array,split);
	}
	
	/**
	 * 将数组转化为字符串进行返回. 不做深度解析，数组中的对象处理方式是tostring();
	 *<br />
	 *@date 2016-1-19 下午12:47:18
	 *@author <a href="bentengwu@163.com">thender</a>
	 *@param args	数组
	 *@param split	分割符
	 *@return	数组转化为字符串进行返回. 不做深度解析，数组中的对象处理方式是tostring();
	 */
	public static String getStrByArray(Object[] array,String split)
	{
		if (array == null || array.length == 0)
			return null;
		StringBuilder str = new StringBuilder("");
		for (Object s : array) {
			str.append(s);
			str.append(split);
		}
		return StringUtils.removeEnd(str.toString(), split);
	}
	
	public static String appendChar$str(String str, char endwith) {
		if (isEmpty(str))
			return "";
		if (str.charAt(str.length() - 1) != endwith) {
			str += endwith;
		}
		return str;
	}

	/**
	 * 数据转为集合
	 *<br /> 只能对String Integer long 进行处理.
	 *@date 2013-11-18 下午3:56:59
	 *@author <a href="bentengwu@163.com">伟宏</a>
	 *@param array	数组
	 *@param classtype	转化后的类型
	 *@return
	 */
	public static Collection<Object> arr2collect(String array[],
			String classtype) {
		Collection<Object> coll = new ArrayList<Object>();
		for (int i = 0; i < array.length; i++) {
			if (!isEmpty(array[i])) {
				if ("long".equalsIgnoreCase(classtype)) {
					coll.add(new Long(array[i]));
				} else if ("string".equalsIgnoreCase(classtype)) {
					coll.add((array[i]));
				} else if ("integer".equalsIgnoreCase(classtype)) {
					coll.add(new Integer(array[i]));
				}
			}
		}
		return coll;
	}

	/**
	 * 把字符替换成数字，支持浮点
	 *<br /> 该方法存在一定的问题,不推荐使用.
	 *@date 2013-11-18 下午3:54:50
	 *@author <a href="bentengwu@163.com">伟宏</a>
	 *@param str	字符串.
	 *@return	剔除非数字后的字符.
	 */
	@Deprecated
	public static String repStr2Numberstr(String str) {
		if (str == null)
			return str;
		for (int i = 0; i < str.length(); i++) {
			if (str.charAt(i) != '.' && (!Character.isDigit(str.charAt(i)))) {
				str = str.replaceAll(str.charAt(i) + "", "");
			}
		}
		return str;
	}

	/**
	 * 去除重复项
	 *<br />
	 *@date 2013-11-18 下午3:53:44
	 *@author <a href="bentengwu@163.com">伟宏</a>
	 *@param tomail	 被检查的字符串
	 *@param split	字符串的分割符
	 *@return	返回除去重复项后的字符串
	 */
	public static String removeDuplicate(String tomail,String split) {
		if (StrUtils.isEmpty(tomail))
			return "";
		Set<String> set = new HashSet<String>(0);
		String _$mails[] = tomail.split(split);
		for (String s : _$mails) {
			set.add(s);
		}
		StringBuffer returnstr = new StringBuffer("");
		for (String s : set) {
			returnstr.append(s + split);
		}
		return returnstr.toString();
	}

	/**
	 * 判断第一个大写的字母出现的位置 从0开始
	 *<br />
	 *@date 2013-11-18 下午3:52:31
	 *@author <a href="bentengwu@163.com">伟宏</a>
	 *@param arg	字符串
	 *@return	返回字符串中第一个大写字符出现的位置
	 */
	public static int getFirstCapitalLoacl(String arg) {
		for (int i = 0; i < arg.length(); i++) {
			char t = arg.charAt(i);
			if (t >= 'A' && t <= 'Z') {
				return i;
			}
		}
		return arg.length();
	}

	/**
	 * 求数字集合的和
	 *<br />
	 *@date 2013-11-18 下午3:51:08
	 *@author <a href="bentengwu@163.com">伟宏</a>
	 *@param list	数字的字符串集合.
	 *@return	该集合的各个值的和.
	 */
	public static Long getSumFromList(List<String> list) {
		Long result = 0L;
		for (String s : list) {
			result += new Integer(s);
		}
		return result;
	}
	
	/**
	 * 与业务相关的方法不推荐放在LIB库中.
	 * @param typecode	操作码
	 * @return	拆分后的结果
	 */
	@Deprecated
	public static Long[] toOperateCode(long typecode) {
		List<Long> t = new ArrayList<Long>();
		String code = Long.toBinaryString(typecode);
		int idx = 0;
		while ((idx = code.indexOf("1")) != -1) {
			int pow = code.length() - idx - 1;
			t.add((long) Math.pow(2, pow));
			if (idx + 1 < code.length())
				code = code.substring(idx + 1);
			else
				break;
		}
		// 转换为数组.
		Long[] types = new Long[t.size()];
		t.toArray(types);
		return types;
	}

       /**
        * 随即生成由字母有数字组合的指定长度的字符串
        *<br />
        *@date 2013-11-18 下午3:48:48
        *@author <a href="bentengwu@163.com">伟宏</a>
        *@param length	字符串的长度
        *@return	随机生存的字符串或数组及其组合的字符串.
        */
	public static String getRandomNumLetters(int length) {
		char str1[] = { 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k',
				'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w',
				'x', 'y', 'z' };
		char str2[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9' };
		StringBuffer out = new StringBuffer();
		if (length < 0) {
			return "";
		}
		for (int i = 0; i < length - 1; i++) {
			if (RandomUtils.nextInt(2) == 0) {
				out.append(str1[RandomUtils.nextInt(str1.length)]);
			} else {
				out.append(str2[RandomUtils.nextInt(str2.length)]);
			}
		}
		// 全面全部是数字时，最后一个添加字母,全部是字母时,添加数字,否则随机
		if (org.apache.commons.lang.math.NumberUtils.isDigits(out.toString())) {
			out.append(str1[RandomUtils.nextInt(str1.length)]);
		} else if (StringUtils.isNotBlank(out.toString())
				&& StringUtils.isAlpha(out.toString())) {
			out.append(str2[RandomUtils.nextInt(str2.length)]);
		} else {
			if (RandomUtils.nextInt(2) == 0) {
				out.append(str1[RandomUtils.nextInt(str1.length)]);
			} else {
				out.append(str2[RandomUtils.nextInt(str2.length)]);
			}
		}
		return out.toString();
	}
       
		/**
		 * 获取长度为len的随机数字字符串.
		 *<br />
		 *@date 2013-11-18 下午3:47:49
		 *@author <a href="bentengwu@163.com">伟宏</a>
		 *@param len	要生成的字符串长度.
		 *@return	长度为len的随机数字字符串.
		 */
        public static String getRandomNumber(int len){
            Double d = Math.pow(10, len);
            return StringUtils.leftPad(RandomUtils.nextInt(d.intValue()-1)+"", len, "0");
        }

	/**
	 * 获取固定范围内的不重复的随机数
	 *
	 * @param   min 	随机数初始值（包含）
	 * @param	max		随机数上限值（包含）
	 * @param 	n		生成的个数
	 * @return       String[]	随机数数组
	 */
	public static String[] getRandomPool(int min,int max,int n){
		int len = max-min+1;

		if(max < min || n > len){
			return null;
		}

		String[] source = new String[len];
		for (int i = min; i < min+len; i++){
			source[i-min] = String.valueOf(i);
		}

		String[] result = new String[n];
		Random rd = new Random();
		int index = 0;
		for (int i = 0; i < result.length; i++) {
			//待选数组0到(len-2)随机一个下标
			index = Math.abs(rd.nextInt() % len--);
			//将随机到的数放入结果集
			result[i] = source[index];
			//将待选数组中被随机到的数，用待选数组(len-1)下标对应的数替换
			source[index] = source[len];
		}
		return result;
	}


	/**
         * 将集合转化为String. 用换行符分隔.
         *<br />
         *@date 2013-11-18 下午3:24:52
         *@author <a href="bentengwu@163.com">伟宏</a>
         *@param list	集合 ,集合中的对象采用toString()进行取值.
         *@return	返回换行符分隔的字符串.
         */
        public static String list2Str(List list){
            StringBuilder sb = new StringBuilder();
            if(list==null || list.size()==0){
                return "";
            }
            for(Object t : list){
            	sb.append(t.toString()).append(PathUtil.getLineSeparator());
            }
            return sb.toString();
        }
        
        /**
         * 将集合转化为String. 用split分隔.
         *<br />
         *@date 2013-11-18 下午3:24:52
         *@author <a href="bentengwu@163.com">伟宏</a>
         *@param list	集合,集合中的对象采用toString()进行取值.
         *@param split 分隔符
         *@return	返回分隔符的字符串.
         */
        public static String list2Str(List list, String split){
        	StringBuilder sb = new StringBuilder();
        	if(list==null || list.size()==0){
        		return "";
        	}
        	for(Object t : list){
        		sb.append(t.toString()).append(split);
        	}
        	return sb.toString();
        }
        
        /**
         * 格式化一个double类型的数据. 不会进行四舍五入.
         *<br />
         *@date 2013-12-10 下午6:30:45
         *@author <a href="bentengwu@163.com">伟宏</a>
         *@param numb		需要被格式化的数据.
         *@param decimals	小数位数.
         *@return	
         *	formateNumbers(1.111,2)	=	1.11
         *	formateNumbers(null,2)	=	null
         *	formateNumbers(1.1,2)	=	1.10
         *	formateNumbers(1.0,2)	=	1.00
         *	formateNumbers(1.899,2)	=	1.89
         */
        public static String formatNumbers(Double numb,int decimals){
        	String	tmp		=	getString(numb);
        	String[] args	=	tmp.split("\\.");
        	String decimalsVal	=	args.length>=2?args[1]:"0";
        	return args[0].concat(".").concat(StringUtil.rightPad(StringUtils.left(decimalsVal, decimals), decimals, "0"));
        }
        
        
        /**
         * 将对象转化为字符串
         *<br />
         *@date 2016-2-4 上午11:10:03
         *@author <a href="bentengwu@163.com">thender</a>
         *@param t	对象。
         *@return  字符串,对象的成员和其值的可视化字符串
         */
        public static String toStringReflection(Object t)
        {
        	if(t==null)return "null";
        	return StringUtils.replace(ToStringBuilder.reflectionToString(t), t.getClass().getPackage().getName(), "");
        }

	/**
	 * @param strings 1个或者多个字符串
	 * @return 拼接后的字符串.
	 */
	public static String join(String... strings) {
		return StringUtils.join(strings);
	}
}
