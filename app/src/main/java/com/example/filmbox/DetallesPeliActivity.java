package com.example.filmbox;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.makeramen.roundedimageview.RoundedImageView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetallesPeliActivity extends AppCompatActivity {

    private RoundedImageView imagenCabeceraFondo, imagenPoster;
    private TextView textoTitulo, textoDirector, textoAnyo, textoDuracion, textoEslogan, textoDescripcion, textoGeneros;
    private NestedScrollView nestedscrollView;
    private int id;
    private ApiService apiService;
    private List<CrewResult> listacrew = new ArrayList<>();
    private List<CastResult> listacast = new ArrayList<>();
    List<ProviderResult> listasubscriptionproviders = new ArrayList<>();
    List<ProviderResult> listabuyproviders = new ArrayList<>();
    List<ProviderResult> listarentproviders = new ArrayList<>();
    List<GenreResult> listageneros = new ArrayList<>();
    private CastAdapter adapterCast;
    private CrewAdapter adapterCrew;
    private ProviderAdapter adapterProvider;
    private ProviderAdapter adapterProviderBuy;
    private ProviderAdapter adapterProviderRent;
    private RecyclerView rvCast;
    private RecyclerView rvCrew;
    private RecyclerView rvProviders;
    private RecyclerView rvProvidersBuy;
    private RecyclerView rvProvidersRent;

    MovieProviderRegion movieProviderRegion = new MovieProviderRegion();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalles_peli);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);

        findViewById(R.id.view).setVisibility(View.INVISIBLE);
        findViewById(R.id.view2).setVisibility(View.INVISIBLE);

        imagenCabeceraFondo = findViewById(R.id.imagenCabeceraFondo);
        imagenPoster = findViewById(R.id.imagenPoster);
        textoTitulo = findViewById(R.id.textoTitulo);
        textoDirector = findViewById(R.id.textoDirector);
        textoAnyo = findViewById(R.id.textoAnyo);
        textoDuracion = findViewById(R.id.textoDuracion);
        textoEslogan = findViewById(R.id.textoEslogan);
        textoDescripcion = findViewById(R.id.textoDescripcion);
        textoGeneros = findViewById(R.id.textoGeneross);
        nestedscrollView = findViewById(R.id.svDetallesPeli);

        id = getIntent().getIntExtra("id", 0);

        apiService = ApiClient.getClient().create(ApiService.class);

        setMovieDetails();

        setMovieCrewCast();

        setMovieProviders();

        findViewById(R.id.imagenAtras).setOnClickListener(v -> onBackPressed());
    }


    private void setMovieDetails() {
        Call<MovieDetails> call = apiService.getMovieDetails(String.valueOf(id), MainActivity.LANGUAGE,MainActivity.MYAPI_KEY);
        call.enqueue(new Callback<MovieDetails>() {
            @Override
            public void onResponse(@NonNull Call<MovieDetails> call, @NonNull Response<MovieDetails> response) {
                if (response.body() != null) {
                    nestedscrollView.setVisibility(View.VISIBLE);
                    ImageAdapter.setBackdropURL(imagenCabeceraFondo, response.body().getBackdropPath());
                    ImageAdapter.setPosterURL(imagenPoster, response.body().getPosterPath());
                    textoTitulo.setText(response.body().getTitle());
                    textoAnyo.setText(response.body().getReleaseDate());
                    textoEslogan.setText(response.body().getTagline());
                    listageneros = response.body().getGenres();
                    textoDescripcion.setText(response.body().getOverview());
                    for(GenreResult genreresult : listageneros)
                    {
                        textoGeneros.setText( textoGeneros.getText() + genreresult.getName()+"\n");
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<MovieDetails> call, @NonNull Throwable t) {
                Toast.makeText(getApplicationContext(), "Error al cargar los detalles de la película " + id,
                        Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void setMovieCrewCast(){
        rvCast = findViewById(R.id.rvCast);
        rvCrew = findViewById(R.id.rvCrew);
        adapterCast = new CastAdapter(listacast, this);
        adapterCrew = new CrewAdapter(listacrew, this);

        Call<MovieCredits> call = apiService.getMovieCredits(String.valueOf(id), MainActivity.LANGUAGE,MainActivity.MYAPI_KEY);
        call.enqueue(new Callback<MovieCredits>() {

            @Override
            public void onResponse(@NonNull Call<MovieCredits> call, @NonNull Response<MovieCredits> response){

                if(response.body().getResults() != null){
                    int oldCount = listacrew.size();
                    listacrew.addAll(response.body().getResults());
                    adapterCrew.notifyItemChanged(oldCount, listacrew);
                    for(CrewResult crewresult : listacrew)
                    {
                        if(crewresult.getJob().equals("Director"))
                        {
                            textoDirector.setText(crewresult.getName());
                        }
                    }
                }

                if(response.body().getResultsCast()!=null){
                    int oldCount = listacast.size();
                    listacast.addAll(response.body().getResultsCast());
                    adapterCast.notifyItemChanged(oldCount, listacast);
                }
            }

            @Override
            public void onFailure(@NonNull Call<MovieCredits> call,@NonNull Throwable t) {
                Toast.makeText(getApplicationContext(), "Error al cargar  los creditos de la peli",
                        Toast.LENGTH_SHORT).show();
            }

        });

        rvCast.setAdapter(adapterCast);
        rvCrew.setAdapter(adapterCrew);

    }

    private void setMovieProviders(){
        rvProviders = findViewById(R.id.rvAvailableSubscription);
        rvProvidersBuy = findViewById(R.id.rvAvailableBuy);
        rvProvidersRent = findViewById(R.id.rvAvailableRent);
        adapterProvider = new ProviderAdapter(listasubscriptionproviders, this);
        adapterProviderBuy = new ProviderAdapter(listabuyproviders, this);
        adapterProviderRent = new ProviderAdapter(listarentproviders, this);

        Call<MovieProviders> call = apiService.getMovieProviders(String.valueOf(id),MainActivity.MYAPI_KEY);
        call.enqueue(new Callback<MovieProviders>() {

            @Override
            public void onResponse(@NonNull Call<MovieProviders> call, @NonNull Response<MovieProviders> response) {

                if (response.body().getMovieProviderRegion().getProviderRegion() != null) {
                    if (response.body().getMovieProviderRegion().getProviderRegion().getSubscriptionsproviderResult() != null){
                        int oldCount = listasubscriptionproviders.size();
                        listasubscriptionproviders.addAll(response.body().getMovieProviderRegion().getProviderRegion().getSubscriptionsproviderResult());
                        adapterProvider.notifyItemChanged(oldCount, listasubscriptionproviders);
                        findViewById(R.id.textoAvailableSubscription).setVisibility(View.VISIBLE);
                        findViewById(R.id.view3).setVisibility(View.VISIBLE);
                    }else{
                        findViewById(R.id.textoAvailableSubscription).setVisibility(View.INVISIBLE);
                        findViewById(R.id.view3).setVisibility(View.INVISIBLE);
                    }

                    if (response.body().getMovieProviderRegion().getProviderRegion().getBuyproviderResult() != null){
                        int oldCount = listabuyproviders.size();
                        listabuyproviders.addAll(response.body().getMovieProviderRegion().getProviderRegion().getBuyproviderResult());
                        adapterProviderBuy.notifyItemChanged(oldCount, listabuyproviders);
                        findViewById(R.id.textoAvailableBuy).setVisibility(View.VISIBLE);
                        findViewById(R.id.view4).setVisibility(View.VISIBLE);
                    }else{
                        findViewById(R.id.textoAvailableBuy).setVisibility(View.INVISIBLE);
                        findViewById(R.id.view4).setVisibility(View.INVISIBLE);
                    }

                    if (response.body().getMovieProviderRegion().getProviderRegion().getRentproviderResult() != null){
                        int oldCount = listarentproviders.size();
                        listarentproviders.addAll(response.body().getMovieProviderRegion().getProviderRegion().getRentproviderResult());
                        adapterProviderRent.notifyItemChanged(oldCount, listarentproviders);
                        findViewById(R.id.textoAvailableRent).setVisibility(View.VISIBLE);
                        findViewById(R.id.view5).setVisibility(View.VISIBLE);
                    }else{
                        findViewById(R.id.textoAvailableRent).setVisibility(View.INVISIBLE);
                        findViewById(R.id.view5).setVisibility(View.INVISIBLE);
                    }

                }else{
                    findViewById(R.id.textoAvailableSubscription).setVisibility(View.INVISIBLE);
                    findViewById(R.id.textoAvailableBuy).setVisibility(View.INVISIBLE);
                    findViewById(R.id.textoAvailableRent).setVisibility(View.INVISIBLE);

                    findViewById(R.id.view3).setVisibility(View.INVISIBLE);
                    findViewById(R.id.view4).setVisibility(View.INVISIBLE);
                    findViewById(R.id.view5).setVisibility(View.INVISIBLE);
                }

            }

            @Override
            public void onFailure(@NonNull Call<MovieProviders> call, @NonNull Throwable t) {
                Toast.makeText(getApplicationContext(), "Error al cargar  los proveedores de la peli",
                        Toast.LENGTH_SHORT).show();
            }
        });

        rvProviders.setAdapter(adapterProvider);
        rvProvidersBuy.setAdapter(adapterProviderBuy);
        rvProvidersRent.setAdapter(adapterProviderRent);
    }

}