package com.tweetphoto.api;

import java.util.Vector;

public class Profiles {
	int					m_count;
	int					m_startIndex;
	Vector				m_list;
	String				m_filter;
	String				m_linkedServices;
	
	String describe() {
		return "Count: " + m_count + ",\n" + "StartIndex: " + m_startIndex + ",\nFilter: " + m_filter + ",\nList: " + m_list + "\nLinkedServices: " + m_linkedServices;
	}
}
