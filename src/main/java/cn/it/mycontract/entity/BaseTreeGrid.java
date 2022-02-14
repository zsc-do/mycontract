package cn.it.mycontract.entity;

import com.baomidou.mybatisplus.annotations.TableField;

import java.io.Serializable;
import java.util.List;

public class BaseTreeGrid implements Serializable {
    private static final long serialVersionUID = 1L;

    @TableField(exist = false)
    public String gridId;//节点id

    @TableField(exist = false)
    public String gridParentId;//节点父id

    @TableField(exist = false)
    public String iconCls = "folder";//节点样式，默认即可

    @TableField(exist = false)
    public Boolean leaf = true;//是否为叶子节点，true表示是叶子节点，false表示不是叶子节点

    @TableField(exist = false)
    public Boolean expanded = true; //是否展开，默认true,展开

    @TableField(exist = false)
    public List<BaseTreeGrid> children;//孩子节点


    public BaseTreeGrid() {

    }

    public BaseTreeGrid(String gridId, String gridParentId) {
        this.gridId=gridId;
        this.gridParentId=gridParentId;
    }

    public String getGridId() {
        return gridId;
    }

    public void setGridId(String gridId) {
        this.gridId = gridId;
    }

    public String getGridParentId() {
        return gridParentId;
    }

    public void setGridParentId(String gridParentId) {
        this.gridParentId = gridParentId;
    }

    public String getIconCls() {
        return iconCls;
    }

    public void setIconCls(String iconCls) {
        this.iconCls = iconCls;
    }

    public Boolean getLeaf() {
        return leaf;
    }

    public void setLeaf(Boolean leaf) {
        this.leaf = leaf;
    }

    public Boolean getExpanded() {
        return expanded;
    }

    public void setExpanded(Boolean expanded) {
        this.expanded = expanded;
    }

    public List<BaseTreeGrid> getChildren() {
        return children;
    }

    public void setChildren(List<BaseTreeGrid> children) {
        this.children = children;
    }
}
