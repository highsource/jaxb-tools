package org.jpox.samples.hotel;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashSet;
import java.util.StringTokenizer;

/**
 * Hotel.
 */
public class Hotel
{
    private String name;
    private int number;
    private String description;
    private Collection rooms = new HashSet();

    public Hotel(String name, int number, String desc)
    {
        super();
        this.name = name;
        this.number = number;
        this.description = desc;
    }

    public Hotel()
    {
        super();
    }

    public String getName()
    {
        return name;
    }

    public int getNumber()
    {
        return number;
    }

    public String getDescription()
    {
        return description;
    }

    public void setDescription(String desc)
    {
        this.description = desc;
    }

    // Inner class for composite PK
    public static class Oid implements Serializable
    {
        public int number;
        public String name;

        public Oid()
        {
        }

        public Oid(String s)
        {
            StringTokenizer token = new StringTokenizer(s, "::");
            s = token.nextToken ();
            this.name = s;
            s = token.nextToken ();
            this.number = Integer.valueOf(s).intValue();
        }

        public String toString()
        {
            return name + "::" + number;
        }

        public int hashCode()
        {
            return name.hashCode() ^ number;
        }

        public boolean equals(Object other)
        {
            if (other != null && (other instanceof Oid))
            {
                Oid k = (Oid)other;
                return k.name.equals(this.name) && k.number == this.number;
            }
            return false;
        }
    }
}
