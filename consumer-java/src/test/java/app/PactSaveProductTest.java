package app;

import au.com.dius.pact.consumer.Pact;
import au.com.dius.pact.consumer.PactProviderRuleMk2;
import au.com.dius.pact.consumer.PactVerification;
import au.com.dius.pact.consumer.dsl.PactDslJsonBody;
import au.com.dius.pact.consumer.dsl.PactDslWithProvider;
import au.com.dius.pact.model.PactSpecVersion;
import au.com.dius.pact.model.RequestResponsePact;
import com.jayway.jsonpath.JsonPath;
import org.junit.Rule;
import org.junit.Test;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;

public class PactSaveProductTest {

    @Rule
    public PactProviderRuleMk2 mockProvider = new PactProviderRuleMk2("product_provider", PactSpecVersion.V3, this);
    private RestTemplate restTemplate = new RestTemplate();

    @Pact(provider = "product_provider", consumer = "save_product_consumer")
    public RequestResponsePact createPact(PactDslWithProvider builder) {

        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", MediaType.APPLICATION_JSON_UTF8_VALUE);

        PactDslJsonBody bodyResponse = new PactDslJsonBody()
                .integerType("productId", 1)
                .stringType("productName", "a product name");

        return builder.given("create product")
                .uponReceiving("a request to save product")
                .path("/api/products")
                .body(bodyResponse)
                .method(RequestMethod.POST.name())
                .willRespondWith()
                .headers(headers)
                .status(200)
                .body(bodyResponse)
                .toPact();
    }

    @Test
    @PactVerification
    public void testCreateProductConsumer() {

        Map<String, Object> product = new HashMap<>();
        product.put("productId", 1);
        product.put("productName", "a product name");

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
        HttpEntity<Object> request = new HttpEntity<>(product, headers);

        ResponseEntity<String> responseEntity = restTemplate.postForEntity(mockProvider.getUrl() + "/api/products", request, String.class);

        assertEquals((Integer) 1, JsonPath.read(responseEntity.getBody(), "$.productId"));
        assertEquals("a product name", JsonPath.read(responseEntity.getBody(), "$.productName"));

    }
}
