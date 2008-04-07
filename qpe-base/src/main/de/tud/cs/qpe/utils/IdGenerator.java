package de.tud.cs.qpe.utils;

public class IdGenerator {
	static long current= System.currentTimeMillis();
	
	static public synchronized String get(){
		return Long.toString(current++);
    }
}
