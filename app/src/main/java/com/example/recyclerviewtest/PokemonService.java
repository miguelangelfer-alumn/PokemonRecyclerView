package com.example.recyclerviewtest;


import retrofit2.Call;
import retrofit2.http.GET;

public interface PokemonService {
    @GET("pokemon")
    Call<Pokemon> getPokemons();
}
