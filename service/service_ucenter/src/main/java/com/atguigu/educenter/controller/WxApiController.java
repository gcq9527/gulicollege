package com.atguigu.educenter.controller;

import com.atguigu.commonutils.JwtUtils;
import com.atguigu.educenter.entity.UcenterMember;
import com.atguigu.educenter.service.UcenterMemberService;
import com.atguigu.educenter.utils.ConstantWxUtils;
import com.atguigu.educenter.utils.HttpClientUtils;
import com.atguigu.servicebase.exception.GuliException;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URLEncoder;
import java.util.HashMap;

/**
 * @author gcq
 * @Create 2020-09-21
 */
@Controller
@RequestMapping("/api/ucenter/wx")
public class WxApiController {


    @Autowired
    private UcenterMemberService ucenterMemberService;

    /**
     * 回调接口
     * @param code 填写第一步获取的code参数
     * @param state
     * @return
     */
    @GetMapping("/callback")
    public String callback(String code, String state) {
        try {
            // 拿着code请求 微信固定地址，得到两个值，access_token 和 openid
            String baseAccessTokenUrl = "https://api.weixin.qq.com/sns/oauth2/access_token" +
                    "?appid=%s" +
                    "&secret=%s" +
                    "&code=%s" +
                    "&grant_type=authorization_code";

            // 拼接三个参数 id 密钥 和 code值
            String accessTokenUrl = String.format(
                    baseAccessTokenUrl,
                    ConstantWxUtils.WX_OPEN_APP_ID,
                    ConstantWxUtils.WX_OPEN_APP_SECRET,
                    code
            );
            // 请求这个拼接好的地址 得到返回两个值 access_token 和 openid
            // 使用httpclient发送请求 返回结果
            String accessTokenInfo = HttpClientUtils.get(accessTokenUrl);

            // 从 accessTokeInfo字符串中取出来两个值，access_Token 和 open_id
            Gson gson = new Gson();
            HashMap mapAccessToken = gson.fromJson(accessTokenInfo, HashMap.class);

            String access_token = (String) mapAccessToken.get("access_token");
            String openid = (String)mapAccessToken.get("openid");


            // 把扫描人信息添加到数据库里面
            // 判断数据表里面是否存在相同的微信信息 根据openid
            UcenterMember member = ucenterMemberService.getOpenIdMember(openid);
            if (member == null) {

                // 3拿着access_token 请求微信接口 获取用户信息
                String baseUserInfoUrl = "https://api.weixin.qq.com/sns/userinfo" +
                        "?access_token=%s" +
                        "&openid=%s";
                String userInfoUrl = String.format(
                        baseUserInfoUrl,
                        access_token,
                        openid);

                // 发送请求
                String userInfo = HttpClientUtils.get(userInfoUrl);

                // 获取返回userInfo字符串的扫描人信息
                HashMap userInfoMap = gson.fromJson(userInfo, HashMap.class);
                // 昵称
                String nickname = (String) userInfoMap.get("nickname");
                //头像
                String headimgUrl = (String) userInfoMap.get("headimgurl");

                // 保存至数据库
                member = new UcenterMember();
                member.setOpenid(openid);
                member.setNickname(nickname);
                member.setAvatar(headimgUrl);
                ucenterMemberService.save(member);
            }

            // 登录成功 根据id和用户名生成token
            String jwtToken = JwtUtils.getJwtToken(member.getId(), member.getNickname());
            // 回到首页面，通过路径带上token
            return "redirect:http://localhost:3000?token=" + jwtToken;
        } catch (Exception e) {
            e.printStackTrace();
            throw new GuliException(20001,"登录失败");
        }

    }

    /**
     * 生成微信扫描二维码
     * @return
     */
    @GetMapping("/login")
    public String getWxCode() {
        // 微信开放平台授权baseUrl  %s相当于?代表占位符
        String baseUrl = "https://open.weixin.qq.com/connect/qrconnect" +
                "?appid=%s" +
                "&redirect_uri=%s" +
                "&response_type=code" +
                "&scope=snsapi_login" +
                "&state=%s" +
                "#wechat_redirect";

        // 对redirect_url进行URLEncodeer编码
        String redirect_url = ConstantWxUtils.WX_OPEN_REDIRECT_URL;
        try {
            redirect_url = URLEncoder.encode(redirect_url,"utf-8");
        } catch (Exception e) {
            e.printStackTrace();
        }
        // 拼接值
        String url = String.format(
                baseUrl,
                ConstantWxUtils.WX_OPEN_APP_ID,
                redirect_url,
                "atguigu");

        // 重定向到请求微信地址里面
        return "redirect:" +url;
    }
}