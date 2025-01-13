package cloud.icode.onlinesubmit.service.impl;

import cloud.icode.onlinesubmit.common.CommonConstant;
import cloud.icode.onlinesubmit.dao.OperateLogMapper;
import cloud.icode.onlinesubmit.enums.AppHttpCodeEnum;
import cloud.icode.onlinesubmit.exception.CustomException;
import cloud.icode.onlinesubmit.model.OperateLog;
import cloud.icode.onlinesubmit.model.OperateLogExample;
import cloud.icode.onlinesubmit.model.vo.OperateLogVo;
import cloud.icode.onlinesubmit.service.OperateLogService;
import cn.hutool.core.bean.BeanUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 作者: 杨振坤
 * 创建日期: 2025/1/10 下午9:43
 * 文件描述: operateLogServiceImpl
 */
@Service
@RequiredArgsConstructor
public class operateLogServiceImpl implements OperateLogService {

    private final OperateLogMapper operateLogMapper;

    @Override
    public int saveLog(OperateLog operateLog) {
        //参数校验
        if (operateLog == null) {
            throw new CustomException(AppHttpCodeEnum.SERVER_ERROR);
        }

        //插入数据库
        int count = operateLogMapper.insertSelective(operateLog);
        if (count == 0) {
            throw new CustomException(AppHttpCodeEnum.PARAM_INVALID);
        }
        return count;
    }

    /**
     * 查询日志列表
     *
     * @return
     */
    @Override
    public List<OperateLogVo> operateLogList() {
        //获取日志
        OperateLogExample example = new OperateLogExample();
        example.createCriteria().andIsDeleteEqualTo(CommonConstant.NO_DELETE);
        example.setOrderByClause("id desc limit 8");
        List<OperateLog> operateLogs = operateLogMapper.selectByExample(example);

        //返回结果
        List<OperateLogVo> result = new ArrayList<OperateLogVo>();
        if (operateLogs == null || operateLogs.isEmpty()) {
            return Collections.emptyList();
        } else {
            operateLogs.forEach(item -> {
                OperateLogVo operateLogVo = BeanUtil.copyProperties(item, OperateLogVo.class);
                result.add(operateLogVo);
            });
        }

        return result;
    }


}
