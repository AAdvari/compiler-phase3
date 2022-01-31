package codegen;

import codegen.dscp.Descriptor;
import codegen.helper.Helper;

import java.util.Stack;

public class CodeGeneratorImpl implements CodeGenerator {
    public Stack<Descriptor> semanticStack;
    public Helper helper;

    public CodeGeneratorImpl(){
        semanticStack = new Stack<>();
        helper = new Helper();
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

    }
    public void add(){

    }
    public void subtract(){

    }
}
