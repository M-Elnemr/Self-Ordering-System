package com.example.hp.baleno;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

public  class Menu_Items_Arrays extends Activity {
    static ArrayList<ItemsDetails> arraySalads, arrayPizza, arrayGrilled, arrayHotDrinks, arrayColdDrinks, arrayBakery, arrayDesserts ,arrayExtras;

    private String[] namesE = {"Herring Salad", "Tona Salad", "Garlic Dip Salad", "Yogurt Salad", "Baba Ghanoush", "Tahini", "Green Salad",

            "Hot Dog", "Spicy Beef", "Super Deluxe", "Sea Food Supreme",

            "Chicken Barbecue", "Chicken Kebab", "Kebab Istanbouli", "Kebab Meat", "Spicy Kebab", "Shish Taouk",

            "Tea", "Tea With Milk", "Turkish Coffee", "Nes Cafe", "Espresso", "Cappuccino",

            "Iced Cafe Mocha", "Orange", "Lemon", "Lemon Mint", "Mango", "Banana Milk", "Strawberries","Cocktail",

            "Pate", "Croissant", "Mexican Bun", "Cinnamon Rolls",

            "Ice Cream", "Chocolate Cake", "Vanilla Cake", "English Cake", "Strawberry Cheescake", "Swiss Roll",

            "Water", "Cola", "Rice", "Fried Potatoes"
            };

    private String[] namesA = {"سلطة رنجة", "سلطة تونة", "سلطة تومية", "سلطة زبادي", "بابا غنوج", "طحينه", "سلطة خضراء",

            "هوت دوج", "سبايسي بيف", "سوبر ديلوكس", "سي فود سوبريم",

            "دجاج علي الفحم", "كباب دجاج", "كباب اسطنبولي", "كباب لحم", "كباب اسبايسي", "شيش طاووق",

            "شاي", "شاي بالحليب", "قهوة تركي", "نسكافيه", "اسبريسو", "كابتشينو",

             "كافيه موكا مثلجه","برتقال", "ليمون", "ليمون بالنعناع", "مانجو", "موز بالحليب", "فراولة", "كوكتيل",

             "باتيه", "كرواسون", "ميكسيكان بان", "سينامون رول",

            "ايس كريم", "كيك الشيكولاته", "كيك الفانيليا", "كيك انجليزي", "تشبز كيك بالفراوله", "سويس رول",

            "مياه", "كولا", "أرز", "بطاطس مقليه"
            };

    private int[] itemID = {100, 101, 102, 103, 104, 105, 106,
            107, 108, 109, 110,
            111, 112, 113, 114, 115, 116,
            117, 118, 119, 120, 121, 122,
            124, 125, 126, 127, 128, 129, 130, 123,
            131, 132, 133, 134,
            135, 136, 137, 138, 139, 140,
            141, 142, 143, 144
            };

    private String[] desc = {"قطع الرنجة الفاخره والطماطم وعصير الليمون والخل وزيت الزتون", "خليط من قطع التونة الفاخرة والفلفل الاخضر وقطع الطماطم والبصل وعصير الليمون", "سلطة تومية", "سلطة زبادي", "بابا غنوج", "طحينه", "سلطة خضراء",

            "هوت دوج - زيتون اسود", "سلامي - بصل - فلفل حار - بيف", "مشروم - بصل - فلفل - هوت دوج - تركي مدخن دجاج", "جمبري - كابوريا - كاليماري - فلفل اخضر - طماطم - بصل",

            "دجاج علي الفحم", "كباب دجاج", "كباب اسطنبولي", "كباب لحم", "كباب اسبايسي", "شيش طاووق",

            "شاي", "شاي بالحليب", "قهوة تركي", "نسكافيه", "اسبريسو", "كابتشينو",

            "كافيه موكا مثلجه","برتقال", "ليمون", "ليمون بالنعناع", "مانجو", "موز بالحليب", "فراولة", "كوكتيل",

            "باتيه", "كرواسون", "ميكسيكان بان", "سينامون رول",

            "ايس كريم", "كيك الشيكولاته", "كيك الفانيليا", "كيك انجليزي", "تشبز كيك بالفراوله", "سويس رول",

            "مياه", "كولا", "أرز", "بطاطس مقليه"
            };

    private double[] priceS = {8, 8, 6, 5, 4, 4, 4.3,
            35.99, 35.99, 48.99, 53.99,
            0, 0, 0, 0, 0, 0,
            4, 5, 7, 10, 10, 11,
            18.5, 8, 7, 7.5, 12, 12, 12, 12,
            0, 0, 0, 0,
            0, 0, 0, 0, 0, 0,
            0, 0, 0, 0
            };

    private double[] price = {10, 10, 8, 7, 6, 6, 6,
            51.99, 51.99, 64.99, 69.00,
            16.5, 20.5, 22.5, 22.5, 22.5, 20.5,
            5, 6, 8, 12, 12, 13,
            20.5, 10, 9.5, 10, 14, 14, 14, 14,
            12, 12, 22, 25,
            10.5, 11, 12, 9, 22.5, 20,
            4, 10, 5, 6
            };

