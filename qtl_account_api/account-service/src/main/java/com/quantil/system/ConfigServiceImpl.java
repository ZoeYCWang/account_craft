package com.quantil.system;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.quantil.account.AccountModel;
import com.quantil.account.AccountService;
import com.quantil.account.CustomizationViewModel;
import com.quantil.account.Token;
import com.quantil.account.permission.PermissionModel;
import com.quantil.account.permission.PermissionViewModel;
import com.quantil.common.QueryParam;
import com.zoe.snow.Global;
import com.zoe.snow.crud.CrudService;
import com.zoe.snow.crud.Result;
import com.zoe.snow.crud.service.proxy.QueryProxy;
import com.zoe.snow.message.Message;
import com.zoe.snow.model.enums.Criterion;
import com.zoe.snow.model.enums.InterventionType;
import com.zoe.snow.model.enums.Operator;
import com.zoe.snow.util.Generator;
import com.zoe.snow.util.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.Transient;
import java.io.Serializable;
import java.util.*;

/**
 * ConfigServiceImpl
 *
 * @author <a href="mailto:dwq676@126.com">daiwenqing</a>
 * @date 2017/9/30
 */
@Service("quantil.system.config.service")
public class ConfigServiceImpl implements ConfigService {
    @Autowired
    private CrudService crudService;
    @Autowired
    private AccountService accountService;

    @Override
    public Result getConfigByName(String name, String token) {
        return Result.reply(() -> {
            if (Validator.isEmpty(name))
                return Message.BadRequest.setArgs("config name can't be empty.");
            ConfigModel config = crudService.query().from(ConfigModel.class).where("name", name).one();
            if (config == null)
                return Message.BadRequest.setArgs("the config name can't be found.");
            if (config.getDataType().equals("json"))
                return config.getJsonValue();
            return config.getValue();
        });
    }

    @Override
    @Transactional
    public Result updateConfigByName(String name, String dataType, CustomizationViewModel configModel, String token) {
        return Result.reply(() -> {
            Message m = checkConfig(configModel.getCustom());
            if (m != null)
                return m;
            Token loginUser = accountService.verify(token).getData();
            /*if (!loginUser.isSuperAdmin())
                return Message.UnAuthorized;*/
            JSONObject jsonObject = JSON.parseObject(configModel.getCustom());
            /*
             * List<AccountModel> accList =
             * crudService.query().from(AccountModel.class)
             * .setExcludeDomain(true).where("appId",
             * loginUser.getAppid()).list();
             */
            ConfigModel config = crudService.query().from(ConfigModel.class).where("name", name).one();

            if (config == null) {
                config.setId(Generator.random(32));
                config = initModel(name, dataType, configModel, loginUser.getUid());
                crudService.save(config, InterventionType.INSERT);
                return config.getJsonValue();
            } else if (Validator.isEmpty(config.getValue())) {
                // config = initModel(name, dataType, configModel,
                // loginUser.getUid());
                config.setUpdatedAt(new Date());
                config.setValue(configModel.getCustom());
                config.setUpdatedBy(loginUser.getUid());
                crudService.save(config, InterventionType.UPDATE);
                return config.getJsonValue();
            }
            JSONObject jo = JSON.parseObject(config.getValue().toString());
            // List<String> posLit = new ArrayList<>();
            // Map<String, String> diffMap = new HashMap<>();
            Map<String, Object> oldCustom = new HashMap<>();
            Map<String, Object> newCustom = new HashMap<>();
            Map<String, String> oldAndNewKey = new HashMap<>();
            List<String> newKeyList = new ArrayList<>();
            for (String k : jsonObject.keySet()) {
                boolean isContain = false;
                // int pos = 0;
                for (String kk : jo.keySet()) {
                    if (k.equals(kk)) {
                        isContain = true;
                        // if to add key more the origin then add the new key
                        oldCustom.putIfAbsent(kk, jsonObject.get(kk));
                        // must override
                        oldAndNewKey.put(k, kk);
                        break;
                    } else
                        oldAndNewKey.putIfAbsent(kk, k);
                }
                if (!isContain) {
                    newCustom.putIfAbsent(k, jsonObject.get(k));
                    newKeyList.add(k);
                }
            }
            JSONObject json = new JSONObject();
            oldCustom.forEach(json::put);
            newCustom.forEach(json::put);

            List<String> theSameKey = new ArrayList<>();
            for (String k : oldAndNewKey.keySet()) {
                if (((JSONObject) config.getJsonValue()).keySet().contains(k))
                    theSameKey.add(k);
            }
            theSameKey.forEach(oldAndNewKey::remove);

            if (oldAndNewKey.size() > 0) {
                for (String k : oldAndNewKey.keySet()) {
                    if (newKeyList.size() > 0)
                        oldAndNewKey.put(newKeyList.get(0), k);
                }
            } else {
                if (newKeyList.size() > 0)
                    oldAndNewKey.put(newKeyList.get(0), newKeyList.get(0));
            }
            theSameKey.forEach(k -> oldAndNewKey.put(k, k));
            config.setValue(json.toJSONString());
            crudService.save(config, InterventionType.UPDATE);

            List<AccountModel> accList = crudService.query().from(AccountModel.class).setExcludeDomain(true).where("appId", loginUser.getAppid()).list();
            for (AccountModel acc : accList) {
                JSONObject userJsonObject = new JSONObject();
                ConfigModel cm = config;
                oldAndNewKey.forEach((k, v) -> {
                    userJsonObject.put(v,
                            acc.getCustomizationObj().get(k) == null ? ((JSONObject) cm.getJsonValue()).get(k) : acc.getCustomizationObj().get(k));
                });
                acc.setCustomization(userJsonObject.toJSONString());
                crudService.save(acc, InterventionType.UPDATE);
            }
            return config.getJsonValue();
        });
    }

