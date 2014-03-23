$("#subscriptionBtn").click (function() {
	var emailId = $("#emailId").val();
	if (!validate(emailId)) { 
		$("#notification").html("");
		$("#notification").html("<span class = 'error'>Kindly check the email ID.</span>");
		return false;
	}
	addSubscription(emailId,$("#productId").val() );
});

function validate() {
	var emailId = $("#emailId").val();
	if (emailId.length === 0) return false;
	var reg = /^\w+([-+.']\w+)*@\w+([-.]\w+)*\.\w+([-.]\w+)*$/
	return (reg.test(emailId));
}

function addSubscription(userMailId, productId) {
		var params = "userMailId=" + userMailId + "&productId=" + productId;
		$.ajax({
		type: "POST",
		url: "http://localhost:8080/price-poller/services/subscriptions",
		data : params, 
		dataType: "json",
		success: function(result){
			if (result.STATUS == 'SUCCESS') 
				status = "<span class = 'success'>  Successfully subscribed</span>";
			else 
				status = "<span class = 'error'> " +  result.MESSAGE + " </span>";
			$("#notification").html("");
			$("#notification").html(status);
		}
	});
}