package com.sumavision.branch.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.sumavision.branch.R;

/**
 * Created by sharpay on 16-8-15.
 */
public class TestActivity extends Activity{
    EditText editText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        editText = (EditText) findViewById(R.id.editText);
        findViewById(R.id.play1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TestActivity.this, ProgramListActivity.class);
                startActivity(intent);
            }
        });
        findViewById(R.id.play2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LiveActivity.intentTo(TestActivity.this,editText.getText().toString(),true);
            }
        });
        findViewById(R.id.play3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LiveActivity.intentTo(TestActivity.this,editText.getText().toString(),false);
            }
        });
    }
}
