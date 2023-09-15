public class HttpResponse {

    String body;

    public HttpResponse(HttpRequest request) {
    }

    public HttpResponse(String body) {
        this.body = body;
    }

    String getResponse(){
        String responseLine = "HTTP/1.1 200 OK\r\n";
        String header1 = "Content-Type:text/html\r\n";
        String header2 = "Content-Length:" + body.getBytes().length + "\r\n";
        String spacer = "\r\n";
        return responseLine + header1 + header2 + spacer + body;
    }
}
