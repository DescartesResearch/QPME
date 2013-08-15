/**
 * Generator programs for templates and primitive type reduction barriers. The
 * contents of this package are only required by developers who want to update
 * the primitive type reduction barriers.
 * 
 * In order to simplify updates to the reduction barriers, two generator
 * programs are used. First, the templates are updated from Java code. Then, the
 * new Java sources are generated from the templates. This is best explained by
 * an example:
 * 
 * Say you would like to make changes to the tournament barrier algorithm used
 * in the primitive type reduction barriers. Then you may pick one of the
 * implementations, e.g., the FloatTournamentBarrier, and make changes to its
 * sourcecode using your favourite IDE, taking advantage of all its features for
 * editing Java code. When finished, you run the
 * {@link edu.bonn.cs.net.jbarrier.template.GenerateTemplates} program
 * specifying "Float" as the primitive type to use. This updates the templates.
 * The final step is to run
 * {@link edu.bonn.cs.net.jbarrier.template.GeneratePrimitiveTypeClasses} which
 * creates all primitive type variants of the barrier, i.e.,
 * FloatTournamentBarrier, DoubleTournamentBarrier, etc. This way, you will only
 * ever need to edit Java code in order to update the primitive type classes.
 */
package edu.bonn.cs.net.jbarrier.template;