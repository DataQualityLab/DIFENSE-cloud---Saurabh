package com.igorkh.trustcheck.securitycheck;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;
import com.igorkh.trustcheck.securitycheck.UserInfo.UserDetails;
import com.igorkh.trustcheck.securitychecklibrary.CheckResults;
import com.igorkh.trustcheck.securitychecklibrary.SecurityCheckLib;
import com.igorkh.trustcheck.securitychecklibrary.SecurityLibResult;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    UserDetails userInfo;  // Object containing user id and user information
    static boolean saveScore = false;

    public ApiConnectionForScore connObj;
    final static byte MAX_SCORE = 8;

    TextView detScr, detOS, detUnkn, detPta, detDev, detIntg, detAct, detAdvice;
    TextView scrScr, scrOS, scrUnkn, scrPta, scrDev, scrIntg, scrAct, scrCompl, scrMax;
    CheckBox chkScr, chkOS, chkUnkn, chkPta, chkDev, chkIntg, chkAct, chkCompl;
    Button btnStart, btnSimul, btnClear, btnHist;
    ProgressBar progress;
    ScrollView mainScroll;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        userInfo = new UserDetails();
        Toast.makeText(MainActivity.this, "Welcome " + UserDetails.username, Toast.LENGTH_SHORT).show();
        connObj = new ApiConnectionForScore();
        initViews();







//        SecurityCheckLib securityLib = SecurityCheckLib.getInstance();
//        securityLib.setActivity(this);

