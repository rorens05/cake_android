package com.example.pristinepastries.engine;

import com.example.pristinepastries.models.Cake;
import com.example.pristinepastries.models.Categories;

import java.util.ArrayList;
import java.util.List;

public class GlobalVariables {
    //public static final String SERVER = "http://192.168.43.194:3000/custom_api/";
    public static final String SERVER = "https://cake-reservation.herokuapp.com/custom_api/";
    public static final String LOGIN_URL = SERVER + "login";
    public static final String CATEGORY_URL = SERVER + "get_categories";
    public static final String PRODUCT_URL = SERVER + "get_cakes";

    public static List<Categories> categoriesList = new ArrayList<>();
    public static Categories selectedCategory = new Categories();

    public static List<Cake> cakeList = new ArrayList<>();
    public static Cake selectedCake = new Cake();

}
