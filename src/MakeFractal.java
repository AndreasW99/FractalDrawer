import java.util.Arrays;

import javax.net.ssl.SSLEngineResult.Status;
import javax.swing.Box;

public class MakeFractal { 
private final static int NOT_FOUND=-1;
private String alphaNumeric;
private String computedFractal; // the initial string is the axion
private String initialAxion; // the initial string is the axion
private Integer index,numSymbols,n,size; 
private String[] symbols;
private String[] rules;
private Integer charsPerLine=60;



// constructor
public MakeFractal(String[] symbols,Integer numSymbols,String initialAxion,String[] rules,Integer n) {
this.numSymbols=numSymbols;
this.symbols=new String[this.numSymbols];
this.rules=new String[this.numSymbols];
for(int i=0;i<this.numSymbols;i++) {
   this.symbols[i]=symbols[i];
   this.rules[i]=rules[i];
   }
this.computedFractal=initialAxion;     // the initial string is the axion
this.n=n;
}

public String buildFractal() {
System.out.println("In buildFractal\n");
// print out symbols and their production rules
System.out.println("Symbol Table");
for(int i=0;i<numSymbols;i++) {
    System.out.println("symbol(" + i + ")=" + symbols[i] + 
                       "   rule(" + i +")=" + rules[i] + "\n");
}

// s is a stack
StackADT<String> s = new LinkedStack<String>();

n=0;
for (int i = 0; i<=n.intValue(); i++)			//I believe this for statement was done incorrectly.
{
	for (index=0; index<computedFractal.length(); index++)     		//range begins from 0 to 
	{
		String q = computedFractal.substring(index, index+1); 
		
		if (in(q,symbols)== NOT_FOUND)			//if q is a symbol then push q onto the stack if not, return not found.
			s.push(q);							// q is pushed	
		else
			s.push(rules[index]);				// if -1 is returned then it is a symbol in the index, then push (rules(index)) instead which is the symbol index.
	}
}

computedFractal = "";					// empty string 
while(!s.isEmpty())						// if the stack is NOT empty.
{
	String topstack = s.pop();				// top thing on the stack is popped and stored as variable name "topstack".
	s.push(topstack);;						// all items from the stack are are popped and added to "topstack".
	computedFractal = computedFractal+topstack; 	//finnaly added to computeFractal
}

return computedFractal;
//returned 




}
// Return the index of the character in symbols 
// or -1 if it is not there
public Integer in(String alphaNumeric,String[] symbols) {
for(int i=0;i<symbols.length;i++)
    {
    if(alphaNumeric.equals(symbols[i])) return(i);
    }
return(NOT_FOUND);
}

// Pretty print the computed fractal
public void prettyPrint() {
String str=computedFractal;
size=str.length();
System.out.println("\nPretty print the final fractal (60 characters per line)\n");
System.out.println("-------------------------------------------------------\n");
while(charsPerLine < size) {
    // print out substrings of string of length charsPerLine
    System.out.println(str.substring(0,charsPerLine-1));
    str=str.substring(charsPerLine,size);
    size=str.length();
} 
// print last bit of string
System.out.println(str);
}
} // MakeFractal
