package com.website.pristinewash.controller;

import com.website.pristinewash.DTO.ScheduleCallDTO;
import com.website.pristinewash.entity.ScheduleCall;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * Thymleaf Controller class will directly interact with the front end.
 */
@Controller
@Slf4j
public class ThymleafController implements ErrorController {

    @Autowired
    OTPController otpController;

    @Autowired
    ScheduleCallController scheduleCallController;

    @Autowired
    CategoryController categoryController;

    @Autowired
    CartController cartController;

    /**
     * Landing page of pristine wash website
     * @return index html page
     */
    @GetMapping
    public String pristineWashHome() {return "index";}

    /**
     * Landing page of pristine wash website
     * @return index html page
     */
    @GetMapping("/index.html")
    public String pristineWashHome2() {
        return "index";
    }

    /**
     * About page
     * @return about us html page
     */
    @GetMapping("/about")
    public String aboutPage(){
        return "aboutus";
    }

    /**
     * Contact Us page
     * @return contact us html page
     */
    @GetMapping("/contact")
    public String contactPage(){
        return "contactus";
    }

    /**
     * Replace default Authentication Page to custom login page
     * @return login html page
     */
    @RequestMapping("/login.html")
    public String login() {
        return "login.html";
    }

    /**
     * Show error html page when even app gets 404 error
     * @return error html page
     */
    @RequestMapping("/error")
    public String handleError() {
        return "error";
    }

    /**
     * User by Login page to send otp
     */
    @GetMapping("/sendotp")
    public String generateOTP(@RequestParam String username, Model model) {
        ResponseEntity<String> response = otpController.generateOTP(username);
        if(response.getStatusCode() == HttpStatus.OK) {
            return "login";
        } else {
            return "login";
        }
    }

    @GetMapping("/schedule-call")
    public String showScheduleCallForm(Model model) {
        model.addAttribute("scheduleCall", new ScheduleCall());
        return "schedule-call";
    }

    @PostMapping("/schedule-call")
    public String scheduleCall(ScheduleCallDTO scheduleCallDTO, HttpSession session, Model model) {
        // Here you can process and save the scheduleCall object
        // Example: userService.findByUsername(scheduleCall.getUser().getUsername()) to get the User object
        //scheduleCallService.saveScheduleCall(scheduleCall);
        ResponseEntity<String> response = scheduleCallController.ScheduleCallToUser(scheduleCallDTO);
        final String smsMessage = response.getBody();
        //session.getAttribute("SPRING_SECURITY_CONTEXT")
//        String username = (String) session.getAttribute("username");
//        log.info(username);
        log.info(scheduleCallDTO.getUsername() + " " + scheduleCallDTO.getDate()
                + " " + scheduleCallDTO.getTime() + " " + scheduleCallDTO.getMessage());

        if(response.getStatusCode() == HttpStatus.OK) {
            model.addAttribute("scheduleCall", new ScheduleCall());
            model.addAttribute("errorMessage", smsMessage);
            return "schedule-call";
        } else {
            model.addAttribute("scheduleCall", new ScheduleCall());
            model.addAttribute("errorMessage", smsMessage);
            return "schedule-call";
        }
    }

//    @GetMapping("/product/{category_id}")
//    public String getProductsByCategory(@PathVariable Integer category_id, Model model) {
//        List<ProductDTO> productDTOList = categoryController.getProductsByCategory(category_id);
//        model.addAttribute("products", productDTOList);
//        return "product";
//    }
//
//    @PostMapping("/cart/{product_id}")
//    public String addProductToCart(@PathVariable String product_id, Model model) {
//        model.addAttribute("products", null);
//        try {
//            cartController.addProductToCart(product_id);
//            model.addAttribute("successMessage", "Product Added Successfully");
//            return "product";
//        } catch (Exception e) {
//            model.addAttribute("successMessage", "Product Not Added");
//            return "product";
//        }
//    }

//    @GetMapping("product_by_category/{categoryId}")
//    public String productByCategory(@PathVariable Integer categoryId){
//        if(categoryId == 1) return "productDescWashAndFold";
//        if(categoryId == 2) return "productDescDryClean";
//        if(categoryId == 3) return "productDescSteamAndIron";
//        if(categoryId == 4) return "productDescStainRemoval";
//        return "productDescWashAndFold";
//    }

    @GetMapping("/wash-and-fold")
    public String washAndFold() {
        return "productDescWashAndFold";
    }

    @GetMapping("/dry-cleaning")
    public String dryCleaning() {
        return "productDescDryClean";
    }

    @GetMapping("/steam-and-iron")
    public String steamAndIron() {
        return "productDescSteamAndIron";
    }

    @GetMapping("/stain-removal")
    public String stainRemoval() {
        return "productDescStainRemoval";
    }

//    @GetMapping("product_by_category/{categoryId}")
//    public String product_test(@PathVariable Integer categoryId, Model model ){
//        model.addAttribute("categoryId", categoryId);
//        //return "product2";
//        return "product-final2";
//    }

    /**
     * Show the user cart
     */
//    @GetMapping("/cart")
//    public String user_cart() {
//        return "cart";
//    }


    /**
     * Admin Controller - Schedule call data
     */
    @GetMapping("/admin/all_schedule")
    public String adminAllScheduleCallData() {
        return "admin_all_Schedule_call_data";
    }

    @GetMapping("/sitemap.xml")
    public String siteMapXml() {
        return "sitemap.xml";
    }

    @GetMapping("/all_schedule")
    public ResponseEntity<List<ScheduleCallDTO>> findAllScheduleData() {
        return scheduleCallController.findAllScheduleCall();
    }
}
