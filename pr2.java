import java.util.*;
import java.util.regex.*;

public class MacroProcessor {

    static class MntEntry {
        String name;
        int mdtIndex;
        List<String> params;
        MntEntry(String n, int i, List<String> p){name=n;mdtIndex=i;params=p;}
    }

    static class MdtEntry {
        int index;
        String line;
        MdtEntry(int idx, String l){index=idx;line=l;}
    }

    static Map<String, MntEntry> MNT = new LinkedHashMap<>();
    static List<MdtEntry> MDT = new ArrayList<>();
    static List<String> INTERMEDIATE = new ArrayList<>();
    static int mdtPtr = 0;

    public static void main(String[] args) {
        String[] source = {
            "START",
            "MACRO",
            "INCR &ARG1,&ARG2",
            "    LOAD &ARG1",
            "    ADD &ARG2",
            "    STORE &ARG1",
            "MEND",
            "MACRO",
            "SWAP &X,&Y",
            "    TEMP =&X",
            "    &X =&Y",
            "    &Y =TEMP",
            "MEND",
            "READ A",
            "INCR A,1",
            "SWAP A,B",
            "PRINT A",
            "END"
        };

        passOne(source);
        List<String> expanded = passTwo(INTERMEDIATE);

        for (String s : expanded)
            System.out.println(s);
    }

    static void passOne(String[] src) {
        boolean inMacroDef = false;
        String currentMacro = null;
        List<String> currentParams = null;

        for (int i = 0; i < src.length; ++i) {
            String raw = src[i].trim();
            if (raw.isEmpty()) continue;

            if (raw.equalsIgnoreCase("MACRO")) {
                inMacroDef = true;
                i++;
                if (i >= src.length) break;
                String header = src[i].trim();
                String[] headerParts = header.split("\\s+", 2);
                String mname = headerParts[0];
                String paramsPart = headerParts.length > 1 ? headerParts[1].trim() : "";
                List<String> params = new ArrayList<>();
                if (!paramsPart.isEmpty()) {
                    for (String p : paramsPart.split(",")) params.add(p.trim());
                }
                MNT.put(mname, new MntEntry(mname, mdtPtr, params));
                currentMacro = mname;
                currentParams = params;
                MDT.add(new MdtEntry(mdtPtr++, "HEADER " + mname));
                continue;
            }

            if (inMacroDef) {
                if (raw.equalsIgnoreCase("MEND")) {
                    MDT.add(new MdtEntry(mdtPtr++, "MEND"));
                    inMacroDef = false;
                    currentMacro = null;
                    currentParams = null;
                } else {
                    String lineWithPlaceholders = raw;
                    if (currentParams != null) {
                        for (int p = 0; p < currentParams.size(); ++p) {
                            String formal = Pattern.quote(currentParams.get(p));
                            String repl = "(P," + (p+1) + ")";
                            lineWithPlaceholders = lineWithPlaceholders.replaceAll(formal, repl);
                        }
                    }
                    MDT.add(new MdtEntry(mdtPtr++, lineWithPlaceholders));
                }
            } else {
                INTERMEDIATE.add(raw);
            }
        }
    }

    static List<String> passTwo(List<String> intermediate) {
        List<String> output = new ArrayList<>();

        for (String line : intermediate) {
            if (line.trim().isEmpty()) continue;
            String[] parts = line.split("\\s+", 2);
            String firstToken = parts[0];
            String rest = parts.length > 1 ? parts[1].trim() : "";

            if (MNT.containsKey(firstToken)) {
                MntEntry mntEntry = MNT.get(firstToken);
                List<String> actuals = new ArrayList<>();
                if (!rest.isEmpty()) for (String a : rest.split(",")) actuals.add(a.trim());

                Map<String, String> callALA = new HashMap<>();
                for (int p = 0; p < mntEntry.params.size(); ++p) {
                    callALA.put("(P," + (p+1) + ")", actuals.size() > p ? actuals.get(p) : "");
                }

                int idx = mntEntry.mdtIndex;
                while (idx < MDT.size()) {
                    MdtEntry me = MDT.get(idx++);
                    String mline = me.line;
                    if (mline.equals("MEND")) break;
                    if (mline.startsWith("HEADER")) continue;
                    for (Map.Entry<String, String> e : callALA.entrySet())
                        mline = mline.replace(e.getKey(), e.getValue());
                    output.add(mline);
                }
            } else {
                output.add(line);
            }
        }
        return output;
    }
}
