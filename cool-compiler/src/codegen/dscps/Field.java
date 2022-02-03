package codegen.dscps;

public class Field {
    public Descriptor descriptor;
    public String name;
    public FieldType fieldType;
    public Field(Descriptor descriptor, FieldType fieldType, String name){
        this.descriptor = descriptor;
        this.fieldType = fieldType;
        this.name = name;
    }
}
