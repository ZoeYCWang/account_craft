package com.quantil.busi.api;


import com.quantil.busi.vo.CreateCompanyViewModel;
import com.zoe.snow.crud.Result;

/**
* @author 
* @date 2018-5-15 2:10:36
*/
public interface CompanyInfoService extends IBaseService {

    String getNameById(String id);
}