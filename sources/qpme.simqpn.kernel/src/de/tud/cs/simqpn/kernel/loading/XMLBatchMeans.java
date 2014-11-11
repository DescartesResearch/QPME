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
 * Original Author(s):  Jürgen Walter
 * Contributor(s):   
 * 
 * NOTE: The above list of contributors lists only the people that have
 * contributed to this source file - for a list of ALL contributors to 
 * the project, please see the README.txt file.
 * 
 *  History:
 *  Date        ID                Description
 *  ----------  ----------------  ------------------------------------------------------------------  
 *  2013/07/22  Jürgen Walter     Created. Code has been taken from other classes during refactoring
 * 
 */
package de.tud.cs.simqpn.kernel.loading;

import static de.tud.cs.simqpn.kernel.util.LogUtil.formatDetailMessage;

import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;
import org.dom4j.Element;
import org.dom4j.XPath;

import de.tud.cs.simqpn.kernel.SimQPNConfiguration;
import de.tud.cs.simqpn.kernel.SimQPNException;
import de.tud.cs.simqpn.kernel.entities.Net;
import de.tud.cs.simqpn.kernel.entities.Place;
import de.tud.cs.simqpn.kernel.entities.Probe;
import de.tud.cs.simqpn.kernel.entities.QPlace;

public class XMLBatchMeans {
	private static Logger log = Logger.getLogger(XMLBatchMeans.class);

