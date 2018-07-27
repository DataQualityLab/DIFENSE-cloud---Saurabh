package com.igorkh.trustcheck.securitycheck;

import android.app.Activity;
import android.content.Intent;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class DetailActivity extends AppCompatActivity {

    public final static String VIEWID_FIELD = "VIEWID_FIELD";

    TextView detailsText, linkTV;
    int viewid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);


        detailsText = (TextView)findViewById(R.id.detailsText);
        linkTV = (TextView)findViewById(R.id.detailsLink);
        linkTV.setOnClickListener(linkClick);

        Intent intent = getIntent();
        if (intent!=null){
            viewid = intent.getIntExtra(VIEWID_FIELD, 0);
        }

        if (viewid==0){
            detailsText.setText(getString(R.string.errDet));
            linkTV.setVisibility(View.INVISIBLE);
        }
        else{
            if (viewid==R.id.det_scr){
                setTitle(R.string.scrTitle);
                detailsText.setText(getString(R.string.scrDet));
            }
            else if (viewid==R.id.det_os){
                detailsText.setText(getString(R.string.osDet));
                setTitle(R.string.osTitle);
            }
            else if (viewid==R.id.det_unkn){
                detailsText.setText(getString(R.string.unknDet));
                setTitle(R.string.unknTitle);
            }
            else if (viewid==R.id.det_pta){
                detailsText.setText(getString(R.string.ptaDet));
                linkTV.setVisibility(View.GONE);
                setTitle(R.string.ptaTitle);
            }
            else if (viewid==R.id.det_dev){
                detailsText.setText(getString(R.string.devDet));
                setTitle(R.string.devTitle);
            }else if (viewid==R.id.det_bit){
                detailsText.setText(getString(R.string.bitDet));
                linkTV.setVisibility(View.INVISIBLE);
                setTitle(R.string.bitTitle);
            }
            else if (viewid==R.id.det_act){
                detailsText.setText(getString(R.string.actDet));
                linkTV.setVisibility(View.INVISIBLE);
                setTitle(R.string.actTitle);
            }
            else if(viewid==R.id.advice){
                linkTV.setVisibility(View.GONE);
                detailsText.setText(R.string.adviceText);
                setTitle(R.string.advTitle);
            }

        }
    }

    View.OnClickListener linkClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (viewid==R.id.det_scr){
                startActivity(new Intent(Settings.ACTION_SECURITY_SETTINGS));
            }
            else if (viewid==R.id.det_os){
                startActivity(new Intent(Settings.ACTION_DEVICE_INFO_SETTINGS));
            }
            else if (viewid==R.id.det_unkn){
                startActivity(new Intent(Settings.ACTION_SECURITY_SETTINGS));
            }
            else if (viewid==R.id.det_pta){
                startActivity(new Intent(Settings.ACTION_APPLICATION_DEVELOPMENT_SETTINGS));
            }
            else if (viewid==R.id.det_dev) {
                startActivity(new Intent(Settings.ACTION_APPLICATION_DEVELOPMENT_SETTINGS));
            }
        }
    };
}
