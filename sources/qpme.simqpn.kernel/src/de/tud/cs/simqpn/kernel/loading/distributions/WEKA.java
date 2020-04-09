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
 * Original Author(s):  Fabian Brosig
 * Contributor(s):      
 * 
 * NOTE: The above list of contributors lists only the people that have
 * contributed to this source file - for a list of ALL contributors to 
 * the project, please see the README.txt file.
 * 
 *  History:
 *  Date        ID                Description
 *  ----------  ----------------  ------------------------------------------------------------------  
 *  2013	    Fabian Brosig     Created.
 */
package de.tud.cs.simqpn.kernel.loading.distributions;

import java.util.ArrayList;

import de.tud.cs.simqpn.kernel.entities.QPlace;
import weka.classifiers.Classifier;
import weka.core.Attribute;
import weka.core.DenseInstance;
import weka.core.Instance;
import weka.core.Instances;

public class WEKA implements AbstractDistribution {
	
	Classifier wekaModel;
	Instances dataStructure = null;

	public WEKA(Classifier model) {
		if (model == null)
			throw new NullPointerException();
		wekaModel = model;
	}

	@Override
	public double nextDouble(QPlace qplace, int color) {
		int[] tokenNumbers = qplace.getQueueTokenPop(); // gets an array with the concurrencies for each WC

		if (dataStructure == null)
			initializeDataStructure(tokenNumbers.length);

		Instance dataPoint = new DenseInstance(dataStructure.numAttributes());
		dataPoint.setDataset(dataStructure);

		for (int i = 0; i < tokenNumbers.length; i++) {
			dataPoint.setValue(i + 1, tokenNumbers[i]);
		}

		try {
			return wekaModel.classifyInstance(dataPoint);
		} catch (Exception e) {
			e.printStackTrace();
			return 0.0;
		}
	}

	/**
	 * initializes the data structure with slots for response time and the
	 * concurrencies for each workload class
	 * 
	 * @param wc_count The amount of workload classes available at the queueing
	 *                 place to be predicted by the model
	 */
	private void initializeDataStructure(int wc_count) {
		ArrayList<Attribute> attributes = new ArrayList<>();
		final Attribute attr_RT = new Attribute("Response Time"); // first attribute is ALWAYS response time
		attributes.add(attr_RT);
		for (int i = 0; i < wc_count; i++) {
			final Attribute attr_CC = new Attribute("Concurrency " + (i + 1)); // Concurrencies must be ordered the same
																				// in QPME and the weka model training
																				// data
			attributes.add(attr_CC);
		}
		dataStructure = new Instances("Data Structure", attributes, 0);
		dataStructure.setClass(attr_RT);
	}

}