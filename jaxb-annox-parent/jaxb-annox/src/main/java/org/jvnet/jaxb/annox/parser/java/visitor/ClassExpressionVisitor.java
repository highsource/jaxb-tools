package org.jvnet.jaxb.annox.parser.java.visitor;

import com.github.javaparser.ast.expr.ClassExpr;
import com.github.javaparser.ast.type.ArrayType;
import com.github.javaparser.ast.type.ClassOrInterfaceType;
import com.github.javaparser.ast.type.PrimitiveType;
import com.github.javaparser.ast.type.ReferenceType;
import com.github.javaparser.ast.type.Type;
import com.github.javaparser.ast.type.VoidType;
import com.github.javaparser.ast.type.WildcardType;

import java.lang.reflect.Array;

import org.jvnet.jaxb.annox.model.annotation.value.AbstractBasicXAnnotationValueVisitor;
import org.jvnet.jaxb.annox.model.annotation.value.XAnnotationValue;
import org.jvnet.jaxb.annox.model.annotation.value.XArrayClassAnnotationValue;
import org.jvnet.jaxb.annox.model.annotation.value.XClassAnnotationValue;
import org.jvnet.jaxb.annox.model.annotation.value.XClassByNameAnnotationValue;

public final class ClassExpressionVisitor extends
		ExpressionVisitor<XAnnotationValue<Class<?>>> {
	public ClassExpressionVisitor(Class<?> targetClass) {
		super(targetClass);
	}

	@Override
	public XAnnotationValue<Class<?>> visit(ClassExpr n, Void arg) {
		return n.getType().accept(
				new ExpressionVisitor<XAnnotationValue<Class<?>>>(
						this.targetClass) {

					@Override
					public XAnnotationValue<Class<?>> visit(
							ClassOrInterfaceType n, Void arg) {
						// TODO Scopes
						// TODO unknown clases
						// TODO We'll need a more complex construct for classes
						// here
						final String className = n.toString();
						try {
							@SuppressWarnings({ "unchecked", "rawtypes" })
							final XAnnotationValue<Class<?>> classAnnotationValue = new XClassAnnotationValue(
									Class.forName(className));
							return classAnnotationValue;
						} catch (ClassNotFoundException cnfex) {
							@SuppressWarnings({ "unchecked", "rawtypes" })
							final XAnnotationValue<Class<?>> classByNameAnnotationValue = new XClassByNameAnnotationValue(
									className);
							return classByNameAnnotationValue;
						}
					}

					@Override
					public XAnnotationValue<Class<?>> visit(ArrayType n,
                                                            Void arg) {

						// BUG arraycount is not yet considered
						// TODO consider arrayCount
						final Type type = n.getElementType();
						final XAnnotationValue<Class<?>> t = type.accept(this,
								arg);
						final int arrayCount = n.getArrayLevel();
						if (arrayCount == 0) {
							return t;
						} else {

							return t.accept(new AbstractBasicXAnnotationValueVisitor<XAnnotationValue<Class<?>>>() {

								@Override
								public XAnnotationValue<Class<?>> visitDefault(
										XAnnotationValue<?> value) {
									throw new IllegalArgumentException();
								}

								@Override
								public XAnnotationValue<Class<?>> visit(
										XArrayClassAnnotationValue<?, ?> value) {
									@SuppressWarnings({ "unchecked", "rawtypes" })
									final XAnnotationValue<Class<?>> arrayClassAnnotationValue = new XArrayClassAnnotationValue(
											value.getItemClassByNameAnnotationValue(),
											value.getDimension() + arrayCount);
									return arrayClassAnnotationValue;
								}

								@Override
								public XAnnotationValue<Class<?>> visit(
										XClassAnnotationValue<?> value) {
									Class<?> _class = value.getValue();
									for (int index = 0; index < arrayCount; index++) {
										_class = Array.newInstance(_class, 0)
												.getClass();
									}
									@SuppressWarnings({ "unchecked", "rawtypes" })
									final XAnnotationValue<Class<?>> classAnnotationValue = new XClassAnnotationValue(
											_class);
									return classAnnotationValue;
								}

								@Override
								public XAnnotationValue<Class<?>> visit(
										XClassByNameAnnotationValue<?> value) {
									@SuppressWarnings({ "unchecked", "rawtypes" })
									final XAnnotationValue<Class<?>> arrayClassAnnotationValue = new XArrayClassAnnotationValue(
											value, arrayCount);
									return arrayClassAnnotationValue;
								}
							});
						}
					}

					@SuppressWarnings({ "rawtypes", "unchecked" })
					@Override
					public XAnnotationValue<Class<?>> visit(VoidType n, Void arg) {
						return new XClassAnnotationValue(Void.class);
					}

					@SuppressWarnings({ "unchecked", "rawtypes" })
					@Override
					public XAnnotationValue<Class<?>> visit(PrimitiveType n,
							Void arg) {
						switch (n.getType()) {
						case BOOLEAN:
							return new XClassAnnotationValue(boolean.class);
						case CHAR:
							return new XClassAnnotationValue(char.class);
						case BYTE:
							return new XClassAnnotationValue(byte.class);
						case SHORT:
							return new XClassAnnotationValue(short.class);
						case INT:
							return new XClassAnnotationValue(int.class);
						case LONG:
							return new XClassAnnotationValue(long.class);
						case DOUBLE:
							return new XClassAnnotationValue(double.class);
						case FLOAT:
							return new XClassAnnotationValue(float.class);
						default:
							throw new IllegalArgumentException();
						}
					}

					@Override
					public XAnnotationValue<Class<?>> visit(WildcardType n,
							Void arg) {
						throw new UnsupportedOperationException(
								"Wildcard types are not supported.");
					}

				}, null);
	}
}
