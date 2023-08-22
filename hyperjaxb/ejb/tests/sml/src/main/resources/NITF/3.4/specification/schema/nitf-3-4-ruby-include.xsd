<?xml version="1.0"?>
<!--
IPTC - International Press Telecommunications Council
Royal Albert House, Windsor, Berkshire SL4 1BE, England
www.iptc.org

See nitf-3-3.xsd to view Non-Exclusive License Agreement for 
International Press Telecommunications Council, which
applies to this specification.
-->
<schema 
	targetNamespace="http://iptc.org/std/NITF/2006-10-18/"
	xmlns="http://www.w3.org/2001/XMLSchema"
	xmlns:nitf="http://iptc.org/std/NITF/2006-10-18/"
	version="3.4"
	>
	<element name="ruby">
		<complexType>
			<choice>
				<sequence>
					<element ref="nitf:rb"/>
					<choice>
						<element ref="nitf:rt"/>
						<sequence>
							<element ref="nitf:rp"/>
							<element ref="nitf:rt"/>
							<element ref="nitf:rp"/>
						</sequence>
					</choice>
				</sequence>
				<sequence>
					<element ref="nitf:rbc"/>
					<element ref="nitf:rtc"/>
					<element ref="nitf:rtc" minOccurs="0"/>
				</sequence>
			</choice>
		</complexType>
	</element>
	<element name="rb">
		<complexType mixed="true"/>
	</element>
	<element name="rt">
		<complexType mixed="true">
			<attribute name="rbspan" type="string" use="optional"/>
		</complexType>
	</element>
	<element name="rp">
		<complexType mixed="true"/>
	</element>
	<element name="rbc">
		<complexType>
			<sequence>
				<element ref="nitf:rb" maxOccurs="unbounded"/>
			</sequence>
		</complexType>
	</element>
	<element name="rtc">
		<complexType>
			<sequence>
				<element ref="nitf:rt" maxOccurs="unbounded"/>
			</sequence>
		</complexType>
	</element>
</schema>
