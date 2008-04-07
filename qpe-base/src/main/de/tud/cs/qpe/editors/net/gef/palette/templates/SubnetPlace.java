package de.tud.cs.qpe.editors.net.gef.palette.templates;

import org.dom4j.Element;
import org.eclipse.draw2d.geometry.Point;

import de.tud.cs.qpe.model.DocumentManager;
import de.tud.cs.qpe.utils.IdGenerator;

public class SubnetPlace extends Place {
	private static final long serialVersionUID = 8679419958508543570L;
	
	public SubnetPlace() {
		super();
		addAttribute("type", "subnet-place");
		Element fixed = addElement("fixed");
		fixed.addElement("input-place");
		fixed.addElement("input-transition");
		fixed.addElement("actual-population");
		fixed.addElement("output-place");
		
		Element subnet = addElement("subnet");
		subnet.addElement("colors");
		Element places = subnet.addElement("places");
		// Add the input-place.
		Element inputPlace = places.addElement("place");
		inputPlace.addAttribute("type", "ordinary-place");
		inputPlace.addAttribute("name", "input-place");
		inputPlace.addAttribute("departure-discipline", "NORMAL");
		inputPlace.addAttribute("id", IdGenerator.get());
		inputPlace.addAttribute("locked", "true");
		DocumentManager.setLocation(inputPlace, new Point(20,120));

		// Add the actual-population-place.
		Element actualPopulation = places.addElement("place");
		actualPopulation.addAttribute("type", "ordinary-place");
		actualPopulation.addAttribute("name", "actual population");
		actualPopulation.addAttribute("departure-discipline", "NORMAL");
		actualPopulation.addAttribute("id", IdGenerator.get());
		actualPopulation.addAttribute("locked", "true");
		DocumentManager.setLocation(actualPopulation, new Point(220,20));

		// Add the output-place.
		Element outputPlace = places.addElement("place");
		outputPlace.addAttribute("type", "ordinary-place");
		outputPlace.addAttribute("name", "output-place");
		outputPlace.addAttribute("departure-discipline", "NORMAL");
		outputPlace.addAttribute("id", IdGenerator.get());
		outputPlace.addAttribute("locked", "true");
		DocumentManager.setLocation(outputPlace, new Point(420,120));

		Element transitions = subnet.addElement("transitions");
		// Add the input-transition.
		Element inputTransition = transitions.addElement("transition");
		inputTransition.addAttribute("type", "immediate-transition");
		inputTransition.addAttribute("name", "input-transition");
		inputTransition.addAttribute("weight", "1.0");
		inputTransition.addAttribute("priority", "0");
		inputTransition.addAttribute("id", IdGenerator.get());
		inputTransition.addAttribute("locked", "true");
		DocumentManager.setLocation(inputTransition, new Point(120,120));

		// Add the output-transition.
		Element outputTransition = transitions.addElement("transition");
		outputTransition.addAttribute("type", "immediate-transition");
		outputTransition.addAttribute("name", "output-transition");
		outputTransition.addAttribute("weight", "1.0");
		outputTransition.addAttribute("priority", "0");
		outputTransition.addAttribute("id", IdGenerator.get());
		outputTransition.addAttribute("locked", "true");
		DocumentManager.setLocation(outputTransition, new Point(320,120));

		Element connections = subnet.addElement("connections");
		Element connection = connections.addElement("connection");
		connection.addAttribute("source-id", inputPlace.attributeValue("id"));
		connection.addAttribute("target-id", inputTransition.attributeValue("id"));
		connection.addAttribute("id", IdGenerator.get());
		connection.addAttribute("locked", "true");
		connection = connections.addElement("connection");
		connection.addAttribute("source-id", inputTransition.attributeValue("id"));
		connection.addAttribute("target-id", actualPopulation.attributeValue("id"));
		connection.addAttribute("id", IdGenerator.get());
		connection.addAttribute("locked", "true");
		connection = connections.addElement("connection");
		connection.addAttribute("source-id", actualPopulation.attributeValue("id"));
		connection.addAttribute("target-id", outputTransition.attributeValue("id"));
		connection.addAttribute("id", IdGenerator.get());
		connection.addAttribute("locked", "true");
		connection = connections.addElement("connection");
		connection.addAttribute("source-id", outputTransition.attributeValue("id"));
		connection.addAttribute("target-id", outputPlace.attributeValue("id"));
		connection.addAttribute("id", IdGenerator.get());
		connection.addAttribute("locked", "true");
	}
}
