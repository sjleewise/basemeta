package kr.wise.meta.ddl.script.platform;

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

import java.io.IOException;
import java.io.Writer;
import java.rmi.server.UID;
import java.sql.Types;
import java.text.DateFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import kr.wise.commons.cmm.exception.WiseBizException;
import kr.wise.commons.damgmt.db.service.WaaDbPrivilege;
import kr.wise.commons.util.UtilString;
import kr.wise.meta.ddl.script.model.Column;
import kr.wise.meta.ddl.script.model.Database;
import kr.wise.meta.ddl.script.model.DdlPartMainVo;
import kr.wise.meta.ddl.script.model.EtcObject;
import kr.wise.meta.ddl.script.model.Grant;
import kr.wise.meta.ddl.script.model.Index;
import kr.wise.meta.ddl.script.model.IndexColumn;
import kr.wise.meta.ddl.script.model.Partition;
import kr.wise.meta.ddl.script.model.Sequence;
import kr.wise.meta.ddl.script.model.Table;
import kr.wise.meta.ddl.service.WaqDdlPartSub;

import org.apache.commons.collections.map.ListOrderedMap;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * This class is a collection of Strategy methods for creating the DDL required to create and drop
 * databases and tables.
 *
 * It is hoped that just a single implementation of this class, for each database should make creating DDL
 * for each physical database fairly straightforward.
 *
 * An implementation of this class can always delegate down to some templating technology such as Velocity if
 * it requires. Though often that can be quite complex when attempting to reuse code across many databases.
 * Hopefully only a small amount code needs to be changed on a per database basis.
 *
 * @version $Revision: 893941 $
 */
public abstract class SqlBuilder
{
    /** The line separator for in between sql commands. */
    private static final String LINE_SEPARATOR = System.getProperty("line.separator", "\n");
    /** The placeholder for the size value in the native type spec. */
    protected static final String SIZE_PLACEHOLDER = "{0}";

    /** The Log to which logging calls will be made. */
    protected final Log _log = LogFactory.getLog(SqlBuilder.class);

    /** The platform that this builder belongs to. */
//    private Platform _platform;

    private PlatformInfo _platPlatformInfo  = new PlatformInfo();
    /** The current Writer used to output the SQL to. */
    private Writer _writer;
    /** The indentation used to indent commands. */
    private String _indent = "    ";
    /** An optional locale specification for number and date formatting. */
    private String _valueLocale;
    /** The date formatter. */
    private DateFormat _valueDateFormat;
    /** The date time formatter. */
    private DateFormat _valueTimeFormat;
    /** The number formatter. */
    private NumberFormat _valueNumberFormat;
    /** Helper object for dealing with default values. */
    private DefaultValueHelper _defaultValueHelper = new DefaultValueHelper();
    /** The character sequences that need escaping. */
    private Map _charSequencesToEscape = new ListOrderedMap();

    //
    // Configuration
    //


    public SqlBuilder()
    {

    }
    /**
     * Creates a new sql builder.
     *
     * @param platform The plaftform this builder belongs to
     */
//    public SqlBuilder(Platform platform)
//    {
//        _platform = platform;
//    }

    /**
     * Returns the platform object.
     *
     * @return The platform
     */
//    public Platform getPlatform()
//    {
//        return _platform;
//    }

    /**
     * Returns the platform info object.
     *
     * @return The info object
     */
    public PlatformInfo getPlatformInfo()
    {
        return _platPlatformInfo;
    }

    /**
     * Returns the writer that the DDL is printed to.
     *
     * @return The writer
     */
    public Writer getWriter()
    {
        return _writer;
    }

    /**
     * Sets the writer for printing the DDL to.
     *
     * @param writer The writer
     */
    public void setWriter(Writer writer)
    {
        _writer = writer;
    }

    /**
     * Returns the default value helper.
     *
     * @return The default value helper
     */
    public DefaultValueHelper getDefaultValueHelper()
    {
        return _defaultValueHelper;
    }

    /**
     * Returns the string used to indent the SQL.
     *
     * @return The indentation string
     */
    public String getIndent()
    {
        return _indent;
    }

    /**
     * Sets the string used to indent the SQL.
     *
     * @param indent The indentation string
     */
    public void setIndent(String indent)
    {
        _indent = indent;
    }

    /**
     * Returns the locale that is used for number and date formatting
     * (when printing default values and in generates insert/update/delete
     * statements).
     *
     * @return The locale or <code>null</code> if default formatting is used
     */
    public String getValueLocale()
    {
        return _valueLocale;
    }

    /**
     * Sets the locale that is used for number and date formatting
     * (when printing default values and in generates insert/update/delete
     * statements).
     *
     * @param localeStr The new locale or <code>null</code> if default formatting
     *                  should be used; Format is "language[_country[_variant]]"
     */
    public void setValueLocale(String localeStr)
    {
        if (localeStr != null)
        {
            int    sepPos   = localeStr.indexOf('_');
            String language = null;
            String country  = null;
            String variant  = null;

            if (sepPos > 0)
            {
                language = localeStr.substring(0, sepPos);
                country  = localeStr.substring(sepPos + 1);
                sepPos   = country.indexOf('_');
                if (sepPos > 0)
                {
                    variant = country.substring(sepPos + 1);
                    country = country.substring(0, sepPos);
                }
            }
            else
            {
                language = localeStr;
            }
            if (language != null)
            {
                Locale locale = null;

                if (variant != null)
                {
                    locale = new Locale(language, country, variant);
                }
                else if (country != null)
                {
                    locale = new Locale(language, country);
                }
                else
                {
                    locale = new Locale(language);
                }

                _valueLocale = localeStr;
                setValueDateFormat(DateFormat.getDateInstance(DateFormat.SHORT, locale));
                setValueTimeFormat(DateFormat.getTimeInstance(DateFormat.SHORT, locale));
                setValueNumberFormat(NumberFormat.getNumberInstance(locale));
                return;
            }
        }
        _valueLocale = null;
        setValueDateFormat(null);
        setValueTimeFormat(null);
        setValueNumberFormat(null);
    }

    /**
     * Returns the format object for formatting dates in the specified locale.
     *
     * @return The date format object or null if no locale is set
     */
    protected DateFormat getValueDateFormat()
    {
        return _valueDateFormat;
    }

    /**
     * Sets the format object for formatting dates in the specified locale.
     *
     * @param format The date format object
     */
    protected void setValueDateFormat(DateFormat format)
    {
        _valueDateFormat = format;
    }

    /**
     * Returns the format object for formatting times in the specified locale.
     *
     * @return The time format object or null if no locale is set
     */
    protected DateFormat getValueTimeFormat()
    {
        return _valueTimeFormat;
    }

    /**
     * Sets the date format object for formatting times in the specified locale.
     *
     * @param format The time format object
     */
    protected void setValueTimeFormat(DateFormat format)
    {
        _valueTimeFormat = format;
    }

    /**
     * Returns the format object for formatting numbers in the specified locale.
     *
     * @return The number format object or null if no locale is set
     */
    protected NumberFormat getValueNumberFormat()
    {
        return _valueNumberFormat;
    }

    /**
     * Returns a new date format object for formatting numbers in the specified locale.
     * Platforms can override this if necessary.
     *
     * @param format The number format object
     */
    protected void setValueNumberFormat(NumberFormat format)
    {
        _valueNumberFormat = format;
    }

    /**
     * Adds a char sequence that needs escaping, and its escaped version.
     *
     * @param charSequence   The char sequence
     * @param escapedVersion The escaped version
     */
    protected void addEscapedCharSequence(String charSequence, String escapedVersion)
    {
        _charSequencesToEscape.put(charSequence, escapedVersion);
    }

    /**
     * Returns the maximum number of characters that a table name can have.
     * This method is intended to give platform specific builder implementations
     * more control over the maximum length.
     *
     * @return The number of characters, or -1 if not limited
     */
    public int getMaxTableNameLength()
    {
        return getPlatformInfo().getMaxTableNameLength();
    }

    /**
     * Returns the maximum number of characters that a column name can have.
     * This method is intended to give platform specific builder implementations
     * more control over the maximum length.
     *
     * @return The number of characters, or -1 if not limited
     */
    public int getMaxColumnNameLength()
    {
        return getPlatformInfo().getMaxColumnNameLength();
    }

    /**
     * Returns the maximum number of characters that a constraint name can have.
     * This method is intended to give platform specific builder implementations
     * more control over the maximum length.
     *
     * @return The number of characters, or -1 if not limited
     */
    public int getMaxConstraintNameLength()
    {
        return getPlatformInfo().getMaxConstraintNameLength();
    }

    /**
     * Returns the maximum number of characters that a foreign key name can have.
     * This method is intended to give platform specific builder implementations
     * more control over the maximum length.
     *
     * @return The number of characters, or -1 if not limited
     */
    public int getMaxForeignKeyNameLength()
    {
        return getPlatformInfo().getMaxForeignKeyNameLength();
    }

    //
    // public interface
    //

    /**
     * Outputs the DDL required to drop and (re)create all tables in the database model.
     *
     * @param database The database model
     */
//    public void createTables(Database database) throws IOException
//    {
//        createTables(database, null, true);
//    }

    /**
     * Outputs the DDL required to drop (if requested) and (re)create all tables in the database model.
     *
     * @param database   The database
     * @param dropTables Whether to drop tables before creating them
     */
//    public void createTables(Database database, boolean dropTables) throws IOException
//    {
//        createTables(database, null, dropTables);
//    }

    /**
     * Outputs the DDL required to drop (if requested) and (re)create all tables in the database model.
     *
     * @param database   The database
     * @param params     The parameters used in the creation
     * @param dropTables Whether to drop tables before creating them
     */
//    public void createTables(Database database, CreationParameters params, boolean dropTables) throws IOException
//    {
//        if (dropTables)
//        {
//            dropTables(database);
//        }
//
//        for (int idx = 0; idx < database.getTableCount(); idx++)
//        {
//            Table table = database.getTable(idx);
//
//            writeTableComment(table);
//            createTable(database,
//                        table,
//                        params == null ? null : params.getParametersFor(table));
//        }
//
//        // we're writing the external foreignkeys last to ensure that all referenced tables are already defined
//        createForeignKeys(database);
//    }

    /**
     * Outputs the DDL to create the given temporary table. Per default this is simply
     * a call to {@link #createTable(Database, Table, Map)}.
     *
     * @param database   The database model
     * @param table      The table
     * @param parameters Additional platform-specific parameters for the table creation
     */
    protected void createTemporaryTable(Database database, Table table, Map parameters) throws IOException
    {
        createTable(database, table, parameters);
    }

    /**
     * Outputs the DDL to drop the given temporary table. Per default this is simply
     * a call to {@link #dropTable(Table)}.
     *
     * @param database The database model
     * @param table    The table
     */
    protected void dropTemporaryTable(Database database, Table table) throws IOException
    {
        dropTable(table);
    }


    /**
     * Writes a cast expression that converts the value of the source column to the data type
     * of the target column. Per default, simply the name of the source column is written
     * thereby assuming that any casts happen implicitly.
     *
     * @param sourceColumn The source column
     * @param targetColumn The target column
     */
    protected void writeCastExpression(Column sourceColumn, Column targetColumn) throws IOException
    {
        printIdentifier(getColumnName(sourceColumn));
    }

    /**
     * Compares the two strings.
     *
     * @param string1     The first string
     * @param string2     The second string
     * @param caseMatters Whether case matters in the comparison
     * @return <code>true</code> if the string are equal
     */
    protected boolean areEqual(String string1, String string2, boolean caseMatters)
    {
        return (caseMatters  && string1.equals(string2)) ||
               (!caseMatters && string1.equalsIgnoreCase(string2));
    }

