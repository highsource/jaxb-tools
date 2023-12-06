package org.jvnet.jaxb.plugin.elementwrapper;

import com.sun.codemodel.JAnnotationUse;
import com.sun.codemodel.JClass;
import com.sun.codemodel.JDefinedClass;
import com.sun.codemodel.JExpr;
import com.sun.codemodel.JFieldVar;
import com.sun.codemodel.JMethod;
import com.sun.codemodel.JMod;
import com.sun.codemodel.JPackage;
import com.sun.tools.xjc.BadCommandLineException;
import com.sun.tools.xjc.Options;
import com.sun.tools.xjc.Plugin;
import com.sun.tools.xjc.model.CClassInfo;
import com.sun.tools.xjc.model.CElementPropertyInfo;
import com.sun.tools.xjc.model.CPropertyInfo;
import com.sun.tools.xjc.model.CTypeInfo;
import com.sun.tools.xjc.model.Model;
import com.sun.tools.xjc.outline.ClassOutline;
import com.sun.tools.xjc.outline.FieldOutline;
import com.sun.tools.xjc.outline.Outline;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlElementWrapper;
import org.xml.sax.ErrorHandler;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.LineNumberReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * When generating XML schema from Java source code using the schemagen tool a common approach is to use
 * the @XmlElementWrapper and @XmlElement annotations to handle schema generation for collections. However, when
 * generating Java source from a schema using the xjc tool the resulting code is not created with these
 * annotations. Instead, the generated Java source contains "injected" inner classes to accommodate the collection
 * of elements contained within an element.
 *
 * This plugin came from here : org.andromda.thirdparty.jaxb2_commons:xml-element-wrapper:1.0
 */
public class XmlElementWrapperPlugin extends Plugin {

    /** Constant for the option string. */
    protected final String OPTION_NAME = "XelementWrapper";
    private final String OPTION_NAME_DELETE = "-" + OPTION_NAME + ":delete";
    private final String OPTION_NAME_INCLUDE = "-" + OPTION_NAME + ":include";
    private final String OPTION_NAME_EXCLUDE = "-" + OPTION_NAME + ":exclude";
    private final String OPTION_NAME_SUMMARY = "-" + OPTION_NAME + ":summary";
    private final String OPTION_NAME_COLLECTION = "-" + OPTION_NAME + ":collection";
    private final String OPTION_NAME_INSTANTIATE = "-" + OPTION_NAME + ":instantiate";


    protected Map<String, Candidate> candidates = null;
    protected File includeFile = null;
    protected Set<String> include = null;
    protected File excludeFile = null;
    protected Set<String> exclude = null;
    protected File summaryFile = null;
    protected PrintWriter summary = null;
    protected boolean debugMode = false;
    protected boolean verbose = false;
    @SuppressWarnings("unchecked")
    protected Class interfaceClass = List.class;
    @SuppressWarnings("unchecked")
    protected Class collectionClass = ArrayList.class;
    protected Instantiation instantiation = Instantiation.EARLY;
    protected boolean deleteCandidates = false;
    protected String factoryClassName = "ObjectFactory";
    protected String debugClassName = "JAXBDebug";

    public XmlElementWrapperPlugin() {
    }

    @Override
    public String getOptionName() {
        return OPTION_NAME;
    }

    @Override
    public String getUsage() {
        return "  -" + OPTION_NAME + " : Replace collection types with fields having the @XmlElementWrapper and @XmlElement annotations.";
    }

    @Override
    public void onActivated(Options opts) throws BadCommandLineException {
        debugMode = opts.debugMode;
        verbose = opts.verbose;

        // If we are in debug mode, report...
        writeDebug("JAXB Compilation started (onActivated):");
        writeDebug("\tbuildId        :\t" + Options.getBuildID());
        writeDebug("\tdefaultPackage :\t" + opts.defaultPackage);
        writeDebug("\tdefaultPackage2:\t" + opts.defaultPackage2);
        writeDebug("\tquiet          :\t" + opts.quiet);
        writeDebug("\tdebug          :\t" + opts.debugMode);
        writeDebug("\ttargetDir      :\t" + opts.targetDir);
        writeDebug("\tverbose        :\t" + opts.verbose);
        writeDebug("\tGrammars       :\t" + opts.getGrammars().length);
        for (int i = 0; i < opts.getGrammars().length; i++)
            writeDebug("\t  [" + i + "]: " + opts.getGrammars()[i].getSystemId());
    }

