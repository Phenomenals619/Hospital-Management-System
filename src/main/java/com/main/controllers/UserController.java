package com.main.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.main.entities.Users;
import com.main.repository.UserRepo;

@Controller
public class UserController {

    @Autowired
    UserRepo userRepo;

    @GetMapping("/userRegister")
    public String openUserPage( Model model) {
        model.addAttribute("user", new Users());

        return "user/register_user";
    }

    @PostMapping("/submit_register_user")
    public String submitUser(@ModelAttribute Users user, Model model) {
        try {

            Users users = userRepo.save(user);
            if (users != null) {
                model.addAttribute("succ", "User Register Successfully");
            } else {
                model.addAttribute("err", "User Register Failed");
            }

        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("err", "An error occurred during registration.");
        }

        return "redirect:/userRegister";
    }

    @GetMapping("/loginUser")
    public String signUpUser(Model model) {
        model.addAttribute("userlog", new Users());
        return "user/login_user";
    }

    @PostMapping("/submit_login_user")
    public String submitLoginUser(@ModelAttribute Users users,Model model) {
        Users logUser=userRepo.findByEmail(users.getEmail());
        if(logUser !=null && logUser.getPassword().equals(users.getPassword())){
            System.out.println(logUser.getName());
            
            model.addAttribute("userName", logUser.getName());
            return "user/user_dash";
        }else{
            return "redirect:/loginUser";
        }
        
    }

}
