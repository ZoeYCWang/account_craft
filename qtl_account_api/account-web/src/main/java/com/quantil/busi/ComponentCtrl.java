package com.quantil.busi;

import com.quantil.busi.api.ComponentInfoService;
import com.quantil.busi.api.OssFileService;
import com.quantil.busi.desc.ComponentInfoDesc;
import com.quantil.busi.vo.CreateComponentViewModel;
import com.quantil.common.BaseCtrl;
import com.quantil.common.Notes;
import com.quantil.common.Constant;
import com.quantil.common.map.StringMapUtil;
import com.quantil.common.map.ValueInfo;
import com.zoe.snow.crud.Result;
import com.zoe.snow.log.Logger;
import com.zoe.snow.message.Message;
import com.zoe.snow.util.Validator;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.InputStream;
import java.util.Map;

/**
 * Created by Administrator on 2018/5/28.
 */

@Controller("account.component.ctrl")
@RequestMapping("/api/0.1/components")
@RestController
@Api(value = "components", description = " ", tags = {"components"})
public class ComponentCtrl extends BaseCtrl {

    @Autowired
    private ComponentInfoService componentInfoService;

    @Autowired
    private OssFileService ossFileService;

    @RequestMapping(value = "/{id:.+}", method = RequestMethod.GET)
    @ApiOperation(value = "get component by id", notes = Notes.RESPONSE_HEAD)
    public Object getById(@ApiParam(name = "id", value = "the component id") @PathVariable("id") String id) {
        return reply(componentInfoService.findById(id, getToken()));
    }

    @RequestMapping(value = "", method = RequestMethod.POST)
    @ApiOperation(value = "add a component", notes = Notes.RESPONSE_HEAD)
    public Object addComponent(@ApiParam(name = "component", value = "component submit info") @RequestBody CreateComponentViewModel createComponentViewModel) {
        return reply(componentInfoService.add(createComponentViewModel, getToken()));
    }

    @RequestMapping(value = "/{id:.+}", method = RequestMethod.PUT)
    @ApiOperation(value = "update the component info", notes = Notes.RESPONSE_HEAD)
    public Object updateRole(@ApiParam(name = "id", value = "component id") @PathVariable("id") String id,
                             @ApiParam(name = "component", value = "the component model") @RequestBody CreateComponentViewModel createComponentViewModel) {
        return reply(componentInfoService.update(id, createComponentViewModel, getToken()));
    }

    @RequestMapping(value = "", method = RequestMethod.GET)
    @ApiOperation(value = "query operation", notes = Notes.RESPONSE_HEAD)
    public Object query(@ApiParam(name = "name", value = "key word") @RequestParam(required = false) String name,
                        @ApiParam(name = "type", value = "query by type,default:-1, denote query all type") @RequestParam(required = false) String type,
                        @ApiParam(name = "companyId", value = "query by company,default:-1, denote query all company") @RequestParam(required = false) String companyId,
                        @ApiParam(name = "page", value = "page，default：1") @RequestParam(required = false) Integer page,
                        @ApiParam(name = "size", value = "size per page，default：10") @RequestParam(required = false) Integer size,
                        @ApiParam(name = "isValigue", value = "is query by key word valiguely，default：false ,") @RequestParam(required = false) boolean isValigue) {
        if (Validator.isEmpty(page)) {
            page = Constant.PAGE_DEFAULT;
        }
        if (Validator.isEmpty(size)) {
            size = Constant.PAGESIZE_DEFAULT;
        }
        if (type == null) {
            type = "-1";
        }
        if (Validator.isEmpty(companyId)){
            companyId = "-1";
        }
        Map<String, ValueInfo> map = StringMapUtil.createMap().addKeyValueInfo(ComponentInfoDesc.name_field, name, isValigue).addKeyValueInfo(ComponentInfoDesc.type_name, type, false).addKeyValueInfo(ComponentInfoDesc.company_id_field, companyId, false).toGenerateInfo();
        return componentInfoService.findByConditionList(map, page, size, this.getToken());
    }

    @RequestMapping(value = "/file", method = RequestMethod.POST)
    @ApiOperation(value = "put component file", notes = Notes.RESPONSE_HEAD)
    public Object uploadFile(@ApiParam(value = "upload file") MultipartFile file, HttpServletRequest r) {

        Result result = new Result();
        MultipartFile files = this.getMultiFile(r);
        if (Validator.isEmpty(files)) {
            result.setResult(null, false, Message.Failed);
            return result;
        }
        String fileName = files.getOriginalFilename();
        InputStream inputStream;
        try {
            inputStream = files.getInputStream();
            result = ossFileService.putFile(fileName, inputStream, "component", this.getToken());
            return result;
        } catch (Exception e) {
            Logger.error(e, "文件上传错误！");
            result.setResult(null, false, Message.Error);
            return result;
        }

    }


    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = "application/json;charset=UTF-8")
    @ApiOperation(value = "delete by id", notes = Notes.RESPONSE_HEAD)
    public Object deleteById(@ApiParam(name = "id", value = "the component id") @PathVariable("id") String id) {
        return componentInfoService.deleteById(id, this.getToken());
    }

}
