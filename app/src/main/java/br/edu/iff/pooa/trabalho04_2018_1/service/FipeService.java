package br.edu.iff.pooa.trabalho04_2018_1.service;

import br.edu.iff.pooa.trabalho04_2018_1.model.Marca;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface FipeService {

    @GET("{veiculo}/marcas")
    Call<Marca> buscarMarcas(@Path("veiculo") String veiculo);
}
