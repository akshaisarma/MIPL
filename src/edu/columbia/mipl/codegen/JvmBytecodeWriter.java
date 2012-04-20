/*
 * MIPL: Mining Integrated Programming Language
 *
 * File: JvmBytecodeWriter.java
 * Author A: YoungHoon Jung <yj2244@columbia.edu>
 * Author B: Akshai Sarma <as4107@columbia.edu>
 * Reviewer: Younghoon Jeon <yj2231@columbia.edu>
 * Description: JVM Byte Code Writer
 */
package edu.columbia.mipl.codegen;

import java.io.*;
import java.util.*;

import org.apache.bcel.classfile.*;
import org.apache.bcel.generic.*;
import org.apache.bcel.*;

import edu.columbia.mipl.runtime.*;
import edu.columbia.mipl.conf.Configuration;
import edu.columbia.mipl.datastr.*;

public class JvmBytecodeWriter extends InstructionWriter {
	String path;
	
	ClassGen _cg;
	ConstantPoolGen _cp;
	InstructionFactory _factory;
	
	InstructionList il;
	MethodGen method;

	int nextVar = 0;
	Stack<Integer> stack = new Stack<Integer>();
	
	int varProgram;		

	/* read http://commons.apache.org/bcel/manual.html */
	public JvmBytecodeWriter() {
	}

	public void init(String path, String filename) {
		this.path = path + "/" + filename + ".class";
		
		_cg = new ClassGen("MiplProgram", "java.lang.Object", "MiplProgram.java", Constants.ACC_PUBLIC | Constants.ACC_SUPER, new String[] {});
		_cp = _cg.getConstantPool();
		_factory = new InstructionFactory(_cg, _cp);
		
		il = new InstructionList();		
	    method = new MethodGen(Constants.ACC_PUBLIC, Type.VOID, Type.NO_ARGS, new String[] {}, "<init>", "MiplProgram", il, _cp);

	    // <init>
	    il.append(_factory.createLoad(Type.OBJECT, 0));
	    il.append(_factory.createInvoke("java.lang.Object", "<init>", Type.VOID, Type.NO_ARGS, Constants.INVOKESPECIAL));
	    il.append(_factory.createReturn(Type.VOID));
	    method.setMaxStack();
	    method.setMaxLocals();
	    _cg.addMethod(method.getMethod());
	    il.dispose();
		
		il = new InstructionList();
		method = new MethodGen(Constants.ACC_PUBLIC | Constants.ACC_STATIC, Type.VOID, new Type[] {new ArrayType(Type.STRING, 1)}, new String[] {"arg0"}, "main", "MiplProgram", il, _cp);
		nextVar++;		
		
		int t1 = nextVar++;
		int t2 = nextVar++;
		varProgram = nextVar++;
		
		// set configuration
		Configuration conf = Configuration.getInstance();
		List<String> servers = conf.getServers();
		
		il.append(_factory.createInvoke("edu.columbia.mipl.conf.Configuration", "getInstance", new ObjectType("edu.columbia.mipl.conf.Configuration"), Type.NO_ARGS, Constants.INVOKESTATIC));
	    il.append(_factory.createStore(Type.OBJECT, t1));
	    
	    il.append(_factory.createLoad(Type.OBJECT, t1));
	    il.append(new PUSH(_cp, conf.getMode()));
	    il.append(_factory.createInvoke("edu.columbia.mipl.conf.Configuration", "setMode", Type.VOID, new Type[] {Type.INT}, Constants.INVOKEVIRTUAL));

	    for (String server : servers) {
	    	il.append(_factory.createLoad(Type.OBJECT, t1));
		    il.append(new PUSH(_cp, server));
		    il.append(_factory.createInvoke("edu.columbia.mipl.conf.Configuration", "addServer", Type.VOID, new Type[] {Type.STRING}, Constants.INVOKEVIRTUAL));
	    }	    	    
	    
	    il.append(_factory.createInvoke("edu.columbia.mipl.runtime.KnowledgeTableFactory", "getKnowledgeTable", new ObjectType("edu.columbia.mipl.runtime.KnowledgeTable"), Type.NO_ARGS, Constants.INVOKESTATIC));
	    il.append(_factory.createStore(Type.OBJECT, t2));

	    il.append(_factory.createNew("edu.columbia.mipl.runtime.Program"));
	    il.append(InstructionConstants.DUP);
	    il.append(new PUSH(_cp, 1));
	    il.append(_factory.createNewArray(new ObjectType("edu.columbia.mipl.runtime.traverse.Traverser"), (short) 1));
	    il.append(InstructionConstants.DUP);
	    il.append(new PUSH(_cp, 0));
	    il.append(_factory.createNew("edu.columbia.mipl.runtime.execute.ProgramExecutor"));
	    il.append(InstructionConstants.DUP);
	    il.append(_factory.createInvoke("edu.columbia.mipl.runtime.execute.ProgramExecutor", "<init>", Type.VOID, Type.NO_ARGS, Constants.INVOKESPECIAL));
	    il.append(InstructionConstants.AASTORE);
	    il.append(_factory.createInvoke("edu.columbia.mipl.runtime.Program", "<init>", Type.VOID, new Type[] {new ArrayType(new ObjectType("edu.columbia.mipl.runtime.traverse.Traverser"), 1)}, Constants.INVOKESPECIAL));
	    il.append(_factory.createStore(Type.OBJECT, varProgram));	    	    
	}

