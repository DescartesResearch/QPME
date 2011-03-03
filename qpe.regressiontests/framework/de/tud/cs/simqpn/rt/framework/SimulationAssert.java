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
 * Original Author(s):  Simon Spinner
 * Contributor(s):   
 * 
 * NOTE: The above list of contributors lists only the people that have
 * contributed to this source file - for a list of ALL contributors to 
 * the project, please see the README.txt file.
 * 
 *  History:
 *  Date        ID                Description
 *  ----------  ----------------  ----------------------------------------------------------------------------------
 *  2011/01/21  Simon Spinner     Created.         
 * 
 */

package de.tud.cs.simqpn.rt.framework;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.File;

import org.apache.commons.math.stat.inference.TestUtils;

import de.tud.cs.simqpn.rt.framework.results.Metric;
import de.tud.cs.simqpn.rt.framework.results.RunInfo;
import de.tud.cs.simqpn.rt.framework.results.SimulationResults;
import de.tud.cs.simqpn.rt.framework.results.Statistics;
import de.tud.cs.simqpn.rt.framework.results.Statistics.ElementType;
import de.tud.cs.simqpn.rt.framework.run.RunConfig.Revision;

/**
 * Static assert methods to check results of simulation tests.
 * 
 * @author Simon Spinner
 * 
 */
public class SimulationAssert {

	// Maximum relative delta when comparing double values.
	private static final double MAX_EQUILIBRIUM_DELTA = 0.01;

	/**
	 * Asserts that the statistics of a specified number of places (ordinary,
	 * queueing, depository) are available in the results.
	 * 
	 * @param expectedCount
	 * @param actual
	 *            - The results from the test simulation run.
	 */
	public static void assertPlaceCount(int expectedCount,
			SimulationResults actual) {
		assertEquals("Unexpected number of places.", expectedCount, actual
				.getPlaceStats().size());
	}

	/**
	 * Asserts that the statistics of a specified number of queues are available
	 * in the results.
	 * 
	 * @param expectedCount
	 * @param actual
	 *            - The results from the test simulation run.
	 */
	public static void assertQueueCount(int expectedCount,
			SimulationResults actual) {
		assertEquals("Unexpected number of queues.", expectedCount, actual
				.getQueueStats().size());
	}

	/**
	 * Asserts that the results of a specified number of probes are available.
	 * 
	 * @param expectedCount
	 * @param actual
	 *            - The results from the test simulation run.
	 */
	public static void assertProbeCount(int expectedCount,
			SimulationResults actual) {
		assertEquals("Unexpected number of probes.", expectedCount, actual
				.getProbeStats().size());
	}

	/**
	 * Asserts that all simulation runs have the expected run length.
	 * 
	 * @param expected
	 *            - Expected run length.
	 * @param actual
	 *            - The results from the test simulation run.
	 */
	public static void assertRunLength(double expected, SimulationResults actual) {
		int run = 0;

		assertTrue("No run infos found.", actual.getRunInfos().size() > 0);
		for (RunInfo info : actual.getRunInfos()) {
			assertEquals("Unexpected run length of " + run + ".", expected,
					info.getTotalRunLength(), expected * 0.001);
			run++;
		}
	}

	/**
	 * Asserts that all simulation runs have at maximum the expected run length.
	 * 
	 * @param expected
	 *            - Expected maximum run length.
	 * @param actual
	 *            - The results from the test simulation run.
	 */
	public static void assertRunLengthLessOrEqual(double expected,
			SimulationResults actual) {
		int run = 0;

		assertTrue("No run infos found.", actual.getRunInfos().size() > 0);
		for (RunInfo info : actual.getRunInfos()) {
			assertTrue("Unexpected run length of " + run + ".",
					expected >= info.getTotalRunLength());
			run++;
		}
	}

