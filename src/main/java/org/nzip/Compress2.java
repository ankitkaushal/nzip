package org.nzip;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.IOFileFilter;
import org.apache.commons.io.filefilter.TrueFileFilter;


import lombok.Data;

@Data
public class Compress2 {

	private String sourceDirectory;
	private String destinationFile;
	private String[] zipExtensions;
	private Map<String,byte[]> streamsMap = new HashMap<String,byte[]>();
	
	public void compress() {
		
		File file = new File(destinationFile);
		FileOutputStream fos =null;
		try {
			fos = new FileOutputStream(file);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			System.out.println("cannot write to file: "+destinationFile);
		}
		compressRecursive(sourceDirectory, fos);
		try {
			fos.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("cannot close stream: "+ destinationFile);
		}
	}
	
	private void compressRecursive(String sourceDir,OutputStream destinationStream) {
		
		Collection<File> files = FileUtils.listFiles(new File(sourceDir), TrueFileFilter.INSTANCE,new IOFileFilter() {

			@Override
			public boolean accept(File file) {
				// TODO Auto-generated method stub
				String name = file.getName();
				System.out.println(name);
				if(name.endsWith(".nzip"))
					return false;
				else
					return true;
				
			}

			@Override
			public boolean accept(File dir, String name) {
				// TODO Auto-generated method stub
				return false;
			}});
		
		
		
		IOFileFilter noFiles = new IOFileFilter() {

			@Override
			public boolean accept(File file) {
				// TODO Auto-generated method stub
				return false;
			}

			@Override
			public boolean accept(File dir, String name) {
				// TODO Auto-generated method stub
				return false;
			}
			
		};
		
		IOFileFilter nestedDirs = new IOFileFilter() {

			@Override
			public boolean accept(File file) {
				// TODO Auto-generated method stub
				if(file.getAbsolutePath().contains(".nzip"))
					return true;
				else
					return false;
			}

			@Override
			public boolean accept(File dir, String name) {
				// TODO Auto-generated method stub
				return false;
			}
			
		};
		Collection<File> nestedZips=FileUtils.listFilesAndDirs(new File(sourceDir), noFiles, nestedDirs);
		nestedZips.remove(new File(sourceDir));
		
//		File[] nestedZips =new File(sourceDir).listFiles(new FileFilter() {
//
//			@Override
//			public boolean accept(File pathname) {
//				// TODO Auto-generated method stub
//				if(pathname.isDirectory()&& pathname.getName().endsWith(".nzip"))
//					return true;
//				else
//					return false;
//			}
//			
//		});
		
		for(File file:nestedZips) {
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			compressRecursive(file.getAbsolutePath(),bos);
			String relativePath = new File(sourceDir).toURI().relativize(file.toURI()).getPath();
			relativePath=relativePath.replace(".nzip","");
			streamsMap.put(relativePath,bos.toByteArray());
			
		}
		
		ZipOutputStream zOs = new ZipOutputStream(destinationStream);
		for(File file:files) {
			String relativePath = new File(sourceDir).toURI().relativize(file.toURI()).getPath();
			ZipEntry zipEntry = new ZipEntry(relativePath);
			try {
				zOs.putNextEntry(zipEntry);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				System.out.println("cannot write to file: "+destinationFile);
			}
			
			byte[] buffer = new byte[256];
			FileInputStream fin = null;
			try {
				fin = new FileInputStream(file);
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				System.out.println("cannot read file: " +file.getAbsolutePath());
			}
			int count;
			try {
				while ((count = fin.read(buffer)) != -1) {
					zOs.write(buffer, 0, count);
				}
				zOs.closeEntry();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				System.out.println("cannot write to file: "+destinationFile);
			}
		}
		
		for(File nFile:nestedZips) {
			String relativePath = new File(sourceDir).toURI().relativize(nFile.toURI()).getPath();
			relativePath=relativePath.replace(".nzip","");
			ZipEntry zipEntry = new ZipEntry(relativePath.substring(0, relativePath.length()-1));
			
			try {
				zOs.putNextEntry(zipEntry);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				System.out.println("cannot write to file: "+destinationFile);
			}
			ByteArrayInputStream bis = new ByteArrayInputStream(streamsMap.get(relativePath));
			byte[] buffer = new byte[256];
			
			int count;
			
				try {
					while ((count = bis.read(buffer)) != -1) {
						zOs.write(buffer, 0, count);
					}
					zOs.closeEntry();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					System.out.println("cannot write to file: "+destinationFile);
				}
				
				
			
		}
		
			try {
				zOs.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				System.out.println("cannot close stream: "+ destinationFile);
			}
			
		
	}
	

	
	
}
