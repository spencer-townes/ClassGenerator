import java.io.File;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class ClassCreator {

	//======================================================= Properties

	ArrayList<Properties> propertyList = new ArrayList<Properties>();

	//Text file that you reading in
	String fileName = "Students.txt";

	//^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
	//Change this to the txt file you are using!

	//======================================================= Constructors

	public ClassCreator(){


		Scanner fin = null;

		try {
			String[] nameHandler = fileName.split("\\.");

			String className = nameHandler[0].replaceAll(" |_|\\s$|0|1|2|3|4|5|6|7|8|9", "");


			//Setting Up Scanners/PrintWriter
			fin = new Scanner(new File(fileName));


			PrintWriter pw = new PrintWriter(className + ".java");


			String[] prop = fin.nextLine().split("\t");
			String[] data = fin.nextLine().split("\t");

			for(int i = 0; i<prop.length; i++) {
				propertyList.add(new Properties(prop[i].replaceAll("\\s", ""), getDataType(data[i])));
			}

			//Imports
			pw.println("import java.util.Scanner;\n\n");
			
			//Class Line
			pw.println("\npublic class " + className + " {");

			//Properties
			pw.println("\n\t//======================================================= Properties\n");

			for(Properties p: propertyList) {
				pw.println(p.generateProperty());
			}

			//Constructors
			pw.println("\n\t//======================================================= Constructors\n");

			//================= Workhorse Constructor
			pw.print("\tpublic " + className + "(");
			for(int i=0; i<propertyList.size()-1; i++) {
				pw.print(propertyList.get(i).getDataType() + " " + propertyList.get(i).getFieldName() + ", ");
			}
			pw.print(propertyList.get(propertyList.size()-1).getDataType() + " " 
					+ propertyList.get(propertyList.size()-1).getFieldName() + "){\n");
			for(Properties p: propertyList) {
				pw.println(p.generateSetCall(p));
			}
			pw.println("\t}\n");

			//================= Scanner Constructor
			pw.print("\tpublic " + className + "(Scanner fin) throws Exception {\n");
			pw.println("\t\tString[] parts = fin.nextLine().split(\"\\t\");");
			int arrayCount = 0;
			for(Properties p: propertyList) {
				if(p.getDataType().equals("int")) {
					pw.println(p.generateSetCall("Integer.parseInt(parts[" + arrayCount + "])"));
					arrayCount++;
				}
				else if(p.getDataType().equals("double")) {
					pw.println(p.generateSetCall("Double.parseDouble(parts[" + arrayCount + "])"));
					arrayCount++;
				}
				else if(p.getDataType().equals("long")) {
					pw.println(p.generateSetCall("Long.parseLong(parts[" + arrayCount + "])"));
					arrayCount++;
				}
				else if(p.getDataType().equals("boolean")) {
					pw.println(p.generateSetCall("Boolean.parseBoolean(parts[" + arrayCount + "])"));
					arrayCount++;
				}
				else {
					pw.println(p.generateSetCall("parts[" + arrayCount + "]"));
					arrayCount++;
				}

			}
			pw.println("\n\t}");

			//Methods
			pw.println("\n\t//======================================================= Methods\n");

				//Equals
			pw.println("\tpublic boolean equals(Object obj) {");
			pw.println("\t\tif(!(obj instanceof " + className + ")) return false;");
			pw.println("\t\t" + className + " " + className.substring(0,1).toLowerCase() + " = (" + className + ")obj;");
			pw.println("\t\treturn getEqualsString().equals(" + className.substring(0,1).toLowerCase() + ".getEqualsString());");
			pw.println("\t}");
			
				//EqualsHelper
			pw.println("\n\tprivate String getEqualsString() {");
			pw.print("\t\treturn ");
			for(int i=0; i<propertyList.size()-1; i++) {
				pw.print(propertyList.get(i).getFieldName() + " + \"~\" + ");
			}
			pw.print(propertyList.get(propertyList.size()-1).getFieldName() + ";\n");
			pw.println("\t}");
			
				//toString
			pw.println("\n\tpublic String toString() {");
			pw.print("\t\treturn \"");
			for(int i=0; i<propertyList.size()-1; i++) {
				pw.print(propertyList.get(i).generateUpperCase() + ": \" + " +  propertyList.get(i).getFieldName() + " + \", ");
			}
			pw.print(propertyList.get(propertyList.size()-1).generateUpperCase() + ": \" + " +  propertyList.get(propertyList.size()-1).getFieldName() + ";\n");
			pw.println("\t}");
			
			
			//Getters/Setters
			pw.println("\n\t//======================================================= Getters/Setters\n");
			for(Properties p: propertyList) {
				pw.println(p.generateGetter());
			}
			pw.println();
			for(Properties p: propertyList) {
				pw.println(p.generateSetter());
			}

			//End of the file code
			pw.print("\n\n}");
			fin.close();
			pw.close();



		} catch (Exception e) {
			System.out.println("Error: " + e.getMessage());
		} finally { //Checked Last after try
			try {fin.close();} catch (Exception e) {} //Just Double Checking

		}




	}


	//======================================================= Methods

	public static String getDataType(String val) {

		try {
			Integer.parseInt(val); return "int";
		} catch (Exception e) {}

		try {
			Long.parseLong(val); return "long";
		} catch (Exception e) {}

		try {
			Double.parseDouble(val); return "double";
		} catch (Exception e) {}

		try {
			if( val.equalsIgnoreCase("True") || val.equalsIgnoreCase("False")) return "boolean";
		} catch (Exception e) {}

		return "String";
	}


	//======================================================= Main

	public static void main(String[] args){

		new ClassCreator();

	}


}
