package com.lk.nxt_pharma.signup;

import android.content.Intent;
import android.os.AsyncTask;
import android.view.View;
import android.widget.Toast;


import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.lk.nxt_pharma.entity.Code;

import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;
import java.util.List;

public class AsyncTaskLogin extends AsyncTask<String,String,String> {

    private final View view;
    private String fullName,email,mobile,password,dob;

    public AsyncTaskLogin(View view) {
        this.view = view;

    }

    @Override
    protected String doInBackground(String... strings) {

        fullName=strings[0];
         email=strings[1];
         mobile=strings[2];
         password=strings[3];
         dob=strings[4];

        JsonObject jo = new JsonObject();
        jo.addProperty("fullName",strings[0]);
        jo.addProperty("email",strings[1]);
        jo.addProperty("mobile",strings[2]);
        jo.addProperty("password",strings[3]);
        jo.addProperty("dob",strings[4]);


        String jsonText = jo.toString();

       List<BasicNameValuePair> lst = new ArrayList<>();
        lst.add(new BasicNameValuePair("parameter",jsonText));


      String response = new HttpPostClient().sendHttpPostRequest(AppConfig.emailhostUrl,lst);



        return response;

    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);

        Toast.makeText(view.getContext(), ""+s, Toast.LENGTH_SHORT).show();
        Code code = new Gson().fromJson(s, Code.class);
        Intent intent=new Intent(view.getContext(),Verifyemail.class);

        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        intent.putExtra("code",code.getCode());
        intent.putExtra("fullName",fullName);
        intent.putExtra("email",email);
        intent.putExtra("mobile",mobile);
        intent.putExtra("password",password);
        intent.putExtra("dob",dob);


        view.getContext().startActivity(intent);



//        Register user = new Gson().fromJson(s,Register.class);
//        Log.d("keyyy",user.getKey());
//        try {
//
//            if(!user.getKey().equals("error")){
//                //intent
//                Intent intent = new Intent(view.getContext(), VerifyEmail.class);
//                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                intent.putExtra("firstname",user.getFirstname());
//                intent.putExtra("lastname",user.getLastname());
//                intent.putExtra("email",user.getEmail());
//                intent.putExtra("pw",user.getPassword());
//                intent.putExtra("mobile",user.getMobile());
//                intent.putExtra("verifykey",user.getKey());
//                view.getContext().startActivity(intent);
//            }else{
//                Toast.makeText(view.getContext(),"Network Error !",Toast.LENGTH_LONG).show();
//            }
//        }catch(Exception e){
//            Toast.makeText(view.getContext(),"Network Error !",Toast.LENGTH_LONG).show();
//
//        }
    }
}
