package com.spoons.sehaehae.product.controller;

import com.spoons.sehaehae.member.dto.MemberDTO;
import com.spoons.sehaehae.product.dto.CartDTO;
import com.spoons.sehaehae.product.dto.CategoryDTO;
import com.spoons.sehaehae.product.dto.ProductDTO;
import com.spoons.sehaehae.product.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.File;
import java.io.IOException;
import java.util.*;

@Controller
@RequestMapping("/product")
public class ProductController {

    @Value("${image.image-dir}")
    private String IMG_DIR;
    private final MessageSourceAccessor messageSourceAccessor;

    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService, MessageSourceAccessor messageSourceAccessor) {
        this.productService = productService;
        this.messageSourceAccessor = messageSourceAccessor;
    }

    @GetMapping("/list")
    public void productList(Model model,
                            @RequestParam (required = false)String searchValue,
                            @RequestParam (required = false)String searchCondition,
                            @RequestParam (defaultValue = "1") int page) {

        Map<String, String> searchMap = new HashMap<>();
        searchMap.put("searchValue",searchValue);
        searchMap.put("searchCondition",searchCondition);

        List<ProductDTO> productList = productService.selectProduct(searchMap);
        List<CategoryDTO> categoryList = productService.selectCategory();
        List<ProductDTO> selectAllProduct = productService.selectAllproduct();
        model.addAttribute("productList",productList);
        model.addAttribute("categoryList",categoryList);
        model.addAttribute("allProduct",selectAllProduct);
    }

    @GetMapping("/productRegist")
    public void registProduct() {

    }

    @GetMapping("/detail")
    public void productDetail(@RequestParam int code, Model model) {
       ProductDTO product=  productService.selectProductByCode(code);
        model.addAttribute("product",product);
    }
    @GetMapping("/cart")
    public void cart(Model model) {
        int i = 1;
        List<CartDTO> cartList = productService.cartList(i);
        model.addAttribute("cartList", cartList);
        model.addAttribute("price",3000);
    }
    @GetMapping("/payment")
    public void payemnt(Model model, @RequestParam int totalPrice) {
        int memberCode = 1;

       MemberDTO member = productService.selectMember(memberCode);
        model.addAttribute("member",member);
    }
    @GetMapping("/categoryRegist")
    public void categoryRegist() {
    }
    @PostMapping("/categoryRegist")
    public String regist(@RequestParam String categoryName, RedirectAttributes rttr) {
        productService.registCategory(categoryName);
        rttr.addFlashAttribute("message", messageSourceAccessor.getMessage("category.regist"));

        return "redirect:/product/list";
    }
    @PostMapping("/regist")
    public String productRegist(@ModelAttribute ProductDTO product, @RequestParam(value = "productImage", required = false) MultipartFile productImage) {
        product.setRegistDate(new Date());
        String originalName = productImage.getOriginalFilename();
        String fileUploadDir = IMG_DIR + "resource/images";
        File dir = new File(fileUploadDir);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        try {
            if (productImage.getSize() > 0) {
                productImage.transferTo(new File(fileUploadDir +"/"+ originalName));
                product.setPhoto("/resource/images/"+originalName);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        productService.registProduct(product);
        return "redirect:/product/list";
    }
    @GetMapping("/getPrice")
    public ResponseEntity<Integer> getPrice(@RequestParam(required = false) int price, @RequestParam(required = false) int body,int eco, int premium){
        int total = (price * body)+eco+premium;
        return ResponseEntity.ok(total);
    }

    @GetMapping("/addCart")
    public ResponseEntity<String> addCart(@ModelAttribute CartDTO cart){
        cart.setMember(1);
        productService.addCart(cart);
        return ResponseEntity.ok("장바구니에 추가됨");
    }
    @GetMapping("/totalPrice")
    public ResponseEntity<Integer> totalPrice(int total){

        return ResponseEntity.ok(total);
    }
    @GetMapping("/admin")
    public void admin(){}

    @GetMapping("/cartList")
    public void cartList(Model model){
        int a = 1;
        List<CartDTO> list = productService.cartList(a);
        int totalPrice = 0;

        for(int i=0 ; list.size()>i; i++){
            totalPrice += list.get(i).getProduct().getPrice();
        }

        model.addAttribute("cartList",list);
        model.addAttribute("totalPrice",totalPrice);
    }

    @GetMapping("/selectAllCategory")
    public ResponseEntity<List<CategoryDTO>> selectCategory(){
        List<CategoryDTO> categoryList = productService.selectCategory();
        return  ResponseEntity.ok(categoryList);
    }

    @GetMapping("/updateCart")
    public ResponseEntity<String> updateCart1(@RequestParam int amount, @RequestParam String productName){
        System.out.println("amount : " + amount);
        System.out.println("productName : " + productName);
//        productService.updateCartList(amount);
        return ResponseEntity.ok("ddd");
    }
    @PostMapping("/deleteCart")
    public @ResponseBody ResponseEntity<String> deleteCart(@RequestBody List<Integer> product){
        int userid = 1;

        Map<String, Object> cartMap = new HashMap<>();
        cartMap.put("memberId",userid);
        cartMap.put("productList",product);
        productService.deleteCart(cartMap);
        return ResponseEntity.ok("삭제완료");
    }





}
