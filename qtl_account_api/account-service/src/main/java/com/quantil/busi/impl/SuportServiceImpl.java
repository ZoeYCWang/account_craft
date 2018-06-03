package com.quantil.busi.impl;


import com.quantil.busi.exception.ParamValidateException;
import com.quantil.busi.vo.ViewModel;
import com.quantil.busi.vo.annotation.MaxLength;
import com.quantil.busi.vo.annotation.Required;
import com.quantil.common.map.ValueInfo;
import com.zoe.snow.auth.NoNeedVerify;
import com.zoe.snow.bean.BeanFactory;
import com.zoe.snow.context.aop.annotation.Statistics;
import com.zoe.snow.crud.CrudService;
import com.zoe.snow.crud.Result;
import com.zoe.snow.crud.service.proxy.QueryProxy;
import com.zoe.snow.message.Message;
import com.zoe.snow.model.Model;
import com.zoe.snow.model.annotation.Property;
import com.zoe.snow.model.enums.Criterion;
import com.zoe.snow.model.enums.InterventionType;
import com.zoe.snow.model.support.BaseModelSupport;
import com.zoe.snow.util.Generator;
import com.zoe.snow.util.Validator;
import org.springframework.transaction.annotation.Transactional;
import sun.reflect.generics.reflectiveObjects.ParameterizedTypeImpl;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Iterator;
import java.util.List;
import java.util.Map;


/**
 * Created by Administrator on 2018/5/13.
 */
