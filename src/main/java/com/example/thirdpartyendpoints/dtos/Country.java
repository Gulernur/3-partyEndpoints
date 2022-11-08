package com.example.thirdpartyendpoints.dtos;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class Country {
    String country_id;
    double probability;
}
