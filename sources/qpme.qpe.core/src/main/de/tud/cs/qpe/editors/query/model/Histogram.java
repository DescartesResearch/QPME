/* ==============================================
 * QPME : Queueing Petri net Modeling Environment
 * ==============================================
 *
 * (c) Copyright 2003-2011, by Samuel Kounev and Contributors.
 * 
 * Project Info:   http://descartes.ipd.kit.edu/projects/qpme/
 *                 http://www.descartes-research.net/
 *    
 * All rights reserved. This software is made available under the terms of the 
 * Eclipse Public License (EPL) v1.0 as published by the Eclipse Foundation
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * This software is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY
 * or FITNESS FOR A PARTICULAR PURPOSE. See the Eclipse Public License (EPL)
 * for more details.
 *
 * You should have received a copy of the Eclipse Public License (EPL)
 * along with this software; if not visit http://www.eclipse.org or write to
 * Eclipse Foundation, Inc., 308 SW First Avenue, Suite 110, Portland, 97204 USA
 * Email: license (at) eclipse.org 
 *  
 * [Java is a trademark or registered trademark of Sun Microsystems, Inc.
 * in the United States and other countries.]
 *                                
 * =============================================
 *
 * Original Author(s):  Frederik Zipp and Samuel Kounev
 * Contributor(s):   
 * 
 * NOTE: The above list of contributors lists only the people that have
 * contributed to this source file - for a list of ALL contributors to 
 * the project, please see the README.txt file.
 * 
 *  History:
 *  Date        ID                Description
 *  ----------  ----------------  ------------------------------------------------------------------  
 *  2009/02/27  Frederik Zipp     Created.
 * 
 */
package de.tud.cs.qpe.editors.query.model;

import java.util.List;
import java.util.NoSuchElementException;

import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.XPath;

public class Histogram {

	private final Element histogramElement;

	public Histogram(Element histogramElement) {
		this.histogramElement = histogramElement;
	}
	
	public double getBucketSize() {
		return Double.parseDouble(this.histogramElement.attributeValue("bucket-size"));
	}

	public int getNumBuckets() {
		return Integer.parseInt(this.histogramElement.attributeValue("num-buckets"));
	}
	
	public double getMean() {
		return Double.parseDouble(this.histogramElement.elementText("mean"));
	}
	
	@SuppressWarnings("unchecked")
	public double getPercentile(double percent) {
		List<Element> percentiles = this.histogramElement.element("percentiles").elements("percentile");
		for (Element percentile : percentiles) {
			if (Double.parseDouble(percentile.attributeValue("for")) == percent) {
				return Double.parseDouble(percentile.getText());
			}
		}
		throw new NoSuchElementException();
	}

	public int getBucketCount(int i) {
		XPath xpathSelector = DocumentHelper.createXPath("//bucket[@index = '" + i + "']");
		Element bucketElement = (Element) xpathSelector.selectSingleNode(this.histogramElement);
		if (bucketElement == null) {
			throw new NoSuchElementException();
		}
		return Integer.parseInt(bucketElement.getText());
	}
	
	public HistogramType getType() {
		return HistogramType.fromType(this.histogramElement.attributeValue("type"));
	}
}
