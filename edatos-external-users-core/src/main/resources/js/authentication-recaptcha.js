var showCaptcha = function () {	
	return;
};

var showCaptchaWithButton = function (request, url, options) {	
	return requestWithCaptcha(request, url, options);
};

var sendRequestWithCaptcha = function(request, baseUrl, recaptchaToken) {
    var url = new URL(baseUrl);
	if(isCaptchaEnabled()) {
	    url.searchParams.set('userValue', recaptchaToken);
	}
    return request(url.href);
};

var isCaptchaEnabled = function() {
	return /*[[${captchaEnabled}]]*/ false;
};

var isCaptchaInvisible = function() {
	return true;
};

var requestWithCaptcha = function(request, url, options) {
    return new Promise((resolve, reject) => {
		if(isCaptchaEnabled()) {			
			grecaptcha.ready(function() {
				grecaptcha.execute((/*[[${recaptchaSiteKey}]]*/ ""), {action: 'submit'}).then(function(token) {
					sendRequestWithCaptcha(request, url, token).then(function (response) {
			            resolve(response);
			        }).catch(function (error) {
						reject(error);
					});
				});
			});
		} else {
			sendRequestWithCaptcha(request, url).then(function (response) {
	            resolve(response);
	        }).catch(function (error) {
				reject(error);
			});
		}
    });
}

if(isCaptchaEnabled()) {
	var recaptchaScript = document.createElement("script");
	recaptchaScript.src = "https://www.google.com/recaptcha/api.js?render=" + (/*[[${recaptchaSiteKey}]]*/ "");
	document.head.appendChild(recaptchaScript);	
}