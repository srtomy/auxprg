package com.srtomy.auxprog.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.core.content.ContextCompat;


import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.flexbox.FlexboxLayout;
import com.google.android.material.chip.Chip;
import com.srtomy.auxprog.Anotacao;
import com.srtomy.auxprog.R;
import com.srtomy.auxprog.activity.utils.Validator;


public class AnotacaoDetailsActivity extends AppCompatActivity {
    private Anotacao anotacao;

    private EditText txtTitulo;
    private EditText txtDescricao;
    private Button btnSalvar;
    private FlexboxLayout layoutCategorias;
    private EditText txtInputCategoria;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anotacao_details);

        initLayout();

        initActions();

        anotacao = (Anotacao) getIntent().getExtras().get("anotacao");
        setAnotacao(anotacao);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu_anotacao_details; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_anotacao_details, menu);

        return true;
    }

    private void initLayout(){
        layoutCategorias = findViewById(R.id.recipient_group_FL);
        txtTitulo = findViewById(R.id.txtTitulo);
        txtDescricao = findViewById(R.id.txtDescricao);
        txtInputCategoria = findViewById(R.id.recipient_input_ET);
        btnSalvar =findViewById(R.id.button_save);
    }

    private void initActions(){

        btnSalvar.setOnClickListener(evt->{
            Intent replyIntent = new Intent();

            if (validar()) {
                anotacao.setTitulo(txtTitulo.getText().toString());
                anotacao.setDescricao(txtDescricao.getText().toString());

                String categoriasStr = "";
                for(int i =0; i < layoutCategorias.getChildCount();i++) {
                    Object view = layoutCategorias.getFlexItemAt(i);

                    if (view instanceof Chip) {
                        categoriasStr += ((Chip) view).getText() + ";";
                    }
                }

                anotacao.setCategoria(categoriasStr);

                replyIntent.putExtra("anotacao",anotacao);
                setResult(RESULT_OK, replyIntent);

                finish();
            } else {
                 //setResult(RESULT_CANCELED, replyIntent);
            }
        });

        txtInputCategoria.setOnEditorActionListener((v, actionId, dd) -> {
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                CharSequence txtVal = v.getText();
                if (txtVal != null) {
                    addNewChip(txtVal.toString(), layoutCategorias);
                    txtInputCategoria.setText("");
                }

                return true;
            }
            return false;
        });
    }

    private boolean validar(){

        boolean tituloValid = Validator.validateNotNull(txtTitulo,"Titulo invalido");
        boolean descricaoValid = Validator.validateNotNull(txtDescricao,"Descrição invalida");

        return tituloValid && descricaoValid;
    }

    private void setAnotacao(Anotacao an){
        anotacao = an;

        txtDescricao.setText(anotacao.getDescricao());
        txtTitulo.setText(anotacao.getTitulo());
        for(String str : anotacao.getCategoria().split(";")){
            addNewChip(str, layoutCategorias);
        }
    }

    private void addNewChip(String person, FlexboxLayout chipGroup) {
        Chip chip = new Chip(this, null, R.style.Widget_MaterialComponents_Chip_Entry);
        chip.setText(person);
        chip.setCloseIcon(ContextCompat.getDrawable(this,R.drawable.ic_close_black_24dp));
        chip.setChipBackgroundColorResource(R.color.colorPrimary);
        chip.setCloseIconVisible(true);
        chip.setClickable(true);
        chip.setCheckable(false);
        chipGroup.addView(chip, chipGroup.getChildCount() - 1);
        chip.setOnCloseIconClickListener(evt-> chipGroup.removeView(chip));
    }
}
