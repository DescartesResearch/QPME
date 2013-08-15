/*
 * Copyright 2010 Patrick Peschlow
 * 
 * This file is part of the jbarrier library.
 * 
 * The jbarrier library is free software; you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation; either version 3 of the License, or (at your
 * option) any later version.
 * 
 * The jbarrier library is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License
 * for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public License
 * along with the jbarrier library; if not, see <http://www.gnu.org/licenses>.
 */
package edu.bonn.cs.net.jbarrier.examples;

import java.lang.reflect.Constructor;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

import edu.bonn.cs.net.jbarrier.barrier.Barrier;
import edu.bonn.cs.net.jbarrier.barrier.ButterflyBarrier;
import edu.bonn.cs.net.jbarrier.barrier.CentralBarrier;
import edu.bonn.cs.net.jbarrier.barrier.DisseminationBarrier;
import edu.bonn.cs.net.jbarrier.barrier.FloatButterflyReduction;
import edu.bonn.cs.net.jbarrier.barrier.FloatCentralReduction;
import edu.bonn.cs.net.jbarrier.barrier.FloatDisseminationReduction;
import edu.bonn.cs.net.jbarrier.barrier.FloatReduction;
import edu.bonn.cs.net.jbarrier.barrier.FloatStaticTreeReduction;
import edu.bonn.cs.net.jbarrier.barrier.FloatTournamentReduction;
import edu.bonn.cs.net.jbarrier.barrier.GenericReductor;
import edu.bonn.cs.net.jbarrier.barrier.ReductionOperator;
import edu.bonn.cs.net.jbarrier.barrier.StaticTreeBarrier;
import edu.bonn.cs.net.jbarrier.barrier.TournamentBarrier;

/**
 * Performance test class for the barrier implementations. This class has three
 * main purposes:
 * <ul>
 * <li>It may be used to learn how to use the barrier algorithms for your
 * application.
 * <li>It may be used to measure the execution times of the different barrier
 * implementations for comparison purposes.
 * <li>It may be used in order to detect errors in the barrier implementations.
 * Threads perform identical computations between each barrier synchronization,
 * so by checking all their results for equality during the barrier action the
 * correctness of the synchronization algorithm may be verified to a certain
 * extent (let's say, with high probability).
 * </ul>
 * 
 * @version 1.0
 * 
 * @author Patrick Peschlow
 * @author Ivan Castilla Rodriguez
 */
public class BarrierExample {
	/**
	 * Types of barriers that can be tested.
	 * 
	 * @author Patrick Peschlow
	 * @author Ivan Castilla Rodriguez
	 */
	private enum BarrierType {
		/**
		 * {@link CentralBarrier Central barrier}
		 */
		CENTRAL(CentralBarrier.class, FloatCentralReduction.class),
		/**
		 * {@link TournamentBarrier Tournament barrier}
		 */
		TOURNAMENT(TournamentBarrier.class, FloatTournamentReduction.class),
		/**
		 * {@link ButterflyBarrier Butterfly barrier}
		 */
		BUTTERFLY(ButterflyBarrier.class, FloatButterflyReduction.class),
		/**
		 * {@link DisseminationBarrier Dissemination barrier}
		 */
		DISSEMINATION(DisseminationBarrier.class,
			FloatDisseminationReduction.class),
		/**
		 * {@link StaticTreeBarrier Static tree barrier}
		 */
		STATICTREE(StaticTreeBarrier.class, FloatStaticTreeReduction.class);

		/**
		 * Constructor.
		 * 
		 * @param barrierClass
		 *            the class of the respective barrier
		 * @param reductionClass
		 *            the reduction class of the respective barrier
		 */
		@SuppressWarnings("rawtypes")
		private BarrierType(Class barrierClass, Class reductionClass) {
			barrierClass_ = barrierClass;
			reductionClass_ = reductionClass;
		}

		/**
		 * The class of the barrier represented by this enum.
		 */
		@SuppressWarnings("rawtypes")
		private final Class barrierClass_;

		/**
		 * The class of the reduction barrier represented by this enum.
		 */
		@SuppressWarnings("rawtypes")
		private final Class reductionClass_;

