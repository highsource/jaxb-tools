package org.jvnet.jaxb2_commons.xjc.outline;

import java.util.List;

import org.jvnet.jaxb2_commons.xml.bind.model.MClassInfo;
import org.jvnet.jaxb2_commons.xml.bind.model.MTargeted;

import com.sun.codemodel.JClass;
import com.sun.codemodel.JDefinedClass;
import com.sun.tools.xjc.model.nav.NClass;
import com.sun.tools.xjc.model.nav.NType;

public interface MClassOutline extends MChildOutline, MPackagedOutline,
		MTargeted<MClassInfo<NType, NClass>> {

	public MClassOutline getSuperClassOutline();

	public List<MPropertyOutline> getPropertyOutlines();

	public List<MPropertyOutline> getDeclaredPropertyOutlines();

	public JDefinedClass getReferenceCode();

	public JDefinedClass getImplementationCode();

	public JClass getImplementationReferenceCode();
}
