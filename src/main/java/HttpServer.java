import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public class HttpServer {

    public final ServerSocket serverSocket;
    public final String requestLine;
    public final Map<String, String> headers;


    public HttpServer(int port) throws IOException {
        this.serverSocket = new ServerSocket(port);
        headers = new HashMap<>();

        Socket clientSocket = serverSocket.accept();

        requestLine = readLine(clientSocket);
        System.out.println(requestLine);;

        String headerLine;
        while ( !(headerLine = readLine(clientSocket)).isEmpty()) {
            String[] headerParts = headerLine.split(" *: *",2);
            headers.put(headerParts[0], headerParts[1]);
        }
        printThings();

    }
    private void printThings(){
        System.out.println(requestLine);
        headers.entrySet().forEach(System.out::println);
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
}