//        int t = securityLib.androidVersion();

        //final TextView label = (TextView)findViewById(R.id.text);



        //securityLib.isHarmfullApp(resultlistener);
        //securityLib.attestDevice(API_KEY,resultlistener);
        //securityLib.comprehensiveCheck(API_KEY,resultlistener);
    }



    private void initViews(){

        detScr = (TextView)findViewById(R.id.det_scr);
        detOS = (TextView)findViewById(R.id.det_os);
        detUnkn = (TextView)findViewById(R.id.det_unkn);
        detPta = (TextView)findViewById(R.id.det_pta);
        detDev = (TextView)findViewById(R.id.det_dev);
        detIntg = (TextView)findViewById(R.id.det_bit);
        detAct = (TextView)findViewById(R.id.det_act);
        detAdvice  = (TextView)findViewById(R.id.advice);

        detScr.setOnClickListener(detailsClick);
        detOS.setOnClickListener(detailsClick);
        detUnkn.setOnClickListener(detailsClick);
        detPta.setOnClickListener(detailsClick);
        detDev.setOnClickListener(detailsClick);
        detIntg.setOnClickListener(detailsClick);
        detAct.setOnClickListener(detailsClick);
        detAdvice.setOnClickListener(detailsClick);

        scrScr  = (TextView)findViewById(R.id.scr_score);
        scrOS = (TextView)findViewById(R.id.os_score);
        scrUnkn = (TextView)findViewById(R.id.unkn_score);
        scrPta = (TextView)findViewById(R.id.pta_score);
        scrDev = (TextView)findViewById(R.id.dev_score);
        scrIntg = (TextView)findViewById(R.id.bit_score);
        scrAct = (TextView)findViewById(R.id.act_score);
        scrCompl = (TextView)findViewById(R.id.compl_score);
        scrMax = (TextView)findViewById(R.id.max_score);

        chkScr = (CheckBox)findViewById(R.id.chk_scr);
        chkOS = (CheckBox)findViewById(R.id.chk_os);
        chkUnkn = (CheckBox)findViewById(R.id.chk_unkn);
        chkPta = (CheckBox)findViewById(R.id.chk_pta);
        chkDev = (CheckBox)findViewById(R.id.chk_dev);
        chkIntg = (CheckBox)findViewById(R.id.chk_bit);
        chkAct = (CheckBox)findViewById(R.id.chk_act);
        chkCompl = (CheckBox)findViewById(R.id.chk_compl);

        chkScr.setOnCheckedChangeListener(chkListener);
        chkOS.setOnCheckedChangeListener(chkListener);
        chkUnkn.setOnCheckedChangeListener(chkListener);
        chkPta.setOnCheckedChangeListener(chkListener);
        chkDev.setOnCheckedChangeListener(chkListener);
        chkIntg.setOnCheckedChangeListener(chkListener);
        chkAct.setOnCheckedChangeListener(chkListener);
        //chkCompl.setOnCheckedChangeListener(chkComplListener);
        chkCompl.setOnClickListener(complClickLstnr);

        progress = (ProgressBar)findViewById(R.id.progressbar);
        mainScroll = (ScrollView)findViewById(R.id.mainScroll);

        btnStart = (Button)findViewById(R.id.but_start);
        btnSimul = (Button)findViewById(R.id.but_sim);
        btnClear = (Button)findViewById(R.id.but_clear);
        btnHist  = (Button)findViewById(R.id.but_history);

        //When clear button is clicked
        View.OnClickListener clearButClick = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clear();
                btnClear.setEnabled(false);
            }
        };

        //When start button is clicked
        View.OnClickListener startButClick = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Initialize score object to store the score details
                saveScore =false;
                //Start evaluation
                startEval();


            }
        };

        //When history button is clicked
        View.OnClickListener histButClick = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intenthist = new Intent(MainActivity.this, HistoryActivity.class );
                startActivity(intenthist);
            }
        };

        btnClear.setOnClickListener(clearButClick);
        btnStart.setOnClickListener(startButClick);
        btnHist.setOnClickListener(histButClick);



        btnSimul.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, SimulationActivity.class));
            }
        });

    }

    private void butSwitch(boolean state){
        btnClear.setEnabled(state);
        btnStart.setEnabled(state);
        btnSimul.setEnabled(state);
    }

    View.OnClickListener detailsClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(MainActivity.this,DetailActivity.class);
            intent.putExtra(DetailActivity.VIEWID_FIELD,v.getId());
            startActivity(intent);
        }
    };

    private void startEval(){

        switchToScoreView(false);

        butSwitch(false);

        final SecurityCheckLib securityLib = SecurityCheckLib.getInstance();
        securityLib.setActivity(this);
        clear();

        final String API_KEY = getString(R.string.api_key);

        // Complex scanning
        if (chkCompl.isChecked()){
//            SecurityCheckLib securityLib = SecurityCheckLib.getInstance();
//            securityLib.setActivity(this);

            SecurityLibResult compResultListener = new SecurityLibResult() {
                @Override
                public void onResultRecieved(CheckResults result) {
                    //label.setText(result.getJSONresult().toString());
                    butSwitch(true);
                    setScores(result);
                    switchToScoreView(true);
                }

                @Override
                public void onBooleanCheckResult(Boolean result) {
                    int t=0;
                }

                @Override
                public void onErrorOccured(String error) {
                    //label.setText(error);
                    butSwitch(true);
                    switchToScoreView(true);
                }
            };

            securityLib.comprehensiveCheck(API_KEY,compResultListener);
        }
        //Partial scanning
        else{
            if (chkScr.isChecked()){

                if (securityLib.isScreenLock()) {
                    scrScr.setText("1");
                }
            }

            if (chkOS.isChecked()){
                String osValue = Byte.toString(securityLib.androidVersion());
                scrOS.setText(osValue);
            }

            if (chkUnkn.isChecked()){

                if (!securityLib.isUnknownSources()) {
                    scrUnkn.setText("1");
                }
            }

            if (chkDev.isChecked()){

                if (!securityLib.isDeveloperMenu()) {
                    scrDev.setText("1");
                }
            }

            if (chkPta.isChecked() || chkAct.isChecked() || chkIntg.isChecked()){

                final SecurityLibResult bitResultListener = new SecurityLibResult() {
                    @Override
                    public void onResultRecieved(CheckResults result) {

                        if(chkAct.isChecked()){
                            if(result.isAct()){
                                scrAct.setText("1");
                            }
                        }

                        if(chkIntg.isChecked()){
                            if(result.isBit()) {
                                scrIntg.setText("1");
                            }
                        }

                        butSwitch(true);
                        switchToScoreView(true);
                        partialTestCalc();


                    }

                    @Override
                    public void onBooleanCheckResult(Boolean result) {}

                    @Override
                    public void onErrorOccured(String error) {
                        //label.setText(error);
                        butSwitch(true);
                        switchToScoreView(true);
                        partialTestCalc();
                    }
                };

                SecurityLibResult ptaResultListener = new SecurityLibResult() {
                    @Override
                    public void onResultRecieved(CheckResults result) {

                    }

                    @Override
                    public void onBooleanCheckResult(Boolean result) {

                        if (!result)
                        {
                            scrPta.setText("1");
                        }

                        if (chkAct.isChecked() || chkIntg.isChecked()){
                            securityLib.attestDevice(API_KEY,bitResultListener);
                        }
                        else{
                            butSwitch(true);
                            switchToScoreView(true);
                            partialTestCalc();
                        }
                    }

                    @Override
                    public void onErrorOccured(String error) {
                        if (chkAct.isChecked() || chkIntg.isChecked()){
                            securityLib.attestDevice(API_KEY,bitResultListener);
                        }
                        else{
                            butSwitch(true);
                            switchToScoreView(true);
                            partialTestCalc();
                        }
                    }
                };

                if (chkPta.isChecked()){
                    securityLib.isHarmfullApp(ptaResultListener);
                }
                else {
                    securityLib.attestDevice(API_KEY,bitResultListener);
                }
            }
            else{
                butSwitch(true);
                switchToScoreView(true);
                partialTestCalc();
            }
        }


    }

    private void partialTestCalc(){

        byte maxScore = 0;
        byte score = 0;
        if (chkScr.isChecked()){
            maxScore++;
            score = (byte)(score + Byte.valueOf(scrScr.getText().toString()));
        }

        if (chkOS.isChecked()){
            maxScore++;
            maxScore++;
            score = (byte)(score + Byte.valueOf(scrOS.getText().toString()));
        }

        if (chkUnkn.isChecked()){
            maxScore++;
            score = (byte)(score + Byte.valueOf(scrUnkn.getText().toString()));
        }

        if (chkDev.isChecked()){
            maxScore++;
            score = (byte)(score + Byte.valueOf(scrDev.getText().toString()));
        }

        if (chkAct.isChecked()){
            maxScore++;
            score = (byte)(score + Byte.valueOf(scrAct.getText().toString()));
        }

        if (chkPta.isChecked()){
            maxScore++;
            score = (byte)(score + Byte.valueOf(scrPta.getText().toString()));
        }

        if (chkIntg.isChecked()){
            maxScore++;
            score = (byte)(score + Byte.valueOf(scrIntg.getText().toString()));
        }

        scrCompl.setText(Byte.toString(score));
        scrMax.setText(Byte.toString(maxScore));

    }

    private void switchToScoreView(boolean state){
        if (state){
            mainScroll.setVisibility(View.VISIBLE);
            progress.setVisibility(View.GONE);
            JSONObject scoreJsonObj = new JSONObject();
            try{
                scoreJsonObj.put("instanceScore", scrCompl.getText());
                scoreJsonObj.put("screenLock", scrScr.getText());
                scoreJsonObj.put("os", scrOS.getText());
                scoreJsonObj.put("unknownSources", scrUnkn.getText());
                scoreJsonObj.put("harmfulApps", scrPta.getText());
                scoreJsonObj.put("devOpt", scrDev.getText());
                scoreJsonObj.put("integrity", scrIntg.getText());
                scoreJsonObj.put("compatibility", scrAct.getText());
            }catch(JSONException e){
                e.printStackTrace();
//                System.out.println("JSON Exceptiom: "+ e.getMessage());
            }

            //Send scores only if it is a complex evaluation
            if(chkCompl.isChecked()) {
                //Add delay for user to see score first
                final Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        //Check if the user wants to update the score in the database
                        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                        builder = builder.setMessage("Would you like to store the score details for future reference?")
                                .setCancelable(true)
                                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        //Creating Test JSON Object
                                        connObj.execute(scrCompl.getText().toString(), scrScr.getText().toString()
                                                , scrOS.getText().toString(), scrUnkn.getText().toString()
                                                , scrPta.getText().toString(), scrDev.getText().toString(),
                                                scrIntg.getText().toString(), scrAct.getText().toString());
                                        saveScore = true;
                                    }
                                })
                                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        dialog.cancel();
                                    }
                                });
                        AlertDialog alert = builder.create();
                        alert.show();
                    }
                }, 2000);
            }


        }
        else {
            mainScroll.setVisibility(View.INVISIBLE);
            progress.setVisibility(View.VISIBLE);
        }
    }

    private void setScores(CheckResults result){

        byte overallscore = 0;
//        byte maxScore = 0;


        if (result.isScreenlock()) {
            scrScr.setText("1");
            overallscore++;
        }
        if(!result.isDevmenu()){
            scrDev.setText("1");
            overallscore++;
        }
        if(!result.isUnknsrc()){
            scrUnkn.setText("1");
            overallscore++;
        }
        if(result.isAct()){
            scrAct.setText("1");
            overallscore++;
        }
        if(result.isBit()){
            scrIntg.setText("1");
            overallscore++;
        }
        if(!result.isPha()){
            scrPta.setText("1");
            overallscore++;
        }


        overallscore = (byte)(overallscore + result.getAndroidVersion());
        ////********************************************Set score on display
        scrOS.setText(Byte.toString(result.getAndroidVersion()));
        scrCompl.setText(Byte.toString(overallscore));
        scrMax.setText(Byte.toString(MAX_SCORE));
    }

    View.OnClickListener complClickLstnr = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            boolean isChecked = chkCompl.isChecked();

            chkAct.setChecked(isChecked);
            chkDev.setChecked(isChecked);
            chkIntg.setChecked(isChecked);
            chkOS.setChecked(isChecked);
            chkPta.setChecked(isChecked);
            chkScr.setChecked(isChecked);
            chkUnkn.setChecked(isChecked);

        }
    };

    CompoundButton.OnCheckedChangeListener chkListener = new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

            if(isChecked){
                if(chkAct.isChecked() && chkDev.isChecked() && chkIntg.isChecked() && chkOS.isChecked() && chkPta.isChecked()
                        && chkScr.isChecked() && chkUnkn.isChecked())
                    chkCompl.setChecked(true);

            }
            else{
                chkCompl.setChecked(false);
            }
        }

    };

    private void clear(){
        scrScr.setText("0");
        scrOS.setText("0");
        scrUnkn.setText("0");
        scrPta.setText("0");
        scrDev.setText("0");
        scrIntg.setText("0");
        scrAct.setText("0");
        scrCompl.setText("0");
        scrMax.setText("0");
    }



    class ApiConnectionForScore extends AsyncTask<String, Void, Boolean > {

        @Override
        protected Boolean doInBackground(String...str) {
            JSONObject user1 = new JSONObject();
            try{
                user1.put("instanceScore", scrCompl.getText());
                user1.put("screenLock", scrScr.getText());
                user1.put("os", scrOS.getText());
                user1.put("unknownSources", scrUnkn.getText());
                user1.put("harmfulApps", scrPta.getText());
                user1.put("devOpt", scrDev.getText());
                user1.put("integrity", scrIntg.getText());
                user1.put("compatibility", scrAct.getText());
                //send the user information
                URL postObj = new URL(LoginActivity.api_url+"users/"+UserDetails.uid+"/scores/");
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
                    return false;
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
                Toast.makeText(MainActivity.this, "Score recorded", Toast.LENGTH_SHORT).show();
            else
                Toast.makeText(MainActivity.this, "Error occurred while saving the score", Toast.LENGTH_SHORT).show();

        }
    }
}
