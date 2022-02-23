package cn.it.mycontract.entity;

import com.baomidou.mybatisplus.enums.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.enums.IdType;
import com.baomidou.mybatisplus.annotations.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author zhengsc
 * @since 2022-02-23
 */
@TableName("htgl_opinion")
public class HtglOpinion implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 意见id
     */
    @TableId(value = "opinion_id", type = IdType.AUTO)
    private Integer opinionId;

    /**
     * 合同id
     */
    private Integer contractId;

    /**
     * 是否通过，0为未通过，1为提供
     */
    private String isPassed;

    /**
     * 意见人id
     */
    private Integer personId;

    /**
     * 填写时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date inputTime;

    /**
     * 意见人名
     */
    private String personName;



    /**
     * 意见部门名
     */
    private String areaName;

    /**
     * 意见内容
     */
    private String opinionContent;

    public Integer getOpinionId() {
        return opinionId;
    }

    public void setOpinionId(Integer opinionId) {
        this.opinionId = opinionId;
    }
    public Integer getContractId() {
        return contractId;
    }

    public void setContractId(Integer contractId) {
        this.contractId = contractId;
    }
    public String getIsPassed() {
        return isPassed;
    }

    public void setIsPassed(String isPassed) {
        this.isPassed = isPassed;
    }
    public Integer getPersonId() {
        return personId;
    }

    public void setPersonId(Integer personId) {
        this.personId = personId;
    }
    public Date getInputTime() {
        return inputTime;
    }

    public void setInputTime(Date inputTime) {
        this.inputTime = inputTime;
    }
    public String getPersonName() {
        return personName;
    }

    public void setPersonName(String personName) {
        this.personName = personName;
    }

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }
    public String getOpinionContent() {
        return opinionContent;
    }

    public void setOpinionContent(String opinionContent) {
        this.opinionContent = opinionContent;
    }

    @Override
    public String toString() {
        return "HtglOpinion{" +
                "opinionId=" + opinionId +
                ", contractId=" + contractId +
                ", isPassed='" + isPassed + '\'' +
                ", personId=" + personId +
                ", inputTime=" + inputTime +
                ", personName='" + personName + '\'' +
                ", areaName='" + areaName + '\'' +
                ", opinionContent='" + opinionContent + '\'' +
                '}';
    }
}
