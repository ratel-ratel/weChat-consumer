package cn.vpclub.shm.shcmcc.consumer.util;


import cn.vpclub.moses.utils.common.Encodes;
import cn.vpclub.moses.utils.common.JsonUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.util.SortedMap;
import java.util.TreeMap;

/**
 * Created by Shijinhua on 2017/4/8.
 */
@Slf4j
@Service
public class SignBaseData {
    private SortedMap<String, Object> _values = new TreeMap<String, Object>();

    // 设置某个字段的值
    public void setValue(String key, Object value) {
        _values.put(key, value);
    }

    // 获取某个字段的Object值
    public Object getValue(String key) {
        return _values.get(key);
    }

    // 获取某个字段的String值
    public String getStrValue(String key) {
        Object val = getValue(key);
        if (val != null) {
            return val.toString();
        }
        return "";
    }

    public SortedMap<String, Object> getValues() {
        return _values;
    }

    // 获取某个字段的Integer值
    public Integer getIntValue(String key) {
        String val = getStrValue(key);
        if (val != null && !val.isEmpty()) {
            return Integer.parseInt(val);
        }
        return 0;
    }

    public String makeSign(String key, String algorithm) {
        // 转url格式
        String content = toUrl(false, false);

        // 在string后加入API KEY
        content += "&key=" + key;
        // 加密
        String result = Encodes.encodeToAlgorithm(content, algorithm);
        return result.toUpperCase();
    }

    public String toUrl(Boolean urlEncode, Boolean containSign) {
        StringBuilder sb = new StringBuilder();
        for (String key : _values.keySet()) {
            // 排除不需要转换成Url格式的key
            if (!containSign && "sign".equals(key)) {
                continue;
            }

            // 将非空key-value,转换成URL模式
            Object value = _values.get(key);
            if (value != null && !value.toString().isEmpty()) {
                if (urlEncode) {
                    try {
                        value = java.net.URLEncoder.encode(value.toString(), "utf-8");
                    } catch (UnsupportedEncodingException ex) {
                        //logger.error("Fail to URLEncode " + ex.getMessage());
                    }
                }
                sb.append(key + "=" + value + "&");
            }
        }

        // 去除最后一个字符
        if (sb.length() > 0) {
            sb.delete(sb.length() - 1, sb.length());
        }
        return sb.toString();
    }

    public String toJson(){
        return JsonUtil.objectToJson(_values);
    }

    public void fromJson(String json) {
        _values = JsonUtil.jsonToObject(json, SortedMap.class);
    }
}
