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
import edu.columbia.mipl.builtin.BuiltinTable;
import edu.columbia.mipl.conf.Configuration;
import edu.columbia.mipl.datastr.*;

public class JvmBytecodeWriter extends InstructionWriter {
	String path;
	
	ClassGen _cg;
	ConstantPoolGen _cp;
	InstructionFactory _factory;
	
	int nextVar = 0;
	InstructionList il;
	MethodGen method;
	
	Map<String, Integer> declarationTab;
	
	int varConfiguration;
	int varKnowledgeTable;
	int varProgram;
	
	int varReturn;

	/* read http://commons.apache.org/bcel/manual.html */
	public JvmBytecodeWriter() {
	}
	
	private void resetDeclarationList() {
		declarationTab = new HashMap<String, Integer>();
	}

	public void init(String path, String filename) {
		this.path = path + "/" + filename + ".class";
		
		_cg = new ClassGen("MiplProgram", "java.lang.Object", "MiplProgram.java", Constants.ACC_PUBLIC | Constants.ACC_SUPER, new String[] {});
		_cp = _cg.getConstantPool();
		_factory = new InstructionFactory(_cg, _cp);
		
		il = new InstructionList();		
	    method = new MethodGen(Constants.ACC_PUBLIC, Type.VOID, Type.NO_ARGS, new String[] {}, "<init>", "MiplProgram", il, _cp);
	    
	    resetDeclarationList();

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
		
		varConfiguration = nextVar++;
		varKnowledgeTable = nextVar++;
		varProgram = nextVar++;
		
		// set configuration
		Configuration conf = Configuration.getInstance();
		List<String> servers = conf.getServers();
		
		il.append(_factory.createInvoke("edu.columbia.mipl.conf.Configuration", "getInstance", new ObjectType("edu.columbia.mipl.conf.Configuration"), Type.NO_ARGS, Constants.INVOKESTATIC));
	    il.append(_factory.createStore(Type.OBJECT, varConfiguration));
	    
	    il.append(_factory.createLoad(Type.OBJECT, varConfiguration));
	    il.append(new PUSH(_cp, conf.getMode()));
	    il.append(_factory.createInvoke("edu.columbia.mipl.conf.Configuration", "setMode", Type.VOID, new Type[] {Type.INT}, Constants.INVOKEVIRTUAL));

	    for (String server : servers) {
	    	il.append(_factory.createLoad(Type.OBJECT, varConfiguration));
		    il.append(new PUSH(_cp, server));
		    il.append(_factory.createInvoke("edu.columbia.mipl.conf.Configuration", "addServer", Type.VOID, new Type[] {Type.STRING}, Constants.INVOKEVIRTUAL));
	    }	    	    
	    
	    il.append(_factory.createInvoke("edu.columbia.mipl.runtime.KnowledgeTableFactory", "getKnowledgeTable", new ObjectType("edu.columbia.mipl.runtime.KnowledgeTable"), Type.NO_ARGS, Constants.INVOKESTATIC));
	    il.append(_factory.createStore(Type.OBJECT, varKnowledgeTable));

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
	
	public void createTerm(Term.Type type, double value) { }
	public void createTerm(Term.Type type, Term term1, Expression expr1) { }
	public void createTerm(Term.Type type, Expression expr1, Expression expr2) { }
	public void createTerm(Term.Type type, String name, PrimitiveMatrix<Double> matrix) { }
	public void createTerm(Term.Type type, Term term1, Term term2) { }
	public void createTerm(Term.Type type, Term term1) { }
	public void createTerm(Term.Type type, String name, List<Term> arguments) { }
	public void createTerm(Term.Type type, String name) { if (type == Term.Type.VARIABLE) declarationTab.put(name, -1); }
	public void createTerm(Term.Type type, Expression expr1) { }
	public void createExpression(Expression.Type type, Term term1) { }
	public void createExpression(Expression.Type type, Expression expr1, Expression expr2) { }
	
	public int genTerm(Term.Type type, double value) {
		int target = nextVar++;
		
		il.append(_factory.createNew("edu.columbia.mipl.runtime.Term"));
	    il.append(InstructionConstants.DUP);
	    il.append(_factory.createFieldAccess("edu.columbia.mipl.runtime.Term$Type", "NUMBER", new ObjectType("edu.columbia.mipl.runtime.Term$Type"), Constants.GETSTATIC));
	    il.append(new PUSH(_cp, value));
	    il.append(_factory.createInvoke("edu.columbia.mipl.runtime.Term", "<init>", Type.VOID, new Type[] {new ObjectType("edu.columbia.mipl.runtime.Term$Type"), Type.DOUBLE}, Constants.INVOKESPECIAL));
	    il.append(_factory.createStore(Type.OBJECT, target));
		
		return target;
	}
	
	public int genTerm(Term.Type type, Term term1, Expression expr1) { assert (false); return -1; }
	public int genTerm(Term.Type type, Expression expr1, Expression expr2) { assert (false); return -1; }
	
	public int genTerm(Term.Type type, String name, PrimitiveMatrix<Double> matrix) {
		int arrayVar = nextVar++;
		int target = nextVar++;
		
		il.append(_factory.createNew("edu.columbia.mipl.datastr.PrimitiveDoubleArray"));
	    il.append(InstructionConstants.DUP);
	    il.append(new PUSH(_cp, matrix.getRow()));
	    il.append(new PUSH(_cp, matrix.getCol()));
	    il.append(_factory.createInvoke("edu.columbia.mipl.datastr.PrimitiveDoubleArray", "<init>", Type.VOID, new Type[] {Type.INT, Type.INT}, Constants.INVOKESPECIAL));
	    il.append(_factory.createStore(Type.OBJECT, arrayVar));
	    	    
	    for (int i = 0; i < matrix.getRow(); i++) {
	    	for (int j = 0; j < matrix.getCol(); j++) {
	    		il.append(_factory.createLoad(Type.OBJECT, arrayVar));
	    		il.append(new PUSH(_cp, i));
	    	    il.append(new PUSH(_cp, j));
	    	    il.append(new PUSH(_cp, matrix.getValue(i,  j)));
	    	    il.append(_factory.createInvoke("java.lang.Double", "valueOf", new ObjectType("java.lang.Double"), new Type[] {Type.DOUBLE}, Constants.INVOKESTATIC));
	    	    il.append(_factory.createInvoke("edu.columbia.mipl.datastr.PrimitiveDoubleArray", "setValue", Type.VOID, new Type[] {Type.INT, Type.INT, Type.OBJECT}, Constants.INVOKEVIRTUAL));
	    	}
	    }
	    
	    il.append(_factory.createNew("edu.columbia.mipl.runtime.Term"));
	    il.append(InstructionConstants.DUP);
	    il.append(_factory.createFieldAccess("edu.columbia.mipl.runtime.Term$Type", "MATRIX", new ObjectType("edu.columbia.mipl.runtime.Term$Type"), Constants.GETSTATIC));
	    il.append(new PUSH(_cp, name));
	    il.append(_factory.createNew("edu.columbia.mipl.datastr.PrimitiveMatrix"));
	    il.append(InstructionConstants.DUP);
	    il.append(_factory.createLoad(Type.OBJECT, arrayVar));
	    il.append(_factory.createInvoke("edu.columbia.mipl.datastr.PrimitiveMatrix", "<init>", Type.VOID, new Type[] {new ObjectType("edu.columbia.mipl.datastr.PrimitiveArray")}, Constants.INVOKESPECIAL));
	    il.append(_factory.createInvoke("edu.columbia.mipl.runtime.Term", "<init>", Type.VOID, new Type[] {new ObjectType("edu.columbia.mipl.runtime.Term$Type"), Type.STRING, new ObjectType("edu.columbia.mipl.datastr.PrimitiveMatrix")}, Constants.INVOKESPECIAL));
	    il.append(_factory.createStore(Type.OBJECT, target));
	    
	    return target;
	}
	
	public int genTerm(Term.Type type, Term term1, Term term2) {
		int target = nextVar++;
		
		int t1 = genTerm(term1);
		int t2 = genTerm(term2);
		
		il.append(_factory.createNew("edu.columbia.mipl.runtime.Term"));
	    il.append(InstructionConstants.DUP);
		switch (type) {
			case ANDTERMS:
				il.append(_factory.createFieldAccess("edu.columbia.mipl.runtime.Term$Type", "ANDTERMS", new ObjectType("edu.columbia.mipl.runtime.Term$Type"), Constants.GETSTATIC));
				break;
			case ORTERMS:
				il.append(_factory.createFieldAccess("edu.columbia.mipl.runtime.Term$Type", "ORTERMS", new ObjectType("edu.columbia.mipl.runtime.Term$Type"), Constants.GETSTATIC));
				break;
		}
		il.append(_factory.createLoad(Type.OBJECT, t1));
		il.append(_factory.createLoad(Type.OBJECT, t2));
		il.append(_factory.createInvoke("edu.columbia.mipl.runtime.Term", "<init>", Type.VOID, new Type[] {new ObjectType("edu.columbia.mipl.runtime.Term$Type"), Type.STRING}, Constants.INVOKESPECIAL));
		il.append(_factory.createStore(Type.OBJECT, target));
		
		return target;
	}
	public int genTerm(Term.Type type, Term term1) { assert (false); return -1; }
	
	public int genTerm(Term.Type type, String name, List<Term> arguments) {
		int listArgVar = nextVar++;
		int target = nextVar++;
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
	    	     
	    il.append(_factory.createStore(Type.OBJECT, target));
		
		return target;
	}
	
	public int genTerm(Term.Type type, String name) {
		int target = nextVar++;
		
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
	    	    
	    il.append(_factory.createStore(Type.OBJECT, target));
	    
	    return target;
	}
	
	public int genTerm(Term.Type type, Expression expr1) { assert (false); return -1; }
	public void genExpression(Expression.Type type, Term term1) { assert (false); }
	public void genExpression(Expression.Type type, Expression expr1, Expression expr2) { assert (false); }

	public int genTerm(Term term) {
		Term.Type type = term.getType(); 
		switch (type) {
			case IS:
				return genTerm(Term.Type.IS, term.getTerm1(), term.getExpr1());
			case EQ:
			case LT:
			case LE:
			case GT:
			case GE:
			case NE:
				return genTerm(term.getType(), term.getExpr1(), term.getExpr2());
			case MATRIX:
				return genTerm(Term.Type.MATRIX, term.getName(), term.getMatrix());
			case ANDTERMS:
			case ORTERMS:
				return genTerm(term.getType(), term.getTerm1(), term.getTerm2());
			case NOTTERM:
				return genTerm(Term.Type.NOTTERM, term.getTerm1());
			case REGEXTERM:
			case TERM:
				return genTerm(type, term.getName(), term.getArguments());
			case NUMBER:
				return genTerm(Term.Type.NUMBER, term.getValue());
			case VARIABLE:
			case QUERYALL:
			case REGEXQUERYALL:
			case STRING:
				return genTerm(type, term.getName());		
			case EXPRESSION:
				return genTerm(Term.Type.EXPRESSION, term.getExpr1());
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

		resetDeclarationList();		
	}

	public void createFact(Fact.Type type, String name, List<String> names, List<Term> terms) {
		// Fact.Type.MATRIXASFACTS
		// TODO : complete other task referring to JavaSourceWriter		
		int curVar = nextVar;
		
		int jobRetVar = nextVar++; 
		
		int namesSize = 0;
		if (names != null)
			namesSize = names.size();
		
		boolean builtin = false;
		if (BuiltinTable.existJob(name))
			builtin = true;
		
		if (builtin) {
			il.append(new PUSH(_cp, name));
			il.append(new PUSH(_cp, terms.size()));
		    il.append(_factory.createNewArray(new ObjectType("edu.columbia.mipl.datastr.PrimitiveType"), (short) 1));
		}
				
		for (int i = 0; i < terms.size(); i++) {
			Term term = terms.get(i);
			
			if (builtin) {
				il.append(InstructionConstants.DUP);
			    il.append(new PUSH(_cp, i));
			}
			
			if (term.getType() == Term.Type.TERM) {
				il.append(_factory.createLoad(Type.OBJECT, varKnowledgeTable));
				il.append(new PUSH(_cp, term.getName()));
			    il.append(_factory.createInvoke("edu.columbia.mipl.runtime.KnowledgeTable", "getFactMatrix", new ObjectType("edu.columbia.mipl.datastr.PrimitiveType"), new Type[] {Type.STRING}, Constants.INVOKEVIRTUAL));
			}
			else if (term.getType() == Term.Type.NUMBER) {
				il.append(_factory.createNew("edu.columbia.mipl.datastr.PrimitiveDouble"));
			    il.append(InstructionConstants.DUP);
			    il.append(new PUSH(_cp, term.getValue()));
			    il.append(_factory.createInvoke("java.lang.Double", "valueOf", new ObjectType("java.lang.Double"), new Type[] {Type.DOUBLE}, Constants.INVOKESTATIC));
			    il.append(_factory.createInvoke("edu.columbia.mipl.datastr.PrimitiveDouble", "<init>", Type.VOID, new Type[] {new ObjectType("java.lang.Double")}, Constants.INVOKESPECIAL));
			}
			else if (term.getType() == Term.Type.STRING) {
				// TODO
			}
			else {
				assert (false);
			}
			
			if (builtin)
				il.append(InstructionConstants.AASTORE);
		}
		
		if (builtin)
			il.append(_factory.createInvoke("edu.columbia.mipl.builtin.BuiltinTable", "job", new ObjectType("java.util.List"), new Type[] {Type.STRING, new ArrayType(new ObjectType("edu.columbia.mipl.datastr.PrimitiveType"), 1)}, Constants.INVOKESTATIC));
		else
			il.append(_factory.createInvoke("MiplProgram", name, new ObjectType("java.util.List"), new Type[] {new ObjectType("edu.columbia.mipl.datastr.PrimitiveType"), new ObjectType("edu.columbia.mipl.datastr.PrimitiveType"), new ObjectType("edu.columbia.mipl.datastr.PrimitiveType")}, Constants.INVOKESTATIC));
	    il.append(_factory.createStore(Type.OBJECT, jobRetVar));
	    
	    BranchInstruction if1 = null;
	    if (namesSize == 0) {
	    	// TODO
	    }
	    else {	    	
		    il.append(_factory.createLoad(Type.OBJECT, jobRetVar));
		    il.append(_factory.createInvoke("java.util.List", "size", Type.INT, Type.NO_ARGS, Constants.INVOKEINTERFACE));
		    il.append(new PUSH(_cp, 1));
		    if1 = _factory.createBranchInstruction(Constants.IF_ICMPEQ, null);
		    il.append(if1);
		    il.append(_factory.createNew("edu.columbia.mipl.runtime.execute.UnmatchedNumberOfReturenException"));
		    il.append(InstructionConstants.DUP);
		    il.append(_factory.createInvoke("edu.columbia.mipl.runtime.execute.UnmatchedNumberOfReturenException", "<init>", Type.VOID, Type.NO_ARGS, Constants.INVOKESPECIAL));
		    il.append(InstructionConstants.ATHROW);
		    
		    InstructionHandle dst1 = null;
		    for (int i = 0; i < namesSize; i++) {
		        InstructionHandle ih = il.append(_factory.createLoad(Type.OBJECT, 3));
		        if (dst1 == null)
		        	dst1 = ih;
		        il.append(_factory.createNew("edu.columbia.mipl.runtime.Fact"));
		        il.append(InstructionConstants.DUP);
		        il.append(_factory.createNew("edu.columbia.mipl.runtime.Term"));
		        il.append(InstructionConstants.DUP);
		        il.append(_factory.createFieldAccess("edu.columbia.mipl.runtime.Term$Type", "MATRIX", new ObjectType("edu.columbia.mipl.runtime.Term$Type"), Constants.GETSTATIC));
		        il.append(new PUSH(_cp, names.get(i)));
		        il.append(_factory.createLoad(Type.OBJECT, jobRetVar));
		        il.append(new PUSH(_cp, i));
		        il.append(_factory.createInvoke("java.util.List", "get", Type.OBJECT, new Type[] {Type.INT}, Constants.INVOKEINTERFACE));
		        il.append(_factory.createCheckCast(new ObjectType("edu.columbia.mipl.datastr.PrimitiveType")));
		        il.append(_factory.createInvoke("edu.columbia.mipl.runtime.Term", "<init>", Type.VOID, new Type[] {new ObjectType("edu.columbia.mipl.runtime.Term$Type"), Type.STRING, new ObjectType("edu.columbia.mipl.datastr.PrimitiveType")}, Constants.INVOKESPECIAL));
		        il.append(_factory.createInvoke("edu.columbia.mipl.runtime.Fact", "<init>", Type.VOID, new Type[] {new ObjectType("edu.columbia.mipl.runtime.Term")}, Constants.INVOKESPECIAL));
		        il.append(_factory.createInvoke("edu.columbia.mipl.runtime.Program", "add", Type.BOOLEAN, new Type[] {new ObjectType("edu.columbia.mipl.runtime.Command")}, Constants.INVOKEVIRTUAL));
		        il.append(InstructionConstants.POP);
		    }
		    
		    if1.setTarget(dst1);
	    }
	    
	    resetDeclarationList();
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
	    
	    resetDeclarationList();
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
	    
	    resetDeclarationList();
	}

	public void createJob(String name, List<Term> args, List<JobStmt> stmts) {
		int nextVarMain = nextVar;
		InstructionList ilMain = il;
		MethodGen methodMain = method;
		
		Set<String> argsDeclSet = new HashSet<String>();
		
		nextVar = 0;

		// TODO : should check argsType can be null
		Type[] typeDesc = null;
		String[] argsDesc = null;
		if (args.size() > 0) {
			typeDesc = new Type[args.size()];
			argsDesc = new String[args.size()];
			for (int i = 0; i < args.size(); i++) {
				typeDesc[i] = new ObjectType("edu.columbia.mipl.datastr.PrimitiveType");
				argsDesc[i] = "arg" + i;
				argsDeclSet.add(args.get(i).getName());
				declarationTab.put(args.get(i).getName(), nextVar++);				
			}
		}					
	    
		il = new InstructionList();
	    method = new MethodGen(Constants.ACC_PUBLIC | Constants.ACC_STATIC, new ObjectType("java.util.List"), typeDesc, argsDesc, name, "MiplProgram", il, _cp);
	    	    
	    varReturn = nextVar++;
	    il.append(_factory.createNew("java.util.ArrayList"));
	    il.append(InstructionConstants.DUP);
	    il.append(_factory.createInvoke("java.util.ArrayList", "<init>", Type.VOID, Type.NO_ARGS, Constants.INVOKESPECIAL));
	    il.append(_factory.createStore(Type.OBJECT, varReturn));
	    
	    // assign local variable locations	    
	    for (String varName : declarationTab.keySet()) {
	    	if (argsDeclSet.contains(varName))
	    		continue;
	    	int localVar = nextVar++;
	    	declarationTab.put(varName, localVar);
	    	il.append(InstructionConstants.ACONST_NULL);
	    	il.append(_factory.createStore(Type.OBJECT, localVar));
	    }
	    
	    for (JobStmt stmt : stmts)
	    	genJobStmt(stmt);
	    
	    il.append(_factory.createLoad(Type.OBJECT, varReturn));
	    il.append(_factory.createReturn(Type.OBJECT));
	    
	    method.setMaxStack();
	    method.setMaxLocals();
	    _cg.addMethod(method.getMethod());
	    il.dispose();
		
		nextVar = nextVarMain;
		il = ilMain;
		method = methodMain;
		
		resetDeclarationList();
	}
	
	public void genJobStmt(JobStmt stmt) {
		switch (stmt.getType()) {
			case IF:
			case WHILE:
			case DOWHILE:
				genJobStmt(stmt.getType(), stmt.getExpr(), stmt.getStmt1(), stmt.getStmt2());
				break;
			case COMPOUND:
				genJobStmt(JobStmt.Type.COMPOUND, stmt.getStmts());
				break;
			case EXPR:
			case RETURN:
				genJobStmt(stmt.getType(), stmt.getExpr());
				break;
			}
	}
	
	public int genJobExpr(JobExpr expr) {
		switch (expr.getType()) {
			case ASSIGN:
			case MULASSIGN:
			case DIVASSIGN:
			case MODASSIGN:
			case ADDASSIGN:
			case SUBASSIGN:
				return genJobExpr(expr.getType(), expr.getName(), expr.getExpr1());
			case OR:
			case AND:
			case EQ:
			case NE:
			case LT:
			case GT:
			case LE:
			case GE:
			case ADD:
			case SUB:
			case MULT:
			case DIV:
			case MOD:
			case MULT_CELL:
			case DIV_CELL:
			case EXP_CELL:
				return genJobExpr(expr.getType(), expr.getExpr1(), expr.getExpr2());
			case NEGATE:
				return genJobExpr(JobExpr.Type.NEGATE, expr.getExpr1());
			case ARRAY:
				return genJobExpr(JobExpr.Type.ARRAY, expr.getTerm(), expr.getIndices1(), expr.getIndices2());
			case JOBCALL:
				return genJobExpr(JobExpr.Type.JOBCALL, expr.getName(), expr.getExprs());
			case TERM:
				return genJobExpr(JobExpr.Type.TERM, expr.getTerm());
		}
		
		assert (false);
		
		return -1;
	}
	
	public void genJobStmt(JobStmt.Type type, JobExpr expr, JobStmt stmt1, JobStmt stmt2) { assert (false); }
	public void genJobStmt(JobStmt.Type type, List<JobStmt> stmts) { assert (false); }
	
	public void genJobStmt(JobStmt.Type type, JobExpr expr) {		
		switch (type) {
			case RETURN:
				il.append(_factory.createLoad(Type.OBJECT, varReturn));
				il.append(_factory.createLoad(Type.OBJECT, genJobExpr(expr)));
			    il.append(_factory.createInvoke("java.util.List", "add", Type.BOOLEAN, new Type[] {Type.OBJECT}, Constants.INVOKEINTERFACE));
			    il.append(InstructionConstants.POP);
				break;
			case EXPR:
				genJobExpr(expr);
				break;
		}
	}
	
	public int genJobExpr(JobExpr.Type type, String name, JobExpr expr) {
		int thisVar = declarationTab.get(name);
		int exprVar = genJobExpr(expr);
		
		if (type != JobExpr.Type.ASSIGN)
			il.append(_factory.createLoad(Type.OBJECT, thisVar));
		il.append(_factory.createLoad(Type.OBJECT, thisVar));
		il.append(_factory.createLoad(Type.OBJECT, exprVar));
		
		switch (type) {
			case ASSIGN:				
				break;
			case MULASSIGN:
				il.append(_factory.createInvoke("edu.columbia.mipl.datastr.PrimitiveOperations", "mult", new ObjectType("edu.columbia.mipl.datastr.PrimitiveType"), new Type[] {new ObjectType("edu.columbia.mipl.datastr.PrimitiveType"), new ObjectType("edu.columbia.mipl.datastr.PrimitiveType")}, Constants.INVOKESTATIC));
				break;
			case DIVASSIGN:
				il.append(_factory.createInvoke("edu.columbia.mipl.datastr.PrimitiveOperations", "div", new ObjectType("edu.columbia.mipl.datastr.PrimitiveType"), new Type[] {new ObjectType("edu.columbia.mipl.datastr.PrimitiveType"), new ObjectType("edu.columbia.mipl.datastr.PrimitiveType")}, Constants.INVOKESTATIC));
				break;
			case MODASSIGN:
				il.append(_factory.createInvoke("edu.columbia.mipl.datastr.PrimitiveOperations", "mod", new ObjectType("edu.columbia.mipl.datastr.PrimitiveType"), new Type[] {new ObjectType("edu.columbia.mipl.datastr.PrimitiveType"), new ObjectType("edu.columbia.mipl.datastr.PrimitiveType")}, Constants.INVOKESTATIC));
				break;
			case ADDASSIGN:				
				il.append(_factory.createInvoke("edu.columbia.mipl.datastr.PrimitiveOperations", "add", new ObjectType("edu.columbia.mipl.datastr.PrimitiveType"), new Type[] {new ObjectType("edu.columbia.mipl.datastr.PrimitiveType"), new ObjectType("edu.columbia.mipl.datastr.PrimitiveType")}, Constants.INVOKESTATIC));			    
				break;
			case SUBASSIGN:
				il.append(_factory.createInvoke("edu.columbia.mipl.datastr.PrimitiveOperations", "sub", new ObjectType("edu.columbia.mipl.datastr.PrimitiveType"), new Type[] {new ObjectType("edu.columbia.mipl.datastr.PrimitiveType"), new ObjectType("edu.columbia.mipl.datastr.PrimitiveType")}, Constants.INVOKESTATIC));
				break;
		}				

		il.append(_factory.createInvoke("edu.columbia.mipl.datastr.PrimitiveOperations", "assign", new ObjectType("edu.columbia.mipl.datastr.PrimitiveType"), new Type[] {new ObjectType("edu.columbia.mipl.datastr.PrimitiveType"), new ObjectType("edu.columbia.mipl.datastr.PrimitiveType")}, Constants.INVOKESTATIC));
		il.append(_factory.createStore(Type.OBJECT, thisVar));
		
		return thisVar;
	}	

	public int genJobExpr(JobExpr.Type type, JobExpr expr1, JobExpr expr2) {
		int target = nextVar++;
		
		int e1 = genJobExpr(expr1);
		int e2 = genJobExpr(expr2);
		il.append(_factory.createLoad(Type.OBJECT, e1));
		il.append(_factory.createLoad(Type.OBJECT, e2));
		
		String opName = null;
		switch (type) {
			case OR:
				opName = "or";
				break;
			case AND:
				opName = "and";
				break;
			case EQ:
				opName = "eq";
				break;
			case NE:
				opName = "ne";
				break;
			case LT:
				opName = "lt";
				break;
			case GT:
				opName = "gt";
				break;
			case LE:
				opName = "le";
				break;
			case GE:
				opName = "ge";
				break;
			case ADD:
				opName = "add";
				break;
			case SUB:
				opName = "sub";
				break;
			case MULT:
				opName = "mult";
				break;
			case DIV:
				opName = "div";
				break;
			case MOD:
				opName = "mod";
				break;
			case MULT_CELL:
				opName = "cellmult";
				break;
			case DIV_CELL:
				opName = "celldiv";
				break;
			case EXP_CELL:
				opName = "cellexp";
				break;
		}

		il.append(_factory.createInvoke("edu.columbia.mipl.datastr.PrimitiveOperations", opName, new ObjectType("edu.columbia.mipl.datastr.PrimitiveType"), new Type[] {new ObjectType("edu.columbia.mipl.datastr.PrimitiveType"), new ObjectType("edu.columbia.mipl.datastr.PrimitiveType")}, Constants.INVOKESTATIC));
		il.append(_factory.createStore(Type.OBJECT, target));
		
		return target;		
	}
	
	public int genJobExpr(JobExpr.Type type, JobExpr expr1) { assert (false); return -1; }
	public int genJobExpr(JobExpr.Type type, Term term, List<ArrayIndex> indices1, List<ArrayIndex> indices2) { assert (false); return -1; }
	public int genJobExpr(JobExpr.Type type, String name, List<JobExpr> exprs) { assert (false); return -1; }
	
	public int genJobExpr(JobExpr.Type type, Term term) {
		// TODO : raise exceptions
		if (term.getType() == Term.Type.VARIABLE) {			
			return declarationTab.get(term.getName());
		}
		else if (term.getType() == Term.Type.NUMBER) {
			int target = nextVar++;
			
			il.append(_factory.createNew("edu.columbia.mipl.datastr.PrimitiveDouble"));
		    il.append(InstructionConstants.DUP);
		    il.append(new PUSH(_cp, term.getValue()));
		    il.append(_factory.createInvoke("java.lang.Double", "valueOf", new ObjectType("java.lang.Double"), new Type[] {Type.DOUBLE}, Constants.INVOKESTATIC));
		    il.append(_factory.createInvoke("edu.columbia.mipl.datastr.PrimitiveDouble", "<init>", Type.VOID, new Type[] {new ObjectType("java.lang.Double")}, Constants.INVOKESPECIAL));
		    il.append(_factory.createStore(Type.OBJECT, target));
		    
		    return target;
		}
		else if (term.getType() == Term.Type.TERM) {
			assert (false);
		}
		else {
			assert (false);
		}		
		
		return -1;
	}

	public void createJobStmt(JobStmt.Type type, JobExpr expr, JobStmt stmt1, JobStmt stmt2) { }
	public void createJobStmt(JobStmt.Type type, List<JobStmt> stmts) { }
	public void createJobStmt(JobStmt.Type type, JobExpr expr) { }
	public void createJobExpr(JobExpr.Type type, String name, JobExpr expr) { declarationTab.put(name, -1); }
	public void createJobExpr(JobExpr.Type type, JobExpr expr1,	JobExpr expr2) { }
	public void createJobExpr(JobExpr.Type type, JobExpr expr1) { }
	public void createJobExpr(JobExpr.Type type, Term term, List<ArrayIndex> indices1, List<ArrayIndex> indices2) { }
	public void createJobExpr(JobExpr.Type type, String name, List<JobExpr> exprs) { }
	public void createJobExpr(JobExpr.Type type, Term term) { }

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
