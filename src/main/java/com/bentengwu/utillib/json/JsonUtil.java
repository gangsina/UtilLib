package com.bentengwu.utillib.json;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import com.bentengwu.utillib.String.StrUtils;
import com.bentengwu.utillib.code.EncodeUtils;
import com.bentengwu.utillib.exception.ExceptionUtils;
import com.bentengwu.utillib.stream.StreamUtil;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.apache.commons.codec.binary.StringUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.Maps;

/**
 * json相关的一些操作类.
 * 
 * @author email: <a href="bentengwu@163.com">徐伟宏</a>
 * @version :
 * @Title: JsonUtil.java
 * @date 2013-5-27 下午05:27:16
 * @Description: json的支持类
 */
public class JsonUtil {
	private final static Logger logger = LoggerFactory
			.getLogger(JsonUtil.class);

	/**
	 * <br />
	 * ----------------------------方法说明----------------------- <br />
	 * 解析收入流中的数据到map中,要求这个输入流是json格式的. <br />
	 * -----------------------------------------------------------
	 * 
	 * @param in
	 *            流, 要求流是一个json格式的输入流
	 * @return 一个map.
	 * @throws IOException
	 * @throws JSONException
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static Map jsonToMap(InputStream in) throws IOException,
			JSONException {
		JSONObject jsonObj = getJSONObject(in);
		Map map = Maps.newHashMap();
		Iterator<String> keys = jsonObj.keys();
		String key = null;
		while (keys.hasNext()) {
			key = keys.next();
			map.put(key, jsonObj.get(key).toString());
		}
		return map;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static Map jsonToMap(JSONObject json) {
		try {
			Map map = Maps.newHashMap();
			Iterator<String> keys = json.keys();
			String key = null;
			while (keys.hasNext()) {
				key = keys.next();
				Object obj = json.get(key);
				byte[] b = StringUtils.getBytesUtf8(obj.toString());
				JSONObject jo = getJSONObject(b);
				if (jo != null) {
					map.put(key, jsonToMap(jo));
					continue;
				}
				JSONArray array = getJSONArray(b);
				if (array != null) {
					map.put(key, jsonToMap(array));
					continue;
				}
				map.put(key, json.get(key).toString());
			}
			return map;
		} catch (JSONException e) {
			throw new RuntimeException("Json转Map出现异常{}", e);
		}
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static List<Object> jsonToMap(JSONArray json) {
		try {
			List<Object> list = new ArrayList<Object>();
			for (int i = 0; i < json.length(); i++) {
				Map map = Maps.newHashMap();
				JSONObject obj = getJSONObject(StringUtils.getBytesUtf8(json
						.get(i).toString()));
				if (obj != null) {
					map.putAll(jsonToMap(obj));
					list.add(map);
					continue;
				}
			}
			return list;
		} catch (JSONException e) {
			throw new RuntimeException("Json转Map出现异常{}", e);
		}
	}

	/**
	 * 将流转化为JSON对象. <br />
	 * 
	 * @date 2013-11-27 上午10:56:59
	 * @author <a href="bentengwu@163.com">伟宏</a>
	 * @param in
	 *            JSON流
	 * @return 如何流是标准的JSON,则返回JSON对象.否则
	 */
	public static JSONObject getJSONObject(InputStream in) {
		try {
			StringBuilder sb = StreamUtil.read(in);
			JSONObject jsonObj = new JSONObject(sb.toString());
			return jsonObj;
		} catch (Exception ex) {
			logger.error("", ex);
			throw new RuntimeException("解析客户端请求流,异常.请使用标准的JSON格式.", ex);
		}
	}

	/**
	 * 将流转化为JSONobject 如果不是JSON格式,则返回null. <br />
	 * 
	 * @date 2013-11-27 上午11:33:06
	 * @author <a href="bentengwu@163.com">伟宏</a>
	 * @param data
	 * @return 不是JSON 返回 NULL. 是JSON 返货JSONObject.
	 */
	public static JSONObject getJSONObject(byte[] data) {
		try {
			JSONObject json = new JSONObject(new String(data));
			return json;
		} catch (JSONException jsonEx) {
			return null;
		}
	}

	/**
	 * 将流转化为JSONArray 如果不是JSONArray格式,则返回null. <br />
	 * 
	 * @date 2013-11-27 上午11:33:06
	 * @author <a href="bentengwu@163.com">伟宏</a>
	 * @param data
	 * @return 不是JSONArray 返回 NULL. 是JSONArray 返货JSONArray.
	 */
	public static JSONArray getJSONArray(byte[] data) {
		try {
			JSONArray array = new JSONArray(new String(data));
			return array;
		} catch (JSONException jsonEx) {
			return null;
		}
	}

