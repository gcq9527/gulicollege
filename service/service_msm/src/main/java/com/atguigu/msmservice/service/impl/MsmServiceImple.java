package com.atguigu.msmservice.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.nacos.client.naming.utils.StringUtils;
import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import com.atguigu.msmservice.service.MsmService;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @author gcq
 * @Create 2020-09-16
 */
@Service
public class MsmServiceImple implements MsmService {

    @Override
    public boolean send(Map<String, Object> map, String phone) {
        if(StringUtils.isEmpty(phone)) return false;

        DefaultProfile profile =
                DefaultProfile.getProfile("default", "LTAI4G32asa8CRShTxk5xXLG", "ybd8TisahS8EWyoI48kVUmmu7ZziNU");

        IAcsClient client = new DefaultAcsClient(profile);

        //
        //设置相关固定的参数
        CommonRequest request = new CommonRequest();
        //request.setProtocol(ProtocolType.HTTPS);
        request.setMethod(MethodType.POST);
        request.setDomain("dysmsapi.aliyuncs.com");
        request.setVersion("2017-05-25");
        request.setAction("SendSms");

        //设置发送相关的参数
        request.putQueryParameter("PhoneNumbers",phone); //手机号
        request.putQueryParameter("SignName","谷粒学院在线教育网站"); //申请阿里云 签名名称
        request.putQueryParameter("TemplateCode","SMS_202820594"); //申请阿里云 模板code
        request.putQueryParameter("TemplateParam", JSONObject.toJSONString(map)); //验证码数据，转换json数据传递


        // 最终发送
        try {
            CommonResponse commonResponse = client.getCommonResponse(request);
            boolean success = commonResponse.getHttpResponse().isSuccess();
            return success;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}