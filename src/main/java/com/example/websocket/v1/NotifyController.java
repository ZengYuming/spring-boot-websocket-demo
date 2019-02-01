package com.example.websocket.v1;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController
@RequestMapping("notify")
public class NotifyController {
    @Autowired
    CounterHandler counterHandler;
    @GetMapping("notice")
    public String notice(String count) {
        counterHandler.sendMessageToUser(count, "当前时间是：" + new Date());
        return "已发送";
    }

}
