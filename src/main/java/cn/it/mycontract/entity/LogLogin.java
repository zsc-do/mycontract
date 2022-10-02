package cn.it.mycontract.entity;


import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;

import java.util.Date;

@TableName("log_login")
public class LogLogin {


    @TableId(value = "login_id", type = IdType.AUTO)
    private Integer loginId;


    private String loginName;

    private Date loginTime;


    public Integer getLoginId() {
        return loginId;
    }

    public void setLoginId(Integer loginId) {
        this.loginId = loginId;
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public Date getLoginTime() {
        return loginTime;
    }

    public void setLoginTime(Date loginTime) {
        this.loginTime = loginTime;
    }


    @Override
    public String toString() {
        return "LogLogin{" +
                "loginId=" + loginId +
                ", loginName='" + loginName + '\'' +
                ", loginTime=" + loginTime +
                '}';
    }
}