    @Override
    public int parseArgument(Options opt, String[] args, int i) throws BadCommandLineException,
        IOException {

        int recognized = 0;
        String filename;

        String arg = args[i];
        writeDebug("Argument[" + i + "] = " + arg);

        if (arg.startsWith(OPTION_NAME_DELETE)) {
            recognized++;
            deleteCandidates = true;
        } else if (arg.startsWith(OPTION_NAME_INCLUDE)) {
            recognized++;
            include = new HashSet<>();

            if (arg.length() > 8) {
                filename = arg.substring(OPTION_NAME_INCLUDE.length()).trim();
            } else {
                filename = args[i + 1];
                recognized++;
            }
            includeFile = new File(filename);
            readCandidates(includeFile, include);
        } else if (arg.startsWith(OPTION_NAME_EXCLUDE)) {
            recognized++;
            exclude = new HashSet<String>();

            if (arg.length() > 8) {
                filename = arg.substring(OPTION_NAME_EXCLUDE.length()).trim();
            } else {
                filename = args[i + 1];
                recognized++;
            }
            excludeFile = new File(filename);
            readCandidates(excludeFile, exclude);
        } else if (arg.startsWith(OPTION_NAME_SUMMARY)) {
            recognized++;
            if (arg.length() > 8) {
                filename = arg.substring(OPTION_NAME_SUMMARY.length()).trim();
            } else {
                filename = args[i + 1];
                recognized++;
            }

            summaryFile = new File(filename);
            summary = new PrintWriter(new FileOutputStream(summaryFile));
        } else if (arg.startsWith(OPTION_NAME_COLLECTION)) {
            String ccn;

            recognized++;
            if (arg.length() > 11) {
                ccn = arg.substring(OPTION_NAME_COLLECTION.length()).trim();
            } else {
                ccn = args[i + 1];
                recognized++;
            }
            try {
                collectionClass = Class.forName(ccn);
            } catch (ClassNotFoundException e) {
                throw new BadCommandLineException(OPTION_NAME_COLLECTION + " " + ccn + ": Class not found.");
            }
        } else if (arg.startsWith(OPTION_NAME_INSTANTIATE)) {
            String instantiate;
            recognized++;

            if (arg.length() > 12) {
                instantiate = arg.substring(OPTION_NAME_INSTANTIATE.length()).trim().toUpperCase();
            } else {
                instantiate = args[i + 1].trim().toUpperCase();
                recognized++;
            }
            instantiation = Instantiation.valueOf(instantiate);
        } else {
            //throw new BadCommandLineException("Invalid argument " + arg);
        }

        return recognized;
    }


    @Override
    public void postProcessModel(Model model, ErrorHandler errorHandler) {
        super.postProcessModel(model, errorHandler);
    }

