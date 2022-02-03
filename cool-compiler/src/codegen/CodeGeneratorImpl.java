package codegen;

import codegen.dscps.*;
import codegen.helper.Helper;
import scanner.LexicalAnalyser;
import scanner.TokenType;
import scanner.Symbol;

import java.util.*;

public class CodeGeneratorImpl implements CodeGenerator {

    public LexicalAnalyser scanner;
    public Stack<Descriptor> semanticStack;
    public Helper helper;
    public ClassDescriptor currentClass;
    public MethodDescriptor currentMethod;
    public Map<String, Descriptor> globalDescriptors;
    public ArrayList<Descriptor> objectDescriptors;


    public CodeGeneratorImpl(LexicalAnalyser scanner) {
        this.scanner = scanner;
        semanticStack = new Stack<>();
        helper = new Helper(this);
        globalDescriptors = new TreeMap<>(String::compareTo);
        seedGlobalDescriptors();
        // TODO : Added for testing purposes : remove after implementing methods
        currentMethod = new MethodDescriptor("main", new ClassDescriptor("Main"));
    }
    public StringBuilder generateCode(){
        return helper.generatedCode.append('\n').append(helper.dataCode);
    }
    private void seedGlobalDescriptors(){
        PrimitiveDescriptor real = new PrimitiveDescriptor("real", "", PrimitiveType.REAL_PRIMITIVE);
        PrimitiveDescriptor integer = new PrimitiveDescriptor("int", "", PrimitiveType.INTEGER_PRIMITIVE);
        PrimitiveDescriptor bool = new PrimitiveDescriptor("bool", "", PrimitiveType.BOOLEAN_PRIMITIVE);
        PrimitiveDescriptor string = new PrimitiveDescriptor("string", "", PrimitiveType.STRING_PRIMITIVE);
        globalDescriptors.put("real",real);
        globalDescriptors.put("int",integer);
        globalDescriptors.put("bool",bool);
        globalDescriptors.put("string",string);
        globalDescriptors.put("void", null);
    }

    @Override
    public void doSemantic(String sem) {
        switch (sem) {
            case "push":
                push();
                break;
            case "add":
                add();
                break;
            case "subtract":
                subtract();
                break;
            case "multiply":
            case "divide":
            case "mod":
            case "minus_minus":
            case "plus_plus":
            case "bitwise_and":
            case "bitwise_or":
            case "bitwise_xor":
            case "and":
            case "or":
            case "not":
            case "bigger_than":
            case "bigger_than_equal":
            case "less_than":
            case "less_than_equal":
            case "equality":
            case "inequality":
            case "read_string":
                read_string();
                break;
            case "read_int":
                read_int();
                break;
            case "read_real":
                read_real();
                break;
            case "print_string":
                print_string();
                break;
            case "print_real":
                print_real();
                break;
            case "print_int":
                print_int();
                break;
            case "left_array_index":
                left_array_index();
                break;
            case "array_index":
                array_index();
                break;
            case "trace_object":
                traceObject();
                break;
            case "pop":
                semanticStack.pop();
                break;
            case "var_dcl":
                declareVariable();
                break;
            case "array_dcl":
                declareArray();
                break;
            case "array_dcl_complete":
                arrayDclComplete();
                break;
            case "assign":
                assign();
                break;
            case "cast":
                cast();
                break;
        }
    }



    // Regular Semantics:
    private void traceObject() {
    }
    private void declareVariable() {
        Descriptor descriptor = semanticStack.peek();
        // TODO : Commented for testing purposes : uncomment after implementing methods.
//        if (currentMethod == null) {
//            addFieldToClass(descriptor);
//            return;
//        }
        if (descriptor instanceof PrimitiveDescriptor) {
            declarePrimitiveVariable((PrimitiveDescriptor) descriptor);
            return;
        }
        if (descriptor instanceof ObjectDescriptor) {
            declareObject((ObjectDescriptor) descriptor);
            return;
        }

        throw new Error("Variable can't be instantiated");

    }
    private void declarePrimitiveVariable(PrimitiveDescriptor pd) {
        Symbol currentSymbol = scanner.currentSymbol;
        String token = currentSymbol.getToken();

        if (globalDescriptors.containsKey(token)
        || currentMethod.symTable.containsKey(token))
            throw new Error("Identifier " + token + " has been declared before!");

        String type = stringTypeOfPrimitiveType(pd.type);

        String address = helper.allocateMemory(globalDescriptors.get(type), "0");
        PrimitiveDescriptor creatingVarDescriptor =
                new PrimitiveDescriptor(token, address, pd.type);
        currentMethod.addVariable(token, creatingVarDescriptor);
    }

