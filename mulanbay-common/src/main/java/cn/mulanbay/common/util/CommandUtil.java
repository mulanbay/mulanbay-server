package cn.mulanbay.common.util;

import cn.mulanbay.common.config.OSType;
import cn.mulanbay.common.exception.ApplicationException;
import cn.mulanbay.common.exception.ErrorCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Properties;

/**
 * 操作系统命令处理
 *
 * @author fenghong
 * @create 2018-01-20 21:44
 */
public class CommandUtil {

	private static final Logger logger = LoggerFactory.getLogger(CommandUtil.class);

	/**
	 * 执行LINUX操作系统命令
	 *
	 * @param cmd
	 * @return
	 */
	public static String executeLinuxCmd(String cmd) {
		InputStreamReader read = null;
		BufferedReader bufferedReader = null;
		InputStream in = null;
		try {
			String[] cmdA = {"/bin/sh", "-c", cmd};
			Process p = Runtime.getRuntime().exec(cmdA);
			in = p.getInputStream();
			read = new InputStreamReader(in, "utf-8");// 考虑到编码格式
			bufferedReader = new BufferedReader(read);
			StringBuffer sb = new StringBuffer();
			String line;
			while ((line = bufferedReader.readLine()) != null) { // 按行打印输出内容
				sb.append(line);
			}
			return sb.toString();
		} catch (Exception e) {
			logger.error("执行命令[" + cmd + "]异常：", e);
			throw new ApplicationException(ErrorCode.EXECUTE_CMD_ERROR, "执行命令"
					+ cmd + "异常：" + e.getMessage(), e);
		} finally {
			try {
				if (bufferedReader != null) {
					bufferedReader.close();
				}
				if (read != null) {
					read.close();
				}
				if (in != null) {
					in.close();
				}
			} catch (Exception e) {
				logger.error("关闭命令[" + cmd + "]流异常：", e);
			}
		}
	}

	/**
	 * 执行WINDOWS操作系统命令
	 *
	 * @param cmd
	 * @return
	 */
	public static String executeWindowsCmd(String cmd) {
		InputStreamReader read = null;
		BufferedReader bufferedReader = null;
		InputStream in = null;
		try {
			String[] cmdA = new String[]{"cmd.exe", "/C", cmd};
			Process p = Runtime.getRuntime().exec(cmdA);
			in = p.getInputStream();
			read = new InputStreamReader(in, "utf-8");// 考虑到编码格式
			bufferedReader = new BufferedReader(read);
			StringBuffer sb = new StringBuffer();
			String line;
			while ((line = bufferedReader.readLine()) != null) { // 按行打印输出内容
				sb.append(line);
			}
			return sb.toString();
		} catch (Exception e) {
			logger.error("执行命令[" + cmd + "]异常：", e);
			return "执行异常：" + e.getMessage();
		} finally {
			try {
				if (bufferedReader != null) {
					bufferedReader.close();
				}
				if (read != null) {
					read.close();
				}
				if (in != null) {
					in.close();
				}
			} catch (Exception e) {
				logger.error("关闭命令[" + cmd + "]流异常：", e);
			}
		}
	}

	/**
	 *
	 * @param osType
	 *            操作系统类型 @see OSType
	 * @param cmd
	 * @return
	 */
	public static String executeCmd(OSType osType, String cmd) {
		if (osType == OSType.LINUX) {
			return executeLinuxCmd(cmd);
		} else if (osType == OSType.WINDOWS) {
			return executeWindowsCmd(cmd);
		} else if (osType == OSType.UNKNOWN||osType==null) {
			// 获得系统属性集
			Properties props = System.getProperties();
			// 操作系统名称
			String osname = props.getProperty("os.name");
			if (osname.toLowerCase().contains("Windows")) {
				return executeWindowsCmd(cmd);
				// 针对linux系统
			} else if ("Linux".equalsIgnoreCase(osname)) {
				return executeLinuxCmd(cmd);
			}
			return executeWindowsCmd(cmd);
		} else {
			logger.warn("未知的操作系统类型：" + osType);
			return null;
		}
	}
}