    /**
     * Outputs the DDL to create the table along with any non-external constraints as well
     * as with external primary keys and indices (but not foreign keys).
     *
     * @param database The database model
     * @param table    The table
     */
    public void createTable(Database database, Table table) throws IOException
    {
        createTable(database, table, null); 
    }

    /**
     * Outputs the DDL to create the table along with any non-external constraints as well
     * as with external primary keys and indices (but not foreign keys).
     *
     * @param database   The database model
     * @param table      The table
     * @param parameters Additional platform-specific parameters for the table creation
     */
    public void createTable(Database database, Table table, Map parameters) throws IOException
    {
        writeTableCreationStmt(database, table, parameters);
        writeTableSpaceStmt (table);
        
        //파티션이 존재할 경우 파티션 정보 출력
        createPartition(database, table, parameters);         
        
        writeTableCreationStmtEnding(table, parameters);

        if (!getPlatformInfo().isPrimaryKeyEmbedded())
        {
            createPrimaryKey(table, table.getPrimaryKeyColumns());
        }
        if (!getPlatformInfo().isIndicesEmbedded())
        {
            createIndexes(table);        	
        }
        //grant
        if (!getPlatformInfo().getGrantEmbedded())
        {
        	//createGrant(table);
        	//createGrants(table);
        }
        //SLC DBA 요청사항
        //createSynonyms(table);
        
    }

    public void alterTable(Table table) throws IOException
    {
    	    	    	
    	//======테이블 커멘트 변경=======
    	String tblCmmtUpdYn = UtilString.null2Blank(table.getDdltbl().getTblCmmtUpdYn());
    	
    	if(tblCmmtUpdYn.equals("Y")) {
    		
    		writeTableCommentStmt(table);
    	}
    	//==============================
    	
    	//컬럼 ALTER
    	writeAlterColumns(table);
    	
		//컬럼 변경사항 존재 && 그 컬럼이 PK인 경우 체크
    	boolean chk = false;
    	for(int idx = 0; idx < table.getColumnCount(); idx++){
    		Column col = table.getColumn(idx);
    		
    		if(!UtilString.isNull(col.getRegTypCd())){
    			if(col.isPrimaryKey() == true){
    				chk = true;
    			}
    		}
    	}
    	

    	if (!getPlatformInfo().isPrimaryKeyEmbedded())
    	{
    		if(chk){
        		createPrimaryKey(table, table.getPrimaryKeyColumns());
    		}
    	}
    	if (!getPlatformInfo().isIndicesEmbedded())
    	{
    		createIndexes(table);
    	}
    }
    
    public void renameTable(Table table) throws IOException
    {
    	if(!StringUtils.isBlank(table.getDdltbl().getBfDdlTblPnm())) {
			print("RENAME ");
			print(table.getDdltbl().getBfDdlTblPnm());
			print(" TO ");
			println(table.getName());
		}
    }

    /** @param table insomnia
     * @throws IOException */
	public void writeAlterColumns(Table table) throws IOException {
		for (int idx = 0; idx < table.getColumnCount(); idx++)
        {
            //printIndent();
            Column col = table.getColumn(idx);
//            if(!"1".equals(col.getVrfCd())) {
//            	continue;
//            }

            if("D".equals(col.getRegTypCd())) {
            	//컬럼 삭제...
            	dropColumn(table, col);
            } else if ("U".equals(col.getRegTypCd()) && "Y".equals(col.getColUpdYn())){
            	//컬럼 업데이트...
            	modifyColumn(table, col);
            } else if ("C".equals(col.getRegTypCd())){
            	//컬럼 추가....
            	addColumn(table, col);
            	
            	//커멘트 추가
            	addColCommentStmt(table, col);
            }
        }

	}

	public void createComment(Table table) throws IOException {
    	writeTableCommentStmt(table);
    	writeColCommentStmt(table);
    	println();
    }

    /** @param table insomnia
     * @throws IOException */
    public void writeTableCommentStmt(Table table) throws IOException {
//    	if(!StringUtils.isBlank(table.getDdltbl().getDdlTblLnm())) {
    	if(!StringUtils.isBlank(table.getDescription())) {

    		print("COMMENT ON TABLE ");
    		print(getTableName(table));
    		print(" IS '");
    		print(table.getDescription());
//    		print(table.getDdltbl().getDdlTblLnm());
    		print("'");
    		println(getPlatformInfo().getSqlCommandDelimiter());

    	}

    }

    /** @param table insomnia
     * @throws IOException */
	public void writeColCommentStmt(Table table) throws IOException {
        for (int idx = 0; idx < table.getColumnCount(); idx++)
        {
        	Column col = table.getColumn(idx);
        	if(UtilString.null2Blank(col.getRegTypCd()).equals("D"))
        		continue;
//        	if(!StringUtils.isBlank(col.getDdlColLnm())) {
        	if(!StringUtils.isBlank(col.getDescription())) {
        		print("COMMENT ON COLUMN ");
        		print(getTableName(table));
        		print(".");
        		print(getColumnName(col));
        		
        		print(spaceFull(40 - getColumnName(col).length()));
        		
        		print(" IS '");
        		print(col.getDescription());
//        		print(col.getDdlColLnm());
        		print("'");
        		println(getPlatformInfo().getSqlCommandDelimiter());
        	}

        }

	}
	
    public void addColCommentStmt(Table table, Column col) throws IOException
    {
    	print("COMMENT ON COLUMN ");
		print(getTableName(table));
		print(".");
		print(getColumnName(col));
		
		print(spaceFull(40 - getColumnName(col).length()));
		
		print(" IS '");
		print(col.getDescription());
//		print(col.getDdlColLnm());
		print("'");
		println(getPlatformInfo().getSqlCommandDelimiter());
		
		println(); 
		
    }

	/** @param table insomnia
     * @throws IOException */
    public void writeTableSpaceStmt(Table table) throws IOException {
		if(!StringUtils.isBlank(table.getTblSpacPnm())) {
			print(" TABLESPACE ");
			println(table.getTblSpacPnm());
		}

	}
    
	/**
     * Writes the primary key constraints of the table as alter table statements.
     *
     * @param table             The table
     * @param primaryKeyColumns The primary key columns
     */
    public void createPrimaryKey(Table table, Column[] primaryKeyColumns) throws IOException
    {
        if ((primaryKeyColumns.length > 0) && shouldGeneratePrimaryKeys(primaryKeyColumns))
        {
        	print("CREATE UNIQUE INDEX ");
        	print(table.getSchema() + ".");        	
        	printIdentifier(getConstraintName("PK", table, null, null));
        	print(" ON ");
        	printlnIdentifier(getTableName(table));         	
        	writePrimaryUniqueIdxStmt(table, primaryKeyColumns); 
        	
        	println(getPlatformInfo().getSqlCommandDelimiter());
        	println();
        	
            print("ALTER TABLE ");
            printlnIdentifier(getTableName(table));
            printIndent();
            print("ADD CONSTRAINT ");
            printIdentifier(getConstraintName("PK", table, null, null));
            print(" ");
            writePrimaryKeyStmt(table, primaryKeyColumns);
           
            
        	println(getPlatformInfo().getSqlCommandDelimiter());
        	println();
        }
    }
    

    /**
     * Writes the indexes for the given table using external index creation statements.
     *
     * @param table The table
     */
    public void createIndexes(Table table) throws IOException
    {
        for (int idx = 0; idx < table.getIndexCount(); idx++)
        {
            Index index = table.getIndex(idx);

            if (!index.isUnique() && !getPlatformInfo().isIndicesSupported())
            {
                throw new WiseBizException("Platform does not support non-unique indices");
            }
            createIndex(table, index);
        }
    }

    /**
     * Writes the given index for the table using an external index creation statement.
     *
     * @param table The table
     * @param index The index
     */
    public void createIndex(Table table, Index index) throws IOException
    {
        if (!getPlatformInfo().isIndicesSupported())
        {
            throw new WiseBizException("This platform does not support indexes");
        }
        else if (index.getName() == null)
        {
            _log.warn("Cannot write unnamed index " + index);
        }
        else
        {
            print("CREATE");
            if (index.isUnique())
            {
                print(" UNIQUE");
            }
            print(" INDEX ");
            printIdentifier(getIndexName(index));
            print(" ON ");
            printIdentifier(getTableName(table));
            print(" (");

            for (int idx = 0; idx < index.getColumnCount(); idx++)
            {
                IndexColumn idxColumn = index.getColumn(idx);
                Column      col       = table.findColumn(idxColumn.getName());
                if(UtilString.null2Blank(col.getRegTypCd()).equals("D"))
                	continue;
                if (col == null)
                {
                    // would get null pointer on next line anyway, so throw exception
                    throw new WiseBizException("Invalid column '" + idxColumn.getName() + "' on index " + index.getName() + " for table " + table.getName());
                }
                if (idx > 0)
                {
                    print(", ");
                }
                printIdentifier(getColumnName(col));
            }

            print(")");
            
          //인덱스 테이블 스페이스 문장 추가.
            if(!StringUtils.isBlank(table.getIdxSpacPnm())) {
    			print(" TABLESPACE ");
    			println(table.getIdxSpacPnm());
    		}
            printEndOfStatement();
        }
    }
    /**
     * Writes the given index for the table using an external index creation statement.
     *
     * @param table The table
     * @param index The index
     */
    public void createIndex(Table table) throws IOException
    {
    	if (!getPlatformInfo().isIndicesSupported())
    	{
    		throw new WiseBizException("This platform does not support indexes");
    	}
    	else if (table.getIdxname() == null)
    	{
    		_log.warn("Cannot write unnamed index " + table.getIdxname() );
    	}
    	else
    	{
    		
    		if(UtilString.null2Blank( table.getIdxtype() ).equals("PK")){
    			print("ALTER TABLE ");
    			printlnIdentifier(getTableName(table));
    			print(" ADD CONSTRAINT  ");
    			printIdentifier(table.getIdxname());
    			print(" PRIMARY KEY ");
    			print(" (");
        		
        		for (int idx = 0; idx < table.getColumnCount(); idx++)
        		{
        			Column idxColumn = table.getColumn(idx);
        			Column      col       = table.findColumn(idxColumn.getName());
        			
        			if (col == null)
        			{
        				// would get null pointer on next line anyway, so throw exception
        				throw new WiseBizException("Invalid column '" + idxColumn.getName() + "' on index " + table.getIdxname() + " for table " + table.getName());
        			}
        			if(UtilString.null2Blank(col.getRegTypCd()).equals("D"))
        				continue;
        			if (idx > 0)
        			{
        				print(", ");
        			}
        			printIdentifier(getColumnName(col));
        		}
        		print(")");
    			
    		}else{
    			print("CREATE");
        		if (UtilString.null2Blank(table.getIdxtype()).equals("UK"))
        		{
        			print(" UNIQUE");
        		}
        		print(" INDEX ");        		
        		print(table.getSchema() + ".");
        		printIdentifier(table.getIdxname());
        		print(" ON ");
        		printIdentifier(getTableName(table));
        		
        		println();
        		print("(");
        		
        		for (int idx = 0; idx < table.getColumnCount(); idx++)
        		{
        			Column idxColumn = table.getColumn(idx);
        			Column      col     = table.findColumn(idxColumn.getName());
        			
        			if (col == null)
        			{
        				// would get null pointer on next line anyway, so throw exception
        				throw new WiseBizException("Invalid column '" + idxColumn.getName() + "' on index " + table.getIdxname() + " for table " + table.getName());
        			}
            		if(UtilString.null2Blank(col.getRegTypCd()).equals("D"))
            			continue;
        			if (idx > 0)
        			{
        				print(", ");
        			}
        			printIdentifier( col.getName() + " "+col.getType() );
        		}
        		print(")");
    		}
    		
    		println();
    		
    		if(!UtilString.null2Blank(table.getIdxSpacPnm()).equals("")){
    			print(" TABLESPACE ");
    			printIdentifier(table.getIdxSpacPnm());
    		}

    		
    		printEndOfStatement();
    	}
    }
    
