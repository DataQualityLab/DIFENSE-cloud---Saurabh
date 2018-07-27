package com.igorkh.trustcheck.securitycheck;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.TextView;

import static com.igorkh.trustcheck.securitycheck.MainActivity.MAX_SCORE;

public class SimulationActivity extends AppCompatActivity {

    CheckBox chkScr, chkUnkn, chkPta, chkDev, chkIntg, chkAct;
    RadioButton rbLatest, rbPrev, rbOld;
    TextView scrScr, scrOS, scrUnkn, scrPta, scrDev, scrIntg, scrAct, scrCompl, scrMax;
    Button btnClear;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simulation);

        setTitle(R.string.simTitle);

        initViews();
        evaluate();
    }

    private void initViews(){
        chkScr = (CheckBox)findViewById(R.id.chk_scr);
        chkUnkn = (CheckBox)findViewById(R.id.chk_unkn);
        chkPta = (CheckBox)findViewById(R.id.chk_pta);
        chkDev = (CheckBox)findViewById(R.id.chk_dev);
        chkIntg = (CheckBox)findViewById(R.id.chk_bit);
        chkAct = (CheckBox)findViewById(R.id.chk_act);

        rbLatest = (RadioButton)findViewById(R.id.rb_last);
        rbPrev = (RadioButton)findViewById(R.id.rb_prev);
        rbOld = (RadioButton)findViewById(R.id.rb_old);

        chkScr.setOnClickListener(chkClick);
        chkUnkn.setOnClickListener(chkClick);
        chkPta.setOnClickListener(chkClick);
        chkDev.setOnClickListener(chkClick);
        chkIntg.setOnClickListener(chkClick);
        chkAct.setOnClickListener(chkClick);
        rbLatest.setOnClickListener(chkClick);
        rbPrev.setOnClickListener(chkClick);
        rbOld.setOnClickListener(chkClick);

        scrScr  = (TextView)findViewById(R.id.scr_score);
        scrOS = (TextView)findViewById(R.id.os_score);
        scrUnkn = (TextView)findViewById(R.id.unkn_score);
        scrPta = (TextView)findViewById(R.id.pta_score);
        scrDev = (TextView)findViewById(R.id.dev_score);
        scrIntg = (TextView)findViewById(R.id.bit_score);
        scrAct = (TextView)findViewById(R.id.act_score);
        scrCompl = (TextView)findViewById(R.id.compl_score);
        scrCompl = (TextView)findViewById(R.id.compl_score);
        scrMax = (TextView)findViewById(R.id.max_score);

        btnClear = (Button)findViewById(R.id.but_clear);
//        btnClear.setVisibility(View.GONE);
        btnClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clear();
            }
        });

    }

    View.OnClickListener chkClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            evaluate();
        }
    };

    private void evaluate(){
        byte overallscore = 0;
        clear();


        if (chkScr.isChecked()) {
            scrScr.setText("1");
            overallscore++;
        }
        if(!chkDev.isChecked()){
            scrDev.setText("1");
            overallscore++;
        }
        if(!chkUnkn.isChecked()){
            scrUnkn.setText("1");
            overallscore++;
        }
        if(chkAct.isChecked()){
            scrAct.setText("1");
            overallscore++;
        }
        if(chkIntg.isChecked()){
            scrIntg.setText("1");
            overallscore++;
        }
        if(!chkPta.isChecked()){
            scrPta.setText("1");
            overallscore++;
        }
        if(rbLatest.isChecked()){
            scrOS.setText("2");
            overallscore = (byte)(overallscore + 2);
        }
        if(rbPrev.isChecked()){
            scrOS.setText("1");
            overallscore++;
        }
        if(rbOld.isChecked()){
            scrOS.setText("0");
        }

        scrCompl.setText(Byte.toString(overallscore));
        scrMax.setText(Byte.toString(MAX_SCORE));
    }

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

}
