package cloud.icode.onlinesubmit.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 作者: 杨振坤
 * 创建日期: 2025/1/6 下午7:15
 * 文件描述: 用户角色枚举
 */
@Getter
@AllArgsConstructor
public enum ManuscriptEnum {
    WAIT_AUDIT(0,"待审核"),
    FIRST_AUDIT(1,"初审通过"),
    FIRST_FAIL(2,"初审拒绝"),
    SECOND_AUDIT(3,"终审通过"),
    SECOND_FAIL(3,"终审拒绝"),
    ;

    private final int value;
    private final String name;

    public static ManuscriptEnum getUserEnum(int value) {
        if(value<0 || value>3){
            return null;
        }
        for (ManuscriptEnum userEnum : ManuscriptEnum.values()) {
            if (userEnum.getValue() == value) {
                return userEnum;
            }
        }

        return null;
    }
}
