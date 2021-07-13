
var showCaptcha = function (options, done) {
    var captchaGeneratedId = "captcha_generated_" + Math.random().toString(36).slice(2);
	var captchaContainerGeneratedId = captchaGeneratedId + "_container";
	var captchaButtonGeneratedId = captchaGeneratedId + "_button";
	
    options.captchaEl.insertAdjacentHTML('beforeend',
        "<div id=\"" + captchaContainerGeneratedId + "\" class=\"captcha captcha-simple\">" +
        "	<div id=\"" + captchaGeneratedId + "\">" +
        "		<table style=\"width: 100%;\">" +
        "			<tr>" +
        "       		<td class=\"formevenrow\" nowrap=\"nowrap\" >" +
        "					<img src=\"" + (/*[[${captchaPictureUrl}]]*/ "") + "\" class=\"" + (options.imgClasses ? options.imgClasses : "captchaImg") + "\">" +
        "				</td>" +
        "			</tr>" +
        "			<tr>" +
        "           	<td class=\"formevenrow\" align=\"right\" width=\"120\">" +
        "               	<label for=\"codigo\" class=\"" + (options.labelClasses ? options.labelClasses : "captchaLabel") + "\">Escriba el texto que aparece en la imagen superior</label>" +
        "               </td>" +
        "			</tr>" +
        "			<tr>" +
        "           	<td class=\"formevenrow\" nowrap=\"nowrap\" align=\"center\">" +
        "               	<input type=\"text\" name=\"codigo\" tabindex=\"1002\" id=\"codigo\" class=\"" + (options.inputClasses ? options.inputClasses : "captchaInput") + "\" />" +
        "               </td>" +
        "			</tr>" +
        "		</table>" +
        "	</div>" +
        "	<button id=\"" + captchaButtonGeneratedId + "\" class=\"" + (options.buttonClasses ? options.buttonClasses : "captchaButton") + "\">Enviar</button>" +
        "</div>");

    var $button = document.getElementById(captchaButtonGeneratedId);
    if ($button.addEventListener) {  // all browsers except IE before version 9
        $button.addEventListener("click", function (e) {
            e.preventDefault();
            done(document.getElementById('codigo').value);
            removeCaptcha(options, captchaContainerGeneratedId);
        }, false);
    } else {
        if ($button.attachEvent) {   // IE before version 9
            $button.attachEvent("click", function (e) {
                e.preventDefault();
                done(document.getElementById('codigo').value);
                removeCaptcha(options, captchaContainerGeneratedId);
            });
        }
    }
};

var sendRequestWithCaptcha = function(request, baseUrl, captchaResponse) {
    var url = new URL(baseUrl);
    url.searchParams.set('userValue', captchaResponse);
    url.searchParams.set('sessionKey', getCookie("captcha_validation_key") || '');
    return request(url.href);
};

var getCookie = function(name) {
    var cookieArr = document.cookie.split(";");

    for(var i = 0; i < cookieArr.length; i++) {
        var cookiePair = cookieArr[i].split("=");
        if(name === cookiePair[0].trim()) {
            return decodeURIComponent(cookiePair[1]);
        }
    }

    return null;
}

var xhrIsUnauthorized = function(xhr) {
    return xhr.status === 401;
};

var removeCaptcha = function(options, captchaId) {
    options.captchaEl.removeChild(document.getElementById(captchaId));
}

var isCaptchaInvisible = function() {
	return false;
}

var requestWithCaptcha = function(request, url, options) {
	if(!options.captchaEl && options.captchaId) {
		options.captchaEl = document.getElementById(options.captchaId);
	}
    return new Promise((resolve, reject) => {
        request(url).then(function (response) {
            resolve(response);
        }).catch(function (error) {
            if (xhrIsUnauthorized(error)) {
                var startCaptchaProcess = function () {
                    showCaptcha(options, function (response) {
                        sendRequestWithCaptcha(request, url, response).then((result) => {
                            resolve(result);
                        }).catch((err) => {
                            if (xhrIsUnauthorized(err)) {
                                startCaptchaProcess();
                            } else {
                                reject(err);
                            }
                        });
                    });
                };

                startCaptchaProcess();
            } else {
                reject(error);
            }
        });
    });
}