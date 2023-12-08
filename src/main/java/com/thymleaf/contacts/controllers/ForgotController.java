package com.thymleaf.contacts.controllers;

import com.thymleaf.contacts.entities.User;
import com.thymleaf.contacts.helper.EmailService;
import com.thymleaf.contacts.repositories.UserRepo;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Random;

@Controller
public class ForgotController {
    Random random = new Random(1000);
    @Autowired
    private EmailService emailService;
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private BCryptPasswordEncoder encoder;

    @RequestMapping("/forgot")
    public String openEmailForm() {

        return "forgot_email";
    }

    @PostMapping("/send-otp")
    public String sendOTP(@RequestParam("email") String email, HttpSession session) {
        //generate 4 digit otp
        int otp = random.nextInt(9999);
        String message = ""
                +"<div style='border:1px solid #e2e2e2; padding:20px'"
                +"<h1>"
                +"OTP is "
                +"<b>"+otp
                +"</n>"
                +"</h1>"
                +"</div>";
        String subject = "MYContacts : OTP Confirmation";
        String to = email;
        System.out.println(otp);
        //code to send otp to email
        boolean flag = this.emailService.sendEmail(message, subject, to);
        if(flag){
            session.setAttribute("myotp",otp);
            session.setAttribute("email",email);
            return "verify_otp";
        }
        else{
            session.setAttribute("message","Incorrect email");
            return "forgot_email";
        }
    }
    @PostMapping("/verify")
    public String verifyOtp(@RequestParam("otp") int otp,HttpSession session){
        int myOtp=(int)session.getAttribute("myotp");
        String email=(String)session.getAttribute("email");
        if(myOtp==otp){
            User user=this.userRepo.getUserByUserName(email);
            if(user==null){
                session.setAttribute("message","Email does not exist");
                return "forgot_email";
            }
            else{

            }
            return "password_change";
        }
        else {
            session.setAttribute("message","Incorrect! OTP");
            return "verify_otp";
        }
    }
    @PostMapping("/change-password")
    public String changePassword(@RequestParam("newPassword") String newPassword,HttpSession session){
        String email=(String)session.getAttribute("email");
        User user=userRepo.getUserByUserName(email);
        user.setPassword(this.encoder.encode(newPassword));
        this.userRepo.save(user);
        return "redirect:/login";
    }
}
