package edu.kit.ipd.descartes.qpme.model.migration;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.junit.Before;
import org.junit.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class DocumentMigrationHandlerTest {
	
	private Document doc;
	private DocumentMigrationHandler handler;
	
	@Before
	public void init() throws Exception {
		DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
		doc = builder.newDocument();
		Element element = doc.createElement("net");
		element.setAttribute("qpme-version", "2.2");
		doc.appendChild(element);
		handler = new DocumentMigrationHandler();
	}
	
	@Test
	public void testCanMigrateSupportedVersion() throws Exception {
		Element root = (Element)doc.getElementsByTagName("net").item(0);
		root.setAttribute("qpme-version", "1.5.2");
		assertTrue(handler.canMigrate(doc));
		root.setAttribute("qpme-version", "2.0");
		assertTrue(handler.canMigrate(doc));
		root.setAttribute("qpme-version", "2.0.1");
		assertTrue(handler.canMigrate(doc));
		root.setAttribute("qpme-version", "2.1");
		assertTrue(handler.canMigrate(doc));
		root.setAttribute("qpme-version", "2.2");
		assertTrue(handler.canMigrate(doc));
	}
	
	@Test
	public void testCanMigrateUnsupportedVersion() throws Exception {
		Element root = (Element)doc.getElementsByTagName("net").item(0);
		root.setAttribute("qpme-version", "1.0");
		assertFalse(handler.canMigrate(doc));
		root.setAttribute("qpme-version", "10.10");
		assertFalse(handler.canMigrate(doc));
	}

	@Test
	public void testIsMigrationRequiredOld() throws Exception {
		assertFalse(handler.isMigrationRequired(doc));
	}
	
	@Test
	public void testIsMigrationRequiredNew() throws Exception {
		Element root = (Element)doc.getElementsByTagName("net").item(0);
		root.setAttribute("qpme-version", "10.10");
		assertTrue(handler.isMigrationRequired(doc));
	}

	@Test
	public void testMigrateCurrentVersion() throws Exception {
		Document result = handler.migrate(doc);
		assertEquals(doc, result);
	}

}
