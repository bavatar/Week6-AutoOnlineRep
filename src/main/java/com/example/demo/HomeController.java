package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

@Controller
public class HomeController {
    @Autowired
    CarRepository carRepository;

    @Autowired
    CategoryRepository categoryRepository;

    @RequestMapping("/")
    public String home(Model model){
        model.addAttribute("cars", carRepository.findAll());
        model.addAttribute("categories", categoryRepository.findAll());
        return "index";
    }

    @GetMapping("/addcategory")
    public String authorForm(Model model){
        model.addAttribute("category", new Category());
        return "categoryform";
    }

    @PostMapping("/process_category")
    public String processCategoryForm(@Valid Category category, BindingResult result){
        if (result.hasErrors()){
            return "categoryform";
        }
        categoryRepository.save(category);
        return "redirect:/categorylist";
    }

    @RequestMapping("/categorylist")
    public String categoryList(Model model){
        model.addAttribute("categories", categoryRepository.findAll());
        return "categorylist";
    }

    @GetMapping("/addcar")
    public String bookForm(Model model){
        model.addAttribute("categories", categoryRepository.findAll());
        model.addAttribute("car", new Car());
        return "carform";
    }

    @PostMapping("/process_car")
    public String processCarForm(@Valid Car car, BindingResult result){
        if (result.hasErrors()){
            return "carform";
        }
        System.out.println("HomeController:process_car: save car: " + car.getManufacturer() + " " + car.getModel());
        carRepository.save(car);
        return "redirect:/";
//        return "redirect:/carlist";
    }

    @RequestMapping("/carlist")
    public String bookList(Model model){
        model.addAttribute("cars", carRepository.findAll());
//        return "carlist";
        return "redirect:/";
    }

    @RequestMapping("/detail/{id}")
    public String showCategory(@PathVariable("id") long id, Model model){
        model.addAttribute("category", categoryRepository.findById(id).get());
        return "showcategory";
    }

    @RequestMapping("/update/{id}")
    public String updateCategory(@PathVariable("id") long id, Model model){
        // Is this a requirement?
        // if this category does not have any cars associated with it then can change it.
        Category cat = categoryRepository.findById(id).get();
        if (cat.getCars().size() == 0){
            // There are no cars listed having this category
            System.out.println("HomeController: update: There are no cars listed having this category ID" + id);
            return "redirect:/";
        }
        System.out.println("updateCategory: Category ID: " + id);
        model.addAttribute("category", categoryRepository.findById(id).get());
        return "categoryform";
    }

    @RequestMapping("/delete/{id}")
    public String delCategory(@PathVariable("id") long id){
        System.out.println("HomeController: Delete Category with id: " + id);
        try {
            long count = carRepository.count();

            Category category = categoryRepository.findByCategoryID(id);
            ArrayList<Car> results1 = (ArrayList<Car>)carRepository.findByCategory(category);
            if (results1.size()>0){
                System.out.println("Results1: Cannot Delete this category as it is in use. Category ID: " + id);
                return "redirect:/";
            }
            else {
                System.out.println("Results1: Number of Cars found with this category : " + results1.size());
            }

            categoryRepository.deleteById(id);
        }
        catch (Exception e){
            System.out.println("HomeController:delCategory: " + e.getMessage());
        }
        return "redirect:/";
    }

    @RequestMapping("/detail_car/{id}")
    public String showCar(@PathVariable("id") long id, Model model){
        model.addAttribute("car", carRepository.findById(id).get());
        return "showcar";
    }

    @RequestMapping("/update_car/{id}")
    public String updateCar(@PathVariable("id") long id, Model model){
        model.addAttribute("car", carRepository.findById(id).get());
        model.addAttribute("categories",categoryRepository.findAll());
        return "carform";
    }

    @RequestMapping("/delete_car/{id}")
    public String delCar(@PathVariable("id") long id, Model model){
        System.out.println("HomeController: Delete Car with id: " + id);
        try {
            carRepository.deleteById(id);
        }
        catch (Exception e){
            System.out.println("HomeController:delCar: " + e.getMessage());
        }

        if (carRepository.existsById(id)){
            System.out.println("HomeController: delete_car: Car exists with id: " + id);
        }
        else {
            System.out.println("HomeController: delete_car: Car does Not exist with id: " + id);
        }
        return "redirect:/";
        // return "index";
    }

    @RequestMapping("/{categoryName}")
    public String searchCategory(Model model, @PathVariable("categoryName") String search) {
        // Find all the cars that match the specified category of car (e.g., SUV)
        System.out.println("SearchCategory: Search String " + search);
        Set<Car> carList;
        Set<Car> sumList = new HashSet();
        ArrayList<Category> categoryList;
        categoryList = (ArrayList<Category>)categoryRepository.findByCategoryNameContainingIgnoreCase(search);
        System.out.println("SearchCategory: Search categoryList.size: " + categoryList.size());

        for (Category cat : categoryList) {
            carList = cat.getCars();
            for (Car car : carList) {
                sumList.add(car);
            }

            System.out.println("SearchCategory: Search sumList.size: " + sumList.size());
            System.out.println("SearchCategory: Search carList.size: " + carList.size());

            model.addAttribute("cars", sumList);
        }
        return "index";
    }

    @PostMapping("/processsearch")
    public String searchResult(Model model, @RequestParam(name = "search") String search) {
        // Search for Matches to Categories or Manufacturer or Model or year
        String[] list = search.split(" ");
        ArrayList<Car>carList = new ArrayList<>();
        Set<Car> sumList = new HashSet();
        ArrayList<Category> categoryList;
        Set<Car> catcarList;

        for (String testStr : list) {
            carList= (ArrayList<Car>)carRepository.findByManufacturerContainingIgnoreCaseOrModelContainingIgnoreCaseOrYearContainingIgnoreCase(testStr,  testStr,  testStr);
            categoryList = (ArrayList<Category>)categoryRepository.findByCategoryNameContainingIgnoreCase(testStr);
            sumList.addAll(carList);

            for (Category cat : categoryList) {
                catcarList = cat.getCars();
                sumList.addAll(catcarList);
            }
            model.addAttribute("cars", sumList);
        }
        return "index";
    }
}