		/**
		 * Whether this barrier is used in the current test run.
		 */
		private boolean isTested_ = false;

		/**
		 * The execution time samples collected for this barrier.
		 */
		private double[] samples_ = new double[20];

		/**
		 * The number of samples collected for this barrier.
		 */
		private int count_ = 0;

		/**
		 * Provides the class of this barrier.
		 * 
		 * @return the class of this barrier
		 */
		@SuppressWarnings("rawtypes")
		Class getBarrierClass() {
			return barrierClass_;
		}

		/**
		 * Provides the reduction class of this barrier.
		 * 
		 * @return the reduction class of this barrier
		 */
		@SuppressWarnings("rawtypes")
		Class getReductionClass() {
			return reductionClass_;
		}

		/**
		 * Configures whether this barrier is used in the current test run.
		 * 
		 * @param isTested
		 *            <code>true</code> if this barrier is to be used in the
		 *            current test run, otherwise <code>false</code>
		 */
		void setTested(boolean isTested) {
			isTested_ = isTested;
		}

		/**
		 * Whether this barrier is used in the current test run.
		 * 
		 * @return whether this barrier is used in the current test run
		 */
		boolean isTested() {
			return isTested_;
		}

		/**
		 * Adds a new sample, for statistics.
		 * 
		 * @param val
		 *            the new sample
		 */
		void addSample(double val) {
			ensureCapacity();
			samples_[count_++] = val;
		}

		/**
		 * Prints the statistics collected for this barrier.
		 */
		void printStats() {
			BarrierExample.printStats(name(), samples_, count_);
		}

		/**
		 * Ensures that the samples_ array does not overflow.
		 */
		private void ensureCapacity() {
			final int length = samples_.length;
			if (count_ == length) {
				double[] newSamples = new double[length * 2];
				System.arraycopy(samples_, 0, newSamples, 0, length);
				samples_ = newSamples;
			}
		}
	}

	/**
	 * Whether to test the barrier implementations or the reduction barrier
	 * implementations.
	 */
	private static boolean testSimpleBarriers = true;

	/**
	 * Whether to calculate and print statistics.
	 */
	private static boolean doStatistics = false;

	/**
	 * Whether to run the same experiment sequentially.
	 */
	private static boolean doSeq = false;

	/**
	 * Whether to run a the Java standard CyclicBarrier for comparison.
	 */
	private static boolean doCompareToCyclic = false;

	/**
	 * Number of tests to be performed per type of barrier.
	 */
	private static int numTests;

	/**
	 * Number of threads to be used during the test.
	 */
	private static int numThreads;

	/**
	 * Amount of work to be performed. If more than one thread is used, the
	 * workload is equally distributed among them.
	 */
	private static int workload;

	/**
	 * How many times the barrier is invoked.
	 */
	private static int numIterations;

	/**
	 * The barrier used when one of the simple barriers is tested.
	 */
	private static Barrier barrier;

	/**
	 * The barrier used when one of the reduction barriers is tested.
	 */
	private static FloatReduction reduction;

	/**
	 * The cyclic barrier used for comparison.
	 */
	private static CyclicBarrier cyclicBarrier;

	/**
	 * An array which stores the results, useful for verifying the correct
	 * behavior of the barrier.
	 */
	private static long[] dummyResults;

	/**
	 * The threads used during the test.
	 */
	private static ExampleThread[] exampleThreads;

	/**
	 * The action to be performed when all the threads has tripped the barrier.
	 */
	private static ExampleAction barrierAction = null;

	/**
	 * The reduction to be performed within the barrier.
	 */
	private static GenericReductor genericReductor = null;

	/**
	 * Helper variable for the test. Stores the result of a generic reduction.
	 */
	private static float genericReductionResult;

