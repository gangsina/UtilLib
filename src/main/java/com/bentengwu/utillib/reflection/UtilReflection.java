/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bentengwu.utillib.reflection;

import com.bentengwu.utillib.file.PathUtil;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.apache.commons.lang.StringUtils;
import org.slf4j.LoggerFactory;

/**
 * @author 伟宏
 */
public class UtilReflection {
    /**
	 * 调用Getter方法.
	 */
	public static Object invokeGetterMethod(Object obj, String propertyName) {
		String getterMethodName = "get" + firstToUpperCase(propertyName);
		return invokeMethod(obj, getterMethodName, new Class[] {}, new Object[] {});
	}

	/**
	 * 调用Setter方法.使用value的Class来查找Setter方法.
	 */
	public static void invokeSetterMethod(Object obj, String propertyName, Object value) {
		invokeSetterMethod(obj, propertyName, value, null);
	}
        
        /**
	 * 调用Setter方法.
	 * 
	 * @param propertyType 用于查找Setter方法,为空时使用value的Class替代.
	 */
	public static void invokeSetterMethod(Object obj, String propertyName, Object value, Class<?> propertyType) {
		Class<?> type = propertyType != null ? propertyType : value.getClass();
		String setterMethodName = "set" + firstToUpperCase(propertyName);
		invokeMethod(obj, setterMethodName, new Class[] { type }, new Object[] { value });
	}
        
        /**
	 * 直接调用对象方法, 无视private/protected修饰符.
	 * 用于一次性调用的情况.
	 */
	public static Object invokeMethod(final Object obj, final String methodName, final Class<?>[] parameterTypes,
			final Object[] args) {
		Method method = getAccessibleMethod(obj, methodName, parameterTypes);
		if (method == null) {
			throw new IllegalArgumentException("Could not find method [" + methodName + "] on target [" + obj + "]");
		}

		try {
			return method.invoke(obj, args);
		} catch (Exception e) {
			throw convertReflectionExceptionToUnchecked(e);
		}
	}
        
        
        /**
	 * 循环向上转型, 获取对象的DeclaredMethod,并强制设置为可访问.
	 * 如向上转型到Object仍无法找到, 返回null.
	 * 
	 * 用于方法需要被多次调用的情况. 先使用本函数先取得Method,然后调用Method.invoke(Object obj, Object... args)
	 */
	public static Method getAccessibleMethod(final Object obj, final String methodName,
			final Class<?>... parameterTypes) {
		for (Class<?> superClass = obj.getClass(); superClass != Object.class; superClass = superClass.getSuperclass()) {
			try {
				Method method = superClass.getDeclaredMethod(methodName, parameterTypes);

				method.setAccessible(true);

				return method;

			} catch (NoSuchMethodException e) {//NOSONAR
				// Method不在当前类定义,继续向上转型
			}
		}
		return null;
	}
        
        
        /**
	 * 将反射时的checked exception转换为unchecked exception.
	 */
	public static RuntimeException convertReflectionExceptionToUnchecked(Exception e) {
		if (e instanceof IllegalAccessException || e instanceof IllegalArgumentException
				|| e instanceof NoSuchMethodException) {
			return new IllegalArgumentException("Reflection Exception.", e);
		} else if (e instanceof InvocationTargetException) {
			return new RuntimeException("Reflection Exception.", ((InvocationTargetException) e).getTargetException());
		} else if (e instanceof RuntimeException) {
			return (RuntimeException) e;
		}
		return new RuntimeException("Unexpected Checked Exception.", e);
	}
        
        
        /**
	 * 直接设置对象属性值, 无视private/protected修饰符, 不经过setter函数.
	 */
	public static void setFieldValue(final Object obj, final String fieldName, final Object value) {
		Field field = getAccessibleField(obj, fieldName);

		if (field == null) {
			throw new IllegalArgumentException("Could not find field [" + fieldName + "] on target [" + obj + "]");
		}

		try {
			field.set(obj, value);
		} catch (IllegalAccessException e) {
			logger.error("不可能抛出的异常:{}", e.getMessage());
		}
	}

        /**
	 * 直接读取对象属性值, 无视private/protected修饰符, 不经过getter函数.
	 */
	public static Object getFieldValue(final Object obj, final String fieldName) {
		Field field = getAccessibleField(obj, fieldName);

		if (field == null) {
			throw new IllegalArgumentException("Could not find field [" + fieldName + "] on target [" + obj + "]");
		}

		Object result = null;
		try {
			result = field.get(obj);
		} catch (IllegalAccessException e) {
			logger.error("不可能抛出的异常{}", e.getMessage());
		}
		return result;
	}
        
	
	/**
	 * 获取该对的列的类型.
	 *<br />
	 *@date 2014-12-5 下午6:12:41
	 *@author <a href="bentengwu@163.com">thender</a>
	 *@param obj	对象实例
	 *@param fieldName 对象的属性名称
	 *@return 对象的属性的类型
	 *@since 3.0
	 */
	public static Class<?> getFieldType(final Object obj, final String fieldName)
	{
		Field field =  getAccessibleField(obj, fieldName);
		return field.getType();
	}
	/**
	 * 循环向上转型, 获取对象的DeclaredField,	 并强制设置为可访问.
	 * 
	 * 如向上转型到Object仍无法找到, 返回null.
	 */
	public static Field getAccessibleField(final Object obj, final String fieldName) {
		for (Class<?> superClass = obj.getClass(); superClass != Object.class; superClass = superClass.getSuperclass()) {
			try {
				Field field = superClass.getDeclaredField(fieldName);
				field.setAccessible(true);
				return field;
			} catch (NoSuchFieldException e) {//NOSONAR
				// Field不在当前类定义,继续向上转型
			}
		}
		return null;
	}
        
        /**
         * 这只做一个简单的toString()操作.
         */
        public static String toStringRefl(Object t){
            Class clazz = t.getClass();
            StringBuilder sb = new StringBuilder().append("{").append(PathUtil.getLineSeparator());
            Field[] fields =  clazz.getDeclaredFields();
            for(Field f : fields){
                f.setAccessible(true);
                try{
                     Object tmp = f.get(t);
                    if(tmp!=null && StringUtils.isNotBlank(tmp.toString())){
                        sb.append(f.getName()).append("=").append(tmp).append(PathUtil.getLineSeparator());
                    }
                }catch(Exception ex){
                    logger.error("",ex);
                }
            }
            sb.append("}").append(PathUtil.getLineSeparator());
            return sb.toString();
        }
        
        /**
    	 * 将首字母转为小写
    	 *<br />
    	 *@date 2014-12-5 下午12:51:19
    	 *@author <a href="bentengwu@163.com">thender</a>
    	 *@param str 
    	 *@return
    	 *@since 3.0
    	 */
    	public static String firstToLower(String str)
    	{
    		char firstChar = str.charAt(0);
    		return str.replaceFirst(firstChar+"", (firstChar+"").toLowerCase());
    	}
    	
    	/**
    	 * 首字母大写
    	 *<br />
    	 *@date 2014-12-5 下午8:14:15
    	 *@author <a href="bentengwu@163.com">thender</a>
    	 *@param str 
    	 *@return
    	 *@since 3.0
    	 */
    	public static  String firstToUpperCase(String str)
    	{
    		char firstChar = str.charAt(0);
    		return str.replaceFirst(firstChar+"", (firstChar+"").toUpperCase());
    	}
        
	private static org.slf4j.Logger logger = LoggerFactory.getLogger(UtilReflection.class);
}
