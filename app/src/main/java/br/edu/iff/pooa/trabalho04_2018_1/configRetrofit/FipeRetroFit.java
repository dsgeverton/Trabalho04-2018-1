package br.edu.iff.pooa.trabalho04_2018_1.configRetrofit;

import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

public class FipeRetroFit {

    private final Retrofit retrofit;

    public FipeRetroFit(){
        this.retrofit = new Retrofit.Builder()
                .baseUrl("https://parallelum.com.br/fipe/api/v1/")
                .addConverterFactory(JacksonConverterFactory.create())
                .build();
    }

    public FipeService getFipeService(){
        return this.retrofit.create(FipeService.class)
    }
}
