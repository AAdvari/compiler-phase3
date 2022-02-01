package codegen;

import codegen.dscp.Descriptor;
import codegen.helper.Helper;
import scanner.LexicalAnalyser;
import java.util.ArrayList;
import java.util.Stack;

public class CodeGeneratorImpl implements CodeGenerator {

    public LexicalAnalyser scanner;
    public Stack<Descriptor> semanticStack;
    public Helper helper;
    public Descriptor currentClass;
    public ArrayList<Descriptor> activeFunctions;

    public CodeGeneratorImpl(LexicalAnalyser scanner){
        this.scanner = scanner;
        semanticStack = new Stack<>();
        helper = new Helper();
        activeFunctions = new ArrayList<>(3);
    }

    @Override
    public void doSemantic(String sem) {
        switch (sem){
            case "push":
                push();
                break;
            case "add":
                add();
                break;
            case "subtract":
                subtract();
                break;
        }
    }

    public void push(){
        System.out.println("Pushing : ");
        System.out.println(scanner.currentSymbol);

    }
    public void add(){
        System.out.println("Adding");
        System.out.println(scanner.currentSymbol);
    }
    public void subtract(){
    }
}
