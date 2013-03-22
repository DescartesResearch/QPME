<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
	xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">

	<xsl:key name="allPlaces" match="place" use="@id" />
	<xsl:key name="allColorRefs" match="color-ref" use="@id" />
	
	<xsl:template match="net/colors">
		<xsl:copy>
			<xsl:for-each select="//color">
				<xsl:apply-templates select="." />
			</xsl:for-each>		
		</xsl:copy>
	</xsl:template>
	
	<xsl:template match="place[@xsi:type='subnet-place']">
		<xsl:for-each select="subnet/places">
			<xsl:apply-templates />
		</xsl:for-each>
	</xsl:template>

	<xsl:template match="net/transitions">
		<xsl:copy>
			<xsl:for-each select="//transition">
				<xsl:apply-templates select="." />
			</xsl:for-each>
		</xsl:copy>
	</xsl:template>
	
	<xsl:template match="net/connections">
		<xsl:copy>
			<xsl:for-each select="connection">
				<xsl:apply-templates select="." />
			</xsl:for-each>
			
			<xsl:for-each select="//subnet/connections/connection">
				<xsl:apply-templates select="." />
			</xsl:for-each>
		</xsl:copy>
	</xsl:template>
	
	<xsl:template match="connection[ancestor::transition]">
		<xsl:variable name="targetColorRef" select="key('allColorRefs', @target-id)" />
		<xsl:if test="$targetColorRef">
			<xsl:variable name="place" select="$targetColorRef/parent::node()/parent::place" />
			<xsl:choose>
				<xsl:when test="$place/@xsi:type='subnet-place'">
					<connection>
						<xsl:attribute name="source-id">
							<xsl:value-of select="@source-id" />
						</xsl:attribute>
						<xsl:attribute name="target-id">
							<xsl:value-of select="$place/subnet/places/place[@name='input-place']/color-refs/color-ref[@color-id=$targetColorRef/@color-id]/@id" />
						</xsl:attribute>
						<xsl:attribute name="count">
							<xsl:value-of select="@count" />
						</xsl:attribute>
						<xsl:attribute name="id">
							<xsl:value-of select="generate-id()" />
						</xsl:attribute>
					</connection>
				</xsl:when>
				<xsl:otherwise>
					<xsl:copy>
						<xsl:apply-templates select="@*" />
						<xsl:apply-templates />
					</xsl:copy>
				</xsl:otherwise>
			</xsl:choose>
		</xsl:if>
		
		<xsl:variable name="sourceColorRef" select="key('allColorRefs', @source-id)" />
		<xsl:if test="$sourceColorRef">
			<xsl:variable name="place" select="$sourceColorRef/parent::node()/parent::place" />
			<xsl:choose>
				<xsl:when test="$place/@xsi:type='subnet-place'">
					<connection>
						<xsl:attribute name="source-id">
							<xsl:value-of select="$place/subnet/places/place[@name='output-place']/color-refs/color-ref[@color-id=$sourceColorRef/@color-id]/@id" />
						</xsl:attribute>
						<xsl:attribute name="target-id">
							<xsl:value-of select="@target-id" />
						</xsl:attribute>
						<xsl:attribute name="count">
							<xsl:value-of select="@count" />
						</xsl:attribute>
						<xsl:attribute name="id">
							<xsl:value-of select="generate-id()" />
						</xsl:attribute>
					</connection>
				</xsl:when>
				<xsl:otherwise>
					<xsl:copy>
						<xsl:apply-templates select="@*" />
						<xsl:apply-templates />
					</xsl:copy>
				</xsl:otherwise> 
			</xsl:choose>
		</xsl:if>		
	</xsl:template>
	
	<xsl:template match="connection[not(ancestor::transition)]">		
		<xsl:variable name="targetPlace" select="key('allPlaces', @target-id)" />
		<xsl:variable name="sourcePlace" select="key('allPlaces', @source-id)" />
		
		<xsl:choose>
			<xsl:when test="$targetPlace/@xsi:type='subnet-place'">
				<connection>
					<xsl:attribute name="source-id">
						<xsl:value-of select="@source-id" />
					</xsl:attribute>
					<xsl:attribute name="target-id">
						<xsl:value-of select="$targetPlace/subnet/places/place[@name='input-place']/@id" />
					</xsl:attribute>
					<xsl:attribute name="id">
						<xsl:value-of select="generate-id()" />
					</xsl:attribute>
				</connection>
			</xsl:when>
			
			<xsl:when test="$sourcePlace/@xsi:type='subnet-place'">
				<connection>
					<xsl:attribute name="source-id">
						<xsl:value-of select="$sourcePlace/subnet/places/place[@name='output-place']/@id" />
					</xsl:attribute>
					<xsl:attribute name="target-id">
						<xsl:value-of select="@target-id" />
					</xsl:attribute>
					<xsl:attribute name="id">
						<xsl:value-of select="generate-id()" />
					</xsl:attribute>
				</connection>
			</xsl:when>
			
			<xsl:otherwise>
				<xsl:copy>
					<xsl:apply-templates select="@*" />
					<xsl:apply-templates />
				</xsl:copy>
			</xsl:otherwise>
		</xsl:choose>
	</xsl:template>

	<xsl:template match="@name[parent::color or parent::place or parent::transition]" >
		<xsl:attribute name="name">
			<xsl:for-each select="parent::node()/ancestor::place/@name">
				<xsl:value-of select="concat(., '.') " />
			</xsl:for-each>
			<xsl:value-of select="." />
		</xsl:attribute>
	</xsl:template>


	<xsl:template match="@*|node()" priority="-1">
		<xsl:copy>
			<xsl:apply-templates select="@*" />
			<xsl:apply-templates />
		</xsl:copy>
	</xsl:template>
</xsl:stylesheet>