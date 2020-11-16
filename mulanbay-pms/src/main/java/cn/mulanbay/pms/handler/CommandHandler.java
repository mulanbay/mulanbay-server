package cn.mulanbay.pms.handler;

import cn.mulanbay.business.handler.BaseHandler;
import cn.mulanbay.business.handler.lock.DistributedLock;
import cn.mulanbay.common.config.OSType;
import cn.mulanbay.common.exception.CommonResult;
import cn.mulanbay.common.thread.CommandExecuteThread;
import cn.mulanbay.common.util.CommandUtil;
import cn.mulanbay.persistent.service.BaseService;
import cn.mulanbay.pms.common.CacheKey;
import cn.mulanbay.pms.common.PmsErrorCode;
import cn.mulanbay.pms.persistent.domain.CommandConfig;
import cn.mulanbay.pms.persistent.enums.CommonStatus;
import cn.mulanbay.pms.persistent.service.CommandService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 命令处理
 */
@Component
public class CommandHandler extends BaseHandler {

    private static final Logger logger = LoggerFactory.getLogger(CommandHandler.class);

    @Autowired
    PmsNotifyHandler pmsNotifyHandler;

    @Autowired
    DistributedLock distributedLock;

    @Autowired
    BaseService baseService;

    @Autowired
    CommandService commandService;

    public CommandHandler() {
        super("命令处理");
    }


    public CommonResult handleCmd(Long id, boolean sync, Long userId) {
        CommandConfig cc = baseService.getObject(CommandConfig.class, id);
        return handleCmd(cc, sync, userId);
    }

    public CommonResult handleCmdScode(String scode, boolean sync, Long userId) {
        CommandConfig cc = commandService.getCommandConfigByScode(scode);
        return handleCmd(cc, sync, userId);
    }

    public CommonResult handleCmdCode(String code, boolean sync, Long userId) {
        CommandConfig cc = commandService.getCommandConfigByCode(code);
        return handleCmd(cc, sync, userId);
    }

    public CommonResult handleCmd(CommandConfig cc, boolean sync, Long userId) {
        CommonResult cr = new CommonResult();
        if (cc.getStatus() == CommonStatus.DISABLE) {
            cr.setCode(PmsErrorCode.CMD_DISABLED);
            return cr;
        }
        String key = CacheKey.getKey(CacheKey.CMD_SEND_LOCK, cc.getId().toString());
        try {
            boolean b = distributedLock.lock(key, 5000L, 3, 20);
            if (!b) {
                //cr.setCode(-1);
                cr.setInfo("相同任务可能还在执行，请稍后再试!");
                return cr;
            }
            if (sync) {
                String s = CommandUtil.executeCmd(OSType.UNKNOWN, cc.getUrl());
                cr.setInfo(s);
                return cr;
            } else {
                CommandExecuteThread thread = new CommandExecuteThread(cc.getUrl(), OSType.UNKNOWN, pmsNotifyHandler);
                thread.start();
                cr.setInfo("请稍后检查执行结果!");
                return cr;
            }
        } catch (Exception e) {
            logger.error("处理命令ID=" + cc.getId() + "异常", e);
            cr.setInfo("处理异常!" + e.getMessage());
            return cr;
        } finally {
            //不释放锁,避免同一个命令被连续执行
            //distributedLock.releaseLock(key);
        }
    }
}
