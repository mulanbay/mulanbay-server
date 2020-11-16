package cn.mulanbay.common.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * 文件压缩工具类
 *
 * @author fenghong
 * @create 2018-01-20 21:44
 */
public class ZipUtil {

	private static final Logger logger = LoggerFactory.getLogger(ZipUtil.class);

	/**
	 * 
	 * @param zip
	 *            目标压缩文件名，全路径
	 * @param path
	 *            压缩包里面的根目录，一般传空字符串
	 * @param srcFiles
	 *            待压缩文件
	 */
	public static boolean ZipFiles(File zip, String path, File srcFiles) {
		ZipOutputStream out = null;
		try {
			logger.debug("开始压缩[" + srcFiles.getAbsolutePath() + "]");
			out = new ZipOutputStream(new FileOutputStream(zip));
			ZipFiles(out, path, srcFiles);
			logger.debug("压缩[" + srcFiles.getAbsolutePath() + "]结束");
			return true;
		} catch (Exception e) {
			logger.error("压缩[" + srcFiles.getAbsolutePath() + "]异常", e);
			return false;
		} finally {
			if (out != null) {
				try {
					out.close();
				} catch (IOException e) {
					logger.error("关闭压缩流异常", e);
				}
			}
		}
	}

	/**
	 * 
	 * @param out
	 * @param path
	 * @param srcFiles
	 */
	private static void ZipFiles(ZipOutputStream out, String path,
			File... srcFiles) {
		byte[] buf = new byte[1024];
		try {
			for (int i = 0; i < srcFiles.length; i++) {
				if (srcFiles[i].isDirectory()) {
					File[] files = srcFiles[i].listFiles();
					String srcPath = srcFiles[i].getName();
					srcPath = srcPath.replaceAll("\\*", "/");
					if (!srcPath.endsWith("/")) {
						srcPath += "/";
					}
					out.putNextEntry(new ZipEntry(path + srcPath));
					ZipFiles(out, path + srcPath, files);
				} else {
					FileInputStream in = new FileInputStream(srcFiles[i]);
					logger.debug("压缩文件：" + path + srcFiles[i].getName());
					out.putNextEntry(new ZipEntry(path + srcFiles[i].getName()));
					int len;
					while ((len = in.read(buf)) > 0) {
						out.write(buf, 0, len);
					}
					out.closeEntry();
					in.close();
				}
			}
		} catch (Exception e) {
			logger.error("压缩文件" + srcFiles + "异常", e);
		}
	}
}