     private double[] priceL = {12, 12, 10, 9, 8, 8, 8,
             68.99, 68.99, 81.99, 86.99,
             0, 0, 0, 0, 0, 0,
             6, 7, 10, 14, 14, 15,
             22.5, 12, 11, 11.5, 16.5, 16.5, 16.5, 16.5,
             0, 0, 0, 0,
             0, 0, 0, 0, 0, 0,
             0, 0, 0, 0
             };

    private String[] type = {"Salads", "Salads", "Salads", "Salads", "Salads", "Salads", "Salads",
            "Pizza", "Pizza", "Pizza", "Pizza",
            "Grilled", "Grilled", "Grilled", "Grilled", "Grilled", "Grilled",
            "HotDrinks", "HotDrinks", "HotDrinks", "HotDrinks", "HotDrinks", "HotDrinks",
            "ColdDrinks", "ColdDrinks", "ColdDrinks", "ColdDrinks", "ColdDrinks", "ColdDrinks", "ColdDrinks", "ColdDrinks",
            "Bakery", "Bakery", "Bakery", "Bakery",
            "Desserts", "Desserts", "Desserts", "Desserts", "Desserts", "Desserts",
            "Extras", "Extras", "Extras", "Extras"
            };

    private int[] imgs = {R.drawable.salad_renga, R.drawable.salad_tona, R.drawable.salad_tomeya, R.drawable.salad_zabady, R.drawable.salad_baba, R.drawable.salad_tehena, R.drawable.salad_green,

            R.drawable.hot_dog, R.drawable.spicy_beef, R.drawable.super_deluxe, R.drawable.sea_food,

            R.drawable.g_chicken_barbecue, R.drawable.g_chicken_kebab, R.drawable.g_kebab_istanbouli, R.drawable.g_kebab_meat, R.drawable.g_spicy_kebab, R.drawable.g_shish_taouk,

            R.drawable.tea, R.drawable.tea_milk, R.drawable.turkish_coffe, R.drawable.nescafe, R.drawable.espresso, R.drawable.cappuccino,

            R.drawable.iced_cafe_mocha, R.drawable.orange, R.drawable.lemon, R.drawable.lemon_mint, R.drawable.mango, R.drawable.banana_milk, R.drawable.strawberry, R.drawable.cocktail,

            R.drawable.pate, R.drawable.croissant, R.drawable.mexican_bun, R.drawable.cinnamon_rolls,

            R.drawable.icecream, R.drawable.chocolate_cake, R.drawable.vanilla_cake, R.drawable.english_cake, R.drawable.strawberry_cheescake, R.drawable.swiss_roll,

            R.drawable.water, R.drawable.cola, R.drawable.rice, R.drawable.potatoes
            };




    public Menu_Items_Arrays(){
        Main2Activity activity = new Main2Activity();
        addItemsToArray();


    }

    public void addItemsToArray() {
        arraySalads = new ArrayList<>();
        arrayPizza = new ArrayList<>();
        arrayDesserts = new ArrayList<>();
        arrayBakery = new ArrayList<>();
        arrayColdDrinks = new ArrayList<>();
        arrayHotDrinks = new ArrayList<>();
        arrayExtras = new ArrayList<>();
        arrayGrilled = new ArrayList<>();
        for (int i = 0; i < type.length; i++) {
            switch (type[i]) {
                case "Salads":
                    arraySalads.add(new ItemsDetails(imgs[i], namesE[i], namesA[i], itemID[i], desc[i], type[i], price[i], priceS[i], priceL[i]));
                    break;
                case "Pizza":
                    arrayPizza.add(new ItemsDetails(imgs[i], namesE[i], namesA[i], itemID[i], desc[i], type[i], price[i], priceS[i], priceL[i]));
                    break;

                case "Desserts":
                    arrayDesserts.add(new ItemsDetails(imgs[i], namesE[i], namesA[i], itemID[i], desc[i], type[i], price[i], priceS[i], priceL[i]));
                    break;

                case "Bakery":
                    arrayBakery.add(new ItemsDetails(imgs[i], namesE[i], namesA[i], itemID[i], desc[i], type[i], price[i], priceS[i], priceL[i]));
                    break;

                case "Extras":
                    arrayExtras.add(new ItemsDetails(imgs[i], namesE[i], namesA[i], itemID[i], desc[i], type[i], price[i], priceS[i], priceL[i]));
                    break;

                case "Grilled":
                    arrayGrilled.add(new ItemsDetails(imgs[i], namesE[i], namesA[i], itemID[i], desc[i], type[i], price[i], priceS[i], priceL[i]));
                    break;

                case "HotDrinks":
                    arrayHotDrinks.add(new ItemsDetails(imgs[i], namesE[i], namesA[i], itemID[i], desc[i], type[i], price[i], priceS[i], priceL[i]));
                    break;

                case "ColdDrinks":
                    arrayColdDrinks.add(new ItemsDetails(imgs[i], namesE[i], namesA[i], itemID[i], desc[i], type[i], price[i], priceS[i], priceL[i]));
                    break;

            }
        }
    }


}
