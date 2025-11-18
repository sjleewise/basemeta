package kr.wise.meta.ddl.script.model;

/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

/**
 * Represents the database model, ie. the tables in the database. It also
 * contains the corresponding dyna classes for creating dyna beans for the
 * objects stored in the tables.
 *
 * @version $Revision: 636151 $
 */
public class Database implements Serializable
{
    /** Unique ID for serialization purposes. */
    private static final long serialVersionUID = -3160443396757573868L;

    /** The name of the database model. */
    private String _name;
    /** The method for generating primary keys (currently ignored). */
    private String _idMethod;
    /** The version of the model. */
    private String _version;
    /** The tables. */
    private ArrayList _tables = new ArrayList();

    /**
     * Creates an empty model without a name.
     */
    public Database()
    {}

    /**
     * Creates an empty model with the given name.
     *
     * @param name The name
     */
    public Database(String name)
    {
        _name = name;
    }


    /**
     * Returns the name of this database model.
     *
     * @return The name
     */
    public String getName()
    {
        return _name;
    }

    /**
     * Sets the name of this database model.
     *
     * @param name The name
     */
    public void setName(String name)
    {
        _name = name;
    }

    /**
     * Returns the version of this database model.
     *
     * @return The version
     */
    public String getVersion()
    {
        return _version;
    }

    /**
     * Sets the version of this database model.
     *
     * @param version The version
     */
    public void setVersion(String version)
    {
        _version = version;
    }

    /**
     * Returns the method for generating primary key values.
     *
     * @return The method
     */
    public String getIdMethod()
    {
        return _idMethod;
    }

    /**
     * Sets the method for generating primary key values. Note that this
     * value is ignored by DdlUtils and only for compatibility with Torque.
     *
     * @param idMethod The method
     */
    public void setIdMethod(String idMethod)
    {
        _idMethod = idMethod;
    }

    /**
     * Returns the number of tables in this model.
     *
     * @return The number of tables
     */
    public int getTableCount()
    {
        return _tables.size();
    }

    /**
     * Returns the tables in this model.
     *
     * @return The tables
     */
    public Table[] getTables()
    {
        return (Table[])_tables.toArray(new Table[_tables.size()]);
    }

    /**
     * Returns the table at the specified position.
     *
     * @param idx The index of the table
     * @return The table
     */
    public Table getTable(int idx)
    {
        return (Table)_tables.get(idx);
    }

    /**
     * Adds a table.
     *
     * @param table The table to add
     */
    public void addTable(Table table)
    {
        if (table != null)
        {
            _tables.add(table);
        }
    }

    /**
     * Adds a table at the specified position.
     *
     * @param idx   The index where to insert the table
     * @param table The table to add
     */
    public void addTable(int idx, Table table)
    {
        if (table != null)
        {
            _tables.add(idx, table);
        }
    }

    /**
     * Adds the given tables.
     *
     * @param tables The tables to add
     */
    public void addTables(Collection tables)
    {
        for (Iterator it = tables.iterator(); it.hasNext();)
        {
            addTable((Table)it.next());
        }
    }

    /**
     * Removes the given table. This method does not check whether there are foreign keys to the table.
     *
     * @param table The table to remove
     */
    public void removeTable(Table table)
    {
        if (table != null)
        {
            _tables.remove(table);
        }
    }

    /**
     * Removes the indicated table. This method does not check whether there are foreign keys to the table.
     *
     * @param idx The index of the table to remove
     */
    public void removeTable(int idx)
    {
        _tables.remove(idx);
    }

    /**
     * Removes the given tables. This method does not check whether there are foreign keys to the tables.
     *
     * @param tables The tables to remove
     */
    public void removeTables(Table[] tables)
    {
        _tables.removeAll(Arrays.asList(tables));
    }

    /**
     * Removes all but the given tables. This method does not check whether there are foreign keys to the
     * removed tables.
     *
     * @param tables The tables to keep
     */
    public void removeAllTablesExcept(Table[] tables)
    {
        ArrayList allTables = new ArrayList(_tables);

        allTables.removeAll(Arrays.asList(tables));
        _tables.removeAll(allTables);
    }


