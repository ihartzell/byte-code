import java.io.*;

public class Exprc 
{
	static BufferedReader in;
	static StreamTokenizer inTok;
	
	public static void main(String[] args) throws IOException 
	{
		// This variable will be used to denote different files for various expressions from expr.dat
		int file_num = 1;
		in = new BufferedReader(new FileReader("expr.dat"));
		inTok = new StreamTokenizer(in);

		inTok.eolIsSignificant(true);
		inTok.nextToken(); // lookahead token
		// processes a sequence of expressions holding onto the translation of the last
		// expression
		while (inTok.ttype != inTok.TT_EOF) 
		{
			if (inTok.ttype != inTok.TT_EOL) 
			{
				PrintWriter out = new PrintWriter(new FileWriter(file_num +".jbc"), true);
				try 
				{
					Node ae = expr();
					System.out.print(" \t\t Value => " + ae.value());
					System.out.println(" \t\t Type => " + ae.type());
					ae.code(out);
					if(ae.type() == "int")
					{
						out.print("i2d\n");
					}
					// Incrementing this variable, so when I get back to creating my PrinterWriter object,
					// The program prints to a new file.
					file_num++; 
					out.flush(); // Clearing out the stream.
				}
				catch (Exception e) 
				{
					System.out.println(" \t\t Error =>  " + e);
				} 
				finally 
				{
					// Had to add a check to make sure the inTok ttype doesn't equal the end of file, because other wise
					// an infinite loop can occur where it never finds the end of the file.
					while (inTok.ttype != inTok.TT_EOL && inTok.ttype != inTok.TT_EOF)
					{
						inTok.nextToken(); 
					}
					out.close(); // Closing the printer writer object.
				}
			}
			inTok.nextToken();
		}
		in.close(); // Closing the file to avoid memory leaks.
	}
	
	public static Node expr() throws Exception 
	{
		// PRE: Expects lookahead token.
		// POST: Lookahead token available.
		Node temp = term();
		Node temp1;
		while (inTok.ttype == '+') 
		{
			inTok.nextToken();
			temp1 = term();
			temp = new OpNode(temp, '+', temp1);
		}
		return temp;
	}
	public static Node term() throws Exception 
	{
		// PRE: Expects lookahead token.
		// POST: Lookahead token available.
		Node temp = factor();
		Node temp1;
		while (inTok.ttype == '*') 
		{
			inTok.nextToken();
			temp1 = factor();
			temp = new OpNode(temp, '*', temp1);
		}
		return temp;
	}

	public static Node factor() throws Exception 
	{
		// PRE: Expects lookahead token.
		// POST: Reads in a lookahead token.
		Node temp;
		// If the token is '(' the go to the next token and store the result of expr() call into temp.
		if(inTok.ttype == '(')
		{
			inTok.nextToken();
			temp = expr();
			// If ')' then go to the next token, if there is no closing brace then give an error.
			if (inTok.ttype == ')')
			{
				inTok.nextToken();
			}
			else
			{
				System.out.println("This is an illegal expression. Try adding a closing )");
			}
		}
		else if (inTok.ttype == inTok.TT_WORD) 
		{
			char ch = inTok.sval.charAt(0);
			// This is basically the base case. If i, a, or j then store in a node and go to the next token.
			if ((inTok.sval.length() == 1) && (ch == 'i') || (inTok.sval.length() == 1) && (ch == 'a') || (inTok.sval.length() == 1) && (ch == 'j') ) 
			{
				temp = new VarNode(ch);
				inTok.nextToken();
			}
			else
			{
				throw new Exception("Illegal Variable Name");
			}
		} 
		else
		{
			throw new Exception("Variable Token Expected");
		}
		return temp;
	}
} // End of Exprc class

interface Node 
{
	double value();

	String type();

	void code(PrintWriter out);
}

class VarNode implements Node 
{
	private char id;

	public VarNode(char _id) 
	{
		id = _id;
	}
	
	public double value() 
	{
		if(id == 'i')
		{
			return 2;
		}
		else if (id == 'j')
		{
			return 3;
		}
		else if(id == 'a')
		{
			return 1.0;
		}
		return 0.0;
	}
	
	public String type() 
	{
		if(id == 'i' || id == 'j')
		{
			return "int";
		}
		else
		{
			return "double";
		}
	}

	public void code(PrintWriter out) 
	{
		if(id == 'i')	
		{
			out.print("iload_0\n");
		}
		else if (id == 'j')
		{
			out.print("iload_3\n");
		}
		else if (id == 'a')
		{
			out.print("dload_1\n");
		}
	}
}

class OpNode implements Node 
{
	private Node LNode, RNode;
	private char op;

	public OpNode(Node _LNode, char _op, Node _RNode) 
	{
		LNode = _LNode;
		op = _op;
		RNode = _RNode;
	}

	// TO BE CHANGED - IMPLEMENT INTERFACE
	public double value() 
	{
		if(op == '+')
		{
			return LNode.value() + RNode.value();
		}
		if(op == '*')
		{
			return LNode.value() * RNode.value();
		}
		return 0.0;
	}

	public String type() 
	{
		if(LNode.type() == "double" && RNode.type() == "double")
		{
			return "double";
		}
		else if(LNode.type() == "double" && RNode.type() == "int")
		{
			return "double";
		}
		else if(LNode.type() == "int" && RNode.type() == "double")
		{
			return "double";
		}
		else if(LNode.type() == "int" && RNode.type() == "int")
		{
			return "int";
		}
		return "This is an illegal type.";
	}

	public void code(PrintWriter out) 
	{
		if(op == '+')
		{
			if(LNode.type() == "int" && RNode.type() == "int")
			{
				LNode.code(out);
				RNode.code(out);
				out.print("iadd\n");
			}
			else if (LNode.type() == "double" && RNode.type() == "double")
			{
				LNode.code(out);
				RNode.code(out);
				out.print("dadd\n");
			}
			else if (LNode.type() == "int" && RNode.type() == "double")
			{
				LNode.code(out);
				if(LNode.type() == "int")
				{
					out.print("i2d\n");
				}
				RNode.code(out);
				out.print("dadd\n");
			}
			else if (LNode.type() == "double" && RNode.type() == "int")
			{
				LNode.code(out);
				RNode.code(out);
				if(RNode.type() == "int")
				{
					out.print("i2d\n");
				}
				out.print("dadd\n");
			}
		}
		else if(op == '*')
		{
			if (LNode.type() == "int" && RNode.type() == "int")
			{
				LNode.code(out);
				RNode.code(out);
				out.print("imul\n");
			}
			else if (LNode.type() == "double" && RNode.type() == "double")
			{
				LNode.code(out);
				RNode.code(out);
				out.print("dmul\n");
			}
			else if (LNode.type() == "int" && RNode.type() == "double")
			{
				LNode.code(out);
				if(LNode.type() == "int")
				{
					out.print("i2d\n");
				}
				RNode.code(out);
				out.print("dmul\n");
			}
			else if (LNode.type() == "double" && RNode.type() == "int")
			{
				LNode.code(out);
				RNode.code(out);
				if(RNode.type() == "int")
				{
					out.print("i2d\n");
				}
				out.print("dmul\n");
			}
		}
	}
}
