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
 *  2013/05/08  Jürgen Walter     Created. Extracted from Simulator good class.
 * 
 */
package de.tud.cs.simqpn.kernel;

import java.util.Date;

import org.apache.log4j.Logger;

import cern.jet.random.Uniform;
import cern.jet.random.engine.DRand;
import cern.jet.random.engine.MersenneTwister;
import cern.jet.random.engine.RandomEngine;
import cern.jet.random.engine.RandomSeedGenerator;
import cern.jet.random.engine.RandomSeedTable;

/**
 * Creates random numbers. 
 * 
 * Adapter for cern.jet.random.engine.
 */
// TODO Make nextRandNumGen() non static, to have possibly more randomNumberGenerators
public class RandomNumberGenerator {
	private enum RandomGeneratorCategory {
		DRand, 			// cern.jet.random.engine.DRand 
		MersenneTwister	// cern.jet.random.engine.MersenneTwister
	}

	private static RandomGeneratorCategory randGenClass;// Defines the type of uniform random number generators used during the simulation.
	private static Uniform randNumGen;					// Random number generator used for seed generation.
	private static boolean useRandSeedTable;			// Specifies whether RandomSeedGenerator (and thus RandomSeedTable) is used.
	private static RandomSeedGenerator randSeedGen;		// Used if useRandSeedTable == true.
	
	private static Logger log = Logger.getLogger(RandomNumberGenerator.class);

	/**
	 * Chooses a seed randomly 
	 */
	public static void initialize() {
		java.util.Date date;
//		date = new java.util.Date(0);
//		System.out.println(date);
//		date = new java.util.Date((long)1350000003000.0);
//		System.out.println(date);
//		date = new java.util.Date((long)1370000000000.0);
//		System.out.println(date);
//		date = new java.util.Date((long)1300000000000.0);
//		System.out.println(date);
//		date = new java.util.Date((long)1500000000000.0);
//		System.out.println(date);
		date = new Date();
		initialize(date);
	}

	/**
	 * Sets a seed according to the date 
	 */
	public static void initialize(Date date) {
		randGenClass = RandomGeneratorCategory.MersenneTwister;
		useRandSeedTable = true;
		if(date == null){
			log.warn("passed date is null");
		}
		randNumGen = new Uniform(new DRand(date));
		if (useRandSeedTable)
			randSeedGen = new RandomSeedGenerator(randNumGen.nextIntFromTo(0, Integer.MAX_VALUE), randNumGen.nextIntFromTo(0, RandomSeedTable.COLUMNS - 1));
	}
	
	/**
	 * Method nextRandNumGen - returns a uniform random number generator
	 * 
	 * @param
	 * @return int
	 * @exception
	 */
	public static RandomEngine nextRandNumGen() throws SimQPNException {
		RandomEngine randomElement = null;
		int nextSeed = 0;

		if (randGenClass == RandomGeneratorCategory.DRand) {
			if (useRandSeedTable) {
				nextSeed = randSeedGen.nextSeed();
				while (!(nextSeed >= 0 && nextSeed < 1073741823))
					nextSeed = randSeedGen.nextSeed();
				randomElement = new DRand(nextSeed);
			} else {
				nextSeed = randNumGen.nextIntFromTo(0, 1073741823 - 1);
				randomElement = new DRand(nextSeed);
			}
		} else if (randGenClass == RandomGeneratorCategory.MersenneTwister) {
			if (useRandSeedTable) {
				nextSeed = randSeedGen.nextSeed();
				// The seed can be any 32-bit integer except 0. 
				// Shawn J. Cokus commented that perhaps the seed should preferably be odd.
				// while (nextSeed % 2 == 0) nextSeed = randSeedGen.nextSeed();
				while (nextSeed == 0)
					nextSeed = randSeedGen.nextSeed();
				randomElement = new MersenneTwister(nextSeed);
			} else {
				nextSeed = randNumGen.nextIntFromTo(Integer.MIN_VALUE, Integer.MAX_VALUE);
				// The seed can be any 32-bit integer except 0. 
				// Shawn J. Cokus commented that perhaps the seed should preferably be odd.
				// while (nextSeed % 2 == 0) nextSeed = randNumGen.nextIntFromTo(Integer.MIN_VALUE, Integer.MAX_VALUE);				
				while (nextSeed == 0)
					nextSeed = randNumGen.nextIntFromTo(Integer.MIN_VALUE, Integer.MAX_VALUE);
				randomElement = new MersenneTwister(nextSeed);
			}
		} else {
			log.error("Invalid randGenClass setting!");
			throw new SimQPNException();
		}
		return randomElement;
	}



}
