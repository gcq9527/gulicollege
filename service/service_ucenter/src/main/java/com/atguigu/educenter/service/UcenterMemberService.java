package com.atguigu.educenter.service;

import com.atguigu.educenter.entity.UcenterMember;
import com.atguigu.educenter.entity.vo.RegisterVo;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 会员表 服务类
 * </p>
 *
 * @author testjava
 * @since 2020-09-16
 */
public interface UcenterMemberService extends IService<UcenterMember> {

    /**
     * 登录
     * @param member
     * @return
     */
    String login(UcenterMember member);

    /**
     * 注册
     * @param registerVo
     */
    void register(RegisterVo registerVo);
}
