package com.srtomy.auxprog.activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.srtomy.auxprog.Anotacao;
import com.srtomy.auxprog.R;
import com.srtomy.auxprog.adapter.AnotacaoListAdapter;
import com.srtomy.auxprog.model.AnotacaoViewModel;

import java.util.List;

public class AnotacaoActivity extends AppCompatActivity {

    public static final int NEW_WORD_ACTIVITY_REQUEST_CODE = 1;

    private AnotacaoViewModel mWordViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anotacao);

        RecyclerView recyclerView = findViewById(R.id.recyclerview);
        final AnotacaoListAdapter adapter = new AnotacaoListAdapter(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Get a new or existing ViewModel from the ViewModelProvider.
        mWordViewModel = new ViewModelProvider(this).get(AnotacaoViewModel.class);

        // Add an observer on the LiveData returned by getAlphabetizedWords.
        // The onChanged() method fires when the observed data changes and the activity is
        // in the foreground.
        mWordViewModel.getAllWords().observe(this, new Observer<List<Anotacao>>() {
            @Override
            public void onChanged(@Nullable final List<Anotacao> words) {
                // Update the cached copy of the words in the adapter.
                adapter.setAnotacoes(words);
            }
        });

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AnotacaoActivity.this, AnotacaoDetailsActivity.class);
                startActivityForResult(intent, NEW_WORD_ACTIVITY_REQUEST_CODE);
            }
        });
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == NEW_WORD_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK) {
            Anotacao anotacao = (Anotacao) data.getSerializableExtra("anotacao");
            mWordViewModel.insert(anotacao);
        } else {
            Toast.makeText(
                    getApplicationContext(),
                    R.string.empty_not_saved,
                    Toast.LENGTH_LONG).show();
        }
    }
}
