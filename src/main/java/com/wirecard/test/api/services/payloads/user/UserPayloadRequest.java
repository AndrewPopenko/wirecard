package com.wirecard.test.api.services.payloads.user;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonRootName;
import com.wirecard.test.api.services.JsonBody;

@JsonRootName("user")
public class UserPayloadRequest implements JsonBody {
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String password;
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String birthDate;
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String mobileNumber;
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String salutation;
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String firstName;
	private String email;
	private String loginName;
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String lastName;

	public UserPayloadRequest setPassword(String password){
		this.password = password;
		return this;
	}

	public String getPassword(){
		return password;
	}

	public UserPayloadRequest setBirthDate(String birthDate){
		this.birthDate = birthDate;
		return this;
	}

	public String getBirthDate(){
		return birthDate;
	}

	public UserPayloadRequest setMobileNumber(String mobileNumber){
		this.mobileNumber = mobileNumber;
		return this;
	}

	public String getMobileNumber(){
		return mobileNumber;
	}

	public UserPayloadRequest setSalutation(String salutation){
		this.salutation = salutation;
		return this;
	}

	public String getSalutation(){
		return salutation;
	}

	public UserPayloadRequest setFirstName(String firstName){
		this.firstName = firstName;
		return this;
	}

	public String getFirstName(){
		return firstName;
	}

	public UserPayloadRequest setEmail(String email){
		this.email = email;
		return this;
	}

	public String getEmail(){
		return email;
	}

	public UserPayloadRequest setLoginName(String loginName){
		this.loginName = loginName;
		return this;
	}

	public String getLoginName(){
		return loginName;
	}

	public UserPayloadRequest setLastName(String lastName){
		this.lastName = lastName;
		return this;
	}

	public String getLastName(){
		return lastName;
	}

	@Override
	public String toString(){
		return
				"User{" +
						"password = '" + password + '\'' +
						",birth-date = '" + birthDate + '\'' +
						",mobile-number = '" + mobileNumber + '\'' +
						",salutation = '" + salutation + '\'' +
						",first-name = '" + firstName + '\'' +
						",email = '" + email + '\'' +
						",login-name = '" + loginName + '\'' +
						",last-name = '" + lastName + '\'' +
						"}";
	}
}
