package cn.mulanbay.pms.web.bean.response.chart;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description:关系节点图
 * @Author: fenghong
 * @Create : 2021/3/23
 */
public class ChartGraphData extends BaseChartData{

    private List<ChartGraphItemData> itemDataList = new ArrayList<>();

    private List<ChartGraphLinkData> linkDataList = new ArrayList<>();

    public void addItem(String name,int category){
        ChartGraphItemData itemData = new ChartGraphItemData();
        itemData.setName(name);
        itemData.setCategory(category);
        this.itemDataList.add(itemData);
    }

    public void addLink(String source,String target){
        this.addLink(source,target,"",0);
    }

    public void addLink(String source,String target,int type){
        this.addLink(source,target,"",type);
    }

    public void addLink(String source,String target,String name,int type){
        ChartGraphLinkData linkData = new ChartGraphLinkData();
        linkData.setSource(source);
        linkData.setTarget(target);
        linkData.setName(name);
        linkData.setType(type);
        this.linkDataList.add(linkData);
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