    public void createSequence(Sequence seq) throws IOException
    {
    	if (!getPlatformInfo().isIndicesSupported())
    	{
    		throw new WiseBizException("This platform does not support sequences");
    	}
    	else if (seq.getName() == null)
    	{
    		_log.warn("Cannot write unnamed sequence " + seq.getName() );
    	}
    	else
    	{
    		print("CREATE SEQUENCE ");

        	printIdentifier(getSequenceName(seq));
        	println("");
        	if (seq.getStrtwt() != null 
        			&& !UtilString.null2Blank(seq.getStrtwt()).equals(""))
    		{
        		print("START WITH ");
        		println(seq.getStrtwt());
    		}
        	
        	if (seq.getIncby() != null 
        			&& !UtilString.null2Blank(seq.getIncby()).equals(""))
    		{
        		print("INCREMENT BY ");
        		println(seq.getIncby());
    		} 
        	
        	if (seq.getMinval() != null 
        			&& !UtilString.null2Blank(seq.getMinval()).equals(""))
    		{
        		print("MINVALUE ");
        		println(seq.getMinval());
    		}
        	
        	if (seq.getMaxval() != null 
        			&& !UtilString.null2Blank(seq.getMaxval()).equals(""))
    		{
        		print("MAXVALUE ");
        		println(seq.getMaxval());
    		}
        	
        	if (seq.getCycYn() != null 
        			&& !UtilString.null2Blank(seq.getCycYn()).equals(""))
    		{
        		if(seq.getCycYn().equals("Y")){
        			println("CYCLE");
        		}else{
        			println("NOCYCLE");
        		}        		
    		}
        	
        	if (seq.getOrdYn() != null 
        			&& !UtilString.null2Blank(seq.getOrdYn()).equals(""))
    		{
        		if(seq.getOrdYn().equals("Y")){
        			println("ORDER");
        		}else{
        			println("NOORDER");
        		}        		
    		}
        	
        	if (seq.getCacheSz() != null 
        			&& !UtilString.null2Blank(seq.getCacheSz()).equals(""))
    		{
        		print("CACHE ");
        		println(seq.getCacheSz());
    		}
        	
    		printEndOfStatement();
    	}
    }
    
    public void createSerial(Sequence seq) throws IOException
    {
    	if (!getPlatformInfo().isIndicesSupported())
    	{
    		throw new WiseBizException("This platform does not support sequences");
    	}
    	else if (seq.getName() == null)
    	{
    		_log.warn("Cannot write unnamed sequence " + seq.getName() );
    	}
    	else
    	{
    		print("CREATE SERIAL ");

        	printIdentifier(getSequenceName(seq));
        	println("");
        	if (seq.getStrtwt() != null 
        			&& !UtilString.null2Blank(seq.getStrtwt()).equals(""))
    		{
        		print("START WITH ");
        		println(seq.getStrtwt());
    		}
        	
        	if (seq.getIncby() != null 
        			&& !UtilString.null2Blank(seq.getIncby()).equals(""))
    		{
        		print("INCREMENT BY ");
        		println(seq.getIncby());
    		} 
        	
        	if (seq.getMinval() != null 
        			&& !UtilString.null2Blank(seq.getMinval()).equals(""))
    		{
        		print("MINVALUE ");
        		println(seq.getMinval());
    		}
        	
        	if (seq.getMaxval() != null 
        			&& !UtilString.null2Blank(seq.getMaxval()).equals(""))
    		{
        		print("MAXVALUE ");
        		println(seq.getMaxval());
    		}
        	
        	if (seq.getCycYn() != null 
        			&& !UtilString.null2Blank(seq.getCycYn()).equals(""))
    		{
        		if(seq.getCycYn().equals("Y")){
        			println("CYCLE");
        		}else{
        			println("NOCYCLE");
        		}        		
    		}
        	
        	if (seq.getOrdYn() != null 
        			&& !UtilString.null2Blank(seq.getOrdYn()).equals(""))
    		{
        		if(seq.getOrdYn().equals("Y")){
        			println("ORDER");
        		}else{
        			println("NOORDER");
        		}        		
    		}
        	
        	if (seq.getCacheSz() != null 
        			&& !UtilString.null2Blank(seq.getCacheSz()).equals(""))
    		{
        		print("CACHE ");
        		println(seq.getCacheSz());
    		}
        	
    		printEndOfStatement();
    	}
    }

   public void createSeqSynonyms(Sequence seq) throws IOException
    {
    	//sequence당 1개의 synonym 출력
    	String seqNm = seq.getName();
    	String schema = seq.getSchema();
		print("CREATE OR REPLACE PUBLIC SYNONYM ");
		print(seqNm);
		print(" FOR ");
		print(schema +"."+seqNm);
        println(getPlatformInfo().getSqlCommandDelimiter());
    	println();
    	
    }
    
    public void createSeqGrants(Sequence seq) throws IOException
    {
    	String seqNm = seq.getName();
    	String schema = seq.getSchema();
		print("GRANT SELECT ON ");
		print(schema +"."+seqNm);
		print(" TO ");
//		print("RL_"+seqNm.substring(3, 6)+"_SEL");
		print("RL_"+seqNm+"_SEL");
        println(getPlatformInfo().getSqlCommandDelimiter());
    	println();
    	
//    	String seqNm = seq.getName();
//    	String exeProcNm = "DBPOWN.SP_SEQ_RECREATE"; //수행 프로시져명
//    	String grtLst[] = null;
//    	String tmpRoleNm = "";
//    	
//		String schema = "";
//		String grantNm = "";
//   	
//		schema = seq.getSchema();
//		if (!StringUtils.isBlank(schema)) {
//			seqNm = schema +"." + seq.getName();
//		}
//		
//		
//		if (!StringUtils.isBlank(seq.getGrtLst()) ) {
//			grtLst = seq.getGrtLst().split(";");
//			
//			//1. SELECT			
//			for(int i=0 ; i<grtLst.length ; i++){
//				tmpRoleNm = "RL_" + grtLst[i] + "_SEQ";
//				grantNm = "SELECT";
//				addGrant(grantNm, seqNm, tmpRoleNm);
//			}
//			 
//
//			//시퀀스 용도가 일중 인 경우에만 EXECUTE GRANT구문 출력
//			if(seq.getUsTypCd().equals("E")){
//				println();
//				//2. EXECUTE
//				for(int i=0 ; i<grtLst.length ; i++){
//					tmpRoleNm = "RL_" + grtLst[i] + "_EXE";
//					grantNm = "EXECUTE";
//					addGrant(grantNm, exeProcNm, tmpRoleNm);
//				}				
//			}	
//			
//		}
//    	println();
//    	
    }

	/**
     * Writes the grant of the table as alter table statements.
     *
     * @param table             The table
     */
    public void createGrant(Table table) throws IOException
    {
        if (table.getDbPrivilege() != null && !StringUtils.isBlank(table.getDbPrivilege().getTgtRolePnm()))
        {
        	String tblNm = table.getName();
        	String roleNm = table.getDbPrivilege().getTgtRolePnm();
        	String schema = table.getDbPrivilege().getSrcDbSchPnm();
        	if (!StringUtils.isBlank(schema)) {
        		tblNm = schema +"." + table.getName();
        	}
        	
        	if(UtilString.null2Blank(table.getDbPrivilege().getSelectYn()).equals("Y")){
        		addGrant("SELECT", tblNm, roleNm);
        	}
        	if(UtilString.null2Blank(table.getDbPrivilege().getInsertYn()).equals("Y")){
        		addGrant("INSERT", tblNm, roleNm);
        	}
        	if(UtilString.null2Blank(table.getDbPrivilege().getUpdateYn()).equals("Y")){
        		addGrant("UPDATE", tblNm, roleNm);
        	}
        	if(UtilString.null2Blank(table.getDbPrivilege().getInsertYn()).equals("Y")){
        		addGrant("DELETE", tblNm, roleNm);
        	}
        	println();
        	
        }
    }

    public void createGrants(Table table) throws IOException
    {	
    	if(table.getDbroles().size() > 0){
        	println("/** Grant **/ ");
    	}
    	
    	for (WaaDbPrivilege dbrole : table.getDbroles()) {
			
    		if ( !StringUtils.isBlank(dbrole.getTgtRolePnm()))
    		{
    			String tblNm = table.getName();
    			String roleNm = dbrole.getTgtRolePnm();
    			String schema = dbrole.getSrcDbSchPnm();
    			if (!StringUtils.isBlank(schema)) {
    				tblNm = schema +"." + table.getName();
    			}
    			
    			String rolnm = "";
    			if(UtilString.null2Blank(dbrole.getSelectYn()).equals("Y")){
    				rolnm += " SELECT,";
//    				addGrant("SELECT", tblNm, roleNm);
    			}
    			if(UtilString.null2Blank(dbrole.getInsertYn()).equals("Y")){
    				rolnm += " INSERT,";
//    				addGrant("INSERT", tblNm, roleNm);
    			}
    			if(UtilString.null2Blank(dbrole.getUpdateYn()).equals("Y")){
    				rolnm += " UPDATE,";
//    				addGrant("UPDATE", tblNm, roleNm);
    			}
    			if(UtilString.null2Blank(dbrole.getInsertYn()).equals("Y")){
    				rolnm += " DELETE,";
//    				addGrant("DELETE", tblNm, roleNm);
    			}
    			
    			if (!StringUtils.isBlank(rolnm)) {
    				rolnm = rolnm.substring(1, rolnm.length()-1);
    				addGrant(rolnm, tblNm, roleNm);
    			}
    			
    		}
    		
		}
    	println();
    	
    }

    public void createSynonyms(Table table) throws IOException
    {
    	for (WaaDbPrivilege dbrole : table.getDbroles()) {
    		
    		if ( !StringUtils.isBlank(dbrole.getTgtRolePnm()))
    		{
    			String tblNm = table.getName();
    			String roleNm = dbrole.getTgtRolePnm();
    			String schema = dbrole.getSrcDbSchPnm();
    			/*if (!StringUtils.isBlank(schema)) {
    				tblNm = schema +"." + table.getName();
    			}*/
    			
    			print("CREATE OR REPLACE SYNONYM ");
    			print(roleNm +"."+tblNm);
    			print(" FOR ");
    			print(schema +"."+tblNm);
    	        println(getPlatformInfo().getSqlCommandDelimiter());
    			
    		}
    		
    	}
    	println();
    	
    }
    
    /**
     * Prints the SQL for adding a grant to a table.
     *
     * @param String   grantNm
     * @param String    tblNm
     * @param String    rolePnm
     */
    public void addGrant(String grantNm, String tblNm, String rolePnm) throws IOException
    {
		print("GRANT ");
		print(grantNm);
		print(" ON ");
		print(tblNm);
        print(" TO ");
        print(rolePnm);
        println(getPlatformInfo().getSqlCommandDelimiter());
    }

