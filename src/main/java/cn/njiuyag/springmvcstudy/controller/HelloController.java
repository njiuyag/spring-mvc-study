package cn.njiuyag.springmvcstudy.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author yangjingliu
 * @date 2020/5/1
 */
@Controller
public class HelloController {

    @RequestMapping("/")
    public String home(){
        return "home";
    }
    @RequestMapping("/hello")
    public String hello(){
     return "home";
    }
}
