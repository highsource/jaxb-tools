package org.jvnet.jaxb.plugin.camelcase;

import com.sun.tools.xjc.BadCommandLineException;
import com.sun.tools.xjc.Options;
import com.sun.tools.xjc.Plugin;
import com.sun.tools.xjc.outline.Outline;
import org.xml.sax.ErrorHandler;

/**
 * {@link Plugin} that always converts an XML name into a camel case java name.
 * This plugin changes the first character of every word composing an XML name
 * to uppercase and the others to lowercase, while the default XJC behavior is
 * to do so only if the first character of the word is lowercase.
 * <pre>
 *
 * XJC default:
 *   FIRST_NAME -&gt; FIRSTNAME
 *   FOOBar     -&gt; FOOBar
 *   SSNCode    -&gt; SSNCode
 *
 * Camel case always:
 *   FIRST_NAME -&gt; FirstName
 *   FOOBar     -&gt; FooBar
 *   SSNCode    -&gt; SsnCode
 *
 * </pre>
 *
 * @author Nicola Fagnani
 *
 * This plugin came from here : org.andromda.thirdparty.jaxb2_commons:camelcase-always:1.0
 */
public class CamelCasePlugin extends Plugin {

    /** Constant for the option string. */
    protected final String OPTION_NAME = "Xcamelcase";

    /**
     * Returns the option string used to turn on this plugin.
     *
     * @return
     *      option string to invoke this plugin
     */
    @Override
    public String getOptionName() {
        return OPTION_NAME;
    }

    /**
     * Returns a string specifying how to use this plugin and what it does.
     *
     * @return
     *      string containing the plugin usage message
     */
    @Override
    public String getUsage() {
        return "  -" + OPTION_NAME +
            "    :  converts every XML name to camel case";
    }

    /**
     * On plugin activation, sets a customized NameConverter to adjust code
     * generation.
     *
     * @param opts
     *      options used to invoke XJC
     *
     * @throws com.sun.tools.xjc.BadCommandLineException
     *      if the plugin is invoked with wrong parameters
     */
    @Override
    public void onActivated(Options opts) throws BadCommandLineException {
        opts.setNameConverter( new CamelCaseNameConverter(), this );
    }

    /**
     * Returns true without touching the generated code. All the relevant
     * work is done during name conversion.
     *
     * @param model
     *      This object allows access to various generated code.
     *
     * @param opts
     *      options used to invoke XJC

     * @param errorHandler
     *      Errors should be reported to this handler.
     *
     * @return
     *      If the add-on executes successfully, return true.
     *      If it detects some errors but those are reported and
     *      recovered gracefully, return false.
     */
    @Override
    public boolean run(Outline model, Options opts, ErrorHandler errorHandler ) {
        return true;
    }

}
