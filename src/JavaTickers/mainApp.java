package JavaTickers;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.util.List;
import java.util.Map;

public class mainApp {
    public static void main(String[] args) throws Exception {

        // fazer uma conexão HTTP (protocolo para acessar web) e buscar os tops 250 filmes

        String url = "https://raw.githubusercontent.com/lukadev08/lukadev08.github.io/main/apidata/imdbmostpopularmovies.json";
        URI endereco = URI.create(url);
        var client = HttpClient.newHttpClient();
        var request = HttpRequest.newBuilder(endereco).GET().build();
        HttpResponse<String> response = client.send(request, BodyHandlers.ofString());
        String body = response.body();

        // extrair só os dados que interessam (titulo, poster, classificação)
        JsonParser parser = new JsonParser();
        List<Map<String, String>> ListaDeFilmes = parser.parse(body);

        // exibir e manipular os dados

        for (Map<String,String> filme : ListaDeFilmes){
            System.out.println();
            System.out.println("\u001b[1mTítulo:\u001b[m " + filme.get("title"));
            System.out.println("\u001b[1mURL da imagem:\u001b[m " + filme.get("image"));
            System.out.println("\u001b[45m\u001b[1mClassificação:\u001b[m " + filme.get("imDbRating"));
            double rating = Double.parseDouble(filme.get("imDbRating"));
            int starNumber = (int) rating;
            for (int n = 1; n <= starNumber; n++) {
                    System.out.print("\uD83C\uDF1F");
            }

                System.out.println();
        }

    }
}
