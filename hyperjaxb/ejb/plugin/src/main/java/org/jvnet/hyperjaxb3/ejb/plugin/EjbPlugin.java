package org.jvnet.hyperjaxb3.ejb.plugin;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.xml.XMLConstants;
import javax.xml.namespace.QName;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jvnet.hyperjaxb3.ejb.IApplicationContext;
import org.jvnet.hyperjaxb3.ejb.schemas.customizations.Customizations;
import org.jvnet.hyperjaxb3.ejb.strategy.mapping.Mapping;
import org.jvnet.hyperjaxb3.ejb.strategy.naming.Naming;
import org.jvnet.hyperjaxb3.ejb.strategy.processor.ModelAndOutlineProcessor;
import org.jvnet.hyperjaxb3.ejb.test.RoundtripTest;
import org.jvnet.hyperjaxb3.xjc.generator.bean.field.UntypedListFieldRenderer;
import org.jvnet.jaxb.plugin.AbstractParameterizablePlugin;
import org.jvnet.jaxb.util.CustomizationUtils;
import org.jvnet.jaxb.util.GeneratorContextUtils;
import org.w3c.dom.Element;
import org.xml.sax.ErrorHandler;

import com.sun.codemodel.JClass;
import com.sun.codemodel.JCodeModel;
import com.sun.codemodel.JDefinedClass;
import com.sun.codemodel.JExpr;
import com.sun.codemodel.JMethod;
import com.sun.codemodel.JMod;
import com.sun.tools.xjc.BadCommandLineException;
import com.sun.tools.xjc.Options;
import com.sun.tools.xjc.generator.bean.BeanGenerator;
import com.sun.tools.xjc.generator.bean.ClassOutlineImpl;
import com.sun.tools.xjc.generator.bean.field.FieldRenderer;
import com.sun.tools.xjc.generator.bean.field.FieldRendererFactory;
import com.sun.tools.xjc.model.CClassInfo;
import com.sun.tools.xjc.model.CPluginCustomization;
import com.sun.tools.xjc.model.CPropertyInfo;
import com.sun.tools.xjc.model.Model;
import com.sun.tools.xjc.outline.ClassOutline;
import com.sun.tools.xjc.outline.FieldOutline;
import com.sun.tools.xjc.outline.Outline;
import com.sun.tools.xjc.reader.Ring;
import com.sun.tools.xjc.reader.xmlschema.BGMBuilder;
import com.sun.tools.xjc.reader.xmlschema.bindinfo.LocalScoping;

/**
 * Hyperjaxb3 EJB plugin.
 *
 */
public class EjbPlugin extends AbstractParameterizablePlugin {

	protected Log logger = LogFactory.getLog(getClass());

	private final Method generateFieldDecl;
	{
		try {
			generateFieldDecl = BeanGenerator.class.getDeclaredMethod(
					"generateFieldDecl", new Class[] { ClassOutlineImpl.class,
							CPropertyInfo.class });
			generateFieldDecl.setAccessible(true);
		} catch (Exception ex) {
			throw new ExceptionInInitializerError(ex);

		}
	}

	private List<URL> episodeURLs = new LinkedList<URL>();

	public String getOptionName() {
		return "Xhyperjaxb3-ejb";
	}

	public String getUsage() {
		return "  -Xhyperjaxb3-ejb: Hyperjaxb3 EJB plugin";
	}

	private String roundtripTestClassName;

	public String getRoundtripTestClassName() {
		return roundtripTestClassName;
	}

	public void setRoundtripTestClassName(String rt) {
		this.roundtripTestClassName = rt;
	}

	private String persistenceUnitName;

	public void setPersistenceUnitName(String persistenceUnitName) {
		this.persistenceUnitName = persistenceUnitName;
	}

	public String getPersistenceUnitName() {
		return persistenceUnitName;
	}

	private File targetDir;

	public File getTargetDir() {
		return targetDir;
	}

