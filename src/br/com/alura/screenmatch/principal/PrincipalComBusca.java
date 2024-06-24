package br.com.alura.screenmatch.principal;

import br.com.alura.screenmatch.modelos.Titulo;
import br.com.alura.screenmatch.modelos.TituloOMDB;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Scanner;

public class PrincipalComBusca {
    public static void main(String[] args) throws IOException, InterruptedException {

        Scanner sc = new Scanner(System.in);

        System.out.print("Qual filme você quer pesquisar? ");
        var filme = sc.nextLine();

        var endereco = "https://www.omdbapi.com/?t=" + filme + "&apikey=53fd5cbd";

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(endereco))
                .build();
        HttpResponse<String> response = client
                .send(request, HttpResponse.BodyHandlers.ofString());

        String json = response.body();
        System.out.println(json);

        Gson gson = new GsonBuilder()
                .setFieldNamingPolicy(FieldNamingPolicy.UPPER_CAMEL_CASE)
                .create();

        TituloOMDB meuFilmeOMDB = gson.fromJson(json, TituloOMDB.class);
        //System.out.println(meuFilmeOMDB);
        Titulo meuFilme = new Titulo(meuFilmeOMDB);
        System.out.println(meuFilme);

        sc.close();
    }
}
