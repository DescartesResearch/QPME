package edu.kit.ipd.descartes.qpme.model.migration;

import java.io.InputStream;

import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMResult;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamSource;

import org.osgi.framework.Version;
import org.w3c.dom.Document;

public class DocumentMigrationHandler {
	
	private static Version CURRENT_VERSION = new Version(2, 1, 0);
	private static Version VERSION_2_0 = new Version(2, 0, 0);
	private static Version VERSION_1_5_2 = new Version(1, 5, 2);
	
	public boolean isMigrationRequired(Document qpe) {
		Version docVersion = getDocumentVersion(qpe);
		return !isCompliant(docVersion, CURRENT_VERSION);
	}
	
	public boolean canMigrate(Document qpe) {
		Version docVersion = getDocumentVersion(qpe);
		return isCompliant(docVersion, CURRENT_VERSION) || isCompliant(docVersion, VERSION_2_0) || VERSION_1_5_2.equals(docVersion);
	}
	
	public Document migrate(Document qpe) throws TransformerException  {
		Version docVersion = getDocumentVersion(qpe);
		if (isCompliant(docVersion, CURRENT_VERSION)) {
			return qpe;
		} else if (isCompliant(docVersion, VERSION_2_0) || VERSION_1_5_2.equals(docVersion)) {
			InputStream xslt = getClass().getClassLoader().getResourceAsStream("edu/kit/ipd/descartes/qpme/model/migration/transforms/v2_1_migration.xsl");
			TransformerFactory transformFactory = TransformerFactory.newInstance();
			Transformer hqpnTransform = transformFactory.newTransformer(new StreamSource(xslt));

			DOMResult migratedDoc = new DOMResult();
			hqpnTransform.transform(new DOMSource(qpe), migratedDoc);
			return (Document)migratedDoc.getNode();
		}
		
		return null;	
	}
	
	private Version getDocumentVersion(Document qpe) {
		String versionText = qpe.getDocumentElement().getAttribute("qpme-version");
		return Version.parseVersion(versionText);
	}
	
	private boolean isCompliant(Version docVersion, Version complianceLevel) {
		return (docVersion.getMajor() == complianceLevel.getMajor() && docVersion.getMinor() == complianceLevel.getMinor());
	}

}
