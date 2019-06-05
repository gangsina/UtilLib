package com.bentengwu.utillib.map;

import com.bentengwu.utillib.Collection.CollectionUtils;
import com.bentengwu.utillib.String.StringFormatUtil;
import com.bentengwu.utillib.UtilConversion;
import com.bentengwu.utillib.validate.ValidateUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.bentengwu.utillib.date.DateUtil;
import com.bentengwu.utillib.CommonUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import static  com.bentengwu.utillib.exception.ExceptionUtils.*;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.sql.Timestamp;
import java.util.*;

/**
 * @author email: <a href="bentengwu@163.com">徐伟宏</a>
 * @date 2013-7-30 下午1:37:53
 */
public abstract class MapUtil {
	protected static Logger logger = LoggerFactory.getLogger(MapUtil.class);

	/**
	 * 查看MAP中的值是否有为NULL
	 * 和空字符串的.
	 * @param paramNames
	 *            被校验的Key 多个可以用,号隔开. 只支持字符串key
	 * @param map
	 *            被校验的map.
	 * @return 没有空的情况,返回true.否则返回false
	 */
	@SuppressWarnings("rawtypes")
	public static boolean validateMap(String paramNames, Map map) {
		String[] args = paramNames.split(SplitCt.comma);
		for (String s : args) {
			if ((!map.containsKey(s)) || map.get(s) == null) {
				return false;
			} else if (map.get(s).toString().trim().equals("")) {
				return false;
			}
		}
		return true;
	}

	/**
	 * 读取需要放回的字段,放到MAP中.
	 * @param itemList
	 *            实例列表
	 * @param fields
	 *            需要返回的字段 "," 分隔.
	 * @param clazz
	 *            被解析的类.
	 * @return
	 */
	@SuppressWarnings({ "rawtypes" })
	public static List<Map> filterBeanList(List itemList, String fields,
			Class clazz) {
		List<Map> resList = Lists.newArrayList();
		if (itemList == null || StringUtils.isBlank(fields)) {
			return resList;
		}
		for (Object t : itemList) {
			resList.add(filterBean(t, fields, clazz));
		}
		return resList;
	}
	
	/**
	 * @param itemList		被过滤的bean列表.
	 * @param split		    如果实例中存在自定义实例字段,则,是将实例解析为一个完整的MAP,然后作为一个单独的key存放,还是解析后的
	 * 				各个字段都拥有一个独立的KEY.
	 * @param fields	字段列表.以逗号分隔. 
	 * 				如果存在实例,而且需要解析,则单独启一个字符串.
	 * @return
	 */
	@SuppressWarnings({ "rawtypes" })
	public static List<Map> filterBeanList(List itemList, boolean split, String...fields) {
		List<Map> resList = Lists.newArrayList();
		if (itemList == null ||fields[0]==null || StringUtils.isBlank(fields[0])) {
			return resList;
		}
		for (Object t : itemList) {
			if(t!=null)
				resList.add(filterBean(t,0,split, fields));
		}
		return resList;
	}
	