    /**
     * Prints the SQL for adding a column to a table.
     *
     * @param model     The database model
     * @param table     The table
     * @param newColumn The new column
     */
    public void addColumn(Table table, Column newColumn) throws IOException
    {
        print("ALTER TABLE ");
        print(getTableName(table));
        print(" ADD ");
        print("(");
        writeColumn(table, newColumn);
        print(")");
        
//        print("ALTER TABLE ");
//        printlnIdentifier(getTableName(table));
//        printIndent();
//        print("ADD COLUMN ");
//        println("(");
//        printIndent();
//        printIndent();
//        writeColumn(table, newColumn);
//        println();
//        printIndent();
//        print(")");

        printEndOfStatement();
    }

    public void modifyColumn(Table table, Column newColumn) throws IOException
    {
    	print("ALTER TABLE ");
    	print(getTableName(table));
    	print(" MODIFY ");
    	print("(");
    	writeColumn(table, newColumn);
    	print(")");
//    	print("ALTER TABLE ");
//    	printlnIdentifier(getTableName(table));
//    	printIndent();
//    	print("MODIFY COLUMN ");
//    	println("(");
//    	printIndent();
//    	printIndent();
//    	writeColumn(table, newColumn);
//    	println();
//    	printIndent();
//    	print(")");
    	printEndOfStatement();
    }

    public void dropColumn(Table table, Column newColumn) throws IOException {
    	print("ALTER TABLE ");
    	print(getTableName(table));
    	print(" DROP COLUMN ");
    	//printIdentifier(getColumnName(newColumn));
    	print(getColumnName(newColumn));
    	
//    	print("ALTER TABLE ");
//    	printlnIdentifier(getTableName(table));
//    	printIndent();
//    	print("DROP COLUMN ");
//    	printIdentifier(getColumnName(newColumn));
    	printEndOfStatement();
    }




    /**
     * Outputs the DDL to drop the table. Note that this method does not drop
     * foreign keys to this table. Use {@link #dropTable(Database, Table)}
     * if you want that.
     *
     * @param table The table to drop
     */
    public void dropTable(Table table) throws IOException
    {
        print("DROP TABLE ");
        printIdentifier(getTableName(table));
        printEndOfStatement();
    }





    /**
     * Generates the string representation of the given value.
     *
     * @param column The column
     * @param value  The value
     * @return The string representation
     */
    protected String getValueAsString(Column column, Object value)
    {
        if (value == null)
        {
            return "NULL";
        }

        StringBuffer result = new StringBuffer();

        // TODO: Handle binary types (BINARY, VARBINARY, LONGVARBINARY, BLOB)
        switch (column.getTypeCode())
        {
            case Types.DATE:
                result.append(getPlatformInfo().getValueQuoteToken());
                if (!(value instanceof String) && (getValueDateFormat() != null))
                {
                    // TODO: Can the format method handle java.sql.Date properly ?
                    result.append(getValueDateFormat().format(value));
                }
                else
                {
                    result.append(value.toString());
                }
                result.append(getPlatformInfo().getValueQuoteToken());
                break;
            case Types.TIME:
                result.append(getPlatformInfo().getValueQuoteToken());
                if (!(value instanceof String) && (getValueTimeFormat() != null))
                {
                    // TODO: Can the format method handle java.sql.Date properly ?
                    result.append(getValueTimeFormat().format(value));
                }
                else
                {
                    result.append(value.toString());
                }
                result.append(getPlatformInfo().getValueQuoteToken());
                break;
            case Types.TIMESTAMP:
                result.append(getPlatformInfo().getValueQuoteToken());
                // TODO: SimpleDateFormat does not support nano seconds so we would
                //       need a custom date formatter for timestamps
                result.append(value.toString());
                result.append(getPlatformInfo().getValueQuoteToken());
                break;
            case Types.REAL:
            case Types.NUMERIC:
            case Types.FLOAT:
            case Types.DOUBLE:
            case Types.DECIMAL:
                result.append(getPlatformInfo().getValueQuoteToken());
                if (!(value instanceof String) && (getValueNumberFormat() != null))
                {
                    result.append(getValueNumberFormat().format(value));
                }
                else
                {
                    result.append(value.toString());
                }
                result.append(getPlatformInfo().getValueQuoteToken());
                break;
            default:
                result.append(getPlatformInfo().getValueQuoteToken());
                result.append(escapeStringValue(value.toString()));
                result.append(getPlatformInfo().getValueQuoteToken());
                break;
        }
        return result.toString();
    }

    /**
     * Generates the SQL for querying the id that was created in the last insertion
     * operation. This is obviously only useful for pk fields that are auto-incrementing.
     * A database that does not support this, will return <code>null</code>.
     *
     * @param table The table
     * @return The sql, or <code>null</code> if the database does not support this
     */
    public String getSelectLastIdentityValues(Table table)
    {
        // No default possible as the databases are quite different in this respect
        return null;
    }

    //
    // implementation methods that may be overridden by specific database builders
    //

    /**
     * Generates a version of the name that has at most the specified
     * length.
     *
     * @param name          The original name
     * @param desiredLength The desired maximum length
     * @return The shortened version
     */
    public String shortenName(String name, int desiredLength)
    {
        if (name == null)
        {
            return null;
        }

        // TODO: Find an algorithm that generates unique names
        int originalLength = name.length();

        if ((desiredLength <= 0) || (originalLength <= desiredLength))
        {
            return name;
        }

        int delta    = originalLength - desiredLength;
        int startCut = desiredLength / 2;

        StringBuffer result = new StringBuffer();

        result.append(name.substring(0, startCut));
        if (((startCut == 0) || (name.charAt(startCut - 1) != '_')) &&
            ((startCut + delta + 1 == originalLength) || (name.charAt(startCut + delta + 1) != '_')))
        {
            // just to make sure that there isn't already a '_' right before or right
            // after the cutting place (which would look odd with an aditional one)
            result.append("_");
        }
        result.append(name.substring(startCut + delta + 1, originalLength));
        return result.toString();
    }

    /**
     * Returns the table name. This method takes care of length limitations imposed by some databases.
     *
     * @param table The table
     * @return The table name
     */
    public String getTableName(Table table)
    {
//        return shortenName(table.getName(), getMaxTableNameLength());
    	String schema = table.getSchema();

    	if (!StringUtils.isBlank(schema)) {
    		return schema +"." + table.getName();
    	}

    	return table.getName();
    }
    /**
     * Returns the table name. This method takes care of length limitations imposed by some databases.
     *
     * @param table The table
     * @return The intex name
     */
    public String getIndexName(Table table)
    {
    	String schema = table.getSchema();
    	
    	if (!StringUtils.isBlank(schema)) {
    		return schema +"." + table.getIdxname();
    	}
    	
    	return table.getName();
    }
    
    public String getSequenceName(Sequence seq)
    {
    	String schema = seq.getSchema();
    	
    	if (!StringUtils.isBlank(schema)) {
    		return schema +"." + seq.getName();
    	}
    	
    	return seq.getName();
    }

    /**
     * Outputs a comment for the table.
     *
     * @param table The table
     */
    public void writeTableComment(Table table) throws IOException
    {
        printComment("==========================================");
        printComment("DBMS TYPE   : " + table.getDbmsTypeNm());
        printComment("DBMS NAME   : " + table.getCatalog());
        printComment("SCHEMA NAME : " + table.getSchema());
        printComment("TABLE NAME  : " + getTableName(table));
        printComment("PARTITION TABLE  : " + table.getPdmTbl().getPartTblYn());
//        printComment("CREATE DATE : " + getTableName(table));
        printComment("REQUEST USER NAME   : " + table.getRqstUserNm());
        printComment("==========================================");
        println();
    }
    
    /**
     * Outputs a comment for the index.
     *
     * @param table The index
     */
    public void writeIndexComment(Table table) throws IOException
    {
    	printComment("==========================================");
    	printComment("DBMS TYPE   : " + table.getDbmsTypeNm());
    	printComment("DBMS NAME   : " + table.getCatalog());
    	printComment("SCHEMA NAME : " + table.getSchema());
    	printComment("TABLE NAME  : " + getTableName(table));
    	printComment("INDEX NAME  : " + getIndexName(table));
    	printComment("INDEX SPACE  : " + table.getIdxSpacPnm());
    	printComment("INDEX TYPE  : " + UtilString.null2Blank( table.getIdxtype() ) );
    	printComment("REQUEST USER NAME   : " + table.getRqstUserNm());
    	printComment("==========================================");
    	println();
    }
    
    /**
     * Outputs a comment for the sequence.
     *
     * @param The sequence
     */
    public void writeSequenceComment(Sequence seq) throws IOException
    {
    	
    	printComment("=================================================");
    	printComment("DB명		: " + seq.getCatalog());
    	printComment("SCHEMA	: " + seq.getSchema());
    	printComment("시퀀스명     	: " + getSequenceName(seq));
//    	printComment("명명규칙     	: " + seq.getNmRlTypNm() );
//    	printComment("사용용도     	: " + seq.getUsTypNm());
        printComment("요청일자     	: " + seq.getRqstDtm2());
        printComment("승인완료일   	: " + UtilString.null2Blank(seq.getAprvDtm2()));
    	printComment("요청자       	: " + seq.getRqstUserNm());
    	printComment("=================================================");
    	println();
    }

    /**
     * Generates the first part of the ALTER TABLE statement including the
     * table name.
     *
     * @param table The table being altered
     */
    protected void writeTableAlterStmt(Table table) throws IOException
    {
        print("ALTER TABLE ");
        printlnIdentifier(getTableName(table));
        printIndent();
    }

    /**
     * Writes the table creation statement without the statement end.
     *
     * @param database   The model
     * @param table      The table
     * @param parameters Additional platform-specific parameters for the table creation
     */
    protected void writeTableCreationStmt(Database database, Table table, Map parameters) throws IOException
    {
        print("CREATE TABLE ");
        printlnIdentifier(getTableName(table));
        println("(");

        writeColumns(table);

        if (getPlatformInfo().isPrimaryKeyEmbedded())
        {
            writeEmbeddedPrimaryKeysStmt(table);
        }
        if (getPlatformInfo().isForeignKeysEmbedded())
        {
//            writeEmbeddCedForeignKeysStmt(database, table);
        }
        if (getPlatformInfo().isIndicesEmbedded())
        {
            writeEmbeddedIndicesStmt(table);
        }
        println();
        println(")");
    }

    /**
     * Writes the end of the table creation statement. Per default,
     * only the end of the statement is written, but this can be changed
     * in subclasses.
     *
     * @param table      The table
     * @param parameters Additional platform-specific parameters for the table creation
     */
    protected void writeTableCreationStmtEnding(Table table, Map parameters) throws IOException
    {
        printEndOfStatement();
    }

    /**
     * Writes the columns of the given table.
     *
     * @param table The table
     */
    protected void writeColumns(Table table) throws IOException
    {
    	boolean isFirst = true;
        for (int idx = 0; idx < table.getColumnCount(); idx++)
        {
        	if(UtilString.null2Blank(table.getColumn(idx).getRegTypCd()).equals("D"))
        		continue;
            if(!isFirst) {
            	println(",");
            }
        	printIndent();
            writeColumn(table, table.getColumn(idx));
            if(isFirst){
            	isFirst = false;
            }
        }
    }

    /**
     * Returns the column name. This method takes care of length limitations imposed by some databases.
     *
     * @param column The column
     * @return The column name
     */
    protected String getColumnName(Column column) throws IOException
    {
        return shortenName(column.getName(), getMaxColumnNameLength());
    }

