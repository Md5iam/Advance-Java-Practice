package org.example.productshop;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Controller
@RequestMapping("/product")
public class ProductController {

    private final List<Product> products = new ArrayList<>();


    @GetMapping("/add")
    public String showForm(Model model){

        model.addAttribute("product", new Product());
        model.addAttribute("isEdit", false);

        return "form";
    }


    @GetMapping("/list")
    public String showList(Model model){

        model.addAttribute("productList", products);

        return "list";
    }


    @PostMapping("/add")
    public String submit(@Valid @ModelAttribute Product product,
                         BindingResult bindingResult,
                         @RequestParam boolean isEdit,
                         Model model){

        if(bindingResult.hasErrors()){
            model.addAttribute("isEdit", isEdit);
            return "form";
        }


        if(isEdit){

            for(int i = 0; i < products.size(); i++){

                if(products.get(i).getId() == product.getId()){

                    products.set(i, product);
                    break;
                }
            }

        }
        else{

            products.add(product);
        }


        return "redirect:/product/list";
    }



    @GetMapping("/edit/{id}")
    public String edit(@PathVariable int id, Model model){

        for(Product product : products){

            if(product.getId() == id){

                model.addAttribute("product", product);
                model.addAttribute("isEdit", true);

                return "form";
            }
        }


        return "redirect:/product/list";
    }



    @GetMapping("/delete/{id}")
    public String delete(@PathVariable int id){

        products.removeIf(product -> product.getId() == id);

        return "redirect:/product/list";
    }

}