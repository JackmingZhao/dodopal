package com.dodopal.portal.business.bean;

import java.util.HashMap;
import java.util.Map;

public class TreeNode {

	private String id;

	private String text;

	private String icon;

	private String parent;

	private NodeState state;
	
	private Map<String,String> a_attr = new HashMap<String,String>();

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public NodeState getState() {
		return state;
	}

	public void setState(NodeState state) {
		this.state = state;
	}

	public String getParent() {
		return parent;
	}

	public void setParent(String parent) {
		this.parent = parent;
	}

	public Map<String, String> getA_attr() {
		return a_attr;
	}

	public void setA_attr(Map<String, String> a_attr) {
		this.a_attr = a_attr;
	}

}