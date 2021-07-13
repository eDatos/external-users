var recaptchaScript = document.createElement("script");
recaptchaScript.src = "https://www.google.com/recaptcha/api.js?render=" + (/*[[${recaptchaSiteKey}]]*/ "");
document.head.appendChild(recaptchaScript);

var sendRequestWithCaptcha = function(request, baseUrl, recaptchaToken) {
    var url = new URL(baseUrl);
    url.searchParams.set('userValue', recaptchaToken);
    return request(url.href);
};

var isCaptchaInvisible = function() {
	return true;
}

var requestWithCaptcha = function(request, url, options) {
    return new Promise((resolve, reject) => {
		grecaptcha.ready(function() {
			grecaptcha.execute((/*[[${recaptchaSiteKey}]]*/ ""), {action: 'submit'}).then(function(token) {
				sendRequestWithCaptcha(request, url, token).then(function (response) {
		            resolve(response);
		        }).catch(function (error) {
					reject(error);
				});
			});
		});
    });
}