package cn.mulanbay.ai.ml.dataset.impl;

import cn.mulanbay.ai.ml.dataset.ModuleHandle;
import cn.mulanbay.ai.ml.dataset.bean.ModuleFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;

/**
 * 基于文件的实现
 *
 * @author fenghong
 * @create 2023-06-21
 */
public class ModuleHandleFileImpl implements ModuleHandle {

    private static final Logger logger = LoggerFactory.getLogger(ModuleHandleFileImpl.class);

    private Map<String,ModuleFile> map = new HashMap<>();

    @Override
    public ModuleFile getModuleFile(String code) {
        return map.get(code);
    }

    /**
     * 加载
     */
    public void load(){
        ResourceBundle rb = ResourceBundle.getBundle("modelConfig");
        Set<String> keys = rb.keySet();
        for(String code:keys){
            String v = rb.getString(code);
            ModuleFile mf = new ModuleFile();
            String[] vs = v.split(",");
            mf.setCode(code);
            mf.setFileName(vs[0]);
            mf.setDu(Boolean.valueOf(vs[1]));
            map.put(code,mf);
            logger.debug("加载了code={},模型文件:{}",code,vs[0]);
        }
    }
}
