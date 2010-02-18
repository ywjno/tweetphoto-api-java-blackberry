package com.tweetphoto.api;

public class Settings {
	boolean		m_doNotTweetFavoritePhoto;
	String		m_email;
	boolean		m_hideViewingPatterns;
	boolean		m_hideVotes;
	String		m_mapType;
	long		m_pin;
	boolean		m_shortenUrl;
	
	String describe() {
		String retVal = "DoNotTweetFavoritePhoto: " + m_doNotTweetFavoritePhoto + ",\nEmail: " + m_email + ",\n";
		retVal += "HideViewingPatterns: " + m_hideViewingPatterns + ",\n";
		retVal += "HideVotes: " + m_hideVotes + ",\n";
		retVal += "MapType: " + m_mapType + ",\n";
		retVal += "PIN: " + m_pin + ",\n";
		retVal += "ShortenUrl: " + m_shortenUrl;
		return retVal;
	}
}
