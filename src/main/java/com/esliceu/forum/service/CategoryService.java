package com.esliceu.forum.service;

import com.esliceu.forum.dao.CategoryDAO;
import com.esliceu.forum.form.CategoryForm;
import com.esliceu.forum.model.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;

@Service
public class CategoryService {

    @Autowired
    CategoryDAO categorydao;

    public Category newCategory(CategoryForm categoryForm){
        Random rnd = new Random();
        int numero = rnd.nextInt(256);
        Category category = new Category(
                //cambiar por el slug
                categoryForm.getTitle().toLowerCase().replace(" ", "-"),
                categoryForm.getTitle(),
                categoryForm.getDescription(),
                //generar color random
                "hsl(" + numero + ", 50%, 50%)"
        );

        Category categories = categorydao.findBySlug(category.getSlug());
        if (categories == null)return categorydao.save(category);
        int i = 1;
        while (categories != null){
            if (i > 1) category.setSlug(category.getSlug().substring(0, category.getSlug().length() - 2));
            category.setSlug(category.getSlug() + "-" + i);
            categories = categorydao.findBySlug(category.getSlug());
            i++;
        }
        return categorydao.save(category);
    }

    public List<Category> getCategories(){
        return categorydao.findAll();
    }

    public Category getCategory(String slug) {
        return categorydao.findBySlug(slug);
    }

    public void deleteCategory(String slug) {
        Category c = getCategory(slug);
        categorydao.deleteById(c.getId());
    }

    public void updateCategory(CategoryForm categoryForm, String slug) {
        categorydao.updateCategory(slug, categoryForm.getTitle(), categoryForm.getDescription());
    }
}
