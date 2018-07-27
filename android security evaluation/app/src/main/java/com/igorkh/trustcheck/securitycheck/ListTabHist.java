package com.igorkh.trustcheck.securitycheck;

/**
 * Created by wanis on 6/21/2018.
 */


import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;
import com.igorkh.trustcheck.securitycheck.UserInfo.UserDetails;
import org.json.JSONArray;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

public class ListTabHist extends Fragment{

    public Context context;
    public TableLayout scoreTable;
    private ApiConnectionForList nwObj;
    protected  static String responseString;
    private View rootView;
    public HashMap<String, String> scoreRecords;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.list_history, container, false);
        scoreTable = (TableLayout) rootView.findViewById(R.id.table_layout);
        nwObj = new ApiConnectionForList();
        nwObj.execute();
        return rootView;
    }

    public void scoreParser(){
        scoreRecords = new HashMap<String, String>();
        try {
            JSONObject js = new JSONObject(responseString);
            JSONArray value = js.getJSONArray("value");
            for(int index=0; index < value.length(); index++){
                JSONObject details = value.getJSONObject(index);
                init(index, details.get("timestamp").toString(),details.get("instanceScore").toString());
            }
            JSONObject details = value.getJSONObject(0);
        }catch(Exception e){
//            System.out.println(e.getMessage());
        }
    }

    public  void init(int index, String time, String score) throws ParseException {

        SimpleDateFormat sdf = new SimpleDateFormat("yyy-MM-dd'T'HH:mm:ss");
        Date d = sdf.parse(time);
        TableRow row1 = new TableRow(rootView.getContext());
        GradientDrawable gd = new GradientDrawable();
        gd.setColor(0x00);
        gd.setStroke(1, 0xFF000000);
        SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        TextView timestamp1 = new TextView(rootView.getContext());
        timestamp1.setText("   "+sdfDate.format(d)+"   ");
        timestamp1.setGravity(Gravity.CENTER);
        timestamp1.setTextSize(15);
        timestamp1.setBackground(gd);
        timestamp1.setPadding(5,0,0,0);

        TextView score1 = new TextView(rootView.getContext());
        score1.setText(score);
        score1.setGravity(Gravity.CENTER);
        score1.setTextSize(15);
        score1.setBackground(gd);
        score1.setPadding(5,0,0,0);

        row1.addView(timestamp1);
        row1.addView(score1);
        scoreTable.addView(row1);
    }

    class ApiConnectionForList extends AsyncTask<View, Void, Boolean> {

        @Override
        protected Boolean doInBackground(View...rootView) {

            JSONObject user1 = new JSONObject();
            try{
                //send the user information
                URL getObj              = new URL(LoginActivity.api_url+"users/"+UserDetails.uid
                        +"/scores/");
                HttpURLConnection conn  = (HttpURLConnection) getObj.openConnection();
                conn.setRequestMethod("GET");
                int responseCode        = conn.getResponseCode();
                if(responseCode == HttpURLConnection.HTTP_OK){
                    BufferedReader in   = new BufferedReader(new InputStreamReader(
                            conn.getInputStream()));
                    String inputLine;
                    StringBuffer response = new StringBuffer();
                    while ((inputLine = in.readLine()) != null){
                        response.append(inputLine);
                    }
                    in.close();
                    responseString      =  response.toString();
                    return true;
                }else{
                    responseString      = ""+ responseCode;
                }
            }catch (Exception e){
//                System.out.println("Exception in register: "+ e.getMessage());
            }
            return false;
        }

        @Override
        protected void onPostExecute(Boolean result){
            super.onPostExecute(result);
            scoreParser();
            if(!result)
                Toast.makeText(rootView.getContext(),
                        "Error!", Toast.LENGTH_SHORT).show();
            return;
        }
    }
}


