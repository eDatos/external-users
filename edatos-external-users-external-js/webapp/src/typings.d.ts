declare var requestWithCaptcha: <T>(
    request: (url: String) => Promise<T>, 
    url: string,
    options: { 
        captchaEl?: any,
        captchaId?: string,
        imgClasses?: string,
        inputClasses?: string,
        buttonClasses?: string,
        labelClasses?: string,
    }
) => Promise<T>;

declare var showCaptcha: (
    options: { 
        captchaEl: any
    }
) => void;