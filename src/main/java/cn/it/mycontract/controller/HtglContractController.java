package cn.it.mycontract.controller;


import cn.it.mycontract.entity.HtglContract;
import cn.it.mycontract.entity.SysUser;
import cn.it.mycontract.service.HtglContractService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author zhengsc
 * @since 2022-02-14
 */
@Controller
public class HtglContractController {
    @Autowired
    public HtglContractService htglContractService;

    @RequestMapping("/queryHtqcPageList")
    public String queryHtqcPageList(Model model){

        List<HtglContract> htglContracts = htglContractService.selectList(null);


        model.addAttribute("contracts",htglContracts);


        return "contract/htqc/htqc-query";

    }


    @RequestMapping("/toHtqcAdd")
    public String toHtqcAdd(){
        return "contract/htqc/htqc-add";
    }

}
