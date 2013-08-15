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
 * Generates templates based on one of the current primitive types classes. You
 * may use Float, Double, or Long for that purpose. It is discouraged (and won't
 * even produce a meaningful result) to make changes to one of the Int reduction
 * barrier implementations and generate templates from it: There are many other
 * ints (except those intended to be replaced by other primitive types) used by
 * the barrier implementations.
 * 
 * @version 1.0
 * 
 * @author Patrick Peschlow
 * @author Ivan Castilla Rodriguez
 */
public class GenerateTemplates {
	/**
	 * Main method.
	 * 
	 * @param args
	 *            input parameters
	 */
	public static void main(String[] args) {
		if (args.length < 3) {
			System.out
				.println("Usage: GenerateTemplates <input path> <output path> <primitive type used>\n<input path> usually is the path to the barrier package directory\n<output path> usually is the path to the barrier package.template directory\n<primitive type used> indicates the primitive type the classes of which will be used to generate the templates, e.g., \"Int\", \"Long\", \"Float\", or \"Double\"");
			return;
		}
		File inPath = new File(args[0]);
		File outPath = new File(args[1]);
		if (!outPath.exists()) {
			System.err.println("Path " + outPath.getAbsolutePath()
				+ " does not exist");
			return;
		}
		String sourceTypeUpperCase = args[2];
		if (sourceTypeUpperCase.equals("Int")) {
			System.err
				.println("Int is discouraged as primitive type, as it will lead to unwanted effects when generating templates");
			return;
		}
		if (! (sourceTypeUpperCase.equals("Long")
			|| sourceTypeUpperCase.equals("Float") || sourceTypeUpperCase
			.equals("Double"))) {
			System.err.println("Unknown source primitive type: "
				+ sourceTypeUpperCase);
			return;
		}
		String sourceType = sourceTypeUpperCase.toLowerCase();

		// Generate different reduction barrier template source code.
		String[] templatePrefixes =
			{ "Central", "Dissemination", "Butterfly", "Tournament",
				"StaticTree" };
		for (int i = 0; i < templatePrefixes.length; i++) {
			String inputName =
				sourceTypeUpperCase + templatePrefixes[i] + "Reduction.java";
			String outputName =
				"P" + templatePrefixes[i] + "Reduction.template";
			generateTemplate(sourceType, sourceTypeUpperCase, inputName,
				outputName, inPath, outPath);
		}

		// Generate reduction interface template source code.
		String inputName = sourceTypeUpperCase + "Reduction.java";
		String outputName = "PReduction.template";
		generateTemplate(sourceType, sourceTypeUpperCase, inputName,
			outputName, inPath, outPath);

		System.out.println("Generation complete.");
	}

	/**
	 * Generates the specified template.
	 * 
	 * @param sourceType
	 *            the primitive type used for generating the template
	 * @param sourceTypeUC
	 *            the primitive type used starting with an uppercase letter
	 * @param inputFileName
	 *            the name of the input file
	 * @param outputFileName
	 *            the name of the output file
	 * @param inputPath
	 *            the path of the input file
	 * @param outputPath
	 *            the path of the output file
	 */
	private static void generateTemplate(String sourceType,
		String sourceTypeUC, String inputFileName, String outputFileName,
		File inputPath, File outputPath) {
		String inputCode = readFile(inputFileName, inputPath);
		String outputCode = inputCode;
		outputCode =
			Pattern.compile(sourceType).matcher(outputCode)
				.replaceAll("#TYPE#");
		outputCode =
			Pattern.compile(sourceTypeUC).matcher(outputCode)
				.replaceAll("#TYPE_NAME#");
		writeFile(outputFileName, outputPath, outputCode);
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
