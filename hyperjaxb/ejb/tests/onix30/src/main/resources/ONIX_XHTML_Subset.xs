<?xml version="1.0" encoding="UTF-8"?>
<!--
  **************************************************
  *                                                *
  *               ONIX INTERNATIONAL               *
  *                                                *
  *     BOOK PRODUCT INFORMATION MESSAGE SCHEMA    *
  *                                                *
  *                  XHTML MODULE                  *
  *                W3C XSD VERSION                 *
  *                                                *
  *          Original author: Francis Cave         *
  *                                                *
  *                  Release 3.0                   *
  *                  Revision 00                   *
  *                 Status: DRAFT                  *
  *            Release date: 2009-04-09            *
  *                                                *
  *             (c) 2000-2009 EDItEUR              *
  *             http://www.editeur.org/            *
  *                                                *
  **************************************************
  
  
  NOTE - THIS MODULE CORRESPONDS TO A SUBSET OF W3C XHTML 1.0. IT ONLY INCLUDES
  ELEMENTS AND ASSOCIATED ATTRIBUTES THAT ARE VALID INSIDE THE XHTML ELEMENT 
  'body', AND EXCLUDES ELEMENTS FOR XHTML FORMS AND SCRIPTS AND 
  ATTRIBUTES THAT DEFINE BEHAVIOUR. SOME PARAMETER ENTITIES HAVE BEEN RENAMED 
  TO AVOID CLASHES WITH ONIX PARAMETER ENTITY NAMES.
  
  
  TERMS AND CONDITIONS OF USE OF THE ONIX BOOK PRODUCT INFORMATION MESSAGE SCHEMA
  
  All ONIX standards and documentation are copyright materials, made available 
  free of charge for general use.  If you use any version of the ONIX Book Product 
  Information Message Schema, you will be deemed to have accepted these terms and 
  conditions:
  
  1.  You agree that you will not add to, delete from or amend any version of the 
  ONIX Product Information Message Schema, or any part of the Schema except for 
  strictly internal use in your own organisation.
  
  2.  You agree that if you wish to add to, amend, or make extracts of any version 
  of the Schema for any purpose that is not strictly internal to your own organisation, 
  you will in the first instance notify EDItEUR and allow EDItEUR to review 
  and comment on your proposed use, in the interest of securing an orderly 
  development of the Schema for the benefit of other users.
  
  If you do not accept these terms, you must not use any version of the ONIX Product 
  Information Message Schema.
  
  Full copies of all published versions of the latest release of this Schema and all 
  associated documentation are available from the EDItEUR web site, where may also be 
  found details of how to contact EDItEUR for advice on the use of this Schema. The URL 
  for the EDItEUR web site is:
  
  http://www.editeur.org/
  
-->
<!-- ================ Character mnemonic entities ========================= -->
<!--
  THE FOLLOWING ENTITY SETS ARE EXCLUDED FROM ALL VERSIONS OF THE ONIX SCHEMA. 
  
  <!ENTITY % HTMLlat1 PUBLIC
     "-//W3C//ENTITIES Latin 1 for XHTML//EN"
     "xhtml-lat1.ent">
  %HTMLlat1;
  
  <!ENTITY % HTMLsymbol PUBLIC
     "-//W3C//ENTITIES Symbols for XHTML//EN"
     "xhtml-symbol.ent">
  %HTMLsymbol;
  
  <!ENTITY % HTMLspecial PUBLIC
     "-//W3C//ENTITIES Special for XHTML//EN"
     "xhtml-special.ent">
  %HTMLspecial;
