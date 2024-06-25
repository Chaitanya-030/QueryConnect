package com.example.demo;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
public class controller1 {
    @GetMapping("/")
    String f1() {
        return "index";
    }
    @PostMapping("/manipulate")
    public String f2(@RequestParam("SparqlString") String SparqlString,Model model)
    {
        String manipulatedString1=App.manipulate(SparqlString);
        model.addAttribute("manipulatedString", manipulatedString1);
        String queryResult = execute.executedbpedia(manipulatedString1);
        model.addAttribute("queryResult", queryResult);
        return "index";
    }
    


    

    
}
