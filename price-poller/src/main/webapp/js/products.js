$(document).ready(function(){ 
	fetchProducts();
});

function fetchProducts() {
var options = "";
$.ajax({
		type: "GET",
		url: "http://localhost:8080/price-poller/services/products",
		dataType: "json",
		success: function(result){ 
			$.each(result, function(value) { 
				options = options + "<option value = " + result[value] + ">" + result[value] + "</option>"
			});
			$("#productId").append(options);
		}
	});
	
}