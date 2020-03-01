package com.wirecard.test.api.services.responses.error;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

@JsonRootName("error")
public class Error{

	@JsonProperty("error-message")
	private String errorMessage;

	@JsonProperty("error-key")
	private String errorKey;

	public Error setErrorMessage(String errorMessage){
		this.errorMessage = errorMessage;
		return this;
	}

	public String getErrorMessage(){
		return errorMessage;
	}

	public Error setErrorKey(String errorKey){
		this.errorKey = errorKey;
		return this;
	}

	public String getErrorKey(){
		return errorKey;
	}

	@Override
 	public String toString(){
		return 
			"Error{" + 
			"error-message = '" + errorMessage + '\'' + 
			",error-key = '" + errorKey + '\'' + 
			"}";
		}
}