package codegen.dscps;
import java.util.Map;
import java.util.TreeMap;

public class ObjectDescriptor extends Descriptor{
    public Map<String, Descriptor> attributes;
    public ClassDescriptor relatedClass;
    public ObjectDescriptor(String symName, ClassDescriptor relatedClass){
        super(symName);
        this.attributes = new TreeMap<>(String::compareTo);
        this.relatedClass = relatedClass;
    }
}
