package edu.kit.ipd.descartes.qpme.model.migration;

import static org.junit.Assert.*;

import java.io.InputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;

import org.junit.Before;
import org.junit.Test;
import org.w3c.dom.Document;
import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

public class V21MigrationTest {
	
	private class AssertErrorHandler implements ErrorHandler {
		private int errors = 0;
		
		@Override
		public void warning(SAXParseException exception)
				throws SAXException {
			System.out.println("WARNING: " + exception.getLineNumber() + ", " + exception.getMessage());
			errors++;
		}

		@Override
		public void error(SAXParseException exception) throws SAXException {
			System.out.println("ERROR: " + exception.getLineNumber() + ", " + exception.getMessage());
			errors++;
		}

		@Override
		public void fatalError(SAXParseException exception)
				throws SAXException {
			System.out.println("FATAL: " + exception.getLineNumber() + ", " + exception.getMessage());
			errors++;
		}
		
		public boolean hasErrors() {
			return errors > 0;
		}
		
	}
	
	private DocumentBuilder builder;
	private DocumentMigrationHandler handler;
	private Validator validator;
	private AssertErrorHandler errorHandler;
	
	@Before
	public void init() throws Exception {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		builder = factory.newDocumentBuilder();		
		SchemaFactory schemaFactory = SchemaFactory.newInstance("http://www.w3.org/2001/XMLSchema");
		Schema schema = schemaFactory.newSchema(getClass().getClassLoader().getResource("edu/kit/ipd/descartes/qpme/model/migration/schemas/v2_1/qpme.xsd"));
		validator = schema.newValidator();
		errorHandler = new AssertErrorHandler();
		validator.setErrorHandler(errorHandler);
		handler = new DocumentMigrationHandler();
	}

	@Test
	public void testIsMigreationRequired() throws Exception {
		InputStream in = getClass().getClassLoader().getResourceAsStream("edu/kit/ipd/descartes/qpme/model/migration/testfiles/v2_0/ispass03.qpe");
		Document doc = builder.parse(in);
		in.close();
		
		assertTrue(handler.isMigrationRequired(doc));
	}
	
	@Test
	public void testMigration_ispass03() throws Exception {
		InputStream in = getClass().getClassLoader().getResourceAsStream("edu/kit/ipd/descartes/qpme/model/migration/testfiles/v2_0/ispass03.qpe");
		Document doc = builder.parse(in);
		
		Document migratedDoc = handler.migrate(doc);
		
		validator.validate(new DOMSource(migratedDoc));
		
		assertFalse(errorHandler.hasErrors());
		
		in.close();
	}
	
	@Test
	public void testMigration_ispass03_probes() throws Exception {
		InputStream in = getClass().getClassLoader().getResourceAsStream("edu/kit/ipd/descartes/qpme/model/migration/testfiles/v2_0/ispass03_probes.qpe");
		Document doc = builder.parse(in);
		
		Document migratedDoc = handler.migrate(doc);
		
		validator.validate(new DOMSource(migratedDoc));
		
		assertFalse(errorHandler.hasErrors());
		
		in.close();
	}
	
	@Test
	public void testMigration_ispass03_hierarchical() throws Exception {
		InputStream in = getClass().getClassLoader().getResourceAsStream("edu/kit/ipd/descartes/qpme/model/migration/testfiles/v2_0/ispass03_hierarchical.qpe");
		Document doc = builder.parse(in);
		
		Document migratedDoc = handler.migrate(doc);
		
		validator.validate(new DOMSource(migratedDoc));
		
		assertFalse(errorHandler.hasErrors());
		
		in.close();
	}
	
	
	@Test
	public void testMigration_pepsy_bcmp2() throws Exception {
		InputStream in = getClass().getClassLoader().getResourceAsStream("edu/kit/ipd/descartes/qpme/model/migration/testfiles/v2_0/pepsy-bcmp2.qpe");
		Document doc = builder.parse(in);
		
		Document migratedDoc = handler.migrate(doc);
		
		validator.validate(new DOMSource(migratedDoc));
		
		assertFalse(errorHandler.hasErrors());
		
		in.close();
	}
	
	@Test
	public void testMigration_SjAS04Model_6AS_L5() throws Exception {
		InputStream in = getClass().getClassLoader().getResourceAsStream("edu/kit/ipd/descartes/qpme/model/migration/testfiles/v2_0/SjAS04Model_6AS-L5.qpe");
		Document doc = builder.parse(in);
		
		Document migratedDoc = handler.migrate(doc);
		
		validator.validate(new DOMSource(migratedDoc));
		
		assertFalse(errorHandler.hasErrors());
		
		in.close();
	}
	
	@Test
	public void testMigration_SPECjms2007Model() throws Exception {
		InputStream in = getClass().getClassLoader().getResourceAsStream("edu/kit/ipd/descartes/qpme/model/migration/testfiles/v2_0/SPECjms2007Model.qpe");
		Document doc = builder.parse(in);
		
		Document migratedDoc = handler.migrate(doc);
		
		validator.validate(new DOMSource(migratedDoc));
		
		assertFalse(errorHandler.hasErrors());
		
		in.close();
	}

}
