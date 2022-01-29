package app;

import java.io.*;

public class SyntaxHighlighter {
    public static final String SPACE = "&nbsp;";
    public static final String TAB = "&nbsp;&nbsp;&nbsp;";
    public static final String ENTER = "<br>";
    public static int lineNumber = 1;
    public static FileWriter fw;
    public static FileReader fr;
    public static String inputPath;

    public static void main(String[] args) throws IOException {
        if (args.length != 0)
            inputPath = args[0];
        else{
            inputPath = "app.cool";
        }

        File inputFile = new File(inputPath);
        if (!inputFile.exists()) {
            throw new FileNotFoundException();
        }


        fr = new FileReader(inputFile);
        Scanner scanner = new Scanner(fr);


        File outputFile = new File("output.html");
        boolean isCreated = outputFile.exists();
        if (isCreated) {
            outputFile.delete();
            outputFile.createNewFile();
        }

        fw = new FileWriter(outputFile, true);
        fw.write(htmlStartPattern("#222222"));
        fw.write(writeNumber());


        Symbol symbol = scanner.nextToken();
        while (symbol != null) {
            System.out.println(symbol);
            String token = symbol.getToken();
            switch (symbol.type) {
                case TAB -> fw.write(TAB);
                case SPACE -> fw.write(SPACE);
                case NEW_LINE -> {
                    fw.write(ENTER);
                    fw.write(writeNumber());
                }
                case INTEGER -> fw.write(coloredText(token, "#F59762", false));
                case REAL -> fw.write(coloredText(token, "#F59762", true));
                case RESERVED -> fw.write(coloredText(token, "#FC618D", false));
                case COMMENT -> fw.write(commentText(token));
                case STRING -> fw.write(coloredText(token, "#FCE566", false));
                case IDENTIFIER -> fw.write(coloredText(token, "#FFFFFF", false));
                case OPERATOR -> fw.write(coloredText(token, "#00FFFF", false));
                case UNDEFINED -> fw.write(coloredText(token, "#FF0000", false));
                case SPECIAL -> fw.write(coloredText(token, "#EE82EE", true));
            }
            symbol = scanner.nextToken();
        }
        fw.write(htmlEndPattern());
        fw.flush();

    }

    public static String htmlStartPattern(String pageColorCode) {
        return "<!DOCTYPE html>\n"
                + "<html>\n"
                + "     <head>\n"
                + "     </head>\n"
                + "         <body style=\"background-color:" + pageColorCode + "\">";
    }

    public static String htmlEndPattern() {
        return "         </body>"
                + "</html>";
    }

    public static String coloredText(String string, String color, boolean isItalic) {
        String italicStart = "";
        String italicEnd = "";
        if (isItalic) {
            italicStart = "<i>";
            italicEnd = "</i>";
        }
        return "<span style=\"color:" + color + "\">" + italicStart + string + italicEnd + "</span>";
    }

    public static String commentText(String text) throws IOException {
        String commentedText = "";
        for (int i = 0; i < text.length(); i++) {
            char c = text.charAt(i);
            switch (c) {
                case '\t' -> commentedText += TAB;
                case ' ' -> commentedText += SPACE;
                case '\n' -> {
                    commentedText += ENTER;
                    commentedText += writeNumber();
                }
                default -> commentedText += c;
            }
        }
        return "<span style=\"color:#69676C\">" + commentedText + "</span>";
    }

    public static String writeNumber() {
        String string = coloredText(lineNumber + " ", "white", false);
        lineNumber++;
        return string;
    }
}
