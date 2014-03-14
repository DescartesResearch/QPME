package de.tud.cs.simqpn.kernel.loading.distributions;

import cern.jet.random.AbstractContinousDistribution;
import de.tud.cs.simqpn.kernel.SimQPNException;
import de.tud.cs.simqpn.kernel.util.LogUtil;

public class PeriodicalCreator extends DistributionCreator {

	double pdf[] = null;
	String pdfFilename = null;
	
	@Override
	protected void loadParams() throws SimQPNException {
		pdf = this.loadDoublesFromFile("pdf_filename");
		pdfFilename = this.loadStringParam("pdf_filename");
	}

	@Override
	public AbstractContinousDistribution getDistribution()
			throws SimQPNException {

		return new Periodical(pdf);
	}

	@Override
	public double getMean() {
		// TODO Compute mean
		
		log.warn(LogUtil.formatDetailMessage(
				"meanServTimes for " + this.getClass().getName() + " not initialized!" +
				"Might experience problems if indrStats is set to true!"));		
		
		return 0;
	}

	@Override
	public String getConstructionText() {
		return "(" + pdfFilename + ")";
	}

	@Override
	public String getMeanComputationText() {
		return "Undefined, used 0.0";
	}

}
