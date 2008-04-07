/*
 * Created on 09.06.2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package de.tud.cs.qpe.utils;

import java.io.BufferedInputStream;
import java.io.IOException;

import org.eclipse.swt.graphics.Image;

/**
 * @author cdutz
 * 
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class ImageUtils {
	public static Image createImage(String path) {
		BufferedInputStream imgStream = new BufferedInputStream(ImageUtils.class.getResourceAsStream(path));
		try {
			if (imgStream.available() > 0) {
				return new Image(null, imgStream);
			}
		} catch (IOException ioe) {
		} finally {
			try {
				imgStream.close();
			} catch (IOException ioe) {
			}
		}
		return null;
	}
}
