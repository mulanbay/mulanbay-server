package cn.mulanbay.common.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URLEncoder;
import java.util.Calendar;
import java.util.Enumeration;
import java.util.List;
import java.util.zip.*;

/**
 * 文件操作工具类
 *
 * @author fenghong
 * @create 2018-01-20 21:44
 */
public class FileUtil {

    private static final Logger logger = LoggerFactory.getLogger(FileUtil.class);

    private static final int MEGA = 1024 * 1024;

    private static int DEFAULT_BUFFER_SIZE = 1024 * 4;

    public static String FILENAME_PATTERN = "[a-zA-Z0-9_\\-\\|\\.\\u4e00-\\u9fa5]+";

    /**
     * 移动文件
     *
     * @param source
     * @param destination
     * @return
     * @throws IOException
     */
    public static boolean moveFile(File source, File destination)
            throws IOException {

        return moveFile(source, destination, false);
    }

    /**
     * 移动文件(如果存在目标文件覆盖)
     *
     * @param source
     * @param destination
     * @param overlay
     * @return
     * @throws IOException
     */
    public static boolean moveFile(File source, File destination,
                                   boolean overlay) throws IOException {

        if (source == null || destination == null) {
            throw new IOException("源文件或目标文件为空。");
        }

        if (!source.exists()) {
            throw new IOException("源文件" + source.getAbsolutePath() + "不存在。");
        }
        if (overlay) {
            if (destination.exists()) {
                destination.delete();
            }
        } else {
            if (destination.exists()) {
                throw new IOException("目标文件" + destination.getAbsolutePath()
                        + "已存在");
            }
        }
        if (!source.renameTo(destination)) {
            copyFile(source, destination);
            return source.delete();
        }
        return true;
    }

    /**
     * 移动文件，并上锁
     *
     * @param source
     * @param destination
     * @return
     * @throws IOException
     */
    public static boolean moveFileByLock(File source, File destination)
            throws IOException {

        if (source == null || destination == null) {
            throw new IOException("源文件或目标文件为空。");
        }

        if (!source.exists()) {
            throw new IOException("源文件" + source.getAbsolutePath() + "不存在。");
        }

        if (destination.exists()) {
            throw new IOException("目标文件" + destination.getAbsolutePath()
                    + "已存在。");
        }
        if (!source.renameTo(destination)) {
            copyFile(source, destination);
            return source.delete();
        }
        return true;
    }

    /**
     * 移动文件
     *
     * @param files
     * @param des   目录
     * @throws IOException
     */
    public static void moveAndCoverFiles(List<File> files, File des)
            throws IOException {
        for (File source : files) {
            copyFile(source, des, null);
            source.delete();
        }
    }

    /**
     * 移动文件
     *
     * @param files
     * @param des   目录
     * @throws IOException
     */
    public static void moveAndCoverFiles(File[] files, File des)
            throws IOException {
        if (!des.exists()) {
            des.mkdirs();
        }
        for (File source : files) {
            if (!source.isDirectory()) {
                copyFile(source, des, null);
                source.delete();
            } else {
                String path = source.getAbsolutePath().substring(
                        source.getParent().length());
                File desc = new File(des.getPath() + File.separator + path);
                moveAndCoverFiles(source.listFiles(), desc);
            }
            // source.delete();
        }
    }

    /**
     * 删除整个文件夹（包括子目录和自身）
     *
     * @param folderFile
     */
    public static void deleteFolder(File folderFile) {
        if (!folderFile.exists()) {
            return;
        }
        for (File file : folderFile.listFiles()) {
            if (file.isDirectory()) {
                deleteFolder(file);
            } else if (file.isFile()) {
                file.delete();
            }
            file.delete();
        }
        folderFile.delete();
    }

    /**
     * 删除文件
     *
     * @param files 文件
     */
    public static void deleteFiles(List<File> files) {
        for (File source : files) {
            source.delete();
        }
    }