	public String getName() {
		return "Bytecode";
	}
	
	public void createTerm(Term.Type type, double value) {
	}

	public void createTerm(Term.Type type, Term term1, Expression expr1) {
	}

	public void createTerm(Term.Type type, Expression expr1, Expression expr2) {
		switch (type) {
			case EQ:
				break;
			case LT:
				break;
			case LE:
				break;
			case GT:
				break;
			case GE:
				break;
			case NE:
				break;
		}

	}

	public void createTerm(Term.Type type, String name,
										PrimitiveMatrix<Double> matrix) {
	}

	public void createTerm(Term.Type type, Term term1, Term term2) {
		switch (type) {
			case ANDTERMS:
				break;
			case ORTERMS:
				break;
		}
	}

	public void createTerm(Term.Type type, Term term1) {
	// Term.Type.NOTTERM
	}

	public void createTerm(Term.Type type, String name, List<Term> arguments) {
	}

	public void createTerm(Term.Type type, String name) {
	}

	public void createTerm(Term.Type type, Expression expr1) {
	}

	public void createExpression(Expression.Type type, Term term1) {
	}

	public void createExpression(Expression.Type type, Expression expr1,
									Expression expr2) {
		switch (type) {
			case MINUS:
				break;
			case PLUS:
				break;
			case MULTI:
				break;
			case DIVIDE:
				break;
		}
	}
	
	public int genTerm(Term.Type type, String name, List<Term> arguments) {
		int listArgVar = nextVar++;
		if (arguments.size() > 0) {		
			il.append(_factory.createNew("java.util.ArrayList"));
			il.append(InstructionConstants.DUP);
			il.append(_factory.createInvoke("java.util.ArrayList", "<init>", Type.VOID, Type.NO_ARGS, Constants.INVOKESPECIAL));			
			il.append(_factory.createStore(Type.OBJECT, listArgVar));
			
			for (Term term : arguments) {			
				il.append(_factory.createLoad(Type.OBJECT, listArgVar));				
		    	il.append(_factory.createLoad(Type.OBJECT, genTerm(term)));
		        il.append(_factory.createInvoke("java.util.List", "add", Type.BOOLEAN, new Type[] {Type.OBJECT}, Constants.INVOKEINTERFACE));
		        il.append(InstructionConstants.POP);
			}
		}
		
		il.append(_factory.createNew("edu.columbia.mipl.runtime.Term"));
	    il.append(InstructionConstants.DUP);
		
	    switch (type) {
		case REGEXTERM:
			il.append(_factory.createFieldAccess("edu.columbia.mipl.runtime.Term$Type", "REGEXTERM", new ObjectType("edu.columbia.mipl.runtime.Term$Type"), Constants.GETSTATIC));
			break;
		case TERM:					    
		    il.append(_factory.createFieldAccess("edu.columbia.mipl.runtime.Term$Type", "TERM", new ObjectType("edu.columbia.mipl.runtime.Term$Type"), Constants.GETSTATIC));		    
			break;
		default:
			assert (false);
	    }
	    
	    il.append(new PUSH(_cp, name));
	    if (arguments.size() > 0)
	    	il.append(_factory.createLoad(Type.OBJECT, listArgVar));
	    else
	    	il.append(InstructionConstants.ACONST_NULL);
	    il.append(_factory.createInvoke("edu.columbia.mipl.runtime.Term", "<init>", Type.VOID, new Type[] {new ObjectType("edu.columbia.mipl.runtime.Term$Type"), Type.STRING, new ObjectType("java.util.List")}, Constants.INVOKESPECIAL));
	    
	    int target = nextVar++; 
	    il.append(_factory.createStore(Type.OBJECT, target));
		
		return target;
	}
	
