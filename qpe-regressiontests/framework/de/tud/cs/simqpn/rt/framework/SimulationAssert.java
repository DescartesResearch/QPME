package de.tud.cs.simqpn.rt.framework;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import org.apache.commons.math.MathException;
import org.apache.commons.math.stat.inference.TestUtils;

import de.tud.cs.simqpn.rt.framework.stats.Metric;
import de.tud.cs.simqpn.rt.framework.stats.PlaceStatistics;
import de.tud.cs.simqpn.rt.framework.stats.Statistics;

public class SimulationAssert {

	private static double maxSingleValueTolerance = 0.03;
	private static double confLevel = Double.parseDouble(System.getProperty("rt.conflevel", "0.95"));

	public static void assertPlaceCount(int expectedCount,
			SimulationResults actual) {
		assertEquals("Unexpected number of places.", expectedCount, actual
				.getPlaces().size());
	}

	public static void assertQueueCount(int expectedCount,
			SimulationResults actual) {
		assertEquals("Unexpected number of queues.", expectedCount, actual
				.getQueues().size());
	}
	
	public static void assertRelativePrecision(double expectedRelPrec, SimulationResults actual, String placeType, String placeName) {
		String placeId = placeName + " (" + placeType + ")";
		
		PlaceStatistics placeStats = actual.getPlaceStatistics(placeType, placeName);
		assertNotNull("Place " + placeId + " not found", placeStats);
		
		for (Statistics colStats : placeStats.getColorStats()) {
			Metric stdStateMeanST = colStats.getMetric(Metric.STD_STATE_MEAN_ST);
			Metric ciHalfLenST = colStats.getMetric(Metric.CI_HALF_LEN_ST);
			
			assertNotNull("Metric stdStateMeanST not found for place " + placeId, stdStateMeanST);
			assertNotNull("Metric ciHalfLenST not found for place " + placeId, ciHalfLenST);
			
			double[] meanValues = stdStateMeanST.getSamples();
			double[] ciValues = ciHalfLenST.getSamples();
			for (int i = 0; i < meanValues.length; i++) {
				double expectedCiHalfLenST = expectedRelPrec * meanValues[i];
				assertTrue("Expected relative precision not reached at place " + placeId + " and color " + colStats.getName(),
						expectedCiHalfLenST >= ciValues[i]);
			}
		}
	}
	
	public static void assertAbsolutePrecision(double expectedAbsPrec, SimulationResults actual, String placeType, String placeName) {
		String placeId = placeName + " (" + placeType + ")";
		
		PlaceStatistics placeStats = actual.getPlaceStatistics(placeType, placeName);
		assertNotNull("Place " + placeId + " not found", placeStats);
		
		for (Statistics colStats : placeStats.getColorStats()) {
			Metric stdStateMeanST = colStats.getMetric(Metric.STD_STATE_MEAN_ST);
			Metric ciHalfLenST = colStats.getMetric(Metric.CI_HALF_LEN_ST);
			
			assertNotNull("Metric stdStateMeanST not found for place " + placeId, stdStateMeanST);
			assertNotNull("Metric ciHalfLenST not found for place " + placeId, ciHalfLenST);
			
			double[] meanValues = stdStateMeanST.getSamples();
			double[] ciValues = ciHalfLenST.getSamples();
			for (int i = 0; i < meanValues.length; i++) {
				assertTrue("Expected relative precision not reached at place " + placeId + " and color " + colStats.getName(),
						expectedAbsPrec >= ciValues[i]);
			}
		}
	}
	
