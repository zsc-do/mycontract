package cn.it.mycontract.controller.htgl.opinion;


import cn.it.mycontract.entity.HtglOpinion;
import cn.it.mycontract.service.HtglOpinionService;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author zhengsc
 * @since 2022-02-23
 */
@Controller
public class HtglOpinionController {

    @Autowired
    private HtglOpinionService htglOpinionService;


    @ResponseBody
    @RequestMapping("getOpinions")
    public List<HtglOpinion> getOpinions(@RequestParam("cid") String cid){

        List<HtglOpinion> opinions = htglOpinionService.selectList(new EntityWrapper<HtglOpinion>()
                .eq("contract_id", cid));

        return opinions;
    }
}
