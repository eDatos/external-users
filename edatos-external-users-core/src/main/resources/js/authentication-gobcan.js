
var showCaptcha = function (options, done) {
    var captchaGeneratedId = createUUID();
	var captchaContainerGeneratedId = "captcha_container_" + captchaGeneratedId;
	var captchaButtonGeneratedId = "captcha_button_" + captchaGeneratedId;
	
	var imgUrl = new URL(/*[[${captchaPictureUrl}]]*/ "");
	imgUrl.searchParams.set('sessionKey', captchaGeneratedId);
	
    options.captchaEl.insertAdjacentHTML('beforeend',
        "<div id=\"" + captchaContainerGeneratedId + "\" class=\"captcha captcha-simple\">" +
        "	<div id=\"" + captchaGeneratedId + "\">" +
        "		<table style=\"width: 100%;\">" +
        "			<tr>" +
        "       		<td class=\"formevenrow\" nowrap=\"nowrap\" >" +
        "					<img src=\"" + imgUrl.href + "\" class=\"" + (options.imgClasses ? options.imgClasses : "captchaImg") + "\">" +
        "				</td>" +
        "			</tr>" +
        "			<tr>" +
        "           	<td class=\"formevenrow\" align=\"right\" width=\"120\">" +
        "               	<label for=\"codigo\" class=\"" + (options.labelClasses ? options.labelClasses : "captchaLabel") + "\">Resultado de la operación</label>" +
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
            done(document.getElementById('codigo').value, captchaGeneratedId);
            removeCaptcha(options, captchaContainerGeneratedId);
        }, false);
    } else {
        if ($button.attachEvent) {   // IE before version 9
            $button.attachEvent("click", function (e) {
                e.preventDefault();
                done(document.getElementById('codigo').value, captchaGeneratedId);
                removeCaptcha(options, captchaContainerGeneratedId);
            });
        }
    }
};

var createUUID = function() {
  return ([1e7]+-1e3+-4e3+-8e3+-1e11).replace(/[018]/g, c =>
    (c ^ crypto.getRandomValues(new Uint8Array(1))[0] & 15 >> c / 4).toString(16)
  )
};

var sendRequestWithCaptcha = function(request, baseUrl, captchaResponse, sessionKey) {
    var url = new URL(baseUrl);
    url.searchParams.set('userValue', captchaResponse);
    url.searchParams.set('sessionKey', sessionKey);
    return request(url.href);
};

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
                    showCaptcha(options, function (response, sessionKey) {
                        sendRequestWithCaptcha(request, url, response, sessionKey).then((result) => {
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