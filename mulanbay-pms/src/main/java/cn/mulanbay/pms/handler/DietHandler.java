package cn.mulanbay.pms.handler;

import cn.mulanbay.business.handler.BaseHandler;
import cn.mulanbay.pms.handler.qa.AhaNLPHandler;
import cn.mulanbay.pms.persistent.service.DietService;
import cn.mulanbay.pms.web.bean.request.diet.DietVarietySearch;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * 饮食处理器
 *
 * @author fenghong
 * @create 2017-07-10 21:44
 */
@Component
public class DietHandler extends BaseHandler {

    @Autowired
    DietService dietService;

    @Autowired
    AhaNLPHandler ahaNLPHandler;

    public DietHandler() {
        super("饮食处理器");
    }

    /**
     * 获取食物平均相似度
     *
     * @param sf
     * @return
     */
    public float getFoodsAvgSimilarity(DietVarietySearch sf) {
        List<String> foodsList = this.getFoodsList(sf);
        return ahaNLPHandler.avgSentenceSimilarity(foodsList);
    }

    private List<String> getFoodsList(DietVarietySearch sf) {
        if (sf.getDietType() != null) {
            return dietService.getFoodsList(sf);
        } else {
            List<Object[]> list = dietService.getFoodsListWithAllDietType(sf);
            List<String> res = new ArrayList<>();
            for (Object[] ss : list) {
                String foods = "";
                for (Object s : ss) {
                    foods += s.toString() + ",";
                }
                res.add(foods);
            }
            return res;
        }
    }

}
