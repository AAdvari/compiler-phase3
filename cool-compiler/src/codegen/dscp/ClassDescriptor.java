package codegen.dscp;

import java.util.ArrayList;

public class ClassDescriptor implements Descriptor {
    public ArrayList<Descriptor> fields;
    public ClassDescriptor(){
        this.fields = new ArrayList<>();
    }
}