-->
<!-- ================== Imported Names ==================================== -->
<xs:schema xmlns:xs="http://www.w3.org/2001/XMLSchema" xmlns:xml="http://www.w3.org/XML/1998/namespace" elementFormDefault="qualified">
	<xs:simpleType name="XHTMLContentType">
		<xs:restriction base="xs:string"/>
	</xs:simpleType>
	<!-- media type, as per [RFC2045] -->
	<!-- comma-separated list of media types, as per [RFC2045] -->
	<xs:simpleType name="Charset">
		<xs:restriction base="xs:string"/>
	</xs:simpleType>
	<!-- a character encoding, as per [RFC2045] -->
	<!-- a space separated list of character encodings, as per [RFC2045] -->
	<xs:simpleType name="XHTMLLanguageCode">
		<xs:restriction base="xs:NMTOKEN"/>
	</xs:simpleType>
	<!-- a language code, as per [RFC1766] -->
	<xs:simpleType name="Character">
		<xs:restriction base="xs:string"/>
	</xs:simpleType>
	<!-- a single character from [ISO10646] -->
	<xs:simpleType name="XHTMLNumber">
		<xs:restriction base="xs:string"/>
	</xs:simpleType>
	<!-- one or more digits -->
	<xs:simpleType name="LinkTypes">
		<xs:restriction base="xs:string"/>
	</xs:simpleType>
	<!-- space-separated list of link types -->
	<!-- single or comma-separated list of media descriptors -->
	<xs:simpleType name="URI">
		<xs:restriction base="xs:string"/>
	</xs:simpleType>
	<!-- a Uniform Resource Identifier, see [RFC2396] -->
	<xs:simpleType name="UriList">
		<xs:restriction base="xs:string"/>
	</xs:simpleType>
	<!-- a space separated list of Uniform Resource Identifiers -->
	<!-- date and time information. ISO date format -->
	<xs:simpleType name="Script">
		<xs:restriction base="xs:string"/>
	</xs:simpleType>
	<!-- script expression -->
	<xs:simpleType name="StyleSheet">
		<xs:restriction base="xs:string"/>
	</xs:simpleType>
	<!-- style sheet data -->
	<xs:simpleType name="XHTMLText">
		<xs:restriction base="xs:string"/>
	</xs:simpleType>
	<!-- used for titles etc. -->
	<!-- render in this frame -->
	<xs:simpleType name="Length">
		<xs:restriction base="xs:string"/>
	</xs:simpleType>
	<!-- nn for pixels or nn% for percentage length -->
	<xs:simpleType name="MultiLength">
		<xs:restriction base="xs:string"/>
	</xs:simpleType>
	<!-- pixel, percentage, or relative -->
	<!-- comma-separated list of MultiLength -->
	<xs:simpleType name="Pixels">
		<xs:restriction base="xs:string"/>
	</xs:simpleType>
	<!-- integer representing length in pixels -->
	<!-- these are used for image maps -->
	<xs:simpleType name="Shape">
		<xs:restriction base="xs:token">
			<xs:enumeration value="rect"/>
			<xs:enumeration value="circle"/>
			<xs:enumeration value="poly"/>
			<xs:enumeration value="default"/>
		</xs:restriction>
	</xs:simpleType>
	<xs:simpleType name="Coords">
		<xs:restriction base="xs:string"/>
	</xs:simpleType>
	<!-- comma separated list of lengths -->
	<!-- =================== Generic Attributes =============================== -->
	<!--
    core attributes common to most elements
    id       document-wide unique id
    class    space separated list of classes
    style    associated style info
    title    advisory title/amplification
  -->
	<xs:attributeGroup name="coreattrs">
		<xs:attribute name="id" type="xs:ID"/>
		<xs:attribute name="class"/>
		<xs:attribute name="style" type="StyleSheet"/>
		<xs:attribute name="title" type="XHTMLText"/>
	</xs:attributeGroup>
	<!--
    internationalization attributes
    lang        language code (backwards compatible)
    xml:lang    language code (as per XML 1.0 spec)
    dir         direction for weak/neutral text
  -->
	<xs:attributeGroup name="i18n">
		<xs:attribute name="lang" type="XHTMLLanguageCode"/>
		<xs:attribute name="dir">
			<xs:simpleType>
				<xs:restriction base="xs:token">
					<xs:enumeration value="ltr"/>
					<xs:enumeration value="rtl"/>
				</xs:restriction>
			</xs:simpleType>
		</xs:attribute>
	</xs:attributeGroup>
	<xs:attributeGroup name="attrs">
		<xs:attributeGroup ref="coreattrs"/>
		<xs:attributeGroup ref="i18n"/>
	</xs:attributeGroup>
	<!-- =================== Text Elements ==================================== -->
	<xs:element name="special" abstract="true" substitutionGroup="inline"/>
	<xs:element name="fontstyle" abstract="true" substitutionGroup="inline"/>
	<xs:element name="phrase" abstract="true" substitutionGroup="inline"/>
	<xs:element name="inline" abstract="true"/>
	<!-- %Inline; covers inline or "text-level" elements -->
	<xs:complexType name="Inline" mixed="true">
		<xs:sequence>
			<xs:element ref="inline" minOccurs="0" maxOccurs="unbounded"/>
		</xs:sequence>
	</xs:complexType>
	<!-- ================== Block level elements ============================== -->
	<xs:element name="heading" abstract="true" substitutionGroup="block"/>
	<xs:element name="lists" abstract="true" substitutionGroup="block"/>
	<xs:element name="blocktext" abstract="true" substitutionGroup="block"/>
	<xs:element name="block" abstract="true"/>
	<xs:complexType name="Block">
		<xs:sequence>
			<xs:element ref="block" minOccurs="0" maxOccurs="unbounded"/>
		</xs:sequence>
	</xs:complexType>
	<!-- %Flow; mixes Block and Inline and is used for list items etc. -->
	<xs:complexType name="Flow" mixed="true">
		<xs:choice minOccurs="0" maxOccurs="unbounded">
			<xs:element ref="block"/>
			<xs:element ref="inline"/>
		</xs:choice>
	</xs:complexType>
	<!-- ================== Content models for exclusions ===================== -->
	<!-- a elements use %Inline; excluding a -->
	<xs:complexType name="a.content" mixed="true">
		<xs:choice minOccurs="0" maxOccurs="unbounded">
			<xs:element ref="special"/>
			<xs:element ref="fontstyle"/>
			<xs:element ref="phrase"/>
		</xs:choice>
	</xs:complexType>
	<!-- pre uses %Inline excluding img, object, big, small, sup or sup -->
	<xs:complexType name="pre.content" mixed="true">
		<xs:choice minOccurs="0" maxOccurs="unbounded">
			<xs:element ref="a"/>
			<xs:element ref="br"/>
			<xs:element ref="span"/>
			<xs:element ref="bdo"/>
			<xs:element ref="map"/>
			<xs:element ref="tt"/>
			<xs:element ref="i"/>
			<xs:element ref="b"/>
			<xs:element ref="phrase"/>
		</xs:choice>
	</xs:complexType>
	<!-- =================== Document Body ==================================== -->
	<xs:element name="div" substitutionGroup="block">
		<xs:complexType>
			<xs:complexContent>
				<xs:extension base="Flow">
					<xs:attributeGroup ref="div.attlist"/>
				</xs:extension>
			</xs:complexContent>
		</xs:complexType>
	</xs:element>
	<!-- generic language/style container -->
	<xs:attributeGroup name="div.attlist">
		<xs:attributeGroup ref="attrs"/>
	</xs:attributeGroup>
	<!-- =================== Paragraphs ======================================= -->
	<xs:element name="p" substitutionGroup="block">
		<xs:complexType>
			<xs:complexContent>
				<xs:extension base="Inline">
					<xs:attributeGroup ref="p.attlist"/>
				</xs:extension>
			</xs:complexContent>
		</xs:complexType>
	</xs:element>
	<xs:attributeGroup name="p.attlist">
		<xs:attributeGroup ref="attrs"/>
	</xs:attributeGroup>
	<!-- =================== Headings ========================================= -->
	<!--
    There are six levels of headings from h1 (the most important)
    to h6 (the least important).
  -->
	<xs:element name="h1" substitutionGroup="heading">
		<xs:complexType>
			<xs:complexContent>
				<xs:extension base="Inline">
					<xs:attributeGroup ref="h1.attlist"/>
				</xs:extension>
			</xs:complexContent>
		</xs:complexType>
	</xs:element>
	<xs:attributeGroup name="h1.attlist">
		<xs:attributeGroup ref="attrs"/>
	</xs:attributeGroup>
	<xs:element name="h2" substitutionGroup="heading">
		<xs:complexType>
			<xs:complexContent>
				<xs:extension base="Inline">
					<xs:attributeGroup ref="h2.attlist"/>
				</xs:extension>
			</xs:complexContent>
		</xs:complexType>
	</xs:element>
	<xs:attributeGroup name="h2.attlist">
		<xs:attributeGroup ref="attrs"/>
	</xs:attributeGroup>
	<xs:element name="h3" substitutionGroup="heading">
		<xs:complexType>
			<xs:complexContent>
				<xs:extension base="Inline">
					<xs:attributeGroup ref="h3.attlist"/>
				</xs:extension>
			</xs:complexContent>
		</xs:complexType>
	</xs:element>
	<xs:attributeGroup name="h3.attlist">
		<xs:attributeGroup ref="attrs"/>
	</xs:attributeGroup>
	<xs:element name="h4" substitutionGroup="heading">
		<xs:complexType>
			<xs:complexContent>
				<xs:extension base="Inline">
					<xs:attributeGroup ref="h4.attlist"/>
				</xs:extension>
			</xs:complexContent>
		</xs:complexType>
	</xs:element>
	<xs:attributeGroup name="h4.attlist">
		<xs:attributeGroup ref="attrs"/>
	</xs:attributeGroup>
	<xs:element name="h5" substitutionGroup="heading">
		<xs:complexType>
			<xs:complexContent>
				<xs:extension base="Inline">
					<xs:attributeGroup ref="h5.attlist"/>
				</xs:extension>
			</xs:complexContent>
		</xs:complexType>
	</xs:element>
	<xs:attributeGroup name="h5.attlist">
		<xs:attributeGroup ref="attrs"/>
	</xs:attributeGroup>
	<xs:element name="h6" substitutionGroup="heading">
		<xs:complexType>
			<xs:complexContent>
				<xs:extension base="Inline">
					<xs:attributeGroup ref="h6.attlist"/>
				</xs:extension>
			</xs:complexContent>
		</xs:complexType>
	</xs:element>
	<xs:attributeGroup name="h6.attlist">
		<xs:attributeGroup ref="attrs"/>
	</xs:attributeGroup>
	<!-- =================== Lists ============================================ -->
	<!-- Unordered list -->
	<xs:element name="ul" substitutionGroup="lists">
		<xs:complexType>
			<xs:sequence>
				<xs:element ref="li" maxOccurs="unbounded"/>
			</xs:sequence>
			<xs:attributeGroup ref="ul.attlist"/>
		</xs:complexType>
	</xs:element>
	<xs:attributeGroup name="ul.attlist">
		<xs:attributeGroup ref="attrs"/>
	</xs:attributeGroup>
	<!-- Ordered (numbered) list -->
	<xs:element name="ol" substitutionGroup="lists">
		<xs:complexType>
			<xs:sequence>
				<xs:element ref="li" maxOccurs="unbounded"/>
			</xs:sequence>
			<xs:attributeGroup ref="ol.attlist"/>
		</xs:complexType>
	</xs:element>
	<xs:attributeGroup name="ol.attlist">
		<xs:attributeGroup ref="attrs"/>
	</xs:attributeGroup>
	<!-- list item -->
	<xs:element name="li">
		<xs:complexType>
			<xs:complexContent>
				<xs:extension base="Flow">
					<xs:attributeGroup ref="li.attlist"/>
				</xs:extension>
			</xs:complexContent>
		</xs:complexType>
	</xs:element>
	<xs:attributeGroup name="li.attlist">
		<xs:attributeGroup ref="attrs"/>
	</xs:attributeGroup>
	<!-- definition lists - dt for term, dd for its definition -->
	<xs:element name="dl" substitutionGroup="lists">
		<xs:complexType>
			<xs:choice maxOccurs="unbounded">
				<xs:element ref="dt"/>
				<xs:element ref="dd"/>
			</xs:choice>
			<xs:attributeGroup ref="dl.attlist"/>
		</xs:complexType>
	</xs:element>
	<xs:attributeGroup name="dl.attlist">
		<xs:attributeGroup ref="attrs"/>
	</xs:attributeGroup>
	<xs:element name="dt">
		<xs:complexType>
			<xs:complexContent>
				<xs:extension base="Inline">
					<xs:attributeGroup ref="dt.attlist"/>
				</xs:extension>
			</xs:complexContent>
		</xs:complexType>
	</xs:element>
	<xs:attributeGroup name="dt.attlist">
		<xs:attributeGroup ref="attrs"/>
	</xs:attributeGroup>
	<xs:element name="dd">
		<xs:complexType>
			<xs:complexContent>
				<xs:extension base="Flow">
					<xs:attributeGroup ref="dd.attlist"/>
				</xs:extension>
			</xs:complexContent>
		</xs:complexType>
	</xs:element>
	<xs:attributeGroup name="dd.attlist">
		<xs:attributeGroup ref="attrs"/>
	</xs:attributeGroup>
	<!-- =================== Address ========================================== -->
	<!-- information on author -->
	<xs:element name="address" substitutionGroup="blocktext">
		<xs:complexType>
			<xs:complexContent>
				<xs:extension base="Inline">
					<xs:attributeGroup ref="address.attlist"/>
				</xs:extension>
			</xs:complexContent>
		</xs:complexType>
	</xs:element>
	<xs:attributeGroup name="address.attlist">
		<xs:attributeGroup ref="attrs"/>
	</xs:attributeGroup>
	<!-- =================== Horizontal Rule ================================== -->
	<xs:element name="hr" substitutionGroup="blocktext">
		<xs:complexType>
			<xs:attributeGroup ref="hr.attlist"/>
		</xs:complexType>
	</xs:element>
	<xs:attributeGroup name="hr.attlist">
		<xs:attributeGroup ref="attrs"/>
	</xs:attributeGroup>
	<!-- =================== Preformatted Text ================================ -->
	<!-- content is %Inline; excluding "img|object|big|small|sub|sup" -->
	<xs:element name="pre" substitutionGroup="blocktext">
		<xs:complexType>
			<xs:complexContent>
				<xs:extension base="pre.content">
					<xs:attributeGroup ref="pre.attlist"/>
				</xs:extension>
			</xs:complexContent>
		</xs:complexType>
	</xs:element>
	<xs:attributeGroup name="pre.attlist">
		<xs:attributeGroup ref="attrs"/>
	</xs:attributeGroup>
	<!-- =================== Block-like Quotes ================================ -->
	<xs:element name="blockquote" substitutionGroup="blocktext">
		<xs:complexType>
			<xs:complexContent>
				<xs:extension base="Block">
					<xs:attributeGroup ref="blockquote.attlist"/>
				</xs:extension>
			</xs:complexContent>
		</xs:complexType>
	</xs:element>
	<xs:attributeGroup name="blockquote.attlist">
		<xs:attributeGroup ref="attrs"/>
		<xs:attribute name="cite" type="URI"/>
	</xs:attributeGroup>
	<!-- ================== The Anchor Element ================================ -->
	<!-- content is %Inline; except that anchors shouldn't be nested -->
	<xs:element name="a" substitutionGroup="inline">
		<xs:complexType>
			<xs:complexContent>
				<xs:extension base="a.content">
					<xs:attributeGroup ref="a.attlist"/>
				</xs:extension>
			</xs:complexContent>
		</xs:complexType>
	</xs:element>
	<xs:attributeGroup name="a.attlist">
		<xs:attributeGroup ref="attrs"/>
		<xs:attribute name="charset" type="Charset"/>
		<xs:attribute name="type" type="XHTMLContentType"/>
		<xs:attribute name="name" type="xs:NMTOKEN"/>
		<xs:attribute name="href" type="URI"/>
		<xs:attribute name="hreflang" type="XHTMLLanguageCode"/>
		<xs:attribute name="rel" type="LinkTypes"/>
		<xs:attribute name="rev" type="LinkTypes"/>
		<xs:attribute name="accesskey" type="Character"/>
		<xs:attribute name="shape" type="Shape" default="rect"/>
		<xs:attribute name="coords" type="Coords"/>
		<xs:attribute name="tabindex" type="XHTMLNumber"/>
		<xs:attribute name="onfocus" type="Script"/>
		<xs:attribute name="onblur" type="Script"/>
	</xs:attributeGroup>
	<!-- ===================== Inline Elements ================================ -->
	<xs:element name="span" substitutionGroup="special">
		<xs:complexType>
			<xs:complexContent>
				<xs:extension base="Inline">
					<xs:attributeGroup ref="span.attlist"/>
				</xs:extension>
			</xs:complexContent>
		</xs:complexType>
	</xs:element>
	<!-- generic language/style container -->
	<xs:attributeGroup name="span.attlist">
		<xs:attributeGroup ref="attrs"/>
	</xs:attributeGroup>
	<xs:element name="bdo" substitutionGroup="special">
		<xs:complexType>
			<xs:complexContent>
				<xs:extension base="Inline">
					<xs:attributeGroup ref="bdo.attlist"/>
				</xs:extension>
			</xs:complexContent>
		</xs:complexType>
	</xs:element>
	<!-- I18N BiDi over-ride -->
	<xs:attributeGroup name="bdo.attlist">
		<xs:attributeGroup ref="coreattrs"/>
		<xs:attribute name="lang" type="XHTMLLanguageCode"/>
		<xs:attribute name="dir" use="required">
			<xs:simpleType>
				<xs:restriction base="xs:token">
					<xs:enumeration value="ltr"/>
					<xs:enumeration value="rtl"/>
				</xs:restriction>
			</xs:simpleType>
		</xs:attribute>
	</xs:attributeGroup>
	<xs:element name="br" substitutionGroup="special">
		<xs:complexType>
			<xs:attributeGroup ref="br.attlist"/>
		</xs:complexType>
	</xs:element>
	<!-- forced line break -->
	<xs:attributeGroup name="br.attlist">
		<xs:attributeGroup ref="coreattrs"/>
	</xs:attributeGroup>
	<xs:element name="em" substitutionGroup="phrase">
		<xs:complexType>
			<xs:complexContent>
				<xs:extension base="Inline">
					<xs:attributeGroup ref="em.attlist"/>
				</xs:extension>
			</xs:complexContent>
		</xs:complexType>
	</xs:element>
	<!-- emphasis -->
	<xs:attributeGroup name="em.attlist">
		<xs:attributeGroup ref="attrs"/>
	</xs:attributeGroup>
	<xs:element name="strong" substitutionGroup="phrase">
		<xs:complexType>
			<xs:complexContent>
				<xs:extension base="Inline">
					<xs:attributeGroup ref="strong.attlist"/>
				</xs:extension>
			</xs:complexContent>
		</xs:complexType>
	</xs:element>
	<!-- strong emphasis -->
	<xs:attributeGroup name="strong.attlist">
		<xs:attributeGroup ref="attrs"/>
	</xs:attributeGroup>
	<xs:element name="dfn" substitutionGroup="phrase">
		<xs:complexType>
			<xs:complexContent>
				<xs:extension base="Inline">
					<xs:attributeGroup ref="dfn.attlist"/>
				</xs:extension>
			</xs:complexContent>
		</xs:complexType>
	</xs:element>
	<!-- definitional -->
	<xs:attributeGroup name="dfn.attlist">
		<xs:attributeGroup ref="attrs"/>
	</xs:attributeGroup>
	<xs:element name="code" substitutionGroup="phrase">
		<xs:complexType>
			<xs:complexContent>
				<xs:extension base="Inline">
					<xs:attributeGroup ref="code.attlist"/>
				</xs:extension>
			</xs:complexContent>
		</xs:complexType>
	</xs:element>
	<!-- program code -->
	<xs:attributeGroup name="code.attlist">
		<xs:attributeGroup ref="attrs"/>
	</xs:attributeGroup>
	<xs:element name="samp" substitutionGroup="phrase">
		<xs:complexType>
			<xs:complexContent>
				<xs:extension base="Inline">
					<xs:attributeGroup ref="samp.attlist"/>
				</xs:extension>
			</xs:complexContent>
		</xs:complexType>
	</xs:element>
	<!-- sample -->
	<xs:attributeGroup name="samp.attlist">
		<xs:attributeGroup ref="attrs"/>
	</xs:attributeGroup>
	<xs:element name="kbd" substitutionGroup="phrase">
		<xs:complexType>
			<xs:complexContent>
				<xs:extension base="Inline">
					<xs:attributeGroup ref="kbd.attlist"/>
				</xs:extension>
			</xs:complexContent>
		</xs:complexType>
	</xs:element>
	<!-- something user would type -->
	<xs:attributeGroup name="kbd.attlist">
		<xs:attributeGroup ref="attrs"/>
	</xs:attributeGroup>
	<xs:element name="var" substitutionGroup="phrase">
		<xs:complexType>
			<xs:complexContent>
				<xs:extension base="Inline">
					<xs:attributeGroup ref="var.attlist"/>
				</xs:extension>
			</xs:complexContent>
		</xs:complexType>
	</xs:element>
	<!-- variable -->
	<xs:attributeGroup name="var.attlist">
		<xs:attributeGroup ref="attrs"/>
	</xs:attributeGroup>
	<xs:element name="cite" substitutionGroup="phrase">
		<xs:complexType>
			<xs:complexContent>
				<xs:extension base="Inline">
					<xs:attributeGroup ref="cite.attlist"/>
				</xs:extension>
			</xs:complexContent>
		</xs:complexType>
	</xs:element>
	<!-- citation -->
	<xs:attributeGroup name="cite.attlist">
		<xs:attributeGroup ref="attrs"/>
	</xs:attributeGroup>
	<xs:element name="abbr" substitutionGroup="phrase">
		<xs:complexType>
			<xs:complexContent>
				<xs:extension base="Inline">
					<xs:attributeGroup ref="abbr.attlist"/>
				</xs:extension>
			</xs:complexContent>
		</xs:complexType>
	</xs:element>
	<!-- abbreviation -->
	<xs:attributeGroup name="abbr.attlist">
		<xs:attributeGroup ref="attrs"/>
	</xs:attributeGroup>
	<xs:element name="acronym" substitutionGroup="phrase">
		<xs:complexType>
			<xs:complexContent>
				<xs:extension base="Inline">
					<xs:attributeGroup ref="acronym.attlist"/>
				</xs:extension>
			</xs:complexContent>
		</xs:complexType>
	</xs:element>
	<!-- acronym -->
	<xs:attributeGroup name="acronym.attlist">
		<xs:attributeGroup ref="attrs"/>
	</xs:attributeGroup>
	<xs:element name="q" substitutionGroup="phrase">
		<xs:complexType>
			<xs:complexContent>
				<xs:extension base="Inline">
					<xs:attributeGroup ref="q.attlist"/>
				</xs:extension>
			</xs:complexContent>
		</xs:complexType>
	</xs:element>
	<!-- inlined quote -->
	<xs:attributeGroup name="q.attlist">
		<xs:attributeGroup ref="attrs"/>
		<xs:attribute name="cite" type="URI"/>
	</xs:attributeGroup>
	<xs:element name="sub" substitutionGroup="phrase">
		<xs:complexType>
			<xs:complexContent>
				<xs:extension base="Inline">
					<xs:attributeGroup ref="sub.attlist"/>
				</xs:extension>
			</xs:complexContent>
		</xs:complexType>
	</xs:element>
	<!-- subscript -->
	<xs:attributeGroup name="sub.attlist">
		<xs:attributeGroup ref="attrs"/>
	</xs:attributeGroup>
	<xs:element name="sup" substitutionGroup="phrase">
		<xs:complexType>
			<xs:complexContent>
				<xs:extension base="Inline">
					<xs:attributeGroup ref="sup.attlist"/>
				</xs:extension>
			</xs:complexContent>
		</xs:complexType>
	</xs:element>
	<!-- superscript -->
	<xs:attributeGroup name="sup.attlist">
		<xs:attributeGroup ref="attrs"/>
	</xs:attributeGroup>
	<xs:element name="tt" substitutionGroup="fontstyle">
		<xs:complexType>
			<xs:complexContent>
				<xs:extension base="Inline">
					<xs:attributeGroup ref="tt.attlist"/>
				</xs:extension>
			</xs:complexContent>
		</xs:complexType>
	</xs:element>
	<!-- fixed pitch font -->
	<xs:attributeGroup name="tt.attlist">
		<xs:attributeGroup ref="attrs"/>
	</xs:attributeGroup>
	<xs:element name="i" substitutionGroup="fontstyle">
		<xs:complexType>
			<xs:complexContent>
				<xs:extension base="Inline">
					<xs:attributeGroup ref="i.attlist"/>
				</xs:extension>
			</xs:complexContent>
		</xs:complexType>
	</xs:element>
	<!-- italic font -->
	<xs:attributeGroup name="i.attlist">
		<xs:attributeGroup ref="attrs"/>
	</xs:attributeGroup>
	<xs:element name="b" substitutionGroup="fontstyle">
		<xs:complexType>
			<xs:complexContent>
				<xs:extension base="Inline">
					<xs:attributeGroup ref="b.attlist"/>
				</xs:extension>
			</xs:complexContent>
		</xs:complexType>
	</xs:element>
	<!-- bold font -->
	<xs:attributeGroup name="b.attlist">
		<xs:attributeGroup ref="attrs"/>
	</xs:attributeGroup>
	<xs:element name="big" substitutionGroup="fontstyle">
		<xs:complexType>
			<xs:complexContent>
				<xs:extension base="Inline">
					<xs:attributeGroup ref="big.attlist"/>
				</xs:extension>
			</xs:complexContent>
		</xs:complexType>
	</xs:element>
	<!-- bigger font -->
	<xs:attributeGroup name="big.attlist">
		<xs:attributeGroup ref="attrs"/>
	</xs:attributeGroup>
	<xs:element name="small" substitutionGroup="fontstyle">
		<xs:complexType>
			<xs:complexContent>
				<xs:extension base="Inline">
					<xs:attributeGroup ref="small.attlist"/>
				</xs:extension>
			</xs:complexContent>
		</xs:complexType>
	</xs:element>
	<!-- smaller font -->
	<xs:attributeGroup name="small.attlist">
		<xs:attributeGroup ref="attrs"/>
	</xs:attributeGroup>
	<!-- ==================== Object ====================================== -->
	<!--
    object is used to embed objects as part of HTML pages.
    param elements should precede other content. Parameters
    can also be expressed as attribute/value pairs on the
    object element itself when brevity is desired.
  -->
	<xs:element name="object" substitutionGroup="special">
		<xs:complexType mixed="true">
			<xs:choice minOccurs="0" maxOccurs="unbounded">
				<xs:element ref="param"/>
				<xs:element ref="block"/>
				<xs:element ref="inline"/>
			</xs:choice>
			<xs:attributeGroup ref="object.attlist"/>
		</xs:complexType>
	</xs:element>
	<xs:attributeGroup name="object.attlist">
		<xs:attributeGroup ref="attrs"/>
		<xs:attribute name="declare">
			<xs:simpleType>
				<xs:restriction base="xs:token">
					<xs:enumeration value="declare"/>
				</xs:restriction>
			</xs:simpleType>
		</xs:attribute>
		<xs:attribute name="classid" type="URI"/>
		<xs:attribute name="codebase" type="URI"/>
		<xs:attribute name="data" type="URI"/>
		<xs:attribute name="type" type="XHTMLContentType"/>
		<xs:attribute name="codetype" type="XHTMLContentType"/>
		<xs:attribute name="archive" type="UriList"/>
		<xs:attribute name="standby" type="XHTMLText"/>
		<xs:attribute name="height" type="Length"/>
		<xs:attribute name="width" type="Length"/>
		<xs:attribute name="usemap" type="URI"/>
		<xs:attribute name="name" type="xs:NMTOKEN"/>
		<xs:attribute name="tabindex" type="XHTMLNumber"/>
	</xs:attributeGroup>
	<!--
    param is used to supply a named property value.
    In XML it would seem natural to follow RDF and support an
    abbreviated syntax where the param elements are replaced
    by attribute value pairs on the object start tag.
  -->
	<xs:element name="param">
		<xs:complexType>
			<xs:attributeGroup ref="param.attlist"/>
		</xs:complexType>
	</xs:element>
	<xs:attributeGroup name="param.attlist">
		<xs:attribute name="id" type="xs:ID"/>
		<xs:attribute name="name"/>
		<xs:attribute name="value"/>
		<xs:attribute name="valuetype" default="data">
			<xs:simpleType>
				<xs:restriction base="xs:token">
					<xs:enumeration value="data"/>
					<xs:enumeration value="ref"/>
					<xs:enumeration value="object"/>
				</xs:restriction>
			</xs:simpleType>
		</xs:attribute>
		<xs:attribute name="type" type="XHTMLContentType"/>
	</xs:attributeGroup>
	<!-- =================== Images =========================================== -->
	<!--
    To avoid accessibility problems for people who aren't
    able to see the image, you should provide a text
    description using the alt and longdesc attributes.
    In addition, avoid the use of server-side image maps.
    Note that in this DTD there is no name attribute. That
    is only available in the transitional and frameset DTD.
  -->
	<xs:element name="img" substitutionGroup="special">
		<xs:complexType>
			<xs:attributeGroup ref="img.attlist"/>
		</xs:complexType>
	</xs:element>
	<xs:attributeGroup name="img.attlist">
		<xs:attributeGroup ref="attrs"/>
		<xs:attribute name="src" type="URI" use="required"/>
		<xs:attribute name="alt" type="XHTMLText" use="required"/>
		<xs:attribute name="longdesc" type="URI"/>
		<xs:attribute name="height" type="Length"/>
		<xs:attribute name="width" type="Length"/>
		<xs:attribute name="usemap" type="URI"/>
		<xs:attribute name="ismap">
			<xs:simpleType>
				<xs:restriction base="xs:token">
					<xs:enumeration value="ismap"/>
				</xs:restriction>
			</xs:simpleType>
		</xs:attribute>
	</xs:attributeGroup>
	<!--
    usemap points to a map element which may be in this document
    or an external document, although the latter is not widely supported
  -->
	<!-- ================== Client-side image maps ============================ -->
	<!--
    These can be placed in the same document or grouped in a
    separate document although this isn't yet widely supported
  -->
	<xs:element name="map" substitutionGroup="special">
		<xs:complexType>
			<xs:choice>
				<xs:element ref="block" maxOccurs="unbounded"/>
				<xs:element ref="area" maxOccurs="unbounded"/>
			</xs:choice>
			<xs:attributeGroup ref="map.attlist"/>
		</xs:complexType>
	</xs:element>
	<xs:attributeGroup name="map.attlist">
		<xs:attributeGroup ref="i18n"/>
		<xs:attribute name="id" type="xs:ID" use="required"/>
		<xs:attribute name="class"/>
		<xs:attribute name="style" type="StyleSheet"/>
		<xs:attribute name="title" type="XHTMLText"/>
		<xs:attribute name="name" type="xs:NMTOKEN"/>
	</xs:attributeGroup>
	<xs:element name="area">
		<xs:complexType>
			<xs:attributeGroup ref="area.attlist"/>
		</xs:complexType>
	</xs:element>
	<xs:attributeGroup name="area.attlist">
		<xs:attributeGroup ref="attrs"/>
		<xs:attribute name="shape" type="Shape" default="rect"/>
		<xs:attribute name="coords" type="Coords"/>
		<xs:attribute name="href" type="URI"/>
		<xs:attribute name="nohref">
			<xs:simpleType>
				<xs:restriction base="xs:token">
					<xs:enumeration value="nohref"/>
				</xs:restriction>
			</xs:simpleType>
		</xs:attribute>
		<xs:attribute name="alt" type="XHTMLText" use="required"/>
	</xs:attributeGroup>
	<!-- ======================= Tables ======================================= -->
	<!-- Derived from IETF HTML table standard, see [RFC1942] -->
	<!--
    The border attribute sets the thickness of the frame around the
    table. The default units are screen pixels.
    
    The frame attribute specifies which parts of the frame around
    the table should be rendered. The values are not the same as
    CALS to avoid a name clash with the valign attribute.
  -->
	<xs:simpleType name="TFrame">
		<xs:restriction base="xs:token">
			<xs:enumeration value="void"/>
			<xs:enumeration value="above"/>
			<xs:enumeration value="below"/>
			<xs:enumeration value="hsides"/>
			<xs:enumeration value="lhs"/>
			<xs:enumeration value="rhs"/>
			<xs:enumeration value="vsides"/>
			<xs:enumeration value="box"/>
			<xs:enumeration value="border"/>
		</xs:restriction>
	</xs:simpleType>
	<!--
    The rules attribute defines which rules to draw between cells:
    
    If rules is absent then assume:
        "none" if border is absent or border="0" otherwise "all"
  -->
	<xs:simpleType name="TRules">
		<xs:restriction base="xs:token">
			<xs:enumeration value="none"/>
			<xs:enumeration value="groups"/>
			<xs:enumeration value="rows"/>
			<xs:enumeration value="cols"/>
			<xs:enumeration value="all"/>
		</xs:restriction>
	</xs:simpleType>
	<!-- horizontal placement of table relative to document -->
	<!--
    horizontal alignment attributes for cell contents
    
    char        alignment char, e.g. char=':'
    charoff     offset for alignment char
  -->
	<xs:attributeGroup name="cellhalign">
		<xs:attribute name="align">
			<xs:simpleType>
				<xs:restriction base="xs:token">
					<xs:enumeration value="left"/>
					<xs:enumeration value="center"/>
					<xs:enumeration value="right"/>
					<xs:enumeration value="justify"/>
					<xs:enumeration value="char"/>
				</xs:restriction>
			</xs:simpleType>
		</xs:attribute>
		<xs:attribute name="char" type="Character"/>
		<xs:attribute name="charoff" type="Length"/>
	</xs:attributeGroup>
	<!-- vertical alignment attributes for cell contents -->
	<xs:attributeGroup name="cellvalign">
		<xs:attribute name="valign">
			<xs:simpleType>
				<xs:restriction base="xs:token">
					<xs:enumeration value="top"/>
					<xs:enumeration value="middle"/>
					<xs:enumeration value="bottom"/>
					<xs:enumeration value="baseline"/>
				</xs:restriction>
			</xs:simpleType>
		</xs:attribute>
	</xs:attributeGroup>
	<xs:element name="table" substitutionGroup="block">
		<xs:complexType>
			<xs:sequence>
				<xs:element ref="caption" minOccurs="0"/>
				<xs:choice>
					<xs:element ref="col" minOccurs="0" maxOccurs="unbounded"/>
					<xs:element ref="colgroup" minOccurs="0" maxOccurs="unbounded"/>
				</xs:choice>
				<xs:element ref="thead" minOccurs="0"/>
				<xs:element ref="tfoot" minOccurs="0"/>
				<xs:choice>
					<xs:element ref="tbody" maxOccurs="unbounded"/>
					<xs:element ref="tr" maxOccurs="unbounded"/>
				</xs:choice>
			</xs:sequence>
			<xs:attributeGroup ref="table.attlist"/>
		</xs:complexType>
	</xs:element>
	<xs:element name="caption">
		<xs:complexType>
			<xs:complexContent>
				<xs:extension base="Inline">
					<xs:attributeGroup ref="caption.attlist"/>
				</xs:extension>
			</xs:complexContent>
		</xs:complexType>
	</xs:element>
	<xs:element name="thead">
		<xs:complexType>
			<xs:sequence>
				<xs:element ref="tr" maxOccurs="unbounded"/>
			</xs:sequence>
			<xs:attributeGroup ref="thead.attlist"/>
		</xs:complexType>
	</xs:element>
	<xs:element name="tfoot">
		<xs:complexType>
			<xs:sequence>
				<xs:element ref="tr" maxOccurs="unbounded"/>
			</xs:sequence>
			<xs:attributeGroup ref="tfoot.attlist"/>
		</xs:complexType>
	</xs:element>
	<xs:element name="tbody">
		<xs:complexType>
			<xs:sequence>
				<xs:element ref="tr" maxOccurs="unbounded"/>
			</xs:sequence>
			<xs:attributeGroup ref="tbody.attlist"/>
		</xs:complexType>
	</xs:element>
	<xs:element name="colgroup">
		<xs:complexType>
			<xs:sequence>
				<xs:element ref="col" minOccurs="0" maxOccurs="unbounded"/>
			</xs:sequence>
			<xs:attributeGroup ref="colgroup.attlist"/>
		</xs:complexType>
	</xs:element>
	<xs:element name="col">
		<xs:complexType>
			<xs:attributeGroup ref="col.attlist"/>
		</xs:complexType>
	</xs:element>
	<xs:element name="tr">
		<xs:complexType>
			<xs:choice maxOccurs="unbounded">
				<xs:element ref="th"/>
				<xs:element ref="td"/>
			</xs:choice>
			<xs:attributeGroup ref="tr.attlist"/>
		</xs:complexType>
	</xs:element>
	<xs:element name="th">
		<xs:complexType>
			<xs:complexContent>
				<xs:extension base="Flow">
					<xs:attributeGroup ref="th.attlist"/>
				</xs:extension>
			</xs:complexContent>
		</xs:complexType>
	</xs:element>
	<xs:element name="td">
		<xs:complexType>
			<xs:complexContent>
				<xs:extension base="Flow">
					<xs:attributeGroup ref="td.attlist"/>
				</xs:extension>
			</xs:complexContent>
		</xs:complexType>
	</xs:element>
	<xs:attributeGroup name="table.attlist">
		<xs:attributeGroup ref="attrs"/>
		<xs:attribute name="summary" type="XHTMLText"/>
		<xs:attribute name="width" type="Length"/>
		<xs:attribute name="border" type="Pixels"/>
		<xs:attribute name="frame" type="TFrame"/>
		<xs:attribute name="rules" type="TRules"/>
		<xs:attribute name="cellspacing" type="Length"/>
		<xs:attribute name="cellpadding" type="Length"/>
	</xs:attributeGroup>
	<xs:attributeGroup name="caption.attlist">
		<xs:attributeGroup ref="attrs"/>
	</xs:attributeGroup>
	<!--
    colgroup groups a set of col elements. It allows you to group
    several semantically related columns together.
  -->
	<xs:attributeGroup name="colgroup.attlist">
		<xs:attributeGroup ref="attrs"/>
		<xs:attribute name="span" type="XHTMLNumber" default="1"/>
		<xs:attribute name="width" type="MultiLength"/>
		<xs:attributeGroup ref="cellhalign"/>
		<xs:attributeGroup ref="cellvalign"/>
	</xs:attributeGroup>
	<!--
    col elements define the alignment properties for cells in
    one or more columns.
    
    The width attribute specifies the width of the columns, e.g.
    
        width=64        width in screen pixels
        width=0.5*      relative width of 0.5
    
    The span attribute causes the attributes of one
    col element to apply to more than one column.
  -->
	<xs:attributeGroup name="col.attlist">
		<xs:attributeGroup ref="attrs"/>
		<xs:attribute name="span" type="XHTMLNumber" default="1"/>
		<xs:attribute name="width" type="MultiLength"/>
		<xs:attributeGroup ref="cellhalign"/>
		<xs:attributeGroup ref="cellvalign"/>
	</xs:attributeGroup>
	<!--
    Use thead to duplicate headers when breaking table
    across page boundaries, or for static headers when
    tbody sections are rendered in scrolling panel.
    
    Use tfoot to duplicate footers when breaking table
    across page boundaries, or for static footers when
    tbody sections are rendered in scrolling panel.
    
    Use multiple tbody sections when rules are needed
    between groups of table rows.
  -->
	<xs:attributeGroup name="thead.attlist">
		<xs:attributeGroup ref="attrs"/>
		<xs:attributeGroup ref="cellhalign"/>
		<xs:attributeGroup ref="cellvalign"/>
	</xs:attributeGroup>
	<xs:attributeGroup name="tfoot.attlist">
		<xs:attributeGroup ref="attrs"/>
		<xs:attributeGroup ref="cellhalign"/>
		<xs:attributeGroup ref="cellvalign"/>
	</xs:attributeGroup>
	<xs:attributeGroup name="tbody.attlist">
		<xs:attributeGroup ref="attrs"/>
		<xs:attributeGroup ref="cellhalign"/>
		<xs:attributeGroup ref="cellvalign"/>
	</xs:attributeGroup>
	<xs:attributeGroup name="tr.attlist">
		<xs:attributeGroup ref="attrs"/>
		<xs:attributeGroup ref="cellhalign"/>
		<xs:attributeGroup ref="cellvalign"/>
	</xs:attributeGroup>
	<!-- Scope is simpler than headers attribute for common tables -->
	<xs:simpleType name="Scope">
		<xs:restriction base="xs:token">
			<xs:enumeration value="row"/>
			<xs:enumeration value="col"/>
			<xs:enumeration value="rowgroup"/>
			<xs:enumeration value="colgroup"/>
		</xs:restriction>
	</xs:simpleType>
	<!-- th is for headers, td for data and for cells acting as both -->
	<xs:attributeGroup name="th.attlist">
		<xs:attributeGroup ref="attrs"/>
		<xs:attribute name="abbr" type="XHTMLText"/>
		<xs:attribute name="axis"/>
		<xs:attribute name="headers" type="xs:IDREFS"/>
		<xs:attribute name="scope" type="Scope"/>
		<xs:attribute name="rowspan" type="XHTMLNumber" default="1"/>
		<xs:attribute name="colspan" type="XHTMLNumber" default="1"/>
		<xs:attributeGroup ref="cellhalign"/>
		<xs:attributeGroup ref="cellvalign"/>
	</xs:attributeGroup>
	<xs:attributeGroup name="td.attlist">
		<xs:attributeGroup ref="attrs"/>
		<xs:attribute name="abbr" type="XHTMLText"/>
		<xs:attribute name="axis"/>
		<xs:attribute name="headers" type="xs:IDREFS"/>
		<xs:attribute name="scope" type="Scope"/>
		<xs:attribute name="rowspan" type="XHTMLNumber" default="1"/>
		<xs:attribute name="colspan" type="XHTMLNumber" default="1"/>
		<xs:attributeGroup ref="cellhalign"/>
		<xs:attributeGroup ref="cellvalign"/>
	</xs:attributeGroup>
</xs:schema>
