package cn.mulanbay.ai.ml.dataset;

import cn.mulanbay.ai.ml.dataset.bean.ModelFile;

import java.util.List;

/**
 * 模型处理
 *
 * @author fenghong
 * @create 2023-06-21
 */
public interface ModelHandle {

    /**
     * 获取模型文件
     * @param code
     * @return
     */
    ModelFile getModelFile(String code);

    /**
     * 模型文件列表
     *
     * @return
     */
    List<ModelFile> getModelFileList();
}
