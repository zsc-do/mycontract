package cn.it.mycontract.service;

import cn.it.mycontract.entity.HtglTemplate;
import com.baomidou.mybatisplus.service.IService;
import org.springframework.web.multipart.MultipartFile;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author zhengsc
 * @since 2022-03-14
 */
public interface HtglTemplateService extends IService<HtglTemplate> {

    void saveHtmb(MultipartFile file, String templateName,String templateRemarks);
}
