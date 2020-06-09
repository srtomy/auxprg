package com.srtomy.auxprog;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import com.srtomy.auxprog.activity.AnotacaoDetailsActivity;
import com.srtomy.auxprog.adapter.AnotacaoListAdapter;
import com.srtomy.auxprog.adapter.RecyclerViewClickListener;
import com.srtomy.auxprog.model.AnotacaoViewModel;

import java.util.List;

public class TabAnotacaoFragment extends Fragment implements RecyclerViewClickListener {

    public static final int NEW_WORD_ACTIVITY_REQUEST_CODE = 1;

    private AnotacaoViewModel mWordViewModel;
    private AnotacaoListAdapter adapter;
    private RecyclerView recyclerView;

    private Context context;
    private View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //inicializa objetos
        view = inflater.inflate(R.layout.activity_anotacao, container, false);
        context = view.getContext();
        recyclerView = view.findViewById(R.id.recyclerview);
        adapter = new AnotacaoListAdapter(view.getContext(),this);

        //set propriedades
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));

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

        FloatingActionButton fab = view.findViewById(R.id.fab);
        fab.setOnClickListener(v -> {
            Intent intent = new Intent(view.getContext(), AnotacaoDetailsActivity.class);
            intent.putExtra("anotacao", new Anotacao());
            startActivityForResult(intent, NEW_WORD_ACTIVITY_REQUEST_CODE);
        });

        return view;
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == NEW_WORD_ACTIVITY_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            Anotacao anotacao = (Anotacao) data.getSerializableExtra("anotacao");
            mWordViewModel.insert(anotacao);
        } else {
            Toast.makeText(
                    context,
                    R.string.empty_not_saved,
                    Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void recyclerViewListClicked(View v, int position) {
        Anotacao anotacao = mWordViewModel.get(position);

        Intent intent = new Intent( context, AnotacaoDetailsActivity.class);
        intent.putExtra("anotacao", anotacao);
        startActivityForResult(intent, NEW_WORD_ACTIVITY_REQUEST_CODE);
    }

    @Override
    public void recyclerViewListLongClicked(View v, int position) {
        /*
        if(deleteItem.isVisible()){
            deleteItem.setVisible(false);
            adapter.selectedPos = -1;
        }else {
            deleteItem.setVisible(true);
        }

         */
    }
}