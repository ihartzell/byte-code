import java.io.*;
import java.util.ArrayDeque;

public class Exprv 
{
	public static void main(String[] args) throws IOException 
	{
		// I'm making a stack of numbers being doubles and integers.
		ArrayDeque<Number> stack = new ArrayDeque<Number>();
		// The first argument passed into the console is the filename, so args[0]
		String fileArg = args[0];
		// This variable is for reading from each line in the byte code files.
		String line;
		// Creating my filereader.
		BufferedReader in = new BufferedReader (new FileReader(fileArg));
		// Here I'm converting i, a, and j that are strings into wrapper objects of their appropriate types.
		int i = (int)Integer.parseInt(args[1]);
		double a = (double)Double.parseDouble(args[2]);
		int j = (int)Integer.parseInt(args[3]);
		// Read from the file as long as there's something.
		while((line = in.readLine()) != null)
		{
			// *Note, have to use equals and not == because .equals checks the actual contents where == has to do with addresses.
			if(line.equals("iload_0"))
			{
				// In the case of iload_0 push i.
				stack.push(i);	
			}
			else if(line.equals("iload_3"))
			{
				// In the case of iload_3 push j.
				stack.push(j);
			}
			else if(line.equals("dload_1"))
			{
				// IN the case of dload_1 push a.
				stack.push(a);
			}
			else if(line.equals("i2d"))
			{
				// I'm storing the popped value from the stack into a variable, if it's not stored then the value will be lost
				// and I'll get an underflow issue.
				Number popped_i_or_j = stack.pop();
				// Converting from Number to double.
				popped_i_or_j = popped_i_or_j.doubleValue();
				// Pushing back onto stack as a primitive double now.
				stack.push(popped_i_or_j);;
			}
			else if(line.equals("dmul"))
			{
				// Casting the previous popped values from the stack as doubles, multiplying them, and putting this result on the stack.
				stack.push((double) stack.pop() * (double) stack.pop());
			}
			else if(line.equals("dadd"))
			{
				// Casting the previous popped values from the stack as ints, adding them, and putting this result on the stack.
				stack.push((double) stack.pop() + (double) stack.pop());
			}
			else if(line.equals("iadd"))
			{
				// Casting the previous popped values from the stack as ints, adding them, and putting this result on the stack.
				stack.push((int) stack.pop() + (int) stack.pop());
			}
			else if(line.equals("imul"))
			{
				// Casting the previous popped values from the stack as ints multiplying them, and putting this result on the stack.
				stack.push((int) stack.pop() * (int) stack.pop());
			}		
		}
		// Storing the results of performing all the math operations while on the stack into this variable.
		// I convert it from type Number to a double and display the result.
		Number byte_code_result = stack.pop();
		byte_code_result = byte_code_result.doubleValue();
		System.out.println(byte_code_result);
		in.close(); // Closing file reader.
	}
}
