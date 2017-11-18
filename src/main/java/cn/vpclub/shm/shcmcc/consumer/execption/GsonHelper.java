package cn.vpclub.shm.shcmcc.consumer.execption;

import com.google.common.collect.Lists;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.util.List;

/**
 * Created by nice on 2017/11/15.
 */
public class GsonHelper {
    public GsonHelper() {
    }

    public static boolean isNull(JsonElement element) {
        return element == null || element.isJsonNull();
    }

    public static boolean isNotNull(JsonElement element) {
        return !isNull(element);
    }

    public static Long getLong(JsonObject json, String property) {
        return getAsLong(json.get(property));
    }

    public static long getPrimitiveLong(JsonObject json, String property) {
        return getAsPrimitiveLong(json.get(property));
    }

    public static Integer getInteger(JsonObject json, String property) {
        return getAsInteger(json.get(property));
    }

    public static int getPrimitiveInteger(JsonObject json, String property) {
        return getAsPrimitiveInt(json.get(property));
    }

    public static Double getDouble(JsonObject json, String property) {
        return getAsDouble(json.get(property));
    }

    public static double getPrimitiveDouble(JsonObject json, String property) {
        return getAsPrimitiveDouble(json.get(property));
    }

    public static Float getFloat(JsonObject json, String property) {
        return getAsFloat(json.get(property));
    }

    public static float getPrimitiveFloat(JsonObject json, String property) {
        return getAsPrimitiveFloat(json.get(property));
    }

    public static Boolean getBoolean(JsonObject json, String property) {
        return getAsBoolean(json.get(property));
    }

    public static String getString(JsonObject json, String property) {
        return getAsString(json.get(property));
    }

    public static String getAsString(JsonElement element) {
        return isNull(element)?null:element.getAsString();
    }

    public static Long getAsLong(JsonElement element) {
        return isNull(element)?null:Long.valueOf(element.getAsLong());
    }

    public static long getAsPrimitiveLong(JsonElement element) {
        Long r = getAsLong(element);
        return r == null?0L:r.longValue();
    }

    public static Integer getAsInteger(JsonElement element) {
        return isNull(element)?null:Integer.valueOf(element.getAsInt());
    }

    public static int getAsPrimitiveInt(JsonElement element) {
        Integer r = getAsInteger(element);
        return r == null?0:r.intValue();
    }

    public static Boolean getAsBoolean(JsonElement element) {
        return isNull(element)?null:Boolean.valueOf(element.getAsBoolean());
    }

    public static boolean getAsPrimitiveBool(JsonElement element) {
        Boolean r = getAsBoolean(element);
        return r != null && r.booleanValue();
    }

    public static Double getAsDouble(JsonElement element) {
        return isNull(element)?null:Double.valueOf(element.getAsDouble());
    }

    public static double getAsPrimitiveDouble(JsonElement element) {
        Double r = getAsDouble(element);
        return r == null?0.0D:r.doubleValue();
    }

    public static Float getAsFloat(JsonElement element) {
        return isNull(element)?null:Float.valueOf(element.getAsFloat());
    }

    public static float getAsPrimitiveFloat(JsonElement element) {
        Float r = getAsFloat(element);
        return r == null?0.0F:r.floatValue();
    }

    public static Integer[] getIntArray(JsonObject o, String string) {
        JsonArray jsonArray = getAsJsonArray(o.getAsJsonArray(string));
        if(jsonArray == null) {
            return null;
        } else {
            List<Integer> result = Lists.newArrayList();

            for(int i = 0; i < jsonArray.size(); ++i) {
                result.add(Integer.valueOf(jsonArray.get(i).getAsInt()));
            }

            return (Integer[])result.toArray(new Integer[0]);
        }
    }

    public static JsonArray getAsJsonArray(JsonElement element) {
        return element == null?null:element.getAsJsonArray();
    }
}
