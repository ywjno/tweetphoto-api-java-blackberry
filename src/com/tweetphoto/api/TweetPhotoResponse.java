package com.tweetphoto.api;

public class TweetPhotoResponse {
	String	m_large;
	long	m_mediaId;
	String	m_mediaUrl;
	String	m_medium;
	String	m_original;
	long	m_photoId;
	String	m_sessionKeyResponse;
	String	m_thumbnail;
	String	m_status;
	long	m_userId;

	String describe()
		{
		String retVal = "Large: " + m_large + ", MediaId: " + m_mediaId + ", MediaUrl: " + m_mediaUrl + ", Medium: " + m_medium + ", Original: " + m_original;
		retVal += ", PhotoId: " + m_photoId + ", SessionKeyResponse: " + m_sessionKeyResponse + ", Thumbnail: " + m_thumbnail + ", Status: " + m_status + ", UserId: " + m_userId; 
		return retVal;
		}
}
