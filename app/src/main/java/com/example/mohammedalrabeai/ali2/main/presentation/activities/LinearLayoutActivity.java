package com.example.mohammedalrabeai.ali2.main.presentation.activities;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import com.example.mohammedalrabeai.ali2.R;
import com.example.mohammedalrabeai.ali2.main.model.UserModel;
import com.example.mohammedalrabeai.ali2.main.presentation.adapter.LineAdapter;
import com.example.mohammedalrabeai.ali2.main.repository.UserLoader;
//import com.orafaaraujo.rxrecyclerexample.R;
//import com.orafaaraujo.rxrecyclerexample.presentation.main.model.UserModel;
//import com.orafaaraujo.rxrecyclerexample.presentation.main.presentation.adapter.LineAdapter;
//import com.orafaaraujo.rxrecyclerexample.presentation.main.repository.UserLoader;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LinearLayoutActivity extends AppCompatActivity implements View.OnClickListener {

    @BindView(R.id.recycler_view_layour_fab)
    FloatingActionButton mFab;

    @BindView(R.id.recycler_view_layour_recycler)
    RecyclerView mRecyclerView;

    private LineAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_linear_layout);
mFab=(FloatingActionButton) findViewById(R.id.recycler_view_layour_fab);
mRecyclerView=(RecyclerView)findViewById(R.id.recycler_view_layour_recycler);
        ButterKnife.bind(this);

        setupView();
        setupRecycler();
    }

    private void setupView() {
        mFab.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        UserLoader
                .fetch()
                .subscribe(o -> mAdapter.updateList((UserModel) o));
    }

    private void setupRecycler() {

        // Configurando o gerenciador de layout para ser uma lista.
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(layoutManager);

        // Adiciona o adapter que irá anexar os objetos à lista.
        // Está sendo criado com lista vazia, pois será preenchida posteriormente.
        mAdapter = new LineAdapter(new ArrayList<>(0));
        mRecyclerView.setAdapter(mAdapter);

        // Configurando um dividr entre linhas, para uma melhor visualização.
        mRecyclerView.addItemDecoration(
                new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
    }
}
