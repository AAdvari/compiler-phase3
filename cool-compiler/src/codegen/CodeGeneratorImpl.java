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
        TokenType symType = currentSymbol.getType();

        PrimitiveType type;
        if (symType == TokenType.INTEGER)
            type = PrimitiveType.INTEGER_PRIMITIVE;
        else if (symType == TokenType.REAL)
            type = PrimitiveType.REAL_PRIMITIVE;
        else if (symType == TokenType.STRING)
            type = PrimitiveType.STRING_PRIMITIVE;
        else if (token.equals("false") || token.equals("true"))
            type = PrimitiveType.BOOLEAN_PRIMITIVE;
        else
            throw new Error("Not a valid primitive type");
        PrimitiveDescriptor currentVarDescriptor =
                new PrimitiveDescriptor(token, helper.allocateMemory(pd), type);
        currentMethod.addVariable(token, currentVarDescriptor);

    }


    public void declareObject(ObjectDescriptor od) {

    }

    public void addFieldToClass(Descriptor descriptor) {

    }

    public void declareArray() {
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
