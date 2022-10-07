package cn.it.mycontract.controller.htgl.file;


import cn.it.mycontract.entity.HtglFile;
import cn.it.mycontract.service.HtglFileService;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author zhengsc
 * @since 2022-02-23
 */
@Controller
public class HtglFileController {

    @Autowired
    HtglFileService htglFileService;


    //下载合同正文方法
    @RequestMapping("/downloadHTZW")
    public void downloadHTZW(HttpServletRequest request, HttpServletResponse response,
                             @RequestParam("cid") String cid) throws IOException {

        HtglFile htglFile = htglFileService.selectOne(new EntityWrapper<HtglFile>()
                .eq("contract_id", cid)
                .eq("status","1")
                .eq("type","1"));

//        String downloadFilePath="D:\\contractUpload";//从我们的上传文件夹中去取

        String downloadFilePath="/opt/contractUpload";

        File file = new File(htglFile.getFilePath());//新建一个文件

        HttpHeaders headers = new HttpHeaders();//http头信息

        String filename = htglFile.getFileName();



        String mimeType = request.getServletContext().getMimeType(filename);
        response.setContentType(mimeType);


        String downloadFileName = new String(filename.getBytes("UTF-8"),"iso-8859-1");

        response.setHeader("Content-Disposition","attachment;filename="+downloadFileName);

        FileInputStream is = new FileInputStream(file);


        ServletOutputStream os = response.getOutputStream();

        IOUtils.copy(is,os);
        is.close();
        os.close();

    }


    //下载合同正文方法
    @RequestMapping("/downloadHTSMJ")
    public void downloadHTSMJ(HttpServletRequest request, HttpServletResponse response,
                                               @RequestParam("cid") String cid) throws IOException {

        HtglFile htglFile = htglFileService.selectOne(new EntityWrapper<HtglFile>()
                .eq("contract_id", cid)
                .eq("status","1")
                .eq("type","2"));

//        String downloadFilePath="D:\\contractUpload";//从我们的上传文件夹中去取

        String downloadFilePath="/opt/contractUpload";
        File file = new File(htglFile.getFilePath());//新建一个文件

        HttpHeaders headers = new HttpHeaders();//http头信息

        String filename = htglFile.getFileName();



        String mimeType = request.getServletContext().getMimeType(filename);
        response.setContentType(mimeType);


        String downloadFileName = new String(filename.getBytes("UTF-8"),"iso-8859-1");

        response.setHeader("Content-Disposition","attachment;filename="+downloadFileName);

        FileInputStream is = new FileInputStream(file);


        ServletOutputStream os = response.getOutputStream();

        IOUtils.copy(is,os);
        is.close();
        os.close();

    }
}
