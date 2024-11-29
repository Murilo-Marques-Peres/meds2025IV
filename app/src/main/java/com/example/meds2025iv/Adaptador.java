package com.example.meds2025iv;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class Adaptador extends RecyclerView.Adapter<Adaptador.NossoViewHolder> {
    private ArrayList<InfoRemedios> lista1 = new ArrayList<>();

    @NonNull
    @Override
    public Adaptador.NossoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.itens, parent, false);
        NossoViewHolder viewHolder = new NossoViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull NossoViewHolder holder, int position) {
        InfoRemedios infoRemedios = lista1.get(position);
        holder.mTextViewNome.setText(infoRemedios.getNome());
        holder.mTextViewInfo.setText((infoRemedios.getInfo()));
    }

    @Override
    public int getItemCount() {
        if (lista1 == null) {
            return 0;
        }
        return lista1.size();
    }

    public class NossoViewHolder extends RecyclerView.ViewHolder {
        TextView mTextViewNome, mTextViewInfo;

        public NossoViewHolder(@NonNull View itemView) {
            super(itemView);

            mTextViewNome = itemView.findViewById(R.id.textView);
            mTextViewInfo = itemView.findViewById(R.id.textView2);
        }
    }

    public void atualizarListagemCompleta(ArrayList<InfoRemedios> lista1) {
        this.lista1 = lista1;
        notifyDataSetChanged();
    }
}
