# nzip
This is a java based utilty to handle nested jars.It has three main functionalities
1. List- lists all the files in a nested directory
2. Extract - extracts a nested directory
3. Compress - compresses an extracted directory back to a nested jar


Here is a list of command line switches

usage: nzip
 -c <arg>   required please enter the command name - list,extract or
            compress
 -e <arg>   please enter the zip extensions separated by comma
 -h         help
 -s <arg>   required please enter source directory or file
 -t <arg>   please enter target file or directory
 -u <arg>   please enter the suffix for extracting zip directories


When extracting a nested zip, a suffix is added to each zip directory. This is required while compressing the extracted directory 
back again. If the flag 'u' is left out, default value of '.nzip' is added to each extracted zip directory.
The 'e' flag specifies the extensions that will be considered as zip directories. default is "war,ear,jar,zip".


Examples


java -jar nzip.jar -c list -s readme.zip optional -e zip,war

java -jar nzip.jar -c extract -s "C:\project\readme.zip" -t readme optional -e zip,war -u .nz

java -jar nzip.jar -c compress -s readme -t "C:\project\readme.zip" optional -e zip -u .nz
