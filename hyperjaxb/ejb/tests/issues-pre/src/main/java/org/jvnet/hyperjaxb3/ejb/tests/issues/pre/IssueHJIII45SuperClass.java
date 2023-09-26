package org.jvnet.hyperjaxb3.ejb.tests.issues.pre;

import java.io.Serializable;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.PrePersist;
import jakarta.xml.bind.annotation.XmlElement;
import org.jvnet.jaxb.lang.Equals;
import org.jvnet.jaxb.lang.EqualsStrategy;
import org.jvnet.jaxb.lang.HashCode;
import org.jvnet.jaxb.lang.HashCodeStrategy;
import org.jvnet.jaxb.lang.JAXBEqualsStrategy;
import org.jvnet.jaxb.lang.JAXBHashCodeStrategy;
import org.jvnet.jaxb.locator.ObjectLocator;
import org.jvnet.jaxb.locator.util.LocatorUtils;


@MappedSuperclass
public abstract class IssueHJIII45SuperClass implements Serializable, Equals, HashCode {

    private static final long serialVersionUID = 7724857660567518243L;


    @XmlElement(name = "Id")
    @Id
    @Column(name = "Id", updatable = false, nullable = false)
    private String id = "";


    public String getId() {
		return this.id;
	}


    private void setId(String uuid) {
		this.id = uuid;
	}

	@PrePersist
	private void prePersist() {
		if (getId().trim().length() == 0) {
			setId(UUID.randomUUID().toString());
		}
	}




    public boolean equals(ObjectLocator thisLocator, ObjectLocator thatLocator, Object object, EqualsStrategy strategy) {
        if (!(object instanceof IssueHJIII45SuperClass)) {
            return false;
        }
        if (this == object) {
            return true;
        }
        final IssueHJIII45SuperClass that = ((IssueHJIII45SuperClass) object);
        {
            String lhsId;
            lhsId = this.getId();
            String rhsId;
            rhsId = that.getId();
            if (!strategy.equals(LocatorUtils.property(thisLocator, "id", lhsId),
                    LocatorUtils.property(thatLocator, "id", rhsId), lhsId, rhsId,
                    this.id != null, that.id != null)) {
                return false;
            }
        }
        return true;
    }

    public boolean equals(Object object) {
        final EqualsStrategy strategy = JAXBEqualsStrategy.INSTANCE;
        return equals(null, null, object, strategy);
    }

    public int hashCode(ObjectLocator locator, HashCodeStrategy strategy) {
        int currentHashCode = 1;
        {
            String theId;
            theId = this.getId();
            currentHashCode = strategy.hashCode(LocatorUtils.property(locator, "id", theId), currentHashCode, theId, this.id != null);
        }
        return currentHashCode;
    }

    public int hashCode() {
        final HashCodeStrategy strategy = JAXBHashCodeStrategy.INSTANCE;
        return this.hashCode(null, strategy);
    }


}

