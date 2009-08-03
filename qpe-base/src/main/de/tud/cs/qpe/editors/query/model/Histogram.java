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
