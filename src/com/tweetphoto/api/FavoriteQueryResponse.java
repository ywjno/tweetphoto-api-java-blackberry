package com.tweetphoto.api;

public class FavoriteQueryResponse {
	boolean	m_isFavorite;
	long	m_photoId;

String describe () {
	return "IsFavorite: " + m_isFavorite + ", PhotoId: " + m_photoId;
	}
}
