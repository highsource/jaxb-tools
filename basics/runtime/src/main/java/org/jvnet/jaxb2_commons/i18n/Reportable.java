package org.jvnet.jaxb2_commons.i18n;

import java.util.ResourceBundle;

/**
 * Reportable object.
 *
 * @author Aleksei Valikov
 */
public interface Reportable
{
  /**
   * Returns message code. This code will be used to locate message resource.
   *
   * @return String code that uniquely identifies this problem. May be used to reference messages.
   */
  public String getMessageCode();

  /**
   * Returns parameters used to format the message.
   *
   * @return Array of parameters used to format problem message.
   */
  public abstract Object[] getMessageParameters();

  /**
   * Formats the message using given resource bundle.
   *
   * @param bundle bundle to use resources from.
   * @return Formatted message.
   */
  public String getMessage(final ResourceBundle bundle);

  /**
   * Returns the message.
   *
   * @return The message.
   */
  public String getMessage();
}
