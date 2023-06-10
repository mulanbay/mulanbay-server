package cn.mulanbay.pms.web.bean.response.chart;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description:关系节点图
 * @Author: fenghong
 * @Create : 2021/3/23
 */
public class ChartGraphData extends BaseChartData{

    private Map<String,String> tempItemMap = new HashMap<>();

    private Map<String,String> tempLinkMap = new HashMap<>();

    /**
     * 分类列表
     */
    private String[] categoryNames;

    private List<ChartGraphItemData> itemDataList = new ArrayList<>();

    private List<ChartGraphLinkData> linkDataList = new ArrayList<>();

    public void addItem(String name,int category){
        String key = name+"_"+category;
        String vv = tempItemMap.get(key);
        if(vv!=null){
            return;
        }
        ChartGraphItemData itemData = new ChartGraphItemData();
        itemData.setName(name);
        itemData.setCategory(category);
        this.itemDataList.add(itemData);
        tempItemMap.put(key,key);
    }

    public void addLink(String source,String target){
        this.addLink(source,target,"",0);
    }

    public void addLink(String source,String target,int type){
        this.addLink(source,target,"",type);
    }

    public void addLink(String source,String target,String name,int type){
        String key = source+"_"+target;
        String vv = tempItemMap.get(key);
        if(vv!=null){
            return;
        }
        ChartGraphLinkData linkData = new ChartGraphLinkData();
        linkData.setSource(source);
        linkData.setTarget(target);
        linkData.setName(name);
        linkData.setType(type);
        this.linkDataList.add(linkData);
        tempItemMap.put(key,key);
    }

    public String[] getCategoryNames() {
        return categoryNames;
    }

    public void setCategoryNames(String[] categoryNames) {
        this.categoryNames = categoryNames;
    }

    public List<ChartGraphItemData> getItemDataList() {
        return itemDataList;
    }

    public void setItemDataList(List<ChartGraphItemData> itemDataList) {
        this.itemDataList = itemDataList;
    }

    public List<ChartGraphLinkData> getLinkDataList() {
        return linkDataList;
    }

    public void setLinkDataList(List<ChartGraphLinkData> linkDataList) {
        this.linkDataList = linkDataList;
    }
}
