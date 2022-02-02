package codegen.dscp;

public class ArrayDescriptor extends Descriptor{
    public Descriptor elementType;
    private String startAddress;
    private int size;
    public ArrayDescriptor(String symName, Descriptor elementType){
        super(symName);
        if (!(elementType instanceof PrimitiveDescriptor))
            throw new Error("Creating Array of non-primitives are not supported yet");
        startAddress = "";
        size = -1;
        this.elementType = elementType;
    }

    public String getStartAddress() {
        return startAddress;
    }

    public void setStartAddress(String startAddress) {
        this.startAddress = startAddress;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public Descriptor getElementType() {
        return elementType;
    }

    public void setElementType(Descriptor elementType) {
        this.elementType = elementType;
    }
}
