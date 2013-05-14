<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
	xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">
	
	<xsl:template match="//net">
		<xsl:copy>
			<xsl:apply-templates select="@*" />
			
			<xsl:apply-templates select="./colors" />
			<xsl:apply-templates select="./queues" />
			<xsl:apply-templates select="./places" />
			<xsl:apply-templates select="./transitions" />
			<xsl:apply-templates select="./connections" />	
			<xsl:apply-templates select="./probes" />
			<xsl:apply-templates select="./meta-attributes" />		
		</xsl:copy>		
	</xsl:template>
	
	<xsl:template match="//place">
		<xsl:copy>
			<xsl:apply-templates select="@*" />
			
			<xsl:apply-templates select="./color-refs" />
			<xsl:apply-templates select="./meta-attributes" />
			<xsl:apply-templates select="./subnet" />	
		</xsl:copy>		
	</xsl:template>
	
	<xsl:template match="//transition">
		<xsl:copy>
			<xsl:apply-templates select="@*" />
			
			<xsl:apply-templates select="./modes" />
			<xsl:apply-templates select="./connections" />
			<xsl:apply-templates select="./meta-attributes" />		
		</xsl:copy>		
	</xsl:template>
	
	<xsl:template match="//probe">
		<xsl:copy>
			<xsl:apply-templates select="@*" />
			
			<xsl:apply-templates select="./color-refs" />
			<xsl:apply-templates select="./meta-attributes" />		
		</xsl:copy>		
	</xsl:template>
	
	<xsl:template match="//subnet">
		<xsl:copy>
			<xsl:apply-templates select="@*" />
			
			<xsl:apply-templates select="./colors" />
			<xsl:apply-templates select="./places" />
			<xsl:apply-templates select="./transitions" />
			<xsl:apply-templates select="./connections" />		
		</xsl:copy>
	</xsl:template>
	
	<xsl:template match="//color-ref/meta-attributes/meta-attribute/@name">
		<xsl:variable name="confName" select="parent::meta-attribute/@configuration-name" />
		<xsl:variable name="confType" select="/net/meta-attributes/meta-attribute[@name='sim-qpn' and @configuration-name=$confName]" />
		<xsl:if test=".='sim-qpn'">
			<xsl:choose>
				<xsl:when test="ancestor::place/@type='queueing-place'">
					<xsl:choose>
						<xsl:when test="$confType/@scenario=1">
							<xsl:attribute name="xsi:type">simqpn-batch-means-queueing-color-configuration</xsl:attribute>
							<xsl:if test="not(parent::meta-attribute/@queueMaxBuckets)">
								<xsl:attribute name="queueMaxBuckets">0</xsl:attribute>
							</xsl:if>
							<xsl:if test="not(parent::meta-attribute/@queueBucketSize)">
								<xsl:attribute name="queueBucketSize">0</xsl:attribute>
							</xsl:if>
							<xsl:if test="not(parent::meta-attribute/@maxBuckets)">
								<xsl:attribute name="maxBuckets">0</xsl:attribute>
							</xsl:if>
							<xsl:if test="not(parent::meta-attribute/@bucketSize)">
								<xsl:attribute name="bucketSize">0</xsl:attribute>
							</xsl:if>
						</xsl:when>
						<xsl:when test="$confType/@scenario=2">
							<xsl:attribute name="xsi:type">simqpn-replication-deletion-queueing-color-configuration</xsl:attribute>
						</xsl:when>
						<xsl:when test="$confType/@scenario=3">
							<xsl:attribute name="xsi:type">simqpn-welch-queueing-color-configuration</xsl:attribute>
						</xsl:when>
					</xsl:choose>
				</xsl:when>
				<xsl:otherwise>
					<xsl:choose>
						<xsl:when test="$confType/@scenario=1">
							<xsl:attribute name="xsi:type">simqpn-batch-means-color-configuration</xsl:attribute>
							<xsl:if test="not(parent::meta-attribute/@maxBuckets)">
								<xsl:attribute name="maxBuckets">0</xsl:attribute>
							</xsl:if>
							<xsl:if test="not(parent::meta-attribute/@bucketSize)">
								<xsl:attribute name="bucketSize">0</xsl:attribute>
							</xsl:if>
						</xsl:when>
						<xsl:when test="$confType/@scenario=2">
							<xsl:attribute name="xsi:type">simqpn-replication-deletion-color-configuration</xsl:attribute>
						</xsl:when>
						<xsl:when test="$confType/@scenario=3">
							<xsl:attribute name="xsi:type">simqpn-welch-color-configuration</xsl:attribute>
						</xsl:when>
					</xsl:choose>
				</xsl:otherwise>
			</xsl:choose>
		</xsl:if>
	</xsl:template>
	
	<xsl:template match="//place/meta-attributes/meta-attribute/@name">
		<xsl:choose>
			<xsl:when test=".='location'">
				<xsl:attribute name="xsi:type">location-attribute</xsl:attribute>
			</xsl:when>
			<xsl:when test=".='sim-qpn'">
				<xsl:attribute name="xsi:type">simqpn-place-configuration</xsl:attribute>
			</xsl:when>	
		</xsl:choose>
	</xsl:template>
	
	<xsl:template match="//transition/meta-attributes/meta-attribute/@name">
		<xsl:choose>
			<xsl:when test=".='location'">
				<xsl:attribute name="xsi:type">location-attribute</xsl:attribute>
			</xsl:when>
		</xsl:choose>
	</xsl:template>
	
	<xsl:template match="/net/meta-attributes/meta-attribute/@name">
		<xsl:choose>
			<xsl:when test=".='sim-qpn'">
				<xsl:attribute name="xsi:type">simqpn-configuration</xsl:attribute>
			</xsl:when>
		</xsl:choose>
	</xsl:template>
	
	<xsl:template match="color-ref">
		<xsl:copy>
			<xsl:choose>
				<xsl:when test="../../@type='ordinary-place'">
					<xsl:attribute name="xsi:type">ordinary-color-reference</xsl:attribute>
				</xsl:when>
				<xsl:when test="../../@type='queueing-place'">
					<xsl:attribute name="xsi:type">queueing-color-reference</xsl:attribute>
				</xsl:when>
				<xsl:when test="../../@type='subnet-place'">
					<xsl:attribute name="xsi:type">subnet-color-reference</xsl:attribute>
				</xsl:when>
				<xsl:when test="name(../..)='probe'">
					<xsl:attribute name="xsi:type">probe-color-reference</xsl:attribute>
				</xsl:when>
				<xsl:otherwise>
					<xsl:message terminate="yes">
						Unknown place type.
					</xsl:message>
				</xsl:otherwise>		
			</xsl:choose>	

			<xsl:apply-templates select="@*" />
			<xsl:apply-templates />
		</xsl:copy>
	</xsl:template>
	
	<xsl:template match="@type[parent::transition]">
		<xsl:choose>
			<xsl:when test=".='immediate-transition'">
				<xsl:attribute name="xsi:type">immediate-transition</xsl:attribute>
			</xsl:when>
			<xsl:when test=".='timed-transition'">
				<xsl:attribute name="xsi:type">timed-transition</xsl:attribute>
			</xsl:when>
		</xsl:choose>
	</xsl:template>
	
	<xsl:template match="@type[parent::place]">
		<xsl:choose>
			<xsl:when test=".='ordinary-place'">
				<xsl:attribute name="xsi:type">ordinary-place</xsl:attribute>
			</xsl:when>
			<xsl:when test=".='queueing-place'">
				<xsl:attribute name="xsi:type">queueing-place</xsl:attribute>
			</xsl:when>
			<xsl:when test=".='subnet-place'">
				<xsl:attribute name="xsi:type">subnet-place</xsl:attribute>
			</xsl:when>
			<xsl:otherwise>
				<xsl:message terminate="yes">
					Unknown place type.
				</xsl:message>
			</xsl:otherwise>
		</xsl:choose>
	</xsl:template>
	
	<xsl:template match="@start-place-id">
		<xsl:attribute name="start-place-id">
			<xsl:value-of select="concat('_', .)" />
		</xsl:attribute>
	</xsl:template>
	
	<xsl:template match="@end-place-id">
		<xsl:attribute name="end-place-id">
			<xsl:value-of select="concat('_', .)" />
		</xsl:attribute>
	</xsl:template>
	
	<xsl:template match="@queue-ref">
		<xsl:attribute name="queue-ref">
			<xsl:value-of select="concat('_', .)" />
		</xsl:attribute>
	</xsl:template>
	
	<xsl:template match="@color-id">
		<xsl:attribute name="color-id">
			<xsl:value-of select="concat('_', .)" />
		</xsl:attribute>
	</xsl:template>
	
	<xsl:template match="@target-id">
		<xsl:attribute name="target-id">
			<xsl:value-of select="concat('_', .)" />
		</xsl:attribute>
	</xsl:template>
	
	<xsl:template match="@source-id">
		<xsl:attribute name="source-id">
			<xsl:value-of select="concat('_', .)" />
		</xsl:attribute>
	</xsl:template>
	
	<xsl:template match="@id">
		<xsl:attribute name="id">
			<xsl:value-of select="concat('_', .)" />
		</xsl:attribute>
	</xsl:template>
	
	<xsl:template match="@*|node()" priority="-1">
		<xsl:copy>
			<xsl:apply-templates select="@*" />
			<xsl:apply-templates />
		</xsl:copy>
	</xsl:template>
</xsl:stylesheet>