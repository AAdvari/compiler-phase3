package codegen.dscp;
import java.util.ArrayList;

public class ObjectDescriptor extends Descriptor{
    public ArrayList<Descriptor> attributes;
    public ClassDescriptor relatedClass;
    public ObjectDescriptor(String symName){
        super(symName);
        this.attributes = new ArrayList<>();
    }
}
