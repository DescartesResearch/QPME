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
 */
// TODO Describe this class and add default header
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
