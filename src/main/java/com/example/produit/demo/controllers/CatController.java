package com.example.produit.demo.controllers;

import com.example.produit.demo.entities.Categorie;
import com.example.produit.demo.entities.Produit;
import com.example.produit.demo.service.ProduitService;
import com.example.produit.demo.Repository.CategorieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Controller
public class CatController {
    @Autowired
    ProduitService produitService;
    @Autowired
    private CategorieRepository  CategorieRepository;


   /* @RequestMapping("/showCreate")
    public String showCreate()
    {
        return "createProduit";
    }
    */

    @RequestMapping("/showCreate")
    public String showCreate(ModelMap modelMap) {
        modelMap.addAttribute("produit", new Produit("PC Asus", 1500.500, new Date()));
        modelMap.addAttribute("categories", CategorieRepository.findAll());

        return "createProduit";
    }

    @RequestMapping("/showok")
    public String showok(ModelMap modelMap) {
        modelMap.addAttribute("categorie", new Categorie());
        return "createCat";
    }

    @RequestMapping("/saveProduit")
    public String saveProduit(@Valid Produit produit,
                              BindingResult bindingResult,
                              ModelMap modelMap) {
        if (bindingResult.hasErrors()) {
            return "createProduit";
        }
        Produit saveProduit = produitService.saveProduit(produit);
        String msg = "produit enregistré avec Id " +
                saveProduit.getIdProduit();
        modelMap.addAttribute("msg", msg);
        return "createProduit";
    }

    @RequestMapping("/ListeProduits")
    public String listeProduits(
            ModelMap modelMap,
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "10") int size) {
        Page<Produit> prods = produitService.getAllProduitsParPage(page, size);
        modelMap.addAttribute("produits", prods);
        modelMap.addAttribute("pages", new int[prods.getTotalPages()]);
        modelMap.addAttribute("currentPage", page);
        modelMap.addAttribute("size", size);
        return "listeProduits";
    }
    /*

     */
    @RequestMapping("/supprimerProduit")
    public String supprimerProduit(@RequestParam("id") Long id, ModelMap
            modelMap,
                                   @RequestParam(name = "page", defaultValue = "0") int page,
                                   @RequestParam(name = "size", defaultValue = "2") int size) {
        produitService.deleteProduitById(id);
        Page<Produit> prods = produitService.getAllProduitsParPage(page,
                size);
        modelMap.addAttribute("produits", prods);
        modelMap.addAttribute("pages", new int[prods.getTotalPages()]);
        modelMap.addAttribute("currentPage", page);
        modelMap.addAttribute("size", size);
        return "listeProduits";
    }
    /*

     */
    @RequestMapping("/modifierProduit")
    public String editeProduit(@RequestParam("id") Long id, ModelMap modelMap,
                               @RequestParam(name = "page", defaultValue = "0") int page,
                               @RequestParam(name = "size", defaultValue = "2") int size) {
        Produit p = produitService.getProduit(id);
        modelMap.addAttribute("produit", p);
        modelMap.addAttribute("currentPage", page);
        modelMap.addAttribute("size", size);
        return "editerProduits";
    }

    @RequestMapping("/updateProduit")
    public String updateProduit(@ModelAttribute("produit") Produit produit,
                                @RequestParam("date") String date,
                                ModelMap modelMap) throws ParseException {
        // Conversion de la date
        SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd");
        Date dateCreation = dateformat.parse(String.valueOf(date));
        produit.setDateCreation(dateCreation);

        produitService.updateProduit(produit);

        return "redirect:/ListeProduits";
    }
    @RequestMapping("/editProduit")
    public String editProduit(@RequestParam("id") Long id, ModelMap modelMap) {
        Produit produit = produitService.getProduit(id);
        modelMap.addAttribute("produit", produit);
        return "editerProduits";
    }
    @RequestMapping("/filetrage")
    public String filtrage()
    {
        return "filtrage";
    }
}
