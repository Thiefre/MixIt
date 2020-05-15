package com.example.mixit;

import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;

import com.example.mixit.ui.discover.DBHelper;
import com.example.mixit.ui.discover.ImageHelper;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import java.net.URL;

public class MainActivity extends AppCompatActivity {

    private ImageHelper imageHelper = new ImageHelper();
    private DBHelper db;
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_grocery_list, R.id.navigation_discover, R.id.navigation_account)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);
        db = new DBHelper(this ,"recipeList.db", null , 1);
//        db.recipeInsert("Basic Omelet", "eggs onion", "Break Eggs then stir them with salt and diced onion and throw into skillet",
//                2, R.drawable.basic_omelet);
//        db.recipeInsert("Baked Omelet", "eggs onion ham cheese", "Beat together the eggs and milk. Add seasoning salt, ham, Cheddar cheese, Mozzarella cheese and minced onion. Pour into prepared casserole dish. Bake at 350 degrees",
//                4, R.drawable.baked_omelet);
//        db.recipeInsert("Grilled Cheese Sandwich", "bread butter cheese onion tomato", "Butter the bread, put cheese between two slices, shove some onions in, fry until brown",
//                5, R.drawable.grilled_cheese);
//        db.recipeInsert("Chicken Salad", "chicken mayonnaise celery lemon pepper tomato", "Mix mayonnaise, lemon, pepper in a bowl, throw chicken, tomato and celery on there. Done",
//                6, R.drawable.chicken_salad);
//        db.recipeInsert("Basic Scrambled Eggs", "eggs milk salt pepper butter", "BEAT eggs, milk, salt and pepper in medium bowl until blended.\nHEAT butter in large nonstick skillet over medium heat until hot. POUR in egg mixture. As eggs begin to set, gently PULL the eggs across the pan with a spatula, forming large soft curds.\nCONTINUE cooking—pulling, lifting and folding eggs—until thickened and no visible liquid egg remains. Do not stir constantly. REMOVE from heat. SERVE immediately.",
//                6, R.drawable.basic_scrambled_eggs);
//        db.recipeInsert("Beef and Broccoli", "cornstarch steak soy_sauce sugar garlic ginger oil broccoli onions", "In a large bowl, whisk together 2 tablespoons cornstarch with 3 tablespoons water. Add the beef to the bowl and toss to combine.\nIn a separate small bowl, whisk together the remaining 1 tablespoon cornstarch with the soy sauce, brown sugar, garlic and ginger. Set the sauce aside.\n" +
//                        "Heat a large nonstick sauté pan over medium heat. Add 1 tablespoon of the vegetable oil and once it is hot, add the beef and cook, stirring constantly until the beef is almost cooked through. Using a slotted spoon, transfer the beef to a plate and set it aside.\n" +
//                        "Add the remaining 1 tablespoon of vegetable oil to the pan and once it is hot, add the broccoli florets and sliced onions and cook, stirring occasionally, until the broccoli is tender, about 4 minutes.\n" +
//                        "Return the beef to the pan then add the prepared sauce. Bring the mixture to a boil and cook, stirring, for 1 minute or until the sauce thickens slightly. Serve with rice or noodles.",
//                2, R.drawable.beef_and_broccoli);


    }

}
