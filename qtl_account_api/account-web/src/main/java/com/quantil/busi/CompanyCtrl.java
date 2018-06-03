package com.quantil.busi;

import com.quantil.busi.api.CompanyInfoService;
import com.quantil.busi.vo.CreateCompanyViewModel;
import com.quantil.common.BaseCtrl;
import com.quantil.common.Notes;
import com.quantil.common.Constant;
import com.quantil.common.map.StringMapUtil;
import com.quantil.common.map.ValueInfo;
import com.zoe.snow.util.Validator;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * Created by Administrator on 2018/5/28.
 */

@Controller("account.company.ctrl")
@RequestMapping("/api/0.1/companys")
@RestController
@Api(value = "companys", description = " ", tags = {"companys"})
public class CompanyCtrl extends BaseCtrl {

    @Autowired
    private CompanyInfoService companyInfoService;


    @RequestMapping(value = "/{id:.+}", method = RequestMethod.GET)
    @ApiOperation(value = "get company by id", notes = Notes.RESPONSE_HEAD)
    public Object getById(@ApiParam(name = "id", value = "the company id") @PathVariable("id") String id) {
        return reply(companyInfoService.findById(id, getToken()));
    }

    @RequestMapping(value = "", method = RequestMethod.POST)
    @ApiOperation(value = "add a company", notes = Notes.RESPONSE_HEAD)
    public Object addCompany(@ApiParam(name = "company", value = "company submit info") @RequestBody CreateCompanyViewModel createCompanyViewModel) {
        return reply(companyInfoService.add(createCompanyViewModel, getToken()));
    }

    @RequestMapping(value = "/{id:.+}", method = RequestMethod.PUT)
    @ApiOperation(value = "update the company info", notes = Notes.RESPONSE_HEAD)
    public Object updateRole(@ApiParam(name = "id", value = "company id") @PathVariable("id") String id,
                             @ApiParam(name = "company", value = "the company model") @RequestBody CreateCompanyViewModel createCompanyViewModel) {
        return reply(companyInfoService.update(id, createCompanyViewModel, getToken()));
    }

    @RequestMapping(value = "", method = RequestMethod.GET)
    @ApiOperation(value = "query operation", notes = Notes.RESPONSE_HEAD)
    public Object query(@ApiParam(name = "name", value = "key word") @RequestParam(required = false) String name,
                        @ApiParam(name = "page", value = "page，default：1") @RequestParam(required = false) Integer page,
                        @ApiParam(name = "size", value = "size per page，default：10") @RequestParam(required = false) Integer size,
                        @ApiParam(name = "isValigue", value = "is query by key word valiguely，default：false ,") @RequestParam(required = false) boolean isValigue) {
        if (Validator.isEmpty(page)) {
            page = Constant.PAGE_DEFAULT;
        }
        if (Validator.isEmpty(size)) {
            size = Constant.PAGESIZE_DEFAULT;
        }
        Map<String, ValueInfo> map = StringMapUtil.createMap().addKeyValueInfo("name", name, isValigue).toGenerateInfo();
        return companyInfoService.findByConditionList(map, page, size, this.getToken());
    }


    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = "application/json;charset=UTF-8")
    @ApiOperation(value = "delete by id", notes = Notes.RESPONSE_HEAD)
    public Object deleteById(@ApiParam(name = "id", value = "the company id") @PathVariable("id") String id) {
        return companyInfoService.deleteById(id, this.getToken());
    }

}
