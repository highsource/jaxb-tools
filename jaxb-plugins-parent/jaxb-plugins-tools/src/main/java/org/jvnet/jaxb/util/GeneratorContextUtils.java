package org.jvnet.jaxb.util;


import org.jvnet.jaxb.xml.bind.ContextPathAware;

import com.sun.codemodel.JClass;
import com.sun.codemodel.JCodeModel;
import com.sun.codemodel.JDefinedClass;
import com.sun.codemodel.JExpr;
import com.sun.codemodel.JMethod;
import com.sun.codemodel.JMod;
import com.sun.tools.xjc.outline.Outline;

public class GeneratorContextUtils {

  private GeneratorContextUtils() {
  }

  public static JDefinedClass generateContextPathAwareClass(
      Outline outline,
      String name,
      Class<?> theClass) {

    return generateContextPathAwareClass(outline, name, theClass == null ? null : outline
        .getCodeModel()
        .ref(theClass));
  }

  public static JDefinedClass generateContextPathAwareClass(
      Outline outline,
      String name,
      JClass baseClass) {

    final String contextPath = OutlineUtils.getContextPath(outline);

    final JCodeModel codeModel = outline.getCodeModel();
    final JDefinedClass contextPathAwareClass = CodeModelUtils.getOrCreateClass(
        codeModel,
        JMod.PUBLIC,
        name);

    ClassUtils._implements(contextPathAwareClass, codeModel.ref(ContextPathAware.class));

    if (baseClass != null) {
      contextPathAwareClass._extends(baseClass);
    }

    final JMethod getContextPath = contextPathAwareClass.method(JMod.PUBLIC, codeModel
        .ref(String.class), "getContextPath");
    getContextPath.body()._return(JExpr.lit(contextPath));
    return contextPathAwareClass;
  }

}
