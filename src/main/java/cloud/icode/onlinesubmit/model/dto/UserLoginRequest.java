package cloud.icode.onlinesubmit.model.dto;

import lombok.Data;

/**
 * 作者: 杨振坤
 * 创建日期: 2025/1/5
 * 文件描述: UserLoginRequest
 */
@Data
public class UserLoginRequest {
    private String username;
    private String password;
}
