package cn.it.mycontract.util;

import cn.it.mycontract.entity.BaseTreeGrid;

import java.util.ArrayList;
import java.util.List;

public class TreeUtils {


    public static <T extends BaseTreeGrid> List<T> formatTree(List<T> list, Boolean flag) {
        List<T> nodeList = new ArrayList<T>();
        for(T node1 : list){
            boolean mark = false;
            for(T node2 : list){
                if(node1.getGridParentId()!=null && node1.getGridParentId()
                        .equals(node2.getGridId())){
                    node2.setLeaf(false);
                    mark = true;
                    if(node2.getChildren() == null) {
                        node2.setChildren(new ArrayList<BaseTreeGrid>());
                    }
                    node2.getChildren().add(node1);
                    if (flag) {
                    } else{
                        node2.setExpanded(false);
                    }
                    break;
                }
            }
            if(!mark){
                nodeList.add(node1);
                if (flag) {
                } else{
                    node1.setExpanded(false);
                }
            }
        }
        return nodeList;
    }
}
