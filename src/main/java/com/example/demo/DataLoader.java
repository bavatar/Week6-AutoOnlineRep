package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Component
public class DataLoader implements CommandLineRunner {
    @Autowired
    CarRepository carRepository;

    @Autowired
    CategoryRepository categoryRepository;

    @Override
    public void run(String... args) throws Exception {
        //Car(String manufacturer, String model, String year, double msrp, String color, String carImage, Category category)

//        Car car = new Car("Porsche", "911 Turbo", "2019", 200000, "Red", "http://www.bing.com/test.jpb", category);
//
//        car.setManufacturer("Porsche");
//
//        car.setModel("911 Turbo");
//        car.setYear("2019");
//        car.setMsrp(200000);
//        car.setColor("Red");
//        car.setCarImage("");
//        Set<Car> cars = new HashSet<Car>();
//        //cars.add(car);
//        Category category = new Category();
//        category.setCategoryName("Sports Car");
//        Car car = new Car("Porsche", "911 Turbo", "2019", 200000, "Red", "http://www.bing.com/test.jpb", category);
//        carRepository.save(car);
////setCars(Set<Car> cars) {
////        this.cars = cars;
////    }
//
//        Set<Car> cars = new HashSet<Car>();
//        cars.add(car);
//        category.setCars(cars);
////        car.setCategory(category);
////        carRepository.save(car);
//        categoryRepository.save(category);
//        System.out.println("DataLoader: category.getCategoryName(): " + category.getCategoryName());

        Category category = new Category();
        Car car = new Car();
        Set<Car> cars = new HashSet<Car>();
        category.setCategoryName("SUV");
        car.setManufacturer("Nissan");
        car.setModel("Murano");
        car.setYear("2019");
        car.setMsrp(27000);
        car.setColor("Red");
        car.setCarImage("https://res.cloudinary.com/ascension-enterprises/image/upload/v1570810308/Nissan_dxg0fq.jpg");
        car.setCategory(category);
        cars = new HashSet<Car>();

        category.setCars(cars);
        cars.add(car);
        categoryRepository.save(category);

        carRepository.save(car);
        categoryRepository.save(category);


        category = new Category();
        car = new Car();
        cars = new HashSet<Car>();
        category.setCategoryName("Sports Car");
        car.setManufacturer("Porsche");
        car.setModel("911");
        car.setYear("2019");
        car.setMsrp(127000);
        car.setColor("blue");
        car.setCarImage("https://res.cloudinary.com/ascension-enterprises/image/upload/v1570810437/porsche9116_so3vzo.jpg");
        car.setCategory(category);
        cars = new HashSet<Car>();

        category.setCars(cars);
        cars.add(car);
        categoryRepository.save(category);

        carRepository.save(car);
        categoryRepository.save(category);
    }
}
