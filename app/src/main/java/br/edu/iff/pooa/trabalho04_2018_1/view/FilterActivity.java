package br.edu.iff.pooa.trabalho04_2018_1.view;

import android.content.Intent;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import java.util.List;

import br.edu.iff.pooa.trabalho04_2018_1.R;
import br.edu.iff.pooa.trabalho04_2018_1.configRetrofit.FipeRetroFit;
import br.edu.iff.pooa.trabalho04_2018_1.model.Ano;
import br.edu.iff.pooa.trabalho04_2018_1.model.AnoModelo;
import br.edu.iff.pooa.trabalho04_2018_1.model.Modelo;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FilterActivity extends AppCompatActivity implements View.OnClickListener{

    private ViewHolder mViewHolder = new ViewHolder();
    private boolean isExpanded = true;
    private Long idMarcaSelecionada;
    private List<Modelo> modelos;
    private List<Ano> anos;
    private Modelo modeloSelecionado;
    private Ano anoSelecionado;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter);

        mViewHolder.modelo = findViewById(R.id.spinnerModelo);
        mViewHolder.ano = findViewById(R.id.spinnerAno);
        mViewHolder.min = findViewById(R.id.edtMin);
        mViewHolder.max = findViewById(R.id.edtMax);
        mViewHolder.procurar = findViewById(R.id.buttonProcurar);
        mViewHolder.escondeMostra = findViewById(R.id.ivEsconderMostrar);
        mViewHolder.clPesquisa = findViewById(R.id.clPesquisa);

        mViewHolder.escondeMostra.setOnClickListener(this);
        mViewHolder.procurar.setOnClickListener(this);
        Intent intent = getIntent();
        idMarcaSelecionada = (Long) intent.getSerializableExtra("id");

        consultaRetroFit();

        mViewHolder.modelo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                modeloSelecionado = (Modelo) mViewHolder.modelo.getSelectedItem();
                consultaAnoEspecifico(modeloSelecionado.getCodigo());
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        mViewHolder.ano.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                anoSelecionado = (Ano) mViewHolder.ano.getSelectedItem();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if(id == R.id.buttonProcurar){


        }
        if (id == R.id.ivEsconderMostrar){
            if (isExpanded){
                unexpander();
                mViewHolder.escondeMostra.setImageResource(R.drawable.expand_more);
                isExpanded = false;
            } else{
                expander();
                mViewHolder.escondeMostra.setImageResource(R.drawable.expand_less);
                isExpanded = true;
            }
        }
    }

    public static class ViewHolder{
        EditText min, max;
        Spinner modelo, ano;
        ImageView escondeMostra;
        Button procurar;
        ConstraintLayout clPesquisa;
    }

    public void unexpander(){
        ConstraintLayout.LayoutParams layoutParams = (ConstraintLayout.LayoutParams)mViewHolder.clPesquisa.getLayoutParams();
        layoutParams.height = 1;
        layoutParams.width = ViewGroup.LayoutParams.WRAP_CONTENT;
        mViewHolder.clPesquisa.setLayoutParams(layoutParams);
    }
    public void expander(){
        ConstraintLayout.LayoutParams layoutParams = (ConstraintLayout.LayoutParams)mViewHolder.clPesquisa.getLayoutParams();
        layoutParams.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        layoutParams.width = ViewGroup.LayoutParams.WRAP_CONTENT;
        mViewHolder.clPesquisa.setLayoutParams(layoutParams);
    }

    private void povoarSpinners(){
        ArrayAdapter<Modelo> spinnerArrayAdapterModelo = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, modelos);
        spinnerArrayAdapterModelo.setDropDownViewResource(android.R.layout.simple_spinner_item);
        mViewHolder.modelo.setAdapter(spinnerArrayAdapterModelo);

        ArrayAdapter<Ano> spinnerArrayAdapterAno = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, anos);
        spinnerArrayAdapterAno.setDropDownViewResource(android.R.layout.simple_spinner_item);
        mViewHolder.modelo.setAdapter(spinnerArrayAdapterAno);
    }

    private void consultaRetroFit(){
        Call<AnoModelo> call = new FipeRetroFit().getFipeService().getModelos(MainActivity.veiculo, idMarcaSelecionada);
        call.enqueue(new Callback<AnoModelo>() {
            @Override
            public void onResponse(Call<AnoModelo> call, Response<AnoModelo> response) {
                modelos = response.body().getModelos();
                anos = response.body().getAnos();
                FilterActivity.this.modelos.addAll(modelos);
                FilterActivity.this.anos.addAll(anos);
                povoarSpinners();
            }

            @Override
            public void onFailure(Call<AnoModelo> call, Throwable t) {
                Log.e("CONSULTARETROFIT", "Falha:"+t.getMessage());
            }
        });
    }

    private void consultaAnoEspecifico(long codigo) {
        Call<List<Ano>> listCall = new FipeRetroFit().getFipeService().getAnosPorModelo(MainActivity.veiculo, idMarcaSelecionada, codigo);
        listCall.enqueue(new Callback<List<Ano>>() {
            @Override
            public void onResponse(Call<List<Ano>> call, Response<List<Ano>> response) {
                anos = response.body();
                FilterActivity.this.anos.addAll(anos);
                povoarSpinners();
            }

            @Override
            public void onFailure(Call<List<Ano>> call, Throwable t) {
                Log.e("-CONSULTAANOESPECIFICO-", t.getMessage());
            }
        });
    }


}
