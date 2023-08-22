package org.jvnet.hyperjaxb3.jdo.test;

import java.io.File;

import javax.jdo.JDOHelper;
import javax.jdo.PersistenceManager;
import javax.jdo.Transaction;
import javax.jdo.identity.SingleFieldIdentity;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.Unmarshaller;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.jvnet.jaxb2_commons.lang.builder.ExtendedJAXBEqualsBuilder;
import org.jvnet.jaxb2_commons.util.ContextUtils;

public class RoundtripTest extends AbstractJDOSamplesTest {

	@Override
	protected void checkSample(File sample) throws Exception {
		// TODO Auto-generated method stub
		final JAXBContext context = createContext();
		logger.debug("Unmarshalling.");
		final Unmarshaller unmarshaller = context.createUnmarshaller();
		// Unmarshall the document
		final JAXBElement element = (JAXBElement) unmarshaller
				.unmarshal(sample);
		final Object object = element.getValue();
		logger.debug("Opening session.");
		// Open the session, save object into the database
		logger.debug("Saving the object.");
		final PersistenceManager saveManager = createPersistenceManager();
//		saveManager.setDetachAllOnCommit(true);
		final Transaction saveTransaction = saveManager.currentTransaction();
		saveTransaction.setNontransactionalRead(true);
		saveTransaction.begin();
		// final Object merged = saveSession.merge(object);
		// saveSession.replicate(object, ReplicationMode.OVERWRITE);
		// saveSession.get
		// final Serializable id =
		final Object mergedObject = saveManager.makePersistent(object);
		
//		final Object asd = saveManager.detachCopy(object);
		saveTransaction.commit();
//		final Object id = saveManager.getObjectId(mergedObject);
		final Object identity = JDOHelper.getObjectId(object);
		final Object id = identity instanceof SingleFieldIdentity ? ((SingleFieldIdentity) identity).getKeyAsObject() : identity;
		// Close the session
		saveManager.close();

		logger.debug("Opening session.");
		// Open the session, load the object
		final PersistenceManager loadManager = createPersistenceManager();
		final Transaction loadTransaction = loadManager.currentTransaction();
		loadTransaction.setNontransactionalRead(true);
		logger.debug("Loading the object.");
		final Object loadedObject = loadManager.getObjectById(mergedObject.getClass(), id);
		logger.debug("Closing the session.");

		final JAXBElement mergedElement = new JAXBElement(element.getName(),
				element.getDeclaredType(), object);

		final JAXBElement loadedElement = new JAXBElement(element.getName(),
				element.getDeclaredType(), loadedObject);

		logger.debug("Checking the document identity.");

		logger.debug("Source object:\n"
				+ ContextUtils.toString(context, mergedElement));
		logger.debug("Result object:\n"
				+ ContextUtils.toString(context, loadedElement));

		checkObjects(mergedObject, loadedObject);
		loadManager.close();

	}

	protected void checkObjects(final Object object, final Object loadedObject) {
		final EqualsBuilder equalsBuilder = new ExtendedJAXBEqualsBuilder();
		equalsBuilder.append(object, loadedObject);
		assertTrue("Objects must be equal.", equalsBuilder.isEquals());
	}

}
