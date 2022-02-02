package codegen.helper;

import codegen.dscp.*;

import java.util.Map;

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
        if (type == null)
            throw new Error("allocation for null descriptor!");

        if(type instanceof PrimitiveDescriptor){
            return allocatePrimitiveMemory((PrimitiveDescriptor) type);
        }
        if (type instanceof ArrayDescriptor) {
            return allocateMemoryForArrays((ArrayDescriptor) type);
        }

        else {
            return null;
        }
    }
    public String allocateConstantMemoryAndSet(String token, PrimitiveType pt){
        // TODO : Implement the function.
        return "";
    }

    private void addWhiteSpace(boolean toDataCode){
        if (toDataCode)
            dataCode.append("\n\t\t");
        else
            generatedCode.append("\n\t\t");
    }
    private String allocateMemoryForArrays(ArrayDescriptor arrayDescriptor){
        if (arrayDescriptor.elementType instanceof PrimitiveDescriptor)
            return allocateMemoryForPrimitiveArrays(arrayDescriptor);
        else if (arrayDescriptor.elementType instanceof ObjectDescriptor)
            return allocateMemoryForObjectiveArrays(arrayDescriptor);
        throw new Error("Invalid Element Type For Array!");
    }
    private String allocateMemoryForPrimitiveArrays(ArrayDescriptor arrayDescriptor){
        return "";
    }
    private String allocateMemoryForObjectiveArrays(ArrayDescriptor arrayDescriptor){
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



}
