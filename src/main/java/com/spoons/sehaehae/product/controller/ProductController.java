package com.spoons.sehaehae.product.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/product")
public class ProductController {

    @GetMapping("/list")
    public void productList(){
    }

    @GetMapping("/regist")
    public void registProduct(){

    }

    @PostMapping("/regist")
    public String registProduct2(){
        return "/product/list";
    }
    @GetMapping("/detail")
    public void productDetail(){
    }
    @GetMapping("/cart")
    public void cart(){}
    @GetMapping("/payment")
    public void payemnt(){}

}