	/**
	 * Asserts that no overflow warning occured during the simulation run.
	 * 
	 * @param actual
	 *            - The results from the test simulation run.
	 */
	public static void assertNoOverflow(SimulationResults actual) {
		int run = 0;

		assertTrue("No run infos found.", actual.getRunInfos().size() > 0);
		for (RunInfo info : actual.getRunInfos()) {
			assertFalse("Unexpected overflow in run " + run + ".",
					info.getOverflowFlag());
			run++;
		}
	}

	/**
	 * Asserts that a overflow warning occured during each simulation run.
	 * 
	 * @param actual
	 *            - The results from the test simulation run.
	 */
	public static void assertOverflow(SimulationResults actual) {
		int run = 0;

		assertTrue("No run infos found.", actual.getRunInfos().size() > 0);
		for (RunInfo info : actual.getRunInfos()) {
			assertTrue("No overflow in run " + run + ".",
					info.getOverflowFlag());
			run++;
		}
	}

	/**
	 * Asserts that no warnings were issued during the simulation run.
	 * 
	 * @param actual
	 *            - The results from the test simulation run.
	 */
	public static void assertNoWarnings(SimulationResults actual) {
		int run = 0;

		assertTrue("No run infos found.", actual.getRunInfos().size() > 0);
		for (RunInfo info : actual.getRunInfos()) {
			assertEquals("Unexpected warnings in run " + run + ".", 0,
					info.getWarningCount());
			run++;
		}
	}

	/**
	 * Asserts that no errors were issued during the simulation run.
	 * 
	 * @param actual
	 *            - The results from the test simulation run.
	 */
	public static void assertNoErrors(SimulationResults actual) {
		int run = 0;

		assertTrue("No run infos found.", actual.getRunInfos().size() > 0);
		for (RunInfo info : actual.getRunInfos()) {
			assertEquals("Unexpected errors in run " + run + ".", 0,
					info.getErrorCount());
			run++;
		}
	}

	/**
	 * Asserts that the arrival throughput is approximately equal to the
	 * departure throughput for each place.
	 * 
	 * @param actual
	 *            - The results from the test simulation run.
	 */
	public static void assertFlowEquilibrium(SimulationResults actual) {
		for (Statistics placeStats : actual.getPlaceStats()) {
			for (Statistics colStats : placeStats.getChildStats()) {
				double[] arrivThrPut = colStats.getMetric(Metric.ARRIV_THR_PUT)
						.getSamples();
				double[] depThrPut = colStats.getMetric(Metric.DEPT_THR_PUT)
						.getSamples();
				for (int i = 0; i < arrivThrPut.length; i++) {
					if (arrivThrPut[i] == 0.0) {
						assertEquals(
								"Flow equilibrium not reached in run " + i,
								0.0, depThrPut[i], 0.0);
					} else {
						double delta = arrivThrPut[i] * MAX_EQUILIBRIUM_DELTA;
						assertEquals(
								"Flow equilibrium not reached in run " + i,
								arrivThrPut[i], depThrPut[i], delta);
					}
				}
			}
		}

		for (Statistics queueStats : actual.getQueueStats()) {
			double[] arrivThrPut = queueStats.getMetric(
					Metric.TOT_ARRIV_THR_PUT).getSamples();
			double[] depThrPut = queueStats.getMetric(Metric.TOT_DEPT_THR_PUT)
					.getSamples();
			for (int i = 0; i < arrivThrPut.length; i++) {
				if (arrivThrPut[i] == 0.0) {
					assertEquals("Flow equilibrium not reached in run ", 0.0,
							depThrPut);
				} else {
					double delta = arrivThrPut[i] * MAX_EQUILIBRIUM_DELTA;
					assertEquals("Flow equilibrium not reached in run " + i,
							arrivThrPut[i], depThrPut[i], delta);
				}
			}
		}
	}

