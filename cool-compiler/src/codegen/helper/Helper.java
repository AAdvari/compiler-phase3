package codegen.helper;

import codegen.dscp.Descriptor;
import codegen.dscp.PrimitiveDescriptor;
import codegen.dscp.PrimitiveType;

public class Helper {
    public StringBuilder generatedCode;
    public StringBuilder dataCode;


    private int currentAddress;

    public Helper(){
        generatedCode = new StringBuilder();
        dataCode = new StringBuilder();
        currentAddress = -1;
    }

    public String allocateMemory(Descriptor type){
        if(type instanceof PrimitiveDescriptor){
            return allocatePrimitiveMemory((PrimitiveDescriptor) type);
        }


        // TODO : Implement memory allocation for Object and Array Types.
        else
            return null;
    }
    public String allocateConstantMemoryAndSet(String token, PrimitiveType pt){
        // TODO : Implement the fucntion.
        return "";
    }
    private String allocatePrimitiveMemory(PrimitiveDescriptor primitiveDescriptor){
        switch (primitiveDescriptor.type){
            case INTEGER_PRIMITIVE:
                currentAddress++;
                dataCode.append("adr").append(currentAddress).append(": .word 0");
                break;
            case REAL_PRIMITIVE:
                currentAddress++;
                dataCode.append("adr").append(currentAddress).append(": .double 0");
                break;
            case BOOLEAN_PRIMITIVE:
                break;
            default:
                throw new Error("undefined primitive type!");
        }

        return "";
    }

    public void addWhiteSpace(boolean toDataCode){
        if (toDataCode)
            dataCode.append("\n\t\t");
        else
            generatedCode.append("\n\t\t");
    }



}
