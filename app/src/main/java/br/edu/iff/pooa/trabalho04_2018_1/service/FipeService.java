package br.edu.iff.pooa.trabalho04_2018_1.service;

import java.util.List;

import br.edu.iff.pooa.trabalho04_2018_1.model.Ano;
import br.edu.iff.pooa.trabalho04_2018_1.model.AnoModelo;
import br.edu.iff.pooa.trabalho04_2018_1.model.Marca;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface FipeService {

    @GET("{veiculo}/marcas")
    Call<List<Marca>> buscarMarcas(@Path("veiculo") String veiculo);

    @GET("{veiculo}/marcas/{id}/modelos")
    Call<AnoModelo> getModelos(@Path("veiculo") String veiculo, @Path("id") long id);

    @GET("{veiculo}/marcas/{id}/modelos/{idModelo}/anos")
    Call<List<Ano>> getAnosPorModelo(@Path("veiculo") String veiculo,
                                     @Path("id") long id,
                                     @Path("idModelo") long idModelo);
}
