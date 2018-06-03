package com.quantil.busi;

import com.quantil.busi.api.CodeService;
import com.quantil.common.BaseCtrl;
import com.quantil.common.DictCodeConstant;
import com.quantil.common.Notes;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Administrator on 2018/6/3.
 */
@Controller("account.code.ctrl")
@RequestMapping("/api/0.1/codes")
@RestController
@Api(value = "codes", description = " ", tags = {"codes"})
public class CodeCtrl extends BaseCtrl {

    @Autowired
    private CodeService codeService;

    @RequestMapping(value = "/component/type", method = RequestMethod.GET)
    @ApiOperation(value = "get component type", notes = Notes.RESPONSE_HEAD)
    public Object getById() {
        return reply(codeService.getCodeInfoList(DictCodeConstant.component_type,this.getToken()));
    }
}
