package com.itheima.paydemo.controller;

import com.alipay.api.internal.util.file.IOUtils;
import com.github.wxpay.sdk.WXPay;
import com.github.wxpay.sdk.WXPayUtil;
import com.itheima.paydemo.config.WXPayConfigCustom;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * @Description:
 * @Version: V1.0
 */
@Slf4j
@RestController
@RequestMapping("wxpay")
public class WxpayController {
    /**
     * 下单操作
     *
     * @param code
     * @return
     * @throws Exception
     */
    @GetMapping("unifiedOrder/{code}")
    public Map<String, String> unifiedOrder(@PathVariable String code) throws Exception {
        //配置好模板
        WXPay wxPay = new WXPay(new WXPayConfigCustom());

        //根据文档 构建所需参数  以下的参数都是必传的
        Map<String, String> paramMap = new HashMap<>();
        //商品描述
        paramMap.put("body", "学习测试");
        //商户订单号
        paramMap.put("out_trade_no", code);
        //订单总金额，单位为分
        paramMap.put("total_fee", "1");
        //终端IP
        paramMap.put("spbill_create_ip", "123.12.12.123");
        //通知地址  借助内网穿透工具
        paramMap.put("notify_url", "http://459ec41.r20.cpolar.top/wxpay/notify");
        //交易类型 JSAPI -JSAPI支付  NATIVE -Native支付  APP -APP支付
        paramMap.put("trade_type", "NATIVE");

        /*根据文档中的api地址 信息 选择调用具体的api
        https://api.mch.weixin.qq.com/pay/unifiedorder
        把请求地址封装到了方法上了*/
        Map<String, String> result = wxPay.unifiedOrder(paramMap);
        //get 什么都需要查看文档 没有提示
        String code_url = result.get("code_url");
        System.out.println(code_url);
        return result;

    }


    /**
     * 支付回调通知
     *
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("notify")
    public String payNotify(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Map<String, String> resultMap = new HashMap<>();
        try {
            //读取request请求体中的Xml参数
            String xmlResult = IOUtils.toString(request.getInputStream(), request.getCharacterEncoding());
            //因为微信返回的参数是xml格式的，借助wx工具类将其转化为map
            Map<String, String> map = WXPayUtil.xmlToMap(xmlResult);
            // 加入自己处理订单的业务逻辑，需要判断订单是否已经支付过，否则可能会重复调用
            WXPay wxPay = new WXPay(new WXPayConfigCustom());
            // 1. 验证签名
            boolean signatureValid = wxPay.isResponseSignatureValid(map);
            if (!signatureValid) {
                System.out.println("验证签名失败");
                resultMap.put("return_code", "FAIL");
                resultMap.put("return_msg", "验证签名失败");
                return WXPayUtil.mapToXml(resultMap);
            }
            //因为没有数据库，这里只是简单点下业务流程
            //2.根据交易单id 查询数据库中的交易单
            System.out.println("根据交易单id 查询数据库中的交易单" + map.get("out_trade_no"));
            //3.对比金额是否一致
            // 加分布式锁  防止订单在多并发的情况下被修改成了已取消，但还是走后续，将其修改为了已支付
            // 所以在操作这种重要数据一定要加锁
            //4.判断交易单的状态
            //5. 根据支付结果通知 修改交易单状态
            //解锁
            //6.返回微信支付结果 因为微信支付会一直向此发请求
            resultMap.put("return_code", "SUCCESS");
            resultMap.put("return_msg", "处理成功");
            return WXPayUtil.mapToXml(resultMap);


        } catch (Exception e) {
            log.error("微信回调结果异常,异常原因{}", e.getMessage());
            //return WxPayNotifyResponse.fail(e.getMessage());
            throw new RuntimeException();
        }
    }

    /**
     * 退款
     *
     * @param code      订单号
     * @param refund_no 退款号
     * @return
     * @throws Exception
     */
    @GetMapping("refunds/{code}/{refund_no}")
    public Map<String, String> refunds(@PathVariable String code, @PathVariable String refund_no) throws Exception {
        WXPayConfigCustom config = new WXPayConfigCustom();
        WXPay wxpay = new WXPay(config);
        Map<String, String> data = new HashMap<String, String>();
        data.put("out_trade_no", code);
        data.put("out_refund_no", refund_no);
        data.put("notify_url", "http://484cd438.cpolar.io/wxpay/notify");
        data.put("refund_desc", "菜品售罄");
        data.put("refund_fee", "1");
        data.put("total_fee", "1");
        data.put("refund_fee_type", "CNY");
        System.out.println("请求参数：" + data);
        Map<String, String> map = wxpay.refund(data);
        return map;
    }
}