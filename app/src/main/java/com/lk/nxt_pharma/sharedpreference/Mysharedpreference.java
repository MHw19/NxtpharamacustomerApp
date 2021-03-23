package com.lk.nxt_pharma.sharedpreference;

import android.content.Context;
import android.content.SharedPreferences;

public class Mysharedpreference {

    public static final String MyPREFERENCES = " Mysharedpreference" ;


    static SharedPreferences sharedpreferences;

    public  static SharedPreferences getInstance(Context context){

      if(sharedpreferences ==null){
          sharedpreferences= context.getSharedPreferences(MyPREFERENCES,Context.MODE_PRIVATE);

      }

     return sharedpreferences;

    }





}
