package com.srtomy.auxprog.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import com.srtomy.auxprog.Anotacao;
import com.srtomy.auxprog.R;
import com.srtomy.auxprog.activity.utils.Validator;
import com.srtomy.auxprog.model.AnotacaoViewModel;

import java.time.LocalDateTime;

public class AnotacaoDetailsActivity extends AppCompatActivity {
    private Anotacao anotacao;

    private EditText txtTitulo;
    private EditText txtDescricao;
    private EditText txtCategoria;
    private Button btnSalvar;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anotacao_details);

        initLayout();

        initActions();

        anotacao = (Anotacao) getIntent().getExtras().get("anotacao");
        setAnotacao(anotacao);
    }

    private void initLayout(){
        txtTitulo = findViewById(R.id.txtTitulo);
        txtCategoria = findViewById(R.id.txtCategoria);
        txtDescricao = findViewById(R.id.txtDescricao);

        btnSalvar =findViewById(R.id.button_save);
    }

    private void initActions(){

        btnSalvar.setOnClickListener(evt->{
            Intent replyIntent = new Intent();

            if (validar()) {
                anotacao.setTitulo(txtTitulo.getText().toString());
                anotacao.setCategoria(txtCategoria.getText().toString());
                anotacao.setDescricao(txtDescricao.getText().toString());

                replyIntent.putExtra("anotacao",anotacao);
                setResult(RESULT_OK, replyIntent);

                finish();
            } else {
                 //setResult(RESULT_CANCELED, replyIntent);
            }

        });
    }

    private boolean validar(){

        boolean tituloValid = Validator.validateNotNull(txtTitulo,"Titulo invalido");
        boolean categoriaValid = Validator.validateNotNull(txtCategoria,"Categoria invalida");
        boolean descricaoValid = Validator.validateNotNull(txtDescricao,"Descrição invalida");

        return tituloValid && categoriaValid && descricaoValid;
    }

    private void setAnotacao(Anotacao an){
        anotacao = an;

        txtDescricao.setText(anotacao.getDescricao());
        txtTitulo.setText(anotacao.getTitulo());
        txtCategoria.setText(anotacao.getCategoria());
    }
}
