package com.igorkh.trustcheck.securitycheck;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;
import com.igorkh.trustcheck.securitycheck.UserInfo.UserDetails;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

public class LoginActivity extends AppCompatActivity {

    protected static final String api_url = "http://androidsystemsecurityevaluationapi.azurewebsites.net/api/";
    EditText usrName;
    EditText password;
    Button btnLogIn, btnReg;
//    ProgressBar loginLoad;
    boolean loginSuccessful = false;
    ApiConnection connectionObj = new ApiConnection();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initView();
    }


    private void initView(){
        usrName = findViewById(R.id.userName);
        password = findViewById(R.id.password);
        btnLogIn = findViewById(R.id.login);
        btnReg = findViewById(R.id.register);
//        loginLoad = findViewById(R.id.loadLogin);
//        loginLoad.setVisibility(View.INVISIBLE);

        View.OnClickListener regButClick = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(LoginActivity.this, RegisterActivity.class );
                startActivity(intent1);
            }
        };
        btnReg.setOnClickListener(regButClick);

        View.OnClickListener loginButClick = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String uName = usrName.getText().toString();
                String pwd = password.getText().toString();
                Log.d("Popup", "uname=" + uName.length());
                if (uName.length() != 0 && pwd.length() != 0){
                    connectionObj.execute(uName, pwd);
                }
                btnLogIn.setEnabled(false);
            }
        };
        btnLogIn.setOnClickListener(loginButClick);
    }

    class ApiConnection extends AsyncTask<String, Void, Boolean > {

        String responseString;

        @Override
        protected Boolean doInBackground(String...str) {

            //Creating Test JSON Object
            JSONObject user1 = new JSONObject();
            try{
                user1.put("username", str[0]);
                user1.put("password", str[1]);
                //send the user information
                try {
                    URL postObj = new URL(api_url+"users/login");
                    HttpURLConnection conn = (HttpURLConnection) postObj.openConnection();
                    conn.setRequestMethod("POST");
                    conn.setRequestProperty("Content-Type", "application/json");
                    conn.setDoOutput(true);
                    OutputStream os = conn.getOutputStream();
                    OutputStreamWriter osw = new OutputStreamWriter(os, "UTF-8");
                    osw.write(user1.toString());
                    osw.flush();
                    osw.close();
                    int responseCode = conn.getResponseCode();
                    if(responseCode == HttpURLConnection.HTTP_OK){
                        BufferedReader in = new BufferedReader(new InputStreamReader(
                                conn.getInputStream()));
                        String inputLine;
                        StringBuffer response = new StringBuffer();
                        while ((inputLine = in.readLine()) != null){
                            response.append(inputLine);
                        }
                        in.close();
                        responseString = response.toString();
                    }else{
                        return false;
                    }
                }catch (Exception e){
//                    System.out.println("Exception in register: "+ e.getMessage());
                }

            String uid = responseString.substring(responseString.indexOf(":")+ 2,
                    responseString.indexOf(",") -1);
            String userType = responseString.substring(responseString.indexOf("p")+ 5,
                    responseString.indexOf("}") -1);
            UserDetails.username = str[0];
            UserDetails.userType = userType;
            UserDetails.uid = uid;
            }catch(JSONException e){
                e.printStackTrace();
//              System.out.println("JSON Exceptiom: "+ e.getMessage());
            }
            return true;
        }

        @Override
        protected void onPostExecute(Boolean result){
            super.onPostExecute(result);
//            loginLoad.setVisibility(View.GONE);
            btnLogIn.setEnabled(true);
            if(result) {
                try
                {
                        loginSuccessful = true;
                    // Switching over to MainActivity
                    Intent intent1 = new Intent(LoginActivity.this, MainActivity.class );
                    startActivity(intent1);
                }
                catch (Exception e){
//                    System.out.println("Toast error: "+ e.getMessage());
                }
            }
            else
                Toast.makeText(LoginActivity.this, "Try again", Toast.LENGTH_SHORT).show();
        }
    }
}