	public static void assertResults(TestReport report,
			SimulationResults expected, SimulationResults actual) {
		try {
			boolean success = true;
			
			report.startAssertSet();

			for (PlaceStatistics expectedPlace : expected.getPlaces()) {
				PlaceStatistics actualPlace = actual.getPlaceStatistics(
						expectedPlace.getType(), expectedPlace.getName());
				report.startPlaceAssert(expectedPlace.getName(), expectedPlace.getType());
				if (actualPlace == null) {
					report.failure("Missing place in actual results.");
					success = false;
				} else {
					boolean a = assertPlace(report, expectedPlace, actualPlace);
					success = success && a;
				}
				report.endPlaceAssert();
			}

			for (Statistics expectedQueue : expected.getQueues()) {
				Statistics actualQueue = actual.getQueueStatistics(expectedQueue
						.getName());
				report.startQueueAssert(expectedQueue.getName());
				if (actualQueue == null) {
					report.failure("Missing queue in actual results.");
					success = false;
				} else {
					boolean a = assertQueue(report, expectedQueue, actualQueue);
					success = success && a;
				}
				report.endQueueAssert();
			}
			
			if (!success) {
				fail("See report for details.");
			}
			
		} finally {
			report.endAssertSet();
		}
	}

	public static boolean assertPlace(TestReport report, PlaceStatistics expected,
			PlaceStatistics actual) {
		
		boolean result = true;

		for(Metric expectedMetric : expected.getMetrics()) {
			Metric actualMetric = actual.getMetric(expectedMetric.getName());
			boolean a = assertMetric(report, expectedMetric, actualMetric);
			result = result && a;
		}
		
		if (expected.getColorCount() != actual.getColorCount()) {
			report.failure("Different number of colors: expected <"
					+ expected.getColorCount() + ">, actual <" + actual.getColorCount());
			return false;
		}

		for (int i = 0; i < expected.getColorCount(); i++) {
			Statistics expectedColor = expected.getColorStats(i);
			Statistics actualColor = actual.getColorStats(i);
			
			report.startColorAssert(expectedColor.getName());
			if (!actualColor.getName().equals(expectedColor.getName())) {
				report.failure("Different actual color name: " + actualColor.getName());
				result = false;
			} else {
				boolean a = assertColor(report, expectedColor, actualColor);
				result = result && a;
			}
			report.endColorAssert();
		}
		
		return result;
	}

	public static boolean assertQueue(TestReport report, Statistics expected, Statistics actual) {
		boolean result = true;
		for (Metric expectedMetric : expected.getMetrics()) {
			Metric actualMetric = actual.getMetric(expectedMetric.getName());
			boolean a = assertMetric(report, expectedMetric, actualMetric);
			result = result && a;
		}
		return result;
	}

	public static boolean assertColor(TestReport report, Statistics expected,
			Statistics actual) {
		boolean result = true;
		for (Metric expectedMetric : expected.getMetrics()) {
			Metric actualMetric = actual.getMetric(expectedMetric.getName());
			boolean a = assertMetric(report, expectedMetric, actualMetric);
			result = result && a;
		}
		return result;
	}

	public static boolean assertMetric(TestReport report, Metric expected,
			Metric actual) {
		if (actual == null) {
			report.failure("Metric " + expected.getName() + " is missing.");
			return false;
		}		
		try {
			boolean result = false;
			
			if (expected.getName().startsWith("min") || expected.getName().startsWith("max")) {
				if ((actual.getMean() >= expected.getMinimum()) && (actual.getMean() <= expected.getMaximum())) {
					// The distribution of minimum and maximum parameters are ususually highly skewed
					// A t-Test would therefore need a lot of samples to provide reliable results. We just check
					// that the actual mean Maximum/Minimum lies somewhere between the expected minimum and maximum value
					// of the Minimum/Maximum.
					result = true;
				}
			} else {
				if ((expected.getSampleCount() >= 7) && actual.getSampleCount() >= 7) {
					result = !TestUtils.tTest(expected.getSamples(), actual.getSamples(), 1- confLevel);
				} else {
					if (actual.getSampleCount() == 0) {
						report.failure("No actual samples.");
						return false;
					}				
					if (expected.getSampleCount() > 0) {
						result = (maxSingleValueTolerance >= (Math.abs(expected.getMean()
								- actual.getMean()) / Math.abs(expected.getMean())));
					}
				}
			}
			if (result == true) {
				report.success(expected, actual);
			} else {
				report.failure(expected, actual);
			}
			return result;
		} catch (IllegalArgumentException e) {
			report.failure(e.getMessage());
			return false;
		} catch (MathException e) {
			report.failure(e.getMessage());
			return false;
		}
	}
}
