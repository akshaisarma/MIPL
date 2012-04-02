/* MIPL: Mining Integrated Programming Language
 *
 * File: PrimitiveOperations.java
 * Author: YoungHoon Jung <yj2244@columbia.edu>
 * Reviewer: Younghoon Jeon <yj2231@columbia.edu>
 * Description: Primitive Operations
*/
package edu.columbia.mipl.datastr;

import edu.columbia.mipl.matops.*;

public class PrimitiveOperations {
	static MatrixOperations ops;
	static {
		ops = new MapReduceMatrixOperations(); //TODO: read from Configuration
	}

	public static PrimitiveType assign(PrimitiveType target, PrimitiveType source) {
		if (target instanceof PrimitiveMatrix) {
			PrimitiveMatrix t = (PrimitiveMatrix) target;
			if (source instanceof PrimitiveMatrix) {
				PrimitiveMatrix s = (PrimitiveMatrix) source;
				ops.assign(t.getData(), s.getData());
			}
			else if (source instanceof PrimitiveDouble) {
				PrimitiveDouble s = (PrimitiveDouble) source;
				ops.assign(t.getData(), s.getData());
			}
		}
		else if (target instanceof PrimitiveDouble) {
			PrimitiveDouble t = (PrimitiveDouble) target;
			if (source instanceof PrimitiveMatrix) {
				assert (false);
				return null;
			}
			else if (source instanceof PrimitiveDouble) {
				PrimitiveDouble s = (PrimitiveDouble) source;
				target = new PrimitiveDouble(s.getData());
			}
		}
		else
			return null;

		return target;
	}

	public static PrimitiveType multiAssign(PrimitiveType target, PrimitiveType source) {
		if (target instanceof PrimitiveMatrix) {
			PrimitiveMatrix t = (PrimitiveMatrix) target;
			if (source instanceof PrimitiveMatrix) {
				PrimitiveMatrix s = (PrimitiveMatrix) source;
				ops.multassign(t.getData(), s.getData());
			}
			else if (source instanceof PrimitiveDouble) {
				PrimitiveDouble s = (PrimitiveDouble) source;
				ops.multassign(t.getData(), s.getData());
			}
		}
		else if (target instanceof PrimitiveDouble) {
			PrimitiveDouble t = (PrimitiveDouble) target;
			if (source instanceof PrimitiveMatrix) {
				assert (false);
				return null;
			}
			else if (source instanceof PrimitiveDouble) {
				PrimitiveDouble s = (PrimitiveDouble) source;
				target = new PrimitiveDouble(t.getData() * s.getData());
			}
		}
		else
			return null;

		return target;
	}

	public static PrimitiveBool or(PrimitiveBool expr1, PrimitiveBool expr2) {
		return new PrimitiveBool(expr1.getData() || expr2.getData());
	}

	public static PrimitiveBool and(PrimitiveBool expr1, PrimitiveBool expr2) {
		return new PrimitiveBool(expr1.getData() && expr2.getData());
	}

	public static PrimitiveBool eq(PrimitiveType expr1, PrimitiveType expr2) {
		if (expr1 instanceof PrimitiveMatrix && expr2 instanceof PrimitiveMatrix) {
			PrimitiveMatrix e1 = (PrimitiveMatrix) expr1;
			PrimitiveMatrix e2 = (PrimitiveMatrix) expr2;
			return new PrimitiveBool(e1.getData().equalsSemantically(e2.getData()));
		}
		else if (expr1 instanceof PrimitiveDouble && expr2 instanceof PrimitiveDouble) {
			PrimitiveDouble e1 = (PrimitiveDouble) expr1;
			PrimitiveDouble e2 = (PrimitiveDouble) expr2;
			return new PrimitiveBool(e1.getData() == e2.getData());
		}
		else if (expr1 instanceof PrimitiveBool && expr2 instanceof PrimitiveBool) {
			PrimitiveBool e1 = (PrimitiveBool) expr1;
			PrimitiveBool e2 = (PrimitiveBool) expr2;
			return new PrimitiveBool(e1.getData() == e2.getData());
		}
		return new PrimitiveBool(false);
	}

	public static PrimitiveBool ne(PrimitiveType expr1, PrimitiveType expr2) {
		return new PrimitiveBool(!eq(expr1, expr2).getData());
	}

	public static PrimitiveBool lt(PrimitiveType expr1, PrimitiveType expr2) {
		if (expr1 instanceof PrimitiveDouble && expr2 instanceof PrimitiveDouble) {
			PrimitiveDouble e1 = (PrimitiveDouble) expr1;
			PrimitiveDouble e2 = (PrimitiveDouble) expr2;
			return new PrimitiveBool(e1.getData() < e2.getData());
		}
		return null; // throws Excpetion;
	}

	public static PrimitiveBool gt(PrimitiveType expr1, PrimitiveType expr2) {
		if (expr1 instanceof PrimitiveDouble && expr2 instanceof PrimitiveDouble) {
			PrimitiveDouble e1 = (PrimitiveDouble) expr1;
			PrimitiveDouble e2 = (PrimitiveDouble) expr2;
			return new PrimitiveBool(e1.getData() > e2.getData());
		}
		return null; // throws Excpetion;
	}

	public static PrimitiveBool le(PrimitiveType expr1, PrimitiveType expr2) {
		if (expr1 instanceof PrimitiveDouble && expr2 instanceof PrimitiveDouble) {
			PrimitiveDouble e1 = (PrimitiveDouble) expr1;
			PrimitiveDouble e2 = (PrimitiveDouble) expr2;
			return new PrimitiveBool(e1.getData() <= e2.getData());
		}
		return null; // throws Excpetion;
	}

