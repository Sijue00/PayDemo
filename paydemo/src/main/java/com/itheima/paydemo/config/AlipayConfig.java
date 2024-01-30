package com.itheima.paydemo.config;

import com.alipay.easysdk.kernel.Config;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "restkeeper.alipay")
@Data
public class AlipayConfig extends Config {
    // 商户appid
    public String APPID = "9021000134641277";
    // 私钥 pkcs8格式的
    public String RSA_PRIVATE_KEY = "MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQCtwsjEKHDTAI1C/j35GNrP6LrFKZzO8MWLqg/M1z6gSBD4B48pCXz0d0gUYt8SsP8CfigBDvBYGC/5zJ4uvAJLFuvqRkB/G62tOSV0FGj2DPsoDyVX6slKRyMrEEXB5tX0gqaLkmTQvO16748P3a5WdTmvscPtRw1l6yMhQB5Hdd7oARe53MXq0hyLUzDGq+XynCe5bPgRUmqI3YnB31jpUfEZcjqDr98l5+u5pACAK6W42o++Q4bh8wPhqZiiRuRZf3TVUJyt040g6zFBfoqbmKGmkHBAtqnJ8/XmdnevjCW9vfnZrfLevq7B5nTuLkrZZUIKEcJxjBUCMzNGVywrAgMBAAECggEAGpO4cYhd+WgUMzL/96G9lh9asd1zhpN9I6eXUxV5FrrvHYDKfNResGWuOLAGvq0j1BXHuKG4UUEh1WtepndLQoD3BJZKNybcoaoj+GVGyJujrrTiWVgoAZxv3J5YjRfpkcSKBL8XfHXpPOx3TU3y5HZ5pE3kUd6URgp5DRTpjLRRvItP6hGr7//eW34cAddNorhor9yNioc9VM67Cnfh+H49uFBjCDmC8xbUwnRYt5H/mANM+ncvk3XZAKg8xAOWtBOYPuUgk4aCJmqVvI6YZVUZBKQb+2Ku5GJb62h8+3v6VXVGbzuF9U0jFs4Qvlr21+sCPbXABjHl5YARuf9cwQKBgQDsQSu7ZzaA4R1USZCvcZZis+YdX2EbinUWRU610Gr+eAp1WPRwAO6yeOYNqSaxyAZmNeUuT4M6k3Q3pFMM4c4JBfKD6fUZrc5V6Yq9zr0gqqIrbXQIthiFfk+AkXK/ubN7epWHJJM+9WdkF7zj5mXug+MHRtVuKyToqFZfcUpnbwKBgQC8SISLYYYKJ3AtGZncQF09HXVkPbfAj1LPkEZNRcJ9kvwjhuylT4+mh/tVWTIKYT6alRFyptR4+5yDVthZLcTKYJFTVUHIPpAAVeb8J0EvaIxDTjpsDJfAANVH0/uJRrp7pJ+qXm1QzNzmc8lZJBZGMelZzKJWa/JobvVdR6PJBQKBgBvBEDUky+daCssDFWc27dbMkYTz1oGYw2aJX9TGFjusuHXOrHCX+3Kl90i1jWEnQXEydaj6GE2CUw3SVRtXT5AJzdKnYm7Q10OKew0PQ3KZ+REm+5Gmar0L+KT+8T8KvsYLwZMtvcqQo8PDciJHx8O8ZKhPLvWRRfW7COKPeZErAoGAXznmH4BxhI0xxP2fva+n+JL/nnVAjFlwrp3vkIFpvo0qjOVeprU4mCdKTH5SOsG4IgKm0m/iIlZPcHXp8iCd2SVCGv2Rya8qsaDAtGM+KrYbyqa0EZXE4k0Jkcx6+ZEWHBbE0ivz6dvTFZMWEMmZomL3/Hzs1fy+m2cTHdxvz+kCgYEAwHZGtPSGT/v6WuyCa4fY2ycJcCMuvGD4TCnaOx0u2B+dbIJjmw/kTtGssH/DbMkf4HjTW2yvg1KEZF0wgxEMH1dcHO4O+EIPTd02SQ9EQPTV5WOa0B9z89td2vKZG7VG4iDcqH96MGOgbcKDn8bhk8MnMLl8SlpaLV4xXAhRVlM=";
    // 服务器异步通知页面路径 需http://或者https://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
    //public String notify_url = "http://商户网关地址/alipay.trade.wap.pay-JAVA-UTF-8/notify_url.jsp";
    // 页面跳转同步通知页面路径 需http://或者https://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问 商户可以自定义同步跳转地址
    //public String return_url = "http://商户网关地址/alipay.trade.wap.pay-JAVA-UTF-8/return_url.jsp";
    // 请求网关地址
    public String URL = "https://openapi-sandbox.dl.alipaydev.com/gateway.do";
    // 编码
    public String CHARSET = "GBK";
    // 返回格式
    public String FORMAT = "json";
    // 支付宝公钥
    public String ALIPAY_PUBLIC_KEY = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEA6OrxUCiMBzuXB4dgWHGfl1D6D3IhnVKVdov/SjqdhUjIW8v2kbv2YXAM1Ay/mvafxp8CiIX2/emLbvWYf3F2CQVXdl2RiQTwCekubDSuiKBmJ7ALUxPWHCC1nU2mzTUGt0zLQjB+6D1UMri3E2impbr++XX7fe7Ts8i8K7Xe7nSko01X8Cdzg3qPcLS63rZ8M73v9FKUKgHo2zRfXfwMjJZsLr1iQCOlEKha9D+PVDE8AA/i+fOrgvHVwyW1tTyo0NSSLL4Fiz2GkdVHXZimFX/ufzJBfWQz8/gvp+hZxYcysmMGHat0pKFG22P2OduN0LWYN94X9YDiajBXgB8OnQIDAQAB";
    // 日志记录目录
    //public String log_path = "/log";
    // RSA2
    public String SIGNTYPE = "RSA2";
}