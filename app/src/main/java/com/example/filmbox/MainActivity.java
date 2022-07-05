package com.example.filmbox;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MainActivity extends AppCompatActivity {

    private ApiService apiService;
    private RecyclerView rvPeliculasPopulares, rvPeliculasCartelera, rvProximosEstrenos, rvPeliculasMejorValoradas;
    private final List<MovieResult> listaPeliculasPopulares = new ArrayList<>();
    private final List<MovieResult> listaPeliculasCartelera = new ArrayList<>();
    private final List<MovieResult> listaProximosEstrenos = new ArrayList<>();
    private final List<MovieResult> listaPeliculasMejorValoradas = new ArrayList<>();
    private MovieAdapter adapterPeliculasPopulares, adapterPeliculasCartelera, adapterProximosEstrenos, adapterPeliculasMejorValoradas;
    private int currentPageMoviePopular = 1, currentPageMovieNowPlaying = 1,
            currentPageMovieUpcoming = 1, currentPageMovieTopRated = 1;
    private int totalPagesMoviePopular = 1, totalPagesMovieNowPlaying = 1, totalPagesMovieUpcoming =1,
            totalPagesMovieTopRated = 1;

    private Calendar calendar;
    private AlarmManager alarmManager;
    private PendingIntent pendingIntent;

    public static final String MYAPI_KEY = "your api key";

    public static final String LANGUAGE = "es-ES";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        setTheme(R.style.Theme_FilmBox);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Retrofit retrofit = ApiClient.getClient();
        apiService = retrofit.create(ApiService.class);
        setPopularMovies();
        setNowPlayingMovies();
        setUpcomingMovies();
        setTopRatedMovies();

        createNotificationChannel();
        setAlarm();

        findViewById(R.id.imagenBuscarLupa).setOnClickListener(v -> mostrarDialogoBuscar());

    }

    private void createNotificationChannel(){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            CharSequence name = "filmboxremminderchannel";
            String description = "Channel For FILMBOX";
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel channel = new NotificationChannel("filmbox",name,importance);
            channel.setDescription(description);

            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    private void setAlarm() {

        calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_MONTH, Calendar.FRIDAY);
        calendar.set(Calendar.HOUR_OF_DAY, 7);
        calendar.set(Calendar.MINUTE,0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND,0);

        alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

        Intent intent = new Intent(this,AlarmReceiver.class);

        pendingIntent = PendingIntent.getBroadcast(this,0,intent,0);

        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis(),
                AlarmManager.INTERVAL_DAY,pendingIntent);

    }

    private void setPopularMovies() {
        rvPeliculasPopulares = findViewById(R.id.rvCast);
        adapterPeliculasPopulares = new MovieAdapter(listaPeliculasPopulares, this);
        getPopularMovies();
        rvPeliculasPopulares.setAdapter(adapterPeliculasPopulares);

        rvPeliculasPopulares.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (!rvPeliculasPopulares.canScrollHorizontally(1)) {
                    if (currentPageMoviePopular <= totalPagesMoviePopular) {
                        currentPageMoviePopular += 1;
                        getPopularMovies();
                    }
                }
            }
        });
    }

    private void getPopularMovies() {
        Call<MovieRespon> call = apiService.getPopularMovies(MYAPI_KEY, LANGUAGE, currentPageMoviePopular);
        call.enqueue(new Callback<MovieRespon>() {
            @Override
            public void onResponse(@NonNull Call<MovieRespon> call,@NonNull Response<MovieRespon> response) {
                if (response.body() != null) {
                    totalPagesMoviePopular = response.body().getTotalPages();
                    if (response.body().getResults() != null) {
                        int oldCount = listaPeliculasPopulares.size();
                        listaPeliculasPopulares.addAll(response.body().getResults());
                        adapterPeliculasPopulares.notifyItemChanged(oldCount, listaPeliculasPopulares.size());
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<MovieRespon> call,@NonNull Throwable t) {
                Toast.makeText(getApplicationContext(), "Error al cargar las películas más populares",
                        Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setNowPlayingMovies() {
        rvPeliculasCartelera = findViewById(R.id.rvPeliculasCartelera);
        adapterPeliculasCartelera = new MovieAdapter(listaPeliculasCartelera, this);
        getNowPlayingMovies();
        rvPeliculasCartelera.setAdapter(adapterPeliculasCartelera);

        rvPeliculasCartelera.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (!rvPeliculasCartelera.canScrollHorizontally(1)) {
                    if (currentPageMovieNowPlaying <= totalPagesMovieNowPlaying) {
                        currentPageMovieNowPlaying += 1;
                        getNowPlayingMovies();
                    }
                }
            }
        });
    }

    private void getNowPlayingMovies() {
        Call<MovieRespon> call = apiService.getNowPlayingMovies(MYAPI_KEY, LANGUAGE, currentPageMovieNowPlaying);
        call.enqueue(new Callback<MovieRespon>() {
            @Override
            public void onResponse(@NonNull Call<MovieRespon> call,@NonNull Response<MovieRespon> response) {
                if (response.body() != null) {
                    totalPagesMovieNowPlaying = response.body().getTotalPages();
                    if (response.body().getResults() != null) {
                        int oldCount = listaPeliculasCartelera.size();
                        listaPeliculasCartelera.addAll(response.body().getResults());
                        adapterPeliculasCartelera.notifyItemChanged(oldCount, listaPeliculasCartelera.size());
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<MovieRespon> call,@NonNull Throwable t) {
                Toast.makeText(getApplicationContext(), "Error al cargar las películas en cartelera",
                        Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setUpcomingMovies() {
        rvProximosEstrenos = findViewById(R.id.rvProximosEstrenos);
        adapterProximosEstrenos = new MovieAdapter(listaProximosEstrenos, this);
        getUpcomingMovies();
        rvProximosEstrenos.setAdapter(adapterProximosEstrenos);

        rvProximosEstrenos.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (!rvProximosEstrenos.canScrollHorizontally(1)) {
                    if (currentPageMovieUpcoming <= totalPagesMovieUpcoming) {
                        currentPageMovieUpcoming += 1;
                        getUpcomingMovies();
                    }
                }
            }
        });
    }

    private void getUpcomingMovies() {
        Call<MovieRespon> call = apiService.getUpcomingMovies(MYAPI_KEY, LANGUAGE, currentPageMovieUpcoming);
        call.enqueue(new Callback<MovieRespon>() {
            @Override
            public void onResponse(@NonNull Call<MovieRespon> call,@NonNull Response<MovieRespon> response) {
                if (response.body() != null) {
                    totalPagesMovieUpcoming = response.body().getTotalPages();
                    if (response.body().getResults() != null) {
                        int oldCount = listaProximosEstrenos.size();
                        listaProximosEstrenos.addAll(response.body().getResults());
                        adapterProximosEstrenos.notifyItemChanged(oldCount, listaProximosEstrenos.size());
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<MovieRespon> call,@NonNull Throwable t) {
                Toast.makeText(getApplicationContext(), "Error al cargar los próximos estrenos",
                        Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setTopRatedMovies() {
        rvPeliculasMejorValoradas = findViewById(R.id.rvPeliculasMejorValoradas);
        adapterPeliculasMejorValoradas = new MovieAdapter(listaPeliculasMejorValoradas, this);
        getTopRatedMovies();
        rvPeliculasMejorValoradas.setAdapter(adapterPeliculasMejorValoradas);

        rvPeliculasMejorValoradas.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (!rvPeliculasMejorValoradas.canScrollHorizontally(1)) {
                    if (currentPageMovieTopRated <= totalPagesMovieTopRated) {
                        currentPageMovieTopRated += 1;
                        getTopRatedMovies();
                    }
                }
            }
        });
    }

    private void getTopRatedMovies() {
        Call<MovieRespon> call = apiService.getTopRatedMovies(MYAPI_KEY, LANGUAGE, currentPageMovieTopRated);
        call.enqueue(new Callback<MovieRespon>() {
            @Override
            public void onResponse(@NonNull Call<MovieRespon> call,@NonNull Response<MovieRespon> response) {
                if (response.body() != null) {
                    totalPagesMovieTopRated = response.body().getTotalPages();
                    if (response.body().getResults() != null) {
                        int oldCount = listaPeliculasMejorValoradas.size();
                        listaPeliculasMejorValoradas.addAll(response.body().getResults());
                        adapterPeliculasMejorValoradas.notifyItemChanged(oldCount, listaPeliculasMejorValoradas.size());
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<MovieRespon> call,@NonNull Throwable t) {
                Toast.makeText(getApplicationContext(), "Error al cargar las películas mejor valoradas",
                        Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void mostrarDialogoBuscar(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        View v = inflater.inflate(R.layout.dialogobusqueda, null);

        EditText inputSearch = v.findViewById(R.id.InputText);
        ImageView imagenBUscar = v.findViewById(R.id.imagenBuscar);

        builder.setView(v);
        AlertDialog dialogSearch = builder.create();
        if (dialogSearch.getWindow() != null)
            dialogSearch.getWindow().setBackgroundDrawable(new ColorDrawable(0));

        imagenBUscar.setOnClickListener(view -> hacerBusqueda(inputSearch.getText().toString()));

        dialogSearch.show();
    }

    private void hacerBusqueda(String query){
        if (query.isEmpty()) {
            Toast.makeText(getApplicationContext(), "No se detecto ninguna palabra.",
                    Toast.LENGTH_SHORT).show();
            return;
        }

        Intent i = new Intent(MainActivity.this, BuscarActivity.class);
        i.putExtra("buscarPor", query);
        startActivity(i);

    }

}
