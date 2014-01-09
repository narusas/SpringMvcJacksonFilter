package net.narusas.jacksonfilter;

import lombok.Getter;

@Getter
public class PredefinedSet {
	String[] list;

	public PredefinedSet(String[] list) {
		this.list = list;
	}
}