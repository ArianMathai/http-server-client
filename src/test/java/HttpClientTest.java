import org.junit.jupiter.api.Test;

import java.io.IOException;

public class HttpClientTest {

    @Test
    void shouldMakeHttpCallAndGet200Response(){
        HttpClient client = new HttpClient("httpbin.org", 80, "/html");

        assert client.statusCode == 200;
        assert client.headers.get("Content-Type") != null;
        assert client.body != null;
    }

    @Test
    void makeServerGoBigBig() throws IOException {
        HttpServer server = new HttpServer(8181);
        HttpClient client = new HttpClient("localhos", 8181, "/");
    }

}
