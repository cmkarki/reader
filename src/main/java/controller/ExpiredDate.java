package controller;

import java.util.Date;

public class ExpiredDate {

	private Long code;
	
	private Date expiredDate;
	
	private String remainingDate;

	public Long getCode() {
		return code;
	}

	public void setCode(Long code) {
		this.code = code;
	}

	public Date getExpiredDate() {
		return expiredDate;
	}

	public void setExpiredDate(Date expiredDate) {
		this.expiredDate = expiredDate;
	}

	public String getRemainingDate() {
		return remainingDate;
	}

	@Override
	public String toString() {
		return "ExpiredDate [code=" + code + ", expiredDate=" + expiredDate + ", remainingDate=" + remainingDate + "]";
	}

	public void setRemainingDate(String remainingDate) {
		this.remainingDate = remainingDate;
	}
	
}
