package com.example.demo;

import org.springframework.data.repository.CrudRepository;

import java.util.ArrayList;

public interface CarRepository extends CrudRepository<Car, Long> {
    Iterable<Car> findByCategory(Category category);
    Iterable<Car> findByManufacturerContainingIgnoreCaseOrModelContainingIgnoreCaseOrYearContainingIgnoreCase(String manufacturer, String model, String year);
////
//    private String manufacturer;
//    private String model;
//    private String year;
//    private double msrp;
//    private String color;
//    private String carImage;
}