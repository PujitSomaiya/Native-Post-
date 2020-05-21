package com.tatvasoft.nativepost.ui.main.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.tatvasoft.nativepost.R;
import com.tatvasoft.nativepost.ui.post.view.PostActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        startActivity(new Intent(this, PostActivity.class));
        finishAffinity();

    }
}