	/**
	 * Main method
	 * 
	 * @param args
	 *            Program arguments
	 */
	public static void main(String[] args) {
		if (args.length < 6 || args.length > 7) {
			die("Wrong number of parameters!\n" + getUsageMessage());
		}
		if ("b".equals(args[0])) {
			testSimpleBarriers = true;
		} else if ("r".equals(args[0])) {
			testSimpleBarriers = false;
		} else {
			die("Invalid first parameter!\n" + getUsageMessage());
		}
		if ("ALL".equals(args[1])) {
			for (BarrierType type : BarrierType.values()) {
				type.setTested(true);
			}
		} else {
			if (!"CENTRAL".equals(args[1]) && !"TOURNAMENT".equals(args[1])
				&& !"BUTTERFLY".equals(args[1])
				&& !"DISSEMINATION".equals(args[1])
				&& !"STATICTREE".equals(args[1])) {
				die("Invalid second parameter!\n" + getUsageMessage());
			}
			BarrierType.valueOf(args[1]).setTested(true);
		}
		numTests = Integer.parseInt(args[2]);
		if (numTests <= 0) {
			die("Invalid third parameter!\n" + getUsageMessage());
		}
		workload = Integer.parseInt(args[3]);
		if (workload < 0) {
			die("Invalid fourth parameter!\n" + getUsageMessage());
		}
		numIterations = Integer.parseInt(args[4]);
		if (numIterations <= 0) {
			die("Invalid fifth parameter!\n" + getUsageMessage());
		}
		numThreads = Integer.parseInt(args[5]);
		if (numThreads < 2) {
			die("Invalid sixth parameter!\n" + getUsageMessage());
		}
		if (workload % numThreads != 0) {
			die("Invalid combination of fourth and sixth parameter: workload has to be divisible by the number of threads.\n"
				+ getUsageMessage());
		}
		// Process modifiers, if any.
		String modifiers = "";
		if (args.length == 7) {
			modifiers = args[6].toLowerCase();
			// Action specified?
			if (modifiers.indexOf('a') != -1) {
				barrierAction = new ExampleAction();
			}
			// Compare to CyclicBarrier?
			if (modifiers.indexOf('c') != -1) {
				doCompareToCyclic = true;
			}
			// Generic reductor specified?
			if (modifiers.indexOf('r') != -1) {
				if (testSimpleBarriers) {
					genericReductor = new MinReductor();
				} else {
					System.out
						.println("Ignoring generic reductior flag 'r' for the reduction barrier test!");
				}
			}
			// Sequential execution specified?
			if (modifiers.indexOf('s') != -1) {
				doSeq = true;
			}
			// Statistics specified?
			if (modifiers.indexOf('t') != -1) {
				if (numTests > 1) {
					doStatistics = true;
				} else {
					System.out.println("Only one test. Skipping statistics...");
					doStatistics = false;
				}
			}
		}

		System.out
			.println("Starting experiment! Details:\ntest_type = "
				+ (testSimpleBarriers ? "b" : "r") + "\ntests = " + numTests
				+ "\nworkload = " + workload + "\niterations = "
				+ numIterations + "\nthreads = " + numThreads
				+ "\nmodifiers = " + modifiers + "\n");

		if (doSeq) {
			seqExperiment();
		}

		for (BarrierType type : BarrierType.values()) {
			if (type.isTested()) {
				testBarrier(type);
				if (doStatistics) {
					type.printStats();
				}
			}
		}

		if (doCompareToCyclic) {
			testCyclic();
		}
	}

