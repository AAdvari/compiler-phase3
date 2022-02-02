package codegen.dscp;

public class ArrayDescriptor extends Descriptor{
    public Descriptor elementType;
    private String startAddress;
    private int elementSize;
    public ArrayDescriptor(String symName, Descriptor elementType){
        super(symName);
        if (!(elementType instanceof PrimitiveDescriptor))
            throw new Error("Creating Array of non-primitives are not supported yet");

        this.elementType = elementType;
    }

    public String getStartAddress() {
        return startAddress;
    }

    public void setStartAddress(String startAddress) {
        this.startAddress = startAddress;
    }

    public int getElementSize() {
        return elementSize;
    }

    public void setElementSize(int elementSize) {
        this.elementSize = elementSize;
    }
}
