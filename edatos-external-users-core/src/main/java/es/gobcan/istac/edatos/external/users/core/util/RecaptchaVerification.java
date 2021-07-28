package es.gobcan.istac.edatos.external.users.core.util;

import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonPropertyOrder({
    "success",
    "challenge_ts",
    "hostname",
    "error-codes",
    "score",
    "action"
})
public class RecaptchaVerification {

    private boolean success;
    
    private double score;
    
    private String action;
    
    private String hostname;
    
    @JsonProperty("challenge_ts")
    private String challengeTs;
    
    @JsonProperty("error-codes")
    private ErrorCode[] errorCodes;
    
    public boolean wasASuccess() {
        return success;
    }

    public String getChallengeTs() {
        return challengeTs;
    }
    
    public String getHostname() {
        return hostname;
    }
    
    public ErrorCode[] getErrorCodes() {
        return errorCodes;
    }
    
    public double getScore() {
        return score;
    }
    
    public String getAction() {
        return action;
    }
    
    @JsonIgnore
    public boolean hasClientError() {
        ErrorCode[] errors = getErrorCodes();
        if(errors == null) {
            return false;
        }
        for(ErrorCode error : errors) {
            switch(error) {
                case INVALID_RESPONSE:
                case MISSING_RESPONSE:
                    return true;
                default:
                    break;
            }
        }
        return false;
    }

    enum ErrorCode {
        MISSING_SECRET, INVALID_SECRET, MISSING_RESPONSE, INVALID_RESPONSE, BAD_REQUEST, TIMEOUT_OR_DUPLICATE;

        private static Map<String, ErrorCode> errorsMap = new HashMap<>(6);

        static {
            errorsMap.put("missing-input-secret",   MISSING_SECRET);
            errorsMap.put("invalid-input-secret",   INVALID_SECRET);
            errorsMap.put("missing-input-response", MISSING_RESPONSE);
            errorsMap.put("invalid-input-response", INVALID_RESPONSE);
            errorsMap.put("bad-request", BAD_REQUEST);
            errorsMap.put("timeout-or-duplicate", TIMEOUT_OR_DUPLICATE);
        }

        @JsonCreator
        public static ErrorCode forValue(String value) {
            return errorsMap.get(value.toLowerCase());
        }
    }
}