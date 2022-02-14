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
 * @since 2022-02-14
 */
@TableName("htgl_contract_partener")
public class HtglContractPartener implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 关联合同编号
     */
    private Integer contractId;

    /**
     * 签订方类型：甲方、乙方、丙方、丁方
     */
    private String partenerType;

    /**
     * 签订方名称
     */
    private String partenerName;

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
    public String getPartenerType() {
        return partenerType;
    }

    public void setPartenerType(String partenerType) {
        this.partenerType = partenerType;
    }
    public String getPartenerName() {
        return partenerName;
    }

    public void setPartenerName(String partenerName) {
        this.partenerName = partenerName;
    }

    @Override
    public String toString() {
        return "HtglContractPartener{" +
        "id=" + id +
        ", contractId=" + contractId +
        ", partenerType=" + partenerType +
        ", partenerName=" + partenerName +
        "}";
    }
}
