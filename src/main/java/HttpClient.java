import java.io.IOException;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public class HttpClient {

    public final int statusCode;
    public final Map<String, String> headers = new HashMap<>();
    public final String status;
    public final String body;


    /*
    * Reply from get request:
    *
    * HTTP/1.1 200 OK
    *
    * headers(key:value)
    *
    * body
    *
    * */




    public HttpClient(String host, int port, String url) {




        try(Socket socket = new Socket(host, port)){

            // String request = "GET " + url + " HTTP/1.1\r\n" + "Host: " + host + "\r\n\r\n";
            String request = String.format("GET %s HTTP/1.1\r\nHost: %s \r\n\r\n", url, host); // Ender på /r/n/r/n for å si at vi er ferdig

            System.out.println(request);

            socket.getOutputStream().write(request.getBytes());

            String[] statusLine = readLine(socket).split(" ", 3);
            /*
            for (int i = 0; i<3; i++)
                System.out.println(statusLine[i]);

             */

            statusCode = Integer.parseInt(statusLine[1]);
            status = statusLine[2];

            String headerLine;
            while ( !(headerLine = readLine(socket)).isEmpty()) {
                String[] headerParts = headerLine.split(" *: *",2);
                headers.put(headerParts[0], headerParts[1]);
            }


            headers.forEach((key, value) -> System.out.printf("key: %s value: %s" + "\n", key, value));

            StringBuilder body = new StringBuilder();
            for (int i = 0; i < getContentLength(); i++){
                body.append((char) socket.getInputStream().read());
            }
            this.body = body.toString();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    private int getContentLength(){

        return Integer.parseInt(headers.get("Content-Length"));
    }

    private String readLine(Socket socket) throws IOException {
        StringBuilder sb = new StringBuilder();
        int character;

        while( (character = socket.getInputStream().read() ) != '\r'){
            sb.append( (char) character);
        }
        character = socket.getInputStream().read();
        if (character != '\n') {
            throw new IllegalStateException("Invalid http header - \\r not followed by \\n");
        }
        return sb.toString();
    }

}
