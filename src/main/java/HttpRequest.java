import java.io.IOException;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public class HttpRequest {

    private String requestLine;
    private Map<String, String> headers;

    public HttpRequest(Socket clientSocket) throws IOException {
        headers = new HashMap<>();
        requestLine = readLine(clientSocket);

        String headerLine;
        while ( !(headerLine = readLine(clientSocket)).isEmpty()) {
            String[] headerParts = headerLine.split(" *: *",2);
            headers.put(headerParts[0], headerParts[1]);
        }
        printThings();
    }
    private String readLine(Socket socket) throws IOException {
        StringBuilder sb = new StringBuilder();
        int character;

        while( (character = socket.getInputStream().read() ) != '\r'){
            sb.append( (char) character);
        }
        character = socket.getInputStream().read();

        return sb.toString();
    }
    private void printThings(){
        System.out.println(requestLine);
        headers.entrySet().forEach(System.out::println);
    }
}
