package com.example.hateoascustomer;

import com.example.hateoascustomer.model.Coffee;
import com.example.hateoascustomer.model.CoffeeOrder;
import com.example.hateoascustomer.model.OrderState;
import lombok.extern.slf4j.Slf4j;
import org.joda.money.CurrencyUnit;
import org.joda.money.Money;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.PagedResources;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import org.springframework.http.HttpMethod;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import java.net.URI;
import java.util.Collections;

@Slf4j
@Component
public class CustomerRunner implements ApplicationRunner {

    private static final URI ROOT_URI= URI.create("http://localhost:8080/");

    @Autowired
    private RestTemplate restTemplate;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        Link coffeeLink =getLink(ROOT_URI,"coffees");
        readCoffeeMenu(coffeeLink);
        Resource<Coffee> americano =addCoffee(coffeeLink);
        Link orderLink = getLink(ROOT_URI,"coffeeOrders");
        addOrder(orderLink,americano);
        queryOrder(orderLink);
    }

    private Link getLink(URI uri,String rel){
        log.info("uri:{}",uri);
        ResponseEntity<Resources<Link>> rootResp = restTemplate.exchange(uri,HttpMethod.GET,null,
                new ParameterizedTypeReference<Resources<Link>>() {});
        Link link=rootResp.getBody().getLink(rel);
        log.info("body:{}",rootResp.getBody());
        log.info("Link:{}",link);
        return link;
    }

    private void readCoffeeMenu(Link link){
        ResponseEntity<PagedResources<Resource<Coffee>>> coffeeResp=restTemplate.exchange(link.getTemplate().expand(),
                                        HttpMethod.GET,null,new ParameterizedTypeReference<PagedResources<Resource<Coffee>>>(){} );

        log.info("Menu Response:{}",coffeeResp.getBody());
    }

    private Resource<Coffee> addCoffee(Link link){
        Coffee americano=Coffee.builder().name("americano").price(Money.of(CurrencyUnit.of("CNY"),25.00)).build();
        RequestEntity<Coffee> req =RequestEntity.post(link.getTemplate().expand()).body(americano);
        ResponseEntity<Resource<Coffee>> resp=restTemplate.exchange(req, new ParameterizedTypeReference<Resource<Coffee>>() {});
        log.info("add Coffee Response:{}",resp);
        return resp.getBody();
    }


    private void addOrder(Link link,Resource<Coffee> coffee){
        CoffeeOrder newOrder=CoffeeOrder.builder().customer("hanmeimei").state(OrderState.INIT).build();
        RequestEntity<?> req=RequestEntity.post(link.getTemplate().expand()).body(newOrder);
        ResponseEntity<Resource<CoffeeOrder>> resp=restTemplate.exchange(req, new ParameterizedTypeReference<Resource<CoffeeOrder>>() {});

        log.info("add Order Response:{}",resp);
        Resource<CoffeeOrder> order = resp.getBody();
        Link items=order.getLink("items");
        req=RequestEntity.post(items.getTemplate().expand()).body(Collections.singletonMap("_links",coffee.getLink("self")));
        ResponseEntity<String> itemResp=restTemplate.exchange(req,String.class);
        log.info("add Order Response:{}",itemResp);
    }

    private void queryOrder(Link link){
        ResponseEntity<String> resp = restTemplate.getForEntity(link.getTemplate().expand(),String.class);
        log.info("query Order Response:{}",resp);
    }
}
