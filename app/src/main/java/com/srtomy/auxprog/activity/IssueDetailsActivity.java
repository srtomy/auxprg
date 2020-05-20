package com.srtomy.auxprog.activity;

import android.os.Bundle;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.srtomy.auxprog.R;
import com.srtomy.auxprog.activity.utils.Validator;

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

        initActions();
    }

    private void initActions(){
        btnSalvar.setOnClickListener(evt->{
            if(validar()){
                //Intent intent = new Intent(Iss.this, AnotacaoDetailsActivity.class);
                //intent.putExtra("anotacao", new Anotacao());
                //startActivityForResult(intent, NEW_WORD_ACTIVITY_REQUEST_CODE);
            }
        });
    }

    private void initLayout(){
        txtTitulo = findViewById(R.id.txtTituloIssue);
        txtCategoria = findViewById(R.id.txtCategoriaIssue);
        txtDtCriacao = findViewById(R.id.txtDtCriacaoIssue);
        txtDtSolucao = findViewById(R.id.txtDtSolucaoIssue);
        txtDescricao = findViewById(R.id.txtDescricaoIssue);
        txtSolucao = findViewById(R.id.txtSolucaoIssue);
        btnSalvar = findViewById(R.id.btnSalvar);
    }

    private boolean validar(){
        boolean txtTituloValid = Validator.validateNotNull(txtTitulo, "Titulo Invalido");
        boolean txtDtCriValid = Validator.validateNotNull(txtDescricao, "Titulo Invalido");
        boolean txtDtSolucaoVAlid = Validator.validateNotNull(txtDtSolucao, "Titulo Invalido");
        boolean txtDescricaoValid = Validator.validateNotNull(txtDescricao, "Titulo Invalido");

        return txtTituloValid & txtDtCriValid & txtDtSolucaoVAlid & txtDescricaoValid;
    }
}
