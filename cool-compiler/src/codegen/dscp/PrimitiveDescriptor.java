package codegen.dscp;

public class PrimitiveDescriptor extends Descriptor {
    // if address equals to constant, then the symName is the value !
    public PrimitiveType type;
    public String address;
    public PrimitiveDescriptor(String symName, String address, PrimitiveType type){
        super(symName);
        this.type = type;
        this.address = address;
    }
}
