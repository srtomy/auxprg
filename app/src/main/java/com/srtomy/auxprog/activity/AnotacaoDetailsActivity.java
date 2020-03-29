package com.srtomy.auxprog.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.srtomy.auxprog.R;
import com.srtomy.auxprog.Anotacao;
import com.srtomy.auxprog.model.AnotacaoViewModel;

public class AnotacaoDetailsActivity extends AppCompatActivity {
    private AnotacaoViewModel mWordViewModel;

    public static final String EXTRA_REPLY = "com.example.android.wordlistsql.REPLY";

    private EditText mEditWordView;
    private EditText txtTitulo;
    private EditText txtDescricao;
    private EditText txtCategoria;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anotacao_details);
        mEditWordView = findViewById(R.id.txtTitulo);

        // Get a new or existing ViewModel from the ViewModelProvider.
        mWordViewModel = new ViewModelProvider(this).get(AnotacaoViewModel.class);

        final Button button = findViewById(R.id.button_save);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent replyIntent = new Intent();
                if (TextUtils.isEmpty(mEditWordView.getText())) {
                    setResult(RESULT_CANCELED, replyIntent);
                } else {
                    Anotacao anotacao = new Anotacao(mEditWordView.getText().toString());
                    replyIntent.putExtra("anotacao",anotacao);
                    setResult(RESULT_OK, replyIntent);
                }
                finish();
            }
        });
    }

    private void initLayout(){

    }
}
