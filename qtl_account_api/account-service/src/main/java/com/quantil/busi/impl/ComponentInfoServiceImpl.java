package com.quantil.busi.impl;


import com.quantil.account.AccountModel;
import com.quantil.account.AccountService;
import com.quantil.account.Token;
import com.quantil.busi.api.CodeHelp;
import com.quantil.busi.api.ComponentInfoService;
import com.quantil.busi.model.CompanyInfoModel;
import com.quantil.busi.model.ComponentInfoModel;
import com.quantil.common.DictCodeConstant;
import com.quantil.common.client.OssClient;
import com.quantil.common.map.ValueInfo;
import com.zoe.snow.crud.CrudService;
import com.zoe.snow.crud.service.proxy.QueryProxy;
import com.zoe.snow.message.Message;
import com.zoe.snow.model.enums.JoinType;
import com.zoe.snow.util.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
* @author 
* @date 2018-5-20 11:39:59
*/
@Service("com.craft.service.ComponentInfo.impl")
public class ComponentInfoServiceImpl extends SuportServiceImpl<ComponentInfoModel> implements ComponentInfoService {

    @Autowired
    private CodeHelp codeHelp;
    @Autowired
    private AccountService accountService;

    @Autowired
    private CrudService crudService;

    @Override
    public void preAddProcess(ComponentInfoModel componentInfoModel,String token) {
        componentInfoModel.setIsUse("1");
        this.changePhotoToRelatePath(componentInfoModel);
        this.addCompany(componentInfoModel,token);
    }

    @Override
    public void preUpdateProcess(ComponentInfoModel componentInfoModel,String token) {
        this.changePhotoToRelatePath(componentInfoModel);
    }

    @Override
    public void afterFindByIdProcess(ComponentInfoModel componentInfoModel,String token) {
        this.changePhotoToAbsolutePath(componentInfoModel);
    }

    @Override
    public void preQueryProcess(Map<String, ValueInfo> map, QueryProxy queryProxy,String token) {
        queryProxy.join(CompanyInfoModel.class, JoinType.Left);
    }

    @Override
    public List afterQueryProcess(List<ComponentInfoModel> list,String token) {
        if (!Validator.isEmpty(list)){
            for (ComponentInfoModel componentInfoModel:list){
                this.changePhotoToAbsolutePath(componentInfoModel);
                this.convertDictCode(componentInfoModel);
            }
        }
        return list;
    }

    private void addCompany(ComponentInfoModel componentInfoModel, String token){
        Token loginUser = accountService.verify(token).getData();
        if (loginUser == null)
            throw new RuntimeException(Message.UnAuthorized.toString());
        AccountModel accountModel = crudService.query().from(AccountModel.class).setExcludeDomain(true).where("appId", loginUser.getAppid())
                .where("id", loginUser.getUid()).one();
        CompanyInfoModel companyInfoModel = new CompanyInfoModel();
        companyInfoModel.setId(accountModel.getCompanyId());
        componentInfoModel.setCompanyInfoModel(companyInfoModel);
    }

    private void convertDictCode(ComponentInfoModel componentInfoModel){
        String type = componentInfoModel.getType();
        componentInfoModel.setType(codeHelp.getName(DictCodeConstant.component_type,type));
    }

    private void changePhotoToRelatePath(ComponentInfoModel componentInfoModel){
        String phoneUrl = componentInfoModel.getPhoto();
        if (!Validator.isEmpty(phoneUrl)){
            if (phoneUrl.startsWith(OssClient.baseOssUrl)){
                phoneUrl = phoneUrl.substring(OssClient.baseOssUrl.length());
                componentInfoModel.setPhoto(phoneUrl);
            }
        }
    }



    private void changePhotoToAbsolutePath(ComponentInfoModel componentInfoModel){
        String photoUrl = componentInfoModel.getPhoto();
        if (Validator.isEmpty(photoUrl)){
            photoUrl = OssClient.defaultPhotoAddr;
        }
        photoUrl = OssClient.baseOssUrl + photoUrl;
        componentInfoModel.setPhoto(photoUrl);
    }


}
