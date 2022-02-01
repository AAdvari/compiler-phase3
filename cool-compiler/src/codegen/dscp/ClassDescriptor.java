package codegen.dscp;

import java.util.ArrayList;

public class ClassDescriptor extends Descriptor {
    public ArrayList<Descriptor> fields;
    public ClassDescriptor(String symName){
        super(symName);
        this.fields = new ArrayList<>();
    }
}
