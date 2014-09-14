package org.jvnet.hyperjaxb3.jdo.test.tests;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;

import org.apache.commons.lang.builder.EqualsBuilder;

@XmlAccessorType(XmlAccessType.PROPERTY)
@XmlType(name = "B", propOrder = { "id", "version", "c" })
public class B {

  private String id;

  private String c;

  public String getId() {
    return id;
  }

  @XmlAttribute
  public void setId(String id) {
    this.id = id;
  }

  public String getC() {
    return c;
  }

  public void setC(String value) {
    this.c = value;
  }

  private int version;

  @XmlAttribute
  public int getVersion() {
    return version;
  }

  public void setVersion(int version) {
    this.version = version;
  }

  @Override
  public boolean equals(Object obj) {
    return EqualsBuilder.reflectionEquals(this, obj);
  }
}