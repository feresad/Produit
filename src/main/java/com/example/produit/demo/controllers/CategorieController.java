package com.example.produit.demo.controllers;

import com.example.produit.demo.entities.Categorie;
import com.example.produit.demo.service.CategorieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.text.ParseException;

@Controller
public class CategorieController {
    @Autowired
    CategorieService categorieService ;

    @RequestMapping("/ok")
    public String okok(ModelMap modelMap) {
        modelMap.addAttribute("categorie", new Categorie());
        return "createCat";
    }

    @RequestMapping("/saveCat")
    public String saveCat(@ModelAttribute("categories") Categorie categorie,

                          ModelMap modelMap) throws ParseException
    {

        Categorie saveCat = categorieService.saveCategorie(categorie);
        String msg ="cat enregistré avec Id "+saveCat.getIdCat();
        modelMap.addAttribute("msg", msg);
        return "createCat";
    }
    @RequestMapping("/saveCategorie")
    public String saveCategorie(@ModelAttribute("categorie") Categorie categorie,
                                ModelMap modelMap) throws ParseException
    {

        categorieService.saveCategorie(categorie);

        return "createCat";
    }
    @RequestMapping("/ListeCategories")
    public String ListeCategories(
            ModelMap modelMap,
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "10") int size) {
        Page<Categorie> prods = categorieService.getAllCategoriesParPage(page, size);
        modelMap.addAttribute("Categories", prods);
        modelMap.addAttribute("pages", new int[prods.getTotalPages()]);
        modelMap.addAttribute("currentPage", page);
        modelMap.addAttribute("size", size);
        return "listeCategories";
    }


    @RequestMapping("/supprimerCategories")
    public String supprimerCategories(@RequestParam("id") Long id, ModelMap
            modelMap,
                                      @RequestParam(name = "page", defaultValue = "0") int page,
                                      @RequestParam(name = "size", defaultValue = "2") int size) {
        categorieService.deleteCategorieById(id);
        Page<Categorie> prods = categorieService.getAllCategoriesParPage(page,
                size);
        modelMap.addAttribute("Categories", prods);
        modelMap.addAttribute("pages", new int[prods.getTotalPages()]);
        modelMap.addAttribute("currentPage", page);
        modelMap.addAttribute("size", size);
        return "listeCategories";
    }

    @RequestMapping("/modifierCategorie")
    public String editeCategorie(@RequestParam("id") Long id, ModelMap modelMap,
                                 @RequestParam(name = "page", defaultValue = "0") int page,
                                 @RequestParam(name = "size", defaultValue = "2") int size) {
        Categorie p = categorieService.getCategorie(id);
        modelMap.addAttribute("Categorie", p);
        modelMap.addAttribute("currentPage", page);
        modelMap.addAttribute("size", size);
        return "editerCategories";
    }

    @RequestMapping("/updateCategorie")
    public String updateCategorie(@ModelAttribute("Categorie") Categorie categorie,

                                  ModelMap modelMap) throws ParseException {


        categorieService.updateCategorie(categorie);

        return "redirect:/ListeCategories";
    }
    @RequestMapping("/editCategorie")
    public String editCategorie(@RequestParam("id") Long id, ModelMap modelMap) {
        Categorie categorie = categorieService.getCategorie(id);
        modelMap.addAttribute("Categorie", categorie);
        return "editerCategories";
    }

}

