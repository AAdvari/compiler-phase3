package codegen;

import codegen.dscp.*;
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
        helper = new Helper();
        globalDescriptors = new TreeMap<>(String::compareTo);
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

        }
    }



    // Regular Semantics:
    public void traceObject() {
    }
    public void declareVariable() {
        Descriptor descriptor = semanticStack.peek();
        if (currentMethod == null) {
            addFieldToClass(descriptor);
            return;
        }
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
    public void declarePrimitiveVariable(PrimitiveDescriptor pd) {
        Symbol currentSymbol = scanner.currentSymbol;
        String token = currentSymbol.getToken();

        if (globalDescriptors.containsKey(token)
        || currentMethod.symTable.containsKey(token))
            throw new Error("Identifier " + token + " has been declared before!");

        String type = stringTypeOfPrimitiveType(pd.type);

        String address = helper.allocateMemory(globalDescriptors.get(type), String.valueOf(0));
        PrimitiveDescriptor creatingVarDescriptor =
                new PrimitiveDescriptor(token, address, pd.type);
        currentMethod.addVariable(token, creatingVarDescriptor);
    }

    /** We cannot specify memory for arrays in declaration,
       allocating memory is done in assignment section for arrays.

     */
    public void declareArray() {
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
    public void declareObject(ObjectDescriptor od) {

    }
    public void addFieldToClass(Descriptor descriptor) {

    }
    public void addArrayFieldToClass(Descriptor elementTypeDescriptor){

    }
    public void push() {
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
    public void push_constant(String token, TokenType type) {

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
        PrimitiveDescriptor pd = new PrimitiveDescriptor(token, "address", constantType);
        pd.address = helper.allocateMemory(pd, token);

        globalDescriptors.put(token, pd);
    }

    // Mathematical Semantics:
    public void add() {
        System.out.println("Adding");
        System.out.println(scanner.currentSymbol);
    }
    public void subtract() {

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
