package br.edu.iff.pooa.trabalho04_2018_1.view;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.util.List;

import br.edu.iff.pooa.trabalho04_2018_1.R;
import br.edu.iff.pooa.trabalho04_2018_1.adapter.MarcaAdapter;
import br.edu.iff.pooa.trabalho04_2018_1.configRetrofit.FipeRetroFit;
import br.edu.iff.pooa.trabalho04_2018_1.model.Marca;
import br.edu.iff.pooa.trabalho04_2018_1.service.ClickRecyclerViewListener;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements ClickRecyclerViewListener {

    RecyclerView mRecyclerView;
    Call<List<Marca>> call;
    List<Marca> marcas;
    public static String veiculo;
    private ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dialog = new ProgressDialog(MainActivity.this);
        dialog.setCancelable(false);
        dialog.setMessage("Atualizando...");

        final ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.veiculos_array,
                                                                            android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Spinner spinnerVeiculo = findViewById(R.id.spinnerVeiculo);
        mRecyclerView = findViewById(R.id.rvMarcas);

        spinnerVeiculo.setAdapter(adapter);
        spinnerVeiculo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(final AdapterView<?> adapterView, View view, final int i, long l) {
                dialog.show();
                switch (i){
                    case 0: veiculo = "carros";
                            break;
                    case 1: veiculo = "motos";
                            break;
                    case 2: veiculo = "caminhoes";
                            break;
                }
                if (veiculo != null){
                    call = new FipeRetroFit().getFipeService().buscarMarcas(veiculo);
                    call.enqueue(new Callback<List<Marca>>() {
                        @Override
                        public void onResponse(Call<List<Marca>> call, Response<List<Marca>> response) {
                            marcas = response.body();
                            Log.i("---------------", "MARCAS QTD:" + marcas.size());

                            obterAdapter(marcas, i);
                            dialog.dismiss();
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
    protected void obterAdapter(List<Marca> marcas, int vehicle) {
        super.onResume();
        mRecyclerView.setAdapter(new MarcaAdapter(vehicle, marcas,getBaseContext(), this));
        RecyclerView.LayoutManager layout = new LinearLayoutManager(getBaseContext(),
                LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(layout);
    }

    @Override
    public void onClick(Object object) {
        Marca marca = (Marca) object;
        Intent intent = new Intent(MainActivity.this, FilterActivity.class);
        intent.putExtra("id", marca.getCodigo());
        startActivity(intent);
    }
}


