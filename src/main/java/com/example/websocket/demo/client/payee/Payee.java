package com.example.websocket.demo.client.payee;

import com.example.websocket.demo.Consts;
import org.springframework.web.client.RestTemplate;

/**
 * 收款方（即，商家）
 */
public class Payee {
    static RestTemplate restTemplate = new RestTemplate();
    //付款方的MemberCode
    private static String payerMemberCode = "PAYER_MEMBERCODE_17111404060548491087";
    //付款方1的交易令牌
    private static String payerTransToken = "17111404060548491087";
    //付款方2的交易令牌
    private static final String payer2TransToken = "18020304094148935915";

    public static void main(String[] args) {
        //模拟商家申请收款
        System.out.println(restTemplate.getForObject("http://"+Consts.HOST + ":" + Consts.PORT + "/collect/apply?" + Consts.USER_IDENTIFY_PARAM_NAME + "=" + payerTransToken + "&memberCode=" + payerMemberCode + "&amount=80.9", String.class));
    }
}