	public int genTerm(Term.Type type, String name) {
		il.append(_factory.createNew("edu.columbia.mipl.runtime.Term"));
	    il.append(InstructionConstants.DUP);
		
	    switch (type) {
		case VARIABLE:		
		    il.append(_factory.createFieldAccess("edu.columbia.mipl.runtime.Term$Type", "VARIABLE", new ObjectType("edu.columbia.mipl.runtime.Term$Type"), Constants.GETSTATIC));		    
			break;
		case QUERYALL:
			il.append(_factory.createFieldAccess("edu.columbia.mipl.runtime.Term$Type", "QUERYALL", new ObjectType("edu.columbia.mipl.runtime.Term$Type"), Constants.GETSTATIC));			
			break;
		case REGEXQUERYALL:
			il.append(_factory.createFieldAccess("edu.columbia.mipl.runtime.Term$Type", "REGEXQUERYALL", new ObjectType("edu.columbia.mipl.runtime.Term$Type"), Constants.GETSTATIC));			
			break;
		case STRING:
			il.append(_factory.createFieldAccess("edu.columbia.mipl.runtime.Term$Type", "STRING", new ObjectType("edu.columbia.mipl.runtime.Term$Type"), Constants.GETSTATIC));			
			break;
		default:
			assert (false);
			break;
		}
	    
	    il.append(new PUSH(_cp, name));
	    il.append(_factory.createInvoke("edu.columbia.mipl.runtime.Term", "<init>", Type.VOID, new Type[] {new ObjectType("edu.columbia.mipl.runtime.Term$Type"), Type.STRING}, Constants.INVOKESPECIAL));
	    
	    int target = nextVar++;
	    il.append(_factory.createStore(Type.OBJECT, target));
	    
	    return target;
	}

	public int genTerm(Term term) {
		Term.Type type = term.getType(); 
		switch (type) {
			case REGEXTERM:
			case TERM:
				return genTerm(type, term.getName(), term.getArguments());				
			case VARIABLE:
			case QUERYALL:
			case REGEXQUERYALL:
			case STRING:
				return genTerm(type, term.getName());				
			default:
				assert (false);
		}
		
		return -1;
	}
	public void createFact(Fact.Type type, Term term) {
		// Fact.Type.FACT
		int curVar = nextVar;
		
		int t = genTerm(term);
								
		il.append(_factory.createLoad(Type.OBJECT, varProgram));
		il.append(_factory.createNew("edu.columbia.mipl.runtime.Fact"));
	    il.append(InstructionConstants.DUP);	    
	    il.append(_factory.createLoad(Type.OBJECT, t));
	    il.append(_factory.createInvoke("edu.columbia.mipl.runtime.Fact", "<init>", Type.VOID, new Type[] {new ObjectType("edu.columbia.mipl.runtime.Term")}, Constants.INVOKESPECIAL));
	    il.append(_factory.createInvoke("edu.columbia.mipl.runtime.Program", "add", Type.BOOLEAN, new Type[] {new ObjectType("edu.columbia.mipl.runtime.Command")}, Constants.INVOKEVIRTUAL));
	    il.append(InstructionConstants.POP);	    

//		resetDeclarationList();		
	}

	public void createFact(Fact.Type type, String name, List<String> names,
							List<Term> terms) {
	// Fact.Type.MATRIXASFACTS

	}

