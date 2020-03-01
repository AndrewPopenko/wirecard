package com.wirecard.test.api.services.responses.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import com.wirecard.test.api.services.JsonBody;

@JsonRootName("register-user-response")
public class RegisterUserResponse implements JsonBody {

	@JsonProperty("pan")
	private String pan;

	@JsonProperty("login-name")
	private String loginName;

	@JsonProperty("status")
	private String status;

	public RegisterUserResponse setPan(String pan){
		this.pan = pan;
		return this;
	}

	public String getPan(){
		return pan;
	}

	public RegisterUserResponse setLoginName(String loginName){
		this.loginName = loginName;
		return this;
	}

	public String getLoginName(){
		return loginName;
	}

	public RegisterUserResponse setStatus(String status){
		this.status = status;
		return this;
	}

	public String getStatus(){
		return status;
	}

	@Override
	public String toString(){
		return
				"RegisterUserResponse{" +
						"pan = '" + pan + '\'' +
						",login-name = '" + loginName + '\'' +
						",status = '" + status + '\'' +
						"}";
	}
}