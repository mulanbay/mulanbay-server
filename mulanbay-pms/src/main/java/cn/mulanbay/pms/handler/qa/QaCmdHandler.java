package cn.mulanbay.pms.handler.qa;

import cn.mulanbay.common.exception.CommonResult;
import cn.mulanbay.common.util.StringUtil;
import cn.mulanbay.pms.handler.CommandHandler;
import cn.mulanbay.pms.handler.SystemConfigHandler;
import cn.mulanbay.pms.handler.qa.bean.QaMatch;
import cn.mulanbay.pms.persistent.domain.CommandConfig;
import cn.mulanbay.pms.persistent.domain.UserRole;
import cn.mulanbay.pms.persistent.service.AuthService;
import cn.mulanbay.pms.persistent.service.CommandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * QA的命令处理器
 *
 * @author fenghong
 * @create 2017-07-10 21:44
 */
@Component
public class QaCmdHandler extends AbstractQaMessageHandler {

    private static Pattern CMD_PATTERN = Pattern.compile("(?<=\\D)\\d{4}(?!\\d)");

    @Autowired
    AuthService authService;

    @Autowired
    SystemConfigHandler systemConfigHandler;

    @Autowired
    CommandService commandService;

    @Autowired
    CommandHandler commandHandler;

    public QaCmdHandler() {
        super("cmd", "QA的命令处理器");
    }

    @Override
    public String handleMsg(QaMatch match) {
        //todo 该功能点目前写死，后期可以配置
        boolean auth = this.checkAuth(match.getUserId(), 847L);
        if (!auth) {
            return "您没有相关权限";
        }
        String keywords = match.getKeywords();
        String cmdCode = this.parseCmdCode(keywords);
        if (StringUtil.isEmpty(cmdCode) || "0000".equals(cmdCode)) {
            return getCmdListHelp();
        } else {
            //发送命令
            CommandConfig cc = commandService.getCommandConfigByScode(cmdCode);
            if (cc == null) {
                return "不支持的命令代码" + cmdCode + ",请输入正确的四位命令代码";
            } else {
                CommonResult cr = commandHandler.handleCmd(cc, true, match.getUserId());
                return "命令" + cc.getScode() + "[" + cc.getName() + "]执行结果:" + cr.getInfo() + ",返回代码:" + cr.getCode();
            }
        }
    }

    private String getCmdListHelp() {
        //发送命令列表
        List<CommandConfig> ccList = baseService.getBeanList(CommandConfig.class, 0, 0, null);
        StringBuffer sb = new StringBuffer();
        sb.append("目前支持的命令列表:\n");
        for (CommandConfig cc : ccList) {
            sb.append(cc.getScode() + ":" + cc.getName() + "\n");
        }
        sb.append("请输入具体的命令代码，例如:发送命令1006\n");
        return sb.toString();
    }

    /**
     * 检查授权
     *
     * @param userId
     * @param fid    功能点编号
     * @return
     */
    private boolean checkAuth(Long userId, Long fid) {
        List<UserRole> userRoleList = authService.selectUserRoleList(userId);
        if (StringUtil.isEmpty(userRoleList)) {
            return false;
        }
        Long roleId = userRoleList.get(0).getRoleId();
        return systemConfigHandler.isRoleAuth(roleId, fid);
    }

    /**
     * 解析出命令码
     *
     * @param str
     * @return
     */
    private String parseCmdCode(String str) {
        Matcher matcher = CMD_PATTERN.matcher(str);
        if (matcher.find()) {
            String s = matcher.group();
            return s;
        }
        return null;
    }
}
