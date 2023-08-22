/**********************************************************************
Copyright (c) 2003 Erik Bengtson and others.
All rights reserved.   This program and the accompanying materials
are made available under the terms of the JPOX License v1.0
which accompanies this distribution. 

Contributors:
	...
**********************************************************************/
package org.jpox.samples.employee.model;

/**
 * Sample Application Employee Projects.
 * 
 * This application shows how to use application identity
 * and existing database schemas. A sample for the PoidGenerator
 * customized is provided. The PoidGenerator provided uses a
 * Oracle Sequence to create unique identifiers
 * 
 * Project 
 */
public class Project
{
	private String name;
	private String id;
	private Employee employee;

	/**
	 * @return
	 */
	public String getId()
	{
		return id;
	}

	/**
	 * @return
	 */
	public String getName()
	{
		return name;
	}

	/**
	 * @param string
	 */
	public void setId(String string)
	{
		id = string;
	}

	/**
	 * @param string
	 */
	public void setName(String string)
	{
		name = string;
	}

	/**
	 * @return
	 */
	public Employee getEmp()
	{
		return employee;
	}

	/**
	 * @param employee
	 */
	public void setEmployee(Employee e)
	{
		employee = e;
	}
	public String toString()
	{
		return " [NAME] " + name + " [ID] " + id + " [EMPLOYEE] " + employee;
	}
}
