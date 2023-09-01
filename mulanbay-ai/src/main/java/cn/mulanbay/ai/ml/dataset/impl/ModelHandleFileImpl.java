package cn.mulanbay.ai.ml.dataset.impl;

import cn.mulanbay.ai.ml.dataset.ModelHandle;
import cn.mulanbay.ai.ml.dataset.bean.ModelFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 基于文件的实现
 *
 * @author fenghong
 * @create 2023-06-21
 */
public class ModelHandleFileImpl implements ModelHandle {

    private static final Logger logger = LoggerFactory.getLogger(ModelHandleFileImpl.class);

    private Map<String, ModelFile> map = new HashMap<>();

    @Override
    public ModelFile getModelFile(String code) {
        return map.get(code);
    }

    @Override
    public List<ModelFile> getModelFileList() {
        List<ModelFile> list =  map.values().stream()
                .collect(Collectors.toList());
        return list;
    }

    /**
     * 加载
     */
    public void load(){
        ResourceBundle rb = ResourceBundle.getBundle("modelConfig");
        Set<String> keys = rb.keySet();
        for(String code:keys){
            String v = rb.getString(code);
            ModelFile mf = new ModelFile();
            String[] vs = v.split(",");
            mf.setCode(code);
            mf.setFileName(vs[0]);
            mf.setDu(Boolean.valueOf(vs[1]));
            map.put(code,mf);
            logger.debug("加载了code={},模型文件:{}",code,vs[0]);
        }
    }
}
