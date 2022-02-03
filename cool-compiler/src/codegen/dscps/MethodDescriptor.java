package codegen.dscps;

import java.util.Map;
import java.util.TreeMap;

public class MethodDescriptor extends Descriptor{
    public Map<String, Descriptor> symTable;
    public ClassDescriptor parentClass;
    public MethodDescriptor(String symName, ClassDescriptor parent){
        super(symName);
        this.symTable = new TreeMap<>(String::compareTo);
        this.parentClass = parent;
    }

    public void addVariable(String name, Descriptor descriptor){
        this.symTable.put(name, descriptor);
    }
}
