package br.com.alura.screenmatch.principal;

import br.com.alura.screenmatch.conectionAPI.ConnectionWithAPI;
import br.com.alura.screenmatch.excecao.ErroDeConversaoDeAnoException;
import br.com.alura.screenmatch.modelos.Titulo;
import br.com.alura.screenmatch.modelos.TituloOMDB;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.FileWriter;
import java.io.IOException;
import java.sql.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class PrincipalComBusca {
    public static void main(String[] args) throws IOException, InterruptedException {
        ConnectionWithAPI connection = new ConnectionWithAPI();
        Scanner sc = new Scanner(System.in);
        List<Titulo> tituloList = new ArrayList<>();

        Gson gson = new GsonBuilder()
                .setFieldNamingPolicy(FieldNamingPolicy.UPPER_CAMEL_CASE)
                .setPrettyPrinting()
                .create();

        var filme = " ";
        while(!filme.equalsIgnoreCase("sair")) {

            System.out.print("Qual filme você quer pesquisar? ");
            filme = sc.nextLine();
            if(filme.equalsIgnoreCase("sair")) {
                break;
            }

            try {
                var endereco = "https://www.omdbapi.com/?t=" + filme.replace(" ", "+") + "&apikey=53fd5cbd";


                String json = connection.bodyAPI(endereco);

                TituloOMDB meuFilmeOMDB = gson.fromJson(json, TituloOMDB.class);
                Titulo meuFilme = new Titulo(meuFilmeOMDB);

                tituloList.add(meuFilme);

            } catch (NumberFormatException e) {
                System.out.println("Aconteceu um erro: " + e.getMessage());
            } catch (IllegalArgumentException e) {
                System.out.println("Algum erro de argumento na busca, verifique o endereco");
            } catch (ErroDeConversaoDeAnoException e) {
                System.out.println(e.getMessage());
            }
        }
        tituloList.forEach(System.out::println);

        FileWriter escrita = new FileWriter("filme.json");
        escrita.write(gson.toJson(tituloList));
        escrita.close();

        System.out.println("Programa finalizou corretamente !!");
        sc.close();
    }
}
