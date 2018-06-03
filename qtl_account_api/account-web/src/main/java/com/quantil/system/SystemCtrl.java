package com.quantil.system;

import com.alibaba.fastjson.JSONObject;
import com.quantil.account.CustomizationViewModel;
import com.quantil.account.permission.PermissionViewModel;
import com.quantil.common.BaseCtrl;
import com.quantil.common.Notes;
import com.quantil.common.QueryParam;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * SystemCtrl
 *
 * @author <a href="mailto:dwq676@126.com">daiwenqing</a>
 * @date 2017/8/24
 */
@Controller("rdc.system.version.ctrl")
@RestController
@RequestMapping(value = "/api/0.1/system")
@Api(value = "system", description = " ", tags = { "system" })
public class SystemCtrl extends BaseCtrl {
    @Autowired
    private VersionService versionService;
    @Autowired
    private ConfigService configService;

    @RequestMapping(value = "/user_custom", method = RequestMethod.GET)
    @ApiOperation(value = "Get user custom", notes = Notes.RESPONSE_HEAD)
    public Object getCustom() {
        return reply(configService.getConfigByName("custom", getToken()));
    }

    @RequestMapping(value = "/user_custom", method = RequestMethod.PUT)
    @ApiOperation(value = "Get user custom", notes = Notes.RESPONSE_HEAD)
    public Object updateCustom(@ApiParam(name = "custom", value = "custom json") @RequestBody JSONObject custom) {
        CustomizationViewModel customizationViewModel = new CustomizationViewModel();
        customizationViewModel.setCustom(custom.toJSONString());
        return reply(configService.updateConfigByName("custom", "json", customizationViewModel, getToken()));
    }

    @RequestMapping(value = "/version", method = RequestMethod.GET)
    @ApiOperation(value = "Get account api version", notes = Notes.RESPONSE_HEAD + Description.VERSION_RESPONSE)
    public Object getVersion() {
        return reply(versionService.get(getToken()));
    }

    @RequestMapping(value = "/key_values", method = RequestMethod.GET)
    @ApiOperation(value = "Get user custom", notes = Notes.RESPONSE_HEAD)
    @ResponseBody
    public Object list(@ApiParam(name = "filters", value = "filter column") @RequestParam(required = false) String filters,
            @ApiParam(name = "sortby", value = "the field name sort by") @RequestParam(required = false) String sortby,
            @ApiParam(name = "order", value = "desc or asc") @RequestParam(required = false) String order,
            @ApiParam(name = "page", value = "page num") @RequestParam(required = false) Integer page,
            @ApiParam(name = "size", value = "each page size") @RequestParam(required = false) Integer size) {

        QueryParam queryParam = new QueryParam(page, size, sortby, order);
        // DJson.parseJson(filters.toJSONString(),Map<String,List<String>>.class);
        Map<String, List<String>> map = filter(filters);
        return reply(configService.list(queryParam, map, getToken()));
    }

    @RequestMapping(value = "/key_values/{key:.+}", method = RequestMethod.GET)
    @ApiOperation(value = "get value by key")
    public Object get(@ApiParam(name = "key", value = "key") @PathVariable("key") String key) {
        return reply(configService.get(key, getToken()));
    }

    @RequestMapping(value = "/key_values/{id:.+}", method = RequestMethod.PUT)
    @ApiOperation(value = "add the permission object")
    public Object update(@ApiParam(name = "id", value = "id") @PathVariable("id") String id,
            @ApiParam(name = "key_value", value = "key value object") @RequestBody ConfigViewModel key_value) {
        return reply(configService.update(id, key_value, getToken()));
    }

    @RequestMapping(value = "/key_values", method = RequestMethod.POST)
    @ApiOperation(value = "add the key property")
    public Object add(@ApiParam(name = "key_value", value = "key value object") @RequestBody ConfigViewModel key_value) {
        return reply(configService.add(key_value, getToken()));
    }

    @RequestMapping(value = "/key_values/{id:.+}", method = RequestMethod.DELETE)
    @ApiOperation(value = "delete a value by the key")
    public Object delete(@ApiParam(name = "id", value = "id", required = false) @PathVariable(value = "id", required = false) String id,
            @ApiParam(name = "key", value = "key", required = false) @RequestParam(required = false) String key) {
        return reply(configService.delete(id, key, getToken()));
    }
}
