package com.tweetphoto.api;

import java.util.Vector;

import net.rim.device.api.system.Bitmap;

public class Photo {
	String				m_bigImageURL;
	Bitmap				m_bigImageBitmap;
	int					m_commentCount;
	String				m_detailsURL;
	int					m_unlikedVotes;
	long				m_gdAlias;
	int					m_likedVotes;
	String				m_largeImageURL;
	Bitmap				m_largeBitmap;
	Location			m_location;
	String				m_mediumImageURL;
	Bitmap				m_mediumBitmap;
	String				m_message;
	String				m_name;
	String				m_next;
	String				m_previous;
	String				m_thumbnailURL;
	Bitmap				m_thumbnailBitmap;
	long				m_tinyAlias;
	long				m_photoId;
	long				m_uploadDate;
	String				m_uploadDateString;
	Profile				m_user;
	long				m_userId;
	Bitmap				m_userBitmap;
	int					m_views;
	Vector				m_photoComments;
	int					m_voteStatus;
	boolean				m_favorite;
	
	String describe() {
		String retVal = "PhotoId: " + m_photoId + ",\n";
		retVal += "Location: (" + m_location.m_latitude + ", " + m_location.m_longitude + "),\n";
		retVal += "MediumImageUrl: " + m_mediumImageURL + ",\n";
		retVal += "Message: " + m_message + ",\n";
		retVal += "Liked Votes: " + m_likedVotes + "\n";
		retVal += "Unliked Votes: " + m_unlikedVotes + "\n";
		retVal += "Comments: " + m_commentCount + "\n";
		retVal += "Views: " + m_views;
		
		return retVal;
	}
	
}
