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
public enum UserEnum {
    ADMIN(0,"管理员"),
    AUTHOR(1,"作者"),
    EXPERT(2,"专家"),
    EDIT(3,"编辑"),
    ;

    private final int value;
    private final String role;

    public static UserEnum getUserEnum(int value) {
        if(value<0 || value>3){
            return null;
        }
        for (UserEnum userEnum : UserEnum.values()) {
            if (userEnum.getValue() == value) {
                return userEnum;
            }
        }

        return null;
    }
}
