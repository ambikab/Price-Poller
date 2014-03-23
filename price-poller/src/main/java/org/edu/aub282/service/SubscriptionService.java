/**
 * 
 */
package org.edu.aub282.service;

import java.util.ArrayList;
import java.util.HashMap;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import org.edu.aub282.enums.Result;
import org.edu.aub282.model.Product;
import org.edu.aub282.model.User;
import org.edu.aub282.repository.ProductRepository;
import org.edu.aub282.repository.SubscriptionRepository;
import org.edu.aub282.repository.UserRepository;
import org.edu.aub282.util.JsonParser;

/**
 * @author ambika_b
 *
 */

@Path("/subscriptions")
public class SubscriptionService {

	UserRepository userRepository = new UserRepository();

	SubscriptionRepository subscriptionRepository = new SubscriptionRepository();

	ProductRepository productRepository = new ProductRepository();

	@POST
	@Produces(MediaType.APPLICATION_JSON)
	public String createSubscriptions(@FormParam("userMailId")String userMailId, @FormParam("productId")String productId) {
		HashMap<String, String> response = new HashMap<String, String>();
		User user = userRepository.getUser(userMailId);
		if (user == null) { 
			userRepository.createUser(userMailId);
			user = userRepository.getUser(userMailId);
		} else {
			ArrayList<Product> products = productRepository.fetchProducts(Long.valueOf(productId));
			if (subscriptionRepository.isSubscribed(user, products)) {
				response.put("STATUS", Result.FAILURE.statusValue());
				response.put("MESSAGE", "You are subscribed to this already!");
				return JsonParser.convertObjectToJson(response);
			} 
		}
		ArrayList<Product> products = productRepository.fetchProducts(Long.valueOf(productId));
		if (subscriptionRepository.addSubscriptions(user, products)) {
			response.put("STATUS", Result.SUCCESS.statusValue());
			response.put("MESSAGE", "Hurray!! you are subscribed!! ");
		} else {
			response.put("STATUS", Result.FAILURE.statusValue() );
			response.put("MESSAGE", "Ouch!! something went wrong. Please try after sometime.");
		}
		return  JsonParser.convertObjectToJson(response);
	}

}