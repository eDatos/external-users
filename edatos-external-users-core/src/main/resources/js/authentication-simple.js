var captchaGeneratedId = null;

var showCaptcha = function (options) {	
	if(isCaptchaEnabled()) {
		captchaGeneratedId = createCaptchaSessionKey();
		var imgUrl = new URL(/*[[${captchaPictureUrl}]]*/ "");
		imgUrl.searchParams.set('sessionKey', captchaGeneratedId);
		
		if(!options.captchaEl && options.captchaId) {
			options.captchaEl = document.getElementById(options.captchaId);
		}
	    options.captchaEl.insertAdjacentHTML('beforeend',
	        "<div id=\"captcha_container\" class=\"captcha captcha-simple\">" +
			"	<img src=\"" + imgUrl.href + "\">" + 
			"	<label for=\"code\"></label>" +
			"	<input type=\"text\" name=\"code\" id=\"code\"/>" +
	        "</div>");

		options.captchaEl.querySelector("#captcha_container img").className = options.imgClasses ? options.imgClasses : "captchaImg";
		options.captchaEl.querySelector("#captcha_container input").className = options.inputClasses ? options.inputClasses : "captchaInput";
		var labelEl = options.captchaEl.querySelector("#captcha_container label");
		labelEl.innerText = options.labelText ? options.labelText : "Escriba el texto que aparece en la imagen superior";
		labelEl.className = options.labelClasses ? options.labelClasses : "captchaLabel";
	}
};

var showCaptchaWithButton = function (request, url, options) {	
    if(isCaptchaEnabled()) {
		return new Promise((resolve, reject) => {
			showCaptcha(options);
			options.withButton = true;
	
			var $button = document.createElement("button");
			$button.className = options.buttonClasses ? options.buttonClasses : "captchaButton";
			$button.textContent = options.buttonText ? options.buttonText : "Enviar";
			options.captchaEl.querySelector("#captcha_container").appendChild($button);
	        $button.addEventListener("click", function (e) {
	            e.preventDefault();
	            requestWithCaptcha(request, url, options).then(val => resolve(val)).catch(error => reject(error));
	        }, false);
	    });
	} else {
		return requestWithCaptcha(request, url, options);
	}
};

var createCaptchaSessionKey = function() {
  return ([1e7]+-1e3+-4e3+-8e3+-1e11).replace(/[018]/g, c =>
    (c ^ crypto.getRandomValues(new Uint8Array(1))[0] & 15 >> c / 4).toString(16)
  )
};

var removeCaptcha = function(options) {
    options.captchaEl.removeChild(options.captchaEl.querySelector("#captcha_container"));
}

var isCaptchaEnabled = function() {
	return /*[[${captchaEnabled}]]*/ false;
}

var isCaptchaInvisible = function() {
	return false || !isCaptchaEnabled();
}

var sendRequestWithCaptcha = function(request, baseUrl, options) {
    var url = new URL(baseUrl);
	if(isCaptchaEnabled()) {		
	    url.searchParams.set('userValue', options.captchaEl.querySelector("#captcha_container input").value);
	    url.searchParams.set('sessionKey', captchaGeneratedId);
	}
    return request(url.href);
};

var requestWithCaptcha = function(request, url, options) {
	if(!options.captchaEl && options.captchaId) {
		options.captchaEl = document.getElementById(options.captchaId);
	}
    return new Promise((resolve, reject) => {
        sendRequestWithCaptcha(request, url, options).then(function (response) {
            resolve(response);
        }).catch(function (error) {
			if(isCaptchaEnabled()) {	
				removeCaptcha(options);
				if(options.withButton) {
					showCaptchaWithButton(request, url, options).then(val => resolve(val)).catch(error => reject(error));
				} else {
					showCaptcha(options);
					reject(error);				
				}
			} else {
				reject(error);
			}
        });
    });
};