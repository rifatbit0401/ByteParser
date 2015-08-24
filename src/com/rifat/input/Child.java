package com.rifat.input;

public class Child extends Parent {

	public String  property4;
	public String property2;
	
	public void ChildMethod() {
		super.ParentMethod("");
		property2 = "this is property2";
	}
	public static void main(String[] args) {
		//Child child = new Child();
		System.out.println("okk");
	}
	public String property5;
}