    /**
     * Outputs the DDL for the specified column.
     *
     * @param table  The table containing the column
     * @param column The column
     */
    protected void writeColumn(Table table, Column column) throws IOException
    {
        
        String tblChgTypCd = UtilString.null2Blank(table.getDdltbl().getTblChgTypCd()) ;       
        String nonulUpdYn  = UtilString.null2Blank(column.getNonulUpdYn());
        String regTypCd    = UtilString.null2Blank(column.getRegTypCd());
        
       
        //see comments in columnsDiffer about null/"" defaults
        printIdentifier(getColumnName(column));
        
        print(spaceFull(40 - getColumnName(column).length()));
        
        if("CBR".equals(table.getType())) {
        	print(getSqlTypeCub(column));
        } else {
        	print(getSqlType(column));
        }
        
        
        writeColumnDefaultValueStmt(table, column);
               
        
        if(tblChgTypCd.equals("ALT")) {
        	
        	 if(regTypCd.equals("C")) {
        		 
        		 if (column.isRequired()){
                     print(" ");
                     print("NOT NULL");
                 }    
        	 }else{
        		
        		 if(nonulUpdYn.equals("Y")) {
                  	
                	 if (column.isRequired()) {
                         print(" ");
                         print("NOT NULL");
                         
                     }else{
                         print(" ");
                         print("NULL");  
                     }
                }
        	 }        	        	 
        	
        }else{
        	
        	 if (column.isRequired())
             {
                 print(" ");
                 print("NOT NULL");                  
             }
        }
       
               
        
        if (column.isAutoIncrement() && !getPlatformInfo().isDefaultValueUsedForIdentitySpec())
        {
            if (!getPlatformInfo().isNonPrimaryKeyIdentityColumnsSupported() && !column.isPrimaryKey())
            {
                throw new WiseBizException("Column "+column.getName()+" in table "+table.getName()+" is auto-incrementing but not a primary key column, which is not supported by the platform");
            }
            print(" ");
            writeColumnAutoIncrementStmt(table, column);
        }
    }

    /**
     * Returns the full SQL type specification (including size and precision/scale) for the
     * given column.
     *
     * @param column The column
     * @return The full SQL type string including the size
     */
    protected String getSqlType(Column column)
    {
        return getSqlType(column, getNativeType(column));
    }
    
    protected String getSqlTypeCub(Column column)
    {
    	if("VARCHAR2".equals(getNativeType(column))) {
    		return getSqlType(column, "VARCHAR");
    	} else {
    		return getSqlType(column, getNativeType(column));
    	}
        
    }

    /**
     * Returns the full SQL type specification (including size and precision/scale) for the
     * given column.
     *
     * @param column     The column
     * @param nativeType Overrides the native type of the column; can include the size placeholder
     * @return The full SQL type string including the size
     */
    protected String getSqlType(Column column, String nativeType)
    {
//        int          sizePos = nativeType.indexOf(SIZE_PLACEHOLDER);
        StringBuffer sqlType = new StringBuffer();

//        sqlType.append(sizePos >= 0 ? nativeType.substring(0, sizePos) : nativeType);
        sqlType.append(nativeType);

        String sizeSpec = getSizeSpec(column);

        if (!StringUtils.isBlank(sizeSpec))
        {
            sqlType.append("(");
            sqlType.append(sizeSpec);
            sqlType.append(")");
        }
//        sqlType.append(sizePos >= 0 ? nativeType.substring(sizePos + SIZE_PLACEHOLDER.length()) : "");

        return sqlType.toString();
    }

    /**
     * Returns the database-native type for the given column.
     *
     * @param column The column
     * @return The native type
     */
    protected String getNativeType(Column column)
    {
        String nativeType = (String)getPlatformInfo().getNativeType(column.getTypeCode());

        return nativeType == null ? column.getType() : nativeType;
    }

    /**
     * Returns the bare database-native type for the given column without any size specifies.
     *
     * @param column The column
     * @return The native type
     */
    protected String getBareNativeType(Column column)
    {
        String nativeType = getNativeType(column);
        int    sizePos    = nativeType.indexOf(SIZE_PLACEHOLDER);

        return sizePos >= 0 ? nativeType.substring(0, sizePos) : nativeType;
    }

    /**
     * Returns the size specification for the given column. If the column is of a type that has size
     * or precision and scale, and no size is defined for the column itself, then the default size
     * or precision/scale for that type and platform is used instead.
     *
     * @param column The column
     * @return The size spec
     */
    protected String getSizeSpec(Column column)
    {
        StringBuffer result   = new StringBuffer();
//        Object       sizeSpec = column.getSize();
        Object   sizeSpec = column.getDataLen();
        Object   scale = column.getDataScal();

        if (sizeSpec == null)
        {
//            sizeSpec = getPlatformInfo().getDefaultSize(column.getTypeCode());
        }
        if (sizeSpec != null)
        {
//        	if (getPlatformInfo().hasSize(column.getTypeCode()))
            if (scale == null)
            {
                result.append(sizeSpec);
            }
//            else if (getPlatformInfo().hasPrecisionAndScale(column.getTypeCode()))
            else
            {
                result.append(sizeSpec);
                result.append(",");
                result.append(scale);
            }
        }
        return result.toString();
    }


    /**
     * Returns the native default value for the column.
     *
     * @param column The column
     * @return The native default value
     */
    protected String getNativeDefaultValue(Column column)
    {
        return column.getDefaultValue();
    }

    /**
     * Escapes the necessary characters in given string value.
     *
     * @param value The value
     * @return The corresponding string with the special characters properly escaped
     */
    protected String escapeStringValue(String value)
    {
        String result = value;

        for (Iterator it = _charSequencesToEscape.entrySet().iterator(); it.hasNext();)
        {
            Map.Entry entry = (Map.Entry)it.next();

            result = StringUtils.replace(result, (String)entry.getKey(), (String)entry.getValue());
        }
        return result;
    }

    /**
     * Determines whether the given default spec is a non-empty spec that shall be used in a DEFAULT
     * expression. E.g. if the spec is an empty string and the type is a numeric type, then it is
     * no valid default value whereas if it is a string type, then it is valid.
     *
     * @param defaultSpec The default value spec
     * @param typeCode    The JDBC type code
     * @return <code>true</code> if the default value spec is valid
     */
    protected boolean isValidDefaultValue(String defaultSpec, int typeCode)
    {
        return (defaultSpec != null) &&
        	      (defaultSpec.length() > 0);
//               ((defaultSpec.length() > 0) ||
//                (!TypeMap.isNumericType(typeCode) && !TypeMap.isDateTimeType(typeCode)));
    }

    /**
     * Prints the default value stmt part for the column.
     *
     * @param table  The table
     * @param column The column
     */
    protected void writeColumnDefaultValueStmt(Table table, Column column) throws IOException
    {
        Object parsedDefault = column.getParsedDefaultValue();

        if (parsedDefault != null)
        {
//            if (!getPlatformInfo().isDefaultValuesForLongTypesSupported() &&
//                ((column.getTypeCode() == Types.LONGVARBINARY) || (column.getTypeCode() == Types.LONGVARCHAR)))
//            {
//                throw new WiseBizException("The platform does not support default values for LONGVARCHAR or LONGVARBINARY columns");
//            }
            // we write empty default value strings only if the type is not a numeric or date/time type
            if (isValidDefaultValue(column.getDefaultValue(), column.getTypeCode()))
            {
            	
                print(" DEFAULT ");
                writeColumnDefaultValue(table, column);
                print(" ");
            }
        }
        else if (getPlatformInfo().isDefaultValueUsedForIdentitySpec() && column.isAutoIncrement())
        {
            print(" DEFAULT ");
            writeColumnDefaultValue(table, column);
            print(" ");
        }
    }

    /**
     * Prints the default value of the column.
     *
     * @param table  The table
     * @param column The column
     */
    protected void writeColumnDefaultValue(Table table, Column column) throws IOException
    {
        printDefaultValue(getNativeDefaultValue(column), column.getTypeCode());
    }

    /**
     * Prints the default value of the column.
     *
     * @param defaultValue The default value
     * @param typeCode     The type code to write the default value for
     */
    protected void printDefaultValue(Object defaultValue, int typeCode) throws IOException
    {
        if (defaultValue != null)
        {
//            boolean shouldUseQuotes = !TypeMap.isNumericType(typeCode);
//
//            if (shouldUseQuotes)
//            {
//                // characters are only escaped when within a string literal
//                print(getPlatformInfo().getValueQuoteToken());
//                print(escapeStringValue(defaultValue.toString()));
//                print(getPlatformInfo().getValueQuoteToken());
//            }
//            else
//            {
                print(defaultValue.toString());
//            }
        }
    }

    /**
     * Prints that the column is an auto increment column.
     *
     * @param table  The table
     * @param column The column
     */
    protected void writeColumnAutoIncrementStmt(Table table, Column column) throws IOException
    {
        print("IDENTITY");
    }

    /**
     * Prints that a column is nullable.
     */
    protected void writeColumnNullableStmt() throws IOException
    {
        print("NULL");
    }

    /**
     * Prints that a column is not nullable.
     */
    protected void writeColumnNotNullableStmt() throws IOException
    {
        print("NOT NULL");
    }

    /**
     * Compares the current column in the database with the desired one.
     * Type, nullability, size, scale, default value, and precision radix are
     * the attributes checked.  Currently default values are compared, and
     * null and empty string are considered equal.
     *
     * @param currentColumn The current column as it is in the database
     * @param desiredColumn The desired column
     * @return <code>true</code> if the column specifications differ
     */
    protected boolean columnsDiffer(Column currentColumn, Column desiredColumn)
    {
        //The createColumn method leaves off the default clause if column.getDefaultValue()
        //is null.  mySQL interprets this as a default of "" or 0, and thus the columns
        //are always different according to this method.  alterDatabase will generate
        //an alter statement for the column, but it will be the exact same definition
        //as before.  In order to avoid this situation I am ignoring the comparison
        //if the desired default is null.  In order to "un-default" a column you'll
        //have to have a default="" or default="0" in the schema xml.
        //If this is bad for other databases, it is recommended that the createColumn
        //method use a "DEFAULT NULL" statement if that is what is needed.
        //A good way to get this would be to require a defaultValue="<NULL>" in the
        //schema xml if you really want null and not just unspecified.

        String  desiredDefault = desiredColumn.getDefaultValue();
        String  currentDefault = currentColumn.getDefaultValue();
        boolean defaultsEqual  = (desiredDefault == null) || desiredDefault.equals(currentDefault);
        boolean sizeMatters    = getPlatformInfo().hasSize(currentColumn.getTypeCode()) &&
                                 (desiredColumn.getSize() != null);

        // We're comparing the jdbc type that corresponds to the native type for the
        // desired type, in order to avoid repeated altering of a perfectly valid column
        if ((getPlatformInfo().getTargetJdbcType(desiredColumn.getTypeCode()) != currentColumn.getTypeCode()) ||
            (desiredColumn.isRequired() != currentColumn.isRequired()) ||
            (sizeMatters && !StringUtils.equals(desiredColumn.getSize(), currentColumn.getSize())) ||
            !defaultsEqual)
        {
            return true;
        }
        else
        {
            return false;
        }
    }


