package com.tweetphoto.api;

import net.rim.device.api.system.Bitmap;

public class Comment {
	long		m_date;
	String		m_dateString;
	long		m_id;
	long		m_imageId;
	String		m_message;
	long		m_profileId;
	String		m_profileImage;
	Bitmap		m_profileBitmap;
	String		m_screenName;
	Bitmap		m_photoBitmap;
	
	String describe() {
	return "Date: " + m_date + ",\nDateString: " + m_dateString + ",\nId: " + m_id + ",\nImageId: " + m_imageId + ",\nMessage: " + m_message + ",\nProfileId: " + m_profileId + ",\nProfileImage: " + m_profileImage + ",\nScreenName: " + m_screenName;
	}
	
}
