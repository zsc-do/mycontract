package cn.it.mycontract.vo;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.enums.IdType;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import java.util.Date;

public class htglContractVo {

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
    @NotEmpty(message = "我在这")
    private String contractName;

    /**
     * 主办单位名称
     */
    @NotEmpty
    private String sponsorName;

    /**
     * 主办单位id
     */
    private Integer sponsorId;

    /**
     * 经办人名称
     */
    @NotEmpty
    private String  operatorName;

    /**
     * 经办人id
     */
    private Integer operatorId;

    /**
     * 起草日期
     */
    @NotEmpty
    private String draftTime;

    /**
     * 财务预算编号
     */
    @NotEmpty
    private String budgetCode;

    /**
     * 合同金额
     */
    @NotEmpty
    private String contractMoney;

    /**
     * 合同授权代表
     */
    @NotEmpty
    private String authorization;

    /**
     * 合同份数
     */
    @Pattern(regexp = "^[0-9]*$")
    @NotEmpty
    private String contractCnt;

    /**
     * 合同签订日期
     */
    private Date approveTime;


    @NotEmpty
    String partenerName;


    @Pattern(regexp = "^[0-9]*$")
    @NotEmpty
    String leaderId;

    @Pattern(regexp = "^[0-9]*$")
    @NotEmpty
    String departmentsId;


    @Pattern(regexp = "^[0-9]*$")
    @NotEmpty
    String bossId;


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

    public String getContractName() {
        return contractName;
    }

    public void setContractName(String contractName) {
        this.contractName = contractName;
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

    public String getDraftTime() {
        return draftTime;
    }

    public void setDraftTime(String draftTime) {
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

    public String getPartenerName() {
        return partenerName;
    }

    public void setPartenerName(String partenerName) {
        this.partenerName = partenerName;
    }

    public String getLeaderId() {
        return leaderId;
    }

    public void setLeaderId(String leaderId) {
        this.leaderId = leaderId;
    }

    public String getDepartmentsId() {
        return departmentsId;
    }

    public void setDepartmentsId(String departmentsId) {
        this.departmentsId = departmentsId;
    }

    public String getBossId() {
        return bossId;
    }

    public void setBossId(String bossId) {
        this.bossId = bossId;
    }


    @Override
    public String toString() {
        return "htglContractVo{" +
                "id=" + id +
                ", code='" + code + '\'' +
                ", contractName='" + contractName + '\'' +
                ", sponsorName='" + sponsorName + '\'' +
                ", sponsorId=" + sponsorId +
                ", operatorName='" + operatorName + '\'' +
                ", operatorId=" + operatorId +
                ", draftTime=" + draftTime +
                ", budgetCode='" + budgetCode + '\'' +
                ", contractMoney='" + contractMoney + '\'' +
                ", authorization='" + authorization + '\'' +
                ", contractCnt='" + contractCnt + '\'' +
                ", approveTime=" + approveTime +
                ", partenerName='" + partenerName + '\'' +
                ", leaderId='" + leaderId + '\'' +
                ", departmentsId='" + departmentsId + '\'' +
                ", bossId='" + bossId + '\'' +
                '}';
    }
}