    /**
     * Returns the constraint name. This method takes care of length limitations imposed by some databases.
     *
     * @param prefix     The constraint prefix, can be <code>null</code>
     * @param table      The table that the constraint belongs to
     * @param secondPart The second name part, e.g. the name of the constraint column
     * @param suffix     The constraint suffix, e.g. a counter (can be <code>null</code>)
     * @return The constraint name
     */
    public String getConstraintName(String prefix, Table table, String secondPart, String suffix)
    {
        StringBuffer result = new StringBuffer();

        if (prefix != null)
        {
            result.append(prefix);
            result.append("_");
        }
        
        String sPrefix = table.getName().substring(0,2);
        
        String sConstNm = "";
        
        sConstNm = table.getName();
        
//        if(sPrefix.equals("TB")){
//        	
//        	sConstNm = table.getName().substring(3);   	        	
//        }else{
//        	sConstNm = table.getName();
//        }
        
        result.append(sConstNm);
        
        
        if(secondPart != null){
        	result.append("_");
        	result.append(secondPart);
        }
        
        if (suffix != null)
        {
            result.append("_");
            result.append(suffix);
        }
        return shortenName(result.toString(), getMaxConstraintNameLength());
    }

    /**
     * Writes the primary key constraints of the table inside its definition.
     *
     * @param table The table
     */
    protected void writeEmbeddedPrimaryKeysStmt(Table table) throws IOException
    {
        Column[] primaryKeyColumns = table.getPrimaryKeyColumns();

        if ((primaryKeyColumns.length > 0) && shouldGeneratePrimaryKeys(primaryKeyColumns))
        {
            printStartOfEmbeddedStatement();
            writePrimaryKeyStmt(table, primaryKeyColumns);
        }
    }
    
    /**
     * Writes the primary key constraints of the table inside its definition.
     *
     * @param table The table
     */
//    protected void writeEmbeddedForeignKeysStmt(Table table) throws IOException
//    {
//    	Column[] primaryKeyColumns = table.getPrimaryKeyColumns();
//    	
//    	if ((primaryKeyColumns.length > 0) && shouldGeneratePrimaryKeys(primaryKeyColumns))
//    	{
//    		printStartOfEmbeddedStatement();
//    		writePrimaryKeyStmt(table, primaryKeyColumns);
//    	}
//    }

    /**
     * Determines whether we should generate a primary key constraint for the given
     * primary key columns.
     *
     * @param primaryKeyColumns The pk columns
     * @return <code>true</code> if a pk statement should be generated for the columns
     */
    protected boolean shouldGeneratePrimaryKeys(Column[] primaryKeyColumns)
    {
        return true;
    }

    /**
     * Writes a primary key statement for the given columns.
     *
     * @param table             The table
     * @param primaryKeyColumns The primary columns
     */
    protected void writePrimaryKeyStmt(Table table, Column[] primaryKeyColumns) throws IOException
    {
    	boolean isFirst = true;
        print("PRIMARY KEY (");
        for (int idx = 0; idx < primaryKeyColumns.length; idx++)
        {
        	if(UtilString.null2Blank(primaryKeyColumns[idx].getRegTypCd()).equals("D")) {
        		continue;
        	}
        	if(!isFirst) {
        		print(", ");
        	}
            printIdentifier(getColumnName(primaryKeyColumns[idx]));
            if(isFirst){
            	isFirst = false;
            }
        }
        printlnIdentifier(");");
    }
    
    protected void writePrimaryUniqueIdxStmt(Table table, Column[] primaryKeyColumns) throws IOException
    {
    	boolean isFirst = true;
        print("(");
        for (int idx = 0; idx < primaryKeyColumns.length; idx++)
        {
        	if(UtilString.null2Blank(primaryKeyColumns[idx].getRegTypCd()).equals("D")) {
        		continue;
        	}
        	if(!isFirst) {
        		print(", ");
        	}
            printIdentifier(getColumnName(primaryKeyColumns[idx]));
            if(isFirst){
            	isFirst = false;
            }
        	
        }
        printIdentifier(")");
    }

    /**
     * Returns the index name. This method takes care of length limitations imposed by some databases.
     *
     * @param index The index
     * @return The index name
     */
    public String getIndexName(Index index)
    {
        return shortenName(index.getName(), getMaxConstraintNameLength());
    }

    /**
     * Writes the indexes embedded within the create table statement.
     *
     * @param table The table
     */
    protected void writeEmbeddedIndicesStmt(Table table) throws IOException
    {
        if (getPlatformInfo().isIndicesSupported())
        {
            for (int idx = 0; idx < table.getIndexCount(); idx++)
            {
                printStartOfEmbeddedStatement();
                writeEmbeddedIndexCreateStmt(table, table.getIndex(idx));
            }
        }
    }

    /**
     * Writes the given embedded index of the table.
     *
     * @param table The table
     * @param index The index
     */
    protected void writeEmbeddedIndexCreateStmt(Table table, Index index) throws IOException
    {
        if ((index.getName() != null) && (index.getName().length() > 0))
        {
            print(" CONSTRAINT ");
            printIdentifier(getIndexName(index));
        }
        if (index.isUnique())
        {
            print(" UNIQUE");
        }
        else
        {
            print(" INDEX ");
        }
        print(" (");

        for (int idx = 0; idx < index.getColumnCount(); idx++)
        {
            IndexColumn idxColumn = index.getColumn(idx);
            Column      col       = table.findColumn(idxColumn.getName());

            if (col == null)
            {
                // would get null pointer on next line anyway, so throw exception
                throw new WiseBizException("DDL SCRIPT ERROR", "Invalid column '" + idxColumn.getName() + "' on index " + index.getName() + " for table " + table.getName());
            }
            if (idx > 0)
            {
                print(", ");
            }
            printIdentifier(getColumnName(col));
        }

        print(")");
    }

    /**
     * Generates the statement to drop a non-embedded index from the database.
     *
     * @param table The table the index is on
     * @param index The index to drop
     */
    public void dropIndex(Table table, Index index) throws IOException
    {
        if (getPlatformInfo().isAlterTableForDropUsed())
        {
            writeTableAlterStmt(table);
        }
        print("DROP INDEX ");
        printIdentifier(getIndexName(index));
        if (!getPlatformInfo().isAlterTableForDropUsed())
        {
            print(" ON ");
            printIdentifier(getTableName(table));
        }
        printEndOfStatement();
    }
    
    
    /**
     * Generates the statement to drop a non-embedded index from the database.
     *
     * @param table The table the index is on
     * @param index The index to drop
     */
    public void dropIndex(Table table) throws IOException
    {
    	
    	String idxType = UtilString.null2Blank(table.getIdxtype());
    	
    	if(idxType.equals("PK")){
    		print("ALTER TABLE ");
    		printlnIdentifier(getTableName(table));
    		print(" DROP CONSTRAINT ");
    		print(table.getIdxname());
    	}else{
    	    
    	    String schPnm = UtilString.null2Blank(table.getSchema());  
    	    
    	    String schTblPnm = schPnm + "." +  UtilString.null2Blank(table.getIdxname()); 
    	    
    		print("DROP INDEX ");
        	printIdentifier(schTblPnm);
    	}
    	
    	printEndOfStatement();
    }
    
    public void dropSequence(Sequence seq) throws IOException
    {    	
    	print("DROP SEQUENCE ");
    	printIdentifier(getSequenceName(seq));
    	printEndOfStatement();
    }

    public void dropSerial(Sequence seq) throws IOException
    {    	
    	print("DROP SERIAL");
    	printIdentifier(getSequenceName(seq));
    	printEndOfStatement();
    }



    //
    // Helper methods
    //

    /**
     * Prints an SQL comment to the current stream.
     *
     * @param text The comment text
     */
    protected void printComment(String text) throws IOException
    {
//        if (getPlatform().isSqlCommentsOn())
        if (true)
        {
            print(getPlatformInfo().getCommentPrefix());
            // Some databases insist on a space after the prefix
            print(" ");
            print(text);
            print(" ");
            print(getPlatformInfo().getCommentSuffix());
            println();
        }
    }

    /**
     * Prints the start of an embedded statement.
     */
    protected void printStartOfEmbeddedStatement() throws IOException
    {
        println(",");
        printIndent();
    }

    /**
     * Prints the end of statement text, which is typically a semi colon followed by
     * a carriage return.
     */
    protected void printEndOfStatement() throws IOException
    {
        // TODO: It might make sense to use a special writer which stores the individual
        //       statements separately (the end of a statement is identified by this method)
        println(getPlatformInfo().getSqlCommandDelimiter());
        println();
    }

    /**
     * Prints a newline.
     */
    protected void println() throws IOException
    {
        print(LINE_SEPARATOR);
    }

    /**
     * Prints some text.
     *
     * @param text The text to print
     */
    protected void print(String text) throws IOException
    {
        _writer.write(text);
    }

    /**
     * Returns the delimited version of the identifier (if configured).
     *
     * @param identifier The identifier
     * @return The delimited version of the identifier unless the platform is configured
     *         to use undelimited identifiers; in that case, the identifier is returned unchanged
     */
    protected String getDelimitedIdentifier(String identifier)
    {
        if (false)
        {
            return getPlatformInfo().getDelimiterToken() + identifier + getPlatformInfo().getDelimiterToken();
        }
        else
        {
            return identifier;
        }
    }

    /**
     * Prints the given identifier. For most databases, this will
     * be a delimited identifier.
     *
     * @param identifier The identifier
     */
    protected void printIdentifier(String identifier) throws IOException
    {
        print(getDelimitedIdentifier(identifier));
    }

    /**
     * Prints the given identifier followed by a newline. For most databases, this will
     * be a delimited identifier.
     *
     * @param identifier The identifier
     */
    protected void printlnIdentifier(String identifier) throws IOException
    {
        println(getDelimitedIdentifier(identifier));
    }

    /**
     * Prints some text followed by a newline.
     *
     * @param text The text to print
     */
    protected void println(String text) throws IOException
    {
        print(text);
        println();
    }

    /**
     * Prints the characters used to indent SQL.
     */
    protected void printIndent() throws IOException
    {
        print(getIndent());
    }

    /**
     * Creates a reasonably unique identifier only consisting of hexadecimal characters and underscores.
     * It looks like <code>d578271282b42fce__2955b56e_107df3fbc96__8000</code> and is 48 characters long.
     *
     * @return The identifier
     */
    protected String createUniqueIdentifier()
    {
        return new UID().toString().replace(':', '_').replace('-', '_');
    }
    
    private String spaceFull(int length) {
		String txtReturn = "";
		for(int i = 0; i < length; i++) {
			txtReturn += " ";
		}
		return txtReturn;
	}

