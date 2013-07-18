package de.tud.cs.simqpn.kernel.executor;

import de.tud.cs.simqpn.kernel.entities.Place;
import de.tud.cs.simqpn.kernel.entities.Token;
import de.tud.cs.simqpn.kernel.entities.Transition;

/**
 * Container to pass a token from one {@link parallel.LP} to another
 * 
 * @author Jürgen Walter
 */
public class TokenEvent {
	/** time the token arrives in a LP */
	private double time;
	private Place place;
	private int color;
	private int number;
	private Token[] tkCopyBuffer;
	private Transition firingTrans;

	/**
	 * Constructor
	 * @param time the timestamp at which this event can be processed
	 * @param firingTrans the transition which fired
	 * @param place the recieving place
	 * @param color the color of the token(s)
	 * @param number the number of tokens
	 * @param tkCopyBuffer
	 */
	public TokenEvent(double time, Transition firingTrans, Place place, int color, int number, Token[] tkCopyBuffer) {
		this.time = time;
		this.firingTrans = firingTrans;
		this.place = place;
		this.color = color;
		this.number = number;
		this.tkCopyBuffer = tkCopyBuffer;
	}


	/**
	 * @return the incomingTime
	 */
	public double getTime() {
		return time;
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
