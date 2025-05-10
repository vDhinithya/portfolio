package com.professional_info.portfolio.controller;

import com.professional_info.portfolio.Email_Srv;
import com.professional_info.portfolio.entity.Contact;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class Homepage {
    @Autowired
    private Email_Srv emailSrv;

    @GetMapping({"/", "", "/home"})
    public String showHomePage(){
        return "home";
    }

    @GetMapping({"/projects"})
    public String showProjects(){
        return "projects";
    }

    @GetMapping({"/resume"})
    public String showResume(){
        return "resume";
    }

    @GetMapping({"/contact"})
    public String showContact(Model model){
        model.addAttribute("contact", new Contact());
        return "contact";
    }

    @PostMapping({"/contact-us"})
    public String submitContact(@ModelAttribute Contact contact, Model model) {
        System.out.println("Received contact: " + contact);

        String recipientEmail = "vdhinithya@gmail.com";
        emailSrv.sendContactEmail(
                recipientEmail,
                contact.getName(),
                contact.getEmail(),
                contact.getPhone_no(),
                contact.getMessage()
        );

        model.addAttribute("message", "Your message has been sent successfully!");
        model.addAttribute("contact", new Contact());
        return "contact";
    }

}
