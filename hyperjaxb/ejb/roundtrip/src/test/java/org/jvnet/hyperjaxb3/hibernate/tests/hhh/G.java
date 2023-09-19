package org.jvnet.hyperjaxb3.hibernate.tests.hhh;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Transient;

@Entity
public class G {

	private String id;

	@Id
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	private List<byte[]> bytes;

	// See http://opensource.atlassian.com/projects/hibernate/browse/HHH-6295
//	@ElementCollection
//	@Lob
	@Transient
	public List<byte[]> getBytes() {
		return bytes;
	}

	public void setBytes(List<byte[]> bytes) {
		this.bytes = bytes;
	}

}