    @Override
    public boolean run(Outline model, Options opt, ErrorHandler errorHandler) {
        int candidateCount = 0;
        int modificationCount = 0;
        int deletionCount = 0;

        JDefinedClass implementationClass;
        Candidate candidate;
        String fieldName;
        String typeName;
        Collection<JMethod> methodsToRemove;

        writeDebug("JAXB Process Model (run)...");

        // Write summary information on the option for this compilation.
        writeSummary(" ");
        writeSummary("Compilation:");
        writeSummary("\tDate         :\t-");
        writeSummary("\tVersion      :\t-");
        writeSummary("\tJAXB version :\t" + Options.getBuildID());
        writeSummary("\tInclude file :\t" + (includeFile == null ? "<none>" : includeFile));
        writeSummary("\tExclude file :\t" + (excludeFile == null ? "<none>" : excludeFile));
        writeSummary("\tSummary file :\t" + (summaryFile == null ? "<none>" : summaryFile));
        writeSummary("\tInstantiate  :\t" + instantiation);
        writeSummary("\tCollection   :\t" + collectionClass);
        writeSummary("\tInterface    :\t" + interfaceClass);
        writeSummary("\tDelete       :\t" + deleteCandidates);
        writeSummary(" ");

        // Find candidate classes for transformation.
        // Candidates are classes having exactly one field which is a collection.
        candidates = findCandidateClasses(model.getModel(), errorHandler);

        // Write information on candidate classes to summary file.
        writeSummary("Candidates:");
        for (Candidate c : candidates.values()) {
            if (isIncluded(c)) {
                writeSummary("\t[+] " + getIncludeOrExcludeReason(c) + ":\t" + c.getClassName());
                candidateCount++;
            } else {
                writeSummary("\t[-]: " + getIncludeOrExcludeReason(c) + ":\t" + c.getClassName());
            }
        }
        writeSummary("\t" + candidateCount + " candidate(s) being considered.");
        writeSummary(" ");

        // Visit all classes generated by JAXB.
        writeSummary("Modifications:");
        for (ClassOutline classOutline : model.getClasses()) {
            // Get the implementation class for the current class.
            implementationClass = classOutline.implClass;

            // Visit all fields in this class.
            for (FieldOutline field : classOutline.getDeclaredFields()) {

                // Extract the field name and type of the current field.
                fieldName = field.getPropertyInfo().getName(false);
                typeName = field.getRawType().fullName();

                // Check to see if the current field references one of the candidate classes.
                candidate = candidates.get(typeName);

                if (candidate != null && isIncluded(candidate)) {
                    // We have a candidate field to be replaced with a wrapped version. Report finding to
                    // summary file.
                    writeSummary("\t" + classOutline.target.getName() + "#" + fieldName + "\t" + typeName);
                    modificationCount++;

                    // Create the new interface and collection classes using the specified interface and
                    // collection classes (configuration) with an element type corresponding to
                    // the element type from the collection present in the candidate class (narrowing).
                    JDefinedClass candidateClass = model.getClazz(candidate.getClassInfo()).implClass;
                    List<JClass> itemNarrowing = ((JClass) candidateClass.fields().get(
                        candidate.getFieldName()).type()).getTypeParameters();
                    JClass newInterfaceClass = implementationClass.owner().ref(interfaceClass).narrow(
                        itemNarrowing);
                    JClass newCollectionClass = implementationClass.owner().ref(collectionClass).narrow(
                        itemNarrowing);

                    // Remove original field which refers to the inner class.
                    JFieldVar implField = implementationClass.fields().get(fieldName);
                    implementationClass.removeField(implField);

                    // Add new wrapped version of the field using the original field name.
                    // CODE: protected I<T> fieldName;
                    implField = implementationClass.field(JMod.PROTECTED, newInterfaceClass, fieldName);

                    // If instantiation is specified to be "early", add code for creating new instance of the
                    // collection class.
                    if (instantiation == Instantiation.EARLY) {
                        writeDebug("Applying EARLY instantiation...");
                        // CODE: ... fieldName = new C<T>();
                        implField.init(JExpr._new(newCollectionClass));
                    }

                    // Annotate the new field with the @XmlElementWrapper annotation using the original field
                    // name as name.
                    JAnnotationUse annotation = implField.annotate(XmlElementWrapper.class);
                    annotation.param("name", XmlElementWrapperPlugin.elementName(field.getPropertyInfo()) == null ? fieldName : XmlElementWrapperPlugin.elementName(field.getPropertyInfo()));
                    writeDebug("XmlElementWrapper(name=" + (XmlElementWrapperPlugin.elementName(field.getPropertyInfo()) == null ? fieldName : XmlElementWrapperPlugin.elementName(field.getPropertyInfo())) + ")");

                    // Annotate the new field with the @XmlElement annotation using the field name from the
                    // wrapped type as name.
                    annotation = implField.annotate(XmlElement.class);
                    annotation.param("name", candidate.getWrappedSchemaTypeName() == null ? candidate.getFieldName() : candidate.getWrappedSchemaTypeName());
                    writeDebug("XmlElement(name=" + (candidate.getWrappedSchemaTypeName() == null ? candidate.getFieldName() : candidate.getWrappedSchemaTypeName()) + ")");

                    // Find original getter and setter methods to remove.
                    methodsToRemove = new ArrayList<JMethod>();
                    for (JMethod m : implementationClass.methods()) {
                        if (m.name().equals("set" + firstUpper(fieldName))
                            || m.name().equals("get" + firstUpper(fieldName))) {
                            methodsToRemove.add(m);
                        }
                    }

                    // Remove original getter and setter methods.
                    for (JMethod m : methodsToRemove) {
                        implementationClass.methods().remove(m);
                    }

                    // Add a new getter method returning the (wrapped) field added.
                    // CODE: public I<T> getFieldname() { ... };
                    JMethod method = implementationClass.method(JMod.PUBLIC, newInterfaceClass, "get"
                        + firstUpper(fieldName));

                    if (instantiation == Instantiation.LAZY) {
                        writeDebug("Applying LAZY instantiation...");
                        // CODE: if (fieldName == null) fieldName = new C<T>();
                        method.body()._if(JExpr.ref(fieldName).eq(JExpr._null()))._then().assign(
                            JExpr.ref(fieldName), JExpr._new(newCollectionClass));
                    }

                    // CODE: return "fieldName";
                    method.body()._return(JExpr.ref(fieldName));
                }
            }
        }
        writeSummary("\t" + modificationCount + " modification(s) to original code.");
        writeSummary(" ");

        writeSummary("Deletions:");
        if (deleteCandidates) {

            // REMOVED:
            // Get the default package from options.
            // This code was used earlier to only get the factory class from the default package.
            // pkg = model.getModel().codeModel._package(opt.defaultPackage);
            // JDefinedClass factoryClass = pkg._getClass(factoryClassName);

            JPackage pkg;

            // Get the factory class from the default package.
            JDefinedClass factoryClass;
            JDefinedClass candidateClass;

            // Visit all candidate classes.
            for (Candidate c : candidates.values()) {
                // Only consider candidates that are actually included...
                if (isIncluded(c)) {
                    // Get the defined class for candidate class.
                    candidateClass = model.getClazz(c.getClassInfo()).implClass;

                    // ADDED:
                    // This code was added to locate the ObjectFactory inside the package of the candidate class.
                    pkg = candidateClass._package();
                    factoryClass = pkg._getClass(factoryClassName);

                    // Remove methods referencing the candidate class from the ObjectFactory.
                    methodsToRemove = new ArrayList<>();
                    for (JMethod m : factoryClass.methods()) {
                        if (m.type().compareTo(candidateClass) == 0) methodsToRemove.add(m);
                    }

                    for (JMethod m : methodsToRemove) {
                        writeSummary("\tRemoving method " + m.type().fullName() + " " + m.name() + " from "
                            + factoryClass.fullName());
                        factoryClass.methods().remove(m);
                        deletionCount++;
                    }

                    // Remove the candidate from the class or package it is defined in.
                    if (candidateClass.parentContainer().isClass()) {
                        // The candidate class is an inner class. Remove the class from its parent class.
                        JDefinedClass parent = (JDefinedClass) candidateClass.parentContainer();
                        writeSummary("\tRemoving class " + candidateClass.fullName() + " from class " + parent.fullName());
                        Iterator<JDefinedClass> itor = parent.classes();
                        while (itor.hasNext()) {
                            if (itor.next().equals(candidateClass)) {
                                itor.remove();
                                break;
                            }
                        }
                        deletionCount++;
                    } else {
                        // The candidate class in in a package. Remove the class from the package.
                        JPackage parent = (JPackage) candidateClass.parentContainer();
                        writeSummary("\tRemoving class " + candidateClass.fullName() + " from package " + parent.name());
                        parent.remove(candidateClass);
                        deletionCount++;
                    }
                }
            }
        }
        writeSummary("\t" + deletionCount + " deletion(s) from original code.");
        writeSummary(" ");

        try {
            writeDebug("Closing summary...");
            closeSummary();
        } catch (IOException e) {
            // TODO BJH: How would this type of exception be reported? Should it just be ignored?
        }
        writeDebug("Done");
        return true;
    }

