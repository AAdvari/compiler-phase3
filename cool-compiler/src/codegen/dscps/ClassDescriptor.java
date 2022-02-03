package codegen.dscps;

import java.util.Map;
import java.util.TreeMap;

public class ClassDescriptor extends Descriptor {
    public Map<String ,Field> fields;
    public ClassDescriptor(String symName){
        super(symName);
        this.fields = new TreeMap<>(String::compareTo);
    }
}