    /**
     * @param srcDir  源目录
     * @param descdir 目标目录
     * @return 拷贝成功时<tt>true</tt>;否则<tt>false</tt>
     * @Description 进行文件拷贝，包括对其子文件夹中的文件拷贝操作。返回成功或失败的结果。
     */
    public static boolean copyFilesIndepth(File srcDir, File descdir) {
        if (null == srcDir || null == descdir || srcDir.isFile()) {
            logger.error("源目录或目标目录为空。");
            return false;
        }
        if (!srcDir.exists()) {
            logger.error("源目录：" + srcDir.getAbsolutePath() + "不存在。");
            return false;
        }
        if (!descdir.exists()) {
            if (!descdir.mkdir()) {
                logger.error("无法创建目标目录：" + descdir.getAbsolutePath());
                return false;
            }
        }

        boolean flag = true;
        if (checkHasSaveFile(srcDir)) {
            try {
                for (File tmpFile : srcDir.listFiles()) {
                    if (tmpFile.isDirectory()) {
                        logger.debug(tmpFile.getName() + "是目录。。。");
                        flag = copyFilesIndepth(tmpFile,
                                new File(descdir.getAbsolutePath()
                                        + File.separator + tmpFile.getName()));
                    } else {
                        logger.debug("拷贝文件:" + tmpFile.getAbsolutePath() + "到:"
                                + descdir.getAbsolutePath());
                        copyFile(tmpFile, descdir, null);
                    }
                    if (!flag) {
                        break;
                    }
                }
            } catch (IOException e) {
                flag = false;
                logger.error("拷贝文件失败。", e);
            }
        }
        return flag;
    }

    /**
     * 拷贝目录下的所有文件.
     *
     * @param srcDir  原目录
     * @param descdir 目标目录
     * @return
     * @throws IOException
     */
    public static void copyFiles(File srcDir, File descdir) throws IOException {
        if (srcDir == null || descdir == null || srcDir.isFile()) {
            throw new IOException("源目录或目标目录为空。");
        }
        if (!srcDir.exists()) {
            throw new IOException("源目录" + srcDir.getAbsolutePath() + "不存在");
        }
        if (!descdir.exists()) {
            if (!descdir.mkdir()) {
                throw new IOException("无法创建目标目录：" + descdir.getAbsolutePath());
            }
        }

        if (checkHasSaveFile(srcDir)) {
            for (File tmpFile : srcDir.listFiles()) {
                copyFile(tmpFile, descdir, null);
            }
        }

    }

    /**
     * 拷贝文件
     *
     * @param source
     * @param descdir
     * @throws IOException
     */
    public static void copyFile(File source, File descdir, String fileName)
            throws IOException {
        if (source == null || descdir == null) {
            throw new IOException("源文件或目标文件为空");
        }
        if (!descdir.exists()) {
            if (!descdir.mkdir()) {
                throw new IOException("不存在");
            }
        }
        File newFile = null;
        if (fileName == null) {
            newFile = new File(descdir, source.getName());
        } else {
            newFile = new File(descdir, fileName);
        }
        if (!newFile.exists()) {
            if (!newFile.createNewFile()) {
                throw new IOException("不存在");
            }
        }
        copyFile(source, newFile);
    }

    /**
     * 拷贝文件
     *
     * @param source      源文件
     * @param destination 目标文件
     * @throws IOException
     */
    public static void copyFile(File source, File destination)
            throws IOException {
        FileInputStream in = null;
        FileOutputStream out = null;
        try {
            in = new FileInputStream(source);
            out = new java.io.FileOutputStream(destination);
            byte[] buff = new byte[1024];
            int len;
            while ((len = in.read(buff)) > -1) {
                out.write(buff, 0, len);
            }
        } catch (FileNotFoundException e) {
            throw new IOException(e.getMessage());
        } catch (IOException e) {
            throw e;
        } finally {
            if (in != null) {
                in.close();
            }
            if (out != null) {
                out.close();
            }
        }

    }

    /**
     * Ticket #33 Time 2010-7-1 下午03:34:44
     *
     * @param sourceFilesPath
     * @param publishNodePath
     * @param fileNameList
     * @return 是否成功
     * @author zhengshuquan
     * @description 拷贝文件至目标目录 集合
     */
    public static boolean copyFilesToDestinationPath(String sourceFilesPath,
                                                     String publishNodePath, List<String> fileNameList) {
        for (String fileName : fileNameList) {
            File source = new File(sourceFilesPath + File.separator + fileName);
            if (source.exists()) {
                File destination = new File(publishNodePath + File.separator
                        + fileName);
                logger.debug("文件: " + fileName + "从" + sourceFilesPath + " 移至"
                        + publishNodePath);
                try {
                    copyFile(source, destination);
                } catch (IOException e) {
                    logger.debug("文件: " + fileName + "从" + sourceFilesPath
                            + " 移至" + publishNodePath + "失败");
                    return false;
                }

            } else {
                logger.debug("目录: " + sourceFilesPath + "未找到文件: " + fileName);
                return false;
            }
        }

        return true;
    }

