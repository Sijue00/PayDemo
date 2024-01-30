package com.itheima.paydemo.controller;

import com.alipay.easysdk.factory.Factory;
import com.alipay.easysdk.kernel.Config;
import com.alipay.easysdk.kernel.util.ResponseChecker;
import com.alipay.easysdk.payment.common.models.AlipayTradeQueryResponse;
import com.alipay.easysdk.payment.common.models.AlipayTradeRefundResponse;
import com.alipay.easysdk.payment.facetoface.models.AlipayTradePrecreateResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("easy")
public class AliEasyPayController {

    @Autowired
    Config config;


    /**
     * 发起订单请求
     * @param orderNo
     * @return
     * @throws Exception
     */
    @GetMapping("/alipaytest")
    public AlipayTradePrecreateResponse alipaytest(String orderNo) throws Exception {
        // 1. 设置参数（全局只需设置一次） -- 项目启动时初始化
        Factory.setOptions(config);

        AlipayTradePrecreateResponse response = Factory.Payment.FaceToFace()
                .asyncNotify("http://459ec41.r20.cpolar.top/easy/notify")
                .preCreate("苹果15promax", orderNo, "0.01");
        return response;
    }

    /**
     * 查询订单状态
     * @param orderNo
     * @return
     */
    @GetMapping("query")
    public AlipayTradeQueryResponse query(String orderNo) {
        // 1. 设置参数（全局只需设置一次） -- 项目启动时初始化
        Factory.setOptions(config);

        try {
            return Factory.Payment.Common().query(orderNo);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 退款
     * @param orderNo
     * @param refundMoney
     * @return
     */
    @GetMapping("refund")
    public AlipayTradeRefundResponse refund(String orderNo, String refundMoney) {
        // 1. 设置参数（全局只需设置一次） -- 项目启动时初始化
        Factory.setOptions(config);
        try {
            AlipayTradeRefundResponse response = Factory.Payment.Common().refund(orderNo, refundMoney);
            return response;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 支付成功后异步通知接口
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping("notify")
    public String notify(HttpServletRequest request) throws  Exception{
        //1.接受参数 ，将其转为map
        Map<String, String[]> parameterMap = request.getParameterMap();
        HashMap<String, String> resultMap = new HashMap<>();
        //将其字符串集合转换为单个的字符
        parameterMap.forEach((k,v)->{
            resultMap.put(k, Arrays.stream(v).collect(Collectors.joining()));
        });
        //2. 验证签名
        Factory.setOptions(config);
        Boolean flag = Factory.Payment.Common().verifyNotify(resultMap);
        if (!flag){
            throw new RuntimeException("验签失败");
        }
        System.out.println(resultMap);
        return "success";
    }

}