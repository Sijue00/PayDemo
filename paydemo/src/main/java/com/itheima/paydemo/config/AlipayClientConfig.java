package com.itheima.paydemo.config;

import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author 思觉失调
 * @version 1.0
 * @date 2024-01-29 21:26
 */
@Component
public class AlipayClientConfig {

    @Resource
    private AlipayConfig alipayConfig;

    //自定义好阿里支付客户端的bean
    @Bean
    public AlipayClient aliPayclient(){
        AlipayClient client = new DefaultAlipayClient(alipayConfig.getURL(),
                alipayConfig.getAPPID(), alipayConfig.getRSA_PRIVATE_KEY(),
                alipayConfig.getFORMAT(), alipayConfig.getCHARSET(),
                alipayConfig.getALIPAY_PUBLIC_KEY(), alipayConfig.getSIGNTYPE());
        return client;
    }
}
