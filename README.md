# uncertainCEP

This file gives instructions about how to run the source code in this repository. For more information about the description of the case studies, we refer the reader to our  website (http://atenea.lcc.uma.es/projects/UCEP.html).

# Requirements/Dependencies

- Eclipse IDE (tested with Eclipse Oxygen.2).
- Java 8

# Case Studies

## Running the Smart House Case Study

In order to run the case study, the reader has to follow the following steps:

1. Import Java projects into a workspace
In order to execute the case study with uncertainty:
2.a. Move to the project: com.ucep.uncertainty.smarthouse
3.a. Move to the package: /src/main/java/com/cor/cep/
4.a. Run the Java file: Main.java. Note that the Java Heap Space might need to be increased with the parameter -Xmx (e.g., -Xmx10G).
In order to execute the case study without uncertainty:
2.b. Move to the project: com.ucep.nouncertainty.smarthouse
3.b. Move to the package: /src/main/java/com/cor/cep/
4.b. Run the Java file: Main.java. Note that the Java Heap Space might need to be increased with the parameter -Xmx (e.g., -Xmx10G).

The input datasets whose size is less than 100Mb are in the folder "inputEvents", note that each dataset correspond to a CSV file. Due to the GitHub's file size limit, those files whose size is 100Mb or more can be downloaded from our website (http://atenea.lcc.uma.es/projects/UCEP.html).

In order to change the input dataset, the reader has to:

1. Move to the package: /src/main/java/com/cor/cep/util/
2. In the file: EventGenerator.java, find the line in which the FileReader is instanciated (i.e., new FileReader("<file_name>");) and change <file_name> to the path where the file with the dataset is.

## Running the Marathon Case Study

Idem as the Smart House case study but:
2.a. Move to the project: com.ucep.uncertainty.marathon
2.b. Move to the project: com.ucep.nouncertainty.marathon

## Running the Motorbike Case Study

Idem as the Smart House case study but:
2.a. Move to the project: com.ucep.uncertainty.motorbike
2.b. Move to the project: com.ucep.nouncertainty.motorbike

## Running the Air Quality Case Study

Idem as the Smart House case study but:
2.a. Move to the project: com.ucep.uncertainty.airquality
2.b. Move to the project: com.ucep.nouncertainty.airquality

## Tunnel Ventilation System Case Study

Idem as the Smart House case study but:
2.a. Move to the project: com.ucep.uncertainty.tunnel
2.b. Move to the project: com.ucep.nouncertainty.tunnel
