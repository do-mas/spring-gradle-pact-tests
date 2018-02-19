package app;

import au.com.dius.pact.consumer.Pact;
import au.com.dius.pact.consumer.PactProviderRuleMk2;
import au.com.dius.pact.consumer.PactVerification;
import au.com.dius.pact.consumer.dsl.PactDslJsonArray;
import au.com.dius.pact.consumer.dsl.PactDslJsonBody;
import au.com.dius.pact.consumer.dsl.PactDslWithProvider;
import au.com.dius.pact.model.PactSpecVersion;
import au.com.dius.pact.model.RequestResponsePact;
import org.junit.Rule;
import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

public class PactGetProductListTest {

    @Rule
    public PactProviderRuleMk2 mockProvider = new PactProviderRuleMk2("product_provider", PactSpecVersion.V3, this);
    private RestTemplate restTemplate = new RestTemplate();

    @Pact(provider = "product_provider", consumer = "get_product_list_consumer")
    public RequestResponsePact createPact(PactDslWithProvider builder) {

        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", MediaType.APPLICATION_JSON_VALUE);

        PactDslJsonBody response2 = new PactDslJsonArray()
                .object()
                .integerType("productId")
                .stringType("productName")
                .closeObject()
                .object()
                .integerType("productId")
                .stringType("productName")
                .asBody();

        return builder.given("get products list")
                .uponReceiving("a request to get products list")
                .path("/api/products")
                .method(RequestMethod.GET.name())
                .willRespondWith()
                .headers(headers)
                .status(200)
                .body(response2)
                .toPact();
    }

    @Test
    @PactVerification
    public void testCreateProductConsumer() {
        restTemplate.getForEntity(mockProvider.getUrl() + "/api/products", String.class);
    }
}