	/**
	 * 对JSONArray进行base64解码. <br />
	 * 
	 * @date 2013-11-27 下午1:41:55
	 * @author <a href="bentengwu@163.com">伟宏</a>
	 * @param jsonArray
	 * @return
	 */
	private static JSONArray getBase64DecodeJSONArray(JSONArray jsonArray) {
		try {
			JSONArray resArray = new JSONArray();
			for (int j = 0; j < jsonArray.length(); j++) {
				Object obj = jsonArray.get(j);
				if (obj != null) {
					byte[] base64ArrayItemData = EncodeUtils.base64Decode(obj
							.toString());
					JSONObject jsonObject = getJSONObject(base64ArrayItemData);
					if (jsonObject != null) {
						resArray.put(j, getBase64DecodeJSONObject(jsonObject));
					} else {
						JSONArray array = getJSONArray(base64ArrayItemData);
						if (array != null) {
							resArray.put(j, getBase64DecodeJSONArray(array));
						} else {
							resArray.put(j, new String(base64ArrayItemData));
						}
					}
				} else {
					resArray.put(j, "");
				}
			}
			return resArray;
		} catch (JSONException jsonEx) {
			logger.error("", jsonEx);
			throw new RuntimeException("", jsonEx);
		}
	}

	/**
	 * 返回对所有值进行BASE64编码的JSONARRAY. <br />
	 * 
	 * @date 2013-11-27 下午2:58:06
	 * @author <a href="bentengwu@163.com">伟宏</a>
	 * @param jsonArray
	 * @return BASE64编码的JSONARRAY 采用深度编码. 即,如果JSOANARRAY的对象是JSON对象的话,会进行深度遍历.
	 */
	private static JSONArray getBase64EecodeJSONArray(JSONArray jsonArray) {
		try {
			JSONArray resArray = new JSONArray();
			for (int j = 0; j < jsonArray.length(); j++) {
				Object obj = jsonArray.get(j);
				if (obj != null) {
					byte[] base64ArrayItemData = obj.toString().getBytes();
					JSONObject jsonObject = getJSONObject(base64ArrayItemData);
					if (jsonObject != null) {
						resArray.put(j, EncodeUtils
								.base64Encode(getBase64EecodeJSONObject(
										jsonObject).toString().getBytes()));
					} else {
						JSONArray array = getJSONArray(base64ArrayItemData);
						if (array != null) {
							resArray.put(j, EncodeUtils
									.base64Encode(getBase64EecodeJSONArray(
											array).toString().getBytes()));
						} else {
							resArray.put(j, EncodeUtils
									.base64Encode(base64ArrayItemData));
						}
					}
				} else {
					resArray.put(j, "");
				}
			}
			return resArray;
		} catch (JSONException jsonEx) {
			logger.error("", jsonEx);
			throw new RuntimeException("", jsonEx);
		}
	}

	/**
	 * 对JSONobject的所有VALUE进行BASE64解码. <br />
	 * 
	 * @date 2013-11-27 下午2:56:48
	 * @author <a href="bentengwu@163.com">伟宏</a>
	 * @param json
	 * @return 返回解码后的JSONObject
	 */
	public static JSONObject getBase64DecodeJSONObject(JSONObject json) {
		try {
			JSONArray jsonArray = json.names();
			for (int i = 0; i < jsonArray.length(); i++) {
				Object base64Object = json.get(jsonArray.getString(i));
				if (base64Object != null) {
					byte[] base64Data = EncodeUtils.base64Decode(base64Object
							.toString());
					JSONObject jsonObj = getJSONObject(base64Data);
					if (jsonObj != null) {
						/********************** 值对象是JSON||2013-11-27 ********************/
						json.put(jsonArray.getString(i),
								getBase64DecodeJSONObject(jsonObj));
					} else {
						JSONArray jsonArrays = getJSONArray(base64Data);
						if (jsonArrays != null) {
							/********************** 是JSON数组||2013-11-27 ********************/
							json.put(jsonArray.getString(i),
									getBase64DecodeJSONArray(jsonArrays));
						} else {
							/********************** 值对象是字符串||2013-11-27 ********************/
							json.put(jsonArray.getString(i), new String(
									base64Data));
						}
					}
				}
			}
			return json;
		} catch (JSONException jsonEx) {
			logger.error("", jsonEx);
			throw new RuntimeException("BASE64解码JSON OBJECT 异常", jsonEx);
		}
	}

