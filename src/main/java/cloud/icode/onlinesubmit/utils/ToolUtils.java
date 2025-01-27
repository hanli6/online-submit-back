package cloud.icode.onlinesubmit.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 作者: 杨振坤
 * 创建日期: 2025/1/27 下午7:42
 * 文件描述: 工具类
 */
public class ToolUtils {
    /**
     * yyyyMMdd日期格式
     */
    public static final SimpleDateFormat yyyyMMddFormat = new SimpleDateFormat("yyyyMMdd");

    /**
     * 将当前日期转化为yyyyMMdd格式
     * @return
     */
    public static String getYYYYMMddString() {
        return yyyyMMddFormat.format(new Date());
    }
}
