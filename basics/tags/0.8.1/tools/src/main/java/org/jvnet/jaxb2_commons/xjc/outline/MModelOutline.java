package org.jvnet.jaxb2_commons.xjc.outline;

import java.util.Collection;

import org.jvnet.jaxb2_commons.xml.bind.model.MClassInfo;
import org.jvnet.jaxb2_commons.xml.bind.model.MElementInfo;
import org.jvnet.jaxb2_commons.xml.bind.model.MEnumLeafInfo;
import org.jvnet.jaxb2_commons.xml.bind.model.MModelInfo;
import org.jvnet.jaxb2_commons.xml.bind.model.MPackageInfo;
import org.jvnet.jaxb2_commons.xml.bind.model.MTargeted;

import com.sun.codemodel.JCodeModel;
import com.sun.tools.xjc.model.nav.NClass;
import com.sun.tools.xjc.model.nav.NType;

public interface MModelOutline extends MTargeted<MModelInfo<NType, NClass>> {

	public Collection<MPackageOutline> getPackageOutlines();

	public MPackageOutline getPackageOutline(MPackageInfo target);

	public Collection<MElementOutline> getElementOutlines();

	public MElementOutline getElementOutline(MElementInfo<NType, NClass> target);

	public Collection<MClassOutline> getClassOutlines();

	public MClassOutline getClassOutline(MClassInfo<NType, NClass> target);

	public Collection<MEnumOutline> getEnumOutlines();

	public MEnumOutline getEnumOutline(MEnumLeafInfo<NType, NClass> target);

	public JCodeModel getCode();
}