	/**
	 * Runs the experiment with the specified barrier implementation.
	 * 
	 * @param type
	 *            The type of barrier used for this experiment
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private static void testBarrier(BarrierType type) {
		System.out.print(type.name() + ":");
		dummyResults = new long[numThreads];
		for (int threadId = 0; threadId < numThreads; threadId++) {
			dummyResults[threadId] = 0L;
		}
		exampleThreads = new ExampleThread[numThreads];
		for (int test = 0; test < numTests; test++) {
			// Create the threads.
			for (int threadId = 0; threadId < numThreads; threadId++) {
				exampleThreads[threadId] = new ExampleThread(threadId);
			}
			// Create the barrier.
			try {
				if (testSimpleBarriers) {
					Class barrierClass = type.getBarrierClass();
					assert Barrier.class.isAssignableFrom(barrierClass) : barrierClass;
					Constructor constructor =
						barrierClass.getConstructor(int.class, Runnable.class,
							GenericReductor.class);
					barrier =
						(Barrier) constructor.newInstance(numThreads,
							barrierAction, genericReductor);
				} else {
					Class reductionClass = type.getReductionClass();
					assert FloatReduction.class
						.isAssignableFrom(reductionClass) : reductionClass;
					Constructor constructor =
						reductionClass.getConstructor(int.class,
							Runnable.class, ReductionOperator.class);
					reduction =
						(FloatReduction) constructor.newInstance(numThreads,
							barrierAction,
							new ReductionOperator.MinimumReduction());
				}
			} catch (Exception e) {
				System.out.print("Exception during barrier initialization!");
				e.printStackTrace();
			}
			// Start the experiment.
			final long t1 = System.nanoTime();
			for (int threadId = 0; threadId < numThreads; threadId++) {
				exampleThreads[threadId].start();
			}
			try {
				for (int threadId = 0; threadId < numThreads; threadId++) {
					exampleThreads[threadId].join();
				}
			} catch (InterruptedException e) {
				System.out.print("InterruptedException!");
				e.printStackTrace();
			}
			final double result = (System.nanoTime() - t1) / 1000000.0;
			if (doStatistics) {
				type.addSample(result);
			}
			System.out.print("\t" + result + " ms");
		}
		System.out.println();
	}

	/**
	 * Runs the experiment using the standard Java CyclicBarrier.
	 */
	private static void testCyclic() {
		System.out.print("CYCLIC:");
		dummyResults = new long[numThreads];
		for (int threadId = 0; threadId < numThreads; threadId++) {
			dummyResults[threadId] = 0L;
		}
		CyclicThread[] cyclicThreads = new CyclicThread[numThreads];
		double[] samples = new double[numTests];
		for (int test = 0; test < numTests; test++) {
			// Create the threads.
			for (int threadId = 0; threadId < numThreads; threadId++) {
				cyclicThreads[threadId] = new CyclicThread(threadId);
			}
			// Create the barrier.
			cyclicBarrier = new CyclicBarrier(numThreads, barrierAction);
			// Start the experiment.
			final long t1 = System.nanoTime();
			for (int threadId = 0; threadId < numThreads; threadId++) {
				cyclicThreads[threadId].start();
			}
			try {
				for (int threadId = 0; threadId < numThreads; threadId++) {
					cyclicThreads[threadId].join();
				}
			} catch (InterruptedException e) {
				System.out.print("InterruptedException!");
				e.printStackTrace();
			}
			final double result = (System.nanoTime() - t1) / 1000000.0;
			if (doStatistics) {
				samples[test] = result;
			}
			System.out.print("\t" + result + " ms");
		}
		System.out.println();
		if (doStatistics) {
			printStats("CYCLIC", samples, numTests);
		}
	}

	/**
	 * Executes a set of sequential tests.
	 */
	private static void seqExperiment() {
		dummyResults = new long[1];
		double[] seqSamples = new double[numTests];
		final int iter = numIterations;
		final int work = workload;
		System.out.print("SEQ:");
		for (int test = 0; test < numTests; test++) {
			final long t1 = System.nanoTime();
			for (int i = 0; i < iter; i++) {
				dummyResults[0] += dummyWork(work);
			}
			final double val = (System.nanoTime() - t1) / 1000000.0;
			if (doStatistics) {
				seqSamples[test] = val;
			}
			System.out.print("\t" + val + " ms");
		}
		System.out.println();
		if (doStatistics) {
			printStats("SEQ", seqSamples, numTests);
		}
	}

	/**
	 * Carries out some dummy work to keep the CPU busy.
	 * 
	 * @param work
	 *            the amount of work to do
	 * @return a dummy result
	 */
	private static long dummyWork(int work) {
		long localDummyRes = 0L;
		for (int j = 0; j < work; j++) {
			localDummyRes += Math.log(j);
		}
		return localDummyRes;
	}