	/**
	 * Asserts that the confidence intervals of the specified place have the
	 * expected relative precision.
	 * 
	 * @param expectedRelPrec
	 *            - Expected relative precision.
	 * @param actual
	 *            - The results from the test simulation run.
	 * @param placeType
	 *            - Type of the place of interest.
	 * @param placeName
	 *            - Name of the place of interest.
	 */
	public static void assertRelativePrecision(double expectedRelPrec,
			SimulationResults actual, ElementType placeType, String placeName) {
		String placeId = placeName + " (" + placeType + ")";

		Statistics placeStats = actual.getStatistics(placeName, placeType);
		assertNotNull("Place " + placeId + " not found", placeStats);

		for (Statistics colStats : placeStats.getChildStats()) {
			Metric stdStateMeanST = colStats
					.getMetric(Metric.STD_STATE_MEAN_ST);
			Metric ciHalfLenST = colStats.getMetric(Metric.CI_HALF_LEN_ST);

			if ((stdStateMeanST != null) && (ciHalfLenST != null)) {
				double[] meanValues = stdStateMeanST.getSamples();
				double[] ciValues = ciHalfLenST.getSamples();
				for (int i = 0; i < meanValues.length; i++) {
					double expectedCiHalfLenST = expectedRelPrec
							* meanValues[i];
					assertTrue(
							"Expected relative precision not reached at place "
									+ placeId + " and color "
									+ colStats.getName(),
							expectedCiHalfLenST >= ciValues[i]);
				}
			}
		}
	}

	/**
	 * Asserts that the confidence intervals of the specified place have the
	 * expected absolute precision.
	 * 
	 * @param expectedAbsPrec
	 *            - Expected absolute precision.
	 * @param actual
	 *            - The results from the test simulation run.
	 * @param placeType
	 *            - Type of the place of interest.
	 * @param placeName
	 *            - Name of the place of interest.
	 */
	public static void assertAbsolutePrecision(double expectedAbsPrec,
			SimulationResults actual, ElementType placeType, String placeName) {
		String placeId = placeName + " (" + placeType + ")";

		Statistics placeStats = actual.getStatistics(placeName, placeType);
		assertNotNull("Place " + placeId + " not found", placeStats);

		for (Statistics colStats : placeStats.getChildStats()) {
			Metric ciHalfLenST = colStats.getMetric(Metric.CI_HALF_LEN_ST);

			if (ciHalfLenST != null) {
				double[] ciValues = ciHalfLenST.getSamples();
				for (int i = 0; i < ciValues.length; i++) {
					assertTrue(
							"Expected absolute precision not reached at place "
									+ placeId + " and color "
									+ colStats.getName() + ": " + ciValues[i]
									+ " . " + i, expectedAbsPrec >= ciValues[i]);
				}
			}
		}
	}

	/**
	 * Asserts that the steady state mean of the sojourn time of a probe is the
	 * sum of the places in the influence region of the probe.
	 * 
	 * @param probe
	 *            - Statistics of the probe of interest.
	 * @param containedPlaces
	 *            - Array of Statistics of the contained places.
	 */
	public static void assertProbeResults(Statistics probe,
			Statistics[] containedPlaces) {

		for (Statistics color : probe.getChildStats()) {
			Metric stdStateMean = color.getMetric(Metric.STD_STATE_MEAN_ST);

			assertNotNull("Metric std state mean not found", stdStateMean);

			Statistics[] curColors = new Statistics[containedPlaces.length];
			for (int i = 0; i < containedPlaces.length; i++) {
				curColors[i] = containedPlaces[i].findChildStats(
						color.getName(), ElementType.COLOR);
				assertNotNull("Color not found.", curColors[i]);
			}

			for (int i = 0; i < stdStateMean.getSampleCount(); i++) {
				double sumStdStateMean = 0.0;
				for (int j = 0; j < curColors.length; j++) {
					sumStdStateMean += curColors[j].getMetric(
							Metric.STD_STATE_MEAN_ST).getSamples()[i];
				}
				assertEquals(
						"Sum of steady state means not matching for probe: "
								+ probe.getName(), sumStdStateMean,
						stdStateMean.getSamples()[i], sumStdStateMean * 0.01);
			}
		}
	}