	/**
	 * 对JSON对象的所有VALUE进行BASE64编码. <br />
	 * 
	 * @date 2013-11-27 下午2:55:46
	 * @author <a href="bentengwu@163.com">伟宏</a>
	 * @param json
	 * @return 返回BASE64编码后的JSON对象
	 */
	public static JSONObject getBase64EecodeJSONObject(JSONObject json) {
		try {
			JSONArray jsonArray = json.names();
			for (int i = 0; i < jsonArray.length(); i++) {
				Object base64Object = json.get(jsonArray.getString(i));
				if (base64Object != null) {
					JSONObject jsonObj = getJSONObject(base64Object.toString()
							.getBytes());
					if (jsonObj != null) {
						/********************** 值对象是JSON||2013-11-27 ********************/
						json.put(
								jsonArray.getString(i),
								EncodeUtils
										.base64Encode(getBase64EecodeJSONObject(
												jsonObj).toString().getBytes()));
					} else {
						JSONArray jsonArrays = getJSONArray(base64Object
								.toString().getBytes());
						if (jsonArrays != null) {
							/********************** 是JSON数组||2013-11-27 ********************/
							json.put(jsonArray.getString(i), EncodeUtils
									.base64Encode(getBase64EecodeJSONArray(
											jsonArrays).toString().getBytes()));
						} else {
							/********************** 值对象是字符串||2013-11-27 ********************/
							json.put(jsonArray.getString(i), EncodeUtils
									.base64Encode(base64Object.toString()
											.getBytes()));
						}
					}
				} else {
					json.put(jsonArray.getString(i), "");
				}
			}
			return json;
		} catch (JSONException jsonEx) {
			logger.error("", jsonEx);
			throw new RuntimeException("BASE64解码JSON OBJECT 异常", jsonEx);
		}
	}

	/**
	 * @param obj 实例对象
	 * @return 转化后的json对象
	 */
	public static JSONObject toJson(Object obj) {
		return toJson(obj, true, false);
	}
	/**
	 * @param obj 实例对象
	 * @param holdEmpty 是否保留空字符串对象
	 * @param holdNullWithEmptyStr 是否用空字符串保留null对象
	 * @return 转化后的json对象
	 */
	public static JSONObject toJson(Object obj, boolean holdEmpty, boolean holdNullWithEmptyStr) {
		try {
			return new JSONObject(toJsonStr(obj, holdEmpty, holdNullWithEmptyStr));
		} catch (JSONException e) {
			throw ExceptionUtils.newRE(e,true,logger);
		}
	}


	/**
	 * @param obj 实例对象
	 * @param holdEmptyStr 是否保留空字符串对象.
	 * @param holdNullWithEmptyStr 是否用空字符串保留null对象
	 * @return 转化后的json对象
	 */
	public static String toJsonStr(Object obj, boolean holdEmptyStr, boolean holdNullWithEmptyStr) {
		Gson gson = null;
		if(holdNullWithEmptyStr) gson = new GsonBuilder().serializeNulls().create();
		else	gson = new Gson();
		String json = gson.toJson(obj);

		if (holdNullWithEmptyStr) {
			json = StrUtils.replaceStr(json, ":null", ":\"\"");
		}

		if (!holdEmptyStr) {
			String regix = "(,?)(\"{1})[a-zA-Z0-9_]{0,200}\":\"\"(,?)";
			json = Pattern.compile(regix).matcher(json).replaceAll("");
		}

		return json;
	}

	/**
	 * @param obj 对象实例
	 * @return 字符串
	 */
	public static String toJsonStr(Object obj) {
		return toJsonStr(obj, true, false);
	}

	@SuppressWarnings("rawtypes")
	public static void main(String[] args) throws JSONException {
//		String path1 = "C:\\Users\\YangMeng\\Desktop\\ddd.jpg";
//		String path2 = "e:\\tmp\\2.png";
//		try {
//			byte[] fileData = FileUtils.readFileToByteArray(new File(path1));
//			System.out.println("发送是的字节数:"+fileData.length);
//			String fileHexData = EncodeUtils.hexEncode(fileData);  //16位Str
//			
//			String fileBase64HexData = EncodeUtils.base64Encode(fileHexData.getBytes());
//			
//			System.out.println(fileBase64HexData);
//			JSONObject json = new JSONObject();
//			json.put("headCode", fileBase64HexData);
//			System.out.println(json);
////			json = getBase64DecodeJSONObject(json);
////			fileHexData = json.getString("headCode");
////			fileData = EncodeUtils.hexDecode(fileHexData);
////			OutputStream out = new FileOutputStream(new File("C:\\Users\\YangMeng\\Desktop\\ddddddddfsdfsf.jpg"));
////			out.write(fileData);
//			System.out.println("接收时的字节数"+fileData.length);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
		String json = "{\"result\":[{\"id\":\"154\",\"letter\":\"h\",\"name\":\"杭州\",\"spell\":\"null\"},{\"id\":\"160\",\"letter\":\"j\",\"name\":\"金华\",\"spell\":\"null\"}],\"code\":\"0000\"}";
		Map<String, Object> map = JsonUtil.jsonToMap(new JSONObject(json));
		System.out.println(map);
	}
}