    /** We cannot specify memory for arrays in declaration,
       allocating memory is done in assignment section for arrays.

     */
    private void declareArray() {
        Descriptor arrayElementTypeDescriptor = semanticStack.peek();
        String creatingArrayName = scanner.currentSymbol.getToken();
        if (currentMethod == null) {
            addFieldToClass(arrayElementTypeDescriptor);
            return;
        }

        if (isDeclaredToken(creatingArrayName))
            throw new Error("Identifier is declared before!");

        ArrayDescriptor arrayDescriptor = new ArrayDescriptor(creatingArrayName,arrayElementTypeDescriptor);
        currentMethod.addVariable(creatingArrayName, arrayDescriptor);
    }
    private void declareObject(ObjectDescriptor od) {

    }
    private void addFieldToClass(Descriptor descriptor) {

    }
    private void addArrayFieldToClass(Descriptor elementTypeDescriptor){

    }
    private void push() {
        String symName = scanner.currentSymbol.getToken();
        TokenType tokenType = scanner.currentSymbol.getType();

        if (constantTypes.contains(tokenType)) {
            push_constant(symName, tokenType);
            return;
        }

        if (currentMethod.symTable.containsKey(symName)) {
            Descriptor descriptor = currentMethod.symTable.get(symName);
            semanticStack.push(descriptor);

        } else if(globalDescriptors.containsKey(symName)){
            Descriptor descriptor = globalDescriptors.get(symName);
            semanticStack.push(descriptor);
        }
        else if (currentClass.fields.containsKey(symName)) {
            System.out.println("Pushing Class Fields is not implemented yet");

        } else
            throw new Error("Literal"+ symName + " is not declared within current scope!");
    }
    private void push_constant(String token, TokenType type) {
        System.out.println("Push Constant Called: " + token);
        if (globalDescriptors.containsKey(token)) {
            semanticStack.push(globalDescriptors.get(token));
            return;
        }

        PrimitiveType constantType;
        switch (type) {
            case INTEGER:
                constantType = PrimitiveType.INTEGER_PRIMITIVE;
                break;
            case REAL:
                constantType = PrimitiveType.REAL_PRIMITIVE;
                break;
            case STRING:
                constantType = PrimitiveType.STRING_PRIMITIVE;
                break;
            default:
                throw new Error("Not a valid constant type");
        }
        PrimitiveDescriptor pd = new PrimitiveDescriptor(token, "", constantType);
        pd.address = helper.allocateMemory(pd, token);
        pd.activeIsConstant();

        globalDescriptors.put(token, pd);
        semanticStack.push(pd);
    }
    private void read_int(){
        semanticStack.push(helper.generateReadInt());
    }
    private void read_string(){
        semanticStack.push(helper.generateReadString());
    }
    private void read_real(){
        semanticStack.push(helper.generateReadReal());
    }

    private void print_string(){
        PrimitiveDescriptor pd = (PrimitiveDescriptor) semanticStack.pop();
        if (pd.type != PrimitiveType.STRING_PRIMITIVE)
            throw new Error("Expected String but got "+ pd.type);
        helper.writeComment(false,"Printing " + pd.symName );
        helper.writeCommand("li","$v0","4");
        helper.writeCommand("la","$a0",pd.getAddress());
        helper.writeCommand("syscall");

    }
    private void print_real(){
        PrimitiveDescriptor pd = (PrimitiveDescriptor) semanticStack.pop();
        if (pd.type != PrimitiveType.REAL_PRIMITIVE)
            throw new Error("Expected Real type but got "+ pd.type);
        helper.writeComment(false,"Printing " + pd.symName );
        helper.writeCommand("li","$v0","2");
        helper.writeCommand("l.s","$f12",pd.getAddress());
        helper.writeCommand("syscall");
    }
    private void print_int(){
        PrimitiveDescriptor pd = (PrimitiveDescriptor) semanticStack.pop();
        if (pd.type != PrimitiveType.REAL_PRIMITIVE)
            throw new Error("Expected String but got "+ pd.type);
        helper.writeComment(false,"Printing " + pd.symName );
        helper.writeCommand("li","$v0","1");
        helper.writeCommand("lw","$a0",pd.getAddress());
        helper.writeCommand("syscall");
    }

