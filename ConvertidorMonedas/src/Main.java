import com.google.gson.Gson;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Scanner;
import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String base = "", cambio = "";
        double cantidad = 0, result = 0;
        int opcion=0;

        while(opcion != 8){
            System.out.println("Bienvenidos al conversor de monedas\n\n" +
                    "1. Quetzal a Dolar\n" +
                    "2. Dolar a Quetzal\n" +
                    "3. Real brasileño a Dolar\n" +
                    "4. Dolar a Real Brasileño\n" +
                    "5. Peso colombiano a Dolar\n" +
                    "6. Dolar a Peso colombiano\n" +
                    "7. Convertir otra moneda\n" +
                    "8. Salir\n");
            System.out.print("Ingrese la opcion a realiar: ");
            opcion = scanner.nextInt();
            scanner.nextLine();
            if(opcion < 7){
                System.out.println("Ingrese la cantidad de la moneda base: ");
                cantidad = scanner.nextDouble();
                scanner.nextLine();
            }

            switch (opcion){
                case 1:
                    base = "GTQ";
                    cambio = "USD";
                    result = Conversion(base, cambio, cantidad);
                    break;
                case 2:
                    base = "USD";
                    cambio = "GTQ";
                    result = Conversion(base, cambio, cantidad);
                    break;
                case 3:
                    base = "BRL";
                    cambio = "USD";
                    result = Conversion(base, cambio, cantidad);
                    break;
                case 4:
                    base = "USD";
                    cambio = "BRL";
                    result = Conversion(base, cambio, cantidad);
                    break;
                case 5:
                    base = "COP";
                    cambio = "USD";
                    result = Conversion(base, cambio, cantidad);
                    break;
                case 6:
                    base = "USD";
                    cambio = "COP";
                    result = Conversion(base, cambio, cantidad);
                    break;
                case 7:
                    System.out.println("Ejemplos de codigos: JPY, EUR, GBP");
                    System.out.println("Ingrese el codigo de la moneda base: ");
                    base = scanner.nextLine();
                    System.out.println("Ingrese la cantidad de la moneda base: ");
                    cantidad = scanner.nextDouble();
                    scanner.nextLine();
                    System.out.println("Ingrese el codigo de la moneda cambio final: ");
                    cambio = scanner.nextLine();
                    result = Conversion(base, cambio, cantidad);
                    break;
            }

            if(opcion != 8){
                System.out.println("El resultado del cambio de " + cantidad + " " + base + " a " + cambio + " es: " + result);
                System.out.println("Press any key to continue...");
                try {
                    System.in.read();
                } catch (IOException e) {
                    System.err.println("Error reading from keyboard: " + e.getMessage());
                }
            }
        }
    }

    public static double Conversion(String base, String cambio, double cantidad){
        URI uri = URI.create("https://v6.exchangerate-api.com/v6/e50f8876ac286aaf6150db27/latest/" + base);
        Gson gson = new Gson();
        Intercambio inter = null;
        double result = 0;

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder().uri(uri).build();

        try{
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            inter = gson.fromJson(response.body(), Intercambio.class);
        }catch(Exception e){
            e.printStackTrace();
        }


        if(inter.result.equals("success")){
            result = inter.conversion_rates.get(base) * cantidad * inter.conversion_rates.get(cambio);
        }

        return result;
    }
}