	/**
	 * Prints statistical information about the test execution
	 * 
	 * @param name
	 *            name of the barrier used (SEQ if sequential execution)
	 * @param samples
	 *            results of the test execution
	 * @param count
	 *            number of samples
	 */
	private static void printStats(String name, double[] samples, int count) {
		double sum = 0.0;
		double min = Double.MAX_VALUE;
		double max = Double.MIN_VALUE;
		for (int i = 0; i < count; i++) {
			sum += samples[i];
			if (samples[i] < min) {
				min = samples[i];
			}
			if (samples[i] > max) {
				max = samples[i];
			}
		}
		final double mean = sum / count;

		double metricVariance = 0.0;
		for (int i = 0; i < count; i++) {
			final double diff = samples[i] - mean;
			metricVariance += diff * diff;
		}
		metricVariance /= (count - 1);
		final double metricStddev = Math.sqrt(metricVariance);

		System.out.println("Summary statistics for " + name + ": min=" + min
			+ ", max=" + max + ", mean=" + mean + ", stddev=" + metricStddev);
	}

	/**
	 * Prints usage information.
	 * 
	 * @return a string containing the usage information
	 */
	private static String getUsageMessage() {
		String usage =
			"Usage:\n"
				+ "java "
				+ BarrierExample.class.getSimpleName()
				+ " <test_type> <barrier_type> <tests> <workload> <iterations> <threads> <modifiers>\n\n"
				+ "<test_type>: [b|r]; \'b\' tests the barriers, \'r\' the reduction barriers\n"
				+ "<barrier_type> [ALL|CENTRAL|TOURNAMENT|BUTTERFLY|DISSEMINATION|STATICTREE]; specify that either all barriers or a specific barrier is to be tested\n"
				+ "<tests>: a positive integer; the number of tests to be performed per barrier implementation\n"
				+ "<workload>: a nonnegative integer; the total amount of work to be performed (has to be divisible between the number of threads used)\n"
				+ "<iterations>: a positive integer; how many times each barrier is invoked in a single test run\n"
				+ "<threads>: an integer >= 2: the number of threads meeting at the barrier (note that, for some barrier implementations, only powers of two may be used)\n"
				+ "<modifiers>: [acrst]; write one or more of these characters to specify different modifiers:\n"
				+ "\t\"a\" adds a barrier action\n"
				+ "\t\"c\" compare to the Java standard CyclicBarrier (note that this barrier does not support reduction)\n"
				+ "\t\"r\" adds a generic reduction operation (note that, for reduction barriers, a generic reductor is not applicable)\n"
				+ "\t\"s\" includes <tests> sequential runs which may be used to compute speedups of parallel over sequential execution\n"
				+ "\t\"t\" prints statistics";
		return usage;
	}

	/**
	 * Terminate the program in case of invalid arguments.
	 * 
	 * @param msg
	 *            custom error message.
	 */
	private static void die(String msg) {
		System.out.println(msg);
		System.err.println(msg);
		System.exit(-1);
	}

	/**
	 * An action to be executed when all the threads have reached the barrier.
	 * As a correctness check for the barrier, the action checks whether all
	 * threads have computed the same value. Also, the action stores the result
	 * of the generic reduction so that all threads may see the final value.
	 * 
	 * @version 1.0
	 * 
	 * @author Patrick Peschlow
	 * @author Ivan Castilla Rodriguez
	 */
	static class ExampleAction implements Runnable {
		/**
		 * @see java.lang.Runnable#run()
		 */
		@Override
		public void run() {
			long ref = dummyResults[0];
			for (int threadId = 1; threadId < numThreads; threadId++) {
				if (dummyResults[threadId] != ref) {
					System.out.println("Barrier failed");
					System.err.println("Barrier failed");
				}
			}
			if (genericReductor != null) {
				genericReductionResult = exampleThreads[0].getMinValue();
			}
		}
	}

	/**
	 * A worker thread which carries out some local computation and then reaches
	 * a barrier.
	 * 
	 * @version 1.0
	 * 
	 * @author Patrick Peschlow
	 * @author Ivan Castilla Rodriguez
	 */
	static class ExampleThread extends Thread {
		/**
		 * The unique id of this thread.
		 */
		private final int threadId_;

		/**
		 * The local variable to compute the minimum in case a reduction is
		 * used.
		 */
		private float minValue_;

