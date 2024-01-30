package com.itheima.paydemo.controller;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.extra.qrcode.QrCodeUtil;
import cn.hutool.extra.qrcode.QrConfig;
import cn.hutool.json.JSONUtil;
import com.alibaba.fastjson.JSONObject;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.AlipayTradeCreateRequest;
import com.alipay.api.request.AlipayTradePrecreateRequest;
import com.alipay.api.request.AlipayTradeQueryRequest;
import com.alipay.api.request.AlipayTradeRefundRequest;
import com.alipay.api.response.AlipayTradeCreateResponse;
import com.alipay.api.response.AlipayTradePrecreateResponse;
import com.alipay.api.response.AlipayTradeQueryResponse;
import com.alipay.api.response.AlipayTradeRefundResponse;
import com.itheima.paydemo.config.AlipayConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

/**
 * @author 思觉失调
 * @version 1.0
 * @date 2024-01-29 16:34
 */
@RestController
@RequestMapping("alipay")
public class AliPayController {

    @Autowired
    AlipayConfig alipayConfig;

    @Resource
    private AlipayClient alipayClient;

    /**
     * 当面付 统一创建交易生成二维码接口
     *
     * @return
     * @throws IOException
     */
    @RequestMapping("/precreate")
    public String precreate(String orderNo) {
        //根据下面写好的方法创建阿里支付客户端
        //AlipayClient alipayClient = this.getClient();
        //获得初始化的AlipayClient
        AlipayTradePrecreateRequest request = new AlipayTradePrecreateRequest();
        request.setReturnUrl("url");
        Map<Object, Object> build = MapUtil.builder().put("out_trade_no", orderNo).put("total_amount", "10000").put("subject", "iphone 15 ProMax").build();
        request.setBizContent(JSONUtil.toJsonStr(build));
        AlipayTradePrecreateResponse response = null;
        try {
            response = alipayClient.execute(request);
            if (response.isSuccess()) {
                System.out.println("调用成功");
                String code = response.getQrCode();
                //借助二维码生成工具根据码串值生成对应的二维码
                //String base64 = QrCodeUtil.generateAsBase64(code, new QrConfig(300, 300), "png");
                System.out.println(code);
                //System.out.println(base64);
                return code;
            } else {
                System.out.println("调用失败");
            }
        } catch (AlipayApiException e) {
            e.printStackTrace();
            throw new RuntimeException("调用失败");
        }
        return alipayClient.getClass().getName();
    }

    /**
     * alipay.trade.create(统一收单交易创建接口)
     * @param orderNo
     * @return
     */
    @RequestMapping("create")
    public String create(String orderNo) throws AlipayApiException {
        AlipayTradeCreateRequest request = new AlipayTradeCreateRequest();
        Map<Object, Object> build = MapUtil.builder()
                .put("out_trade_no", orderNo)
                .put("total_amount", "10000")
                .put("subject", "iphone 15 ProMax")
                .put("buyer_id","2088722029209225")
                .build();
        request.setBizContent(JSONUtil.toJsonStr(build));

        AlipayTradeCreateResponse response = alipayClient.execute(request);
        String json = JSONUtil.toJsonStr(response);
        System.out.println(json);
        return json;
    }

    /**
     * alipay.trade.query(统一收单交易查询)
     *
     * @param orderNo
     * @return
     */
    @RequestMapping("query")
    public String query(String orderNo) throws AlipayApiException {
        //创建支付宝统一收单交易查询  对象
        AlipayTradeQueryRequest request = new AlipayTradeQueryRequest();
        //设置好要查询的单号
        Map<Object, Object> build = MapUtil.builder().put("out_trade_no", orderNo).build();
        request.setBizContent(JSONUtil.toJsonStr(build));

        //这里因为没有使用安卓的支付宝沙盒 测试订单支付 所以显示的是WAIT_BUYER_PAY 等待买家支付状态
        AlipayTradeQueryResponse response = alipayClient.execute(request);
        String tradeStatus = response.getTradeStatus();
        if (ObjectUtil.equal(tradeStatus, "TRADE_SUCCESS")) {
            return "交易成功";
        } else {
            return "具体情况根据返回的状态值而定" + tradeStatus;
        }

    }

    /**
     * alipay.trade.refund(统一收单交易退款接口)
     * @param orderNo
     * @return
     */
    @RequestMapping("refund")
    public String refund(String orderNo) throws AlipayApiException {
        AlipayTradeRefundRequest request = new AlipayTradeRefundRequest();
        //设置好要查询的单号
        Map<Object, Object> build = MapUtil.builder().put("out_trade_no", orderNo).put("refund_amount","10000").build();
        request.setBizContent(JSONUtil.toJsonStr(build));
        //同样是由于上述的原因  是等待买家支付状态 所以不支持  当前交易状态不支持此操作  但是步骤是没错的
        AlipayTradeRefundResponse response = alipayClient.execute(request);
        String json = JSONUtil.toJsonStr(response);
        System.out.println(json);
        return json;
    }




}
