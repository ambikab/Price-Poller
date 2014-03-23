/**
 * 
 */
package org.edu.aub282.jobs;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.edu.aub282.dto.ProductDTO;
import org.edu.aub282.dto.SearchResultsDTO;
import org.edu.aub282.enums.Progress;
import org.edu.aub282.model.Category;
import org.edu.aub282.model.EmailBatch;
import org.edu.aub282.model.NotificationEmailLog;
import org.edu.aub282.repository.CategoryRepository;
import org.edu.aub282.repository.EmailBatchRepository;
import org.edu.aub282.repository.NotificationEmailLogRepository;
import org.edu.aub282.repository.ProductRepository;
import org.edu.aub282.repository.SubscriptionRepository;
import org.edu.aub282.util.DateTimeUtil;
import org.edu.aub282.util.EmailUtil;
import org.edu.aub282.util.JsonParser;
import org.edu.aub282.util.RESTClient;

/**
 * Updates the price after a given time interval.
 * @author ambika_b
 *
 */

@Path("/pricePoller")
public class PricePoll {

	SubscriptionRepository subscriptionRepository = new SubscriptionRepository();

	ProductRepository productRepository = new ProductRepository();

	EmailBatchRepository emailBatchRepository = new EmailBatchRepository();

	CategoryRepository categoryRepository = new CategoryRepository();

	NotificationEmailLogRepository notificationEmailLogRepository = new NotificationEmailLogRepository();

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public String notifyUsers() {
		if (!emailBatchRepository.isAllPreviousComplete()) return "There are previous incomplete jobs. PLease try again later.";
		EmailBatch currentBatch = emailBatchRepository.startNewBatch();
		if (currentBatch == null) return "exception in running the job. Please consult the logs";

		List<Category> categories = categoryRepository.getAllCategory();
		currentBatch.setStatus(Progress.INPROGRESS.getProgressValue());
		currentBatch.setDate(DateTimeUtil.getCurrentTimeStamp());
		emailBatchRepository.updateStatus(currentBatch);

		for (Category category : categories) 
			updatePrice(category);

		blastEmail(currentBatch);

		currentBatch.setStatus(Progress.COMPLETE.getProgressValue());
		currentBatch.setDate(DateTimeUtil.getCurrentTimeStamp());

		emailBatchRepository.updateStatus(currentBatch);

		return "Job run complete :) ";
	}

	public void blastEmail(EmailBatch currentBatch) {
		String subject = "Wooohoo!! Prices slashed!!";
		List<NotificationEmailLog> notifyUsers = notificationEmailLogRepository.getNotifySubscriptions(currentBatch);
		if (notifyUsers == null) return ; //No users to notify.

		HashMap<String, ArrayList<NotificationEmailLog>> letters = new HashMap<String, ArrayList<NotificationEmailLog>>();
		for (NotificationEmailLog notifyUser : notifyUsers) {
			ArrayList<NotificationEmailLog> products;
			if (letters.containsKey(notifyUser.getUserEmail()))
				products = letters.get(notifyUser.getUserEmail());
			else 
				products = new ArrayList<NotificationEmailLog>();
			products.add(notifyUser);
			letters.put(notifyUser.getUserEmail(), products);
		}

		Iterator<String> keys = letters.keySet().iterator();
		String style = "<style type=\"text/css\"> " 
				+ " table.subscription {font-size:12px;color:#333333;width:100%;border-width: 1px;border-color: #729ea5;border-collapse: collapse;} "
				+ "table.subscription th {font-size:12px;background-color:#acc8cc;border-width: 1px;padding: 8px;border-style: solid;border-color: #729ea5;text-align:left;} "
				+ " table.subscription tr {background-color:#d4e3e5;} "
				+ " table.subscription td {font-size:12px;border-width: 1px;padding: 8px;border-style: solid;border-color: #729ea5;}" 
				+ " </style>";
		String contents = "<p> Its time for shopping!! Now enjoy your shopping at Zappos, at reduced prices on your favourite and most eyed items!! " 
				+ " Happy shopping :) </p>";
		while(keys.hasNext()) {
			String table = "<table align = 'center' class = 'subscription'>", tableEnd = "</table>", row = "<tr>", rowEnd = "</tr>", col = "<td>", colEnd = "</td>";
			String email = keys.next();
			ArrayList<NotificationEmailLog> subscriptions = letters.get(email);
			String tBody = "";
			for(NotificationEmailLog subscription : subscriptions ) {
				String col1 = col + "<a href = \"www.zappos.com/product/" + subscription.getProductId() + "/\"> "  + subscription.getProductName() + " @ Zappos!! </a> <hr/> " + colEnd;
				String col2 = col + "<img src=\"" + subscription.getProductUrl() + "\"/>" + colEnd;
				tBody = tBody + row + col1 + col2 + rowEnd ;
			}
			table = table + tBody + tableEnd;
			String html = "<html> <head> " + style + " </head> <body> " + contents + table + "</body> </html>"; 
			EmailUtil.sendEmail(email, subject, html);
		}
	}

	public SearchResultsDTO getMetaData(String url) {
		SearchResultsDTO results = (SearchResultsDTO) JsonParser.convertJsonToObject(RESTClient.httpGet(url), SearchResultsDTO.class);
		return results;
	}

	// Since the local db has only 300 records, i have used 3. In real time 
	// it should be calculated with metaData / limit.
	// find the total number of pages given load.
	public long getTotalPageNumber(long total, long perPageCount) {
		return 3;
	}

	/**
	 * Updates the prices of a given category.
	 * @param category
	 */
	public void updatePrice(Category category) {
		int limit = 1, page = 1;
		String baseUrl = "http://api.zappos.com/Search";
		String term = "/term/" + category.getCategoryName();
		String key = "&key=" + RESTClient.key;
		String pagination = "?limit=" + limit + "&page=" + page;
		SearchResultsDTO metaData = getMetaData(baseUrl + term + pagination + key);
		limit = 100;
		ArrayList<ProductDTO> products = new ArrayList<ProductDTO>();
		long totalNumberOfPages = getTotalPageNumber(metaData.getTotalResultCount(), limit);
		for (long curPage = 1; curPage <= totalNumberOfPages; curPage++ ) {
			pagination = "?sort={\"productId\":\"desc\"}&limit=" + limit + "&page=" + curPage;
			String jsonResult = RESTClient.httpGet(baseUrl + term + pagination + key);
			SearchResultsDTO searchResults = (SearchResultsDTO) JsonParser.convertJsonToObject(jsonResult, SearchResultsDTO.class);
			products.addAll(searchResults.getResults());
		}
		productRepository.batchUpdate(products);
	}

/*	public static void insertProducts() {
		String baseUrl = "http://api.zappos.com/Search";
		String term = "/term/" + "boots";
		String key = "&key=" + "52ddafbe3ee659bad97fcce7c53592916a6bfd73";
		String pagination = "?sort={\"productId\":\"desc\"}&limit=" + 100 + "&page=" + 3;
		String jsonResult = RESTClient.httpGet(baseUrl + term + pagination + key);
		SearchResultsDTO searchResults = (SearchResultsDTO) JsonParser.convertJsonToObject(jsonResult, SearchResultsDTO.class);
		System.out.println(searchResults.getResults().size());
		productRepository.batchInsert(searchResults.getResults());
	} 

	public static void main(String args[]) {
		insertProducts();
	}*/
}