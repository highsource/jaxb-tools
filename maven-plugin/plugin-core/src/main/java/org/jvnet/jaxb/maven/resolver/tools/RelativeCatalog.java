package org.jvnet.jaxb.maven.resolver.tools;

import org.apache.xml.resolver.Catalog;
import org.apache.xml.resolver.CatalogEntry;
import org.apache.xml.resolver.CatalogException;

import java.util.Vector;

public class RelativeCatalog extends Catalog {
    @Override
    public void addEntry(CatalogEntry entry) {
        super.addEntry(entry);
        if (entry.getEntryType() == REWRITE_SYSTEM) {
            catalogManager.debug.message(0, "REWRITE_SYSTEM relativeCatalog checking");
            try {
                if (entry.getEntryArg(0) != null && entry.getEntryArg(0).startsWith("..")) {
                    // generate new entry for catalog
                    Vector<String> args = new Vector<>(2);
                    args.addElement(makeAbsolute(normalizeURI(entry.getEntryArg(0))));
                    args.addElement(makeAbsolute(normalizeURI(entry.getEntryArg(1))));

                    CatalogEntry duplicatedEntry = new CatalogEntry(entry.getEntryType(), args);

                    catalogManager.debug.message(0, "REWRITE_SYSTEM adding "
                        + duplicatedEntry.getEntryArg(0) + " to " + duplicatedEntry.getEntryArg(1));

                    catalogEntries.addElement(duplicatedEntry);
                }
            } catch (CatalogException e) {
                catalogManager.debug.message(1, "REWRITE_SYSTEM relative fix failed");
            }
        }
    }
}
