package com.example.administrator.myapplicationpinnedlist;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    Button one,two;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        one= (Button) findViewById(R.id.one);
        two= (Button) findViewById(R.id.two);

        one.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intet=new Intent();
                intet.setClass(MainActivity.this,PinnedSectionListActivity.class);
                startActivity(intet);
            }
        });
        two.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intet=new Intent();
                intet.setClass(MainActivity.this,TwoActivity.class);
                startActivity(intet);
            }
        });



    }
}
