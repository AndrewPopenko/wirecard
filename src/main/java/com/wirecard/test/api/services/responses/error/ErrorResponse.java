package com.wirecard.test.api.services.responses.error;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import com.wirecard.test.api.services.JsonBody;

@JsonRootName(value = "error-response")
public class ErrorResponse implements JsonBody {
	@JsonProperty("error-code")
	private int errorCode;

	@JsonProperty("error")
	private Error error;

	public ErrorResponse setErrorCode(int errorCode){
		this.errorCode = errorCode;
		return this;
	}

	public int getErrorCode(){
		return errorCode;
	}

	public ErrorResponse setError(Error error){
		this.error = error;
		return this;
	}

	public Error getError(){
		return error;
	}

	@Override
 	public String toString(){
		return 
			"ErrorResponse{" +
			"error-code = '" + errorCode + '\'' +
			",error = '" + error + '\'' +
			"}";
		}
}