	public static void assertResults(TestReport report,
			Revision comparedRevision, SimulationResults actual)
			throws Exception {
		try {
			boolean success = true;

			SimulationResults expected = new SimulationResults();
			expected.load(new File("testfiles/" + report.getTestName()
					+ "/reference/" + comparedRevision.toString().toLowerCase()
					+ "/reference-testdata.xml"));

			report.startReferenceSet(comparedRevision.toString());
			
			assertMetric(report, expected.getWallClockTime(), actual.getWallClockTime());

			for (Statistics expectedPlace : expected.getPlaceStats()) {
				Statistics actualPlace = actual.getStatistics(
						expectedPlace.getName(), expectedPlace.getType());
				report.startPlaceAssert(expectedPlace.getName(),
						expectedPlace.getType());
				if (actualPlace == null) {
					report.error("Missing place in actual results.");
					success = false;
				} else {
					boolean a = assertPlace(report, expectedPlace, actualPlace);
					success = success && a;
				}
				report.endPlaceAssert();
			}

			for (Statistics expectedQueue : expected.getQueueStats()) {
				Statistics actualQueue = actual.getStatistics(
						expectedQueue.getName(), ElementType.QUEUE);
				report.startQueueAssert(expectedQueue.getName());
				if (actualQueue == null) {
					report.error("Missing queue in actual results.");
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
			report.endReferenceSet();
		}
	}

	public static boolean assertPlace(TestReport report, Statistics expected,
			Statistics actual) {

		boolean result = true;

		if (TestConfig.getInstance().isSkipped(report.getTestName(),
				Statistics.getId(expected.getName(), expected.getType()), null)) {
			return result;
		}

		for (Metric expectedMetric : expected.getMetrics()) {
			Metric actualMetric = actual.getMetric(expectedMetric.getName());
			boolean a = assertMetric(report, expectedMetric, actualMetric);
			result = result && a;
		}

		if (expected.getChildStats().size() != actual.getChildStats().size()) {
			report.error("Different number of colors: expected <"
					+ expected.getChildStats().size() + ">, actual <"
					+ actual.getChildStats().size());
			return false;
		}

		for (int i = 0; i < expected.getChildStats().size(); i++) {
			Statistics expectedColor = expected.getChildStats().get(i);
			Statistics actualColor = actual.getChildStats().get(i);

			report.startColorAssert(expectedColor.getName());

			if (!TestConfig.getInstance().isSkipped(report.getTestName(),
					Statistics.getId(expected.getName(), expected.getType()),
					expectedColor.getName())) {
				if (!actualColor.getName().equals(expectedColor.getName())) {
					report.error("Different actual color name: "
							+ actualColor.getName());
					result = false;
				} else {
					boolean a = assertColor(report, expectedColor, actualColor);
					result = result && a;
				}
			}
			report.endColorAssert();
		}

		return result;
	}

	public static boolean assertQueue(TestReport report, Statistics expected,
			Statistics actual) {
		boolean result = true;

		if (TestConfig.getInstance().isSkipped(report.getTestName(),
				Statistics.getId(expected.getName(), expected.getType()), null)) {
			return result;
		}

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
		if (TestConfig.getInstance().isSkipped(expected)) {
			return true;
		}
		if (actual == null) {
			report.error(expected, "Metric " + expected.getName()
					+ " is missing.");
			return false;
		}

		try {
			boolean result = !TestUtils.tTest(expected.getSamples(), actual.getSamples(), TestConfig.getInstance().getTestSignificaneLevel(report.getTestName()));
			
			if (result == true) {
				report.success(expected, actual, !result);
				return true;
			} else {
				if (TestConfig.getInstance().isIgnored(expected)) {
					report.highlight(expected, actual, !result);
					return true;
				} else {
					report.failure(expected, actual, !result);
					return false;
				}
			}
		} catch (IllegalArgumentException e) {
			report.error(expected, e.getMessage());
			return false;
		} catch (Exception e) {
			report.error(expected, e.getMessage());
			return false;
		}
	}
}