    private void array_index(){
        Descriptor index = semanticStack.pop();
        Descriptor array = semanticStack.pop();

        ArrayDescriptor arrayDescriptor = (ArrayDescriptor) array;
        PrimitiveDescriptor indexPd = (PrimitiveDescriptor) index;

        if (indexPd.type != PrimitiveType.INTEGER_PRIMITIVE)
            throw new Error("Indices must be integers.");

        PrimitiveDescriptor element = new PrimitiveDescriptor(helper.getTempName(), "",
                ((PrimitiveDescriptor)arrayDescriptor.getElementType()).type);
        String address = helper.allocateMemory(element);
        element.setAddress(address);

        helper.writeComment(false,"# Extract element from array");
        helper.writeCommand("lw", "$t6", indexPd.getAddress());
        helper.writeCommand("add", "$t6","$t6","$t6");
        helper.writeCommand("add", "$t6","$t6","$t6");

        String command = element.type == PrimitiveType.REAL_PRIMITIVE ? "s.s" : "sw";
        helper.writeCommand(command, arrayDescriptor.getStartAddress()+"($t6)", address);

        semanticStack.push(element);
    }
    private void left_array_index(){

        Descriptor index = semanticStack.pop();
        Descriptor array = semanticStack.pop();

        ArrayDescriptor arrayDescriptor = (ArrayDescriptor) array;
        PrimitiveDescriptor indexPd = (PrimitiveDescriptor) index;

        if (indexPd.type!= PrimitiveType.INTEGER_PRIMITIVE)
            throw new Error("Indices must be integers.");

        PrimitiveDescriptor elementDSCP = (PrimitiveDescriptor) arrayDescriptor.elementType;

        helper.writeComment(false,"# Left Array Index");
        helper.writeCommand("lw", "$t7", indexPd.getAddress());
        helper.writeCommand("add", "$t7","t7","t7");
        helper.writeCommand("add", "$t7","t7","t7");

        PrimitiveDescriptor pd = new PrimitiveDescriptor(helper.getTempName(),
                arrayDescriptor.getStartAddress()+"($t7)", elementDSCP.type);
        semanticStack.push(pd);
    }
    private void assign(){
        Descriptor right = semanticStack.pop();
        Descriptor left = semanticStack.pop();
        if (!left.getClass().equals(right.getClass())){
            throw new Error("Assigning inconsistent types.");
        }
        if (left instanceof ArrayDescriptor){
            ArrayDescriptor leftArray = (ArrayDescriptor) left;
            ArrayDescriptor rightArray = (ArrayDescriptor) right;
            leftArray.setSize(rightArray.getSize());
            leftArray.setStartAddress(rightArray.getStartAddress());
        }
        else if (left instanceof PrimitiveDescriptor){
            PrimitiveDescriptor leftPrimitive = (PrimitiveDescriptor) left;
            PrimitiveDescriptor rightPrimitive = (PrimitiveDescriptor) right;
            if (leftPrimitive.type == PrimitiveType.STRING_PRIMITIVE){
                if (leftPrimitive.isConstant())
                    throw new Error("Assignment to a constant!");
                else {
                    leftPrimitive.setAddress(rightPrimitive.getAddress());
                }
            }
            else {
                helper.assignAddressLabelsValues(leftPrimitive.getAddress(), rightPrimitive.getAddress(), leftPrimitive.type);
            }
        } else
            throw new Error("Invalid Types for assignment!");
    }
    private void arrayDclComplete(){
        Descriptor rightExpr = semanticStack.pop();
        Descriptor rightType = semanticStack.pop();
        Descriptor left = semanticStack.pop();

        if (!(left instanceof ArrayDescriptor))
            throw new Error("Left value is not an array type!");
        ArrayDescriptor leftArray = (ArrayDescriptor) left;

        if (!(rightType instanceof PrimitiveDescriptor))
            throw new Error("invalid Element type for array");
        PrimitiveDescriptor pdRight = (PrimitiveDescriptor) rightExpr;
        if (pdRight.type != PrimitiveType.INTEGER_PRIMITIVE)
            throw new Error("invalid type for array size");

        if (!leftArray.getElementType().getClass().equals(rightType.getClass()))
            throw new Error("Inconsistent Array Element Types!");

        // rightExpr has to be a constant PrimitiveDescriptor
        PrimitiveDescriptor rightExprPd = (PrimitiveDescriptor) rightExpr;
        if (!rightExprPd.isConstant())
            throw new Error("Dynamic array size not supported!");

        int size = Integer.parseInt(rightExpr.symName);
        if (size <= 0)
            throw new Error("Size of an array should be a natural number!");

        leftArray.setSize(size);

        System.out.println(Integer.parseInt(rightExpr.symName));
        String allocatedMemoryStart = helper.allocateMemory(leftArray);
        System.out.println(allocatedMemoryStart);
        leftArray.setStartAddress(allocatedMemoryStart);
    }
    private void cast(){
        PrimitiveDescriptor expr = (PrimitiveDescriptor) semanticStack.pop();
        PrimitiveDescriptor castType = (PrimitiveDescriptor) semanticStack.pop();

        if (castType.type != PrimitiveType.INTEGER_PRIMITIVE && castType.type != PrimitiveType.REAL_PRIMITIVE)
            throw new Error("Invalid Casting Type!");

        if (castType.type == expr.type){
            semanticStack.push(expr);
            return;
        }
        semanticStack.push(castAndAllocate(castType, expr));
    }
    private PrimitiveDescriptor castAndAllocate(PrimitiveDescriptor castType, PrimitiveDescriptor expr){
        PrimitiveDescriptor casted;
        if (castType.type == PrimitiveType.INTEGER_PRIMITIVE){
            if (expr.isConstant()){
                int castedValue = Integer.parseInt(expr.symName);
                casted = new PrimitiveDescriptor(String.valueOf(castedValue), "", PrimitiveType.INTEGER_PRIMITIVE);
                String address = helper.allocateMemory(castType, String.valueOf(castedValue));
                casted.activeIsConstant();
                casted.setAddress(address);
                globalDescriptors.put(String.valueOf(castedValue), casted);
            }
            else {
                casted = new PrimitiveDescriptor(helper.getTempName(),"", PrimitiveType.INTEGER_PRIMITIVE);
                String addressForCasted = helper.allocateMemory(casted);
                casted.setAddress(addressForCasted);

                String exprAddress = expr.getAddress();
                helper.writeComment(false,"Casting float to integer");
                helper.writeCommand("l.s", "$f2",exprAddress);
                helper.writeCommand("cvt.w.s","$f2","$f2");
                helper.writeCommand("mfc1","$t2","$f2");
                helper.writeCommand("sw", "$t2", addressForCasted);
            }
        }
        else {
            if (expr.isConstant()){
                float castedValue = Float.parseFloat(expr.symName);
                casted = new PrimitiveDescriptor(String.valueOf(castedValue), "", PrimitiveType.REAL_PRIMITIVE);
                String address = helper.allocateMemory(castType, String.valueOf(castedValue));
                casted.activeIsConstant();
                casted.setAddress(address);
                globalDescriptors.put(String.valueOf(castedValue), casted);
            }
            else {
                casted = new PrimitiveDescriptor(helper.getTempName(),"", PrimitiveType.REAL_PRIMITIVE);
                String addressForCasted = helper.allocateMemory(casted);
                casted.setAddress(addressForCasted);

                String exprAddress = expr.getAddress();
                helper.writeComment(false,"Casting Integer to Float");
                helper.writeCommand("lw", "$t2",exprAddress);
                helper.writeCommand("mfc1","$t2","$f2");
                helper.writeCommand("cvt.s.w","$f2","$f2");
                helper.writeCommand("s.s", "$f2", addressForCasted);

            }

        }
        return casted;

    }


    // Mathematical Semantics:
    private void add() {
        System.out.println("Adding");
        System.out.println(scanner.currentSymbol);
    }
    private void subtract() {

    }



    // utils:
    private String stringTypeOfPrimitiveType(PrimitiveType pt){
        String type;
        if (pt == PrimitiveType.INTEGER_PRIMITIVE)
            type = "int";
        else if(pt == PrimitiveType.REAL_PRIMITIVE)
            type = "real";
        else if(pt == PrimitiveType.STRING_PRIMITIVE)
            type = "string";
        else if (pt == PrimitiveType.BOOLEAN_PRIMITIVE)
            type = "bool";
        else
            throw new Error("Not a valid type!");
        return type;
    }
    private final ArrayList<TokenType> constantTypes =
            new ArrayList<>(Arrays.asList(TokenType.REAL, TokenType.INTEGER, TokenType.STRING));
    private boolean isDeclaredToken(String token){
        return (currentMethod.symTable.containsKey(token) ||
                globalDescriptors.containsKey(token));
    }


}
