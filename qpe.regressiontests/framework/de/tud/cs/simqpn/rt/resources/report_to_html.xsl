<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

	<xsl:template match="report">
		<html>
			<head>
			</head>
			<body>
				<h1><xsl:value-of select="@test" /></h1>
				<p>
					<xsl:value-of select="./description" disable-output-escaping="yes"/>
				</p>
				
				<p>
					<xsl:for-each select="//container[@type='reference-set']">
						<a>
							<xsl:attribute name="href">#<xsl:value-of select="@name" /></xsl:attribute>
							<xsl:value-of select="@name" />
						</a>&#160;
					</xsl:for-each>
				</p>
				
				<xsl:apply-templates select="./container" />
			</body>
		</html>
	</xsl:template>
	
	<xsl:template match="container[@type='report']">
		<xsl:apply-templates select="./child-containers/container" />
	</xsl:template>
	
	<xsl:template match="container[@type='reference-set']">
		<a>
			<xsl:attribute name="name"><xsl:value-of select="@name" /></xsl:attribute>
			<h3>Revision <xsl:value-of select="@name" /></h3>
		</a>
		
		<xsl:for-each select=".//container[contains(@type, 'place') or @type='queue']">
			<a>
				<xsl:attribute name="href">#<xsl:value-of select="../../@name" />_<xsl:value-of select="@name" />_<xsl:value-of select="@type" /></xsl:attribute>
				<xsl:value-of select="@name" />(<xsl:value-of select="@type" />)
			</a>&#160;
		</xsl:for-each>
		
		<table width="100%">
			<tr style="font-weight:bold;">
				<td colspan="3">Element</td>
				<td>Expected Mean</td>
				<td>Actual Mean</td>
				<td>Relative Error</td>
				<td>Statistically significant (p-value)</td>
			</tr>
			<xsl:apply-templates select="./check-results/check-result" />
			<xsl:apply-templates select="./child-containers/container" />	
		</table>		
	</xsl:template>
	
	<xsl:template match="container[@type='color']">
		<tr>
			<td width="60">&#160;</td>
			<td style="background-color:lightgrey;" colspan="6">Color <xsl:value-of select="@name" /></td>				
		</tr>
		<xsl:apply-templates select="./check-results/check-result" />
		<xsl:apply-templates select="./child-containers/container" />
	</xsl:template>

	<xsl:template match="container[contains(@type, 'place') or @type='queue']">		
		<tr>
			<a><xsl:attribute name="name">#<xsl:value-of select="../../@name" />_<xsl:value-of select="@name" />_<xsl:value-of select="@type" /></xsl:attribute></a>
			<td style="background-color:lightgrey;" colspan="7"><xsl:value-of select="@name"/>(<xsl:value-of select="@type" />)</td>
		</tr>
		<xsl:apply-templates select="./check-results/check-result" />
		<xsl:apply-templates select="./child-containers/container" />
	</xsl:template>
	
	<xsl:template match="check-result">
		<tr>
			<xsl:choose>
				<xsl:when test="@status='SUCCESS'">
					<td style="background-color: green;"><td>&#160;</td></td>
				</xsl:when>
				<xsl:when test="@status='HIGHLIGHT'">
					<td  style="background-color: yellow;"><td>&#160;</td></td>
				</xsl:when>
				<xsl:when test="@status='FAILURE' or @status='ERROR'">
					<td  style="background-color: red;"><td>&#160;</td></td>
				</xsl:when>			
			</xsl:choose>
			<td><xsl:value-of select="@name" /></td>
			<td><xsl:value-of select="@expected-mean" /></td>
			<td><xsl:value-of select="@actual-mean" /><xsl:value-of select="./error" /></td>
			<xsl:choose>
				<xsl:when test="@status='SUCCESS'">
					<td style="color: green;"><xsl:value-of select="@relative-error" />%</td>
					<td style="color: green;"><xsl:value-of select="@t-test" /></td>
				</xsl:when>
				<xsl:when test="@status='HIGHLIGHT'">
					<td  style="color: yellow;"><xsl:value-of select="@relative-error" />%</td>
					<td style="color: yellow;"><xsl:value-of select="@t-test" /></td>
				</xsl:when>
				<xsl:when test="@status='FAILURE' or @status='ERROR'">
					<td  style="color: red;"><xsl:value-of select="@relative-error" />%</td>
					<td style="color: red;"><xsl:value-of select="@t-test" /></td>
				</xsl:when>			
			</xsl:choose>			
		</tr>
	</xsl:template>
	
</xsl:stylesheet>