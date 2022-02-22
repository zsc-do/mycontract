package cn.it.mycontract.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.enums.IdType;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.enums.IdType;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author zhengsc
 * @since 2022-02-13
 */
@TableName("sys_area")
public class SysArea implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private String name;

    private Integer leaderId;


    private String delFlag;


    @TableField(exist = false)
    private String leaderName;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    public Integer getLeaderId() {
        return leaderId;
    }

    public void setLeaderId(Integer leaderId) {
        this.leaderId = leaderId;
    }


    public String getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(String delFlag) {
        this.delFlag = delFlag;
    }


    public String getLeaderName() {
        return leaderName;
    }

    public void setLeaderName(String leaderName) {
        this.leaderName = leaderName;
    }

    @Override
    public String toString() {
        return "SysArea{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", leaderId=" + leaderId +
                ", delFlag='" + delFlag + '\'' +
                '}';
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SysArea sysArea = (SysArea) o;

        if (id != null ? !id.equals(sysArea.id) : sysArea.id != null) return false;
        if (name != null ? !name.equals(sysArea.name) : sysArea.name != null) return false;
        if (leaderId != null ? !leaderId.equals(sysArea.leaderId) : sysArea.leaderId != null) return false;
        if (delFlag != null ? !delFlag.equals(sysArea.delFlag) : sysArea.delFlag != null) return false;
        return leaderName != null ? leaderName.equals(sysArea.leaderName) : sysArea.leaderName == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (leaderId != null ? leaderId.hashCode() : 0);
        result = 31 * result + (delFlag != null ? delFlag.hashCode() : 0);
        result = 31 * result + (leaderName != null ? leaderName.hashCode() : 0);
        return result;
    }
}
