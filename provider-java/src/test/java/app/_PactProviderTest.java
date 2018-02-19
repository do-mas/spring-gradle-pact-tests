package app;

import au.com.dius.pact.provider.junit.PactRunner;
import au.com.dius.pact.provider.junit.Provider;
import au.com.dius.pact.provider.junit.State;
import au.com.dius.pact.provider.junit.loader.PactFolder;
import au.com.dius.pact.provider.junit.target.HttpTarget;
import au.com.dius.pact.provider.junit.target.Target;
import au.com.dius.pact.provider.junit.target.TestTarget;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.springframework.boot.SpringApplication;

@RunWith(PactRunner.class)
@Provider("product_provider")
@PactFolder("pacts")
public class _PactProviderTest {

    @TestTarget
    public final Target target = new HttpTarget("http", "localhost", 8080, "");

    @BeforeClass
    public static void start() {
        SpringApplication.run(Application.class);
    }

    @State("get products list")
    public void toGetState() {
    }
    @State("get product by id")
    public void sss() {
    }
    @State("create product")
    public void ss1s() {
    }

}