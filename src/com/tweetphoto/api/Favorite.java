package com.tweetphoto.api;

public class Favorite {
	long	m_favoriteDate;
	String	m_favoriteDateString;
	long	m_imageId;
	long	m_userId;
	Photo	m_photo;
	
	String describe() {
		return "FavoriteDate: " + m_favoriteDate + ",\nFavoriteDateString: " + m_favoriteDateString + "\nImageId: " + m_imageId + ",\nUserId: " + m_userId + ",\nPhoto: " + m_photo;
	}	
}
