package com.example.mvcpluscontroller.controller;

import com.example.mvcpluscontroller.controller.request.NewCoffeeRequest;
import com.example.mvcpluscontroller.model.Coffee;
import com.example.mvcpluscontroller.service.CoffeeService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.joda.money.CurrencyUnit;
import org.joda.money.Money;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import sun.nio.ch.IOUtil;

import javax.validation.Valid;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

@Slf4j
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

    @PostMapping(path = "/",consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    @ResponseBody
    @ResponseStatus(HttpStatus.CREATED)
    public Coffee addCoffee(
            @Valid
            NewCoffeeRequest newCoffee,
            BindingResult result
    ){
        if(result.hasErrors()){
            log.info("Binding Errors:{}",result);
            return null;
        }
        return coffeeService.saveCoffee(newCoffee.getName(),newCoffee.getPrice());
    }

    /*
    *批量上传咖啡
    * 文件内容如下:
    * 拿铁 6000
    * 摩卡 8000
    * 卡布奇诺 10000
    */

    @PostMapping(path="/",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ResponseBody
    @ResponseStatus(HttpStatus.CREATED)
    public List<Coffee> batchAddCoffee(@RequestParam("file") MultipartFile file){
        List<Coffee> coffees=new ArrayList<>();
        if(!file.isEmpty()){
            BufferedReader reader =null;

            try{
                reader=new BufferedReader(new InputStreamReader(file.getInputStream()));
                String str;
                while((str=reader.readLine())!=null){
                    String[] arr= StringUtils.split(str," ");
                    if(arr!=null && arr.length==2){
                        coffees.add(coffeeService.saveCoffee(arr[0], Money.of(CurrencyUnit.of("CNY"),NumberUtils.createBigDecimal(arr[1]))));
                    }
                }
            }catch(IOException e){
                log.error("exception",e);
            }finally {
                IOUtils.closeQuietly(reader);
            }

        }
        return coffees;
    }


}
