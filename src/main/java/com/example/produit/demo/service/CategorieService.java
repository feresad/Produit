package com.example.produit.demo.service;

import com.example.produit.demo.entities.Categorie;
import org.springframework.data.domain.Page;

import java.util.List;

public interface CategorieService {
    Categorie saveCategorie(Categorie c);
    Categorie updateCategorie(Categorie c);
    void deleteCategorie(Categorie p);
    void deleteCategorieById(Long id);
    Categorie getCategorie(Long id);

    List<Categorie> getAllCategories();

    Page<Categorie> getAllCategoriesParPage(int page, int size);


}
