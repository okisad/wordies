package com.chosapp.wordies.dao.configuration;


import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MapperConfiguration {

    @Bean
    public ModelMapper daoModelMapper(){
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper;
    }

}
