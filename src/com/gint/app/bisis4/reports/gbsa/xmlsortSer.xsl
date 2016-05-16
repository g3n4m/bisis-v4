<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="2.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" >
	<xsl:template match="report">
		<report>
			<xsl:for-each select="item">
			<xsl:sort select="rbr"/>
		    <item>
			   <rbr><xsl:value-of select="rbr"/></rbr>
			  <datum><xsl:value-of select="datum"/></datum>
			  <opis><xsl:value-of select="opis"/></opis>
			   <god><xsl:value-of select="god"/></god>
			   <brSv><xsl:value-of select="brSv"/></brSv>
			   <dim><xsl:value-of select="dim"/></dim>
			  <povez><xsl:value-of select="povez"/></povez>
			  <nabavkaO><xsl:value-of select="nabavkaO"/></nabavkaO>
			  <nabavkaK><xsl:value-of select="nabavkaK"/></nabavkaK>
			  <nabavkaR><xsl:value-of select="nabavkaR"/></nabavkaR>
			  <nabavkaP><xsl:value-of select="nabavkaP"/></nabavkaP>
			  <cena><xsl:value-of select="cena"/></cena>
			  <signatura><xsl:value-of select="signatura"/></signatura>
			  <napomena><xsl:value-of select="napomena"/></napomena>
			</item>
				
			</xsl:for-each>
		</report>
	</xsl:template>
</xsl:stylesheet>


