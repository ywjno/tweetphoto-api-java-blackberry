package com.tweetphoto.api;

import net.rim.device.api.system.Bitmap;

public class Profile {
	long	  	m_id;
	String		m_comments;
	String		m_description;
	String		m_favorites;
	String		m_firstName;
	String		m_friends;
	String		m_homepage;
	String		m_mapTypeForProfile;
	String		m_photos;
	String		m_profileImage;
	Bitmap		m_profileBitmap;
	String		m_screenName;
	long     	m_serviceId;
	String		m_settings;
	String		m_views;

	// Describe the class
	public String describe () {
		String retVal = "User Id: " + m_id + ",\nComments: " + m_comments + ",\nDescription: " + m_description;
		retVal += ",\nFavorites: " + m_favorites + ",\nFirstName: " + m_firstName + ",\nFriends: " + m_friends + ",\nHomepage: " + m_homepage;
		retVal += ",\nMapTypeForProfile: " + m_mapTypeForProfile + ",\nPhotos: " + m_photos + ",\nProfileImage: " + m_profileImage;
		retVal += ",\nScreenName: " + m_screenName + ",\nServiceId: " + m_serviceId + ",\nSettings: " + m_settings + ",\nViews: " + m_views;
		return retVal;
	}
}
