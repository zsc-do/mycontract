package cn.it.mycontract.service.impl;

import cn.it.mycontract.entity.HtglContract;
import cn.it.mycontract.entity.HtglFile;
import cn.it.mycontract.entity.HtglTemplate;
import cn.it.mycontract.mapper.HtglTemplateMapper;
import cn.it.mycontract.service.HtglTemplateService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author zhengsc
 * @since 2022-03-14
 */
@Service
public class HtglTemplateServiceImpl extends ServiceImpl<HtglTemplateMapper, HtglTemplate> implements HtglTemplateService {



    @Autowired
    HtglTemplateMapper htglTemplateMapper;

    @Override
    @Transactional
    public void saveHtmb(MultipartFile file, String templateName,String templateRemarks) {

        String uuid = UUID.randomUUID().toString().replaceAll("-", "");


        int pos = file.getOriginalFilename().lastIndexOf('.');
        String houZui = "";
        if (pos > -1) {
            houZui = file.getOriginalFilename().substring(pos);
        }

        String filePath = "D:\\contractUpload\\htmb\\"+uuid + houZui;

        try {
            file.transferTo(new File(filePath));
        } catch (IOException e) {
            e.printStackTrace();
        }




        HtglTemplate htglTemplate = new HtglTemplate();
        htglTemplate.setDelFlag("1");
        htglTemplate.setTemplateName(templateName + houZui);
        htglTemplate.setTemplatePath(filePath);
        htglTemplate.setRemarks(templateRemarks);

        htglTemplateMapper.insert(htglTemplate);

    }





}