    public void alterPartition(Database database, Table table) throws IOException {
		Partition partvo = table.getPartition();
		
		if (partvo == null) {
			//logger.debug("파티션정보 없음");
			println("변경된 파티션 내역이 존재하지 않습니다. ");
        	println("파티션 부가정보 변경이력을 확인하십시오. ");
			return;
		}
		
		//파티션 유형, 파티션 키정보 셋팅....
		
		//파티션 키컬럼을 찾아 파티션 정보에 추가셋팅한다...
		if(StringUtils.isNotBlank(partvo.getPartKey())) {
			setPartitionKey(table, partvo, "M");
		} else {
			//logger.debug("주파티션 키 정보가 없슴...");
			return;
		}
		//서브파 키컬럼 셋팅...
		if(StringUtils.isNotBlank(partvo.getSubPartKey())) {
			setPartitionKey(table, partvo, "S");
		}
		
		//ALTER 파티션 문장 시작...
		writeAlterPartitions(table, partvo);
		
		
	}
	public void writeAlterPartitions(Table table, Partition partvo) throws IOException {
		
		ArrayList<DdlPartMainVo> mainlist = partvo.getPartMainList();
		ArrayList<WaqDdlPartSub> sublist = partvo.getPartSubList();

		for(DdlPartMainVo mainvo : mainlist) {
//			printIndent();
			String regtypcd =  mainvo.getMainRegTypCd();
			if ("C".equals(regtypcd)) {
				//주파티션 신규생성...
				addPartition(table, partvo, mainvo);
			} else if ("U".equals(regtypcd)) {
				//주파티션 변경... 삭제 후 변경처리한다.
				dropPartition(table, partvo, mainvo);
				addPartition(table, partvo, mainvo);
			} else if ("D".equals(regtypcd)) {
				//주파티션 삭제...
				dropPartition(table, partvo, mainvo);
			}
		}
		
		//서브파티션 삭제
		dropSubPartList(table, partvo, sublist);
		println();		
		
		boolean subAlter = false;
		for(WaqDdlPartSub subbo : sublist){
			if("C".equals(subbo.getSubRegTypCd()) || "U".equals(subbo.getSubRegTypCd())){
				subAlter = true;
				break;
			}
		}
		
		//서브파티션이 있을경우 서브파티션 템플릿 형태로 DDL 발행...
		if (StringUtils.isNotEmpty(partvo.getSubPartTypCd()) 
				&& StringUtils.isNotEmpty(partvo.getSubPartKey())
				&& sublist != null && !sublist.isEmpty()
				&& subAlter
				) {
			print("ALTER TABLE ");
	        print(getTableName(table));
			println(" SET SUBPARTITION TEMPLATE ");
			println("(");
		
			addSubPartList(partvo, sublist);
			println();
			println(")");
			printEndOfStatement();
		}		
		
	}
	
	public void createPartition(Database database, Table table) throws Exception {
		createPartition(database, table, null);
		
	}
	public void createPartition(Database database, Table table, Map parameters ) throws IOException  {
		
		Partition partvo = table.getPartition();
		
		if (partvo == null) {
			//logger.debug("파티션정보 없음");
			return;
		}
		
//        writeTableCreationStmt(database, table, parameters);
        //파티션 유형, 파티션 키정보 셋팅....
		
		//파티션 키컬럼을 찾아 파티션 정보에 추가셋팅한다...
		if(StringUtils.isNotBlank(partvo.getPartKey())) {
			setPartitionKey(table, partvo, "M");
			if(partvo.getPartKeyList()==null){
				println("-- 파티션키컬럼 미존재("+partvo.getPartKey()+")");
				return;
			}
		} else {
			//logger.debug("주파티션 키 정보가 없슴...");
			return;
		}
		//서브파 키컬럼 셋팅...
		if(StringUtils.isNotBlank(partvo.getSubPartKey())) {
			setPartitionKey(table, partvo, "S");
			if(partvo.getSubPartKeyList()==null){
				println("-- 파티션키컬럼 미존재("+partvo.getSubPartKey()+")");
				return;
			}
		}
		
		//파티션 키 문장 처리
		writePartitionKeyStmt (partvo);
		println();
		println("(");
		
		//파티션 리스트 문장 처리....
		writePartListStmt(partvo);

		println();
		println(")");

	}
	
	public void dropPartition(Table table, Partition partvo, DdlPartMainVo mainvo) throws IOException {
		print("ALTER TABLE ");
        print(getTableName(table));
        print(" DROP ");
        print("PARTITION "+ mainvo.getDdlPartPnm());
        printEndOfStatement();
		
	}
	public void addPartition(Table table, Partition partvo, DdlPartMainVo mainvo) throws IOException {
		print("ALTER TABLE ");
        print(getTableName(table));
        print(" ADD ");
        writeMainPartition(partvo, mainvo);
        printEndOfStatement();
	}
	
	
	public void addSubPartList(Partition partvo, ArrayList<WaqDdlPartSub> partSubList) throws IOException {
		int cntpart = 0;
		for(WaqDdlPartSub subbo : partSubList) {
			
			if("C".equals(subbo.getSubRegTypCd()) || "U".equals(subbo.getSubRegTypCd())){
				String sSubPartPnm = UtilString.null2Blank(subbo.getDdlPartSubPnm());
				//서브파티션명이 없으면 pass
				if(!sSubPartPnm.equals("")) {
					cntpart++;
					if(cntpart == 1){
						printIndent();
						printIndent();								
						writeSubPartition(partvo, subbo); 				
					}else{					
						println(",");
						printIndent();
						printIndent();								
						writeSubPartition(partvo, subbo);					
					}				
				}	
			}
		}
	}
	
	public void dropSubPartList(Table table,Partition partvo, ArrayList<WaqDdlPartSub> partSubList) throws IOException {
		for(WaqDdlPartSub subbo : partSubList) {
			if("D".equals(subbo.getSubRegTypCd()) || "U".equals(subbo.getSubRegTypCd())){
				String sSubPartPnm = UtilString.null2Blank(subbo.getDdlPartSubPnm());
				//서브파티션명이 없으면 pass
				if(!sSubPartPnm.equals("")) {
					print("ALTER TABLE ");
			        print(getTableName(table));
			        print(" DROP SUBPARTITION "+ sSubPartPnm);
			        printEndOfStatement();
				}	
			}
		}
	}
	
	protected void setPartitionKey(Table table, Partition partvo, String keytype) {
		//파티션 키정보를 Colunm 리스트 형태로 Partition 에 셋팅한다....
		String strkeys[] =  partvo.getPartKey().split(",");
		
		//M 인경우 주파티션, S는 서브파티션 키 정보를 셋팅한다.
		if ("M".equals(keytype)) {
			strkeys =  partvo.getPartKey().split(",");
		} else {
			strkeys =  partvo.getSubPartKey().split(",");
		}
		
		ArrayList<Column> partkeylist = new ArrayList<Column>();
		
		for (int i = 0; i < strkeys.length; i++) {
			//파티션 키에 해당하는 컬럼정보를 찾아 리스트에 추가한다.
			Column partkey = new Column();
			partkey = table.findColumn(strkeys[i].trim());
			partkeylist.add(partkey);
		}
		
		if ("M".equals(keytype)) {
			partvo.setPartKeyList(partkeylist);
		} else {
			partvo.setSubPartKeyList(partkeylist);
		}
	}
	
	public void writePartitionKeyStmt(Partition partvo) throws IOException {
		
		print("PARTITION BY "+partvo.getPartTypCd() +" ("+partvo.getPartKey()+")");
		
		if (StringUtils.isNotBlank(partvo.getSubPartTypCd())) {
			print(" SUBPARTITION BY "+partvo.getSubPartTypCd()+" ("+partvo.getSubPartKey()+")");
		}
	}
	
	public void writeMainPartition(Partition partvo, DdlPartMainVo mainvo) throws IOException {
		String parttype = partvo.getPartTypCd();
		
//		printIndent();
		//주파티션 명 출력...
		print("PARTITION "+ mainvo.getDdlPartPnm());
		//파티션 유형에 따라 VALUES 값 출력...
		writePartitionVal(partvo, mainvo);
		
		//주파티션 리스트에 테이블스페이스가 있을 경우 추가...
		if (StringUtils.isNotBlank(mainvo.getTblSpacPnm())) {
			print(" TABLESPACE " + mainvo.getTblSpacPnm());
		}
		
		//서브파티션 리스트가 존재할 경우....
		/*if (StringUtils.isNotBlank(partvo.getSubPartTypCd()) && StringUtils.isNotBlank(partvo.getSubPartKey()) && mainvo.getPartSubList() != null && !mainvo.getPartSubList().isEmpty()) {
			println();
			printIndent();
			println("(");
			writeSubPartListStmt(partvo, mainvo.getPartSubList());
			println();
			printIndent();
			print(")");
		}*/
		
	}
	
	public void writePartListStmt(Partition partvo) throws IOException {
		
		ArrayList<DdlPartMainVo> mainlist = partvo.getPartMainList();

		int cntpart = 0;
		for(DdlPartMainVo mainvo : mainlist) {
			cntpart++;
			printIndent();
			writeMainPartition(partvo, mainvo);
			if (cntpart < mainlist.size()) {
				println(",");
			}
		}
	        
		
	}
	
	
	/**
     * Prints the default value of the column.
     *
     * @param defaultValue The default value
     * @param typeCode     The type code to write the default value for
     */
    protected void printDefaultValue(Object defaultValue, int typeCode, String type) throws IOException
    {
        if (defaultValue != null)
        {
        	
            boolean shouldUseQuotes = false; //==!TypeMap.isNumericType(typeCode);

            if(type.equals("VARCHAR2")||type.equals("CHAR")||type.equals("VARCHAR")){
                shouldUseQuotes = true;
            }
            //1129 MAXVALUE는 파티션테이블에서 쿼테이션 붙지 않음
            if(defaultValue.equals("MAXVALUE")){
            	shouldUseQuotes = false;
            }
            
            if (shouldUseQuotes)
            {
                // characters are only escaped when within a string literal
                print(getPlatformInfo().getValueQuoteToken());
                print(escapeStringValue(defaultValue.toString()));
                print(getPlatformInfo().getValueQuoteToken());
            }
            else
            {
                print(defaultValue.toString());
            }
        }
    }
    
    public void writeSubPartition(Partition partvo, WaqDdlPartSub subvo) throws IOException {
		String parttype = partvo.getSubPartTypCd();
		//서브파티션 명 출력...
		print("SUBPARTITION "+ subvo.getDdlPartSubPnm());
		//파티션 유형에 따라 VALUES 값 출력...
		writePartitionVal(partvo, subvo);
		
		//서브파티션 리스트에 테이블스페이스가 있을 경우 추가...
		if (StringUtils.isNotBlank(subvo.getTblSpacPnm())) {
			print(" TABLESPACE " + subvo.getTblSpacPnm());
		}
		
	}
    
    
    public void writePartitionVal(Partition partvo, DdlPartMainVo mainvo) throws IOException {
		//주파티션 val 값 셋팅...
		List<Column> partKeyList = partvo.getPartKeyList();
		
		String parttype = partvo.getPartTypCd();
		
		int cntkey = partKeyList.size();
		
		if ("RANGE".equals(parttype) && StringUtils.isNotEmpty(mainvo.getDdlPartVal())) {
			print(" VALUES LESS THAN (");

			String partvals[] = mainvo.getDdlPartVal().split(",");
			//RANGE 파티션의 경우 키컬럼과 키밸류가 1:1 처리
			for (int i = 0; i < partvals.length; i++) {
				if (i >= cntkey) break;
				Column parcol = partKeyList.get(i);
				if (isValidDefaultValue(partvals[i].trim(), parcol.getTypeCode())){
					printDefaultValue(partvals[i].trim(), parcol.getTypeCode(), parcol.getType());
					if (i < partvals.length - 1) {
						print(", ");
					}
				}
				
			}
			print(") ");
			
		} else if ("LIST".equals(parttype) && StringUtils.isNotEmpty(mainvo.getDdlPartVal())) {
			print(" VALUES (");
			
			String partvals[] = mainvo.getDdlPartVal().split(",");
			//List 파티션일 경우 파티션 첫번째 키컬럼에 해당하는 파티션 밸류값 전체 추가함...
			Column parcol = partKeyList.get(0);
			for (int i = 0; i < partvals.length; i++) {
				if (isValidDefaultValue(partvals[i].trim(), parcol.getTypeCode())){
					printDefaultValue(partvals[i].trim(), parcol.getTypeCode(), parcol.getType());
					if (i < partvals.length - 1) {
						print(", ");
					}
				}
				
			}
			print(") ");
			
		} else if ("HASH".equals(parttype)) {
			//HASH 파티션의 경우 파티션 val값을 추가하지 않는다....
			print(" ");
		}
		
	}
    
