package com.bentengwu.utillib.map;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/** 
 * @Title: Bizz_Bean.java 
 * 
 * @author email: <a href="bentengwu@163.com">徐伟宏</a> 
 * @date 2013-8-2 下午5:40:36 
 * @version :
 * @Description:  受不了了,还是决定定义一个标示用于解析实体类.
 * 		方便定制化字段的读取.
 *    这个类用于标示实体类中的字段是否是bizz系统的实体类.
 */
@Retention(RetentionPolicy.RUNTIME)  
@Target( { java.lang.annotation.ElementType.FIELD}) 
public @interface Bizz_Bean {
	/**
	 * ----------------------------方法说明-----------------------
	 *
	 *-----------------------------------------------------------
	 * @return 
	 */
	boolean isBizzEntity() default false;
	
	
	/**
	 * 是否是list
	 *<br />
	 *@date 2014-12-30 下午3:55:42
	 *@author <a href="bentengwu@163.com">thender</a>
	 *@return
	 *@since 3.0
	 */
	boolean isList() default false;
}