    /**
     * 生成文件夹
     *
     * @param folderName ,总路径
     * @throws IOException
     */
    public static File createFolder(String folderName) throws IOException {
        File file = new File(folderName);
        if (!file.exists()) {
            if (!file.mkdirs()) {
                throw new IOException("创建目录\'" + folderName + "\'失败");
            } else {
                logger.info("创建目录‘" + folderName + "’完成");
            }
        }
        return file;
    }

    /**
     * 删除目标文件夹下的文件(保留改文件夹)
     *
     * @param sourceFolder 目标文件夹
     * @return
     * @throws IOException
     */
    public static boolean delFolder(File sourceFolder) {
        boolean deleted = false;
        if (!sourceFolder.exists()) {
            // 文件不存在，就不需要删除
            return true;
        }
        if (sourceFolder.isFile()) {
            return sourceFolder.delete();
        }
        for (File source : sourceFolder.listFiles()) {
            if (source.isDirectory()) {
                if (source.listFiles().length == 0) {
                    deleted = source.delete();
                } else {
                    deleted = delFolder(source);
                    if (deleted) {
                        deleted = source.delete();
                    }
                }
            } else {
                deleted = source.delete();
            }
            if (!deleted) {
                return false;
            }
        }
        return true;
    }

    /**
     * 只删除指定目录（子目录）下的文件,保留原文件夹结构
     *
     * @param sourceFolder
     * @return
     */
    public static boolean delFiles(File sourceFolder) {
        boolean deleted = false;
        if (!sourceFolder.exists()) {
            // 文件不存在，就不需要删除
            return true;
        }
        if (sourceFolder.isFile()) {
            return sourceFolder.delete();
        }
        for (File source : sourceFolder.listFiles()) {
            if (source.isDirectory()) {
                deleted = delFiles(source);
            } else {
                deleted = source.delete();
            }
            if (!deleted) {
                return false;
            }
        }
        return true;
    }

