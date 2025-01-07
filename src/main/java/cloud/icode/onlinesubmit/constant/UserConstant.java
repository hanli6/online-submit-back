package cloud.icode.onlinesubmit.constant;

/**
 * 作者: 杨振坤
 * 创建日期: 2025/1/5 下午9:35
 * 文件描述: 用户常量
 */
public class UserConstant {
    /**
     * 用户登录状态
     */
    public final static String USER_LOGIN_STATE = "user_login_state";

    /**
     * 非法字符正则
     */
    public static final String ILLEGAL_CHARACTER = "^[\\\\w\\\\u4e00-\\\\u9fa5\\\\s]+$";

    /**
     * 加密盐值
     */
    public static final String SALT = "online_submit_system";
}
