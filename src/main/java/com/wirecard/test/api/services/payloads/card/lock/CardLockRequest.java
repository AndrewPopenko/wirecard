package com.wirecard.test.api.services.payloads.card.lock;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import com.wirecard.test.api.services.JsonBody;

@JsonRootName("card-lock")
public class CardLockRequest implements JsonBody {
	@JsonProperty("locking-reason")
	private String lockingReason;

	public CardLockRequest setLockingReason(String lockingReason){
		this.lockingReason = lockingReason;
		return this;
	}

	public String getLockingReason(){
		return lockingReason;
	}

	@Override
	public String toString(){
		return
				"CardLock{" +
						"locking-reason = '" + lockingReason + '\'' +
						"}";
	}
}