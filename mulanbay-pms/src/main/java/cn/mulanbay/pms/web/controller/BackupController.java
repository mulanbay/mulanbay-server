package cn.mulanbay.pms.web.controller;

import cn.mulanbay.common.util.FileUtil;
import cn.mulanbay.persistent.query.PageResult;
import cn.mulanbay.pms.handler.SystemConfigHandler;
import cn.mulanbay.pms.web.bean.request.system.BackupFileDeleteRequest;
import cn.mulanbay.pms.web.bean.response.system.FileVo;
import cn.mulanbay.web.bean.request.PageSearch;
import cn.mulanbay.web.bean.response.ResultBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.File;
import java.util.*;

/**
 * 备份管理
 *
 * @author fenghong
 * @create 2017-07-10 21:44
 */
@RestController
@RequestMapping("/backup")
public class BackupController extends BaseController {

    private static final Logger logger = LoggerFactory.getLogger(BackupController.class);

    @Autowired
    SystemConfigHandler systemConfigHandler;

    @RequestMapping(value = "/getData", method = RequestMethod.GET)
    public ResultBean getData(PageSearch sf) {
        List<FileVo> list = new ArrayList<>();
        String backupPath = systemConfigHandler.getStringConfig("system.backupPath");
        File file = new File(backupPath);
        long totalSize = 0L;
        if (file.exists()) {
            for (File f : file.listFiles()) {
                FileVo fb = new FileVo();
                fb.setFileName(f.getName());
                fb.setPath(f.getPath());
                fb.setDirectory(f.isDirectory());
                long size = f.length();
                fb.setSize(size);
                totalSize += size;
                fb.setLastModifyTime(new Date(f.lastModified()));
                list.add(fb);
            }
            Collections.sort(list, new Comparator<FileVo>() {
                @Override
                public int compare(FileVo o1, FileVo o2) {
                    return o2.getLastModifyTime().compareTo(o1.getLastModifyTime());
                }
            });
        }
        FileVo total = new FileVo();
        total.setFileName("合计");
        total.setPath(backupPath);
        total.setDirectory(true);
        total.setSize(totalSize);
        total.setLastModifyTime(new Date(0L));
        list.add(total);
        PageResult<FileVo> res = new PageResult<FileVo>();
        res.setBeanList(list);
        res.setMaxRow(list.size());
        return callbackDataGrid(res);
    }

    /**
     * 下载
     *
     * @param fileName
     * @param response
     */
    @RequestMapping(value = "/download", method = RequestMethod.GET)
    public void fileDownload(String fileName, HttpServletResponse response) {
        try {
            String realFileName = System.currentTimeMillis() + fileName.substring(fileName.indexOf("_") + 1);
            String filePath = systemConfigHandler.getStringConfig("system.backupPath") + "/" + fileName;

            //response.setCharacterEncoding("utf-8");
            response.setContentType("multipart/form-data");
            response.setHeader("Content-Disposition",
                    "attachment;fileName=" + FileUtil.setFileDownloadHeader(request, realFileName));
            FileUtil.writeBytes(filePath, response.getOutputStream());
        } catch (Exception e) {
            logger.error("下载文件失败", e);
        }
    }

    /**
     * 删除
     *
     * @param dr
     */
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public ResultBean delete(@RequestBody @Valid BackupFileDeleteRequest dr) {
        String fullPath = systemConfigHandler.getStringConfig("system.backupPath") + "/" + dr.getFileName();
        FileUtil.deleteFile(fullPath);
        return callback(null);
    }

}
