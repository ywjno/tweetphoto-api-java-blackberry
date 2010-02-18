package com.tweetphoto.api;

import java.util.Vector;

public class SocialFeed {
	int							m_count;
	int							m_startIndex;
	String						m_filter;
	Vector						m_list;
	
	String describe() {
		return "Count: " + m_count + ",\nStartIndex: " + m_startIndex + ",\nFilter: " + m_filter + ",\nList: " + m_list;
	}
}
