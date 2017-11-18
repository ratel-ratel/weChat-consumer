package cn.vpclub.shm.shcmcc.consumer.execption;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * Created by nice on 2017/11/15.
 */
public class ToStringUtils {
    public static final ToStringStyle THE_STYLE = new ToStringUtils.SimpleMultiLineToStringStyle();

    public ToStringUtils() {
    }

    public static String toSimpleString(Object obj) {
        String toStringResult = ToStringBuilder.reflectionToString(obj, THE_STYLE);
        String[] split = toStringResult.split("\n");
        StringBuilder result = new StringBuilder();
        String[] var4 = split;
        int var5 = split.length;

        for(int var6 = 0; var6 < var5; ++var6) {
            String string = var4[var6];
            if(!string.endsWith("<null>")) {
                result.append(string + "\n");
            }
        }

        if(result.length() == 0) {
            return "";
        } else if(StringUtils.countMatches(result, "\n") == 2) {
            return result.toString().split("\n")[0] + "<all null values>]";
        } else {
            return result.deleteCharAt(result.length() - 1).toString();
        }
    }

    private static class SimpleMultiLineToStringStyle extends ToStringStyle {
        private static final long serialVersionUID = 4645306494220335355L;
        private static final String LINE_SEPARATOR = "\n";
        private static final String NULL_TEXT = "<null>";

        public SimpleMultiLineToStringStyle() {
            this.setContentStart("[");
            this.setFieldSeparator("\n  ");
            this.setFieldSeparatorAtStart(true);
            this.setContentEnd("\n]");
            this.setNullText("<null>");
            this.setUseShortClassName(true);
            this.setUseIdentityHashCode(false);
        }
    }
}
