package com.epam.idea.rest.resource.support;

public class View {
	public interface Basic {}
	public interface ExtendedBasic extends Basic {}
	public interface Admin extends ExtendedBasic {}
}
