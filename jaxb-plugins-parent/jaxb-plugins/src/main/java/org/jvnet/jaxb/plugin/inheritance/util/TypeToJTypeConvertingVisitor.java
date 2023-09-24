package org.jvnet.jaxb.plugin.inheritance.util;

import japa.parser.ast.type.ClassOrInterfaceType;
import japa.parser.ast.type.PrimitiveType;
import japa.parser.ast.type.ReferenceType;
import japa.parser.ast.type.Type;
import japa.parser.ast.type.VoidType;
import japa.parser.ast.type.WildcardType;
import japa.parser.ast.visitor.GenericVisitorAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.Validate;

import com.sun.codemodel.JClass;
import com.sun.codemodel.JCodeModel;
import com.sun.codemodel.JType;

public class TypeToJTypeConvertingVisitor extends
		GenericVisitorAdapter<JType, JCodeModel> {

	private final Map<String, JClass> knownClasses;

	public TypeToJTypeConvertingVisitor(Map<String, JClass> knownClasses) {
		Validate.notNull(knownClasses);
		this.knownClasses = knownClasses;
	}

	@Override
	public JType visit(VoidType type, JCodeModel codeModel) {
		return codeModel.VOID;
	}

	@Override
	public JType visit(PrimitiveType type, JCodeModel codeModel) {
		switch (type.getType()) {
		case Boolean:
			return codeModel.BOOLEAN;
		case Char:
			return codeModel.CHAR;
		case Byte:
			return codeModel.BYTE;
		case Short:
			return codeModel.SHORT;
		case Int:
			return codeModel.INT;
		case Long:
			return codeModel.LONG;
		case Float:
			return codeModel.FLOAT;
		case Double:
			return codeModel.DOUBLE;
		default:
			throw new AssertionError("Unknown primitive type ["
					+ type.getType() + "]");
		}
	}

	@Override
	public JType visit(ReferenceType type, JCodeModel codeModel) {
		final JType referencedType = type.getType().accept(this, codeModel);

		JType referencedTypeArray = referencedType;
		for (int index = 0; index < type.getArrayCount(); index++) {
			referencedTypeArray = referencedTypeArray.array();
		}
		return referencedTypeArray;
	}

	@Override
	public JType visit(WildcardType type, JCodeModel codeModel) {

		if (type.getExtends() != null) {
			final ReferenceType _extends = type.getExtends();
			final JType boundType = _extends.accept(this, codeModel);

			if (!(boundType instanceof JClass)) {
				throw new IllegalArgumentException("Bound type [" + _extends
						+ "]in the wildcard type must be class.");
			}

			final JClass boundClass = (JClass) boundType;
			return boundClass.wildcard();
		} else if (type.getSuper() != null) {
			// TODO
			throw new IllegalArgumentException(
					"Wildcard types with super clause are not supported at the moment.");
		} else {
			throw new IllegalArgumentException(
					"Wildcard type must have either extends or super clause.");
		}
	}

	@Override
	public JType visit(ClassOrInterfaceType type, JCodeModel codeModel) {
		final String name = getName(type);
		final JClass knownClass = this.knownClasses.get(name);
		final JClass jclass = knownClass != null ? knownClass : codeModel
				.ref(name);
		final List<Type> typeArgs = type.getTypeArgs();
		if (typeArgs == null || typeArgs.isEmpty()) {
			return jclass;
		} else {
			final List<JClass> jtypeArgs = new ArrayList<JClass>(
					typeArgs.size());
			for (Type typeArg : typeArgs) {
				final JType jtype = typeArg.accept(this, codeModel);
				if (!(jtype instanceof JClass)) {
					throw new IllegalArgumentException("Type argument ["
							+ typeArg.toString() + "] is not a class.");
				} else {
					jtypeArgs.add((JClass) jtype);
				}
			}
			return jclass.narrow(jtypeArgs);
		}
	}

	private String getName(ClassOrInterfaceType type) {
		final String name = type.getName();
		final ClassOrInterfaceType scope = type.getScope();
		if (scope == null) {
			return name;
		} else {
			return getName(scope) + "." + name;
		}
	}
}
