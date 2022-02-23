package cn.it.mycontract.entity;

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
 * @since 2022-02-15
 */
@TableName("htgl_process_record")
public class HtglProcessRecord implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 合同id
     */
    private Integer contractId;

    /**
     * 当前流程处理人
     */
    private Integer nowHandler;

    /**
     * 流程状态(0表示还没有轮到处理，1表示可以处理，2表示处理完毕)
     */
    private String status;



    private String delSort;


    private String areaName;



    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
    public Integer getContractId() {
        return contractId;
    }

    public void setContractId(Integer contractId) {
        this.contractId = contractId;
    }

    public Integer getNowHandler() {
        return nowHandler;
    }

    public String getDelSort() {
        return delSort;
    }

    public void setDelSort(String delSort) {
        this.delSort = delSort;
    }

    public void setNowHandler(Integer nowHandler) {
        this.nowHandler = nowHandler;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "HtglProcessRecord{" +
                "id=" + id +
                ", contractId=" + contractId +
                ", nowHandler=" + nowHandler +
                ", status='" + status + '\'' +
                ", delSort='" + delSort + '\'' +
                '}';
    }
}