	public static PrimitiveBool ge(PrimitiveType expr1, PrimitiveType expr2) {
		if (expr1 instanceof PrimitiveDouble && expr2 instanceof PrimitiveDouble) {
			PrimitiveDouble e1 = (PrimitiveDouble) expr1;
			PrimitiveDouble e2 = (PrimitiveDouble) expr2;
			return new PrimitiveBool(e1.getData() >= e2.getData());
		}
		return null; // throws Excpetion;
	}

	public static PrimitiveType add(PrimitiveType target, PrimitiveType source) {
		if (target instanceof PrimitiveMatrix) {
			PrimitiveMatrix t = (PrimitiveMatrix) target;
			if (source instanceof PrimitiveMatrix) {
				PrimitiveMatrix s = (PrimitiveMatrix) source;
				ops.add(t.getData(), s.getData());
			}
			else if (source instanceof PrimitiveDouble) {
				PrimitiveDouble s = (PrimitiveDouble) source;
				ops.add(t.getData(), s.getData());
			}
		}
		else if (target instanceof PrimitiveDouble) {
			PrimitiveDouble t = (PrimitiveDouble) target;
			if (source instanceof PrimitiveMatrix) {
				assert (false);
				return null;
			}
			else if (source instanceof PrimitiveDouble) {
				PrimitiveDouble s = (PrimitiveDouble) source;
				target = new PrimitiveDouble(t.getData() + s.getData());
			}
		}
		else
			return null;

		return target;
	}

	public static PrimitiveType sub(PrimitiveType target, PrimitiveType source) {
		if (target instanceof PrimitiveMatrix) {
			PrimitiveMatrix t = (PrimitiveMatrix) target;
			if (source instanceof PrimitiveMatrix) {
				PrimitiveMatrix s = (PrimitiveMatrix) source;
				ops.sub(t.getData(), s.getData());
			}
			else if (source instanceof PrimitiveDouble) {
				PrimitiveDouble s = (PrimitiveDouble) source;
				ops.sub(t.getData(), s.getData());
			}
		}
		else if (target instanceof PrimitiveDouble) {
			PrimitiveDouble t = (PrimitiveDouble) target;
			if (source instanceof PrimitiveMatrix) {
				assert (false);
				return null;
			}
			else if (source instanceof PrimitiveDouble) {
				PrimitiveDouble s = (PrimitiveDouble) source;
				target = new PrimitiveDouble(t.getData() - s.getData());
			}
		}
		else
			return null;

		return target;
	}


	public static PrimitiveType mult(PrimitiveType target, PrimitiveType source) {
		if (target instanceof PrimitiveMatrix) {
			PrimitiveMatrix t = (PrimitiveMatrix) target;
			if (source instanceof PrimitiveMatrix) {
				PrimitiveMatrix s = (PrimitiveMatrix) source;
				ops.mult(t.getData(), s.getData());
			}
			else if (source instanceof PrimitiveDouble) {
				PrimitiveDouble s = (PrimitiveDouble) source;
				ops.mult(t.getData(), s.getData());
			}
		}
		else if (target instanceof PrimitiveDouble) {
			PrimitiveDouble t = (PrimitiveDouble) target;
			if (source instanceof PrimitiveMatrix) {
				assert (false);
				return null;
			}
			else if (source instanceof PrimitiveDouble) {
				PrimitiveDouble s = (PrimitiveDouble) source;
				target = new PrimitiveDouble(t.getData() * s.getData());
			}
		}
		else
			return null;

		return target;
	}


	public static PrimitiveType div(PrimitiveType target, PrimitiveType source) {
		if (target instanceof PrimitiveMatrix) {
			PrimitiveMatrix t = (PrimitiveMatrix) target;
			if (source instanceof PrimitiveMatrix) {
				PrimitiveMatrix s = (PrimitiveMatrix) source;
				ops.div(t.getData(), s.getData());
			}
			else if (source instanceof PrimitiveDouble) {
				PrimitiveDouble s = (PrimitiveDouble) source;
				ops.div(t.getData(), s.getData());
			}
		}
		else if (target instanceof PrimitiveDouble) {
			PrimitiveDouble t = (PrimitiveDouble) target;
			if (source instanceof PrimitiveMatrix) {
				assert (false);
				return null;
			}
			else if (source instanceof PrimitiveDouble) {
				PrimitiveDouble s = (PrimitiveDouble) source;
				target = new PrimitiveDouble(t.getData() / s.getData());
			}
		}
		else
			return null;

		return target;
	}


	public static PrimitiveType mod(PrimitiveType target, PrimitiveType source) {
		if (target instanceof PrimitiveMatrix) {
			PrimitiveMatrix t = (PrimitiveMatrix) target;
			if (source instanceof PrimitiveMatrix) {
				PrimitiveMatrix s = (PrimitiveMatrix) source;
				ops.mod(t.getData(), s.getData());
			}
			else if (source instanceof PrimitiveDouble) {
				PrimitiveDouble s = (PrimitiveDouble) source;
				ops.mod(t.getData(), s.getData());
			}
		}
		else if (target instanceof PrimitiveDouble) {
			PrimitiveDouble t = (PrimitiveDouble) target;
			if (source instanceof PrimitiveMatrix) {
				assert (false);
				return null;
			}
			else if (source instanceof PrimitiveDouble) {
				PrimitiveDouble s = (PrimitiveDouble) source;
				int v = (int) (t.getData() / s.getData());
				target = new PrimitiveDouble(t.getData() - (v * s.getData()));
			}
		}
		else
			return null;

		return target;
	}


	/*
	public static PrimitiveBool add(PrimitiveBool expr1, PrimitiveBool expr2) {
	}

	...
	*/

}
