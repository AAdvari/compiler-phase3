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

        PrimitiveDescriptor trueBool = new PrimitiveDescriptor("true", "", PrimitiveType.BOOLEAN_PRIMITIVE);
        PrimitiveDescriptor falseBool = new PrimitiveDescriptor("false", "", PrimitiveType.BOOLEAN_PRIMITIVE);
        String trueBoolAddress = helper.allocateMemory(trueBool, String.valueOf(1));
        String falseBoolAddress = helper.allocateMemory(trueBool, String.valueOf(0));

        trueBool.setAddress(trueBoolAddress);
        falseBool.setAddress(falseBoolAddress);

        globalDescriptors.put("real",real);
        globalDescriptors.put("int",integer);
        globalDescriptors.put("bool",bool);
        globalDescriptors.put("string",string);
        globalDescriptors.put("true", trueBool);
        globalDescriptors.put("false", falseBool);
        globalDescriptors.put("void", null);
    }

    @Override
    public void doSemantic(String sem) {
        switch (sem) {
            case "push":
                push();
                break;
            case "add":
            case "subtract":
            case "multiply":
            case "divide":
            case "mod":
            case "bitwise_and":
            case "bitwise_or":
            case "bitwise_xor":
            case "bigger_than":
            case "bigger_than_equal":
            case "less_than":
            case "less_than_equal":
            case "equality":
            case "inequality":
            case "and":
            case "or":
                twoOperandCheckAndCalculate(sem);
            case "not":
            case "minus_minus":
            case "plus_plus":
            case "unary_minus":
                System.out.println("Not Implemented Yet");
                break;
            case "read_string":
                read_string();
                break;
            case "read_int":
                read_int();
                break;
            case "read_real":
                read_real();
                break;
            case "print_expr":
                print_expr();
                break;
            case "left_array_index":
                leftArrayIndex();
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
            case "len":
                len();
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
//        else if (currentClass.fields.containsKey(symName)) {
//            System.out.println("Pushing Class Fields is not implemented yet");
//        }
        else
            throw new Error("Literal "+ symName + " is not declared within current scope!");
    }
    private void push_constant(String token, TokenType type) {
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
    private void print_string(PrimitiveDescriptor pd){
        if (pd.type != PrimitiveType.STRING_PRIMITIVE)
            throw new Error("Expected String but got "+ pd.type);
        helper.writeComment(false,"Printing " + pd.symName );
        helper.writeCommand("li","$v0","4");
        helper.writeCommand("la","$a0",pd.getAddress());
        helper.writeCommand("syscall");

    }
    private void print_expr(){
        PrimitiveDescriptor pd = (PrimitiveDescriptor) semanticStack.pop();
        if (pd.type == PrimitiveType.REAL_PRIMITIVE)
            print_real(pd);
        else if (pd.type == PrimitiveType.STRING_PRIMITIVE)
            print_string(pd);
        else
            print_int(pd);
    }
    private void print_real(PrimitiveDescriptor pd){
        if (pd.type != PrimitiveType.REAL_PRIMITIVE)
            throw new Error("Expected Real type but got "+ pd.type);
        helper.writeComment(false,"Printing " + pd.symName );
        helper.writeCommand("li","$v0","2");
        helper.writeCommand("l.s","$f12",pd.getAddress());
        helper.writeCommand("syscall");
    }
    private void print_int(PrimitiveDescriptor pd){
        if (pd.type != PrimitiveType.INTEGER_PRIMITIVE && pd.type != PrimitiveType.BOOLEAN_PRIMITIVE)
            throw new Error("Expected Integer or boolean but got "+ pd.type);
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

        String store = element.type == PrimitiveType.REAL_PRIMITIVE ? "s.s" : "sw";
        String load = element.type == PrimitiveType.REAL_PRIMITIVE ? "l.s" : "lw";
        String reg = element.type == PrimitiveType.REAL_PRIMITIVE ? "$f6" : "$t6";

        helper.writeCommand(load, reg, arrayDescriptor.getStartAddress()+"($t6)");
        helper.writeCommand(store, reg, address);

        semanticStack.push(element);
    }
    private void leftArrayIndex(){

        Descriptor index = semanticStack.pop();
        Descriptor array = semanticStack.pop();

        ArrayDescriptor arrayDescriptor = (ArrayDescriptor) array;
        PrimitiveDescriptor indexPd = (PrimitiveDescriptor) index;

        if (indexPd.type!= PrimitiveType.INTEGER_PRIMITIVE)
            throw new Error("Indices must be integers.");

        PrimitiveDescriptor elementDSCP = (PrimitiveDescriptor) arrayDescriptor.elementType;

        helper.writeComment(false,"# Left Array Index");
        helper.writeCommand("lw", "$t7", indexPd.getAddress());
        helper.writeCommand("add", "$t7","$t7","$t7");
        helper.writeCommand("add", "$t7","$t7","$t7");

        PrimitiveDescriptor pd = new PrimitiveDescriptor(helper.getTempName(),
                arrayDescriptor.getStartAddress()+"($t7)", elementDSCP.type);
        System.out.println("elemType: " + elementDSCP.type);
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

        String allocatedMemoryStart = helper.allocateMemory(leftArray);
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
                int castedValue = (int) Float.parseFloat(expr.symName);
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
                float castedValue = (float) Integer.parseInt(expr.symName);
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
    private void len(){
        Descriptor descriptor = semanticStack.pop();
        if (descriptor instanceof ArrayDescriptor){
            ArrayDescriptor ad = (ArrayDescriptor) descriptor;
            PrimitiveDescriptor val = new PrimitiveDescriptor(helper.getTempName(),
                    "", PrimitiveType.INTEGER_PRIMITIVE);
            String allocatedMemoryAddress = helper.allocateMemory(val,  String.valueOf(ad.getSize()));
            val.setAddress(allocatedMemoryAddress);
            semanticStack.push(val);
        }
        else
            throw new Error("len function for given type is not supported!");
    }


    // Mathematical Semantics:
    private void twoConstantOperandCheckAndCalculate(String semantic,PrimitiveDescriptor firstOperand, PrimitiveDescriptor secondOperand){
        PrimitiveType type = firstOperand.type;
        String resValue;
        PrimitiveType resType;
        switch (semantic){
            case "add":
                if (type == PrimitiveType.INTEGER_PRIMITIVE){
                    resValue = String.valueOf(
                            Integer.parseInt(firstOperand.symName) + Integer.parseInt(secondOperand.symName)
                    );
                    resType = PrimitiveType.INTEGER_PRIMITIVE;
                }
                else if (type == PrimitiveType.REAL_PRIMITIVE){
                    resValue = String.valueOf(
                            Float.parseFloat(firstOperand.symName) + Float.parseFloat(secondOperand.symName)
                    );
                    resType = PrimitiveType.REAL_PRIMITIVE;
                }
                else
                    throw new Error("Addition is not applicable on given operand types.");
                break;
            case "subtract":
                if (type == PrimitiveType.INTEGER_PRIMITIVE){
                    resValue = String.valueOf(
                            Integer.parseInt(firstOperand.symName) - Integer.parseInt(secondOperand.symName)
                    );
                    resType = PrimitiveType.INTEGER_PRIMITIVE;
                }
                else if (type == PrimitiveType.REAL_PRIMITIVE){
                    resValue = String.valueOf(
                            Float.parseFloat(firstOperand.symName) - Float.parseFloat(secondOperand.symName)
                    );
                    resType = PrimitiveType.REAL_PRIMITIVE;
                }
                else
                    throw new Error("Subtraction is not applicable on given operand types.");
                break;
            case "multiply":
                if (type == PrimitiveType.INTEGER_PRIMITIVE){
                    resValue = String.valueOf(
                            Integer.parseInt(firstOperand.symName) * Integer.parseInt(secondOperand.symName)
                    );
                    resType = PrimitiveType.INTEGER_PRIMITIVE;
                }
                else if (type == PrimitiveType.REAL_PRIMITIVE){
                    resValue = String.valueOf(
                            Float.parseFloat(firstOperand.symName) * Float.parseFloat(secondOperand.symName)
                    );
                    resType = PrimitiveType.REAL_PRIMITIVE;
                }
                else
                    throw new Error("Multiplication is not applicable on given operand types.");
                break;
            case "mod":
                if (type == PrimitiveType.INTEGER_PRIMITIVE){
                    resValue = String.valueOf(
                            Integer.parseInt(firstOperand.symName) % Integer.parseInt(secondOperand.symName)
                    );
                    resType = PrimitiveType.INTEGER_PRIMITIVE;
                }
                else
                    throw new Error("Mod is not applicable on given operand types.");
                break;
            case "divide":
                if (secondOperand.symName.charAt(0) == '0')
                    throw new Error("Can't divide by zero");
                if (type == PrimitiveType.INTEGER_PRIMITIVE){
                    resValue = String.valueOf(
                            Integer.parseInt(firstOperand.symName) / Integer.parseInt(secondOperand.symName)
                    );
                    resType = PrimitiveType.INTEGER_PRIMITIVE;
                }
                else if (type == PrimitiveType.REAL_PRIMITIVE){
                    resValue = String.valueOf(
                            Float.parseFloat(firstOperand.symName) / Float.parseFloat(secondOperand.symName)
                    );
                    resType = PrimitiveType.REAL_PRIMITIVE;
                }
                else
                    throw new Error("Division is not applicable on given operand types.");
                break;
            case "bitwise_and":
                if (type == PrimitiveType.INTEGER_PRIMITIVE){
                    resValue = String.valueOf(
                            Integer.parseInt(firstOperand.symName) & Integer.parseInt(secondOperand.symName)
                    );
                    resType = PrimitiveType.INTEGER_PRIMITIVE;
                }
                else
                    throw new Error("Bitwise and  is not applicable on given operand types.");
                break;
            case "bitwise_or":
                if (type == PrimitiveType.INTEGER_PRIMITIVE){
                    resValue = String.valueOf(
                            Integer.parseInt(firstOperand.symName) | Integer.parseInt(secondOperand.symName)
                    );
                    resType = PrimitiveType.INTEGER_PRIMITIVE;
                }
                else
                    throw new Error("Bitwise or is not applicable on given operand types.");
                break;
            case "bitwise_xor":
                if (type == PrimitiveType.INTEGER_PRIMITIVE){
                    resValue = String.valueOf(
                            Integer.parseInt(firstOperand.symName) ^ Integer.parseInt(secondOperand.symName)
                    );
                    resType = PrimitiveType.INTEGER_PRIMITIVE;
                }
                else
                    throw new Error("Bitwise xor  is not applicable on given operand types.");
                break;
            case "bigger_than":
                if (type == PrimitiveType.INTEGER_PRIMITIVE){
                    resValue = String.valueOf(
                            Integer.parseInt(firstOperand.symName) > Integer.parseInt(secondOperand.symName)
                    );
                    resType = PrimitiveType.BOOLEAN_PRIMITIVE;
                }
                else if (type == PrimitiveType.REAL_PRIMITIVE){
                    resValue = String.valueOf(
                            Float.parseFloat(firstOperand.symName) > Float.parseFloat(secondOperand.symName)
                    );
                    resType = PrimitiveType.BOOLEAN_PRIMITIVE;
                }
                else
                    throw new Error("biggerThan operator is not applicable on given operand types.");
                break;
            case "bigger_than_equal":
                if (type == PrimitiveType.INTEGER_PRIMITIVE){
                    resValue = String.valueOf(
                            Integer.parseInt(firstOperand.symName) >= Integer.parseInt(secondOperand.symName)
                    );
                    resType = PrimitiveType.BOOLEAN_PRIMITIVE;
                }
                else if (type == PrimitiveType.REAL_PRIMITIVE){
                    resValue = String.valueOf(
                            Float.parseFloat(firstOperand.symName) >= Float.parseFloat(secondOperand.symName)
                    );
                    resType = PrimitiveType.BOOLEAN_PRIMITIVE;
                }
                else
                    throw new Error("biggerThanEqual operator is not applicable on given operand types.");
                break;
            case "less_than":
                if (type == PrimitiveType.INTEGER_PRIMITIVE){
                    resValue = String.valueOf(
                            Integer.parseInt(firstOperand.symName) < Integer.parseInt(secondOperand.symName)
                    );
                    resType = PrimitiveType.BOOLEAN_PRIMITIVE;
                }
                else if (type == PrimitiveType.REAL_PRIMITIVE){
                    resValue = String.valueOf(
                            Float.parseFloat(firstOperand.symName) < Float.parseFloat(secondOperand.symName)
                    );
                    resType = PrimitiveType.BOOLEAN_PRIMITIVE;
                }
                else
                    throw new Error("lessThan operator is not applicable on given operand types.");
                break;
            case "less_than_equal":
                if (type == PrimitiveType.INTEGER_PRIMITIVE){
                    resValue = String.valueOf(
                            Integer.parseInt(firstOperand.symName) <= Integer.parseInt(secondOperand.symName)
                    );
                    resType = PrimitiveType.BOOLEAN_PRIMITIVE;
                }
                else if (type == PrimitiveType.REAL_PRIMITIVE){
                    resValue = String.valueOf(
                            Float.parseFloat(firstOperand.symName) <= Float.parseFloat(secondOperand.symName)
                    );
                    resType = PrimitiveType.BOOLEAN_PRIMITIVE;
                }
                else
                    throw new Error("lessThanEqual operator is not applicable on given operand types.");
                break;
            case "equality":
                if (type == PrimitiveType.INTEGER_PRIMITIVE || type == PrimitiveType.BOOLEAN_PRIMITIVE){
                    resValue = String.valueOf(
                            Integer.parseInt(firstOperand.symName) == Integer.parseInt(secondOperand.symName)
                    );
                    resType = PrimitiveType.BOOLEAN_PRIMITIVE;
                }
                else if (type == PrimitiveType.REAL_PRIMITIVE){
                    resValue = String.valueOf(
                            Float.parseFloat(firstOperand.symName) == Float.parseFloat(secondOperand.symName)
                    );
                    resType = PrimitiveType.BOOLEAN_PRIMITIVE;
                }
                else
                    throw new Error("Equality operator is not applicable on given operand types.");
                break;
            case "inequality":
                if (type == PrimitiveType.INTEGER_PRIMITIVE || type == PrimitiveType.BOOLEAN_PRIMITIVE){
                    resValue = String.valueOf(
                            Integer.parseInt(firstOperand.symName) != Integer.parseInt(secondOperand.symName)
                    );
                    resType = PrimitiveType.BOOLEAN_PRIMITIVE;
                }
                else if (type == PrimitiveType.REAL_PRIMITIVE){
                    resValue = String.valueOf(
                            Float.parseFloat(firstOperand.symName) !=  Float.parseFloat(secondOperand.symName)
                    );
                    resType = PrimitiveType.BOOLEAN_PRIMITIVE;
                }
                else
                    throw new Error("Inequality operator is not applicable on given operand types.");
                break;
            case "and":
            case "or":
            case "xor":
                throw new Error("Invalid boolean operation!");
            default:
                throw new Error("Operation is not supported");
        }

        PrimitiveDescriptor pd = new PrimitiveDescriptor(resValue, "", resType);
        String allocatedAddress = helper.allocateMemory(pd, resValue);
        pd.setAddress(allocatedAddress);
        pd.activeIsConstant();
        semanticStack.push(pd);
        globalDescriptors.put(resValue, pd);
    }
    private void twoOperandCheckAndCalculate(String semantic){
        PrimitiveDescriptor firstOperand = (PrimitiveDescriptor) semanticStack.pop();
        PrimitiveDescriptor secondOperand = (PrimitiveDescriptor) semanticStack.pop();
        if (firstOperand.type != secondOperand.type){
            throw new Error("Inconsistent types for " + semantic + " operation");
        }
        if (firstOperand.isConstant() && secondOperand.isConstant()){
            twoConstantOperandCheckAndCalculate(semantic, firstOperand, secondOperand);
            return;
        }

        switch (semantic){
            case "add":
                add(firstOperand, secondOperand);
                break;
            case "subtract":
                subtract(firstOperand, secondOperand);
                break;
            case "multiply":
                multiply(firstOperand, secondOperand);
                break;
            case "divide":
                divide(firstOperand, secondOperand);
                break;
            case "mod":
                mod(firstOperand, secondOperand);
                break;
            case "bitwise_and":
                bitwiseAnd(firstOperand, secondOperand);
                break;
            case "bitwise_or":
                bitwiseOr(firstOperand, secondOperand);
                break;
            case "bitwise_xor":
                bitwiseXor(firstOperand, secondOperand);
                break;
            case "bigger_than":
                biggerThan(firstOperand, secondOperand);
                break;
            case "bigger_than_equal":
                biggerThanEqual(firstOperand, secondOperand);
                break;
            case "less_than":
                lessThan(firstOperand, secondOperand);
                break;
            case "less_than_equal":
                lessThanEqual(firstOperand, secondOperand);
                break;
            case "equality":
                equality(firstOperand, secondOperand);
                break;
            case "inequality":
                inequality(firstOperand, secondOperand);
                break;
            case "and":
                and(firstOperand, secondOperand);
                break;
            case "or":
                or(firstOperand, secondOperand);
                break;
            case "xor":
                xor(firstOperand, secondOperand);
                break;

        }
    }



    private void add(PrimitiveDescriptor firstOperand, PrimitiveDescriptor secondOperand){
        if (firstOperand.type == PrimitiveType.REAL_PRIMITIVE){
            PrimitiveDescriptor pd = new PrimitiveDescriptor(helper.getTempName(), "", firstOperand.type);
            String allocatedAddress = helper.allocateMemory(pd);
            helper.writeComment(false,"Real Addition");
            helper.writeCommand("l.s", "$f0", firstOperand.getAddress());
            helper.writeCommand("l.s", "$f1", secondOperand.getAddress());
            helper.writeCommand("add.s","$f0","$f0","$f1");
            helper.writeCommand("s.s", "$f0", allocatedAddress);

            semanticStack.push(pd);
        }
        else if (firstOperand.type == PrimitiveType.INTEGER_PRIMITIVE){
            PrimitiveDescriptor pd = new PrimitiveDescriptor(helper.getTempName(), "", firstOperand.type);
            String allocatedAddress = helper.allocateMemory(pd);
            helper.writeComment(false,"Integer Addition");
            helper.writeCommand("lw", "$t0", firstOperand.getAddress());
            helper.writeCommand("lw", "$t1", secondOperand.getAddress());
            helper.writeCommand("add","$t0","$t0","$t1");
            helper.writeCommand("sw", "$t0", allocatedAddress);

            semanticStack.push(pd);
        }
        else
            throw new Error("invalid types for addition");
    }
    private void subtract(PrimitiveDescriptor firstOperand, PrimitiveDescriptor secondOperand){
        if (firstOperand.type == PrimitiveType.REAL_PRIMITIVE){
            PrimitiveDescriptor pd = new PrimitiveDescriptor(helper.getTempName(), "", firstOperand.type);
            String allocatedAddress = helper.allocateMemory(pd);
            helper.writeComment(false,"Real Subtraction");
            helper.writeCommand("l.s", "$f0", firstOperand.getAddress());
            helper.writeCommand("l.s", "$f1", secondOperand.getAddress());
            helper.writeCommand("sub.s","$f0","$f0","$f1");
            helper.writeCommand("s.s", "$f0", allocatedAddress);

            semanticStack.push(pd);
        }
        else if (firstOperand.type == PrimitiveType.INTEGER_PRIMITIVE){
            PrimitiveDescriptor pd = new PrimitiveDescriptor(helper.getTempName(), "", firstOperand.type);
            String allocatedAddress = helper.allocateMemory(pd);
            helper.writeComment(false,"Integer Subtraction");
            helper.writeCommand("lw", "$t0", firstOperand.getAddress());
            helper.writeCommand("lw", "$t1", secondOperand.getAddress());
            helper.writeCommand("sub","$t0","$t0","$t1");
            helper.writeCommand("sw", "$t0", allocatedAddress);

            semanticStack.push(pd);
        }
        else
            throw new Error("invalid types for Subtraction");
    }

    private void multiply(PrimitiveDescriptor firstOperand, PrimitiveDescriptor secondOperand){}
    private void divide(PrimitiveDescriptor firstOperand, PrimitiveDescriptor secondOperand){}
    private void mod(PrimitiveDescriptor firstOperand, PrimitiveDescriptor secondOperand){}

    private void bitwiseAnd(PrimitiveDescriptor firstOperand, PrimitiveDescriptor secondOperand){
        if (firstOperand.type == PrimitiveType.INTEGER_PRIMITIVE){
            PrimitiveDescriptor pd = new PrimitiveDescriptor(helper.getTempName(), "", firstOperand.type);
            String allocatedAddress = helper.allocateMemory(pd);
            helper.writeComment(false,"BitwiseAnd");
            helper.writeCommand("lw", "$t0", firstOperand.getAddress());
            helper.writeCommand("lw", "$t1", secondOperand.getAddress());
            helper.writeCommand("and","$t0","$t0","$t1");
            helper.writeCommand("sw", "$t0", allocatedAddress);

            semanticStack.push(pd);
        }
        else
            throw new Error("Not valid type for bitwise operation");
    }
    private void bitwiseOr(PrimitiveDescriptor firstOperand, PrimitiveDescriptor secondOperand){
        if (firstOperand.type == PrimitiveType.INTEGER_PRIMITIVE){
            PrimitiveDescriptor pd = new PrimitiveDescriptor(helper.getTempName(), "", firstOperand.type);
            String allocatedAddress = helper.allocateMemory(pd);
            helper.writeComment(false,"BitwiseOr");
            helper.writeCommand("lw", "$t0", firstOperand.getAddress());
            helper.writeCommand("lw", "$t1", secondOperand.getAddress());
            helper.writeCommand("or","$t0","$t0","$t1");
            helper.writeCommand("sw", "$t0", allocatedAddress);

            semanticStack.push(pd);
        }
        else
            throw new Error("Not valid type for bitwise operation");
    }

    private void bitwiseXor(PrimitiveDescriptor firstOperand, PrimitiveDescriptor secondOperand){}


    private void biggerThan(PrimitiveDescriptor firstOperand, PrimitiveDescriptor secondOperand){
        if (firstOperand.type == PrimitiveType.REAL_PRIMITIVE){
            PrimitiveDescriptor pd = new PrimitiveDescriptor(helper.getTempName(), "", PrimitiveType.BOOLEAN_PRIMITIVE);
            String allocatedAddress = helper.allocateMemory(pd);
            helper.writeComment(false,"Real Comparison : BiggerThan");
            helper.writeCommand("l.s", "$f0", firstOperand.getAddress());
            helper.writeCommand("l.s", "$f1", secondOperand.getAddress());
            helper.writeCommand("c.lt.s","$f0","$f1");
            helper.writeCommand("mov","$t0","$1");
            helper.writeCommand("movt","$t0","$0","$fcc0");
            helper.writeCommand("lw", "$t0", allocatedAddress);

            semanticStack.push(pd);
        }
        else if (firstOperand.type == PrimitiveType.INTEGER_PRIMITIVE){
            PrimitiveDescriptor pd = new PrimitiveDescriptor(helper.getTempName(), "", PrimitiveType.BOOLEAN_PRIMITIVE);
            String allocatedAddress = helper.allocateMemory(pd);
            helper.writeComment(false,"Integer Comparison : BiggerThan");
            helper.writeCommand("lw", "$t0", firstOperand.getAddress());
            helper.writeCommand("lw", "$t1", secondOperand.getAddress());
            helper.writeCommand("sgt","$t0","$t0","$t1");
            helper.writeCommand("sw", "$t0", allocatedAddress);

            semanticStack.push(pd);
        }
        else
            throw new Error("invalid types for addition");
    }
    private void lessThan(PrimitiveDescriptor firstOperand, PrimitiveDescriptor secondOperand){
        if (firstOperand.type == PrimitiveType.REAL_PRIMITIVE){
            PrimitiveDescriptor pd = new PrimitiveDescriptor(helper.getTempName(), "", PrimitiveType.BOOLEAN_PRIMITIVE);
            String allocatedAddress = helper.allocateMemory(pd);
            helper.writeComment(false,"Real Comparison : LessThan");
            helper.writeCommand("l.s", "$f0", firstOperand.getAddress());
            helper.writeCommand("l.s", "$f1", secondOperand.getAddress());
            helper.writeCommand("c.lt.s","$f0","$f1");
            helper.writeCommand("mov","$t0","$0");
            helper.writeCommand("movt","$t0","$1","$fcc0");
            helper.writeCommand("lw", "$t0", allocatedAddress);

            semanticStack.push(pd);
        }
        else if (firstOperand.type == PrimitiveType.INTEGER_PRIMITIVE){
            PrimitiveDescriptor pd = new PrimitiveDescriptor(helper.getTempName(), "", PrimitiveType.BOOLEAN_PRIMITIVE);
            String allocatedAddress = helper.allocateMemory(pd);
            helper.writeComment(false,"Integer Comparison : LessThan");
            helper.writeCommand("lw", "$t0", firstOperand.getAddress());
            helper.writeCommand("lw", "$t1", secondOperand.getAddress());
            helper.writeCommand("slt","$t0","$t0","$t1");
            helper.writeCommand("sw", "$t0", allocatedAddress);

            semanticStack.push(pd);
        }
        else
            throw new Error("invalid types for addition");

    }
    private void lessThanEqual(PrimitiveDescriptor firstOperand, PrimitiveDescriptor secondOperand){
        if (firstOperand.type == PrimitiveType.REAL_PRIMITIVE){
            PrimitiveDescriptor pd = new PrimitiveDescriptor(helper.getTempName(), "", PrimitiveType.BOOLEAN_PRIMITIVE);
            String allocatedAddress = helper.allocateMemory(pd);
            helper.writeComment(false,"Real Comparison : LessThanEqual");
            helper.writeCommand("l.s", "$f0", firstOperand.getAddress());
            helper.writeCommand("l.s", "$f1", secondOperand.getAddress());
            helper.writeCommand("c.le.s","$f0","$f1");
            helper.writeCommand("mov","$t0","$0");
            helper.writeCommand("movt","$t0","$1","$fcc0");
            helper.writeCommand("lw", "$t0", allocatedAddress);

            semanticStack.push(pd);
    }
    else if (firstOperand.type == PrimitiveType.INTEGER_PRIMITIVE){
            PrimitiveDescriptor pd = new PrimitiveDescriptor(helper.getTempName(), "", PrimitiveType.BOOLEAN_PRIMITIVE);
            String allocatedAddress = helper.allocateMemory(pd);
            helper.writeComment(false,"Integer Comparison : LessThanEqual");
            helper.writeCommand("lw", "$t0", firstOperand.getAddress());
            helper.writeCommand("lw", "$t1", secondOperand.getAddress());
            helper.writeCommand("sle","$t0","$t0","$t1");
            helper.writeCommand("sw", "$t0", allocatedAddress);

            semanticStack.push(pd);
    }
    else
        throw new Error("invalid types for addition");
    }
    private void equality(PrimitiveDescriptor firstOperand, PrimitiveDescriptor secondOperand){
        if (firstOperand.type == PrimitiveType.REAL_PRIMITIVE){
            PrimitiveDescriptor pd = new PrimitiveDescriptor(helper.getTempName(), "", PrimitiveType.BOOLEAN_PRIMITIVE);
            String allocatedAddress = helper.allocateMemory(pd);
            helper.writeComment(false,"Real Comparison : equality");
            helper.writeCommand("l.s", "$f0", firstOperand.getAddress());
            helper.writeCommand("l.s", "$f1", secondOperand.getAddress());
            helper.writeCommand("c.eq.s","$f0","$f1");
            helper.writeCommand("mov","$t0","$0");
            helper.writeCommand("movt","$t0","$1","$fcc0");
            helper.writeCommand("lw", "$t0", allocatedAddress);

            semanticStack.push(pd);
        }
        else if (firstOperand.type == PrimitiveType.INTEGER_PRIMITIVE){
            PrimitiveDescriptor pd = new PrimitiveDescriptor(helper.getTempName(), "", PrimitiveType.BOOLEAN_PRIMITIVE);
            String allocatedAddress = helper.allocateMemory(pd);
            helper.writeComment(false,"Integer Comparison : equality");
            helper.writeCommand("lw", "$t0", firstOperand.getAddress());
            helper.writeCommand("lw", "$t1", secondOperand.getAddress());
            helper.writeCommand("seq","$t0","$t0","$t1");
            helper.writeCommand("sw", "$t0", allocatedAddress);

            semanticStack.push(pd);
        }
        else
            throw new Error("invalid types for addition");
    }
    private void inequality(PrimitiveDescriptor firstOperand, PrimitiveDescriptor secondOperand){
        if (firstOperand.type == PrimitiveType.REAL_PRIMITIVE){
            PrimitiveDescriptor pd = new PrimitiveDescriptor(helper.getTempName(), "", PrimitiveType.BOOLEAN_PRIMITIVE);
            String allocatedAddress = helper.allocateMemory(pd);
            helper.writeComment(false,"Real Comparison : inequality");
            helper.writeCommand("l.s", "$f0", firstOperand.getAddress());
            helper.writeCommand("l.s", "$f1", secondOperand.getAddress());
            helper.writeCommand("c.eq.s","$f0","$f1");
            helper.writeCommand("mov","$t0","$1");
            helper.writeCommand("movt","$t0","$0","$fcc0");
            helper.writeCommand("lw", "$t0", allocatedAddress);

            semanticStack.push(pd);
        }
        else if (firstOperand.type == PrimitiveType.INTEGER_PRIMITIVE){
            PrimitiveDescriptor pd = new PrimitiveDescriptor(helper.getTempName(), "", PrimitiveType.BOOLEAN_PRIMITIVE);
            String allocatedAddress = helper.allocateMemory(pd);
            helper.writeComment(false,"Integer Comparison : inequality");
            helper.writeCommand("lw", "$t0", firstOperand.getAddress());
            helper.writeCommand("lw", "$t1", secondOperand.getAddress());
            helper.writeCommand("sne","$t0","$t0","$t1");
            helper.writeCommand("sw", "$t0", allocatedAddress);

            semanticStack.push(pd);
        }
        else
            throw new Error("invalid types for addition");
    }
    private void biggerThanEqual(PrimitiveDescriptor firstOperand, PrimitiveDescriptor secondOperand){
        if (firstOperand.type == PrimitiveType.REAL_PRIMITIVE){
            PrimitiveDescriptor pd = new PrimitiveDescriptor(helper.getTempName(), "", PrimitiveType.BOOLEAN_PRIMITIVE);
            String allocatedAddress = helper.allocateMemory(pd);
            helper.writeComment(false,"Real Comparison : BiggerThanEqual");
            helper.writeCommand("l.s", "$f0", firstOperand.getAddress());
            helper.writeCommand("l.s", "$f1", secondOperand.getAddress());
            helper.writeCommand("c.le.s","$f0","$f1");
            helper.writeCommand("mov","$t0","$1");
            helper.writeCommand("movt","$t0","$0","$fcc0");
            helper.writeCommand("lw", "$t0", allocatedAddress);

            semanticStack.push(pd);
        }
        else if (firstOperand.type == PrimitiveType.INTEGER_PRIMITIVE){
            PrimitiveDescriptor pd = new PrimitiveDescriptor(helper.getTempName(), "", PrimitiveType.BOOLEAN_PRIMITIVE);
            String allocatedAddress = helper.allocateMemory(pd);
            helper.writeComment(false,"Integer Comparison : BiggerThanEqual");
            helper.writeCommand("lw", "$t0", firstOperand.getAddress());
            helper.writeCommand("lw", "$t1", secondOperand.getAddress());
            helper.writeCommand("sge","$t0","$t0","$t1");
            helper.writeCommand("sw", "$t0", allocatedAddress);

            semanticStack.push(pd);
        }
        else
            throw new Error("invalid types for addition");
    }


    private void and(PrimitiveDescriptor firstOperand, PrimitiveDescriptor secondOperand){
        if (firstOperand.type == PrimitiveType.BOOLEAN_PRIMITIVE){
            PrimitiveDescriptor pd = new PrimitiveDescriptor(helper.getTempName(), "", firstOperand.type);
            String allocatedAddress = helper.allocateMemory(pd);
            helper.writeComment(false,"And");
            helper.writeCommand("lw", "$t0", firstOperand.getAddress());
            helper.writeCommand("lw", "$t1", secondOperand.getAddress());
            helper.writeCommand("and","$t0","$t0","$t1");
            helper.writeCommand("sw", "$t0", allocatedAddress);

            semanticStack.push(pd);
        }
        else
            throw new Error("Not valid type for bitwise operation");
    }
    private void or(PrimitiveDescriptor firstOperand, PrimitiveDescriptor secondOperand){
        if (firstOperand.type == PrimitiveType.BOOLEAN_PRIMITIVE){
            PrimitiveDescriptor pd = new PrimitiveDescriptor(helper.getTempName(), "", firstOperand.type);
            String allocatedAddress = helper.allocateMemory(pd);
            helper.writeComment(false,"Or");
            helper.writeCommand("lw", "$t0", firstOperand.getAddress());
            helper.writeCommand("lw", "$t1", secondOperand.getAddress());
            helper.writeCommand("or","$t0","$t0","$t1");
            helper.writeCommand("sw", "$t0", allocatedAddress);

            semanticStack.push(pd);
        }
        else
            throw new Error("Not valid type for bitwise operation");
    }

    private void xor(PrimitiveDescriptor firstOperand, PrimitiveDescriptor secondOperand){}


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
