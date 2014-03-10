package de.tud.cs.simqpn.kernel.loader.distributions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public enum Distribution {

	BETA("Beta", new BetaCreator(), new String[] { "alpha", "beta"}, new String[] { "1", "1" }, false),
	
	BREIT_WIGNER("BreitWigner", new BreitWignerCreator(), new String[] { "mean", "gamma", "cut" }, new String[] { "1", "1", "1" }, false),
	
	BREIT_WIGNER_MEAN_SQUARE("BreitWignerMeanSquare", new BreitWignerMeanSquareCreator(), new String[] { "mean", "gamma", "cut" }, new String[] { "1", "1", "1" }, false),
	
	CHI_SQUARE("ChiSquare", new ChiSquareCreator(), new String[] { "freedom" }, new String[] { "1" }, false),

	DETERMINISTIC("Deterministic", new DeterministicCreator(), new String[] { "p1" }, new String[] { "1" }, false), 
	
	GAMMA("Gamma", new GammaCreator(), new String[] { "alpha", "lambda" }, new String[] { "1", "1" }, false),
	
	HYPERBOLIC("Hyperbolic", new HyperbolicCreator(), new String[] { "alpha", "beta" }, new String[] { "1", "1" }, false),

	EMPIRICAL("Empirical", new EmpiricalCreator(), new String[] { "scale" }, new String[] { "1" }, true),
	
	EXPONENTIAL("Exponential", new ExponentialCreator(), new String[] { "lambda" }, new String[] { "1" }, false),
	
	EXPONENTIAL_POWER("ExponentialPower", new ExponentialPowerCreator(), new String[] { "tau" }, new String[] { "1" }, false),
	
	LOGARITHMIC("Logarithmic", new LogarithmicCreator(), new String[] { "p" }, new String[] { "1" }, false),
	
	NORMAL("Normal", new NormalCreator(), new String[] { "mean", "stdDev" }, new String[] { "1", "1" }, false),

	PERIODICAL("Periodical", new PeriodicalCreator(), new String[] { }, new String[] { }, true),
	
	STUDENT_T("StudentT", new StudentTCreator(), new String[] { "freedom" }, new String[] { "1" }, false),
	
	UNIFORM("Uniform", new UniformCreator(), new String[] { "min", "max" }, new String[] { "1", "1" }, false),
	
	VON_MISES("VonMises", new VonMisesCreator(), new String[] { "freedom" }, new String[] { "1" }, false);
	
	public static final Distribution DEFAULT_DISTRIBUTION = EXPONENTIAL;
	
	private final String name;
	private final DistributionCreator creator;
	private final String[] paramNames;
	private final String[] defaultValues;
	private final boolean hasInputFromFile;
	
	private Distribution(String name, DistributionCreator creator, 
			String[] paramNames, String[] defaultValues, boolean hasInputFromFile) {
		
		this.name = name;
		this.creator = creator;
		this.paramNames = paramNames;
		this.defaultValues = defaultValues;
		this.hasInputFromFile = hasInputFromFile;
		
		if (paramNames.length != defaultValues.length) {
			throw new InternalError("Param names and default values arrays do not have the same size.");
		}
	}
	
	public String getName() {
		return this.name;
	}
	
	public DistributionCreator getCreator() {
		return this.creator;
	}
	
	public String[] getParamNames() {
		return this.paramNames;
	}
	
	public String[] getDefaultValues() {
		return this.defaultValues;
	}

	public int getParamsCount() {
		return this.paramNames.length;
	}
	
	public boolean hasInputFromFile() {
		return this.hasInputFromFile;
	}
	
	private static Map<String, Distribution> nameMapping = initCreatorMapping();
	
	private static Map<String, Distribution> initCreatorMapping() {
		Map<String, Distribution> mapping = new HashMap<String, Distribution>();
		
		for (Distribution distribution : Distribution.values()) {
			mapping.put(distribution.getName(), distribution);
		}
		
		return mapping;
	}
	
	public static Distribution fromName(String name) {
		Distribution distribution = nameMapping.get(name);
		
		if (distribution == null) {
			throw new IllegalArgumentException("Distribution with the name '" + name + "' is not defined");
		}
		
		return distribution;
	}
	
	public static Set<String> getAllParamNames() {
		Set<String> result = new HashSet<String>();
				
		for (Distribution dist : Distribution.values()) {
			for (String param : dist.paramNames) {
				result.add(param);
			}
		}
		
		return result;
	}
	
	public static List<String> getNames() {
		List<String> result = new ArrayList<String>();
		
		for (Distribution dist : Distribution.values()) {
			result.add(dist.getName());
		}

		return result;
	}
}
