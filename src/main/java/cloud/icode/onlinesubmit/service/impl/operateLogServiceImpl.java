package cloud.icode.onlinesubmit.service.impl;

import cloud.icode.onlinesubmit.dao.OperateLogMapper;
import cloud.icode.onlinesubmit.enums.AppHttpCodeEnum;
import cloud.icode.onlinesubmit.exception.CustomException;
import cloud.icode.onlinesubmit.model.OperateLog;
import cloud.icode.onlinesubmit.service.OperateLogService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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
}
