package cloud.icode.onlinesubmit.controller;

import cloud.icode.onlinesubmit.model.dto.UserLoginRequest;
import cloud.icode.onlinesubmit.model.vo.UserVo;
import cloud.icode.onlinesubmit.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * 作者: 杨振坤
 * 创建日期: 2025/1/5
 * 文件描述: UserController
 */
@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
@CrossOrigin(origins = {"http://localhost:8081"},allowCredentials = "true")
public class UserController {
    private final UserService userService;

    @PostMapping("/login")
    public Map<String, Object> login(@RequestBody UserLoginRequest userLoginRequest, HttpServletRequest request) {
        Map<String,Object> map = new HashMap<>();
        //参数校验
        if (request==null){
            map.put("code","40000");
            map.put("message","参数不合法");
            return map;
        }

        //封装数据
        UserVo userVo = userService.userLogin(userLoginRequest, request);
        map.put("code","2000");
        map.put("message","ok");
        map.put("data",userVo);

        return map;
    }

}
