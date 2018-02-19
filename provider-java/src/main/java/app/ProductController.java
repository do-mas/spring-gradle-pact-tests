package app;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/api")
public class ProductController {
	
	@PostMapping(value="/products",produces= MediaType.APPLICATION_JSON_UTF8_VALUE)
	public Product saveInventory(@RequestBody Product inventory){
		return inventory;
	}

	@GetMapping(value="/products/{productId}",produces= MediaType.APPLICATION_JSON_UTF8_VALUE)
	public Product saveInventory(@PathVariable Integer productId){
		return new Product(productId, "a product name");
	}

	@GetMapping(value="/products",produces= MediaType.APPLICATION_JSON_UTF8_VALUE)
	public List<Product> saveInventory(){
		return Arrays.asList(
				new Product(1, "a product name 1"),
				new Product(2, "a product name 2")
		);
	}

}
