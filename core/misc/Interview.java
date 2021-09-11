import java.util.List;
import java.util.ArrayList;

/**
 * If A..Z are encoded as 1..26.
 * Write a program to decode an input string of numbers.
 * List all possible result.
 * 
 * Testcase
 * decode("101") - "JA"
 * decode("211") - "BAA", "BK", "UA"
 * decode("2019") - "TAI", "TS"
 */
class Interview 
{
    /**
     * utility function to decode a number
     */
    private String lookup(String s) 
    {
        switch (s) {
            case "1": return "A";
            case "2": return "B";
            case "3": return "C";
            case "4": return "D";
            case "5": return "E";
            case "6": return "F";
            case "7": return "G";
            case "8": return "H";
            case "9": return "I";
            case "10": return "J";
            case "11": return "K";
            case "12": return "L";
            case "13": return "M";
            case "14": return "N";
            case "15": return "O";
            case "16": return "P";
            case "17": return "Q";
            case "18": return "R";
            case "19": return "S";
            case "20": return "T";
            case "21": return "U";
            case "22": return "V";
            case "23": return "W";
            case "24": return "X";
            case "25": return "Y";
            case "26": return "Z";
            default: return null;
        }
    }

    /**
     * generate all possible encodings
     * @param list: list to collect results found so far
     * @param acc: accumulated encoded value 
     * @param s: the rest of input to be decoded
     */
    private void generate(List<String> list, String acc, String s) 
    {
        if (s.isEmpty()) 
        {
            // nothing left to be decoded so this is a result
            list.add(acc);
        } 
        else if (s.length() == 1) 
        {
            // just one digit to be decoded
            generate(list, acc + lookup(s), "");
        } 
        else 
        {
            // try decoding the first digit
            String t1 = lookup(s.substring(0,1)); 

            if (t1 != null)
                generate(list, acc + t1, s.substring(1));
    
            // try decoding the first 2 digits        
            String t2 = lookup(s.substring(0,2)); 
            if (t2 != null)
                generate(list, acc + t2, s.substring(2));
        }
    }

    /**
     * find all possible decodings of given input string of numbers
     * @param source input string
     * @return list of all possible decodings
     */
    public List<String> decode(String source) 
    {
        List<String> ret = new ArrayList<String>();

        if (source != null)
            generate(ret, "", source);

        ret.stream().forEach(System.out::println);

        return ret;
    }    
    
    public static void main (String[] args) throws java.lang.Exception
    {
        new Interview().decode("2019");
    }    
}
