package de.tud.cs.simqpn.kernel.loading.distributions;

import cern.jet.random.AbstractContinousDistribution;
import de.tud.cs.simqpn.kernel.RandomNumberGenerator;
import de.tud.cs.simqpn.kernel.SimQPNException;
import de.tud.cs.simqpn.kernel.util.LogUtil;

public class EmpiricalCreator extends DistributionCreator {

	double pdf[] = null;
	String pdfFilename = null;
	double scale = -1;
	double offset;
	
	@Override
	protected void loadParams() throws SimQPNException {
		pdf = this.loadDoublesFromFile("pdf_filename");
		pdfFilename = this.loadStringParam("pdf_filename");
		scale = this.loadDoubleParam("scale");
		offset = this.loadDoubleParam("offset");
	}

	@Override
	public AbstractContinousDistribution getDistribution()
			throws SimQPNException {
		return new ScaledEmpirical( offset, scale, pdf, cern.jet.random.Empirical.LINEAR_INTERPOLATION,
				RandomNumberGenerator.nextRandNumGen());
	}

	@Override
	public double getMean() {
		//SDK-TODO: find out how meanServTimes is computed?

		log.warn(LogUtil.formatDetailMessage(
				"meanServTimes for " + this.getClass().getName() + " not initialized!" +
				"Might experience problems if indrStats is set to true!"));		

		return 0;
	}

	@Override
	public String getConstructionText() {
		return "(" + pdfFilename + ", LINEAR_INTERPOLATION, " + scale + ", Simulator.nextRandNumGen())";
	}

	@Override
	public String getMeanComputationText() {
		return "Undefined, used 0.0";
	}
}
