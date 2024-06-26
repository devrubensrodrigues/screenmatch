package br.com.alura.screenmatch.principal;

import br.com.alura.screenmatch.conectionAPI.ConnectionWithAPI;
import br.com.alura.screenmatch.excecao.ErroDeConversaoDeAnoException;
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
        ConnectionWithAPI connection = new ConnectionWithAPI();
        Scanner sc = new Scanner(System.in);

        System.out.print("Qual filme você quer pesquisar? ");
        var filme = sc.nextLine();

        var endereco = "https://www.omdbapi.com/?t=" + filme.replace(" ", "+") + "&apikey=53fd5cbd";

        try {
            String json = connection.bodyAPI(endereco);

            Gson gson = new GsonBuilder()
                    .setFieldNamingPolicy(FieldNamingPolicy.UPPER_CAMEL_CASE)
                    .create();

            TituloOMDB meuFilmeOMDB = gson.fromJson(json, TituloOMDB.class);
            //System.out.println(meuFilmeOMDB);
            Titulo meuFilme = new Titulo(meuFilmeOMDB);
            System.out.println("Titulo convertido:");
            System.out.println(meuFilme);
        } catch (NumberFormatException e) {
            System.out.println("Aconteceu um erro: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            System.out.println("Algum erro de argumento na busca, verifique o endereco");
        } catch (ErroDeConversaoDeAnoException e) {
            System.out.println(e.getMessage());
        }
        System.out.println("Programa finalizou corretamente !!");
        sc.close();
    }
}
