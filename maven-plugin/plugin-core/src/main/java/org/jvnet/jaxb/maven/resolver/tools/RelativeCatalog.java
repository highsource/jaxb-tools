package org.jvnet.jaxb.maven.resolver.tools;

import com.sun.org.apache.xml.internal.resolver.Catalog;
import com.sun.org.apache.xml.internal.resolver.CatalogEntry;
import com.sun.org.apache.xml.internal.resolver.CatalogException;

import java.util.Vector;

public class RelativeCatalog extends Catalog {
    @Override
    public void addEntry(CatalogEntry entry) {
        super.addEntry(entry);
        if (entry.getEntryType() == REWRITE_SYSTEM) {
            try {
                if (entry.getEntryArg(0) != null && entry.getEntryArg(0).startsWith("..")) {
                    // generate new entry for catalog
                    Vector<String> args = new Vector<>(2);
                    args.addElement(makeAbsolute(normalizeURI(entry.getEntryArg(0))));
                    args.addElement(makeAbsolute(normalizeURI(entry.getEntryArg(1))));

                    CatalogEntry duplicatedEntry = new CatalogEntry(entry.getEntryType(), args);

                    catalogManager.debug.message(4, "REWRITE_SYSTEM",
                        duplicatedEntry.getEntryArg(0), duplicatedEntry.getEntryArg(1));

                    catalogEntries.addElement(duplicatedEntry);
                }
            } catch (CatalogException e) {
                catalogManager.debug.message(1, "REWRITE_SYSTEM relative fix failed");
            }
        }
    }
}
