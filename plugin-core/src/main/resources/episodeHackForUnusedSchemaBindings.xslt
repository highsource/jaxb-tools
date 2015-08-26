<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:jaxp="http://java.sun.com/xml/ns/jaxb"
   version="1.0">
    <!-- this template is applied by default to all nodes and attributes -->
   <xsl:template match="@*|node()">
        <!-- just copy all my attributes and child nodes, except if there's a better template for some of them -->
      <xsl:copy>
         <xsl:apply-templates select="@*|node()" />
      </xsl:copy>
   </xsl:template>
   <xsl:template match="jaxp:bindings[@scd='x-schema::tns']">
      <xsl:copy>
         <xsl:attribute name="if-exists">true</xsl:attribute>
         <xsl:apply-templates select="@*|node()" />
      </xsl:copy>
   </xsl:template>
</xsl:stylesheet>