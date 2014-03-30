package com.example.DBtest;

import com.example.DBtest.com.nvkudba.recipe.dao.Contributor;
import com.example.DBtest.com.nvkudba.recipe.dao.Plate;
import retrofit.RestAdapter;
import retrofit.http.GET;
import retrofit.http.Path;

import java.util.List;

public class RestClient {
    private static RestClient instance = null;
    private static final String MONGO_KEY="?apiKey=w1KeF3ei_CIB9G94huxJSmazss1anGJd";
    private static final String API_URL = "https://api.github.com";
    private static final String MONGO_URL = "https://api.mongolab.com/api/1/databases/recipedb";
    private static RestAdapter gitAdapter, recipedbAdapter;

    public GitHub github;
    public Recipe recipe;

    RestClient(){
        gitAdapter = new RestAdapter.Builder().setEndpoint(API_URL).build();
        recipedbAdapter = new RestAdapter.Builder().setEndpoint(MONGO_URL).build();

        github = gitAdapter.create(GitHub.class);
        recipe = recipedbAdapter.create(Recipe.class);

    }
    public static RestClient getInstance() {
        if(instance == null) {
            instance = new RestClient();
        }
        return instance;
    }

    interface GitHub {
        @GET("/repos/{owner}/{repo}/contributors")
        List<Contributor> getContributors(
                @Path("owner") String owner,
                @Path("repo") String repo
        );
    }

    interface Recipe {
        @GET("/collections/plate"+ MONGO_KEY)
        List<Plate> getPlates();
    }

}
