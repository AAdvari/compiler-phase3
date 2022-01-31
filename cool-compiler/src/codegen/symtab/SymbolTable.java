package codegen.symtab;

import codegen.dscp.Descriptor;

import java.util.Map;

public class SymbolTable {
    public String environmentName;
    public Map<String, Descriptor> vars;
    public SymbolTable(String envName){
        this.environmentName = envName;
    }
}
