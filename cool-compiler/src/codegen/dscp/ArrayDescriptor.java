package codegen.dscp;

public class ArrayDescriptor {
    PrimitiveDescriptor elementType;
    public ArrayDescriptor(PrimitiveDescriptor elementType){
        this.elementType = elementType;
    }

}
