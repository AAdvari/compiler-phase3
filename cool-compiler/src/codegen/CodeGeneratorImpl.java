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

        String type;
        if (pd.type == PrimitiveType.INTEGER_PRIMITIVE)
            type = "int";
        else if(pd.type == PrimitiveType.REAL_PRIMITIVE)
            type = "real";
        else if(pd.type == PrimitiveType.STRING_PRIMITIVE)
            type = "string";
        else if (pd.type == PrimitiveType.BOOLEAN_PRIMITIVE)
            type = "bool";
        else
            throw new Error("Not a valid type!");

        String address = helper.allocateMemory(globalDescriptors.get(type));
        PrimitiveDescriptor creatingVarDescriptor =
                new PrimitiveDescriptor(token, address, pd.type);
        currentMethod.addVariable(token, creatingVarDescriptor);
    }

    // We cannot specify memory for arrays in declaration,
    // allocating memory is done in assignment section for arrays.
    public void declareArray() {
        Descriptor arrayElementTypeDescriptor = semanticStack.peek();
        String creatingArrayName = scanner.currentSymbol.getToken();

        if (isDeclaredToken(creatingArrayName))
            throw new Error("Identifier is declared before!");

        ArrayDescriptor arrayDescriptor = new ArrayDescriptor(creatingArrayName,arrayElementTypeDescriptor);
        currentMethod.addVariable(creatingArrayName, arrayDescriptor);
    }

    private boolean isDeclaredToken(String token){
        return (currentMethod.symTable.containsKey(token) ||
                globalDescriptors.containsKey(token));
    }

    public void declareObject(ObjectDescriptor od) {

    }

    public void addFieldToClass(Descriptor descriptor) {

    }

    private final ArrayList<TokenType> constantTypes =
            new ArrayList<>(Arrays.asList(TokenType.REAL, TokenType.INTEGER, TokenType.STRING));

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

        } else if (currentClass.fields.containsKey(symName)) {
            // TODO : Implement Pushing For Class Attributes
            System.out.println("Pushing thing is a field!");

        } else
            throw new Error("Literal is not declared within current scope!");
    }

    public void push_constant(String token, TokenType type) {
        if (globalDescriptors.containsKey(token)) {
            semanticStack.push(globalDescriptors.get(token));
            return;
        }
        switch (type) {
            case INTEGER:
                globalDescriptors.put(
                        token,
                        new PrimitiveDescriptor(token,
                                helper.allocateMemory(globalDescriptors.get("int"))
                                , PrimitiveType.INTEGER_PRIMITIVE)
                );
                break;
            case REAL:
                globalDescriptors.put(
                        token,
                        new PrimitiveDescriptor(token,
                                helper.allocateMemory(globalDescriptors.get("real")),
                                PrimitiveType.REAL_PRIMITIVE)
                );
                break;
            case STRING:

        }
    }

    public void add() {
        System.out.println("Adding");
        System.out.println(scanner.currentSymbol);
    }

    public void subtract() {

    }
}