	public void setTargetDir(File targetDir) {
		this.targetDir = targetDir;
	}

	private File persistenceXml;

	public File getPersistenceXml() {
		return persistenceXml;
	}

	public void setPersistenceXml(File persistenceXml) {
		this.persistenceXml = persistenceXml;
	}

    public String applicationContextClassName = null;

    public String getApplicationContextClassName() {
        return applicationContextClassName;
    }

    public void setApplicationContextClassName(String applicationContextClassName) {
        this.applicationContextClassName = applicationContextClassName;
    }

    public IApplicationContext applicationContext;

    public IApplicationContext getApplicationContext() {
        if (applicationContext == null) {
            applicationContext = createApplicationContext();
            if (getApplicationContextClassName() != null) {
                try {
                    applicationContext.setDelegate((IApplicationContext) Class.forName(getApplicationContextClassName()).getDeclaredConstructor().newInstance());
                } catch (ClassNotFoundException | ClassCastException | NoSuchMethodException | InvocationTargetException | InstantiationException | IllegalAccessException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        return applicationContext;
    }

    private IApplicationContext createApplicationContext() {
        return new ApplicationContext();
    }

    public void setApplicationContext(IApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    private Integer maxIdentifierLength = null;

    public Integer getMaxIdentifierLength() { return maxIdentifierLength; }

    public void setMaxIdentifierLength(Integer maxIdentifierLength) { this.maxIdentifierLength = maxIdentifierLength; }

    private String result = "annotations";

	public String getResult() {
		return result;
	}

	public void setResult(String variant) {
		this.result = variant;
	}

	public String getModelAndOutlineProcessorBeanName() {
		return getResult();
	}

	private String[] mergePersistenceUnits = new String[0];

	public String[] getMergePersistenceUnits() {
		return mergePersistenceUnits;
	}

	public void setMergePersistenceUnits(String[] mergePersistenceUnits) {
		this.mergePersistenceUnits = mergePersistenceUnits;
	}

	@Override
	public int parseArgument(Options opt, String[] args, int start)
			throws BadCommandLineException, IOException {
		final int result = super.parseArgument(opt, args, start);

		for (int i = 0; i < args.length; i++) {
			if (args[i].length() != 0) {
				if (args[i].charAt(0) != '-') {
					if (args[i].endsWith(".jar")) {
						episodeURLs.add(new File(args[i]).toURI().toURL());
					}
				}
			}
		}
		return result;
	}

	@Override
	public boolean run(Outline outline, Options options) throws Exception {

		final Ring ring = Ring.begin();

		try {
			Ring.add(this.bgmBuilder);
			Ring.add(outline.getModel());

			final ModelAndOutlineProcessor<EjbPlugin> modelAndOutlineProcessor = getModelAndOutlineProcessor();

			modelAndOutlineProcessor.process(this, outline.getModel(), options);
		} catch (Exception ex) {
			ex.printStackTrace();
			throw ex;
		} finally {
			Ring.end(ring);

		}

		for (final CClassInfo classInfo : getCreatedClasses()) {
			final ClassOutline classOutline = outline.getClazz(classInfo);
			if (Customizations.isGenerated(classInfo)) {
				generateClassBody(outline, (ClassOutlineImpl) classOutline);
			}

			for (final CPropertyInfo propertyInfo : classInfo.getProperties()) {
				if (outline.getField(propertyInfo) == null) {
					generateFieldDecl(outline, (ClassOutlineImpl) classOutline,
							propertyInfo);
				}
			}
		}

		modelAndOutlineProcessor.process(this, outline, options);

		generateRoundtripTestClass(outline);

		checkCustomizations(outline);
		return true;

	}

	private void generateRoundtripTestClass(Outline outline) {
		if (getRoundtripTestClassName() != null) {
			final JDefinedClass roundtripTestClass = GeneratorContextUtils
					.generateContextPathAwareClass(outline,
							getRoundtripTestClassName(), RoundtripTest.class);

			final String persistenceUnitName = getPersistenceUnitName() != null ? getPersistenceUnitName()
					: getNaming().getPersistenceUnitName(getMapping(), outline);
			JMethod getPersistenceUnitName = roundtripTestClass.method(
					JMod.PUBLIC, outline.getCodeModel().ref(String.class),
					"getPersistenceUnitName");
			getPersistenceUnitName.body()._return(
					JExpr.lit(persistenceUnitName));
		}
	}

	private void checkCustomizations(Outline outline) {
		for (final CClassInfo classInfo : outline.getModel().beans().values()) {
			checkCustomizations(classInfo);
			for (final CPropertyInfo propertyInfo : classInfo.getProperties()) {
				checkCustomizations(classInfo, propertyInfo);
			}
		}
	}

	private void checkCustomizations(CClassInfo classInfo,
			CPropertyInfo customizable) {

		for (CPluginCustomization pluginCustomization : CustomizationUtils
				.getCustomizations(customizable)) {
			if (!pluginCustomization.isAcknowledged()
					&& Customizations.NAMESPACE_URI
							.equals(pluginCustomization.element
									.getNamespaceURI())) {
				logger.warn("Unacknowledged customization [" +

				getName(pluginCustomization.element) + "] in the property ["
						+ classInfo.getName() + "."
						+ customizable.getName(true) + "].");

				pluginCustomization.markAsAcknowledged();
			}
		}

	}

	private void checkCustomizations(CClassInfo customizable) {

		for (final CPluginCustomization pluginCustomization : CustomizationUtils
				.getCustomizations(customizable)) {
			final Element element = pluginCustomization.element;

			if (!pluginCustomization.isAcknowledged()
			// && Customizations.NAMESPACE_URI.equals(element
			// .getNamespaceURI())
			) {
				logger.warn("Unacknowledged customization [" +

				getName(element) + "] in the class [" + customizable.getName()
						+ "].");

				pluginCustomization.markAsAcknowledged();

			}
		}

	}

	private QName getName(Element element) {
		return new QName(element.getNamespaceURI(), element.getLocalName(),
				element.getPrefix() == null ? XMLConstants.DEFAULT_NS_PREFIX
						: element.getPrefix());
	}

	@Override
	public void postProcessModel(Model model, ErrorHandler errorHandler) {

		this.bgmBuilder = Ring.get(BGMBuilder.class);

		if (LocalScoping.NESTED.equals(bgmBuilder.getGlobalBinding()
				.getFlattenClasses())) {
			logger.warn("According to the Java Persistence API specification, section 2.1, "
					+ "entities must be top-level classes:\n"
					+ "\"The entity class must be a top-level class.\"\n"
					+ "Your JAXB model is not customized as with top-level local scoping, "
					+ "please use the <jaxb:globalBinding localScoping=\"toplevel\"/> "
					+ "global bindings customization.");
		}

		final boolean serializable = model.serializable;

		if (!serializable) {
			logger.warn("According to the Java Persistence API specification, section 2.1, "
					+ "entities must implement the serializable interface:\n"
					+ "\"If an entity instance is to be passed by value as a detached object\n"
					+ "(e.g., through a remote interface), the entity class must implement\n "
					+ "the Serializable interface.\"\n"
					+ "Your JAXB model is not customized as serializable, please use the "
					+ "<jaxb:serializable/> global bindings customization element to make your model serializable.");
		}
	}

	@Override
	public void onActivated(Options options) throws BadCommandLineException {

		Thread.currentThread().setContextClassLoader(
				getClass().getClassLoader());

		super.onActivated(options);

		final FieldRendererFactory fieldRendererFactory = new FieldRendererFactory() {

			public FieldRenderer getList(JClass coreList) {
				return new UntypedListFieldRenderer(coreList);
			}
		};
		options.setFieldRendererFactory(fieldRendererFactory, this);
	}

	@Override
	protected void beforeRun(Outline outline, Options options) throws Exception {
		super.beforeRun(outline, options);

		if (getModelAndOutlineProcessor() == null) {
            final ModelAndOutlineProcessor<EjbPlugin> modelAndOutlineProcessor =
                getApplicationContext().getModelAndOutlineProcessor(getModelAndOutlineProcessorBeanName());
            setModelAndOutlineProcessor(modelAndOutlineProcessor);
		}

		if (getNaming() == null) {
			setNaming(getApplicationContext().getNaming());
		}

        if (getMaxIdentifierLength() != null) {
            getNaming().setMaxIdentifierLength(getMaxIdentifierLength());
        }

		if (getMapping() == null) {
			setMapping(getApplicationContext().getMapping());
		}

		if (getTargetDir() == null) {
			setTargetDir(options.targetDir);
		}
	}

	private ModelAndOutlineProcessor<EjbPlugin> modelAndOutlineProcessor;

	public ModelAndOutlineProcessor<EjbPlugin> getModelAndOutlineProcessor() {
		return modelAndOutlineProcessor;
	}

	public void setModelAndOutlineProcessor(
			ModelAndOutlineProcessor<EjbPlugin> modelAndOutlineProcessor) {
		this.modelAndOutlineProcessor = modelAndOutlineProcessor;
	}

	private Mapping mapping;

	public Mapping getMapping() {
		return mapping;
	}

	public void setMapping(Mapping mapping) {
		this.mapping = mapping;
	}

	private Naming naming;

	public Naming getNaming() {
		return naming;
	}

	public void setNaming(Naming naming) {
		this.naming = naming;
	}

	@Override
	public List<String> getCustomizationURIs() {
		final List<String> customizationURIs = new LinkedList<String>();
		customizationURIs.addAll(super.getCustomizationURIs());
		customizationURIs.addAll(Customizations.NAMESPACES);
		return customizationURIs;
	}

	@Override
	public boolean isCustomizationTagName(String namespace, String localPart) {
		return super.isCustomizationTagName(namespace, localPart)
				|| Customizations.NAMESPACES.contains(namespace);
	}

	private Collection<ClassOutline> includedClasses;

	public Collection<ClassOutline> getIncludedClasses() {
		return includedClasses;
	}

	public void setIncludedClasses(Collection<ClassOutline> includedClasses) {
		this.includedClasses = includedClasses;
	}

	private Collection<CClassInfo> createdClasses = new LinkedList<CClassInfo>();

	public Collection<CClassInfo> getCreatedClasses() {
		return createdClasses;
	}

	private Map<CPropertyInfo, CClassInfo> createdProperties = new HashMap<CPropertyInfo, CClassInfo>();

	public Map<CPropertyInfo, CClassInfo> getCreatedProperties() {
		return createdProperties;
	}

	private void generateClassBody(Outline outline, ClassOutlineImpl cc) {

		final JCodeModel codeModel = outline.getCodeModel();
		final Model model = outline.getModel();
		CClassInfo target = cc.target;

		if (model.serializable) {
			cc.implClass._implements(Serializable.class);
			if (model.serialVersionUID != null) {
				cc.implClass.field(JMod.PRIVATE | JMod.STATIC | JMod.FINAL,
						codeModel.LONG, "serialVersionUID",
						JExpr.lit(model.serialVersionUID));
			}
		}

		for (CPropertyInfo prop : target.getProperties()) {
			generateFieldDecl(outline, cc, prop);
		}

		assert !target.declaresAttributeWildcard();
	}

	private FieldOutline generateFieldDecl(Outline outline,
			ClassOutlineImpl cc, CPropertyInfo prop) {

		try {
			return (FieldOutline) generateFieldDecl.invoke(outline,
					new Object[] { cc, prop });
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new RuntimeException(ex);
		}
	}

	private BGMBuilder bgmBuilder;

}
