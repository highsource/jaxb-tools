package org.jvnet.hyperjaxb3.ejb.cascadedids.tests;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@IdClass(value = Department.DepartmentId.class)
@Table(name = "DEPARTMENT")
public class Department implements Serializable {

	private static final long serialVersionUID = 1L;
	private long companyId;
	private long departmentId;

	@Id
	// @Column(updatable = false, insertable = false)
	public long getCompanyId() {
		return companyId;
	}

	public void setCompanyId(long companyId) {
		this.companyId = companyId;
	}

	@Id
	public long getDepartmentId() {
		return departmentId;
	}

	public void setDepartmentId(long departmentId) {
		this.departmentId = departmentId;
	}

	private Company company;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "companyId", insertable = false, updatable = false)
	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
		if (company != null) {
			this.setCompanyId(company.getId());
		}
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

	public static class DepartmentId implements Serializable {

		private static final long serialVersionUID = 1L;

		public long companyId;

		public long departmentId;

		public long getCompanyId() {
			return companyId;
		}

		public void setCompanyId(long companyId) {
			this.companyId = companyId;
		}

		public long getDepartmentId() {
			return departmentId;
		}

		public void setDepartmentId(long departmentId) {
			this.departmentId = departmentId;
		}

		public DepartmentId(long companyId, long departmentId) {
			super();
			this.companyId = companyId;
			this.departmentId = departmentId;
		}

		@SuppressWarnings("unused")
		private DepartmentId() {

		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + (int) (companyId ^ (companyId >>> 32));
			result = prime * result
					+ (int) (departmentId ^ (departmentId >>> 32));
			return result;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			DepartmentId other = (DepartmentId) obj;
			if (companyId != other.companyId)
				return false;
			if (departmentId != other.departmentId)
				return false;
			return true;
		}

	}

}
