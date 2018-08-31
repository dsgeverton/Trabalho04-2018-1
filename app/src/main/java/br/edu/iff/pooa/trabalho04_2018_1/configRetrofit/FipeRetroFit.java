package br.edu.iff.pooa.trabalho04_2018_1.configRetrofit;

import br.edu.iff.pooa.trabalho04_2018_1.service.FipeService;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class FipeRetroFit {

    private final Retrofit retrofit;

    public FipeRetroFit(){
        this.retrofit = new Retrofit.Builder()
                .baseUrl("https://parallelum.com.br/fipe/api/v1/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public FipeService getFipeService(){
        return this.retrofit.create(FipeService.class);
    }
}