    protected boolean isIncluded(Candidate candidate) {
        //
        // A candidate is included if, ...
        // 1. No includes and no excludes have been specified
        // 2. Includes have been specified and canditate is included, and no excludes have been
        // specified.
        // 3. No includes have been specified and excludes have been specified and candidate is not in
        // excludes.
        // 4. Both includes and excludes have been specified and candidate is in includes and not in
        // excludes.
        //
        if (!hasIncludes() && !hasExcludes()) {
            return true; // [+] (default)
        } else if (hasIncludes() && !hasExcludes()) {
            return include.contains(candidate.getClassName()); // [+/-] (included)
        } else if (!hasIncludes() && hasExcludes()) {
            return !exclude.contains(candidate.getClassName()); // [+/-] (excluded)
        } else {
            return include.contains(candidate.getClassName())
                && !exclude.contains(candidate.getClassName()); // [+/-] (override)
        }
    }

    protected String getIncludeOrExcludeReason(Candidate candidate) {
        if (!hasIncludes() && !hasExcludes()) {
            return "(default)"; // [+] (default)
        } else if (hasIncludes() && !hasExcludes()) {
            return "(included)";
        } else if (!hasIncludes() && hasExcludes()) {
            return "(excluded)";
        } else {
            return "(override)";
        }
    }

