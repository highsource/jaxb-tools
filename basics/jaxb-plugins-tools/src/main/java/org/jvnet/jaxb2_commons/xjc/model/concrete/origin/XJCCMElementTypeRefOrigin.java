package org.jvnet.jaxb2_commons.xjc.model.concrete.origin;

import org.hisrc.xml.xsom.FindXSElementDeclVisitor;
import org.hisrc.xml.xsom.SchemaComponentAware;
import org.jvnet.jaxb2_commons.xml.bind.model.concrete.origin.CMElementTypeRefOrigin;

import com.sun.tools.xjc.model.CElementPropertyInfo;
import com.sun.tools.xjc.model.CTypeRef;
import com.sun.tools.xjc.model.nav.NClass;
import com.sun.tools.xjc.model.nav.NType;
import com.sun.xml.xsom.XSComponent;

public class XJCCMElementTypeRefOrigin extends
		CMElementTypeRefOrigin<NType, NClass, CElementPropertyInfo, CTypeRef>
		implements SchemaComponentAware {

	private final XSComponent component;

	public XJCCMElementTypeRefOrigin(CElementPropertyInfo source,
			CTypeRef typeRef) {
		super(source, typeRef);
		final XSComponent schemaComponent = source.getSchemaComponent();
		if (schemaComponent != null) {
			final FindXSElementDeclVisitor visitor = new FindXSElementDeclVisitor(
					typeRef.getTagName());
			schemaComponent.visit(visitor);
			this.component = visitor.getElementDecl();
		} else {
			this.component = null;
		}
	}

	@Override
	public XSComponent getSchemaComponent() {
		return component;
	}

}
