package com.tweetphoto.api;

import java.util.Vector;

public class Photos {
	int					m_count;
	int					m_startIndex;
	Vector				m_list;
	
	String describe() {
		return "Count: " + m_count + ",\n" + "StartIndex: " + m_startIndex + ",\nList: " + m_list;
	}
}
