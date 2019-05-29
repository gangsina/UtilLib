package com.bentengwu.utillib.exception;
/**  
 * @Title: UtilException.java
 * @Description: 
 * @author thender.xu
 *         mail: bentengwu@163.com
 * @date 2012-6-21 上午11:23:18 
 */
public class UtilException extends Exception{
	
	/** 
	* @Fields serialVersionUID : TODO
	*/ 
	private static final long serialVersionUID = 1L;
	
	public UtilException(){
		super();
	}

	public UtilException(String msg){
		super(msg);
	}
	
	public UtilException(Throwable thable){
		super(thable);
	}
	
	public UtilException(String msg,Throwable thable){
		super(msg, thable);
	}
	
	public UtilException(Exception e){
		super(e.getMessage());
	}
}