    protected boolean hasIncludes() {
        return include != null;
    }

    protected boolean hasExcludes() {
        return exclude != null;
    }

    protected void readCandidates(File file, Set<String> candidates) throws IOException {
        LineNumberReader input;
        String line;

        input = new LineNumberReader(new FileReader(file));
        while ((line = input.readLine()) != null) {
            line = line.trim();

            // Lines starting with # are considered comments.
            if (!line.startsWith("#")) {
                candidates.add(line);
            }
        }
        input.close();
    }

    protected String firstUpper(String s) {
        if (s == null) return null;
        if (s.length() == 0) return "";
        return s.substring(0, 1).toUpperCase() + s.substring(1);
    }

    protected Map<String, Candidate> findCandidateClasses(Model model, ErrorHandler errorHandler) {
        Map<String, Candidate> candidates = new HashMap<>();

        // Visit all beans created by JAXB processing.
        for (CClassInfo classInfo : model.beans().values()) {
            String className = classInfo.fullName();

            // The candidate class must have exactly one property.
            if (classInfo.getProperties().size() == 1) {
                CPropertyInfo property = classInfo.getProperties().get(0);

                // The property must be a collection
                if (property.isCollection()) {
                    if (property.ref().size() == 1) {
                        // We have a candidate class.
                        Candidate candidate = new Candidate(classInfo);
                        candidates.put(className, candidate);
                        writeDebug("Candidate found: " + candidate.getClassName() + ", " + candidate.getFieldName() + ", [" + candidate.getFieldTypeName() + "]");
                    }
                }
            }
        }
        return candidates;
    }

    protected void writeSummary(String s) {
        if (summary != null) {
            summary.println(s);
            if (verbose) {
                System.out.println(s);
            }
        } else if (verbose) {
            System.out.println(s);
        }
    }

    protected void closeSummary() throws IOException {
        if (summary != null) {
            summary.close();
        }
    }

    protected void writeDebug(String s) {
        if (debugMode) {
            System.out.println("DEBUG:" + s);
        }
    }

    protected static String elementName(CPropertyInfo property) {
        try {
            if (property instanceof CElementPropertyInfo) {
                return ((CElementPropertyInfo) property).getTypes().get(0).getTagName().getLocalPart();
            } else {
                return null;
            }
        } catch (Exception ex) {
            return null;
        }
    }
}
