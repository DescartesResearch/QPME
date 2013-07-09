package de.tud.cs.simqpn.kernel.executor;

import de.tud.cs.simqpn.kernel.entities.Place;
import de.tud.cs.simqpn.kernel.entities.Token;
import de.tud.cs.simqpn.kernel.entities.Transition;

public class TokenEvent {
	private double incommingTime;
	private Place place;
	private int color;
	private int number;
	private Token[] tkCopyBuffer;
	private Transition firingTrans;

	public TokenEvent(double incommingTime, Transition firingTrans, Place place, int color, int number, Token[] tkCopyBuffer) {
		this.incommingTime = incommingTime;
		this.firingTrans = firingTrans;
		this.place = place;
		this.color = color;
		this.number = number;
		this.tkCopyBuffer = tkCopyBuffer;
	}


	/**
	 * @return the incommingTime
	 */
	public double getIncommingTime() {
		return incommingTime;
	}

	/**
	 * @return the color
	 */
	public int getColor() {
		return color;
	}

	/**
	 * @return the number
	 */
	public int getNumber() {
		return number;
	}

	/**
	 * @return the place
	 */
	public Place getPlace() {
		return place;
	}

	/**
	 * @return the tkCopyBuffer
	 */
	public Token[] getTkCopyBuffer() {
		return tkCopyBuffer;
	}


	/**
	 * @return the firingTrans
	 */
	public Transition getFiringTrans() {
		return firingTrans;
	}

}
