package com.spoons.sehaehae.product.controller;

import com.spoons.sehaehae.admin.dto.CouponDTO;
import com.spoons.sehaehae.admin.dto.CpBoxDTO;
import com.spoons.sehaehae.admin.dto.OrderDTO;
import com.spoons.sehaehae.member.dto.MemberDTO;
import com.spoons.sehaehae.member.dto.MemberLevelDTO;
import com.spoons.sehaehae.product.dto.*;
import com.spoons.sehaehae.product.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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
                            @RequestParam(required = false) String searchCondition,
                            @RequestParam(defaultValue = "1" ) int page,
                            @AuthenticationPrincipal MemberDTO member
                            ) {
        Map<String, String> searchMap = new HashMap<>();
        searchMap.put("searchValue", searchValue);
        searchMap.put("searchCondition", searchCondition);
        System.out.println("point : " + member);

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
    public void cart(Model model, @AuthenticationPrincipal MemberDTO member) {
        List<CartDTO> cartList = productService.cartList(member.getMemberNo());
        model.addAttribute("cartList", cartList);
        model.addAttribute("price", 3000);
    }

    @GetMapping("/payment")
    public void payemnt(Model model, @RequestParam int totalPrice, @AuthenticationPrincipal MemberDTO member) {
        int memberCode = member.getMemberNo();

        MemberDTO member1 = productService.selectMember(memberCode);
        List<CartDTO> cart = productService.cartList(memberCode);
        PointDTO point = productService.selectPoint(memberCode);
        model.addAttribute("totalPrice", totalPrice);
        model.addAttribute("member", member1);
        model.addAttribute("cartList", cart);
        model.addAttribute("point",point);
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
        System.out.println(new Date());
        product.setRegistDate(new Date());
        String originalName = productImage.getOriginalFilename();
        String fileUploadDir = IMG_DIR + "/resource/productImage";
        File dir = new File(fileUploadDir);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        try {
            if (productImage.getSize() > 0) {
                productImage.transferTo(new File(fileUploadDir + "/" + originalName));
                product.setPhoto("/upload/resource/productImage/" + originalName);
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
    public ResponseEntity<String> addCart(@ModelAttribute CartDTO cart, @AuthenticationPrincipal MemberDTO member) {
        cart.setMember(member.getMemberNo());
        String message = "장바구니에 추가 됐습니다.";

        productService.addCart(cart);


        return ResponseEntity.ok(message);
    }

    @GetMapping("/totalPrice")
    public ResponseEntity<Integer> totalPrice(int total) {

        return ResponseEntity.ok(total);
    }

    @GetMapping("/admin")
    public void admin() {
    }

    @GetMapping("/cartList")
    public void cartList(Model model, @AuthenticationPrincipal MemberDTO member) {
        List<CartDTO> list = productService.cartList(member.getMemberNo());
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
    public ResponseEntity<String> updateCart1(@RequestParam int amount, @RequestParam String productCode, @AuthenticationPrincipal MemberDTO member) {
        System.out.println("amount : " + amount);
        System.out.println("productCode : " + productCode);
        Map<String, Object> updateCartMap = new HashMap<>();
        updateCartMap.put("amount", amount);
        updateCartMap.put("productCode", productCode);
        updateCartMap.put("memberId", member.getMemberNo());
        productService.updateCartList(updateCartMap);
        return ResponseEntity.ok("ddd");
    }
    @PostMapping("/deleteCart")
    public @ResponseBody ResponseEntity<String> deleteCart(@RequestBody List<Integer> product, @AuthenticationPrincipal MemberDTO member) {

        Map<String, Object> cartMap = new HashMap<>();
        cartMap.put("memberId", member.getMemberNo());
        cartMap.put("productList", product);
        productService.deleteCart(cartMap);

        return ResponseEntity.ok("삭제완료");
    }
    @GetMapping("/finalPrice")
    public ResponseEntity<Long> abc(@RequestParam(defaultValue = "0") int point, @RequestParam(required = false) int totalPrice, @RequestParam(defaultValue = "10") int rate) {
        System.out.println(totalPrice);
        System.out.println(rate);

        float per = (float) rate / 100;
        Long discount = (long) (totalPrice * per);
        System.out.println("discount : " + discount); //할인금액
        Long finalPrice = totalPrice-point-discount;
        System.out.println(finalPrice);
        return ResponseEntity.ok(finalPrice);
    }
    @PostMapping("/complete")
    public String complete(@ModelAttribute OrderDTO order, @AuthenticationPrincipal MemberDTO member,MultipartFile photo1,
                           @RequestParam(value = "productCode") List<Integer> productCode, @RequestParam(value = "amount") List<Integer> amount,
                           @RequestParam(value = "usePremium") List<Character> usePremium, @RequestParam(value = "useEco") List<Character> useEco,
                           @ModelAttribute PointDTO point1) {
        System.out.println("photo : " + photo1);
        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
        int discount = (order.getOrderPrice()) - order.getOrderTotalPrice();
        String uuid = UUID.randomUUID().toString().substring(0,4);
        String code = simpleDateFormat.format(date) + uuid;
        List<OrderProductDTO> orderProducts = new ArrayList<>();
        for(int i = 0; i < productCode.size(); i++){
            OrderProductDTO orderProduct = new OrderProductDTO();
            orderProduct.setProductCode(productCode.get(i));
            orderProduct.setAmount(amount.get(i));
            orderProduct.setOrderCode(code);
            orderProduct.setUsePremium(usePremium.get(i));
            orderProduct.setUseEco(useEco.get(i));
            orderProducts.add(orderProduct);
        }
        int reward = 0;

        String level = productService.selectMemberLevel(member.getMemberNo());
        if(level.equals("1")){
            reward = (int) (order.getOrderTotalPrice() * 0.02);
        }else if(level.equals("2")){
            reward = (int) (order.getOrderTotalPrice() * 0.03);
        }else if(level.equals("3")){
            reward = (int) (order.getOrderTotalPrice() * 0.04);
        }else if(level.equals("5")){
            reward = (int) (order.getOrderTotalPrice() * 0.05);
        }
        System.out.println("reward : " + reward);
        System.out.println(orderProducts);
        order.setReward(reward);
        order.setOrderDiscount(discount);
        order.setCode(code);
        order.setOrderDate(new Date());
        order.setMember(member);
        String originalName = photo1.getOriginalFilename();
        String fileUploadDir = IMG_DIR + "/resource/location";
        File dir = new File(fileUploadDir);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        try {
            if (photo1.getSize() > 0) {
                photo1.transferTo(new File(fileUploadDir + "/" + originalName));
                order.setImage("/upload/resource/location/" + originalName);
            }else{
                order.setImage("null");
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        System.out.println(order);
        productService.addOrder(order, orderProducts);
        System.out.println("memberusePoint : " + order.getUsePoint());
        int point = point1.getPoint() - order.getUsePoint() + reward;
        System.out.println("totalPoint : " + point);
        Map<String,Object> map = new HashMap<>();
        map.put("usecouponCode",order.getUseCoupon());
        map.put("member", member);
        map.put("point", point);
        map.put("order",order);

        productService.updateInfo(map);


        return "redirect:/product/orderComplete?code="+code;
    }

    @GetMapping("/orderComplete")
    public void Ordercomplete(Model model, @RequestParam String code){
        model.addAttribute("code",code);
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
        System.out.println("modify Product : " + product);
        product.setModifyDate(new Date());
        String imageName = productImage.getOriginalFilename();
        String fileUploadDir = IMG_DIR + "/upload/resource/productImage";
        File dir = new File(fileUploadDir);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        try {
            if (productImage.getSize() > 0) {
                productImage.transferTo(new File(fileUploadDir + "/" + imageName));
                product.setPhoto("/upload/resource/productImage/" + imageName);
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
    public void coupon(Model model, @AuthenticationPrincipal MemberDTO member){

        List<CpBoxDTO> couponList = productService.selectCoupon(member.getMemberNo());

        model.addAttribute("couponList",couponList);
    }

    @GetMapping("/deletePremium")
    public ResponseEntity<String> deletePremium(int code, @AuthenticationPrincipal MemberDTO member){
        Map<String,Object> map = new HashMap<>();
        map.put("memberId",member.getMemberNo());
        map.put("code",code);
        productService.deletePremium(map);

        return ResponseEntity.ok("변경 완료");
    }
    @GetMapping("/deleteEco")
    public String deleteEco(int code, @AuthenticationPrincipal MemberDTO member){
        Map<String,Object> map = new HashMap<>();
        map.put("memberId",member.getMemberNo());
        map.put("code",code);
        productService.deleteEco(map);
        return "redirect:/product/cartList";
    }

    @GetMapping("/addOption")
    public void addoption(int code, String option,@AuthenticationPrincipal MemberDTO member){
        System.out.println(code);
        System.out.println(option);
        Map<String, Object> addoption = new HashMap<>();
        addoption.put("option",option);
        addoption.put("code",code);
        addoption.put("memberCode",member.getMemberNo());
        productService.addOption(addoption);
    }

    @GetMapping("/categoryList")
    public void categoryList(Model model){
        List<CategoryDTO> category =  productService.selectCategory();
        model.addAttribute("categoryList",category);

    }
    @PostMapping("/registCategory")
    public String registCategory(String name){

        productService.registCategory(name);
        return "redirect:/product/categoryList";
    }
    @PostMapping("/deleteCategory")
    public String deleteCategory(@RequestParam List<Integer> codeList){

        productService.deleteCategory(codeList);

        return "redirect:/product/categoryList";
    }

}