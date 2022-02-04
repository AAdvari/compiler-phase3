package codegen.helper;
import codegen.CodeGenerator;
import codegen.CodeGeneratorImpl;
import codegen.dscp.*;

public class Helper {
    public StringBuilder generatedCode;
    public StringBuilder dataCode;



    private CodeGeneratorImpl codeGen;
    private int currentAddress;
    private int currentLabel = 0;

    public Helper(CodeGeneratorImpl codeGen){
        generatedCode = new StringBuilder();
        dataCode = new StringBuilder();
        currentAddress = -1;
        this.codeGen = codeGen;
        initDataCode();
        initGeneratedCode();
    }
    private void initDataCode(){
        dataCode.append(".data");
        addWhiteSpace(true);
        dataCode.append("inputBuffer: .space 20");
        addWhiteSpace(true);
        dataCode.append("outOfBoundException: .asciiz \"Array Index Out Of Bounds\"");
        addWhiteSpace(true);

    }
    private void initGeneratedCode(){
        generatedCode.append(".text\n.globl main\nmain:");
        addWhiteSpace(false);
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
            throw new Error("Memory Allocation for this kind of descriptor is not supported.");
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
            case STRING_PRIMITIVE:
                currentAddress++;
                if (value.length == 0)
                    dataCode.append("adr").append(currentAddress).append(": .space 20");
                else if (value.length == 1)
                    dataCode.append("adr").append(currentAddress).append(": .asciiz ").append("\"").append(value[0]).append("\"");
                else
                    throw new Error("Array for strings not supported yet");
                return "adr"+currentAddress;

            default:
                throw new Error("undefined primitive type!");
        }

        return "adr"+currentAddress;
    }
    private String allocateObjectMemory(ObjectDescriptor objectDescriptor, int count, String... value){
        return "";
    }

    // Assignments :
    public PrimitiveDescriptor generateReadString(){
        PrimitiveDescriptor pd = new PrimitiveDescriptor("$temp", "", PrimitiveType.STRING_PRIMITIVE);
        String allocatedMemoryAddress =  allocateMemory(pd);
        writeComment(false,"Reading String");
        writeCommand("li", "$v0", "8" );
        writeCommand("la", "$a0", allocatedMemoryAddress);
        writeCommand("li", "$a1", "20");
        writeCommand("syscall");
        pd.setAddress(allocatedMemoryAddress);
        return pd;
    }
    public PrimitiveDescriptor generateReadInt(){
        PrimitiveDescriptor pd = new PrimitiveDescriptor("$temp","",PrimitiveType.INTEGER_PRIMITIVE);
        String allocatedMemoryAddress = allocateMemory(pd);
        writeComment(false, "Reading Integer");
        writeCommand("li","$v0","5");
        writeCommand("syscall");
        writeCommand("sw", "$v0", allocatedMemoryAddress);
        pd.setAddress(allocatedMemoryAddress);
        return pd;
    }
    public PrimitiveDescriptor generateReadReal(){
        PrimitiveDescriptor pd = new PrimitiveDescriptor("$temp", "", PrimitiveType.REAL_PRIMITIVE);
        String allocatedMemoryAddress = allocateMemory(pd);
        writeComment(false,"Reading Real");
        writeCommand("li","$v0","6");
        writeCommand("syscall");
        writeCommand("s.s", "$f0", allocatedMemoryAddress);
        pd.setAddress(allocatedMemoryAddress);
        return pd;
    }
    public void assignAddressLabelsValues(String adr1, String adr2, PrimitiveType type){
        writeComment(false,"# Assigning "+ adr2 + "to"+ adr1 + "Type: " + type);
        if (type == PrimitiveType.INTEGER_PRIMITIVE || type == PrimitiveType.BOOLEAN_PRIMITIVE){
            writeCommand("lw","$t6",adr2);
            writeCommand("sw","$t6",adr1);
        }
        else if (type == PrimitiveType.REAL_PRIMITIVE){
            writeCommand("l.s","$f6",adr2);
            writeCommand("s.s","$f6",adr1);
        }
        else
            throw new Error("Invalid descriptor type!");
    }


    // utils :
    public void writeCommand(String...literals){
        generatedCode.append(literals[0]).append(" ");
        for (int i = 1; i < literals.length ; i++) {
            generatedCode.append(",").append(literals[i]);
        }
        addWhiteSpace(false);
    }
    public void writeComment(boolean toDataCode, String comment){
        if (toDataCode){
            dataCode.append("#").append(comment);
            addWhiteSpace(true);
        }
        else {
            generatedCode.append("#").append(comment);
            addWhiteSpace(false);
        }
    }
    public void addWhiteSpace(boolean toDataCode){
        if (toDataCode)
            dataCode.append("\n\t\t");
        else
            generatedCode.append("\n\t\t");
    }


    public String allocateConstantMemoryAndSet(String token, PrimitiveType constantType) {
        return null;
    }
    public String labelMaker() {
        String s = "label" + currentLabel;
        currentLabel++;
        return  s;
    }
}
