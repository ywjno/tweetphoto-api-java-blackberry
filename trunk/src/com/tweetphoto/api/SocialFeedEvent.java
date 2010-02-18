package com.tweetphoto.api;

import net.rim.device.api.system.Bitmap;

public class SocialFeedEvent {
	String		m_content;
	PhotoDate	m_date;
	String		m_eventType;
	String		m_imageThumbnail;
	Bitmap		m_imageBitmap;
	long		m_photoId;
	Profile		m_user;
	int			m_voteStatus;
	Boolean		m_favorite;
	
	// Good place to hang a photo class, too
	// Photo	m_photo;
	
	String describe() {
		return "Content: " + m_content + ",\nDate: " + m_date + ",\nEventType: " + m_eventType + ",\nImageThumbnail: " + m_imageThumbnail + ",\nPhotoId: " + m_photoId + ",\nUser: " + m_user + ",\nVoteStatus: " + m_voteStatus + ",\nFavorite: " + m_favorite;
	}
}
