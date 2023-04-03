package JavaTickers;

import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URI;
import java.net.URL;
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
        var gerador = new GeradorDeFigurinhas();
        for (Map<String,String> filme : ListaDeFilmes){

            String urlImagem = filme.get("image");
            String urlImagemMaior = urlImagem.replaceFirst("(@?\\.)([0-9A-Z, ]+).jpg$","$1.jpg");
            String titulo = filme.get("title");
            double classificacao = Double.parseDouble(filme.get("imDbRating"));

            String textoFigurinhas;
            InputStream imagemJulia;
            if (classificacao >= 7.0 ){
                textoFigurinhas = "TOPZERA";
                imagemJulia = new FileInputStream("sobreposicao/bom.png");
            }else{
                textoFigurinhas = "HMMM";
                imagemJulia = new FileInputStream("sobreposicao/ruim.png");
            }

            InputStream inputStream = new URL(urlImagemMaior).openStream();
            String nomeArquivo = titulo + ".png";

            gerador.cria(inputStream, nomeArquivo, textoFigurinhas, imagemJulia);

            System.out.println(titulo);
            System.out.println();
        }

    }
}