    @Override
    public Result list(QueryParam queryParam, Map<String, List<String>> filters, String token) {
        return Result.reply(() -> {
            QueryProxy queryProxy = crudService.query().from(ConfigModel.class);
            boolean flag = false;
            if (filters != null) {
                if (filters.size() > 0) {
                    flag = true;
                    if (filters.get(Description.KEYS) != null) {
                        queryProxy.where(Description.NAME, Criterion.In, String.join(",", filters.get(Description.KEYS)));
                        flag = false;
                    } else if (filters.get(Description.KEY) != null) {
                        queryProxy.where(Description.NAME, filters.get(Description.KEY));
                        flag = false;
                    }

                    if (flag)
                        queryProxy.where(Description.ID, Global.Constants.STRING_NONE);
                }
            }
            if (queryParam != null)
                queryProxy.paging(queryParam.getPage(), queryParam.getSize()).order(queryParam.getSortBy(), queryParam.getOrder());
            if (queryParam.getPage() == -1 || queryParam.getSize() == -1)
                return queryProxy.list();
            return queryProxy.pageList();
        });
    }

    @Override
    public Result get(String key, String token) {
        return Result.reply(() -> {
            if (Validator.isEmpty(key))
                return Message.MustNotEmpty.setArgs(Description.KEY);
            ConfigModel configModel = crudService.query().from(ConfigModel.class).where(Description.NAME, key).one();
            return configModel;
        });
    }

    @Override
    @Transient
    public Result add(ConfigViewModel configViewModel, String token) {
        return Result.reply(() -> {
            Serializable x = check(configViewModel, null);
            if (x != null)
                return x;
            Token loginUser = accountService.verify(token).getData();
            ConfigModel configModel = new ConfigModel();
            getConfigModel(configModel, configViewModel);

            configModel.setCreatedBy(loginUser.getUid());
            configModel.setValidFlag(1);
            configModel.setCreatedAt(new Date());
            configModel.setId(Generator.random(32));
            crudService.save(configModel, InterventionType.INSERT);
            return configModel;
        });
    }

    private Serializable check(ConfigViewModel configViewModel, String exceptId) {
        if (Validator.isEmpty(configViewModel.getKey()))
            return Message.MustNotEmpty.setArgs(Description.KEY);
        if (Validator.isEmpty(configViewModel.getValue()))
            return Message.MustNotEmpty.setArgs(Description.VALUE);
        if (exits(configViewModel, exceptId))
            return Message.Exist.setArgs("key");
        return null;
    }

    private boolean exits(ConfigViewModel configViewModel, String exceptId) {
        return crudService.query().from(ConfigModel.class).where(Description.ID, Criterion.NotEqual, exceptId).where(Description.NAME, configViewModel.getKey())
                .one() != null;
    }

    private ConfigModel getConfigModel(ConfigModel configModel, ConfigViewModel configViewModel) {
        configModel.setValue(configViewModel.getValue());
        configModel.setName(configViewModel.getKey());
        String type = configViewModel.getDataType();
        if (Validator.isEmpty(type))
            type = Global.DataType.string.toString();
        configModel.setDataType(type);
        configModel.setDescription(configViewModel.getDescription());
        return configModel;
    }

    @Override
    @Transactional
    public Result delete(String id, String key, String token) {
        return Result.reply(() -> {
            if (Validator.isEmpty(id) && Validator.isEmpty(key))
                return Message.MustNotEmpty.setArgs("id or key");
            return crudService.execute().remove(ConfigModel.class).where(Description.ID, id).where(Description.NAME, key, Operator.Or).invoke();
        });
    }

    @Override
    @Transactional
    public Result update(String id, ConfigViewModel configViewModel, String token) {
        return Result.reply(() -> {
            Serializable x = check(configViewModel, id);
            if (x != null)
                return x;
            Token loginUser = accountService.verify(token).getData();
            ConfigModel configModel = crudService.query().from(ConfigModel.class).where(Description.ID, id).one();
            if (configModel == null)
                return Message.BadRequest.setArgs("id is not correct.");
            configModel.setUpdatedBy(loginUser.getUid());
            configModel.setUpdatedAt(new Date());
            getConfigModel(configModel, configViewModel);
            crudService.save(configModel, InterventionType.UPDATE);
            return configModel;
        });
    }

    private ConfigModel initModel(String name, String dataType, CustomizationViewModel configModel, String uId) {
        ConfigModel config;
        config = new ConfigModel();
        config.setCreatedAt(new Date());
        config.setCreatedBy(uId);
        config.setDataType(dataType);
        config.setName(name);
        config.setValidFlag(1);
        config.setValue(configModel.getCustom());
        return config;
    }

    private Message checkConfig(String config) {
        if (Validator.isEmpty(config))
            return Message.BadRequest.setArgs("system config can be empty.");
        net.sf.json.JSONObject jo = net.sf.json.JSONObject.fromObject(config);
        if (jo == null)
            return Message.BadRequest.setArgs("customization format is not correct.");

        return null;
    }
}