	/**
	 * @param t		被解析的对象.
	 * @param index    索引.  每递归一次 index的值   +1.
	 * @param fields  需要检索的字段名称 逗号分隔
	 * @return
	 */
	@SuppressWarnings({ "unchecked","rawtypes" })
	public static Map filterBean(Object t, int index, boolean split,String...fields) {
		Map map = Maps.newHashMap();
		if(StringUtils.isBlank(fields[index])){
			return map;
		}

		if(t==null){
			return map;
		}
		
		String[] args = fields[index].split(SplitCt.comma);
		for (String f : args) {
			try {
				if (StringUtils.isNotBlank(f)) {
					Field field = t.getClass().getDeclaredField(f);
					field.setAccessible(true);
					Annotation[] annots =  field.getAnnotations();
					if(annots.length>0){
						for(Annotation a : annots){
							if(a instanceof Bizz_Bean){
								Bizz_Bean ba = (Bizz_Bean)a;
								if(ba.isBizzEntity()){
									if(ba.isList())
									{
										List<Map> mapList = filterBeanList((List)field.get(t), false, getFilelds(index+1, fields));
										map.put(field.getName(), mapList);
									}else{
										Map bizzEntityMap = filterBean(field.get(t), index+1, split, fields);
										//如果是true 则每个字段作为一个独立的字段存放.
										if(split){
											map.putAll(bizzEntityMap);
										}else{
											//如果不是,则所有字段放在一个MAP中,然后作为一个字段在MAP中存放.
											map.put(field.getName(), bizzEntityMap);
										}
									}
								}
							}
						}
					}else{
						Object obj = field.get(t);
						if(obj instanceof Date
								|| obj instanceof java.sql.Date
								|| obj instanceof Timestamp)
						{
							map.put(f, field.get (t) == null ? "" : field.get (t) );
						}else{
							map.put(f, field.get (t) == null ? "" : field.get (t)+"" );
						}
					}
				}
			} catch (SecurityException e) {
				logger.error("{}", e);
			} catch (NoSuchFieldException e) {
				logger.error("{}", e);
			} catch (IllegalArgumentException e) {
				logger.error("{}", e);
			} catch (IllegalAccessException e) {
				logger.error("{}", e);
			}
		}
		return map;
	}
	
	
	/**
	 * 读取分类字段,从下标index开始.
	 *<br />
	 *@date 2014-12-30 下午4:23:05
	 *@author <a href="bentengwu@163.com">thender</a>
	 *@param index
	 *@param args
	 *@return
	 *@since 3.0
	 */
	public static String[] getFilelds(int index,String...args)
	{
		int i = index;
		List<String> list = Lists.newArrayList();
		try {
			while(args.length>=i&& args[i]!=null)
			{
				list.add(args[i]);
				i++;
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		
		return list.toArray(new String[0]);
	}
	
	
	
	
	/**
	 *  将Bean中的某个字段的值放入Map
	 * @param t  实例
	 * @param fields  字段名称  ","分隔
	 * @param clazz Class
	 * @return 返回被选择的字段的键值对放入一个MAP中.
	 */
	@SuppressWarnings({ "unchecked","rawtypes" })
	public static Map filterBean(Object t, String fields, Class clazz) {
		Map map = Maps.newHashMap();
		if(StringUtils.isBlank(fields)){
			return map;
		}
		String[] args = fields.split(SplitCt.comma);
		for (String f : args) {
			try {
				if (StringUtils.isNotBlank(f)) {
					Field field = clazz.getDeclaredField(f);
					field.setAccessible(true);
					map.put(f, field.get (t) == null ? "" : field.get (t));
				}
			} catch (SecurityException e) {
				logger.error("{}", e);
			} catch (NoSuchFieldException e) {
				logger.error("{}", e);
			} catch (IllegalArgumentException e) {
				logger.error("{}", e);
			} catch (IllegalAccessException e) {
				logger.error("{}", e);
			}
		}
		return map;
	}

	
	/**
	 * 格式化map中的某个时间字段
	 *<br />
	 *@date 2014-4-21 上午10:44:07
	 *@author <a href="bentengwu@163.com">伟宏</a>
	 *@param map	 结合
	 *@param keyName key 's name 
	 *@param fmt	form. eg. yyyy-MM-dd or MM-dd
	 *@since 1.0.2
	 */
	public static void fmtDate(final Map map,String keyName, String fmt)
	{
		Object t = map.get(keyName);
		if(t instanceof Date)
		{
			Date d = (Date)t;
			String r = DateUtil.convertDate2String(d, fmt);
			map.put(keyName, r);
		} else if( t instanceof String)
		{
			Date d = DateUtil.convertString2Date(t.toString(), DateUtil.LONG_DATE_FORMAT);
			String r = DateUtil.convertDate2String(d, fmt);
			map.put(keyName, r);
		}else{
			throw new RuntimeException("不支持其他格式时间的类型");
		}
	}


	/**
	 * 格式化@param map中的时间字段.
	 * @param map	需要处理的键值对集合
	 * @param keyNames	一个或者多个键位
	 * @param fmts	时间格式.  eg. yyyy-MM-dd or MM-dd
	 */
	public static void fmtDates(final Map map,String keyNames, String fmts){
		String[] arrayKey = keyNames.split(",");
		String[] arrayFmt = fmts.split(",");
		if(arrayKey.length != arrayFmt.length){
			throw new RuntimeException("属性和格式无法对应");
		}else{
			int index = 0;
			for(String keyName : arrayKey){
				String fmt = arrayFmt[index];
				Object t = map.get(keyName);
				if(t instanceof Date){
					Date d = (Date)t;
					String r = DateUtil.convertDate2String(d, fmt);
					map.put(keyName, r);
				} else if( t instanceof String){
					Date d = DateUtil.convertString2Date(t.toString(), DateUtil.LONG_DATE_FORMAT);
					String r = DateUtil.convertDate2String(d, fmt);
					map.put(keyName, r);
				}else{
					throw new RuntimeException("不支持其他格式时间的类型");
				}
				index++;
			}
		}
	}


	/**
	 * 将map中的值set到对应的类型的实例中. 按照JAVA bean的标准设置.
	 * 这里只支持单层的map.
	 * eg , Agent_Terminal --> agentTermianl
	 *<br />
	 *@date 2016-8-31 下午12:25:14
	 *@author <a href="bentengwu@163.com">thender</a>
	 *@param map		被转化的对象
	 *@param requireType	对应的javabean.
	 *@return javabean.
	 */
	public static <T> T fmtMap2Bean(Map<String,Object> map , Class<T> requireType){
		try {
			Map newMap = new HashMap<String, Object>();
			for(String column : map.keySet())
			{
				Object value = map.get(column);
				String fieldName = StringFormatUtil.formatAsJavaStyple(column);
				logger.debug("{}-->{}", column, fieldName);// 改成debug
				newMap.put(fieldName, value);
			}

			String mapstrdata = CommonUtils.mapper.writer().writeValueAsString(newMap);
			logger.debug("newMap-->{}", mapstrdata);// 改成debug

			return CommonUtils.mapper.readValue(mapstrdata, requireType);
		} catch (Exception e) {
			logger.warn(e.getMessage());
			logger.debug(e.getMessage(),e);
			return null;
		}
	}


	public static <T> T fmtMap2Bean(Map<String,Object> map , Class<T> requireType,String...exceptFields){
		try {
			Map newMap = new HashMap<String, Object>();
			Set<String> set = new HashSet<String>();
			CollectionUtils.addAll(set, exceptFields);
			for(String column : map.keySet()){
				Object value = map.get(column);
				String fieldName = StringFormatUtil.formatAsJavaStyple(column);
				if (set.contains(column)) {
					continue;
				}
				logger.debug("{}-->{}", column, fieldName);// 改成debug
				newMap.put(fieldName, value);
			}

			String mapstrdata = CommonUtils.mapper.writer().writeValueAsString(newMap);
			logger.debug("newMap-->{}", mapstrdata);// 改成debug
			return CommonUtils.mapper.readValue(mapstrdata, requireType);
		} catch (Exception e) {
			logger.warn(e.getMessage());
			logger.debug(e.getMessage(),e);
			return null;
		}
	}

	
	
	/**
	 *  时间格式处理, 不支持string to string
	 *<br />
	 *@date 2014-4-21 上午10:51:01
	 *@author <a href="bentengwu@163.com">伟宏</a>
	 *@param maps	map集合
	 *@param keyName key name,多个用逗号分隔
	 *@param fmt	form ,多个用逗号分隔
	 *@since 1.0.2
	 */
	public static void fmtDate(List<Map> maps,String keyName, String fmt)
	{
		for(Map map : maps)
		{
			String[] keys = StringUtils.split(keyName, ',');
			String[] fmts = StringUtils.split(fmt, ',');
			for(int i=0;i<keys.length;i++){
				fmtDate(map, keys[i], fmts[i]);
			}
		}
	}
	
	/**
	 *遍历map,  如果key的值oldValue 则替换为新的值:newValue
	 *@since 1.0.2
	 *@param map 集合
	 *@param keys 数组
	 *@param oldValue 旧值
	 *@param newValue 新值
	 */
	public static void replaceValue(Map map ,Object oldValue, Object newValue,Object...keys){
			for(Object key : keys)
			{
				if(map.containsKey(key) && map.get(key).equals(oldValue))
				{
					map.put(key, newValue);
				}
			}
	}
	
	
	/**
	 * 将MAP中的keys的值为oldValue替换为newValue
	 *<br />
	 *@since 1.0.2
	 *@date 2014-4-21 下午1:37:19
	 *@author <a href="bentengwu@163.com">伟宏</a>
	 *@param maps	 集合
	 *@param oldValue	旧值
	 *@param newValue	新值
	 *@param keys	数组
	 */
	public static void replaceValues(List<Map> maps , Object oldValue, Object newValue,Object...keys)
	{
		for(Map map : maps){
			replaceValue(map, oldValue, newValue, keys);
		}
		
	}
	
	/**
	 * null替换为空字符串
	 * @date 2015年8月18日 上午10:49:12
	 * @author Lilh
	 * @param map
	 */
	public static Map<String,Object> replaceNullWihtEmptyString(Map<String,Object> map){
		Set<String> keys=map.keySet();
		for(String key:keys){
			if(map.get(key)==null){
				map.put(key,"");
			}
		}
		return map;
	}
	
	/**
	 * null替换为空字符串
	 * @date 2015年8月18日 上午10:49:12
	 * @author Lilh
	 * @param list
	 */
	public static List<Map<String,Object>> replaceNullWihtEmptyString(List<Map<String,Object>> list){
		for(Map<String,Object> map:list){
			replaceNullWihtEmptyString(map);
		}
		return list;
	}

	/**
	 * 将map转化为字符串返回.
	 * @param map
	 * @return 将map转化为字符串返回.
	 */
	public static final String _2String(final Map map) {
		try {
			return  CommonUtils.mapper.writeValueAsString(map);
		} catch (JsonProcessingException e) {
			logger.warn(e.getMessage());
			logger.debug(e.getMessage(),e);
			throw new RuntimeException(e.getMessage(), e);
		}
	}

	/**
	 * 读取字符串类型的值
	 * @param map 集合
	 * @param key 键
	 * @return 键对应的字符串值.  不存在对应的key返回 null
	 * @throw key为空, map为空等特殊情况抛出运行时异常.
	 */
	public static final String getStr(final Map map, String key) {
		return get(map, key, String.class);
	}

	/**
	 * 读取对应的key的值.
	 * @param map 键值对集合
	 * @param key 键
	 * @param _class
	 * @return 对应的key的值,不存在对应的key返回 null
	 * @throw key为空, map为空等特殊情况抛出运行时异常.
	 */
	public static final <T> T get(final Map map,String key, Class<T> _class) {
		if (!containKey(map, key)) {
			return null;
		}
		return UtilConversion.convert(_class, map.get(key));
	}

	public static final boolean containKey(final Map map, String key) {
		ValidateUtils.validateParams(key);
		if (map == null) {
			newRE("params-error: map-->null");
		}
		return map.containsKey(key);
	}
	
}
