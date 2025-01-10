package cloud.icode.onlinesubmit.service;

import cloud.icode.onlinesubmit.model.OperateLog;

/**
 * 作者: 杨振坤
 * 创建日期: 2025/1/10 下午9:43
 * 文件描述: operateLogService
 */
public interface OperateLogService {

    /**
     * 将日志信息储存在数据库中
     * @param operateLog
     * @return
     */
    public int saveLog(OperateLog operateLog);
}
