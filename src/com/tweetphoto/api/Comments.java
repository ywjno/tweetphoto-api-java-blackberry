package com.tweetphoto.api;

import java.util.Vector;

public class Comments {
	int						m_count;
	int						m_startIndex;
	long					m_photoId;
	
	Vector					m_list; 		// = new ArrayList<Comment>();

	String describe() {
		String retVal = "Count: " + m_count + ", StartIndex: " + m_startIndex + ", PhotoId: " + m_photoId + ", List: " + m_list;
		return retVal;
		}
}
