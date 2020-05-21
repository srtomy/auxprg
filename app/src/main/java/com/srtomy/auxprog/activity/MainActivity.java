package com.srtomy.auxprog.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.srtomy.auxprog.R;

public class MainActivity extends AppCompatActivity {
    public static final int ANOTACAO_ACTIVITY_REQUEST_CODE = 1;
    public static final int ISSUE_ACTIVITY_REQUEST_CODE = 2;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        Button btnAnotacao = findViewById(R.id.btn_anotacao);
        btnAnotacao.setOnClickListener(evt->{
            Intent intent = new Intent(this, AnotacaoActivity.class);
            startActivityForResult(intent, ANOTACAO_ACTIVITY_REQUEST_CODE);
        });

        Button btnIssue = findViewById(R.id.btn_issue);
        btnIssue.setOnClickListener(evt->{
            Intent intent = new Intent(this, IssueActivity.class);
            startActivityForResult(intent, ISSUE_ACTIVITY_REQUEST_CODE);
        });
    }
}
