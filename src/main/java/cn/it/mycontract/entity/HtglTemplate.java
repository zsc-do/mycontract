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
 * @since 2022-03-14
 */
@TableName("htgl_template")
public class HtglTemplate implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 模板id
     */
    @TableId(value = "template_id", type = IdType.AUTO)
    private Integer templateId;

    /**
     * 模板名
     */
    private String templateName;

    /**
     * 模板路径
     */
    private String templatePath;

    private String remarks;

    /**
     * 模板状态，0表示无效，1表示有效
     */
    private String delFlag;


    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public Integer getTemplateId() {
        return templateId;
    }

    public void setTemplateId(Integer templateId) {
        this.templateId = templateId;
    }
    public String getTemplateName() {
        return templateName;
    }

    public void setTemplateName(String templateName) {
        this.templateName = templateName;
    }
    public String getTemplatePath() {
        return templatePath;
    }

    public void setTemplatePath(String templatePath) {
        this.templatePath = templatePath;
    }
    public String getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(String delFlag) {
        this.delFlag = delFlag;
    }

    @Override
    public String toString() {
        return "HtglTemplate{" +
        "templateId=" + templateId +
        ", templateName=" + templateName +
        ", templatePath=" + templatePath +
        ", delFlag=" + delFlag +
        "}";
    }
}
