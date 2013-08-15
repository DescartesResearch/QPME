/**
 * Main package containing all the barrier and reduction algorithms. A barrier
 * is a construct for synchronizing a number of threads, usually after they have
 * performed some computation in parallel. When a thread reaches the barrier, it
 * waits until all other threads have reached the barrier as well. As soon as
 * all threads have reached the barrier, they are released again and start the
 * next parallel computation.
 * <p>
 * There are various well-known ways to implement the functionality of a
 * barrier. Currently, this package includes five different barrier algorithms:
 * a barrier based on a single shared counter (central barrier) and four
 * tree-based barriers (butterfly barrier, dissemination barrier, static tree
 * barrier, tournament barrier). With increasing numbers of threads, tree-based
 * barriers offer higher scalability than a central barrier.
 * <p>
 * In addition to pure thread synchronization, the barriers of this package have
 * two important features:
 * <ol type="1">
 * <li>Support for an optional barrier action that is executed when all threads
 * have reached the barrier, but before they are released again.
 * <li>Support for a global reduction, e.g. computing the minimum of a number of
 * numerical values contributed by the threads when they reach the barrier.
 * </ol>
 * A barrier action may simply be specified as an optional
 * {@link java.lang.Runnable} argument to the barrier constructors. Global
 * reductions may be realized in two different ways:
 * <ul>
 * <li>For popular types of reductions, i.e., minimum, maximum, and sum, special
 * "reduction barrier" classes are available for the primitive types int, long,
 * float, and double (e.g.,
 * {@link edu.bonn.cs.net.jbarrier.barrier.FloatButterflyReduction}). With a
 * reduction barrier, each thread hands its local value to the await() method of
 * the barrier and retrieves the result of the global reduction as a return
 * value as soon as the barrier is completed. Using one of these classes may do
 * the job in a number of use cases.
 * <li>The constructors of the standard barrier classes (such as
 * {@link edu.bonn.cs.net.jbarrier.barrier.TournamentBarrier}) take an optional
 * argument of type {@link edu.bonn.cs.net.jbarrier.barrier.GenericReductor}.
 * Using a generic reductor, arbitrary types of reductions (e.g., several
 * reductions in parallel during the same barrier) may be implemented.
 * </ul>
 */
package edu.bonn.cs.net.jbarrier.barrier;