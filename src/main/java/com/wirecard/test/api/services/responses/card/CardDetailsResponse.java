package com.wirecard.test.api.services.responses.card;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import com.wirecard.test.api.services.JsonBody;

@JsonRootName("card")
public class CardDetailsResponse implements JsonBody {

	@JsonProperty("number")
	private String number;

	@JsonProperty("holder-name")
	private String holderName;

	@JsonProperty("status")
	private String status;

	public CardDetailsResponse setNumber(String number){
		this.number = number;
		return this;
	}

	public String getNumber(){
		return number;
	}

	public CardDetailsResponse setHolderName(String holderName){
		this.holderName = holderName;
		return this;
	}

	public String getHolderName(){
		return holderName;
	}

	public CardDetailsResponse setStatus(String status){
		this.status = status;
		return this;
	}

	public String getStatus(){
		return status;
	}

	@Override
	public String toString(){
		return
				"Card{" +
						"number = '" + number + '\'' +
						",holder-name = '" + holderName + '\'' +
						",status = '" + status + '\'' +
						"}";
	}
}