package com.example.breakingbad;

import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class DetailActivity extends AppCompatActivity {
    private TextView mName;
    private TextView mNick;
    private ImageView mImage;
    private TextView mStatus;
    private TextView mBirth;
    private TextView mOccupation;
    private TextView mAppearance;
    private TextView mInformation;
    private final String TAG = getClass().getSimpleName();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        Log.d("DetailActivity", "onCreate aangeroepen");
        mName = (TextView) findViewById(R.id.character_detail_name);
        mNick = (TextView) findViewById(R.id.character_detail_nick);
        mImage = (ImageView) findViewById(R.id.character_detail_image);
        mStatus = (TextView) findViewById(R.id.character_detail_status);
        mBirth = (TextView) findViewById(R.id.character_detail_birth);
        mOccupation = (TextView) findViewById(R.id.character_detail_occupation);
        mAppearance = (TextView) findViewById(R.id.character_detail_appearance);
        mInformation = (TextView) findViewById(R.id.character_detail_info);
        mName.setText(getIntent().getStringExtra("NAME"));
        mNick.setText(mNick.getText() + ": "+ getIntent().getStringExtra("NICK"));
        Picasso
                .get()
                .load(getIntent().getStringExtra("IMAGE"))
                .resize(700, 700)
                .into(mImage);
        mInformation.setText(mInformation.getText());
        mStatus.setText("Status: " + getIntent().getStringExtra("STATUS"));
        mBirth.setText("Birthday: "+ getIntent().getStringExtra("BIRTH"));
        ArrayList<String> occ = getIntent().getStringArrayListExtra("OCCUPATION");
        String x = "";
        for (int i = 0; i < occ.size(); i++) {
            if(i < occ.size()-1){
                x += occ.get(i) + ", ";
            }else {
                x += occ.get(i);
            }
        }
        mOccupation.setText("Occupation: "+ x);
        ArrayList<Integer> appear = getIntent().getIntegerArrayListExtra("APPEARANCES");
        String s = "";
        for (int i = 0; i < appear.size(); i++) {
            if(i < appear.size()-1){
            s += appear.get(i) + ", ";
            }else {
                s += appear.get(i);
            }
        }
        mAppearance.setText("Appearances: " + s);


    }

}