package com.igorkh.trustcheck.securitycheck;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.igorkh.trustcheck.securitycheck.UserInfo.UserDetails;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by wanis on 6/21/2018.
 */

public class GraphTabHist extends Fragment {

    public  static  LineGraphSeries<DataPoint>  plot;
    public  static  GraphView                   graph;
    private static  String                      responseString;
    private         ApiConnectionForGraph       gObj;
    private         View                        rootView;
    private         ArrayList<Integer>          timeList  = new ArrayList<Integer>();
    private         ArrayList<Integer>          scoreList = new ArrayList<Integer>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.graph_history, container, false);

        //To fetch the user scores
        gObj = new ApiConnectionForGraph();
        gObj.execute();

        //Radio button to switch between the X-axis range of graph
        RadioGroup radioGroup = (RadioGroup) rootView.findViewById(R.id.scale);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                graph.removeAllSeries();
                timeList.clear();
                scoreList.clear();
                parser(checkedId);
            }
        });
        return rootView;
    }

    public  void parser(int choice){

            graph = (GraphView) rootView.findViewById(R.id.graph);
            plot = new LineGraphSeries<DataPoint>();
            try{
                JSONObject js = new JSONObject(responseString);
                JSONArray value = js.getJSONArray("value");
                for(int index=0; index < value.length(); index++)
                {
                    JSONObject details = value.getJSONObject(index);
                    SimpleDateFormat sdf = new SimpleDateFormat("yyy-MM-dd'T'HH:mm:ss");
                    Date d = sdf.parse(details.get("timestamp").toString());
                    Calendar cal = Calendar.getInstance();
                    cal.setTime(d);
                    scoreList.add(Integer.parseInt(details.get("instanceScore").toString()));
                    //Hourly representation
                    if (choice == 2131230837) {
                        int hour = cal.get(Calendar.HOUR_OF_DAY);
                        GraphTabHist.plot.appendData(new DataPoint(hour, Integer.parseInt(details.get("instanceScore").toString())),
                                true, 500);
                        graph.getViewport().setXAxisBoundsManual(true);
                        graph.getViewport().setMinX(0);
                        graph.getViewport().setMaxX(24);
                        timeList.add(hour);
                    } else if (choice == 2131230795) {
                        //Date-wise representation
                        int dateValue = cal.get(Calendar.DATE);
                        GraphTabHist.plot.appendData(new DataPoint(dateValue, Integer.parseInt(details.get("instanceScore").toString())),
                                true, 500);
                        graph.getViewport().setXAxisBoundsManual(true);
                        graph.getViewport().setMinX(0);
                        graph.getViewport().setMaxX(31);
                        timeList.add(dateValue);
                    } else {
                        //Monthly representation
                        int month = cal.get(Calendar.MONTH);
                        GraphTabHist.plot.appendData(new DataPoint(month, Integer.parseInt(details.get("instanceScore").toString())),
                                true, 500);
                        graph.getViewport().setXAxisBoundsManual(true);
                        graph.getViewport().setMinX(1);
                        graph.getViewport().setMaxX(12);
                        timeList.add(month);
                    }
                }
                //Checking for atleast 5 unique values on both axes to plot the graph
                Set<Integer> timeSet = new HashSet<Integer>(timeList);
                Set<Integer> scoreSet = new HashSet<Integer>(scoreList);
                //If not then show message to the user
                if(scoreSet.size() < 5 || timeSet.size() < 5)
                    Toast.makeText(rootView.getContext(),
                            "Not enough score to plot graph", Toast.LENGTH_LONG).show();
                //Configuring the graph
                graph.addSeries(GraphTabHist.plot);
                graph.getViewport().setYAxisBoundsManual(true);
                graph.setTitle("Score Comparison");
                graph.getViewport().setMinY(0);
                graph.getViewport().setMaxY(8);
                graph.getViewport().setScrollable(true); // enables horizontal scrolling
                graph.getViewport().setScrollableY(true); // enables vertical scrolling
            }catch(Exception e) {
//            System.out.println("E:"+ e.getMessage());
        }
    }

    class ApiConnectionForGraph extends AsyncTask<View, Void, Boolean> {


        @Override
        protected Boolean doInBackground(View...rootView) {
            try{
                //Request for user's score
                URL getObj = new URL(LoginActivity.api_url +"users/"+ UserDetails.uid+"/scores/");
                HttpURLConnection conn = (HttpURLConnection) getObj.openConnection();
                conn.setRequestMethod("GET");
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
                    responseString =  response.toString();
                    return true;
                }else
                    responseString = ""+ responseCode;
            }catch (Exception e){}
            return false;
        }

        @Override
        protected void onPostExecute(Boolean result){
            super.onPostExecute(result);
            if(result)
                //Success
                parser(2131230795);
            else
                //Error
                Toast.makeText(rootView.getContext(),
                        "Error occured!", Toast.LENGTH_LONG).show();
        }
    }
}