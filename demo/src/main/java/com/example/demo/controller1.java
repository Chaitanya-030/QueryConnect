package com.example.demo;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
public class controller1 {
    @GetMapping("/")
    public String f1(Model model) {
        return "index";
    }
    @PostMapping("/manipulate")
    public String f2(@RequestParam("kqlString") String kqlString, Model model)
    {
        model.addAttribute("kqlString", kqlString);
        String sparqlString = App.manipulate(kqlString);
        model.addAttribute("sparqlString", sparqlString);
        String queryResult = execute.executedbpedia(sparqlString);
        model.addAttribute("queryResult", queryResult);
        return "index";
    }
    


    

    
}
