package codegen.dscp;

public class PrimitiveDescriptor extends Descriptor {
    public PrimitiveType type;
    public PrimitiveDescriptor(String symName, PrimitiveType type){
        super(symName);
        this.type = type;
    }
}
