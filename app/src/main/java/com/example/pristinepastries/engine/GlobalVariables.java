package com.example.pristinepastries.engine;

import com.example.pristinepastries.models.Cake;
import com.example.pristinepastries.models.Categories;
import com.example.pristinepastries.models.Order;
import com.example.pristinepastries.models.Sizes;
import com.example.pristinepastries.models.User;

import java.util.ArrayList;
import java.util.List;

public class GlobalVariables {
    public static final String SERVER = "http://192.168.43.194:3000/custom_api/";
//    public static final String SERVER = "https://cake-reservation.herokuapp.com/custom_api/";
    public static final String LOGIN_URL = SERVER + "login";
    public static final String CATEGORY_URL = SERVER + "get_categories";
    public static final String PRODUCT_URL = SERVER + "get_cakes";
    public static final String SIZE_URL = SERVER + "get_sizes";
    public static final String CREATE_COD_ORDER_URL = SERVER + "create_cod_order";
    public static final String GET_ORDER_URL = SERVER + "get_orders";
    public static final String GET_CART_URL = SERVER + "get_cart";
    public static final String SIGNUP_URL = SERVER + "signup";
    public static final String CHANGE_PASS_URL = SERVER + "change_password";
    public static final String ADD_TO_CART_URL = SERVER + "add_to_cart";
    public static final String CHECKOUT_CART_URL = SERVER + "checkout_cart";
    public static final String ADDCUSTOMCAKEURL = SERVER + "add_custom_cake";

    public static User currentUser = null;

    public static List<Categories> categoriesList = new ArrayList<>();
    public static Categories selectedCategory = new Categories();

    public static List<Cake> cakeList = new ArrayList<>();
    public static Cake selectedCake = new Cake();

    public static Sizes selectedSize = null;
    public static int selectedAmount = 0;

    public static Order selectedOrder;
    public static String delivery_location = "";

    public static String selectedPaymentMethod = "COD";
}