		/**
		 * Creates a thread which carries out some local computation.
		 * 
		 * @param threadId
		 *            The unique id of this thread
		 */
		public ExampleThread(int threadId) {
			super();
			threadId_ = threadId;
		}

		/**
		 * Returns the minimum value being computed by this thread.
		 * 
		 * @return the minimum value being computed by this thread
		 */
		public float getMinValue() {
			return minValue_;
		}

		/**
		 * Sets the value of the minimum.
		 * 
		 * @param minValue
		 *            minimum value to be set
		 */
		public void setMinValue(float minValue) {
			minValue_ = minValue;
		}

		/**
		 * Runs the experiment with one of the simple barriers of the library.
		 */
		private void runBarrier() {
			final int iter = numIterations;
			final int work = workload / numThreads;
			for (int i = 0; i < iter; i++) {
				minValue_ = threadId_ + 100 * i;
				dummyResults[threadId_] += dummyWork(work);
				barrier.await(threadId_);
				if (genericReductor != null) {
					float result = genericReductionResult;
					if (result != 100 * i) {
						System.err.println("During "
							+ barrier.getClass().getSimpleName()
							+ " test, iteration " + i + ": Thread " + threadId_
							+ " reduction failed: " + result + " (expected: "
							+ (100.0 * i) + ")");
					}
				}
			}
		}

		/**
		 * Runs the experiment with one of the reduction barriers of the
		 * library.
		 */
		private void runReduction() {
			final int iter = numIterations;
			final int work = workload / numThreads;
			for (int i = 0; i < iter; i++) {
				float myValue = threadId_ + 100 * i;
				dummyResults[threadId_] += dummyWork(work);
				float result = reduction.await(threadId_, myValue);
				final float expectedResult = 100 * i;
				if (result != expectedResult) {
					System.err.println("During "
						+ reduction.getClass().getSimpleName()
						+ " test, iteration " + i + ": Thread " + threadId_
						+ " reduction failed: " + myValue + " (expected: "
						+ expectedResult + ")");
				}
			}
		}

		/**
		 * @see java.lang.Thread#run()
		 */
		@Override
		public void run() {
			if (testSimpleBarriers) {
				runBarrier();
			} else {
				runReduction();
			}
		}
	}

	/**
	 * A simple implementation of a min reduction.
	 * 
	 * @version 1.0
	 * 
	 * @author Patrick Peschlow
	 * @author Ivan Castilla Rodriguez
	 */
	static class MinReductor implements GenericReductor {
		/**
		 * @see edu.bonn.cs.net.jbarrier.barrier.GenericReductor#reduce(int,
		 *      int)
		 */
		@Override
		public void reduce(int threadId1, int threadId2) {
			final float val1 = exampleThreads[threadId1].getMinValue();
			final float val2 = exampleThreads[threadId2].getMinValue();
			if (val2 < val1) {
				exampleThreads[threadId1].setMinValue(val2);
			}
		}
	}

	/**
	 * A worker thread for testing the cyclic barrier.
	 * 
	 * @version 1.0
	 * 
	 * @author Patrick Peschlow
	 * @author Ivan Castilla Rodriguez
	 */
	static class CyclicThread extends Thread {
		/**
		 * The unique id of this thread.
		 */
		private final int threadId_;

		/**
		 * Creates a thread which carries out some local computation.
		 * 
		 * @param threadId
		 *            The unique id of this thread
		 */
		public CyclicThread(int threadId) {
			super();
			threadId_ = threadId;
		}

		/**
		 * Runs the experiment with the CyclicBarrier.
		 */
		private void runCyclic() {
			try {
				final int iter = numIterations;
				final int work = workload / numThreads;
				for (int i = 0; i < iter; i++) {
					dummyResults[threadId_] += dummyWork(work);
					cyclicBarrier.await();
				}
			} catch (InterruptedException e) {
				System.out.print("InterruptedException!");
				e.printStackTrace();
			} catch (BrokenBarrierException e) {
				System.out.print("BrokenBarrierException!");
				e.printStackTrace();
			}
		}

		/**
		 * @see java.lang.Thread#run()
		 */
		@Override
		public void run() {
			runCyclic();
		}
	}
}
