package org.jvnet.jaxb.annox.demos.guide;

public class DemoClass {
	
	public int value = 0;
	
	public DemoClass()
	{}
	
	public DemoClass(int value)
	{ this.value = value; }
	
	public int getValue()
	{ return this.value; } 
	
	public void setValue()
	{ this.value = 0; }

	public void setValue(int value)
	{ this.value = value; }
	
	public void setValue(String s)
	{ this.value = Integer.valueOf(s); }
	
	public void setValue(String s, int radix)
	{ this.value = Integer.valueOf(s, radix); }
	
	public int add(int value)
	{ return this.value += value; }
}
