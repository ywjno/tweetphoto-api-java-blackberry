package com.tweetphoto.api;

public class VoteStatus {
	long		m_photoId;
	long		m_userId;
	String		m_status;
	
	// Describe the class
	public String describe () {
		String retVal = "Photo Id: " + m_photoId + ", User Id: " + m_userId + ", Status: " + m_status;
		return retVal;
	}
}
