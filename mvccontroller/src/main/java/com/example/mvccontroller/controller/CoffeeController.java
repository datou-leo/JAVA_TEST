package com.example.mvccontroller.controller;

import com.example.mvccontroller.model.Coffee;
import com.example.mvccontroller.service.CoffeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/coffee")
public class CoffeeController {

    @Autowired
    private CoffeeService coffeeService;

    @GetMapping(path = "/",params = "!name")
    @ResponseBody
    public List<Coffee> getAll(){
        return coffeeService.getAllCoffee();
    }


    @RequestMapping(path = "/{id}",method = RequestMethod.GET,produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    public Coffee getById(
            @PathVariable
            Long id
    ){
        return coffeeService.getCoffeeById(id);
    }

    @GetMapping(path = "/",params = "name")
    @ResponseBody
    public Coffee getByName(
            @RequestParam
                    String name
    ){
        return coffeeService.findOneCofee(name).get();
    }
}
