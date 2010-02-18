package com.tweetphoto.api;

import java.util.Vector;

public class Favorites {
	int					m_count;
	int					m_startIndex;
	Vector				m_list;	

	String describe() {
		return "Count: " + m_count + ",\nStartIndex: " + m_startIndex + ",\nList: " + m_list;
	}
}
