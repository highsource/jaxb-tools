/*
 * Copyright 2006 The Apache Software Foundation.
 *
 * Licensed under the Apache License, Version 2.0 (the "License")
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.jvnet.jaxb2_commons.plugin.fluent_api;

import java.util.Collection;
import java.util.List;

import com.sun.codemodel.JBlock;
import com.sun.codemodel.JClass;
import com.sun.codemodel.JConditional;
import com.sun.codemodel.JDefinedClass;
import com.sun.codemodel.JExpr;
import com.sun.codemodel.JForEach;
import com.sun.codemodel.JInvocation;
import com.sun.codemodel.JMethod;
import com.sun.codemodel.JMod;
import com.sun.codemodel.JType;
import com.sun.codemodel.JVar;

/**
 * @author Hanson Char
 */
public enum FluentMethodType {
	FLUENT_SETTER 
	{
	    /** 
	     * Adds a fluent api method, which invokes the given setter method, to the given class.
	     * This applies to both simple property setter method and 
	     * indexed property setter method.
	     */
	    @Override
        public void createFluentMethod(JDefinedClass implClass, FluentMethodInfo fluentMethodInfo)
	    {
	    	JMethod setterMethod = fluentMethodInfo.getJmethod();
	        String name = setterMethod.name();
	        // Create a with* method for the respective set* method.
	        int mods = JMod.PUBLIC | setterMethod.mods().getValue() & JMod.FINAL;
	        JMethod fluentMethod = implClass.method(mods, implClass,  
	            FLUENT_SETTER_METHOD_PREFIX + name.substring(SETTER_METHOD_PREFIX_LEN));
	        if (fluentMethodInfo.isOverride())
	        	fluentMethod.annotate(Override.class);
	        JVar[] jvars = setterMethod.listParams();
	        // jvars.length == 1 means simple property setter method.
	        // jvars.length == 2 means indexed property setter method.
	        assert jvars.length == 1 || jvars.length == 2;
	        // with the same parameter(s) as the set* method
	        for (JVar jvar : jvars)
	            fluentMethod.param(jvar.mods().getValue(), jvar.type(), jvar.name());
	        JBlock jblock = fluentMethod.body();
	        // The with* method in turn invoke the setter method
	        JInvocation jinvocation = jblock.invoke(setterMethod);
	        // passing the same list of arguments
	        for (JVar jvar : jvars)
	            jinvocation.arg(jvar);
	        // and return "this"
	        jblock._return(JExpr._this());
	        return;
	    }
	},
	FLUENT_LIST_SETTER 
	{
	    /**
	     * Create a fluent setter method for List, with support of variable arguments.
	     */
	    @Override
        public void createFluentMethod(JDefinedClass implClass, FluentMethodInfo fluentMethodInfo)
	    {
	    	JMethod listGetterMethod = fluentMethodInfo.getJmethod();
	    	String name = listGetterMethod.name();
	        // Create a with* method for the respective List<T> get* method.
	        int mods = JMod.PUBLIC | listGetterMethod.mods().getValue() & JMod.FINAL;
	        JMethod fluentMethod = implClass.method(mods, implClass,  
	                FLUENT_SETTER_METHOD_PREFIX + name.substring(GETTER_METHOD_PREFIX_LEN));
	        if (fluentMethodInfo.isOverride())
	        	fluentMethod.annotate(Override.class);
	        JType returnJType = listGetterMethod.type();
	        // As is already checked in isListGetterMethod(JMethod):
	        // 1) the return type must be a subtype of JClass; and
	        // 2) the number of type parameters must be 1
	        JClass returnJClass = JClass.class.cast(returnJType);
	        List<JClass> typeParams = returnJClass.getTypeParameters();
	        assert typeParams.size() == 1;
	        JClass typeParam = typeParams.get(0);
	        // Support variable arguments  
	        JVar jvarParam = fluentMethod.varParam(typeParam, VALUES);
	        JBlock body = fluentMethod.body();
            JConditional cond = body._if(
                                        jvarParam.ne(
                                            JExpr._null()));
	        JForEach forEach = cond._then()
                                   .forEach(
                                       typeParam, VALUE, JExpr.ref(VALUES));
	        JInvocation addInvocation = forEach.body()
                                               .invoke(
                                                   JExpr.invoke(listGetterMethod), "add");
	        addInvocation.arg(
                            JExpr.ref(VALUE));
	        // and return "this"
	        body._return(
                    JExpr._this());
	        return;
	    }
	},
	FLUENT_COLLECTION_SETTER
	{
        // Originally proposed by Alex Wei ozgwei@dev.java.net:
        // https://jaxb2-commons.dev.java.net/issues/show_bug.cgi?id=12
	    /**
	     * Create a fluent setter method for List, with support of a java.util.Collection argument.
	     */
	    @Override
        public void createFluentMethod(JDefinedClass implClass, FluentMethodInfo fluentMethodInfo)
	    {
	    	JMethod listGetterMethod = fluentMethodInfo.getJmethod();
	    	String name = listGetterMethod.name();
	        // Create a with* method for the respective List<T> get* method.
	        int mods = JMod.PUBLIC | listGetterMethod.mods().getValue() & JMod.FINAL;
	        JMethod fluentMethod = implClass.method(mods, implClass,  
	                FLUENT_SETTER_METHOD_PREFIX + name.substring(GETTER_METHOD_PREFIX_LEN));
	        if (fluentMethodInfo.isOverride())
	        	fluentMethod.annotate(Override.class);
	        JType returnJType = listGetterMethod.type();
	        // As is already checked in isListGetterMethod(JMethod):
	        // 1) the return type must be a subtype of JClass; and
	        // 2) the number of type parameters must be 1
	        JClass returnJClass = JClass.class.cast(returnJType);
	        List<JClass> typeParams = returnJClass.getTypeParameters();
	        assert typeParams.size() == 1;
	        JClass typeParam = typeParams.get(0);
	        // Support Collection with type parameter
	        JClass narrowedCollectionJClass = implClass.owner().ref(Collection.class).narrow(typeParam);
	        JVar jvarParam = fluentMethod.param(narrowedCollectionJClass, VALUES);
	        JBlock body = fluentMethod.body();
            JConditional cond = body._if(
                                        jvarParam.ne(
                                            JExpr._null()));
	        JInvocation addInvocation = cond._then()
                                               .invoke(
                                                   JExpr.invoke(listGetterMethod), "addAll");
	        addInvocation.arg(jvarParam);
	        // and return "this"
	        body._return(
                    JExpr._this());
	        return;
	    }
	}
	;
    private static final String VALUE = "value";
	private static final String VALUES = "values";
	public static final String GETTER_METHOD_PREFIX = "get";
    public static final String SETTER_METHOD_PREFIX = "set";
    public static final String FLUENT_SETTER_METHOD_PREFIX = "with";
    public static final String PARAMETERIZED_LIST_PREFIX = List.class.getName() + "<";
    
    public static final int SETTER_METHOD_PREFIX_LEN = SETTER_METHOD_PREFIX.length();
    public static final int GETTER_METHOD_PREFIX_LEN = GETTER_METHOD_PREFIX.length();
    
    public abstract void createFluentMethod(JDefinedClass implClass, FluentMethodInfo fluentMethodInfo);
}