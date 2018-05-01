package org.nzip;

import java.io.File;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

public class CommandTool {
	
	public static void main(String[] args) {
		Options options = new Options();
		options.addOption("c", true, "required please enter the command name - list,extract or compress");
		options.addOption("e", true, "please enter the zip extensions separated by comma");
		options.addOption("s", true, "required please enter source directory or file");
		options.addOption("t",true,"please enter target file or directory");
		options.addOption("u", true, "please enter the suffix for extracting zip directories");
		options.addOption("h",false,"help");
		
		
		CommandLine line = null;
		CommandLineParser parser = new DefaultParser();
		try {
			line = parser.parse(options,args);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			System.err.println(e.getMessage());
			return;
		}
		
		if(line.hasOption('h')) {
		HelpFormatter formatter = new HelpFormatter();
		formatter.printHelp( "nzip", options );
		return;
		}
		
		
		String source = null;
		if(line.hasOption("s")) {
		 source = line.getOptionValue('s');
		}
		else {
			System.err.println("please provide the source");
			return;
		}
		
		String target = line.getOptionValue("t");
		
		String suffix;
		if(line.hasOption('u')) {
			suffix = line.getOptionValue('u');
		}
		else {
			suffix = ".nzip";
		}
		
		
		String[] extensions;
		if(line.hasOption("e")) {
			extensions = line.getOptionValue("e").split(",");
		}
		else {
			extensions = new String[] {"war","ear","zip","jar"};
		}
		
		String command = line.getOptionValue("c");
		
		if(command.equalsIgnoreCase("list")) {
			List list = new List();
			File file = new File(source);
			if(!file.isFile()) {
				System.err.println("source is not a valid file");
				return;
			}
			list.setSourceFileName(source);
			list.setZipExtensions(extensions);
			list.list();
		}
		else if(command.equalsIgnoreCase("extract")) {
			if(target ==null) {
				System.err.println("please provide target");
				return;
			}
			Extractor extractor = new Extractor();
			extractor.setSourceFileName(source);
			extractor.setDestinationDirectory(target);
			extractor.setZipDirectorySuffix(suffix);
			extractor.setZipExtensions(extensions);
			extractor.extract();
			System.out.println("command has executed successfully");
			
		}
		else if(command.equalsIgnoreCase("compress")) {
			if(target ==null) {
				System.err.println("please provide target");
				return;
			}
			Compressor compressor = new Compressor();
			compressor.setSourceDirectory(source);
			compressor.setDestinationFile(target);
			compressor.setZipDirectorySuffix(suffix);
			compressor.setZipExtensions(extensions);
			compressor.compress();
			System.out.println("command has executed successfully");
		}
		else {
			System.err.println("not a valid command");
		}
	}
	
	
	
}
