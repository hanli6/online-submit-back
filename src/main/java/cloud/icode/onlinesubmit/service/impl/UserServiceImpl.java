package cloud.icode.onlinesubmit.service.impl;

import cloud.icode.onlinesubmit.constant.UserConstant;
import cloud.icode.onlinesubmit.dao.UserMapper;
import cloud.icode.onlinesubmit.model.User;
import cloud.icode.onlinesubmit.model.UserExample;
import cloud.icode.onlinesubmit.model.dto.UserLoginRequest;
import cloud.icode.onlinesubmit.model.vo.UserVo;
import cloud.icode.onlinesubmit.service.UserService;
import cn.hutool.core.bean.BeanUtil;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 作者: 杨振坤
 * 创建日期: 2025/1/5 下午5:53
 * 文件描述: UserServiceimpl
 */
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserMapper userMapper;

    @Override
    public UserVo userLogin(UserLoginRequest userLoginRequest, HttpServletRequest request) {
        //数据校验
        if (userLoginRequest == null) {
            return null;
        }
        String username = userLoginRequest.getUsername();
        String password = userLoginRequest.getPassword();
        if(StringUtils.isAnyEmpty(username,password)){
            return null;
        }

        //数据库查询
        UserExample userExample=new UserExample();
        userExample.createCriteria().andUsernameEqualTo(username);
        userExample.createCriteria().andPasswordEqualTo(password);

        List<User> userList = userMapper.selectByExample(userExample);
        if (userList == null || userList.isEmpty()) {
            return null;
        }
        User user = userList.get(0);
        UserVo userVo = BeanUtil.copyProperties(user, UserVo.class);

        //将用户信息存储在Session中
        request.getSession().setAttribute(UserConstant.USER_LOGIN_STATE,userVo);
        return userVo;
    }
}
