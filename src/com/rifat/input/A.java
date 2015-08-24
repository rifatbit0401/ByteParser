package com.rifat.input;

public class A {
	
	public void a()
	{
		b();
		c();
	}
	public void b()
	{
		c();
	}
	public void c()
	{
		new B().x();
	}
}
