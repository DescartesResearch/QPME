package de.tud.cs.qpe.editors.r;

import java.awt.FileDialog;
import java.awt.Frame;
import java.io.BufferedReader;
import java.io.InputStreamReader;

import org.rosuda.JRI.REXP;
import org.rosuda.JRI.RMainLoopCallbacks;
import org.rosuda.JRI.Rengine;

public class RIntegration {
	
	private static final String FILE_NAME = "C:\\Dokumente und Einstellungen\\zipp\\Desktop\\RunStats-queue0-col2-ST.txt";

	public static void main(String[] args) {

		// just making sure we have the right version of everything
		if (!Rengine.versionCheck()) {
			System.err.println("** Version mismatch - Java files don't match library version.");
			System.exit(1);
		}

		System.out.println("Creating R engine");
		Rengine re = new Rengine(null, false, new TextConsole());
		System.out.println("R engine created, waiting for R");
		// the engine creates R is a new thread, so we should wait until it's
		// ready
		if (!re.waitForR()) {
			System.out.println("Cannot load R");
			return;
		}
		System.out.println("Ready");
		
		String escapedFileName = FILE_NAME.replace("\\", "\\\\");
		re.eval("x <- read.delim('"+ escapedFileName + "', header = FALSE, sep='\\n', quote='', dec='.', fill=FALSE, comment.char='')", false);
		System.out.println("Loaded file " + FILE_NAME);
		REXP result = re.eval("mean(x[[1]])");
		double mean = result.asDouble();
		System.out.println("Mean value: " + mean);
		re.startMainLoop();
//		re.end();
	}
	
	private static class TextConsole implements RMainLoopCallbacks {
		public void rWriteConsole(Rengine re, String text, int oType) {
			System.out.print(text);
		}

		public void rBusy(Rengine re, int which) {
		}

		public String rReadConsole(Rengine re, String prompt, int addToHistory) {
			System.out.print(prompt);
			try {
				BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
				String s = br.readLine();
				return (s == null || s.length() == 0) ? s : s + "\n";
			} catch (Exception e) {
				System.out.println("jriReadConsole exception: " + e.getMessage());
			}
			return null;
		}

		public void rShowMessage(Rengine re, String message) {
			System.out.println("rShowMessage \"" + message + "\"");
		}

		public String rChooseFile(Rengine re, int newFile) {
			FileDialog fd = new FileDialog(new Frame(),
					(newFile == 0) ? "Select a file" : "Select a new file",
					(newFile == 0) ? FileDialog.LOAD : FileDialog.SAVE);
			fd.setVisible(true);
			String res = null;
			if (fd.getDirectory() != null)
				res = fd.getDirectory();
			if (fd.getFile() != null)
				res = (res == null) ? fd.getFile() : (res + fd.getFile());
			return res;
		}

		public void rFlushConsole(Rengine re) {
		}

		public void rLoadHistory(Rengine re, String filename) {
		}

		public void rSaveHistory(Rengine re, String filename) {
		}			
	}
}
