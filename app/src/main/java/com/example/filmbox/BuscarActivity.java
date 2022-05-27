package com.example.filmbox;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.text.HtmlCompat;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BuscarActivity extends AppCompatActivity {


    private final List<MovieResult> movieResults = new ArrayList<>();
    private String query;
    private RecyclerView rvBuscar;
    private MovieSearchAdapter movieAdapter;
    private ApiService apiService;
    private TextView textNoResults;
    private int currentPage = 1;
    private int totalPages = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buscar);

        textNoResults = findViewById(R.id.textNoResults);
        TextView textoBusqueda = findViewById(R.id.textoBusqueda);
        rvBuscar = findViewById(R.id.rvBuscar);
        rvBuscar.setLayoutManager(new StaggeredGridLayoutManager(4, RecyclerView.VERTICAL));
        movieAdapter = new MovieSearchAdapter(movieResults, this);

        apiService = ApiClient.getClient().create(ApiService.class);

        query = getIntent().getStringExtra("buscarPor");
        textoBusqueda.setText(HtmlCompat.fromHtml("Has Buscado por : <b>" + query + "</b>",
                HtmlCompat.FROM_HTML_MODE_LEGACY));


        buscarPeliculas();
        rvBuscar.setAdapter(movieAdapter);

        findViewById(R.id.imagenAtras2).setOnClickListener(v -> onBackPressed());

    }

    protected void buscarPeliculas(){
        Call<MovieRespon> call = apiService.searchMovie(MainActivity.MYAPI_KEY, MainActivity.LANGUAGE,
                query, currentPage);
        call.enqueue(new Callback<MovieRespon>() {
            @Override
            public void onResponse(@NonNull Call<MovieRespon> call, @NonNull Response<MovieRespon> response) {
                if (response.body() != null) {
                    if (response.body().getResults().size() > 0) {
                        totalPages = response.body().getTotalPages();
                        int oldCount = movieResults.size();
                        movieResults.addAll(response.body().getResults());
                        movieAdapter.notifyItemRangeInserted(oldCount, movieResults.size());
                        checkSize(response.body().getResults().size());
                    }
                }
            }
            @Override
            public void onFailure(@NonNull Call<MovieRespon> call,@NonNull Throwable t) {
                textNoResults.setVisibility(View.VISIBLE);
                textNoResults.setText("Algo ha salido mal");
            }
        });
    }

    private void checkSize(int resulst) {
        if (resulst == 0) {
            textNoResults.setVisibility(View.VISIBLE);
        } else {
            textNoResults.setVisibility(View.GONE);
        }
    }

}