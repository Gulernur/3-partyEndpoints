package com.example.thirdpartyendpoints.controllers;

import com.example.thirdpartyendpoints.dtos.Age;
import com.example.thirdpartyendpoints.dtos.Gender;
import com.example.thirdpartyendpoints.dtos.NameResponse;
import com.example.thirdpartyendpoints.dtos.Nationality;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@RestController
public class NameController {

    @RequestMapping("/name-info")
    public NameResponse getDetails(@RequestParam String name){
        Mono<Age> age = getAge(name);
        Mono<Gender> gender = getGender(name);
        Mono<Nationality> nationality = getNationality(name);

        var responseMono = Mono.zip(age, gender, nationality).map(topping -> {
            NameResponse nameResponse = new NameResponse();
            nameResponse.setAge(topping.getT1().getAge());
            nameResponse.setAgeCount(topping.getT1().getCount());

            nameResponse.setGender(topping.getT2().getGender());
            nameResponse.setGenderProbability(topping.getT2().getProbability());

            nameResponse.setCountry(topping.getT3().getCountry().get(0).getCountry_id());
            nameResponse.setCountryProbability(topping.getT3().getCountry().get(0).getProbability());
            return nameResponse;
                });

        NameResponse response = responseMono.block();
        response.setName(name);

        return response;
    }

    Mono<Age> getAge(String name) {
        WebClient client = WebClient.create();
        Mono<Age> age = client.get()
                .uri("https://api.agify.io/?name=" + name)
                .retrieve()
                .bodyToMono(Age.class);
        return age;
    }

    Mono<Gender> getGender(String name) {
        WebClient client = WebClient.create();
        Mono<Gender> gender = client.get()
                .uri("https://api.genderize.io?name=" + name)
                .retrieve()
                .bodyToMono(Gender.class);
        return gender;
    }

    Mono<Nationality> getNationality(String name) {
        WebClient client = WebClient.create();
        Mono<Nationality> nationality = client.get()
                .uri("https://api.nationalize.io/?name=" + name)
                .retrieve()
                .bodyToMono(Nationality.class);
        return nationality;
    }


}
