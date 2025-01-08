package cloud.icode.onlinesubmit.model.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 作者: 杨振坤
 * 创建日期: 2025/1/5
 * 文件描述: UserLoginRequest
 */
@Data
@ApiModel(description = "用户登录请求体")
public class UserLoginRequest {

    /**
     * 用户名
     */
    @ApiModelProperty(name = "用户名",required = true)
    private String username;

    /**
     * 密码
     */
    @ApiModelProperty(name = "密码",required = true)
    private String password;
}
