package codegen.dscp;

import java.util.ArrayList;

public class ObjectDescriptor implements Descriptor{
    public ArrayList<Descriptor> attributes;
    public ClassDescriptor relatedClass;
    public ObjectDescriptor(){
        this.attributes = new ArrayList<>();
    }
}
