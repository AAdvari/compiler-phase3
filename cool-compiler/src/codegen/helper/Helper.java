package codegen.helper;
import codegen.dscp.*;

public class Helper {
    public StringBuilder generatedCode;
    public StringBuilder dataCode;


    private int currentAddress;

    public Helper(){
        generatedCode = new StringBuilder();
        dataCode = new StringBuilder();
        currentAddress = -1;
    }
    // Allocations :
    public String allocateMemory(Descriptor type, String... value){
        if (type == null)
            throw new Error("allocation for null descriptor!");

        if(type instanceof PrimitiveDescriptor){
            return allocatePrimitiveMemory((PrimitiveDescriptor) type, 1, value);
        }
        if (type instanceof ArrayDescriptor) {
            ArrayDescriptor ad = (ArrayDescriptor) type;
            if (ad.getElementType() instanceof PrimitiveDescriptor)
                return allocatePrimitiveMemory((PrimitiveDescriptor) ad.getElementType(), ad.getSize(), value);
            else
                return allocateObjectMemory((ObjectDescriptor) ad.getElementType() , ad.getSize(), value);
        }

        else {
            return null;
        }
    }
    private String allocatePrimitiveMemory(PrimitiveDescriptor primitiveDescriptor, int count, String... value){
        switch (primitiveDescriptor.type){
            case INTEGER_PRIMITIVE:
            case BOOLEAN_PRIMITIVE:
                currentAddress++;
                dataCode.append("adr").append(currentAddress).append(": .word ").append(value[0]);
                for (int i = 1; i < count; i++) {
                    dataCode.append(",").append(value[i]);
                }
                addWhiteSpace(true);
                break;
            case REAL_PRIMITIVE:
                currentAddress++;
                dataCode.append("adr").append(currentAddress).append(": .float ").append(value[0]);
                for (int i = 0; i < count - 1; i++) {
                    dataCode.append(",").append(value[i]);
                }
                addWhiteSpace(true);
                break;
            default:
                throw new Error("undefined primitive type!");
        }

        return "adr"+currentAddress;
    }
    private String allocateObjectMemory(ObjectDescriptor objectDescriptor, int count, String... value){
        return "";
    }

    // Assignments :

    public void assignSecondToFirst(){}



    // utils :
    private void addWhiteSpace(boolean toDataCode){
        if (toDataCode)
            dataCode.append("\n\t\t");
        else
            generatedCode.append("\n\t\t");
    }


}
