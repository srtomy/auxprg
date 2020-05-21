package com.srtomy.auxprog.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.srtomy.auxprog.Issue;
import com.srtomy.auxprog.R;
import com.srtomy.auxprog.activity.utils.Validator;

public class IssueDetailsActivity extends AppCompatActivity {
    private Issue issue;

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

        issue = (Issue) getIntent().getExtras().get("issue");
        setIssue(issue);
    }

    private void initActions(){

        btnSalvar.setOnClickListener(evt->{
            if(validar()){
                issue.setTitulo(txtTitulo.getText().toString());
                issue.setCategorias(txtCategoria.getText().toString());
                issue.setDtCriacao(txtDtCriacao.getText().toString());
                issue.setDtSolucao(txtDtSolucao.getText().toString());
                issue.setDescricao(txtDescricao.getText().toString());
                issue.setSolucao(txtSolucao.getText().toString());


                Intent replyIntent = new Intent();
                replyIntent.putExtra("issue",issue);
                setResult(RESULT_OK, replyIntent);
                finish();

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
        btnSalvar = findViewById(R.id.btnSalvarIssue);
    }

    private boolean validar(){
        boolean txtTituloValid = Validator.validateNotNull(txtTitulo, "Titulo Invalido");
        boolean txtDtCriValid = Validator.validateNotNull(txtDescricao, "Titulo Invalido");
        boolean txtDtSolucaoVAlid = Validator.validateNotNull(txtDtSolucao, "Titulo Invalido");
        boolean txtDescricaoValid = Validator.validateNotNull(txtDescricao, "Titulo Invalido");

        return txtTituloValid & txtDtCriValid & txtDtSolucaoVAlid & txtDescricaoValid;
    }

    private void setIssue(Issue iss){
        this.issue = iss;

        txtTitulo.setText(issue.getTitulo());
        txtCategoria.setText(issue.getCategorias());
        txtDtCriacao.setText(issue.getDtCriacao());
        txtDtSolucao.setText(issue.getDtSolucao());
        txtDescricao.setText(issue.getDescricao());
        txtSolucao.setText(issue.getSolucao());
    }
}
