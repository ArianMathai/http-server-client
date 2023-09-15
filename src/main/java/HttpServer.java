import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class HttpServer {
    public final ServerSocket serverSocket;
    public HttpServer(int port) throws IOException {
        this.serverSocket = new ServerSocket(port);

        while (true){
        Socket clientSocket = serverSocket.accept();
        HttpRequest request = new HttpRequest(clientSocket);
        HttpResponse response = handleRequest(request);

        clientSocket.getOutputStream().write(
                response.getResponse().getBytes(StandardCharsets.UTF_8));
        clientSocket.getOutputStream().close();
        clientSocket.close();
        }
    }
    private HttpResponse handleRequest(HttpRequest request){
        String path = request.getPath();

        if (path.equals("/test")){
            return new HttpResponse("<HTML><H1>This is the test page</H1></HTML>");
        }
        if (path.equals("/")){
            return new HttpResponse("<HTML><H1>This is the main page</H1></HTML>");
        }
        if (path.equals("/server")){
            return new HttpResponse("<HTML><H1>This is the server page</H1></HTML>");
        }
        else {
            return new HttpResponse("<HTML><H1>Page not found</H1></HTML>");
        }

    }
}