    /**
     * 删除目标文件夹下的文件
     *
     * @param sourceFolder 目标文件夹
     * @return
     * @throws IOException
     */
    public static boolean deleteFiles(File sourceFolder) {
        logger.info("开始删除文件!");
        if (!sourceFolder.exists()) {
            // 文件不存在，就不需要删除
            return true;
        }
        if (sourceFolder.isFile()) {
            return false;
        }
        if (checkHasSaveFile(sourceFolder)) {
            for (File tmpFile : sourceFolder.listFiles()) {
                if (!tmpFile.delete()) {
                    logger.error("can not delete:" + tmpFile.getAbsolutePath());
                    // 如果删除不成功
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * @param source          源文件
     * @param dir             目标文件夹
     * @param destinationName 目标文件名
     * @param minutes         间隔多少秒建立文件夹
     * @return 描述：
     */
    public static boolean moveFile(File source, File dir,
                                   String destinationName, int minutes) throws IOException {
        Calendar cal = Calendar.getInstance();
        String path = cal.get(Calendar.HOUR_OF_DAY) + "_"
                + cal.get(Calendar.MINUTE / 10);
        File newDest = new File(dir, path);
        if (!newDest.exists()) {
            if (!newDest.createNewFile()) {
                return false;
            }
        }
        File destFile = new File(newDest, destinationName);
        return moveFile(source, destFile);
    }

    /**
     * @param srcDir
     * @param destDir
     * @param fileName
     * @param deleteChildren     是否删除原文件
     * @param deleteSelf
     * @return
     * @throws IOException 描述：压缩打包把srcDir 里面所有的文件打包至
     *                     destDir下面文件名为fileName的文件中，校验码算法用Adler32，当文件大小超过1M时开启1M的內存缓冲
     */
    public static int compressDir(File srcDir, File destDir, String fileName,
                                  boolean deleteChildren, boolean deleteSelf) throws IOException {
        if (srcDir.isFile()) {
            return 0;
        }
        int result = 0;
        if (checkHasSaveFile(srcDir)) {
            FileOutputStream fos = new FileOutputStream(new File(destDir,
                    fileName));
            CheckedOutputStream csum = new CheckedOutputStream(fos,
                    new Adler32());
            ZipOutputStream zos = new ZipOutputStream(csum);
            for (File tmpFile : srcDir.listFiles()) {
                result += zipDir(zos, tmpFile, "", deleteChildren);
            }
            zos.close();
            if (deleteSelf) {
                srcDir.delete();
            }
        }
        return result;
    }

    /**
     * @param srcDir
     * @param destDir
     * @param fileName
     * @param deleteChildren           是否删除原文件
     * @param deleteSelf
     * @param designateFolders
     * @return
     * @throws IOException 描述：压缩打包把srcDir 里面designateFolders指定的文件打包至
     *                     destDir下面文件名为fileName的文件中，校验码算法用Adler32，当文件大小超过1M时开启1M的內存缓冲
     */
    public static int compressDesignateFolder(File srcDir, File destDir,
                                              String fileName, boolean deleteChildren, boolean deleteSelf,
                                              String... designateFolders) throws IOException {
        if (srcDir.isFile()) {
            return 0;
        }
        int result = 0;
        if (checkHasSaveFile(srcDir)) {
            FileOutputStream fos = new FileOutputStream(new File(destDir,
                    fileName));
            CheckedOutputStream csum = new CheckedOutputStream(fos,
                    new Adler32());
            ZipOutputStream zos = new ZipOutputStream(csum);
            for (File tmpFile : srcDir.listFiles()) {
                for (String designateFolder : designateFolders) {
                    if (designateFolder.equals(tmpFile.getName())) {
                        result += zipDir(zos, tmpFile, "", deleteChildren);
                    }
                }
            }
            zos.close();
            if (deleteSelf) {
                srcDir.delete();
            }
        }
        return result;
    }

    private static boolean checkHasSaveFile(File srcDir) {
        for (File file : srcDir.listFiles()) {
            if (file.canWrite() && file.isFile()) {
                return true;
            }
            if (file.isDirectory()) {
                return checkHasSaveFile(file);
            }
        }
        return false;
    }

    /**
     * @param srcDir
     * @param destDir
     * @param fileName
     * @return 压缩的文件数量
     * @throws IOException 描述：压缩打包把srcDir 里面所有的文件打包至
     *                     destDir下面文件名为fileName的文件中，校验码算法用Adler32，当文件大小超过1M时开启1M的內存缓冲
     */
    public static int compressDir(File srcDir, File destDir, String fileName)
            throws IOException {
        return compressDir(srcDir, destDir, fileName, false, false);
    }

    private static int zipDir(ZipOutputStream zos, File file, String context,
                              boolean delete) throws IOException {

        if (file.isFile() && file.canRead() && file.canWrite()) {
            ZipEntry ze = new ZipEntry(context + File.separator
                    + file.getName());
            zos.putNextEntry(ze);
            FileInputStream fis = new FileInputStream(file);
            // 1M buffer
            byte[] buf = null;
            if (file.length() < MEGA) {
                buf = new byte[(int) file.length()];
            } else {
                buf = new byte[MEGA];
            }
            try {
                int len = 0;
                while ((len = fis.read(buf)) > 0) {
                    zos.write(buf, 0, len);
                }
            } catch (Exception e) {
                e.printStackTrace();
                return 0;
            } finally {
                if (fis != null) {
                    fis.close();
                }
            }
            zos.closeEntry();
            if (delete) {
                if (!file.delete()) {
                    logger.error(file.getAbsolutePath() + " 删除失败");
                }
            }

            return 1;

        } else if (file.isDirectory()) {
            int k = 0;
            for (File innerFile : file.listFiles()) {
                k += zipDir(zos, innerFile,
                        context + File.separator + file.getName(), delete);
            }
            if (delete) {
                if (!file.delete()) {
                    logger.error(file.getAbsolutePath() + " 删除失败");
                }
            }
            return k;
        } else {
            return 0;
        }
    }

    /**
     * @param file
     * @return 描述：这个函数得到目标文件或文件夹的长度
     */
    public static long size(File file) {
        if (file.isFile()) {
            return file.length();
        }
        long size = file.length();
        for (File lsFile : file.listFiles()) {
            size += size(lsFile);
        }
        return size;
    }

    public static long countFile(File dir) {
        if (dir.isFile()) {
            return 0;
        }
        long count = 0;
        for (File file : dir.listFiles()) {
            if (file.isFile()) {
                count++;
            } else {
                count += countFile(file);
            }
        }
        return count;
    }

    public static boolean writeFile(File path, String fileName, byte[] bytes) {
        OutputStream out = null;
        try {
            if (!path.exists()) {
                path = new File(path.getAbsolutePath());
                path.mkdirs();
            }
            out = new FileOutputStream(new File(path, fileName));
            out.write(bytes);
            return true;
        } catch (IOException e) {
            logger.error("writeFile error",e);
            return false;
        } finally {
            if (out != null) {
                try {
                    out.close();
                } catch (IOException e) {
                    logger.error("close io error",e);
                }
            }
        }
    }

    /**
     * 读取文件中的内容,并以字符串的形式返回
     *
     * @param path     文件所在的路径
     * @param fileName 文件名
     * @return 文件的内容
     */
    public static String readFile(File path, String fileName) {
        String readLineString = null;
        StringBuffer stringBuffer = new StringBuffer();
        File file = new File(path, fileName);
        BufferedReader br = null;
        // 判断文件是否存在
        try {
            if (file.exists()) {
                try {

                    br = new BufferedReader(new InputStreamReader(
                            new FileInputStream(file)));
                    // 读取文件,将内容添加到stringBuffer中
                    while ((readLineString = br.readLine()) != null) {
                        stringBuffer.append(readLineString);
                    }
                } catch (Exception e) {
                    logger.error("读取文件：" + file + "失败。", e);
                }
            } else {
                logger.warn("文件不存在");
            }
        } catch (Exception e) {
            logger.error("读取文件失败。", e);
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    logger.error("关闭文件读取流失败。", e);
                }
            }
        }
        // 返回文件内容
        return stringBuffer.toString();
    }

    /**
     * 将bytes中的内容写入文件
     *
     * @param file  需要写入的文件
     * @param bytes 需要写入的内容
     * @return 成功信息
     */
    public static boolean writeFile(File file, byte[] bytes) {
        OutputStream out = null;
        try {
            out = new FileOutputStream(file);
            out.write(bytes);
            return true;
        } catch (IOException e) {
            logger.error("写文件：" + file + "失败。", e);
            return false;
        } finally {
            if (out != null) {
                try {
                    out.close();
                } catch (IOException e) {
                    logger.error("关闭IO流异常", e);
                }
            }
        }
    }

    /**
     * 移动文件(先备份后移动)。<br>
     * 注意：目标文件夹不能是源文件的子目录
     *
     * @param destFolder   目标目录
     * @param backFolder   备份目录（null或者空 不备份）
     * @param sourceFolder 源文件目录
     * @return
     */
    @SuppressWarnings("unused")
    public static boolean moveFiles(String destFolder, String backFolder,
                                    String sourceFolder) {
        logger.info("文件夹路径信息：\n目标文件夹：" + destFolder + "\n备份文件夹：" + backFolder
                + "\n源文件夹：" + sourceFolder);
        File localDir = new File(destFolder);
        File tempDir = new File(sourceFolder);

        File backDir = null;
        if (backFolder != null && !"".equals(backFolder)) {
            backDir = new File(backFolder);
            if (!backDir.exists()) {
                if (!backDir.mkdirs()) {
                    logger.warn("创建备份文件夹失败。");
                    return false;
                }
            }
            if (backDir.getAbsolutePath().startsWith(tempDir.getAbsolutePath())
                    || localDir.getAbsolutePath().startsWith(
                    tempDir.getAbsolutePath())) {
                logger.warn("目标文件不允许是源文件的子目录,移动文件失败。");
                return false;
            }

            // 先将备份文夹的文件清空
            if (!FileUtil.delFiles(backDir)) {
                logger.warn("清空备份文件夹：" + backFolder + "失败。");

            }
            // 先备份本地文件，预防用临时文件覆盖本地文件时出错而不能回滚
            try {
                copyFiles(destFolder, backFolder);
                logger.debug("备份文件目录: " + destFolder + " 到 " + backFolder
                        + " 完成。");
            } catch (IOException e) {
                logger.error("备份本地文件目录：" + sourceFolder + "失败，所以不能对本地文件进行覆盖。",
                        e);
                return false;
            }
        } else {
            logger.warn("备份文件夹名为空，移动文件失败。");
            return false;
        }
        // 覆盖本地文件

        try {
            FileUtil.moveAndCoverFiles(tempDir.listFiles(), localDir);
        } catch (IOException e) {
            if (backDir != null) {
                logger.error("将临时文件夹下的文件去覆盖本地文件失败，回滚本地文件夹。", e);
                try {
                    FileUtil.moveAndCoverFiles(backDir.listFiles(), localDir);
                } catch (IOException e1) {
                    logger.error("回滚本地文件失败。", e1);
                }
            } else {
                logger.error("将临时文件夹下的文件去覆盖本地文件失败。", e);
            }
            return false;
        }
        logger.info("覆盖本地文件成功。");
        delFolder(backDir);
        return true;

    }

    /**
     * 移动文件（先备份后移动）
     *
     * @param destFolder   目标目录(目标文件不能使源文件的子目录)
     * @param sourceFolder 源文件目录
     * @return
     */
    public static boolean moveFiles(String destFolder, String sourceFolder) {
        String backFolder = System.getProperty("user.dir") + "/user_temp";
        return moveFiles(destFolder, backFolder, sourceFolder);
    }

    /**
     * 复制整个目录，包括源文件目录结构(目标文件不允许是源文件的子目录。)
     *
     * @param sourcePath
     * @param destPath
     * @throws IOException
     */
    public static void copyFiles(String sourcePath, String destPath)
            throws IOException {
        File destFile = new File(destPath);
        File sourceFile = new File(sourcePath);
        if (destFile.getAbsolutePath().startsWith(sourceFile.getAbsolutePath())) {
            throw new IOException("目标文件夹不允许是源文夹的子目录,无法复制文件。");
        }
        if (!destFile.exists()) {
            destFile.mkdirs();
        }
        if (!sourceFile.exists()) {
            throw new IOException("源文件" + sourcePath + "不存在。");
        }
        for (File source : sourceFile.listFiles()) {
            if (!source.isDirectory()) {
                copyFile(source, destFile, null);
            } else {
                // 源文件夹不能等于目标文件夹路径
                if (source.getAbsolutePath().equals(destFile.getAbsolutePath())) {
                    continue;
                }
                String path = source.getName();
                String desc = destFile.getPath() + File.separator + path;
                copyFiles(source.getAbsolutePath(), desc);
            }
        }
    }

    /**
     * 解压缩文件
     *
     * @param fileName 压缩文件路径+名字 例: ./temp/test.zip
     * @param filePath 存放解压缩文件的路径 例: ./temp
     */
    public static void unZipFile(String fileName, String filePath)
            throws IOException, FileNotFoundException {
        filePath += "/";
        ZipFile zipFile = null;
        BufferedInputStream bis = null;
        BufferedOutputStream bos = null;
        FileOutputStream fos = null;
        try {
            zipFile = new ZipFile(fileName);
            Enumeration<?> emu = zipFile.entries();
            while (emu.hasMoreElements()) {
                ZipEntry entry = (ZipEntry) emu.nextElement();
                // 会把目录作为一个file读出一次，所以只建立目录就可以，之下的文件还会被迭代到。
                if (entry.isDirectory()) {
                    new File(filePath + entry.getName()).mkdirs();
                    continue;
                }
                bis = new BufferedInputStream(zipFile.getInputStream(entry));
                File file = new File(filePath + entry.getName());
                // 加入这个的原因是zipfile读取文件是随机读取的，这就造成可能先读取一个文件
                // 而这个文件所在的目录还没有出现过，所以要建出目录来。
                File parent = file.getParentFile();
                if (parent != null && (!parent.exists())) {
                    parent.mkdirs();
                }
                fos = new FileOutputStream(file);
                bos = new BufferedOutputStream(fos, MEGA);
                int count = 0;
                byte[] buf = new byte[MEGA];
                while ((count = bis.read(buf, 0, MEGA)) != -1) {
                    bos.write(buf, 0, count);
                }
                bos.flush();
            }
        } finally {
            if (fos != null) {
                fos.close();
            }
            if (bos != null) {
                bos.close();
            }
            if (bis != null) {
                bis.close();
            }
            if (zipFile != null) {
                try {
                    zipFile.close();
                } catch (IOException e) {
                    logger.error("关闭IO流异常。");
                }
            }
        }
    }

    public static byte[] toByteArray(InputStream input) throws IOException {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        copy(input, output);
        return output.toByteArray();
    }

    private static int copy(InputStream input, OutputStream output)
            throws IOException {
        long count = copyLarge(input, output);
        if (count > Integer.MAX_VALUE) {
            return -1;
        }
        return (int) count;
    }

    private static long copyLarge(InputStream input, OutputStream output)
            throws IOException {
        byte[] buffer = new byte[DEFAULT_BUFFER_SIZE];
        long count = 0;
        int n = 0;
        while (-1 != (n = input.read(buffer))) {
            output.write(buffer, 0, n);
            count += n;
        }
        return count;
    }

    /**
     * 检查路径是否存在，不存在则创建文件夹
     *
     * @param path
     */
    public static void checkPathExits(String path) {
        File fileP = new File(path);
        if (!fileP.exists()) {
            fileP.mkdirs();
        }

    }

    /**
     * 检查路径是否存在，不存在则创建文件夹
     *
     * @param path
     */
    public static void checkPathExits(File path) {
        if (!path.exists()) {
            path.mkdirs();
        }
    }

    /**
     * 将图片转换成base64格式进行存储
     *
     * @param imagePath
     * @return
     */
    public static String encodeImageTOBase64(String imagePath) throws IOException {
        int index = imagePath.lastIndexOf(".");
        String type = imagePath.substring(index + 1);
        BufferedImage image = ImageIO.read(new File(imagePath));
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ImageIO.write(image, type, bos);
        byte[] imageBytes = bos.toByteArray();
        String imageString = Base64Util.encode(imageBytes);
        bos.close();
        return imageString;
    }

    /**
     * 输出指定文件的byte数组
     *
     * @param filePath 文件路径
     * @param os       输出流
     * @return
     */
    public static void writeBytes(String filePath, OutputStream os) throws IOException {
        FileInputStream fis = null;
        try {
            File file = new File(filePath);
            if (!file.exists()) {
                throw new FileNotFoundException(filePath);
            }
            fis = new FileInputStream(file);
            byte[] b = new byte[1024];
            int length;
            while ((length = fis.read(b)) > 0) {
                os.write(b, 0, length);
            }
        } catch (IOException e) {
            throw e;
        } finally {
            if (os != null) {
                try {
                    os.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        }
    }

    /**
     * 删除文件
     *
     * @param filePath 文件
     * @return
     */
    public static boolean deleteFile(String filePath) {
        boolean flag = false;
        File file = new File(filePath);
        // 路径为文件且不为空则进行删除
        if (file.isFile() && file.exists()) {
            file.delete();
            flag = true;
        }
        return flag;
    }

    /**
     * 文件名称验证
     *
     * @param filename 文件名称
     * @return true 正常 false 非法
     */
    public static boolean isValidFilename(String filename) {
        return filename.matches(FILENAME_PATTERN);
    }

    /**
     * 下载文件名重新编码
     *
     * @param request  请求对象
     * @param fileName 文件名
     * @return 编码后的文件名
     */
    public static String setFileDownloadHeader(HttpServletRequest request, String fileName)
            throws UnsupportedEncodingException {
        final String agent = request.getHeader("USER-AGENT");
        String filename = fileName;
        if (agent.contains("MSIE")) {
            // IE浏览器
            filename = URLEncoder.encode(filename, "utf-8");
            filename = filename.replace("+", " ");
        } else if (agent.contains("Firefox")) {
            // 火狐浏览器
            filename = new String(fileName.getBytes(), "ISO8859-1");
        } else if (agent.contains("Chrome")) {
            // google浏览器
            filename = URLEncoder.encode(filename, "utf-8");
        } else {
            // 其它浏览器
            filename = URLEncoder.encode(filename, "utf-8");
        }
        return filename;
    }
}