	/**
	 * Modifies the PlaceStats of the passed net according to XML and
	 * configuration
	 * 
	 * @throws SimQPNException
	 */
	public static void modificateNetForBatchMeans(Element XMLDescription,
			SimQPNConfiguration configuration, Net net) throws SimQPNException {
		/*
		 * "Advanced Configuration Options" only applicable to the BATCH_MEANS
		 * method
		 * 
		 * These options are configurable for each place/queue/depository that
		 * has statsLevel >= 3. Options should be displayed only if BATCH_MEANS
		 * is chosen and in this case only for places that have statsLevel set
		 * to be >= 3.
		 * 
		 * double signLevST - Required significance level. double reqAbsPrc -
		 * Used if stoppingRule=ABSPRC: Required absolute precision (max c.i.
		 * half length). double reqRelPrc - Used if stoppingRule=RELPRC:
		 * Required relative precision (max ratio of c.i. half length to mean).
		 * int batchSizeST - Batch size for the batch means algorithm. int
		 * minBatches - Minimum number of batches required for steady state
		 * statistics. If minBatches[c] <= 0, no steady state statistics are
		 * collected for this color! int numBMeansCorlTested - If > 0 checks
		 * whether the batch size is sufficient - the first numBMeansCorlTested
		 * batch means from the beginning of steady state are tested for
		 * autocorrelation.
		 * 
		 * NOTE: reqAbsPrc should be available only if stoppingRule=ABSPRC;
		 * reqRelPrc should be available only if stoppingRule=RELPRC.
		 * 
		 * Check and make sure that numBMeansCorlTested is even!
		 */

		// CONFIG: BATCH_MEANS Method Initialization Parameters
		if (configuration.getAnalMethod() == SimQPNConfiguration.AnalysisMethod.BATCH_MEANS) {
			XPath xpathSelector = XMLHelper.createXPath("//place");
			List<Element> placeList = xpathSelector.selectNodes(XMLDescription);
			xpathSelector = XMLHelper.createXPath("//probe");
			List<Element> probeList = xpathSelector.selectNodes(XMLDescription);
			Iterator<Element> placeIterator;
			placeIterator = placeList.iterator();
			for (int p = 0; placeIterator.hasNext(); p++) {
				Element place = placeIterator.next();
				Place pl = net.getPlace(p);
				if (pl.statsLevel >= 3) {
					log.debug("places[" + p + "]");
					xpathSelector = XMLHelper
							.createXPath("color-refs/color-ref");
					List<Element> colorList = xpathSelector.selectNodes(place);
					Iterator<Element> colorRefIterator = colorList.iterator();
					for (int cr = 0; colorRefIterator.hasNext(); cr++) {
						Element colorRef = colorRefIterator.next();
						Element colorRefSettings = XMLHelper
								.getSettings(colorRef,
										net.getConfigurationName());
						// Initialize Place (or Depository if pl is QPlace)
						if (colorRefSettings != null) {
							if (colorRefSettings.attributeValue("signLev") == null) {
								log.error(formatDetailMessage(
										"Configuration parameter \"signLev\" for Batch Means Method is missing!",
										"configuration",
										net.getConfigurationName(),
										"place-num", Integer.toString(p),
										"place.id", place.attributeValue("id"),
										"place.name",
										place.attributeValue("name"),
										"colorRef-num", Integer.toString(cr),
										"colorRef.id",
										colorRef.attributeValue("id"),
										"colorRef.color-id",
										colorRef.attributeValue("color-id")));
								throw new SimQPNException();
							}
							pl.placeStats.signLevST[cr] = Double
									.parseDouble(colorRefSettings
											.attributeValue("signLev"));
							log.debug("-- placeStats.signLevST[" + cr + "] = "
									+ pl.placeStats.signLevST[cr]);

							if (colorRefSettings.attributeValue("reqAbsPrc") == null) {
								log.error(formatDetailMessage(
										"Configuration parameter \"reqAbsPrc\" for Batch Means Method is missing!",
										"configuration",
										net.getConfigurationName(),
										"place-num", Integer.toString(p),
										"place.id", place.attributeValue("id"),
										"place.name",
										place.attributeValue("name"),
										"colorRef-num", Integer.toString(cr),
										"colorRef.id",
										colorRef.attributeValue("id"),
										"colorRef.color-id",
										colorRef.attributeValue("color-id")));
								throw new SimQPNException();
							}
							pl.placeStats.reqAbsPrc[cr] = Double
									.parseDouble(colorRefSettings
											.attributeValue("reqAbsPrc"));
							log.debug("-- placeStats.reqAbsPrc[" + cr + "] = "
									+ pl.placeStats.reqAbsPrc[cr]);

							if (colorRefSettings.attributeValue("reqRelPrc") == null) {
								log.error(formatDetailMessage(
										"Configuration parameter \"reqRelPrc\" for Batch Means Method is missing!",
										"configuration",
										net.getConfigurationName(),
										"place-num", Integer.toString(p),
										"place.id", place.attributeValue("id"),
										"place.name",
										place.attributeValue("name"),
										"colorRef-num", Integer.toString(cr),
										"colorRef.id",
										colorRef.attributeValue("id"),
										"colorRef.color-id",
										colorRef.attributeValue("color-id")));
								throw new SimQPNException();
							}
							pl.placeStats.reqRelPrc[cr] = Double
									.parseDouble(colorRefSettings
											.attributeValue("reqRelPrc"));
							log.debug("-- placeStats.reqRelPrc[" + cr + "] = "
									+ pl.placeStats.reqRelPrc[cr]);

							if (colorRefSettings.attributeValue("batchSize") == null) {
								log.error(formatDetailMessage(
										"Configuration parameter \"batchSize\" for Batch Means Method is missing!",
										"configuration",
										net.getConfigurationName(),
										"place-num", Integer.toString(p),
										"place.id", place.attributeValue("id"),
										"place.name",
										place.attributeValue("name"),
										"colorRef-num", Integer.toString(cr),
										"colorRef.id",
										colorRef.attributeValue("id"),
										"colorRef.color-id",
										colorRef.attributeValue("color-id")));
								throw new SimQPNException();
							}
							pl.placeStats.batchSizeST[cr] = Integer
									.parseInt(colorRefSettings
											.attributeValue("batchSize"));
							log.debug("-- placeStats.batchSizeST[" + cr
									+ "] = " + pl.placeStats.batchSizeST[cr]);

							if (colorRefSettings.attributeValue("minBatches") == null) {
								log.error(formatDetailMessage(
										"Configuration parameter \"minBatches\" for Batch Means Method is missing!",
										"configuration",
										net.getConfigurationName(),
										"place-num", Integer.toString(p),
										"place.id", place.attributeValue("id"),
										"place.name",
										place.attributeValue("name"),
										"colorRef-num", Integer.toString(cr),
										"colorRef.id",
										colorRef.attributeValue("id"),
										"colorRef.color-id",
										colorRef.attributeValue("color-id")));
								throw new SimQPNException();
							}
							pl.placeStats.minBatches[cr] = Integer
									.parseInt(colorRefSettings
											.attributeValue("minBatches"));
							log.debug("-- placeStats.minBatches[" + cr + "] = "
									+ pl.placeStats.minBatches[cr]);

							if (colorRefSettings
									.attributeValue("numBMeansCorlTested") == null) {
								log.error(formatDetailMessage(
										"Configuration parameter \"numBMeansCorlTested\" for Batch Means Method is missing!",
										"configuration",
										net.getConfigurationName(),
										"place-num", Integer.toString(p),
										"place.id", place.attributeValue("id"),
										"place.name",
										place.attributeValue("name"),
										"colorRef-num", Integer.toString(cr),
										"colorRef.id",
										colorRef.attributeValue("id"),
										"colorRef.color-id",
										colorRef.attributeValue("color-id")));
								throw new SimQPNException();
							}
							pl.placeStats.numBMeansCorlTested[cr] = Integer
									.parseInt(colorRefSettings
											.attributeValue("numBMeansCorlTested"));
							log.debug("-- placeStats.numBMeansCorlTested[" + cr
									+ "] = "
									+ pl.placeStats.numBMeansCorlTested[cr]);

							// Note that if (minBatches > 0 &&
							// numBMeansCorlTested[c] > 0),
							// minBatches[c] must be > numBMeansCorlTested[c]!
							if (pl.placeStats.minBatches[cr] > 0
									&& pl.placeStats.numBMeansCorlTested[cr] > 0
									&& !(pl.placeStats.minBatches[cr] > pl.placeStats.numBMeansCorlTested[cr])) {
								log.error(formatDetailMessage(
										"Place.placeStats.minBatches[c] must be greater than Place.placeStats.numBMeansCorlTested[c]!",
										"configuration",
										net.getConfigurationName(),
										"place-num", Integer.toString(p),
										"place.id", place.attributeValue("id"),
										"place.name",
										place.attributeValue("name"),
										"colorRef-num", Integer.toString(cr),
										"colorRef.id",
										colorRef.attributeValue("id"),
										"colorRef.color-id",
										colorRef.attributeValue("color-id")));
								throw new SimQPNException();
							}
							// If (numBMeansCorlTested[c] <= 0) no correlation
							// test is done!
							// Note that numBMeansCorlTested must be even!
							if (pl.placeStats.numBMeansCorlTested[cr] % 2 != 0) {
								log.error(formatDetailMessage(
										"Place.placeStats.numBMeansCorlTested[c] must be even!",
										"configuration",
										net.getConfigurationName(),
										"place-num", Integer.toString(p),
										"place.id", place.attributeValue("id"),
										"place.name",
										place.attributeValue("name"),
										"colorRef-num", Integer.toString(cr),
										"colorRef.id",
										colorRef.attributeValue("id"),
										"colorRef.color-id",
										colorRef.attributeValue("color-id")));
								throw new SimQPNException();
							}

							if (pl.statsLevel >= 4) {
								if (colorRefSettings
										.attributeValue("bucketSize") == null) {
									log.error(formatDetailMessage(
											"Configuration parameter \"bucketSize\" for Batch Means Method is missing!",
											"configuration",
											net.getConfigurationName(),
											"place-num", Integer.toString(p),
											"place.id",
											place.attributeValue("id"),
											"place.name",
											place.attributeValue("name"),
											"colorRef-num",
											Integer.toString(cr),
											"colorRef.id",
											colorRef.attributeValue("id"),
											"colorRef.color-id",
											colorRef.attributeValue("color-id")));
									throw new SimQPNException();
								}
								pl.placeStats.histST[cr].setBucketSize(Double
										.parseDouble(colorRefSettings
												.attributeValue("bucketSize")));
								log.debug("-- placeStats.histST["
										+ cr
										+ "].bucketSize = "
										+ pl.placeStats.histST[cr]
												.getBucketSize());

								if (colorRefSettings
										.attributeValue("maxBuckets") == null) {
									log.error(formatDetailMessage(
											"Configuration parameter \"maxBuckets\" for Batch Means Method is missing!",
											"configuration",
											net.getConfigurationName(),
											"place-num", Integer.toString(p),
											"place.id",
											place.attributeValue("id"),
											"place.name",
											place.attributeValue("name"),
											"colorRef-num",
											Integer.toString(cr),
											"colorRef.id",
											colorRef.attributeValue("id"),
											"colorRef.color-id",
											colorRef.attributeValue("color-id")));
									throw new SimQPNException();
								}
								pl.placeStats.histST[cr]
										.setMaximumNumberOfBuckets(Integer.parseInt(colorRefSettings
												.attributeValue("maxBuckets")));
								log.debug("-- placeStats.histST["
										+ cr
										+ "].maxBuckets = "
										+ pl.placeStats.histST[cr]
												.getMaximumNumberOfBuckets());
							}
						} else {
							log.error(formatDetailMessage(
									"SimQPN configuration parameters for Batch Means Method are missing!",
									"configuration",
									net.getConfigurationName(), "place-num",
									Integer.toString(p), "place.id",
									place.attributeValue("id"), "place.name",
									place.attributeValue("name"),
									"colorRef-num", Integer.toString(cr),
									"colorRef.id",
									colorRef.attributeValue("id"),
									"colorRef.color-id",
									colorRef.attributeValue("color-id")));
							throw new SimQPNException();
							/*
							 * ORIGINAL MANUAL CONFIGURATION
							 * pl.placeStats.signLevST[cr] = 0.05; // e.g. 0.05
							 * ---> 95% c.i.; 0.1 ---> 90% logln(2,
							 * "-- placeStats.signLevST[" + cr + "] = " +
							 * pl.placeStats.signLevST[cr]);
							 * pl.placeStats.reqAbsPrc[cr] = 50; logln(2,
							 * "-- placeStats.reqAbsPrc[" + cr + "] = " +
							 * pl.placeStats.reqAbsPrc[cr]);
							 * pl.placeStats.reqRelPrc[cr] = 0.05; // e.g. 0.1
							 * ---> 10% relative precision logln(2,
							 * "-- placeStats.reqRelPrc[" + cr + "] = " +
							 * pl.placeStats.reqRelPrc[cr]);
							 * pl.placeStats.batchSizeST[cr] = 200; // Initial
							 * batch size logln(2, "-- placeStats.batchSizeST["
							 * + cr + "] = " + pl.placeStats.batchSizeST[cr]);
							 * pl.placeStats.minBatches[cr] = 60; // Note that
							 * if (minBatches > 0 && numBMeansCorlTested[c] >
							 * 0), // minBatches[c] must be >
							 * numBMeansCorlTested[c]! logln(2,
							 * "-- placeStats.minBatches[" + cr + "] = " +
							 * pl.placeStats.minBatches[cr]);
							 * pl.placeStats.numBMeansCorlTested[cr] = 50; //
							 * Note that numBMeansCorlTested must be even! // If
							 * (numBMeansCorlTested[c] <= 0) no correlation test
							 * is done! logln(2,
							 * "-- placeStats.numBMeansCorlTested[" + cr +
							 * "] = " + pl.placeStats.numBMeansCorlTested[cr]);
							 */
						}

						// Initialize Queue if pl is QPlace
						if (pl instanceof QPlace) {
							QPlace qpl = (QPlace) pl;
							if (colorRefSettings != null) {
								if (colorRefSettings
										.attributeValue("queueSignLev") == null) {
									log.error(formatDetailMessage(
											"Configuration parameter \"queueSignLev\" for Batch Means Method is missing!",
											"configuration",
											net.getConfigurationName(),
											"place-num", Integer.toString(p),
											"place.id",
											place.attributeValue("id"),
											"place.name",
											place.attributeValue("name"),
											"colorRef-num",
											Integer.toString(cr),
											"colorRef.id",
											colorRef.attributeValue("id"),
											"colorRef.color-id",
											colorRef.attributeValue("color-id")));
									throw new SimQPNException();
								}
								qpl.qPlaceQueueStats.signLevST[cr] = Double
										.parseDouble(colorRefSettings
												.attributeValue("queueSignLev"));
								log.debug("-- qPlaceQueueStats.signLevST[" + cr
										+ "] = "
										+ qpl.qPlaceQueueStats.signLevST[cr]);

								if (colorRefSettings
										.attributeValue("queueReqAbsPrc") == null) {
									log.error(formatDetailMessage(
											"Configuration parameter \"queueReqAbsPrc\" for Batch Means Method is missing!",
											"configuration",
											net.getConfigurationName(),
											"place-num", Integer.toString(p),
											"place.id",
											place.attributeValue("id"),
											"place.name",
											place.attributeValue("name"),
											"colorRef-num",
											Integer.toString(cr),
											"colorRef.id",
											colorRef.attributeValue("id"),
											"colorRef.color-id",
											colorRef.attributeValue("color-id")));
									throw new SimQPNException();
								}
								qpl.qPlaceQueueStats.reqAbsPrc[cr] = Double
										.parseDouble(colorRefSettings
												.attributeValue("queueReqAbsPrc"));
								log.debug("-- qPlaceQueueStats.reqAbsPrc[" + cr
										+ "] = "
										+ qpl.qPlaceQueueStats.reqAbsPrc[cr]);

								if (colorRefSettings
										.attributeValue("queueReqRelPrc") == null) {
									log.error(formatDetailMessage(
											"Configuration parameter \"queueReqRelPrc\" for Batch Means Method is missing!",
											"configuration",
											net.getConfigurationName(),
											"place-num", Integer.toString(p),
											"place.id",
											place.attributeValue("id"),
											"place.name",
											place.attributeValue("name"),
											"colorRef-num",
											Integer.toString(cr),
											"colorRef.id",
											colorRef.attributeValue("id"),
											"colorRef.color-id",
											colorRef.attributeValue("color-id")));
									throw new SimQPNException();
								}
								qpl.qPlaceQueueStats.reqRelPrc[cr] = Double
										.parseDouble(colorRefSettings
												.attributeValue("queueReqRelPrc"));
								log.debug("-- qPlaceQueueStats.reqRelPrc[" + cr
										+ "] = "
										+ qpl.qPlaceQueueStats.reqRelPrc[cr]);

								if (colorRefSettings
										.attributeValue("queueBatchSize") == null) {
									log.error(formatDetailMessage(
											"Configuration parameter \"queueBatchSize\" for Batch Means Method is missing!",
											"configuration",
											net.getConfigurationName(),
											"place-num", Integer.toString(p),
											"place.id",
											place.attributeValue("id"),
											"place.name",
											place.attributeValue("name"),
											"colorRef-num",
											Integer.toString(cr),
											"colorRef.id",
											colorRef.attributeValue("id"),
											"colorRef.color-id",
											colorRef.attributeValue("color-id")));
									throw new SimQPNException();
								}
								qpl.qPlaceQueueStats.batchSizeST[cr] = Integer
										.parseInt(colorRefSettings
												.attributeValue("queueBatchSize"));
								log.debug("-- qPlaceQueueStats.batchSizeST["
										+ cr + "] = "
										+ qpl.qPlaceQueueStats.batchSizeST[cr]);

								if (colorRefSettings
										.attributeValue("queueMinBatches") == null) {
									log.error(formatDetailMessage(
											"Configuration parameter \"queueMinBatches\" for Batch Means Method is missing!",
											"configuration",
											net.getConfigurationName(),
											"place-num", Integer.toString(p),
											"place.id",
											place.attributeValue("id"),
											"place.name",
											place.attributeValue("name"),
											"colorRef-num",
											Integer.toString(cr),
											"colorRef.id",
											colorRef.attributeValue("id"),
											"colorRef.color-id",
											colorRef.attributeValue("color-id")));
									throw new SimQPNException();
								}
								qpl.qPlaceQueueStats.minBatches[cr] = Integer
										.parseInt(colorRefSettings
												.attributeValue("queueMinBatches"));
								log.debug("-- qPlaceQueueStats.minBatches["
										+ cr + "] = "
										+ qpl.qPlaceQueueStats.minBatches[cr]);

								if (colorRefSettings
										.attributeValue("queueNumBMeansCorlTested") == null) {
									log.error(formatDetailMessage(
											"Configuration parameter \"queueNumBMeansCorlTested\" for Batch Means Method is missing!",
											"configuration",
											net.getConfigurationName(),
											"place-num", Integer.toString(p),
											"place.id",
											place.attributeValue("id"),
											"place.name",
											place.attributeValue("name"),
											"colorRef-num",
											Integer.toString(cr),
											"colorRef.id",
											colorRef.attributeValue("id"),
											"colorRef.color-id",
											colorRef.attributeValue("color-id")));
									throw new SimQPNException();
								}
								qpl.qPlaceQueueStats.numBMeansCorlTested[cr] = Integer
										.parseInt(colorRefSettings
												.attributeValue("queueNumBMeansCorlTested"));
								log.debug("-- qPlaceQueueStats.numBMeansCorlTested["
										+ cr
										+ "] = "
										+ qpl.qPlaceQueueStats.numBMeansCorlTested[cr]);

								if (qpl.qPlaceQueueStats.minBatches[cr] > 0
										&& qpl.qPlaceQueueStats.numBMeansCorlTested[cr] > 0
										&& !(qpl.qPlaceQueueStats.minBatches[cr] > qpl.qPlaceQueueStats.numBMeansCorlTested[cr])) {
									log.error(formatDetailMessage(
											"QPlace.qPlaceQueueStats.minBatches[c] must be greater than QPlace.qPlaceQueueStats.numBMeansCorlTested[c]!",
											"configuration",
											net.getConfigurationName(),
											"place-num", Integer.toString(p),
											"place.id",
											place.attributeValue("id"),
											"place.name",
											place.attributeValue("name"),
											"colorRef-num",
											Integer.toString(cr),
											"colorRef.id",
											colorRef.attributeValue("id"),
											"colorRef.color-id",
											colorRef.attributeValue("color-id")));
									throw new SimQPNException();
								}
								// If (numBMeansCorlTested[c] <= 0) no
								// correlation test is done!
								// Note that numBMeansCorlTested must be even!
								if (qpl.qPlaceQueueStats.numBMeansCorlTested[cr] % 2 != 0) {
									log.error(formatDetailMessage(
											"QPlace.qPlaceQueueStats.numBMeansCorlTested[c] must be even!",
											"configuration",
											net.getConfigurationName(),
											"place-num", Integer.toString(p),
											"place.id",
											place.attributeValue("id"),
											"place.name",
											place.attributeValue("name"),
											"colorRef-num",
											Integer.toString(cr),
											"colorRef.id",
											colorRef.attributeValue("id"),
											"colorRef.color-id",
											colorRef.attributeValue("color-id")));
									throw new SimQPNException();
								}

								if (pl.statsLevel >= 4) {
									if (colorRefSettings
											.attributeValue("queueBucketSize") == null) {
										log.error(formatDetailMessage(
												"Configuration parameter \"queueBucketSize\" for Batch Means Method is missing!",
												"configuration",
												net.getConfigurationName(),
												"place-num",
												Integer.toString(p),
												"place.id",
												place.attributeValue("id"),
												"place.name",
												place.attributeValue("name"),
												"colorRef-num",
												Integer.toString(cr),
												"colorRef.id",
												colorRef.attributeValue("id"),
												"colorRef.color-id",
												colorRef.attributeValue("color-id")));
										throw new SimQPNException();
									}
									qpl.qPlaceQueueStats.histST[cr]
											.setBucketSize(Double
													.parseDouble(colorRefSettings
															.attributeValue("queueBucketSize")));
									log.debug("-- qPlaceQueueStats.histST["
											+ cr
											+ "].bucketSize = "
											+ qpl.qPlaceQueueStats.histST[cr]
													.getBucketSize());

									if (colorRefSettings
											.attributeValue("queueMaxBuckets") == null) {
										log.error(formatDetailMessage(
												"Configuration parameter \"queueMaxBuckets\" for Batch Means Method is missing!",
												"configuration",
												net.getConfigurationName(),
												"place-num",
												Integer.toString(p),
												"place.id",
												place.attributeValue("id"),
												"place.name",
												place.attributeValue("name"),
												"colorRef-num",
												Integer.toString(cr),
												"colorRef.id",
												colorRef.attributeValue("id"),
												"colorRef.color-id",
												colorRef.attributeValue("color-id")));
										throw new SimQPNException();
									}
									qpl.qPlaceQueueStats.histST[cr]
											.setMaximumNumberOfBuckets(Integer.parseInt(colorRefSettings
													.attributeValue("queueMaxBuckets")));
									log.debug("-- qPlaceQueueStats.histST["
											+ cr
											+ "].maxBuckets = "
											+ qpl.qPlaceQueueStats.histST[cr]
													.getMaximumNumberOfBuckets());
								}
							} else {
								log.error(formatDetailMessage(
										"SimQPN configuration parameters for Batch Means Method are missing!",
										"configuration",
										net.getConfigurationName(),
										"place-num", Integer.toString(p),
										"place.id", place.attributeValue("id"),
										"place.name",
										place.attributeValue("name"),
										"colorRef-num", Integer.toString(cr),
										"colorRef.id",
										colorRef.attributeValue("id"),
										"colorRef.color-id",
										colorRef.attributeValue("color-id")));
								throw new SimQPNException();
								/*
								 * ORIGINAL MANUAL CONFIGURATION
								 * qpl.qPlaceQueueStats.signLevST[cr] = 0.05; //
								 * e.g. 0.05 ---> 95% c.i.; 0.1 ---> 90%
								 * logln(2, "-- qPlaceQueueStats.signLevST[" +
								 * cr + "] = " +
								 * qpl.qPlaceQueueStats.signLevST[cr]);
								 * qpl.qPlaceQueueStats.reqAbsPrc[cr] = 50;
								 * logln(2, "-- qPlaceQueueStats.reqAbsPrc[" +
								 * cr + "] = " +
								 * qpl.qPlaceQueueStats.reqAbsPrc[cr]);
								 * qpl.qPlaceQueueStats.reqRelPrc[cr] = 0.05; //
								 * e.g. 0.1 ---> 10% relative precision logln(2,
								 * "-- qPlaceQueueStats.reqRelPrc[" + cr +
								 * "] = " + qpl.qPlaceQueueStats.reqRelPrc[cr]);
								 * qpl.qPlaceQueueStats.batchSizeST[cr] = 200;
								 * // Initial batch size logln(2,
								 * "-- qPlaceQueueStats.batchSizeST[" + cr +
								 * "] = " +
								 * qpl.qPlaceQueueStats.batchSizeST[cr]);
								 * qpl.qPlaceQueueStats.minBatches[cr] = 60; //
								 * Note that if (minBatches > 0 &&
								 * numBMeansCorlTested[c] > 0), // minBatches[c]
								 * must be > numBMeansCorlTested[c]! logln(2,
								 * "-- qPlaceQueueStats.minBatches[" + cr +
								 * "] = " +
								 * qpl.qPlaceQueueStats.minBatches[cr]);
								 * qpl.qPlaceQueueStats.numBMeansCorlTested[cr]
								 * = 50; // Note that numBMeansCorlTested must
								 * be even! // If (numBMeansCorlTested[c] <= 0)
								 * no correlation test is done! logln(2,
								 * "-- qPlaceQueueStats.numBMeansCorlTested[" +
								 * cr + "] = " +
								 * qpl.qPlaceQueueStats.numBMeansCorlTested
								 * [cr]);
								 */
							}
						}
					}
				}
			}

			// Configure the probes statistic collection
			Iterator<Element> probeIterator = probeList.iterator();
			for (int p = 0; probeIterator.hasNext(); p++) {
				Element probe = probeIterator.next();
				Probe pr = net.getProbe(p);
				if (pr.statsLevel >= 3) {
					log.debug("probes[" + p + "]");
					xpathSelector = XMLHelper
							.createXPath("color-refs/color-ref");
					Iterator<Element> colorRefIterator = xpathSelector
							.selectNodes(probe).iterator();
					for (int cr = 0; colorRefIterator.hasNext(); cr++) {
						Element colorRef = colorRefIterator.next();
						Element colorRefSettings = XMLHelper
								.getSettings(colorRef,
										net.getConfigurationName());
						// Initialize Probe
						if (colorRefSettings != null) {
							if (colorRefSettings.attributeValue("signLev") == null) {
								log.error(formatDetailMessage(
										"Configuration parameter \"signLev\" for Batch Means Method is missing!",
										"configuration"
												+ net.getConfigurationName(),
										"probe-num" + Integer.toString(p),
										"probe.id" + probe.attributeValue("id"),
										"probe.name"
												+ probe.attributeValue("name"),
										"colorRef-num" + Integer.toString(cr),
										"colorRef.id"
												+ colorRef.attributeValue("id"),
										"colorRef.color-id"
												+ colorRef
														.attributeValue("color-id")));
								throw new SimQPNException();
							}
							pr.probeStats.signLevST[cr] = Double
									.parseDouble(colorRefSettings
											.attributeValue("signLev"));
							log.debug("-- probeStats.signLevST[" + cr + "] = "
									+ pr.probeStats.signLevST[cr]);

							if (colorRefSettings.attributeValue("reqAbsPrc") == null) {
								log.error(formatDetailMessage(
										"Configuration parameter \"reqAbsPrc\" for Batch Means Method is missing!",
										"configuration"
												+ net.getConfigurationName(),
										"probe-num" + Integer.toString(p),
										"probe.id" + probe.attributeValue("id"),
										"probe.name"
												+ probe.attributeValue("name"),
										"colorRef-num" + Integer.toString(cr),
										"colorRef.id"
												+ colorRef.attributeValue("id"),
										"colorRef.color-id"
												+ colorRef
														.attributeValue("color-id")));
								throw new SimQPNException();
							}
							pr.probeStats.reqAbsPrc[cr] = Double
									.parseDouble(colorRefSettings
											.attributeValue("reqAbsPrc"));
							log.debug("-- probeStats.reqAbsPrc[" + cr + "] = "
									+ pr.probeStats.reqAbsPrc[cr]);

							if (colorRefSettings.attributeValue("reqRelPrc") == null) {
								log.error(formatDetailMessage(
										"Configuration parameter \"reqRelPrc\" for Batch Means Method is missing!",
										"configuration"
												+ net.getConfigurationName(),
										"probe-num" + Integer.toString(p),
										"probe.id" + probe.attributeValue("id"),
										"probe.name"
												+ probe.attributeValue("name"),
										"colorRef-num" + Integer.toString(cr),
										"colorRef.id"
												+ colorRef.attributeValue("id"),
										"colorRef.color-id"
												+ colorRef
														.attributeValue("color-id")));
								throw new SimQPNException();
							}
							pr.probeStats.reqRelPrc[cr] = Double
									.parseDouble(colorRefSettings
											.attributeValue("reqRelPrc"));
							log.debug("-- probeStats.reqRelPrc[" + cr + "] = "
									+ pr.probeStats.reqRelPrc[cr]);

							if (colorRefSettings.attributeValue("batchSize") == null) {
								log.error(formatDetailMessage(
										"Configuration parameter \"batchSize\" for Batch Means Method is missing!",
										"configuration"
												+ net.getConfigurationName(),
										"probe-num" + Integer.toString(p),
										"probe.id" + probe.attributeValue("id"),
										"probe.name"
												+ probe.attributeValue("name"),
										"colorRef-num" + Integer.toString(cr),
										"colorRef.id"
												+ colorRef.attributeValue("id"),
										"colorRef.color-id"
												+ colorRef
														.attributeValue("color-id")));
								throw new SimQPNException();
							}
							pr.probeStats.batchSizeST[cr] = Integer
									.parseInt(colorRefSettings
											.attributeValue("batchSize"));
							log.debug("-- probeStats.batchSizeST[" + cr
									+ "] = " + pr.probeStats.batchSizeST[cr]);

							if (colorRefSettings.attributeValue("minBatches") == null) {
								log.error(formatDetailMessage(
										"Configuration parameter \"minBatches\" for Batch Means Method is missing!",
										"configuration"
												+ net.getConfigurationName(),
										"probe-num" + Integer.toString(p),
										"probe.id" + probe.attributeValue("id"),
										"probe.name"
												+ probe.attributeValue("name"),
										"colorRef-num" + Integer.toString(cr),
										"colorRef.id"
												+ colorRef.attributeValue("id"),
										"colorRef.color-id"
												+ colorRef
														.attributeValue("color-id")));
								throw new SimQPNException();
							}
							pr.probeStats.minBatches[cr] = Integer
									.parseInt(colorRefSettings
											.attributeValue("minBatches"));
							log.debug("-- probeStats.minBatches[" + cr + "] = "
									+ pr.probeStats.minBatches[cr]);

							if (colorRefSettings
									.attributeValue("numBMeansCorlTested") == null) {
								log.error(formatDetailMessage(
										"Configuration parameter \"numBMeansCorlTested\" for Batch Means Method is missing!",
										"configuration"
												+ net.getConfigurationName(),
										"probe-num" + Integer.toString(p),
										"probe.id" + probe.attributeValue("id"),
										"probe.name"
												+ probe.attributeValue("name"),
										"colorRef-num" + Integer.toString(cr),
										"colorRef.id"
												+ colorRef.attributeValue("id"),
										"colorRef.color-id"
												+ colorRef
														.attributeValue("color-id")));
								throw new SimQPNException();
							}
							pr.probeStats.numBMeansCorlTested[cr] = Integer
									.parseInt(colorRefSettings
											.attributeValue("numBMeansCorlTested"));
							log.debug("-- probeStats.numBMeansCorlTested[" + cr
									+ "] = "
									+ pr.probeStats.numBMeansCorlTested[cr]);

							// Note that if (minBatches > 0 &&
							// numBMeansCorlTested[c] > 0),
							// minBatches[c] must be > numBMeansCorlTested[c]!
							if (pr.probeStats.minBatches[cr] > 0
									&& pr.probeStats.numBMeansCorlTested[cr] > 0
									&& !(pr.probeStats.minBatches[cr] > pr.probeStats.numBMeansCorlTested[cr])) {
								log.error(formatDetailMessage(
										"Probe.probeStats.minBatches[c] must be greater than Probe.probeStats.numBMeansCorlTested[c]!",
										"configuration"
												+ net.getConfigurationName(),
										"probe-num" + Integer.toString(p),
										"probe.id" + probe.attributeValue("id"),
										"probe.name"
												+ probe.attributeValue("name"),
										"colorRef-num" + Integer.toString(cr),
										"colorRef.id"
												+ colorRef.attributeValue("id"),
										"colorRef.color-id"
												+ colorRef
														.attributeValue("color-id")));
								throw new SimQPNException();
							}
							// If (numBMeansCorlTested[c] <= 0) no correlation
							// test is done!
							// Note that numBMeansCorlTested must be even!
							if (pr.probeStats.numBMeansCorlTested[cr] % 2 != 0) {
								log.error(formatDetailMessage(
										"Probe.probeStats.numBMeansCorlTested[c] must be even!",
										"configuration"
												+ net.getConfigurationName(),
										"probe-num" + Integer.toString(p),
										"probe.id" + probe.attributeValue("id"),
										"probe.name"
												+ probe.attributeValue("name"),
										"colorRef-num" + Integer.toString(cr),
										"colorRef.id"
												+ colorRef.attributeValue("id"),
										"colorRef.color-id"
												+ colorRef
														.attributeValue("color-id")));
								throw new SimQPNException();
							}

							if (pr.statsLevel >= 4) {
								if (colorRefSettings
										.attributeValue("bucketSize") == null) {
									log.error(formatDetailMessage(
											"Configuration parameter \"bucketSize\" for Batch Means Method is missing!",
											"configuration"
													+ net.getConfigurationName(),
											"probe-num" + Integer.toString(p),
											"probe.id"
													+ probe.attributeValue("id"),
											"probe.name"
													+ probe.attributeValue("name"),
											"colorRef-num"
													+ Integer.toString(cr),
											"colorRef.id"
													+ colorRef
															.attributeValue("id"),
											"colorRef.color-id"
													+ colorRef
															.attributeValue("color-id")));
									throw new SimQPNException();
								}
								pr.probeStats.histST[cr].setBucketSize(Double
										.parseDouble(colorRefSettings
												.attributeValue("bucketSize")));
								log.debug("-- probeStats.histST["
										+ cr
										+ "].bucketSize = "
										+ pr.probeStats.histST[cr]
												.getBucketSize());

								if (colorRefSettings
										.attributeValue("maxBuckets") == null) {
									log.error(formatDetailMessage(
											"Configuration parameter \"maxBuckets\" for Batch Means Method is missing!",
											"configuration"
													+ net.getConfigurationName(),
											"probe-num" + Integer.toString(p),
											"probe.id"
													+ probe.attributeValue("id"),
											"probe.name"
													+ probe.attributeValue("name"),
											"colorRef-num"
													+ Integer.toString(cr),
											"colorRef.id"
													+ colorRef
															.attributeValue("id"),
											"colorRef.color-id"
													+ colorRef
															.attributeValue("color-id")));
									throw new SimQPNException();
								}
								pr.probeStats.histST[cr]
										.setMaximumNumberOfBuckets(Integer.parseInt(colorRefSettings
												.attributeValue("maxBuckets")));
								log.debug("-- probeStats.histST["
										+ cr
										+ "].maxBuckets = "
										+ pr.probeStats.histST[cr]
												.getMaximumNumberOfBuckets());
							}
						} else {
							log.error(formatDetailMessage(
									"SimQPN configuration parameters for Batch Means Method are missing!",
									"configuration"
											+ net.getConfigurationName(),
									"probe-num" + Integer.toString(p),
									"probe.id" + probe.attributeValue("id"),
									"probe.name" + probe.attributeValue("name"),
									"colorRef-num" + Integer.toString(cr),
									"colorRef.id"
											+ colorRef.attributeValue("id"),
									"colorRef.color-id"
											+ colorRef
													.attributeValue("color-id")));
							throw new SimQPNException();
						}
					}
				}
			}
		}
	}

}
