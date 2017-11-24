package com.bentengwu.utillib.validate;

import org.apache.commons.lang3.StringUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;

/**
 * 用于校验
 *@author：email: <a href="bentengwu@163.com"> thender </a> 
 *@Date 2016-6-10 下午3:12:06 
 */

public abstract class ValidateUtils {
	/**
	 *  检查参数是否正常,如果不正常将抛出参数异常！错误
	 *<br />
	 *@date 2016-1-6 下午2:00:28
	 *@author <a href="bentengwu@163.com">thender</a>
	 *@param args	一个或者多个参数
	 */
	public final static void validateParams(Object...args)
	{
		if(args==null || args.length<=0)
		{
			throw new RuntimeException("error:params_empty");
		}
		
		
		for(Object arg:args)
		{
			if(arg==null)
			{
				throw new RuntimeException("error:params_null");
			}
			if(StringUtils.isBlank(arg.toString()))
			{
				throw new RuntimeException("error:params_isBlank");
			}
		}
	}
	
	/**
	 * 检查参数。有异常参数就报出异常,或者不存在的参数
	 *<br />
	 *@date 2016-1-12 下午6:18:07
	 *@author <a href="bentengwu@163.com">thender</a>
	 *@param params		参数键值对
	 *@param keys		需要检查的一个或者多个key.
	 *@param _null_and_empty -1 不做非空判断 ,  1不允许null, 但允许空字符串, 2不允许null, 不允许空
	 */
	public final static void validateMapParams(final Map params,int _null_and_empty,Object...keys)
	{
		if(params==null || params.size()==0) throw new RuntimeException("error:params_empty");
		
		if(keys==null || keys.length<=0)
		{
			throw new RuntimeException("error:validate_params_empty");
		}
		
		for(Object key: keys)
		{
			if(!params.containsKey(key)){
				throw new RuntimeException("error:not_contain_key");
			}else if( _null_and_empty > 0)
			{
				Object t = params.get(key);
				if(t==null) throw new RuntimeException("error:contain_null");
				if(_null_and_empty>1)
				{
					if(StringUtils.isBlank(t.toString())) throw new RuntimeException("error:contain_empty");
				}
			}
		}
	}
	
	
	/**
	 * 检查JSON中的非法字段.
	 *<br />
	 *@date 2016-6-10 下午3:16:33
	 *@author <a href="bentengwu@163.com">thender</a>
	 *@param params		参数键值对
	 *@param keys		需要检查的一个或者多个key.
	 *@param _null_and_empty -1 不做非空判断 ,  1不允许null, 但允许空字符串, 2不允许null, 不允许空
	 */
	public final static void validatJSONParams(final JSONObject params,int _null_and_empty,String...keys)
	{
		if(params==null || params.length()==0) throw new RuntimeException("error:params_empty");
		
		if(keys==null || keys.length<=0)
		{
			throw new RuntimeException("error:validate_params_empty");
		}
		
		for(String key: keys)
		{
			if(!params.has(key)){
				throw new RuntimeException("error:not_contain_key");
			}else if( _null_and_empty > 0)
			{
				Object t;
				try {
					t = params.get(key);
					if(t==null) throw new RuntimeException("error:contain_null");
					if(_null_and_empty>1)
					{
						if(StringUtils.isBlank(t.toString())) throw new RuntimeException("error:contain_empty");
					}
				} catch (JSONException e) {
					throw new RuntimeException("error:JSONException",e);
				}
			}
		}
	}
}
