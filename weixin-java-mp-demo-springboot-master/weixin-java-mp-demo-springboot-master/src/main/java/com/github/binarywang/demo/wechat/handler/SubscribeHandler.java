package com.github.binarywang.demo.wechat.handler;

import com.common.system.entity.WxDetailEntity;
import com.common.system.entity.WxUserEntity;
import com.common.system.service.WxDetailService;
import com.common.system.service.WxUserService;
import com.github.binarywang.demo.wechat.builder.TextBuilder;

import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.common.session.WxSessionManager;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.message.WxMpXmlMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutMessage;
import me.chanjar.weixin.mp.bean.result.WxMpUser;

import org.springframework.stereotype.Component;

import java.util.Map;

import javax.annotation.Resource;

/**
 * @author Binary Wang(https://github.com/binarywang)
 */
@Component
public class SubscribeHandler extends AbstractHandler {

	@Resource
	private WxUserService wxUserService;
	
	@Resource
	private WxDetailService wxDetailService;
	
    @Override
    public WxMpXmlOutMessage handle(WxMpXmlMessage wxMessage,
                                    Map<String, Object> context, WxMpService weixinService,
                                    WxSessionManager sessionManager) throws WxErrorException {

        this.logger.info("新关注用户 OPENID: " + wxMessage.getFromUser());

        // 获取微信用户基本信息
        WxMpUser userWxInfo = weixinService.getUserService()
                .userInfo(wxMessage.getFromUser(), null);

        if (userWxInfo != null) {
            // TODO 可以添加关注用户到本地
        	try {
        		WxUserEntity wxUserEntity = wxUserService.getById(userWxInfo.getOpenId());
        		if(null == wxUserEntity){
        			wxUserEntity = new WxUserEntity();
        			wxUserEntity.setOpenId(userWxInfo.getOpenId());
        			wxUserService.save(wxUserEntity);
        		}
        		WxDetailEntity wxDetailEntity = wxDetailService.findByOpenId(userWxInfo.getOpenId());
        		if(null == wxDetailEntity){
        			wxDetailEntity = new WxDetailEntity();
        			wxDetailEntity.setOpenId(userWxInfo.getOpenId());
        			wxDetailEntity.setPic(userWxInfo.getHeadImgUrl());
        			wxDetailEntity.setSex(userWxInfo.getSex());
        			wxDetailService.save(wxDetailEntity);
        		}
			} catch (Exception e) {
				this.logger.error("保存用户信息报错！", e);
			}
        }

        WxMpXmlOutMessage responseResult = null;
        try {
            responseResult = handleSpecial(wxMessage);
        } catch (Exception e) {
            this.logger.error(e.getMessage(), e);
        }

        if (responseResult != null) {
            return responseResult;
        }

        try {
            return new TextBuilder().build("感谢关注", wxMessage, weixinService);
        } catch (Exception e) {
            this.logger.error(e.getMessage(), e);
        }

        return null;
    }

    /**
     * 处理特殊请求，比如如果是扫码进来的，可以做相应处理
     */
    private WxMpXmlOutMessage handleSpecial(WxMpXmlMessage wxMessage)
            throws Exception {
        //TODO
        return null;
    }

}
