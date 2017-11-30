package com.bentengwu.utillib.number;

import java.text.NumberFormat;
import org.apache.commons.lang.StringUtils;

public class NumberUtils {

	/**
	 * 格式化double,按照四舍五入，小数位数由参数fractionDigits控制
	 * 
	 * @param source  需要被格式化的double 数值
	 * @param fractionDigits   double的小数位数
	 * @return 格式化后的字符串 
	 */
	public static String format(Double source, int fractionDigits) {
		NumberFormat nf = NumberFormat.getInstance();
		nf.setMaximumFractionDigits(fractionDigits);
		nf.setMinimumFractionDigits(fractionDigits);
		return nf.format(source);
	}

	/**
	* @package: cn.thender.xu.oputils
	* @Title: NumberUtils.java   function: isLong 
	* @Description: 判断是否为整数
	* @param source
	* @return
	* @return boolean   整数 true     非整数  false
	* @throws
	 */
	public static boolean isLong(String source){
		try {
			if (!org.apache.commons.lang.math.NumberUtils.isNumber(source)) {
				return false;
			}
			double d = Double.parseDouble(source);
			if (d == Double.parseDouble(clearFractionZero(source))) {
				return true;
			}
		} catch (Exception e) {
			return false;
		}
			return false;
	}
	
	/**
	* @package: cn.thender.xu.oputils
	* @Title: NumberUtils.java   function: clearFractionZero 
	* @Description: 如果是数字，不存在，直接返回。如果存在，获取小数点前的整数部分
	* @param source  
	* @return String   返回 小数点前的整数部分。
        *       source="1.2" return 1
	* @throws
	 */
	public static String clearFractionZero(String source){
		if(StringUtils.isBlank(source)){
			return source;
		}
		if(source.indexOf(".")>=0){
			source = source.substring(0,source.lastIndexOf("."));
		}
		return source;
	}
	
        
       /**
	 * 将t 转化为 integer类型.
	 *  
	 *@param t
	 *@return 如果不能转化为integer 类型,则返回-1.
         *  t==null return -1
         *  t==100  return 100
         *  t==""   return -1
	 */
	public static Integer toInteger(Object t){
		try{
			if(t==null){
				return -1;
			}else{
				return Integer.parseInt(t.toString().trim());
			}
		}catch(Exception e){
			return -1;
		}
	}

	/**
	 * @param bf 需要被处理的值
	 * @param defaultValue 默认值
	 * @return 将bf转化为Int类型返回,如果出现异常情况,则返回默认值(defaultValue)
	 */
	public final static Integer toInteger(Object bf,int defaultValue){
		try{
			if(bf==null){
				return defaultValue;
			}else{
				return Integer.parseInt(bf.toString().trim());
			}
		}catch(Exception e){
			return defaultValue;
		}
	}
        
        
        
        /**
	 * 将对象转化为LOng对象. 如果对象为空则返回-1L
	 *@param  t
	 *@return 
         *      t==null return -1L
         *      t==""   return -1L
         *      t==100  return 100L
	 */
	public static Long toLong(Object t){
			if(t==null){
				return -1L;
			}else {
				try{
					return Long.parseLong(t.toString());
				}catch(Exception e){
					return -1L;
				}
			}
	}
        
        /**
         * 
         * @param t  
         * @return 
         *      toDouble(null)  ==null;
         *      toDouble("")    ==null;
         *      toDouble("1.1") ==1.1;
         *      toDouble(1.1)   ==1.1;
         *      toDouble("1.1a1")==null;
         *      toDouble("$#.1a1")==null;
         */
        public static Double toDouble(Object t){
            if(t==null)return null;
            else if(StringUtils.isBlank(t.toString())){
                return null;
            }else{
                try{
                    return Double.parseDouble(t.toString());
                }catch(Exception e){
                    return null;
                }
            }
        }
	
	
	/**
	 * 将字符串 按照comma转化为 Long 数组 .   
	 *@param strGroupIds
	 *@param comma
	 *@return  Long 数组 . 
         *      strGroupIds==null || strGroupIds==""    return : [];
         *      strGroupIds=="1,1,1" comma==","    return [1,1,1];
         *      strGroupIds=="1,1,1," comma==","    return [1,1,1];
         *      strGroupIds=="1,1,1," comma==";"    return [];
	 */
	public static Long[] strArray2LongArray(String strGroupIds, String comma) {
		if(StringUtils.isBlank(strGroupIds)){
			return new Long[0];
		}else{
			try{
				String tmp = strGroupIds.trim();
				if(tmp.endsWith(comma)){
					tmp = tmp.substring(0,tmp.length()-1);
				}
				String[] tmps = tmp.split(comma);
				Long[] ls = new Long[tmps.length];
				for(int i=0;i<ls.length;i++){
					ls[i] = toLong(tmps[i]);
				}
				return ls;
			}catch(Exception e){
				return new Long[0];
			}
		}
	}
        
        
	public static void main(String args[]){
		System.out.println(org.apache.commons.lang.math.NumberUtils.isDigits("1231"));
//		String result = format(3.95D,0);
		System.out.println(format(new Double(13.41531),2));
		System.out.println(isLong("569.9"));
		System.out.println(clearFractionZero("3.90"));
	}
	
}
