/* ==============================================
 * QPME : Queueing Petri net Modeling Environment
 * ==============================================
 *
 * (c) Copyright 2003-2011, by Samuel Kounev and Contributors.
 * 
 * Project Info:   http://descartes.ipd.kit.edu/projects/qpme/
 *                 http://www.descartes-research.net/
 *    
 * All rights reserved. This software is made available under the terms of the 
 * Eclipse Public License (EPL) v1.0 as published by the Eclipse Foundation
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * This software is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY
 * or FITNESS FOR A PARTICULAR PURPOSE. See the Eclipse Public License (EPL)
 * for more details.
 *
 * You should have received a copy of the Eclipse Public License (EPL)
 * along with this software; if not visit http://www.eclipse.org or write to
 * Eclipse Foundation, Inc., 308 SW First Avenue, Suite 110, Portland, 97204 USA
 * Email: license (at) eclipse.org 
 *  
 * [Java is a trademark or registered trademark of Sun Microsystems, Inc.
 * in the United States and other countries.]
 *                                
 * =============================================
 *
 * Original Author(s):  Samuel Kounev and Christofer Dutz
 * Contributor(s):   
 * 
 * NOTE: The above list of contributors lists only the people that have
 * contributed to this source file - for a list of ALL contributors to 
 * the project, please see the README.txt file.
 * 
 *  History:
 *  Date        ID                Description
 *  ----------  ----------------  ------------------------------------------------------------------  
 *  2006        Christofer Dutz   Created.
 * 
 */
package de.tud.cs.qpe.utils;

import java.util.Random;

import org.eclipse.swt.graphics.RGB;

public class ColorHelper {
	protected static Random generator = new Random();
	
	public static RGB getRGBFromString(String code) {
		if(code == null) {
			return null;
		}
		String rHex = code.substring(1, 3);
		String gHex = code.substring(3, 5);
		String bHex = code.substring(5, 7);
		
		int rInt = Integer.valueOf(rHex, 16).intValue();
		int gInt = Integer.valueOf(gHex, 16).intValue();
		int bInt = Integer.valueOf(bHex, 16).intValue();
		
		return new RGB(rInt, gInt, bInt);
	}
	
	public static String getStringfromRGB(RGB rgb) {
		String rHex = Integer.toHexString(rgb.red);
		if(rHex.length() == 1) {
			rHex = "0" + rHex;
		}
		String gHex = Integer.toHexString(rgb.green);
		if(gHex.length() == 1) {
			gHex = "0" + gHex;
		}
		String bHex = Integer.toHexString(rgb.blue);
		if(bHex.length() == 1) {
			bHex = "0" + bHex;
		}
		return "#" + rHex + gHex + bHex;
	}
	
	public static String generateRandomColor() {
		int red = generator.nextInt() % 256;
		int green = generator.nextInt() % 256;
		int blue = generator.nextInt() % 256;
		RGB newRgb = new RGB((red >= 0) ? red : -red, (green >= 0) ? green : -green, (blue >= 0) ? blue : -blue);
		return ColorHelper.getStringfromRGB(newRgb);
	}
}
