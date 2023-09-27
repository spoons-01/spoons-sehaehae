package com.spoons.sehaehae.product.controller;

import com.spoons.sehaehae.product.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/product")
public class ProductController {

    private final ProductService productService;
    @Autowired
    public ProductController(ProductService productService){
        this.productService = productService;
    }
    @GetMapping("/list")
    public void productList(){
    }

    @GetMapping("/productRegist")
    public void registProduct(){

    }

    @PostMapping("/productRegist")
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

    @GetMapping("/categoryRegist")
    public void categoryRegist(){}
    @PostMapping("/categoryRegist")
    public String regist(@RequestParam String categoryName){
        System.out.println(categoryName);
        productService.registCategory(categoryName);
        return "redirect:/product/list";
    }
}
