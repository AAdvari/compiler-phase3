package codegen.dscp;

public class PrimitiveDescriptor extends Descriptor {
    public PrimitiveType type;
    public String address;
    public PrimitiveDescriptor(String symName, String address, PrimitiveType type){
        super(symName);
        this.type = type;
        this.address = address;
    }
}
