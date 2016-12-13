package org.off_work_system.web;

import org.off_work_system.dto.ResultWrapper;
import org.off_work_system.enums.ResultStateEnum;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by 张栋迪 on 2016/12/9.
 */
@Controller
@RequestMapping("/error")
public class ErrorController {

    @RequestMapping(
            value = "/api/getErrorInfo",
            method = RequestMethod.POST,
            produces = {"application/json;charset=UTF-8"}
    )
    @ResponseBody
    //传入错误序号，返回错误描述
    public ResultWrapper<String> getErrorInfoAPI(@ModelAttribute("state") int state) {
         ResultStateEnum stateEnum = ResultStateEnum.stateof(state);
         if (stateEnum == null) {
             return new ResultWrapper<String>(404, null);
         }
         else {
             return new ResultWrapper<String>(200, stateEnum.getStateInfo());
         }
    }
}
