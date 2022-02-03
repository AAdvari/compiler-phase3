package codegen.dscp;

import com.sun.org.apache.bcel.internal.generic.FADD;

public class PrimitiveDescriptor extends Descriptor {
    // if address equals to constant, then the symName is the value !
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
