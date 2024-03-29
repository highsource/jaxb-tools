package org.jvnet.jaxb.plugin.inheritance.util;

import com.github.javaparser.ast.type.ArrayType;
import com.github.javaparser.ast.type.ClassOrInterfaceType;
import com.github.javaparser.ast.type.PrimitiveType;
import com.github.javaparser.ast.type.ReferenceType;
import com.github.javaparser.ast.type.Type;
import com.github.javaparser.ast.type.VoidType;
import com.github.javaparser.ast.type.WildcardType;
import com.github.javaparser.ast.visitor.GenericVisitorAdapter;

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
		case BOOLEAN:
			return codeModel.BOOLEAN;
		case CHAR:
			return codeModel.CHAR;
		case BYTE:
			return codeModel.BYTE;
		case SHORT:
			return codeModel.SHORT;
		case INT:
			return codeModel.INT;
		case LONG:
			return codeModel.LONG;
		case FLOAT:
			return codeModel.FLOAT;
		case DOUBLE:
			return codeModel.DOUBLE;
		default:
			throw new AssertionError("Unknown primitive type ["
					+ type.getType() + "]");
		}
	}

	@Override
	public JType visit(ArrayType type, JCodeModel codeModel) {
		final JType referencedType = type.getElementType().accept(this, codeModel);

		JType referencedTypeArray = referencedType;
		for (int index = 0; index < type.getArrayLevel(); index++) {
			referencedTypeArray = referencedTypeArray.array();
		}
		return referencedTypeArray;
	}

	@Override
	public JType visit(WildcardType type, JCodeModel codeModel) {

		if (type.getExtendedType().isPresent()) {
			final ReferenceType _extends = type.getExtendedType().get();
			final JType boundType = _extends.accept(this, codeModel);

			if (!(boundType instanceof JClass)) {
				throw new IllegalArgumentException("Bound type [" + _extends
						+ "]in the wildcard type must be class.");
			}

			final JClass boundClass = (JClass) boundType;
			return boundClass.wildcard();
		} else if (type.getSuperType().isPresent()) {
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
		final List<Type> typeArgs = type.getTypeArguments().orElse(null);
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
		final String name = type.getNameAsString();
		final ClassOrInterfaceType scope = type.getScope().orElse(null);
		if (scope == null) {
			return name;
		} else {
			return getName(scope) + "." + name;
		}
	}
}
