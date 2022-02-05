
import codegen.CodeGeneratorImpl;
import parser.Parser;
import scanner.LexicalAnalyser;

import java.io.*;

public class Main {

    public static void main(String[] args) throws IOException {
        String inputCoolFilePath = "cool-compiler/src/app.cool";
        String outputFilePath = "cool-compiler/src/out.s";
        String tablePath = "cool-compiler/src/parser/table.npt";

        if (args.length >= 1) {
            for (int i = 0; i < args.length; i++) {
                if (args[i].equals("--input")) {
                    inputCoolFilePath = args[i + 1];
                }
                if (args[i].equals("--output")) {
                    outputFilePath = args[i + 1];
                }
                if (args[i].equals("--table")) {
                    tablePath = args[i + 1];
                }
            }
        } else {
            System.out.println("Run like bellow:\njava <javaClassFile> --input <inputCoolFilePath> --output <outputFilePath> --table <tablePath>");
            return;
        }

        File file = new File(inputCoolFilePath);
        File out = new File(outputFilePath);
        if(out.exists()){
            out.delete();
            out.createNewFile();
        }
        FileWriter fw = new FileWriter(out);
        LexicalAnalyser scanner = new LexicalAnalyser(new FileReader(file));
        CodeGeneratorImpl codeGenerator = new CodeGeneratorImpl(scanner);
        Parser parser = new Parser(scanner, codeGenerator, tablePath);
        try{
            parser.parse();
            StringBuilder generatedCode = codeGenerator.generateCode();
            fw.write(generatedCode.toString());
            fw.flush();
            System.out.println(generatedCode);
        } catch (Error e){
            System.out.println(e.getMessage());
            System.out.println("Parser confronted with syntactical errors.");
        }
    }
}