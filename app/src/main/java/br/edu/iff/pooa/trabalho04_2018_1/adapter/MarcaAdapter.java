package br.edu.iff.pooa.trabalho04_2018_1.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.List;
import br.edu.iff.pooa.trabalho04_2018_1.R;
import br.edu.iff.pooa.trabalho04_2018_1.model.Marca;
import br.edu.iff.pooa.trabalho04_2018_1.service.ClickRecyclerViewListener;

public class MarcaAdapter extends RecyclerView.Adapter{

    private int vehicle;
    private List<Marca> marcas;
    private Context context;
    private ClickRecyclerViewListener clickRecyclerViewListener;

    public MarcaAdapter(int vehicle, List<Marca> marcas, Context context, ClickRecyclerViewListener clickRecyclerViewListener) {
        this.vehicle = vehicle;
        this.marcas = marcas;
        this.context = context;
        this.clickRecyclerViewListener = clickRecyclerViewListener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.cardview_marca, parent, false);
        return new MecanicoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        MarcaAdapter.MecanicoViewHolder mViewHolder = (MarcaAdapter.MecanicoViewHolder) holder;
        Marca marca = marcas.get(position);
        mViewHolder.nomeMarca.setText(marca.getNome());
        if (vehicle == 0 )
            mViewHolder.imagemMarca.setImageResource(R.drawable.car_icon);
        if (vehicle == 1)
            mViewHolder.imagemMarca.setImageResource(R.drawable.motorcycle_icon);
        if (vehicle == 2)
            mViewHolder.imagemMarca.setImageResource(R.drawable.truck_icon);
    }

    @Override
    public int getItemCount() {
        return marcas.size();
    }

    private class MecanicoViewHolder extends RecyclerView.ViewHolder {
        private final TextView nomeMarca;
        private final ImageView imagemMarca;
        private MecanicoViewHolder(View itemView) {
            super(itemView);
            nomeMarca = itemView.findViewById(R.id.tvMarca);
            imagemMarca = itemView.findViewById(R.id.ivMarca);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    clickRecyclerViewListener.onClick(marcas.get(getLayoutPosition()));
                }
            });
        }
    }
}
