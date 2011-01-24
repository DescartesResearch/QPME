package de.tud.cs.simqpn.rt.framework.run;

import java.io.File;

import de.tud.cs.simqpn.rt.framework.run.SimulationRunner.AnalysisMode;
import de.tud.cs.simqpn.rt.framework.run.SimulationRunner.StoppingRule;

public class RunConfig {

	private File model;
	private String configurationName;
	private AnalysisMode analysisMode;
	private StoppingRule stoppingRule;
	private boolean expectError;
	private int repeats;

	public File getModel() {
		return model;
	}

	public void setModel(File model) {
		this.model = model;
	}

	public String getConfigurationName() {
		return configurationName;
	}

	public void setConfigurationName(String name) {
		this.configurationName = name;
	}

	public AnalysisMode getAnalysisMode() {
		return analysisMode;
	}

	public void setAnalysisMode(AnalysisMode mode) {
		this.analysisMode = mode;
	}

	public StoppingRule getStoppingRule() {
		return stoppingRule;
	}

	public void setStoppingRule(StoppingRule rule) {
		this.stoppingRule = rule;
	}

	public boolean isExpectError() {
		return expectError;
	}

	public void setExpectError(boolean expectError) {
		this.expectError = expectError;
	}

	public int getRepeats() {
		return repeats;
	}

	public void setRepeats(int repeats) {
		this.repeats = repeats;
	}

}
