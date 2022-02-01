package codegen;

import codegen.dscp.ClassDescriptor;
import codegen.dscp.Descriptor;
import codegen.dscp.MethodDescriptor;
import codegen.helper.Helper;
import scanner.LexicalAnalyser;
import java.util.ArrayList;
import java.util.Stack;
import scanner.Symbol;
public class CodeGeneratorImpl implements CodeGenerator {

    public LexicalAnalyser scanner;
    public Stack<Descriptor> semanticStack;
    public Helper helper;
    public ClassDescriptor currentClass;
    public MethodDescriptor currentMethod;
    public Descriptor[] objectDescriptors;
    public boolean inDcl;
    public CodeGeneratorImpl(LexicalAnalyser scanner){
        this.scanner = scanner;
        semanticStack = new Stack<>();
        helper = new Helper();
        inDcl = false;
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
        Symbol currentSym = scanner.currentSymbol;


    }
    public void add(){
        System.out.println("Adding");
        System.out.println(scanner.currentSymbol);
    }
    public void subtract(){

    }
}
