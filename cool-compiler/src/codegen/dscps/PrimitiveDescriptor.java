package codegen.dscps;

public class PrimitiveDescriptor extends Descriptor {
    public PrimitiveType type;
    public String address;
    private boolean isConstant;
    public PrimitiveDescriptor(String symName, String address, PrimitiveType type){
        super(symName);
        this.type = type;
        this.address = address;
        isConstant = false;
    }

    public String getAddress() {
        return address;
    }
    public void activeIsConstant(){
       isConstant = true;
    }
    public boolean isConstant(){
        return isConstant;
    }
    public void setAddress(String address) {
        this.address = address;
    }
}
