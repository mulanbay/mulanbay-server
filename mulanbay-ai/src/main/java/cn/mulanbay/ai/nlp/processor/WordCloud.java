package cn.mulanbay.ai.nlp.processor;

import me.xiaosheng.chnlp.Config;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

/**
 * 词云处理，针对AHANLP的封装
 * 主要是增加了日志
 *
 * @author fenghong
 * @create 2017-07-10 21:44
 */
public class WordCloud {

    private static final Logger logger = LoggerFactory.getLogger(WordCloud.class);

    private List<String> wordList = null;

    public WordCloud(List<String> wordList) {
        this.wordList = wordList;
    }

    /**
     * 绘制词云图片
     *
     * @param savePicPath 图片存储路径
     * @throws IOException
     */
    public void createImage(String savePicPath) throws IOException {
        createImage(savePicPath, 500, 400, false);
    }

    /**
     * 绘制词云图片
     *
     * @param savePicPath     图片存储路径
     * @param blackBackground 是否使用黑色背景
     * @throws IOException
     */
    public void createImage(String savePicPath, boolean blackBackground) throws IOException {
        createImage(savePicPath, 500, 400, true);
    }

    /**
     * 绘制词云图片
     *
     * @param savePicPath 图片存储路径
     * @param picWidth    图片宽度
     * @param picHeight   图片高度
     * @throws IOException
     */
    public void createImage(String savePicPath, int picWidth, int picHeight) throws IOException {
        createImage(savePicPath, picWidth, picHeight, false);
    }

    /**
     * 绘制词云图片
     *
     * @param savePicPath     图片存储路径
     * @param picWidth        图片宽度
     * @param picHeight       图片高度
     * @param blackBackground 是否使用黑色背景
     * @throws IOException
     */
    public void createImage(String savePicPath, int picWidth, int picHeight, boolean blackBackground) throws IOException {
        String tempFileName = UUID.randomUUID().toString();
        // 生成临时词语文件
        BufferedWriter bw = new BufferedWriter(new FileWriter(Config.wordCloudPath() + tempFileName));
        for (String word : wordList) {
            bw.write(word + "\n");
        }
        bw.flush();
        bw.close();
        StringBuilder cmd = new StringBuilder(Config.pythonCMD());
        cmd.append(" create_word_cloud.py");
        cmd.append(" -l ");
        cmd.append(tempFileName);
        cmd.append(" -w ");
        cmd.append(picWidth);
        cmd.append(" -h ");
        cmd.append(picHeight);
        cmd.append(" -b ");
        cmd.append(blackBackground ? "black" : "white");
        cmd.append(" -s ");
        cmd.append("\"" + savePicPath + "\"");
        cmd.append(" -f ");
        cmd.append("\"simhei.ttf\"");
        logger.info("wordCloud 命令:" + cmd.toString());
        Process pr = Runtime.getRuntime().exec(cmd.toString(), null, new File(Config.wordCloudPath()));
        try {
            pr.waitFor();
        } catch (Exception e) {
            logger.error("cmd process error", e);
        } finally {
            pr.destroy();
        }
        new File(Config.wordCloudPath() + tempFileName).delete();
        logger.info("create wordcloud success!");
    }
}
