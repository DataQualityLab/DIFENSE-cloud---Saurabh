package com.igorkh.trustcheck.securitycheck;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;



public class RegisterActivity extends AppCompatActivity {

    private static final int MY_PERMISSIONS_REQUEST_READ_PHONE_STATE = 999;
    private EditText firstName, lastName, uName, pwd, cnfrmpwd;
    private CheckBox imeiPermission;
    private Button save;
    protected String imei;
    private ApiConnection1 connectionObj = new ApiConnection1();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        initViews();
    }

    public void initViews() {
        firstName       = findViewById(R.id.firstName);
        lastName        = findViewById(R.id.lname);
        uName           = findViewById(R.id.userName);
        pwd             = findViewById(R.id.password);
        cnfrmpwd        = findViewById(R.id.confirmPassword);
        imeiPermission  = (CheckBox) findViewById(R.id.usrImeiPmn);
        save            = findViewById(R.id.registerUser);
        save.setEnabled(false);

        imeiPermission.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked)
                {
                    try {
                        if (ContextCompat.checkSelfPermission(RegisterActivity.this,
                                Manifest.permission.READ_PHONE_STATE)
                                != PackageManager.PERMISSION_GRANTED) {
                            // Permission is not granted
                                ActivityCompat.requestPermissions(RegisterActivity.this,
                                        new String[]{Manifest.permission.READ_PHONE_STATE},
                                        MY_PERMISSIONS_REQUEST_READ_PHONE_STATE);

                                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                                // app-defined int constant. The callback method gets the
                                // result of the request.
                        } else {
                            // Permission has already been granted
                            //Extracting the imei number of the device
                            try{TelephonyManager telephonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
                                imei = telephonyManager.getDeviceId();
                                save.setEnabled(true);}catch(SecurityException e){
//                        System.out.println("Error: "+e.getMessage());
                                Toast.makeText(RegisterActivity.this, "Try again!" , Toast.LENGTH_SHORT).show();
                            }
                            save.setEnabled(true);
                        }
                    }catch(SecurityException e){
                        System.out.println("Error: "+e.getMessage());
                        Toast.makeText(RegisterActivity.this, "Try again!" , Toast.LENGTH_SHORT).show();
                    }
                }
                else
                    save.setEnabled(false);
            }
        });

        //When save button is clicked
        View.OnClickListener saveUser = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Check the firstname, lastname fields
                if(firstName.getText().length()==0 || lastName.getText().length()==0){
                    Toast.makeText(RegisterActivity.this,
                            "One of the fields is empty", Toast.LENGTH_SHORT).show();
                    return;
                }

                //Verifying the length of the password to be atleast 8 characters
                if (pwd.getText().length() < 8 || cnfrmpwd.getText().length() < 8) {
                    Toast.makeText(RegisterActivity.this,
                            "Password cannot be less than 8 characters", Toast.LENGTH_SHORT).show();
                    return;
                }

                //Confirming if both the password fields match
                if (pwd.getText() == cnfrmpwd.getText()) {
                    Toast.makeText(RegisterActivity.this,
                            "Passwords do not match. Try again", Toast.LENGTH_SHORT).show();
                    return;
                }
                //Pack user information in a JSON Object and send to API
                connectionObj.execute("");
            }
        };
        save.setOnClickListener(saveUser);
    }


    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_READ_PHONE_STATE: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    //Extracting the imei number of the device
                    try{TelephonyManager telephonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
                    imei = telephonyManager.getDeviceId();
                    save.setEnabled(true);}catch(SecurityException e){
//                        System.out.println("Error: "+e.getMessage());
                        Toast.makeText(RegisterActivity.this, "Try again!" , Toast.LENGTH_SHORT).show();
                    }
                } else {
                    save.setEnabled(false);
                }
                return;
            }
        }
    }


    class ApiConnection1 extends AsyncTask<String, Void,Boolean > {

        @Override
        protected Boolean doInBackground(String...str) {
            JSONObject userInfo = new JSONObject();
            try{
                userInfo.put("firstname",firstName.getText());
                userInfo.put("lastname",lastName.getText());
                userInfo.put("username",uName.getText());
                userInfo.put("password",pwd.getText());
                userInfo.put("imei",imei);
            }catch(JSONException e){
//                System.out.println(e.getMessage());
            }
            //send the user information
            try {
                URL postObj = new URL(LoginActivity.api_url+"users");
                HttpURLConnection conn = (HttpURLConnection) postObj.openConnection();
                conn.setRequestMethod("POST");
                conn.setRequestProperty("Content-Type", "application/json");
                conn.setDoOutput(true);
                OutputStream os = conn.getOutputStream();
                OutputStreamWriter osw = new OutputStreamWriter(os, "UTF-8");
                osw.write(userInfo.toString());
                osw.flush();
                osw.close();
                int responseCode = conn.getResponseCode();
                if(responseCode == HttpURLConnection.HTTP_CREATED){
                    BufferedReader in = new BufferedReader(new InputStreamReader(
                            conn.getInputStream()));
                    String inputLine;
                    StringBuffer response = new StringBuffer();
                    while ((inputLine = in.readLine()) != null){
                        response.append(inputLine);
                    }
                    in.close();
                    return true;
                }else{
//                    System.out.println(responseCode);
                }

            }catch (Exception e){
//                System.out.println("Exception in register: "+ e.getMessage());
            }
            return false;
        }

        @Override
        protected void onPostExecute(Boolean result) {
            super.onPostExecute(result);
            if(result)
                Toast.makeText(RegisterActivity.this,
                        "User created successfully", Toast.LENGTH_SHORT).show();
            else
                Toast.makeText(RegisterActivity.this,
                        "Try again!", Toast.LENGTH_SHORT).show();
            Intent intent1 = new Intent(RegisterActivity.this, LoginActivity.class );
            startActivity(intent1);
        }
    }
}
