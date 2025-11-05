import java.io.*;
import java.util.*;

class Symbol {
    String name;
    int address;

    Symbol(String name, int address) {
        this.name = name;
        this.address = address;
    }
}

public class TwoPassAssembler {
    static Map<String, Integer> MOT = new HashMap<>(); // Machine Opcode Table
    static Map<String, Integer> POT = new HashMap<>(); // Pseudo Opcode Table
    static List<Symbol> SYMTAB = new ArrayList<>();    // Symbol Table
    static List<String> INTERMEDIATE = new ArrayList<>(); // Intermediate Code

    static int LC = 0; // Location Counter

    public static void main(String[] args) throws Exception {
        initializeTables();

        // Input Assembly Program (as list of lines)
        String[] program = {
            "START 100",
            "MOVER AREG, NUM1",
            "ADD BREG, NUM2",
            "LABEL1 SUB AREG, NUM3",
            "MULT AREG, NUM4",
            "MOVEM AREG, RESULT",
            "END"
        };

        // Pass I
        passOne(program);

        System.out.println("\n--- SYMBOL TABLE ---");
        for (Symbol s : SYMTAB)
            System.out.println(s.name + "\t" + s.address);

        System.out.println("\n--- INTERMEDIATE CODE ---");
        for (String line : INTERMEDIATE)
            System.out.println(line);

        // Pass II
        System.out.println("\n--- MACHINE CODE ---");
        passTwo();
    }

    static void initializeTables() {
        // Machine Opcodes (Format: Mnemonic -> Opcode)
        MOT.put("STOP", 0);
        MOT.put("ADD", 1);
        MOT.put("SUB", 2);
        MOT.put("MULT", 3);
        MOT.put("MOVER", 4);
        MOT.put("MOVEM", 5);
        MOT.put("COMP", 6);
        MOT.put("BC", 7);
        MOT.put("DIV", 8);
        MOT.put("READ", 9);
        MOT.put("PRINT", 10);

        // Pseudo Opcodes
        POT.put("START", 1);
        POT.put("END", 2);
        POT.put("ORIGIN", 3);
        POT.put("EQU", 4);
        POT.put("LTORG", 5);
        POT.put("DS", 6);
        POT.put("DC", 7);
    }

    static void passOne(String[] program) {
        for (String line : program) {
            String[] tokens = line.split("[ ,]+");
            String label = "", opcode = "", operand1 = "", operand2 = "";

            if (tokens.length == 0) continue;

            // Handle label
            if (!MOT.containsKey(tokens[0]) && !POT.containsKey(tokens[0])) {
                label = tokens[0];
                addSymbol(label, LC);
                opcode = tokens[1];
                if (tokens.length > 2) operand1 = tokens[2];
                if (tokens.length > 3) operand2 = tokens[3];
            } else {
                opcode = tokens[0];
                if (tokens.length > 1) operand1 = tokens[1];
                if (tokens.length > 2) operand2 = tokens[2];
            }

            if (POT.containsKey(opcode)) {
                switch (opcode) {
                    case "START":
                        LC = Integer.parseInt(operand1);
                        INTERMEDIATE.add("(AD,01)\t(C," + LC + ")");
                        break;
                    case "END":
                        INTERMEDIATE.add("(AD,02)");
                        break;
                }
            } else if (MOT.containsKey(opcode)) {
                INTERMEDIATE.add("(IS," + String.format("%02d", MOT.get(opcode)) + ")\t" +
                        operand1 + "\t" + operand2);
                LC++;
            }
        }
    }

    static void addSymbol(String name, int addr) {
        for (Symbol s : SYMTAB)
            if (s.name.equals(name)) return;
        SYMTAB.add(new Symbol(name, addr));
    }

    static void passTwo() {
        for (String ic : INTERMEDIATE) {
            if (ic.startsWith("(IS")) {
                String[] parts = ic.split("\t");
                String opcode = parts[0].substring(4, 6);
                String reg = parts.length > 1 ? parts[1] : "";
                String sym = parts.length > 2 ? parts[2] : "";

                int symAddr = getSymbolAddress(sym);
                System.out.println(opcode + "\t" + reg + "\t" + symAddr);
            }
        }
    }

    static int getSymbolAddress(String name) {
        for (Symbol s : SYMTAB)
            if (s.name.equals(name))
                return s.address;
        return 0;
    }
}