    public void writePartitionVal(Partition partvo, WaqDdlPartSub subvo) throws IOException {
		//서브파티션 VALUE 값 출력....
		List<Column> partKeyList = partvo.getSubPartKeyList();
		
		String parttype = partvo.getSubPartTypCd();
		
		int cntkey = partKeyList.size();
		
		if ("RANGE".equals(parttype) && StringUtils.isNotEmpty(subvo.getDdlPartSubVal())) {
			print(" VALUES LESS THAN (");

			String partvals[] = subvo.getDdlPartSubVal().split(",");
			//RANGE 파티션의 경우 키컬럼과 키밸류가 1:1 처리
			for (int i = 0; i < partvals.length; i++) {
				if (i >= cntkey) break;
				Column parcol = partKeyList.get(i);
				if (isValidDefaultValue(partvals[i].trim(), parcol.getTypeCode())){
					printDefaultValue(partvals[i].trim(), parcol.getTypeCode(), parcol.getType());
					if (i < partvals.length - 1) {
						print(", ");
					}
				}
				
			}
			print(") ");
			
		} else if ("LIST".equals(parttype) && StringUtils.isNotEmpty(subvo.getDdlPartSubVal())) {
			print(" VALUES (");

			String partvals[] = subvo.getDdlPartSubVal().split(",");
			//List 파티션일 경우 파티션 첫번째 키컬럼에 해당하는 파티션 밸류값 전체 추가함...
			Column parcol = partKeyList.get(0);
			for (int i = 0; i < partvals.length; i++) {
				if (isValidDefaultValue(partvals[i].trim(), parcol.getTypeCode())){
					printDefaultValue(partvals[i].trim(), parcol.getTypeCode(), parcol.getType());
					if (i < partvals.length - 1) {
						print(", ");
					}
				}
				
			}
			print(") ");
			
		} else if ("HASH".equals(parttype)) {
			//HASH 파티션의 경우 파티션 val값을 추가하지 않는다....
			print(" ");
		}
		
	}

	public void dropSynonym(Table table) throws IOException{
        print("DROP PUBLIC SYNONYM ");
        printIdentifier(table.getName());
        printEndOfStatement();
		
	}
	
	public void writeGrantComment(Grant grt) throws IOException {
    	printComment("=================================================");
    	printComment("DBMS NAME    : " + grt.getCatalog());
    	printComment("SCHEMA NAME  : " + grt.getSchema());
    	printComment("OBJECT NAME  : " + grt.getName());
    	printComment("ROLE NAME    : " + grt.getGtdSchema());
        printComment("REQUEST DATE : " + grt.getRqstDtm2());
//      printComment("승인완료일   	: " + UtilString.null2Blank(grt.getAprvDtm2()));
    	printComment("REQUEST USER NAME : " + grt.getRqstUserNm());
    	printComment("=================================================");
    	println();
	}
	
	public void createObjectGrant(Grant grt) throws IOException {
		if (!getPlatformInfo().isIndicesSupported())
    	{
    		throw new WiseBizException("This platform does not support grant");
    	}
    	else if (grt.getName() == null)
    	{
    		_log.warn("Cannot write unnamed grant " + grt.getName() );
    	}
    	else
    	{
    		if ( !StringUtils.isBlank(grt.getGtdSchema()))
    		{
    			String tblNm = grt.getName();
    			String roleNm = grt.getGtdSchema();
    			String schema = grt.getSchema();
    			if (!StringUtils.isBlank(schema)) {
    				tblNm = schema +"." + grt.getName();
    			}
    			
    			String rolnm = "";
    			if(UtilString.null2Blank(grt.getSelectYn()).equals("Y")){
    				rolnm += " SELECT,";
    			}
    			if(UtilString.null2Blank(grt.getInsertYn()).equals("Y")){
    				rolnm += " INSERT,";
    			}
    			if(UtilString.null2Blank(grt.getUpdateYn()).equals("Y")){
    				rolnm += " UPDATE,";
    			}
    			if(UtilString.null2Blank(grt.getDeleteYn()).equals("Y")){
    				rolnm += " DELETE,";
    			}
    			if(UtilString.null2Blank(grt.getExecuteYn()).equals("Y")){
    				rolnm += " EXECUTE,";
    			}
    			
    			if (!StringUtils.isBlank(rolnm)) {
    				rolnm = rolnm.substring(1, rolnm.length()-1);
    				addGrant(rolnm, tblNm, roleNm);
    			}
    			
//    			println();
    		}
    	}
	}
	
	public void revokeObjectGrant(Grant grt) throws IOException {
		if (!getPlatformInfo().isIndicesSupported())
    	{
    		throw new WiseBizException("This platform does not support grant");
    	}
    	else if (grt.getName() == null)
    	{
    		_log.warn("Cannot write unnamed grant " + grt.getName() );
    	}
    	else
    	{
    		
    		if ( !StringUtils.isBlank(grt.getGtdSchema()))
    		{
    			String tblNm = grt.getName();
    			String roleNm = grt.getGtdSchema();
    			String schema = grt.getSchema();
    			if (!StringUtils.isBlank(schema)) {
    				tblNm = schema +"." + grt.getName();
    			}
    			
    			String rolnm = "";
    			if(UtilString.null2Blank(grt.getRvkSelectYn()).equals("Y")){
    				rolnm += " SELECT,";
    			}
    			if(UtilString.null2Blank(grt.getRvkInsertYn()).equals("Y")){
    				rolnm += " INSERT,";
    			}
    			if(UtilString.null2Blank(grt.getRvkUpdateYn()).equals("Y")){
    				rolnm += " UPDATE,";
    			}
    			if(UtilString.null2Blank(grt.getRvkDeleteYn()).equals("Y")){
    				rolnm += " DELETE,";
    			}
    			if(UtilString.null2Blank(grt.getRvkExecuteYn()).equals("Y")){
    				rolnm += " EXECUTE,";
    			}
    			
    			if (!StringUtils.isBlank(rolnm)) {
    				rolnm = rolnm.substring(1, rolnm.length()-1);
    				revokeGrant(rolnm, tblNm, roleNm);
    			}
    			
    			println();
    		}
    	}
		
	}
	
	public void revokeGrant(String grantNm, String tblNm, String rolePnm) throws IOException
    {
		print("REVOKE ");
		print(grantNm);
		print(" ON ");
		print(tblNm);
        print(" FROM ");
        print(rolePnm);
        println(getPlatformInfo().getSqlCommandDelimiter());
    }
	
	
	public void createEtcObject(EtcObject etc) throws IOException
    {
    	if (!getPlatformInfo().isIndicesSupported())
    	{
    		throw new WiseBizException("This platform does not support etcObjects");
    	}
    	else if (etc.getName() == null)
    	{
    		_log.warn("Cannot write unnamed etcObject " + etc.getName() );
    	}
    	else
    	{
    		println(getEtcScrtInfo(etc));
    	}
    }
	
	public void dropEtcObject(EtcObject etc) throws IOException
    {    	
    	print("DROP ");
		printIdentifier(getObjectDcdNm(etc));
		print(" ");
    	printIdentifier(getEtcObjectName(etc));
    	printEndOfStatement();
    }
	
	public String getEtcScrtInfo(EtcObject etc)
	{
//		_log.debug("scrtInfo: "+etc.getScrtInfo());
		return etc.getScrtInfo();
	}
	
	public String getEtcObjectName(EtcObject etc)
    {
    	return etc.getName();
    }
	
	public String getObjectDcdNm(EtcObject etc)
    {
    	return etc.getEtcObjDcdNm();
    }

	public void createBackupTable(Database database, Table table) throws IOException
	{
		createBackupTable(database, table, null);
	}
	
	public void createBackupTable(Database database, Table table, Map parameters) throws IOException
    {
        writeBackupTableCreationStmt(database, table, parameters);
        
        dropTable(table);
        
        if("D".equals(table.getRegTypCd())){
        	return ;
        }
        
        writeTableCreationStmt(database, table, parameters);
        writeTableSpaceStmt (table);
        
        //파티션이 존재할 경우 파티션 정보 출력
        createPartition(database, table, parameters); 
        
        writeTableCreationStmtEnding(table, parameters);

        int removeIdx = 0;
        
        for (int idx = 0; idx < table.getPrimaryKeyColumns().length; idx++)
        {
	        for(int colIdx=0; colIdx < table.getColumnCount(); colIdx++) {
	    		if(getColumnName(table.getColumn(colIdx)).equals(getColumnName(table.getPrimaryKeyColumns()[idx]))) {
	    			if(table.getColumn(colIdx).getRegTypCd() != null && table.getColumn(colIdx).getRegTypCd().equals("D")) {
	    				removeIdx++;
	    			}
	    		}
	        }
        }
        
        if (!getPlatformInfo().isPrimaryKeyEmbedded())
        {
        	if(table.getPrimaryKeyColumns().length > removeIdx)
        		createPrimaryKey(table, table.getPrimaryKeyColumns());
        }
        if (!getPlatformInfo().isIndicesEmbedded())
        {
//        	if(table.getPrimaryKeyColumns().length > removeIdx)
    		createIndexes(table);        	
        }
        //grant
        if (!getPlatformInfo().getGrantEmbedded())
        {
        	//createGrant(table);
        	//createGrants(table);
        }
        //SLC DBA 요청사항
        //createSynonyms(table);
        
    }
	
	protected void writeBackupTableCreationStmt(Database database, Table table, Map parameters) throws IOException
    {
    	println("/****[ BACKUP TABLE CREATE  ]********/");
        print("CREATE TABLE ");
        printlnIdentifier(getTableName(table) + "_BACKUP");
        writeTableSpaceStmt (table);
        println("AS ");
        println("SELECT * FROM " + getTableName(table));
        printEndOfStatement();
    }

	public void dataCopy(Table table) throws IOException
    {
    	println("/****[ DATA COPY   ]******/");
    	println("ALTER TABLE " + getTableName(table) + " NOLOGGING;");
    	println();
    	
        print("INSERT INTO ");
        printlnIdentifier(getTableName(table));
        println("(");

        writeDataCopyAlterColumns(table);
        
        println();
        println(")");
        
        println("SELECT ");
        writeDataCopyAlterColumns(table);
        println();
        println("FROM " + getTableName(table) + "_BACKUP");
        println(";");
        
        println();
        
        println("COMMIT;");
        
        println();
        
        println("ALTER TABLE " + getTableName(table) + " LOGGING;");
        
        println();

    }
	
	public void writeDataCopyAlterColumns(Table table) throws IOException {
		for (int idx = 0; idx < table.getColumnCount(); idx++)
        {
            //printIndent();
            Column col = table.getColumn(idx);
            
            if("D".equals(col.getRegTypCd())) {
            	//컬럼 삭제...
            	print("-- " + getColumnName(col) + "      DEL ");
            } else if ("U".equals(col.getRegTypCd()) && "Y".equals(col.getColUpdYn())){
            	//컬럼 업데이트...
            	print(getColumnName(col));
            	
            } else if ("C".equals(col.getRegTypCd())){
            	print("-- " + getColumnName(col) + "      NEW ");
            } else {
            	print(getColumnName(col));
            }
	            
	        if (idx < table.getColumnCount() - 1) {
	        	println(",");
	        }
        }

	}

	public void dropBackupTable(Table table) throws IOException
    {
        print("DROP TABLE ");
        printIdentifier(getTableName(table) + "_BACKUP");
        printEndOfStatement();
    }

}
