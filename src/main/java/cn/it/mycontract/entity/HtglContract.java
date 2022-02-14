package cn.it.mycontract.entity;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 
 * </p>
 *
 * @author zhengsc
 * @since 2022-02-14
 */
@TableName("htgl_contract")
public class HtglContract implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 合同编号
     */
    private String code;

    /**
     * 合同名称
     */
    private String name;

    /**
     * 主办单位名称
     */
    private String sponsorName;

    /**
     * 主办单位id
     */
    private Integer sponsorId;

    /**
     * 经办人名称
     */
    private String  operatorName;

    /**
     * 经办人id
     */
    private Integer operatorId;

    /**
     * 起草日期
     */
    private Date draftTime;

    /**
     * 财务预算编号
     */
    private String budgetCode;

    /**
     * 合同金额
     */
    private String contractMoney;

    /**
     * 合同授权代表
     */
    private String authorization;

    /**
     * 合同份数
     */
    private String contractCnt;

    /**
     * 合同签订日期
     */
    private Date approveTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    public String getSponsorName() {
        return sponsorName;
    }

    public void setSponsorName(String sponsorName) {
        this.sponsorName = sponsorName;
    }
    public Integer getSponsorId() {
        return sponsorId;
    }

    public void setSponsorId(Integer sponsorId) {
        this.sponsorId = sponsorId;
    }

    public String getOperatorName() {
        return operatorName;
    }

    public void setOperatorName(String operatorName) {
        this.operatorName = operatorName;
    }

    public Integer getOperatorId() {
        return operatorId;
    }

    public void setOperatorId(Integer operatorId) {
        this.operatorId = operatorId;
    }
    public Date getDraftTime() {
        return draftTime;
    }

    public void setDraftTime(Date draftTime) {
        this.draftTime = draftTime;
    }
    public String getBudgetCode() {
        return budgetCode;
    }

    public void setBudgetCode(String budgetCode) {
        this.budgetCode = budgetCode;
    }
    public String getContractMoney() {
        return contractMoney;
    }

    public void setContractMoney(String contractMoney) {
        this.contractMoney = contractMoney;
    }
    public String getAuthorization() {
        return authorization;
    }

    public void setAuthorization(String authorization) {
        this.authorization = authorization;
    }
    public String getContractCnt() {
        return contractCnt;
    }

    public void setContractCnt(String contractCnt) {
        this.contractCnt = contractCnt;
    }
    public Date getApproveTime() {
        return approveTime;
    }

    public void setApproveTime(Date approveTime) {
        this.approveTime = approveTime;
    }



    @Override
    public String toString() {
        return "HtglContract{" +
        "id=" + id +
        ", code=" + code +
        ", name=" + name +
        ", sponsorName=" + sponsorName +
        ", sponsorId=" + sponsorId +
        ",  operatorName=" +  operatorName +
        ", operatorId=" + operatorId +
        ", draftTime=" + draftTime +
        ", budgetCode=" + budgetCode +
        ", contractMoney=" + contractMoney +
        ", authorization=" + authorization +
        ", contractCnt=" + contractCnt +
        ", approveTime=" + approveTime +
        "}";
    }
}
