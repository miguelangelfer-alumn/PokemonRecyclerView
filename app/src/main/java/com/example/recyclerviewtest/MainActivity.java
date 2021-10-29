package com.example.recyclerviewtest;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.*;
import retrofit2.http.GET;
import retrofit2.http.Path;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    List<Result> datos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        datos = new ArrayList<>();



        //Inicialización RecyclerView
        recyclerView = (RecyclerView) findViewById(R.id.RecView);
        recyclerView.setHasFixedSize(false);

        //Crea el adaptador, pasándole como parámetro los datos
        final PokemonAdapter adaptador = new PokemonAdapter(datos, new PokemonAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Result j) {
                //Toast.makeText(MainActivity.this, "Jugador pulsado: " + j.getNombre_jugador() + " " + j.getApellido_jugador(), Toast.LENGTH_SHORT).show();
                /*Intent i = new Intent(MainActivity.this, DetallesJugador.class);
                i.putExtra("nombre", j.getNombre_jugador()); //Optional parameters
                i.putExtra("apellido", j.getApellido_jugador());
                i.putExtra("dorsal", j.getDorsal());
                i.putExtra("goles", j.getGoles());
                i.putExtra("equipo", j.getEquipo());
                MainActivity.this.startActivity(i);*/

            }
        });

        //Asocia a recylerView el adaptador
        recyclerView.setAdapter(adaptador);

        //Fija un layout linear al recyclerview
        recyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));

        //Pone una división entre items
        recyclerView.addItemDecoration(
                new DividerItemDecoration(this,DividerItemDecoration.VERTICAL));
        // Pone la animación por defecto
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://pokeapi.co/api/v2/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        PokemonService poke_service = retrofit.create(PokemonService.class);

        Call<Pokemon> pokemones = poke_service.getPokemons();


        pokemones.enqueue(new Callback<Pokemon>() {
            @Override
            public void onResponse(Call<Pokemon> call, Response<Pokemon> response) {
                switch(response.code()) {
                    case 200:

                        Pokemon pokemon_extraido = response.body();
                        datos = pokemon_extraido.getResults();
                        adaptador.swap(datos);
                        adaptador.notifyDataSetChanged();
                        break;
                    case 401:
                        break;
                    default:
                        break;
                }
            }

            @Override
            public void onFailure(Call<Pokemon> call, Throwable t) {

            }
        });

    }
}