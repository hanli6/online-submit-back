package cloud.icode.onlinesubmit.service.impl;

import cloud.icode.onlinesubmit.common.CommonConstant;
import cloud.icode.onlinesubmit.constant.UserConstant;
import cloud.icode.onlinesubmit.dao.MenuMapper;
import cloud.icode.onlinesubmit.dao.UserMapper;
import cloud.icode.onlinesubmit.enums.AppHttpCodeEnum;
import cloud.icode.onlinesubmit.exception.CustomException;
import cloud.icode.onlinesubmit.model.Menu;
import cloud.icode.onlinesubmit.model.MenuExample;
import cloud.icode.onlinesubmit.model.User;
import cloud.icode.onlinesubmit.model.UserExample;
import cloud.icode.onlinesubmit.model.dto.UserLoginRequest;
import cloud.icode.onlinesubmit.model.dto.UserRegisterRequest;
import cloud.icode.onlinesubmit.model.vo.MenuVo;
import cloud.icode.onlinesubmit.model.vo.UserVo;
import cloud.icode.onlinesubmit.service.UserService;
import cn.hutool.core.bean.BeanUtil;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

/**
 * 作者: 杨振坤
 * 创建日期: 2025/1/5 下午5:53
 * 文件描述: UserServiceimpl
 */
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private static final Logger log = LoggerFactory.getLogger(UserServiceImpl.class);
    private final UserMapper userMapper;
    private final MenuMapper menuMapper;

    @Override
    public Map<String, Object> userLogin(UserLoginRequest userLoginRequest, HttpServletRequest request, HttpServletResponse response) {
        //数据校验
        if (userLoginRequest == null) {
            throw new CustomException(AppHttpCodeEnum.PARAM_INVALID);
        }

        //查询用户
        String username = userLoginRequest.getUsername();
        String password = userLoginRequest.getPassword();
        if (StringUtils.isAnyEmpty(username, password)) {
            throw new CustomException(AppHttpCodeEnum.PARAM_INVALID);
        }

        //数据库查询
        UserExample userExample = new UserExample();
        userExample.createCriteria().andUsernameEqualTo(username)
                .andPasswordEqualTo(DigestUtils.md5DigestAsHex((UserConstant.SALT + password).getBytes()))
                .andIsDeleteEqualTo(CommonConstant.NO_DELETE);

        List<User> userList = userMapper.selectByExample(userExample);
        if (userList == null || userList.isEmpty()) {
            throw new CustomException(AppHttpCodeEnum.USER_DATA_NOT_EXIST);
        }
        User user = userList.get(0);
        UserVo userVo = BeanUtil.copyProperties(user, UserVo.class);

        //获取当前登录用户菜单树
        MenuExample menuExample = new MenuExample();
        menuExample.createCriteria().andUserIdEqualTo(user.getId())
                .andIsDeleteEqualTo(CommonConstant.NO_DELETE);
        List<Menu> menus = menuMapper.selectByExample(menuExample);

        //获取一级菜单,将其存储在menuList中
        List<MenuVo> menuVoList = new ArrayList<>();
        menus.stream().forEach(menu -> {
            if (menu.getParentId() == null) {
                MenuVo menuVo = BeanUtil.copyProperties(menu, MenuVo.class);
                menuVoList.add(menuVo);
            }
        });

        //将二级菜单存储在一级菜单的子菜单属性下
        menuVoList.stream().forEach(menuVo -> {
            //过滤出子菜单，将子菜单赋值给父菜单的Children属性
            menus.stream().filter(menuFilter -> {
                return menuFilter.getParentId() != null;
            }).forEach(menuChildren -> {
                if (Objects.equals(menuVo.getId(), menuChildren.getParentId())) {
                    if (menuVo.getChildren() == null) {
                        menuVo.setChildren(new ArrayList<>());
                    }
                    MenuVo menuVoChildren = BeanUtil.copyProperties(menuChildren, MenuVo.class);
                    menuVo.getChildren().add(menuVoChildren);
                }
            });
        });

        //整和数据
        Map<String, Object> result = new HashMap<>();
        result.put("userVo", userVo);
        result.put("menuVoList", menuVoList);

        //将用户信息存储在token中
//        String token = AppJwtUtil.getToken(user.getId());
//        result.put("token", token);
        //将用户信息存储在session中
        request.getSession().setAttribute(UserConstant.USER_LOGIN_STATE, userVo);
        return result;
    }

    /**
     * 注册用户
     *
     * @param userRegisterRequest
     * @return
     */
    @Override
    public int registerUser(UserRegisterRequest userRegisterRequest) {
        //判断是否已经注册
        UserExample userExample = new UserExample();
        userExample.createCriteria().andUsernameEqualTo(userRegisterRequest.getUsername());
        List<User> userList = userMapper.selectByExample(userExample);
        if (!(userList == null || userList.isEmpty())) {
            throw new CustomException(AppHttpCodeEnum.USER_EXIST);
        }

        //密码进行加密
        String md5DigestAsHex = DigestUtils.md5DigestAsHex((UserConstant.SALT + userRegisterRequest.getPassword()).getBytes());

        //更换数据类型
        User user = BeanUtil.copyProperties(userRegisterRequest, User.class);
        user.setPassword(md5DigestAsHex);
        return userMapper.insertSelective(user);
    }

    /**
     * 查询用户信息
     *
     * @param userId
     * @return
     */
    @Override
    public UserVo getUserInfo(Long userId) {
        //参数校验
        if (userId == null) {
            return null;
        }

        //查询用户信息
        User user = userMapper.selectByPrimaryKey(userId);
        if (user == null) {
            return null;
        }
        return BeanUtil.copyProperties(user, UserVo.class);
    }
}
