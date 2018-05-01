package org.nzip;

import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import org.apache.commons.io.FileUtils;

import lombok.Data;

@Data
public class List {
	private String[] zipExtensions;
	private String sourceFileName;
	
	
	public void list() {
		FileInputStream fis = null;
		try {
			fis = new FileInputStream(new File(sourceFileName));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			System.out.println("Sorry, cannot find the file: " +sourceFileName);
		}
		list(fis,"");
		try {
			fis.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("cannot close file: "+ sourceFileName);
		}
	}
	
	private void list(InputStream is,String destinationDir) {
		
		ZipInputStream zin= new ZipInputStream(is);;
		
		ZipEntry zEn;
		try {
			while((zEn=zin.getNextEntry())!=null) {
				if(!zEn.isDirectory()) {
				String destinationFile = destinationDir+File.separator+zEn;
				outputFile(destinationFile);
				if(isZip(zEn.getName())) {
						ByteArrayOutputStream bos = new ByteArrayOutputStream();
						byte[] bytesIn = new byte[256];
						int read = 0;
						try {
							while ((read = zin.read(bytesIn)) != -1) {
								bos.write(bytesIn, 0, read);
							}
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						
						ByteArrayInputStream bis = new ByteArrayInputStream(bos.toByteArray());
						list(bis, destinationFile);
				 	}
				}
				
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("Sorry, following is not a valid zip file: " + sourceFileName);
		}
		finally {
			try {
				zin.close();
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				System.out.println("cannot close file stream:" +sourceFileName);
			}
		}
		
		
		
	}
	private boolean isZip(String zipFile) {
		// TODO Auto-generated method stub
		for (int i = 0; i < zipExtensions.length; i++) {
			if (zipFile.endsWith(zipExtensions[i])) {
				return true;
			}
		}
		return false;
	}

	private void outputFile(String filePath) {
		System.out.println(filePath.replaceAll("\\", "/"));
		
	}
	
}
