import java.io.IOException;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public class MyHttpClient {

    public final String status;
    public final int statusCode;
    public final String body;
    public final Map<String, String> headers = new HashMap<>();

    public MyHttpClient(String host, String url, int port) {
        try(Socket socket = new Socket(host, port)){

            String request = String.format("GET %s HTTP/1.1\r\nHost: %s \r\n\r\n", url, host);

            socket.getOutputStream().write(request.getBytes());

            String[] reply = readLine(socket).split(" ", 3);

            statusCode = Integer.parseInt(reply[1]);
            status = reply[2];

            String headerLine;
            while ( !(headerLine = readLine(socket)).isEmpty()){
                String[] headerParts = headerLine.split(" *: *", 2);
                headers.put(headerParts[0], headerParts[1]);
            }

            StringBuilder dadBod = new StringBuilder();
            for (int i = 0; i < getContentLength(); i++){

                dadBod.append((char)socket.getInputStream().read());
            }
            body = dadBod.toString();



        }
        catch(IOException e){
            throw new RuntimeException(e);
        }
    }

    private int getContentLength(){
        return Integer.parseInt(headers.get("Content-Length"));
    }

    private String readLine(Socket socket) throws IOException {
        StringBuilder builder = new StringBuilder();
        int character;

        while ( (character = socket.getInputStream().read() ) != '\r'){
            builder.append((char) character);
        }
        character = socket.getInputStream().read();

        if (character != '\n')
            throw new IllegalStateException("Invalid http header - \\r not followed by \\n");

        return builder.toString();

    }
}