    /**
     * Finds the table with the specified name, using case insensitive matching.
     * Note that this method is not called getTable to avoid introspection
     * problems.
     *
     * @param name The name of the table to find
     * @return The table or <code>null</code> if there is no such table
     */
    public Table findTable(String name)
    {
        return findTable(name, false);
    }

    /**
     * Finds the table with the specified name, using case insensitive matching.
     * Note that this method is not called getTable) to avoid introspection
     * problems.
     *
     * @param name          The name of the table to find
     * @param caseSensitive Whether case matters for the names
     * @return The table or <code>null</code> if there is no such table
     */
    public Table findTable(String name, boolean caseSensitive)
    {
        for (Iterator iter = _tables.iterator(); iter.hasNext();)
        {
            Table table = (Table) iter.next();

            if (caseSensitive)
            {
                if (table.getName().equals(name))
                {
                    return table;
                }
            }
            else
            {
                if (table.getName().equalsIgnoreCase(name))
                {
                    return table;
                }
            }
        }
        return null;
    }

    /**
     * Returns the indicated tables.
     *
     * @param tableNames    The names of the tables
     * @param caseSensitive Whether the case of the table names matters
     * @return The tables
     */
    public Table[] findTables(String[] tableNames, boolean caseSensitive)
    {
        ArrayList tables = new ArrayList();

        if (tableNames != null)
        {
            for (int idx = 0; idx < tableNames.length; idx++)
            {
                Table table = findTable(tableNames[idx], caseSensitive);

                if (table != null)
                {
                    tables.add(table);
                }
            }
        }
        return (Table[])tables.toArray(new Table[tables.size()]);
    }

    /**
     * Finds the tables whose names match the given regular expression.
     *
     * @param tableNameRegExp The table name regular expression
     * @param caseSensitive   Whether the case of the table names matters; if not, then the regular expression should
     *                        assume that the table names are all-uppercase
     * @return The tables
     * @throws PatternSyntaxException If the regular expression is invalid
     */
    public Table[] findTables(String tableNameRegExp, boolean caseSensitive) throws PatternSyntaxException
    {
        ArrayList tables = new ArrayList();

        if (tableNameRegExp != null)
        {
            Pattern pattern = Pattern.compile(tableNameRegExp);

            for (Iterator tableIt = _tables.iterator(); tableIt.hasNext();)
            {
                Table  table     = (Table)tableIt.next();
                String tableName = table.getName();

                if (!caseSensitive)
                {
                    tableName = tableName.toUpperCase();
                }
                if (pattern.matcher(tableName).matches())
                {
                    tables.add(table);
                }
            }
        }
        return (Table[])tables.toArray(new Table[tables.size()]);
    }


    /**
     * {@inheritDoc}
     */
    public boolean equals(Object obj)
    {
        if (obj instanceof Database)
        {
            Database other = (Database)obj;

            // Note that this compares case sensitive
            return new EqualsBuilder().append(_name,   other._name)
                                      .append(_tables, other._tables)
                                      .isEquals();
        }
        else
        {
            return false;
        }
    }

    /**
     * {@inheritDoc}
     */
    public int hashCode()
    {
        return new HashCodeBuilder(17, 37).append(_name)
                                          .append(_tables)
                                          .toHashCode();
    }

    /**
     * {@inheritDoc}
     */
    public String toString()
    {
        StringBuffer result = new StringBuffer();

        result.append("Database [name=");
        result.append(getName());
        result.append("; ");
        result.append(getTableCount());
        result.append(" tables]");

        return result.toString();
    }

    /**
     * Returns a verbose string representation of this database.
     *
     * @return The string representation
     */
    public String toVerboseString()
    {
        StringBuffer result = new StringBuffer();

        result.append("Database [");
        result.append(getName());
        result.append("] tables:");
        for (int idx = 0; idx < getTableCount(); idx++)
        {
            result.append(" ");
            result.append(getTable(idx).toVerboseString());
        }

        return result.toString();
    }
}
