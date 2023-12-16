package com.example.parkingmanagementsystemsecured.controller;

import com.example.parkingmanagementsystemsecured.model.Location;
import com.example.parkingmanagementsystemsecured.model.UserInfo;
import com.example.parkingmanagementsystemsecured.service.interfaces.EmailService;
import com.example.parkingmanagementsystemsecured.service.interfaces.LocationInterface;
import com.example.parkingmanagementsystemsecured.service.interfaces.UserInfoInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.UUID;

@Controller
public class UserInfoUsersController {
    @Autowired
    private UserInfoInterface service;
    @Autowired
    private EmailService emailService;
    @Autowired
    private LocationInterface locationInterface;

    @GetMapping("/welcome")
    public String welcomePage(){
        return "welcome";
    }
    @PostMapping("/new")
    public String addNewUser(@ModelAttribute("userInfo") UserInfo userInfo,
                             @RequestParam(value = "email") String email, Model model){
        Optional<UserInfo> emailChecker = service.getEmail(email);
        System.out.println(email +"1"+ emailChecker);
        String errorMessage ="";
        if(emailChecker.isPresent()){

            System.out.println(email +"2"+ emailChecker);
            errorMessage= "Account was not created because email was already registered";
            model.addAttribute("errorMessage", errorMessage);
            return "signup";
        }
        else {

            System.out.println(email +"3"+ emailChecker);
            userInfo.setRole("USER");
            userInfo.setStatus("INACTIVE");
            service.saveUser(userInfo);
            String subject="ACCOUNT CREATION PENDING";
            String body ="Your account has been created successfully, please wait for the admin to approve." +
                    "Thank you";
            emailService.sendEmail(email,subject,body);
            model.addAttribute("errorMessage", errorMessage);
            return "redirect:/welcome";
        }
    }
    @GetMapping("/register")
    public String showAddNewUser(@ModelAttribute("userInfo") UserInfo userInfo){
        return "signup";
    }

    @GetMapping("/userInfoPage")
    public  String homePage(@ModelAttribute("userInfo") UserInfo userInfo, Model model){
//        List<UserInfo> userInfoList = service.getAllUserInfo();
//        model.addAttribute("userInfoList", userInfoList);
//        List<Location> locationList = locationInterface.getAllLocation();
//        model.addAttribute("locationList", locationList);
//        String type ="password";
//        model.addAttribute("type",type);
//        return "userInfo";
        return findPagenited(1, "email", "asc", model);
    }
    @PostMapping("/userInfoSave")
    public String AdminAddNewUser(@ModelAttribute("userInfo") UserInfo userInfo,
                                  @RequestParam(value = "status")String status,
                                  @RequestParam(value = "location")UUID location,
                             @RequestParam(value = "email") String email, Model model){
        Optional<UserInfo> emailChecker = service.getEmail(email);
        Location locationName = locationInterface.getLocationById(location);
        String locationNameForEmail = locationName.getLocationName();
        String errorMessage ="";
        if(emailChecker.isPresent()&& status.equals("INACTIVE")){
            errorMessage= "Account was not created because email was already registered";
            model.addAttribute("errorMessage", errorMessage);
            return "userInfo";
        }
        else {
            service.saveUser(userInfo);
            String subject="Account created";
            String body ="Your account has been created successfully and assigned " + locationNameForEmail +" You can now login" +
                    "Thank you";
            emailService.sendEmail(email,subject,body);
            model.addAttribute("errorMessage", errorMessage);
            return "redirect:/userInfoPage";
        }
    }
    @GetMapping("/admin/userInfoUpdate/{id}")

    public String updateUserInfo(@PathVariable(value = "id") UUID id, Model model) {
        List<UserInfo> userInfoList = service.getAllUserInfo();
        model.addAttribute("userInfoList", userInfoList);
        UserInfo userInfo = service.getUserInfoById(id);
        String type="hidden";
        model.addAttribute("userInfo", userInfo);
        List<Location> locationList = locationInterface.getAllLocation();
        model.addAttribute("locationList", locationList);
        model.addAttribute("type",type);

        return "userInfo";
    }
    @GetMapping("/admin/userInfoDelete/{id}")
    public String deleteUserInfo(@PathVariable(value = "id")UUID id, Model model){
        this.service.deleteUserInfoById(id);
        return "redirect:/userInfoPage";
    }
    @GetMapping("/login")
    public String login(){
        return "login";
    }
    @GetMapping("/pageUserInfo/{pageNo}")
    public String findPagenited(@PathVariable(value = "pageNo") int pageNo,
                                @RequestParam("sortField") String sortField,
                                @RequestParam("sortDir") String sortDir,
                                Model model) {

        int pageSize = 5;
        Page<UserInfo> page = service.findPaginated(pageNo, pageSize, sortField, sortDir);
        List<UserInfo> listUserInfos = page.getContent();
        model.addAttribute("currentPage", pageNo);
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("totalItems", page.getTotalElements());

        model.addAttribute("sortField", sortField);
        model.addAttribute("sortDir", sortDir);
        model.addAttribute("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");

        model.addAttribute("listUserInfos", listUserInfos);
        List<UserInfo> userInfoList = service.getAllUserInfo();
        model.addAttribute("userInfoList", userInfoList);
        List<Location> locationList = locationInterface.getAllLocation();
        model.addAttribute("locationList", locationList);

        UserInfo userInfo = new UserInfo();

        model.addAttribute("userInfo", userInfo);
        String type ="password";
        model.addAttribute("type",type);

        return "userInfo";
    }
    @PostMapping("/forgot")
    public String recoverPassword(@RequestParam(value = "username") String email

            , Model model){
        Random random = new Random();
//        Optional<UserInfo> userInfo = service.getEmail(email);
        int recoverSecret = random.nextInt(1000,9999);
        String subject="Recovery Email";
        String body ="Please enter the given " +recoverSecret+
                " to Recover the password";
        emailService.sendEmail(email,subject,body);
        model.addAttribute("recoverSecret");
        return "recover";
    }
    @GetMapping("/forgotPage")
    public String recoverPage(Model model){
        Random random = new Random();
        int recoverSecret = random.nextInt(1000,9999);
        model.addAttribute("recoverSecret", recoverSecret);
        return "recover";
    }

}
