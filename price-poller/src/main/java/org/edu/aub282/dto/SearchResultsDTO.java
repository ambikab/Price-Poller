package org.edu.aub282.dto;

import java.util.ArrayList;

/**
 * 
 * @author ambika_b
 *
 */
public class SearchResultsDTO {

	int limit;
	
	int statusCode;
	
	ArrayList<ProductDTO> results;
	
	String term, originalTerm;
	
	int currentResultCount;
	
	long totalResultCount;
	
	public String getOriginalTerm() {
		return originalTerm;
	}

	public void setOriginalTerm(String originalTerm) {
		this.originalTerm = originalTerm;
	}

	public int getLimit() {
		return limit;
	}

	public void setLimit(int limit) {
		this.limit = limit;
	}

	public int getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(int statusCode) {
		this.statusCode = statusCode;
	}

	public ArrayList<ProductDTO> getResults() {
		return results;
	}

	public void setResults(ArrayList<ProductDTO> results) {
		this.results = results;
	}

	public String getTerm() {
		return term;
	}

	public void setTerm(String term) {
		this.term = term;
	}

	public int getCurrentResultCount() {
		return currentResultCount;
	}

	public void setCurrentResultCount(int currentResultCount) {
		this.currentResultCount = currentResultCount;
	}

	public long getTotalResultCount() {
		return totalResultCount;
	}

	public void setTotalResultCount(long totalResultCount) {
		this.totalResultCount = totalResultCount;
	}

	@Override
	public String toString() {
		return "SearchResultsDTO [limit=" + limit + ", statusCode="
				+ statusCode + ", results=" + results + ", term=" + term
				+ ", currentResultCount=" + currentResultCount
				+ ", totalResultCount=" + totalResultCount + "]";
	}
	
}