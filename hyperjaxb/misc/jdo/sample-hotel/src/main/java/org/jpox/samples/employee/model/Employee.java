/**********************************************************************
Copyright (c) 2003 Erik Bengtson and others.
All rights reserved. This program and the accompanying materials
are made available under the terms of the JPOX License v1.0
which accompanies this distribution. 

Contributors:
	...
**********************************************************************/
package org.jpox.samples.employee.model;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Sample Application Employee Projects.
 * 
 * This application shows how to use application identity
 * and existing database schemas. A sample for the PoidGenerator
 * customized is provided. The PoidGenerator provided uses a
 * Oracle Sequence to create unique identifiers
 * 
 * Employee
 *  
 */
public class Employee
{
	private Department department;
	private String name;
	private Date hiredate;
	private String job;
	private double salary;
	private Set projects;
	
	/**
	 * @return
	 */
	public Department getDepartment()
	{
		return department;
	}

	/**
	 * @return
	 */
	public Date getHiredate()
	{
		return hiredate;
	}

	/**
	 * @return
	 */
	public String getJob()
	{
		return job;
	}

	/**
	 * @return
	 */
	public String getName()
	{
		return name;
	}

	/**
	 * @return
	 */
	public double getSalary()
	{
		return salary;
	}

	/**
	 * @param department
	 */
	public void setDepartment(Department department)
	{
		this.department = department;
	}

	/**
	 * @param date
	 */
	public void setHiredate(Date date)
	{
		hiredate = date;
	}

	/**
	 * @param string
	 */
	public void setJob(String string)
	{
		job = string;
	}

	/**
	 * @param string
	 */
	public void setName(String string)
	{
		name = string;
	}

	/**
	 * @param d
	 */
	public void setSalary(double d)
	{
		salary = d;
	}

	/**
	 * gets the projects for the employee
	 * @return
	 */
	public Set getProjects()
	{
		if( projects == null )
		{
			projects = new HashSet();		
		}
		return projects;
	}
	
	/**
	 * Add a project to the employee
	 */
	public void addProject(Project project)
	{
		getProjects().add(project);
	}
	
	/**
	 * Remove a project from the employee
	 */
	public void removeProject(Project project)
	{
		getProjects().remove(project);
	}

	public String toString()
	{
		return " [DEPARTMENT] "
			+ department
			+ " [NAME] "
			+ name
			+ " [HIREDATE] "
			+ hiredate
			+ " [JOB] "
			+ job
			+ " [SALARY] "
			+ salary;
	}
}
