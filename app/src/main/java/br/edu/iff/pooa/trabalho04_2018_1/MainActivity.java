package br.edu.iff.pooa.trabalho04_2018_1;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.List;

import br.edu.iff.pooa.trabalho04_2018_1.adapter.MarcaAdapter;
import br.edu.iff.pooa.trabalho04_2018_1.configRetrofit.FipeRetroFit;
import br.edu.iff.pooa.trabalho04_2018_1.model.Marca;
import br.edu.iff.pooa.trabalho04_2018_1.service.ClickRecyclerViewListener;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements ClickRecyclerViewListener {

    RecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.veiculos_array,
                                                                            android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Spinner spinnerVeiculo = findViewById(R.id.spinnerVeiculo);
        mRecyclerView = findViewById(R.id.rvMarcas);

        spinnerVeiculo.setAdapter(adapter);
        spinnerVeiculo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(final AdapterView<?> adapterView, View view, int i, long l) {
                String marca = null;
                switch (i){
                    case 0: marca = "carros";
                            break;
                    case 1: marca = "motos";
                            break;
                    case 2: marca = "caminhoes";
                            break;
                }
                Toast.makeText(getBaseContext(), String.valueOf(adapterView.getItemIdAtPosition(i)), Toast.LENGTH_SHORT).show();
                if (marca != null){
                    Call<List<Marca>> call = new FipeRetroFit().getFipeService().buscarMarcas(marca);
                    call.enqueue(new Callback<List<Marca>>() {
                        @Override
                        public void onResponse(Call<List<Marca>> call, Response<List<Marca>> response) {
                            List<Marca> marcas = response.body();
                            Log.i("---------------", "MARCAS QTD:" + marcas.size());
                            obterAdapter(marcas);
                        }

                        @Override
                        public void onFailure(Call<List<Marca>> call, Throwable t) {

                        }
                    });
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }
    protected void obterAdapter(List<Marca> marcas) {
        super.onResume();
        mRecyclerView.setAdapter(new MarcaAdapter(marcas,getBaseContext(), this));
        RecyclerView.LayoutManager layout = new LinearLayoutManager(getBaseContext(),
                LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(layout);
    }

    @Override
    public void onClick(Object object) {

    }
}
