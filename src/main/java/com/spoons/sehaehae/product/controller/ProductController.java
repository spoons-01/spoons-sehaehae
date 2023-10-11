package com.spoons.sehaehae.product.controller;

import com.spoons.sehaehae.common.paging.SelectCriteria;
import com.spoons.sehaehae.member.dto.MemberDTO;
import com.spoons.sehaehae.product.dto.*;
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
import java.text.SimpleDateFormat;
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
                            @RequestParam(required = false) String searchValue,
                            @RequestParam(required = false) String searchCondition
                            ) {
        SelectCriteria selectCriteria = new SelectCriteria();

        Map<String, String> searchMap = new HashMap<>();
        searchMap.put("searchValue", searchValue);
        searchMap.put("searchCondition", searchCondition);
        List<ProductDTO> productList = productService.selectProduct(searchMap);
        List<ProductDTO> allProduct = productService.selectAllproduct(null);
        List<CategoryDTO> categoryList = productService.selectCategory();
        model.addAttribute("allProduct",allProduct);
        model.addAttribute("productList", productList);
        model.addAttribute("categoryList", categoryList);
    }

    @GetMapping("/productRegist")
    public void registProduct() {

    }

    @GetMapping("/detail")
    public void productDetail(@RequestParam int code, Model model) {
        ProductDTO product = productService.selectProductByCode(code);

        model.addAttribute("product", product);
    }

    @GetMapping("/cart")
    public void cart(Model model) {
        int i = 1;
        List<CartDTO> cartList = productService.cartList(i);
        model.addAttribute("cartList", cartList);
        model.addAttribute("price", 3000);
    }

    @GetMapping("/payment")
    public void payemnt(Model model, @RequestParam int totalPrice) {
        int memberCode = 1;
        System.out.println(totalPrice);

        MemberDTO member = productService.selectMember(memberCode);
        List<CartDTO> cart = productService.cartList(memberCode);

        model.addAttribute("totalPrice", totalPrice-3000);
        model.addAttribute("member", member);
        model.addAttribute("cartList", cart);
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
    public String productRegist(RedirectAttributes rttr, @ModelAttribute ProductDTO product, @RequestParam(value = "productImage", required = false) MultipartFile productImage) {
        product.setRegistDate(new Date());
        String originalName = productImage.getOriginalFilename();
        String fileUploadDir = IMG_DIR + "resource/images";
        File dir = new File(fileUploadDir);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        try {
            if (productImage.getSize() > 0) {
                productImage.transferTo(new File(fileUploadDir + "/" + originalName));
                product.setPhoto("/resource/images/" + originalName);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        productService.registProduct(product);
        rttr.addFlashAttribute("message",messageSourceAccessor.getMessage("product.regist"));
        return "redirect:/product/listAdmin";
    }

    @GetMapping("/getPrice")
    public ResponseEntity<Integer> getPrice(@RequestParam(required = false) int price, @RequestParam(required = false) int body, int eco, int premium) {

        int total = (price * body) + eco + premium;
        return ResponseEntity.ok(total);
    }

    @GetMapping("/addCart")
    public ResponseEntity<String> addCart(@ModelAttribute CartDTO cart) {
        cart.setMember(1);
        productService.addCart(cart);
        return ResponseEntity.ok("장바구니에 추가됨");
    }

    @GetMapping("/totalPrice")
    public ResponseEntity<Integer> totalPrice(int total) {

        return ResponseEntity.ok(total);
    }

    @GetMapping("/admin")
    public void admin() {
    }

    @GetMapping("/cartList")
    public void cartList(Model model) {
        int a = 1;
        List<CartDTO> list = productService.cartList(a);
        int totalPrice = 0;

        System.out.println(list);


        for (int i = 0; list.size() > i; i++) {
            totalPrice += list.get(i).getProduct().getPrice() * list.get(i).getAmount();
            if(list.get(i).getUseEco().equals("Y")){
                totalPrice += 3000;
            }
            if(list.get(i).getUsePremium().equals("Y")){
                totalPrice += list.get(i).getProduct().getPremiumPrice();
            }
        }

        model.addAttribute("cartList", list);
        model.addAttribute("totalPrice", totalPrice);
        model.addAttribute("ecoPrice",3000);
    }

    @GetMapping("/selectAllCategory")
    public ResponseEntity<List<CategoryDTO>> selectCategory() {
        List<CategoryDTO> categoryList = productService.selectCategory();
        return ResponseEntity.ok(categoryList);
    }

    @GetMapping("/updateCart")
    public ResponseEntity<String> updateCart1(@RequestParam int amount, @RequestParam String productCode) {
        int memberid = 1;
        System.out.println("amount : " + amount);
        System.out.println("productCode : " + productCode);
        Map<String, Object> updateCartMap = new HashMap<>();
        updateCartMap.put("amount", amount);
        updateCartMap.put("productCode", productCode);
        updateCartMap.put("memberId", memberid);
        productService.updateCartList(updateCartMap);
        return ResponseEntity.ok("ddd");
    }

    @PostMapping("/deleteCart")
    public @ResponseBody ResponseEntity<String> deleteCart(@RequestBody List<Integer> product) {
        int userid = 1;

        Map<String, Object> cartMap = new HashMap<>();
        cartMap.put("memberId", userid);
        cartMap.put("productList", product);
        productService.deleteCart(cartMap);

        return ResponseEntity.ok("삭제완료");
    }

    @GetMapping("/finalPrice")
    public ResponseEntity<Long> abc(@RequestParam(defaultValue = "0") int point, @RequestParam(required = false) int totalPrice, @RequestParam(defaultValue = "10") int rate) {
        float per = (float) rate / 100;
        Long discount = (long) (totalPrice * per);
        System.out.println("discount : " + discount); //할인금액
        Long finalPrice = totalPrice-point-discount;
        System.out.println(finalPrice);
        return ResponseEntity.ok(finalPrice);
    }

    @PostMapping("/complete")
    public String complete(@ModelAttribute OrderDTO order, MultipartFile photo, Model model, OrderProductDTO orderProductDTO) {
        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
        int memberId = 1;
        int discount = (order.getPrice() + 3000) - order.getTotalPrice();
        String uuid = UUID.randomUUID().toString().substring(0,4);
        String code = simpleDateFormat.format(date) + uuid;
        order.setDiscount(discount);
        order.setCode(code);
        order.setDate(new Date());
        order.setMember(memberId);
        String originalName = photo.getOriginalFilename();
        String fileUploadDir = IMG_DIR + "resource/images";
        File dir = new File(fileUploadDir);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        try {
            if (photo.getSize() > 0) {
                photo.transferTo(new File(fileUploadDir + "/" + originalName));
                order.setImage("/resource/images/" + originalName);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        System.out.println(order);
        productService.addOrder(order);
        model.addAttribute(code);

        System.out.println(orderProductDTO);
        return "redirect:/product/ordercomplete?code="+code;
    }
    @GetMapping("/ordercomplete")
    public void orderComplete(RedirectAttributes rttr, String code){

        rttr.addAttribute("code", code);
    }


    @GetMapping("/listAdmin")
    public void listAdmin(Model model, String searchValue){
        Map<String,Object> searchMap = new HashMap<>();
        searchMap.put("searchValue",searchValue);
        List<ProductDTO> productList = productService.selectAllproduct(searchMap);
        model.addAttribute("productList",productList);
        model.addAttribute("searchValue",searchValue);
    }

    @GetMapping("/productModify")
    public void modify(int code,Model model){
        ProductDTO product = productService.selectProductByCode(code);
        model.addAttribute("product",product);
    }
    @PostMapping("/modify")
    public String productModify(ProductDTO product, MultipartFile productImage,RedirectAttributes rttr){
        product.setModifyDate(new Date());
        String imageName = productImage.getOriginalFilename();
        String fileUploadDir = IMG_DIR + "resource/images";
        File dir = new File(fileUploadDir);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        try {
            if (productImage.getSize() > 0) {
                productImage.transferTo(new File(fileUploadDir + "/" + imageName));
                product.setPhoto("/resource/images/" + imageName);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        System.out.println(product);
        productService.modifyProduct(product);

        rttr.addFlashAttribute("message",messageSourceAccessor.getMessage("product.regist"));
        return "redirect:/product/listAdmin";
    }

    @PostMapping("/delete")
    public ResponseEntity<String> delete(@RequestBody List<Integer> productCode){
        System.out.println(productCode);

        Map<String,List<Integer>> productMap = new HashMap<>();
        productMap.put("productMap",productCode);
        productService.deleteProduct(productMap);

        return ResponseEntity.ok("삭제가 완료되었습니다.");
    }

    @GetMapping("/coupon")
    public void coupon(Model model){
        int memberId =1;
        List<CouponDTO> couponList = productService.selectCoupon(memberId);
        model.addAttribute("couponList",couponList);
    }
}