package com.srtomy.auxprog.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.google.android.flexbox.FlexboxLayout;
import com.google.android.material.chip.Chip;
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
    private FlexboxLayout layoutCategorias;
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

        txtCategoria.setOnEditorActionListener((v, actionId, dd) -> {
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                CharSequence txtVal = v.getText();
                if (txtVal != null) {
                    addNewChip(txtVal.toString(), layoutCategorias);
                    txtCategoria.setText("");
                }

                return true;
            }
            return false;
        });

        btnSalvar.setOnClickListener(evt->{
            if(validar()){
                issue.setTitulo(txtTitulo.getText().toString());
                issue.setDtCriacao(txtDtCriacao.getText().toString());
                issue.setDtSolucao(txtDtSolucao.getText().toString());
                issue.setDescricao(txtDescricao.getText().toString());
                issue.setSolucao(txtSolucao.getText().toString());

                String categoriasStr = "";
                for(int i =0; i < layoutCategorias.getChildCount();i++) {
                    Object view = layoutCategorias.getFlexItemAt(i);

                    if (view instanceof Chip) {
                        categoriasStr += ((Chip) view).getText() + ";";
                    }
                }

                issue.setCategorias(categoriasStr);

                Intent replyIntent = new Intent();
                replyIntent.putExtra("issue",issue);
                setResult(RESULT_OK, replyIntent);
                finish();

            }
        });
    }

    private void initLayout(){
        txtTitulo = findViewById(R.id.txtTituloIssue);
        txtCategoria = findViewById(R.id.txtCaregoriaIssue);
        txtDtCriacao = findViewById(R.id.txtDtCriacaoIssue);
        txtDtSolucao = findViewById(R.id.txtDtSolucaoIssue);
        txtDescricao = findViewById(R.id.txtDescricaoIssue);
        txtSolucao = findViewById(R.id.txtSolucaoIssue);
        btnSalvar = findViewById(R.id.btnSalvarIssue);
        layoutCategorias = findViewById(R.id.categoria_group);
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
        txtDtCriacao.setText(issue.getDtCriacao());
        txtDtSolucao.setText(issue.getDtSolucao());
        txtDescricao.setText(issue.getDescricao());
        txtSolucao.setText(issue.getSolucao());

        if(issue.getCategorias() !=null && !issue.getCategorias().isEmpty()) {
            for (String str : issue.getCategorias().split(";")) {
                addNewChip(str, layoutCategorias);
            }
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

        FlexboxLayout.LayoutParams lp = (FlexboxLayout.LayoutParams) chip.getLayoutParams();
        lp.setMargins(5,5,5,5);    }
}
