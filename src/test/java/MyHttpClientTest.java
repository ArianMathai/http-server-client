import org.junit.jupiter.api.Test;

public class MyHttpClientTest {
    @Test
    void test(){
        MyHttpClientTwo client = new MyHttpClientTwo("httpbin.org", "/html", 80);

        assert client.statusCode == 200;
        assert client.headers.get("Content-Type") != null;
        assert client.body != null;
    }
}
