package com.example.recyclerviewtest;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class PokemonAdapter extends RecyclerView.Adapter<PokemonAdapter.PokemonViewHolder>{

    /*Arraylist donde almaceno los datos que se van a mostrar en el RecylerView*/
    private List<Result> datos;

    private final OnItemClickListener listener;

    public void swap(List<Result> datos1) {
        datos = datos1;
    }

    /* Defino un interface con el OnItemClickListener*/
    public interface OnItemClickListener {
        void onItemClick(Result item);
    }

    /* Incluyo el Viewholder en el Adapter */
    public static class PokemonViewHolder
            extends RecyclerView.ViewHolder {
        /* Como atributos se incluyen los elementos que van a referenciar a los elementos de la vista*/
        private TextView tvNombre;
        private TextView tvURL;

        /*constructor con parámetro de la vista*/
        public PokemonViewHolder(View itemView) {
            super(itemView);
            // Asocia los atributos a los widgets del layout de la vista
            tvNombre = (TextView)itemView.findViewById(R.id.tvNombre);
            tvURL = (TextView)itemView.findViewById(R.id.tvURL);
        }

        /*Muestra los datos de jugador en el item*/
        public void bindPokemon(Result j, final OnItemClickListener listener) {
            tvNombre.setText(j.getName());
            tvURL.setText(j.getUrl());

            /* Coloco el listener a la vista*/
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override public void onClick(View v) {
                    listener.onItemClick(j);
                }
            });
            }
        }


    /* Constructor con parámetros*/
    public PokemonAdapter(List<Result> datos, OnItemClickListener listener) {
        this.datos = datos;
        this.listener = listener;
    }

    @Override
    public PokemonViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        /*Crea la vista de un item y la "pinta"*/
        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_pokemon, viewGroup, false);
        /* Crea un objeto de la clase CocheViewHolder, pasándole la vista anteriormente creada*/
        PokemonViewHolder pokemonVH = new PokemonViewHolder(itemView);
        /* devuelve la vissta*/
        return pokemonVH;
    }

    @Override
    public void onBindViewHolder(PokemonViewHolder viewHolder, int pos) {
        Result j = datos.get(pos);
        /* Llama a bindCoche, para que "pinte" los datos del coche que se le pasa como parámetro*/
        viewHolder.bindPokemon(j,listener);
    }

    @Override
    public int getItemCount() {
        return datos.size();
    }

}