public abstract class SuportServiceImpl<T extends Model> {
    @Statistics
    @NoNeedVerify
    public Result findById(String id, String token) {
        return Result.reply(() -> {
            CrudService crudService = BeanFactory.getBean(CrudService.class);
            Class<T> tClass = (Class<T>) ((ParameterizedTypeImpl) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
            T t = crudService.findById(tClass, id);
            this.afterFindByIdProcess(t,token);
            return t;
        });
    }

    @Statistics
    @NoNeedVerify
    public Result findByConditionList(Map<String, ValueInfo> map, int page, int size, String token) {
        return Result.reply(() -> {
            CrudService crudService = BeanFactory.getBean(CrudService.class);
            Class<T> tClass = (Class<T>) ((ParameterizedTypeImpl) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
            QueryProxy queryProxy = crudService.query().from(tClass);
            preQueryProcess(map, queryProxy,token);
            queryProxy = this.createWhere(queryProxy, map);
            queryProxy.setExcludeDomain(true);
            List<T> qeuryList = null;
            if (page == -1) {
                qeuryList = queryProxy.list();
            } else {
                queryProxy.paging(page, size);
                qeuryList = queryProxy.pageList();
            }
            List resultList = afterQueryProcess(qeuryList,token);
            return resultList;
        });
    }

    @NoNeedVerify
    public Result findCount(Map<String, ValueInfo> map, String token) {
        return Result.reply(() -> {
            CrudService crudService = BeanFactory.getBean(CrudService.class);
            Class<T> tClass = (Class<T>) ((ParameterizedTypeImpl) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
            QueryProxy queryProxy = crudService.query().from(tClass);
            queryProxy = this.createWhere(queryProxy, map);
            queryProxy.setExcludeDomain(true);
            return crudService.query().from(tClass).count();
        });
    }

    protected QueryProxy createWhere(QueryProxy queryProxy, Map<String, ValueInfo> map) {
        if (!Validator.isEmpty(map)) {
            Iterator<String> iterator = map.keySet().iterator();
            while (iterator.hasNext()) {
                String key = iterator.next();
                ValueInfo valueInfo = map.get(key);
                if ("-1".equals(valueInfo.getValue())) {
                    continue;
                }
                if (valueInfo.isValigue()) {
                    queryProxy.where(key, Criterion.Like, valueInfo.getValue());
                } else {
                    queryProxy.where(key, Criterion.Equals, valueInfo.getValue());
                }
            }
        }
        return queryProxy;
    }


    @Statistics
    @NoNeedVerify
    public Result deleteById(String id, String token) {
        return Result.reply(() -> {
            CrudService crudService = BeanFactory.getBean(CrudService.class);
            Class<T> tClass = (Class<T>) ((ParameterizedTypeImpl) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
            return crudService.deleteById(tClass, id);
        });
    }

    @NoNeedVerify
    @Transactional
    public <M extends ViewModel> Result add(M m, String token) {
        return Result.reply(() -> {
            try {
                CrudService crudService = BeanFactory.getBean(CrudService.class);
                Class<T> tClass = (Class<T>) ((ParameterizedTypeImpl) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
                T t = this.convertAndValidate(m, tClass.newInstance());
                t.setId(Generator.uuid());
                ((BaseModelSupport) t).setValidFlag(1);
                preAddProcess(t,token);
                crudService.save(t, InterventionType.INSERT);
                return t;
            } catch (ParamValidateException e) {
                return Message.BadRequest.setArgs(e.getMessage());
            } catch (InstantiationException e) {
                return Message.Failed.setArgs("初始化对象出现故障");
            } catch (IllegalAccessException e) {
                return Message.Failed.setArgs("初始化对象出现故障");
            }
        });
    }

    @NoNeedVerify
    @Transactional
    public <M extends ViewModel> Result update(String id, M m, String token) {
        return Result.reply(() -> {
            try {
                CrudService crudService = BeanFactory.getBean(CrudService.class);
                Class<T> tClass = (Class<T>) ((ParameterizedTypeImpl) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
                T t = crudService.findById(tClass, id);
                this.convertAndValidate(m, t);
                this.preUpdateProcess(t,token);
                crudService.save(t, InterventionType.UPDATE);
                return t;
            } catch (ParamValidateException e) {
                return Message.BadRequest.setArgs(e.getMessage());
            }
        });
    }

    public abstract void preAddProcess(T t,String token);

    public abstract void preUpdateProcess(T t,String token);

    public abstract List afterQueryProcess(List<T> list,String token);

    public abstract void afterFindByIdProcess(T t,String token);

    public abstract void preQueryProcess(Map<String, ValueInfo> map, QueryProxy queryProxy,String token);

    protected <M extends ViewModel> T convertAndValidate(M m, T t) throws ParamValidateException {
        try {
            Class tClazz = t.getClass();
            Class mClazz = m.getClass();

            if (Validator.isEmpty(m)) {
                throw new ParamValidateException(this.getPropertyName(tClazz) + "不能为空");
            }
            Method[] methods = mClazz.getDeclaredMethods();
            for (Method method : methods) {
                String methodName = method.getName();
                if (methodName.startsWith("get")) {
                    Object o = method.invoke(m);
                    Method tGetMethod = tClazz.getDeclaredMethod(methodName, method.getParameterTypes());
                    String propertyName = this.getPropertyName(tGetMethod);
                    this.validateFieldNotEmpty(method, propertyName, o);
                    this.validateFieldMaxLength(method, propertyName, o);
                    Method tSetMethod = tClazz.getDeclaredMethod("set" + methodName.substring(3), method.getReturnType());
                    if (Validator.isEmpty(tSetMethod)) {
                        System.out.print(methodName);
                    }
                    tSetMethod.invoke(t, o);
                } else {
                    continue;
                }
            }
        } catch (NoSuchMethodException var1) {
            var1.printStackTrace();
        } catch (IllegalAccessException var2) {
            var2.printStackTrace();
        } catch (InvocationTargetException var3) {
            var3.printStackTrace();
        }
        return t;
    }

    private String getPropertyName(Class tClazz) {
        if (Validator.isEmpty(tClazz)) {
            return "";
        }
        Property property = (Property) tClazz.getAnnotation(Property.class);
        if (Validator.isEmpty(property)) {
            return "";
        }
        return property.name();
    }

    private String getPropertyName(Method tGetMethod) {
        if (Validator.isEmpty(tGetMethod)) {
            return "";
        }
        Property property = tGetMethod.getAnnotation(Property.class);
        if (Validator.isEmpty(property)) {
            return "";
        }
        return property.name();
    }

    private void validateFieldNotEmpty(Method mGetMethod, String propertyName, Object value) throws ParamValidateException {
        Required required = mGetMethod.getAnnotation(Required.class);
        if (!Validator.isEmpty(required)) {
            if (required.required()) {
                if (Validator.isEmpty(value)) {
                    throw new ParamValidateException(propertyName + "不能为空");
                }
            }
        }
    }

    private void validateFieldMaxLength(Method mGetMethod, String propertyName, Object value) throws ParamValidateException {
        MaxLength maxLength = mGetMethod.getAnnotation(MaxLength.class);
        if (!Validator.isEmpty(maxLength)) {
            int length = maxLength.length();
            String str = null;
            try {
                str = new String(value.toString().getBytes("gb2312"), "iso-8859-1");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            System.out.println(str.length());//7
            if (str.length() > length) {
                throw new ParamValidateException(propertyName + "长度大于允许的最大长度");
            }
        }
    }

    public static <B, S extends B> S copyBaseToSub(B bo, S so) {
        Class bc = bo.getClass();
        Class sc = so.getClass();
        if (Validator.isEmpty(bo) || Validator.isEmpty(so)) {
            return so;
        }
        Method[] methods = bc.getDeclaredMethods();
        for (Method method : methods) {
            String methodName = method.getName();
            if (methodName.startsWith("get")) {
                try {
                    Object o = method.invoke(bo);
                    Method setMethod = bc.getDeclaredMethod("set" + methodName.substring(3),method.getReturnType());
                    if (Validator.isEmpty(setMethod)) {
                        continue;
                    }
                    setMethod.invoke(so, o);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                } catch (NoSuchMethodException e) {
                    continue;
                }
            } else {
                continue;
            }
        }
        return so;
    }


}
