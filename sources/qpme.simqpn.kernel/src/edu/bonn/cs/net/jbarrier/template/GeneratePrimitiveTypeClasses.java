/*
 * Copyright 2010 Patrick Peschlow
 * 
 * This file is part of the jbarrier library.
 * 
 * The jbarrier library is free software; you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation; either version 3 of the License, or (at your
 * option) any later version.
 * 
 * The jbarrier library is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License
 * for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public License
 * along with the jbarrier library; if not, see <http://www.gnu.org/licenses>.
 */
package edu.bonn.cs.net.jbarrier.template;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.regex.Pattern;

/**
 * Generates reduction classes for the primitive types int, long, float, and
 * double, based on a template. Make sure the templates are in a valid state
 * before running this program. A safe way is to make changes to one of the
 * primitive type classes, e.g., the Float variant of a barrier, and then
 * generate the templates from Java code using the GenerateTemplates program.
 * 
 * @version 1.0
 * 
 * @author Patrick Peschlow
 * @author Ivan Castilla Rodriguez
 */
public class GeneratePrimitiveTypeClasses {
	/**
	 * Main method.
	 * 
	 * @param args
	 *            input parameters
	 */
	public static void main(String[] args) {
		if (args.length < 2) {
			System.out
				.println("Usage: GeneratePrimitiveTypeClasses <input path> <output path>\n<input path> usually is the path to the barrier.template package directory\n<output path> usually is the path to the barrier package directory");
			return;
		}
		File inPath = new File(args[0]);
		File outPath = new File(args[1]);
		if (!outPath.exists()) {
			System.err.println("Path " + outPath.getAbsolutePath()
				+ " does not exist");
			return;
		}

		String[] primitives = { "int", "long", "float", "double" };
		String[] primitivesUpperCase = { "Int", "Long", "Float", "Double" };

		// Generate reduction barrier source code.
		String[] templatePrefixes =
			{ "Central", "Dissemination", "Butterfly", "Tournament",
				"StaticTree" };
		for (int i = 0; i < templatePrefixes.length; i++) {
			String templateName =
				"P" + templatePrefixes[i] + "Reduction.template";
			String outputName = templatePrefixes[i] + "Reduction.java";
			generateClasses(primitives, primitivesUpperCase, templateName,
				outputName, inPath, outPath);
		}

		// Generate reduction interface source code.
		String templateName = "PReduction.template";
		String outputName = "Reduction.java";
		generateClasses(primitives, primitivesUpperCase, templateName,
			outputName, inPath, outPath);

		System.out.println("Generation complete.");
	}

	/**
	 * Generates all primitive type classes for the specified template.
	 * 
	 * @param primitives
	 *            the primitive type classes
	 * @param primitivesUC
	 *            the primitive type classes starting with an uppercase letter
	 * @param inputFileName
	 *            the name of the input file
	 * @param outputFileNameSuffix
	 *            the suffix for the name of the output files
	 * @param inputPath
	 *            the path of the input file
	 * @param outputPath
	 *            the path of the output files
	 */
	private static void generateClasses(String[] primitives,
		String[] primitivesUC, String inputFileName,
		String outputFileNameSuffix, File inputPath, File outputPath) {
		String templateCode = readFile(inputFileName, inputPath);
		for (int i = 0; i < primitives.length; i++) {
			String primitive = primitives[i];
			String primitiveUC = primitivesUC[i];
			String outputCode = templateCode;
			outputCode =
				Pattern.compile("#TYPE#").matcher(outputCode)
					.replaceAll(primitive);
			outputCode =
				Pattern.compile("#TYPE_NAME#").matcher(outputCode)
					.replaceAll(primitiveUC);
			String outFile = primitiveUC + outputFileNameSuffix;
			writeFile(outFile, outputPath, outputCode);
		}
	}

	/**
	 * Writes to a file.
	 * 
	 * @param name
	 *            the name of the file
	 * @param path
	 *            the path of the file
	 * @param out
	 *            the text to write
	 */
	private static void writeFile(String name, File path, String out) {
		File destination = new File(path, name);
		File parent = destination.getParentFile();
		parent.mkdirs();
		if (destination.exists()) {
			destination.delete();
		}
		try {
			FileWriter writer = new FileWriter(destination);
			try {
				writer.write(out);
				writer.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			System.out.println("Wrote: " + destination);
		} catch (IOException ex) {
			System.out.println("Problem occured in writeFile(): "
				+ ex.getMessage());
			System.err.println("Problem occured in writeFile(): "
				+ ex.getMessage());
			System.exit(-1);
		}
	}

	/**
	 * Reads from a file.
	 * 
	 * @param name
	 *            the name of the file
	 * @param path
	 *            the path of the file
	 * @return the contents of the file as a string
	 */
	private static String readFile(String name, File path) {
		File file = new File(path, name);
		if (!file.exists()) {
			throw new IllegalArgumentException("Couldn't find: " + file);
		}
		StringBuilder out = new StringBuilder();
		try {
			BufferedReader reader = new BufferedReader(new FileReader(file));
			try {
				String line = null;
				while ( (line = reader.readLine()) != null) {
					out.append(line);
					out.append("\n");
				}
			} finally {
				reader.close();
			}
		} catch (IOException ex) {
			System.out.println("Problem occured in readFile(): "
				+ ex.getMessage());
			System.err.println("Problem occured in readFile(): "
				+ ex.getMessage());
			System.exit(-1);
		}
		return out.toString();
	}
}
