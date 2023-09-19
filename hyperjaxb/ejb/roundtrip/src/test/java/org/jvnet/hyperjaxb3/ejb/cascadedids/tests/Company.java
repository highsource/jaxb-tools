package org.jvnet.hyperjaxb3.ejb.cascadedids.tests;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Basic;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "COMPANY")
public class Company {

	private long id;

	@Id
	@Column(name = "companyId")
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	private String name;

	@Basic
	@Column(name = "name")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	private List<Department> departments = new ArrayList<Department>();

	@OneToMany(mappedBy = "company", cascade = { CascadeType.ALL })
	public List<Department> getDepartments() {
		return departments;
	}

	public void setDepartments(List<Department> departments) {
		this.departments = departments;
	}

}
