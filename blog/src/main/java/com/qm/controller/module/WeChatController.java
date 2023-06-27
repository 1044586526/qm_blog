package com.qm.controller.module;

import com.qm.common.Constants;
import com.qm.common.RedisConstants;
import com.qm.service.RedisService;
import com.qm.util.RandomUtils;
import com.qm.util.WeChatUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.MessageFormat;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.TimeUnit;


/**
 *
 * @Author: qin
 * @Date: 2023/6/7 16:48
 */

@Slf4j
@Api(tags = "微信接口相关控制器")
@RestController
@RequiredArgsConstructor
public class WeChatController {

    private final RedisService redisService;

    @ApiOperation("微信公众号服务器配置校验token")
    @GetMapping(value = "/wechat")
    public void checkToken(HttpServletRequest request, HttpServletResponse response) {
        log.info("开始校验token");
        Optional.ofNullable(request.getParameter("signature"))
                .filter(StringUtils::isNotBlank)
                .ifPresent(signature -> {
                    String timestamp = request.getParameter("timestamp");
                    String nonce = request.getParameter("nonce");
                    String echostr = request.getParameter("echostr");
                    log.info("signature[{}], timestamp[{}], nonce[{}], echostr[{}]", signature, timestamp, nonce, echostr);
                    if (WeChatUtils.checkSignature(signature, timestamp, nonce)) {
                        log.info("数据源为微信后台，将echostr[{}]返回！", echostr);
                        ServletOutputStream out = null;
                        try {
                            out = response.getOutputStream();
                            out.write(echostr.getBytes());
                            out.flush();
                        } catch (IOException e) {
                            log.error("校验出错", e);
                        } finally {
                            if (out != null) {
                                try {
                                    out.close();
                                } catch (IOException e) {
                                    log.error("关闭输出流出错", e);
                                }
                            }
                        }
                    }
                });
    }


    @ApiOperation("处理微信服务器的消息转发")
    @PostMapping(value = "wechat")
    public String  wechat(HttpServletRequest request) throws Exception {
        // 调用parseXml方法解析请求消息
        Map<String,String> requestMap = WeChatUtils.parseXml(request);
        // 消息类型
        String msgType = requestMap.get("MsgType");
        // xml格式的消息数据
        String respXml = null;
        String mes = requestMap.get("Content");
        // 文本消息
        if (Constants.TEXT.equals(msgType) && Constants.CODE.equals(mes)) {
            String code = RandomUtils.generationNumber(6);
            String msg = MessageFormat.format("您的本次验证码:{0},该验证码30分钟内有效。", code);
            respXml= WeChatUtils.sendTextMsg(requestMap,msg);
            redisService.setCacheObject(RedisConstants.WECHAT_CODE+code,code,30, TimeUnit.MINUTES);
        }
        return respXml;
    }
}
