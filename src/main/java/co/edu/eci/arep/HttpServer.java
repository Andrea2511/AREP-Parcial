package co.edu.eci.arep;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
import java.lang.reflect.Array;
import java.net.*;
import java.io.*;
import java.util.Arrays;

public class HttpServer {
    public static void main(String[] args) throws IOException, URISyntaxException {

        ServerSocket serverSocket = null;
        Socket clientSocket = null;

        Boolean running = true;
        while(running) {
            try {
                serverSocket = new ServerSocket(35000);
                try {
                    System.out.println("Listo para recibir ...");
                    clientSocket = serverSocket.accept();
                } catch (IOException e) {
                    System.err.println("Accept failed.");
                    System.exit(1);
                }
            } catch (IOException e) {
                System.err.println("Could not listen on port: 35000.");
                System.exit(1);
            }

            PrintWriter out = new PrintWriter(
                    clientSocket.getOutputStream(), true);

            BufferedReader in = new BufferedReader(
                    new InputStreamReader(clientSocket.getInputStream()));

            String inputLine, outputLine;
            StringBuffer response = new StringBuffer();


            while ((inputLine = " " + in.readLine()) != null) {
                response.append(inputLine);
                System.out.println("Recibí: " + inputLine);

                if (!in.ready()) {
                    break;
                }
            }

            String[] r = response.toString().split(" ");
            System.out.println("response: " + Arrays.toString(response.toString().split(" ")));

            String path = r[5];
            String query = r[2];
            System.out.println("path " + path);
            System.out.println("query " + query);

            String uri = path + query;

            URI absolutePath = new URI(uri);
            System.out.println("uri: " + uri);

            outputLine = handleRequest(absolutePath);
            out.println(outputLine);
            out.close();
            in.close();
            clientSocket.close();
            serverSocket.close();
        }
    }

    private static String handleRequest(URI uri){

        String root = "/resources";

        return "HTTP/1.1 200 OK\r\n"
                + "Content-Type: text/html\r\n"
                + "\r\n"
                + "<!DOCTYPE html>\n"
                + "<html>\n"
                + "<head>\n"
                + "<meta charset=\"UTF-8\">\n"
                + "<title>Title of the document</title>\n"
                + "</head>\n"
                + "<body>\n"
                + "<h1>Mi propio mensaje</h1>\n"
                + "</body>\n"
                + "</html>\n";

    }
}