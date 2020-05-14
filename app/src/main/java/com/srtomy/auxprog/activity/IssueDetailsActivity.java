package com.srtomy.auxprog.activity;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.srtomy.auxprog.R;

public class IssueDetailsActivity extends AppCompatActivity {
    private EditText txtTitulo;
    private EditText txtCategoria;
    private EditText txtDtCriacao;
    private EditText txtDtSolucao;
    private EditText txtDescricao;
    private EditText txtSolucao;
    private FloatingActionButton btnSalvar;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_issue_details);

        initLayout();


    }

    private void initLayout(){
        txtTitulo = findViewById(R.id.txtTituloIssue);
        txtCategoria = findViewById(R.id.txtCategoriaIssue);
        txtDtCriacao = findViewById(R.id.txtDtCriacaoIssue);
        txtDtSolucao = findViewById(R.id.txtDtSolucaoIssue);
        txtDescricao = findViewById(R.id.txtDescricaoIssue);
        txtSolucao = findViewById(R.id.txtSolucaoIssue);
        btnSalvar = findViewById(R.id.btnSalvarIssue);
    }
}