	public void createRule(Term term, Term source) {
		int curVar = nextVar;
		
		int t = genTerm(term);
		int s = genTerm(source);
		
		il.append(_factory.createLoad(Type.OBJECT, varProgram));
		il.append(_factory.createNew("edu.columbia.mipl.runtime.Rule"));
	    il.append(InstructionConstants.DUP);
	    il.append(_factory.createLoad(Type.OBJECT, t));
	    il.append(_factory.createLoad(Type.OBJECT, s));
	    il.append(_factory.createInvoke("edu.columbia.mipl.runtime.Rule", "<init>", Type.VOID, new Type[] {new ObjectType("edu.columbia.mipl.runtime.Term"), new ObjectType("edu.columbia.mipl.runtime.Term")}, Constants.INVOKESPECIAL));
	    il.append(_factory.createInvoke("edu.columbia.mipl.runtime.Program", "add", Type.BOOLEAN, new Type[] {new ObjectType("edu.columbia.mipl.runtime.Command")}, Constants.INVOKEVIRTUAL));
	    il.append(InstructionConstants.POP);	    
	}

	public void createQuery(Term term) {		
		int curVar = nextVar;
		
		int t = genTerm(term);
								
		il.append(_factory.createLoad(Type.OBJECT, varProgram));
		il.append(_factory.createNew("edu.columbia.mipl.runtime.Query"));
	    il.append(InstructionConstants.DUP);	    
	    il.append(_factory.createLoad(Type.OBJECT, t));
	    il.append(_factory.createInvoke("edu.columbia.mipl.runtime.Query", "<init>", Type.VOID, new Type[] {new ObjectType("edu.columbia.mipl.runtime.Term")}, Constants.INVOKESPECIAL));
	    il.append(_factory.createInvoke("edu.columbia.mipl.runtime.Program", "add", Type.BOOLEAN, new Type[] {new ObjectType("edu.columbia.mipl.runtime.Command")}, Constants.INVOKEVIRTUAL));
	    il.append(InstructionConstants.POP);
	}

	public void createJob(String name, List<Term> args, List<JobStmt> stmts) {

	}

	public void createJobStmt(JobStmt.Type type, JobExpr expr, JobStmt stmt1,
								JobStmt stmt2) {
		switch (type) {
			case IF:
				break;
			case WHILE:
				break;
			case DOWHILE:
				break;
		}
	}

	public void createJobStmt(JobStmt.Type type, List<JobStmt> stmts) {
	}

	public void createJobStmt(JobStmt.Type type, JobExpr expr) {
		switch (type) {
			case RETURN:
				break;
			case EXPR:
				break;
		}
	}

	public void createJobExpr(JobExpr.Type type, String name, JobExpr expr) {
		switch (type) {
			case ASSIGN:
				break;
			case MULASSIGN:
				break;
			case DIVASSIGN:
				break;
			case MODASSIGN:
				break;
			case ADDASSIGN:
				break;
			case SUBASSIGN:
				break;
		}
	}

	public void createJobExpr(JobExpr.Type type, JobExpr expr1,	JobExpr expr2) {
		switch (type) {
			case OR:
				break;
			case AND:
				break;
			case EQ:
				break;
			case NE:
				break;
			case LT:
				break;
			case GT:
				break;
			case LE:
				break;
			case GE:
				break;
			case ADD:
				break;
			case SUB:
				break;
			case MULT:
				break;
			case DIV:
				break;
			case MOD:
				break;
			case MULT_CELL:
				// TO DO Function needs to be implemented in PrimitiveOperations
				break;
			case DIV_CELL:
				// TO DO Function needs to be implemented in PrimitiveOperations
				break;
			case EXP_CELL:
				// TO DO Function needs to be implemented in PrimitiveOperations
				break;
		}
	}

	public void createJobExpr(JobExpr.Type type, JobExpr expr1) {
	}

	public void createJobExpr(JobExpr.Type type, Term term,
										List<ArrayIndex> indices1,
										List<ArrayIndex> indices2) {
	}

	public void createJobExpr(JobExpr.Type type, String name,
										List<JobExpr> exprs) {
	}

	public void createJobExpr(JobExpr.Type type, Term term) {
	}

// ObjectType i_stream = new ObjectType("java.io.InputStream");
	public void finish() {
		try {
			il.append(_factory.createReturn(Type.VOID));
		    method.setMaxStack();
		    method.setMaxLocals();
		    _cg.addMethod(method.getMethod());
		    il.dispose();
			
		    _cg.getJavaClass().dump(new FileOutputStream(path));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
