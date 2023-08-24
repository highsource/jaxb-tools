package org.jvnet.jaxb.annox.samples.po;

import javax.xml.datatype.XMLGregorianCalendar;

public class PurchaseOrderType {

	protected USAddress shipTo;

	protected USAddress billTo;

	protected String comment;

	protected Items items;

	protected XMLGregorianCalendar orderDate;

	public USAddress getShipTo() {
		return shipTo;
	}

	public void setShipTo(USAddress value) {
		this.shipTo = value;
	}

	public USAddress getBillTo() {
		return billTo;
	}

	public void setBillTo(USAddress value) {
		this.billTo = value;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String value) {
		this.comment = value;
	}

	public Items getItems() {
		return items;
	}

	public void setItems(Items value) {
		this.items = value;
	}

	public XMLGregorianCalendar getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(XMLGregorianCalendar value) {
		this.orderDate = value;
	}
}
