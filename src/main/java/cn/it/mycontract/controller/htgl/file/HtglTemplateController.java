package cn.it.mycontract.controller.htgl.file;


import cn.it.mycontract.entity.HtglContract;
import cn.it.mycontract.entity.HtglFile;
import cn.it.mycontract.entity.HtglTemplate;
import cn.it.mycontract.entity.SysUser;
import cn.it.mycontract.service.HtglTemplateService;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author zhengsc
 * @since 2022-03-14
 */
@Controller
public class HtglTemplateController {

    @Autowired
    HtglTemplateService htglTemplateService;





    @RequestMapping("/queryTemplatePageList")
    public String queryHtqcPageList(HttpServletRequest request, Model model){


        List<HtglTemplate> templates = htglTemplateService.selectList(new EntityWrapper<HtglTemplate>()
                .eq("del_flag", "1"));

        model.addAttribute("templates",templates);


        return "contract/htmb/htmb-query";

    }


    @RequestMapping("/toHtmbAdd")
    public String toHtmbAdd(Model model,HttpServletRequest request){

        return "contract/htmb/htmb-add";
    }


    @RequestMapping("/saveHtmb")
    public String saveHtmb( @RequestParam("htmbFile") MultipartFile file,
                            @RequestParam("templateName") String templateName,
                            @RequestParam("templateRemarks") String templateRemarks){

        htglTemplateService.saveHtmb(file,templateName,templateRemarks);

        return "redirect:/queryTemplatePageList";
    }



    //下载合同模板
    @RequestMapping("/downloadHTMB")
    public ResponseEntity<byte[]> downloadHTMB(HttpServletRequest request, HttpServletResponse response,
                                               @RequestParam("tid") String tid) throws IOException {

        HtglTemplate htglTemplate = htglTemplateService.selectById(tid);

        String downloadFilePath="D:\\contractUpload\\htmb";//从我们的上传文件夹中去取

        File file = new File(htglTemplate.getTemplatePath());//新建一个文件

        HttpHeaders headers = new HttpHeaders();//http头信息

        String templateName = htglTemplate.getTemplateName();

        String downloadFileName = null;//设置编码
        try {
            downloadFileName = new String(templateName.getBytes("UTF-8"),"iso-8859-1");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        headers.setContentDispositionFormData("attachment", downloadFileName);

        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);

        //MediaType:互联网媒介类型  contentType：具体请求中的媒体类型信息

        try {
            return new ResponseEntity<byte[]>(FileUtils.readFileToByteArray(file),headers, HttpStatus.CREATED);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new ResponseEntity<byte[]>(FileUtils.readFileToByteArray(file),headers, HttpStatus.CREATED);
    }



    @RequestMapping("/deleteTemplate")
    public String deleteTemplate(@RequestParam("tid") String tid){
        HtglTemplate htglTemplate = new HtglTemplate();
        htglTemplate.setDelFlag("0");

        htglTemplateService.update(htglTemplate,new EntityWrapper<HtglTemplate>()
                .eq("template_id",tid));

        return "redirect:/queryTemplatePageList";


    }

}
