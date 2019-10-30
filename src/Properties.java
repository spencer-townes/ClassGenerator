
public class Properties {
	
	//======================================================= Properties
	
	private String fieldName;
	private String dataType;
	
	//======================================================= Constructors
	
	public Properties(String fieldName, String dataType) {
		setFieldName(fieldName);
		setDataType(dataType);
	}
	
	
	//======================================================= Methods
	
	public String generateProperty() {
		return String.format("\tprivate %s %s;", dataType, fieldName);
	}
	
	public String generateGetter() {
		return String.format("\tpublic %s get%s() \t\t{\treturn %s;\t}", dataType, upperCase(fieldName), fieldName);
	}
	
	public String generateSetter() {
		return String.format("\tpublic %s set%s(%s %s) \t\t{\tthis.%s = %s;\t}", "void", upperCase(fieldName), dataType, fieldName, fieldName, fieldName);
	}
	
	public String generateSetCall(Properties p) {
		return String.format("\t\tset%s(%s);", upperCase(fieldName), p.getFieldName());
	}
	public String generateSetCall(String s) {
		return String.format("\t\tset%s(%s);", upperCase(fieldName), s);
	}
	
	public String generateUpperCase() {
		return String.format("%s", upperCase(fieldName));
	}
	
	private String lowerCase(String s) {
		return s.substring(0, 1).toLowerCase() + s.substring(1);
	}

	private String upperCase(String s) {
		return s.substring(0, 1).toUpperCase() + s.substring(1);
	}
	
	
	
	//======================================================= Getters/Setters

	public String getFieldName() 	{	return fieldName;	}
	public String getDataType() 	{	return dataType;	}
	public void setFieldName(String fieldName) 	{	this.fieldName = lowerCase(fieldName);	}
	public void setDataType(String dataType) 	{	this.dataType = dataType;	}
	
}
