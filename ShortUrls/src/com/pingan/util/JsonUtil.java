package com.pingan.util;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import net.sf.json.JSON;
import net.sf.json.JSONArray;
import net.sf.json.JSONNull;
import net.sf.json.JSONObject;
import net.sf.json.JSONSerializer;
import net.sf.json.JsonConfig;
import net.sf.json.util.JSONUtils;
import net.sf.json.xml.XMLSerializer;

/**
 * @author 作者 E-mail:
 * @version 创建时间：2015-5-10 下午2:50:01 类说明
 */
public class JsonUtil {


	private static XMLSerializer serializer = new XMLSerializer();

	static {
		serializer.setRootName("root");
		serializer.setTypeHintsEnabled(false);
		serializer.setTrimSpaces(true);
	}

	//
	public static boolean isJSON(String jsonString) {
		return JSONUtils.mayBeJSON(jsonString);
	}

	//
	public static Map<String, Object> toMap(String jsonString) {
		return toMap(toJSONObject(jsonString));
	}

	//
	public static Map<String, Object> toMap(InputStream is) {
		return toMap(toJSONObject(is));
	}

	//
	public static Map<String, Object> toMap(JSONObject jsonObject) {
		// return JSONUtils.getProperties(jsonObject);
		Map<String, Object> map = new HashMap<String, Object>();
		@SuppressWarnings("rawtypes")
		Iterator keys = jsonObject.keys();
		String key = null;
		Object value = null;
		while (keys.hasNext()) {
			key = keys.next().toString();
			value = jsonObject.get(key);
			if (value == null) {
				continue;
			}
			if (value instanceof JSONNull) {
				continue;
			} else if (value instanceof JSONObject) {
				if (((JSONObject) value).isNullObject()) {
					continue;
				}
				if (((JSONObject) value).isEmpty()) {
					continue;
				}
			} else if (value instanceof JSONArray) {
				if (((JSONArray) value).isEmpty()) {
					continue;
				}
			}
			if (value instanceof Number) {
				map.put(key, value.toString());
			} else {
				map.put(key, value);
			}
		}
		return map;
	}

	//
	@SuppressWarnings({ "rawtypes", "deprecation" })
	public static List toList(Object object, Class objectClass) {
		if (object instanceof JSONArray) {
			return (List) JSONArray.toList((JSONArray) object, objectClass);
		} else {
			return null;
		}

	}

	//
	public static JSONArray toJSONArray(String jsonString) {
		return JSONArray.fromObject(jsonString);
	}

	//
	public static JSONArray toJSONArray(Object obj) {
		return JSONArray.fromObject(obj);
	}

	//
	public static JSONObject toJSONObject(InputStream is) {
		return ((JSONObject) serializer.readFromStream(is));
	}

	public static JSON toJSON(InputStream is) {
		return serializer.readFromStream(is);
	}

	//
	public static JSONObject toJSONObject(String jsonString) {
		return JSONObject.fromObject(jsonString);
	}

	//
	public static String toXmlString(String jsonString) {
		return serializer.write(toJSONObject(jsonString));
	}

	public static InputStream toStream(String xmlString) {
		InputStream is = null;
		try {
			is = new ByteArrayInputStream(xmlString.getBytes("UTF-8"));
		} catch (UnsupportedEncodingException e) {
		}
		return is;
	}

	public static String toXmlString(JSONObject jsonObject) {
		return serializer.write(jsonObject);
	}

	public static String toXmlString(JSONObject jsonObject, String subName) {
		if (subName != null && !"".equals(subName)) {
			serializer.setElementName(subName);
		}
		return serializer.write(jsonObject);
	}

	//
	public static Object toJava(JSON json) {
		return JSONSerializer.toJava(json);
	}

	//
	public static Object toJava(JSON json, JsonConfig jsonConfig) {
		return JSONSerializer.toJava(json, jsonConfig);
	}

	//
	public static JSONObject toJSONObject(Object object) {
		JsonConfig jsonConfig = new JsonConfig();
		return (JSONObject) JSONSerializer.toJSON(object, jsonConfig);
	}

	public static InputStream toByteArrayStream(String string) {
		InputStream is = null;
		try {
			is = new ByteArrayInputStream(string.getBytes("UTF-8"));
		} catch (UnsupportedEncodingException e) {
		}
		return is;
	}

	public static void main(String[] args) {
		String str = "{\"user\":{\"id\":\"123\",\"name\":\"张三\",\"say\":\"Hello , i am a action to print a json!\",\"password\":\"JSON\"},\"success\":true}";
		JSONObject jo = toJSONObject(str);
		System.out.println(jo);
	}

}
