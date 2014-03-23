/**
 * 
 */
package org.edu.aub282.service;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.edu.aub282.repository.ProductRepository;
import org.edu.aub282.util.JsonParser;

/**
 * @author ambika_b
 *
 */
@Path("/products")
public class ProductService {

	ProductRepository productRepository = new ProductRepository();
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public String getAllProducts() {
		return JsonParser.convertObjectToJson(productRepository.getDistinctProducts()).toString();
	}
}