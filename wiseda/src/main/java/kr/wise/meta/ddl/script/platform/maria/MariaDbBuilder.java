package kr.wise.meta.ddl.script.platform.maria;

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
import java.util.Map;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

import org.apache.commons.lang.StringUtils;

import kr.wise.commons.cmm.exception.WiseBizException;
import kr.wise.commons.damgmt.db.service.WaaDbPrivilege;
import kr.wise.commons.util.UtilString;
import kr.wise.meta.ddl.script.model.Column;
import kr.wise.meta.ddl.script.model.Database;
import kr.wise.meta.ddl.script.model.Index;
import kr.wise.meta.ddl.script.model.Table;
import kr.wise.meta.ddl.script.platform.SqlBuilder;


/**
 * The SQL Builder for Oracle.
 *
 * @version $Revision: 893917 $
 */
public class MariaDbBuilder extends SqlBuilder
{
	/** The regular expression pattern for ISO dates, i.e. 'YYYY-MM-DD'. */
	private Pattern _isoDatePattern;
	/** The regular expression pattern for ISO times, i.e. 'HH:MI:SS'. */
	private Pattern _isoTimePattern;
	/** The regular expression pattern for ISO timestamps, i.e. 'YYYY-MM-DD HH:MI:SS.fffffffff'. */
	private Pattern _isoTimestampPattern;

	/**
     * Creates a new builder instance.
     *
     * @param platform The plaftform this builder belongs to
     */
    public MariaDbBuilder()
    {
        super();
        addEscapedCharSequence("'", "''");

    	try
    	{
            _isoDatePattern      = Pattern.compile("\\d{4}\\-\\d{2}\\-\\d{2}");
            _isoTimePattern      = Pattern.compile("\\d{2}:\\d{2}:\\d{2}");
            _isoTimestampPattern = Pattern.compile("\\d{4}\\-\\d{2}\\-\\d{2} \\d{2}:\\d{2}:\\d{2}[\\.\\d{1,8}]?");
        }
    	catch (PatternSyntaxException ex)
        {
        	throw new WiseBizException("Oracle8Builder Error");
        }
    }

    /**
     * {@inheritDoc}
     */
    public void createTable(Database database, Table table, Map parameters) throws IOException
    {
        // lets create any sequences
//        Column[] columns = table.getAutoIncrementColumns();
//
//        for (int idx = 0; idx < columns.length; idx++)
//        {
//            createAutoIncrementSequence(table, columns[idx]);
//        }

    	createTableMaria(database, table, parameters);

//        for (int idx = 0; idx < columns.length; idx++)
//        {
//            createAutoIncrementTrigger(table, columns[idx]);
//        }
    }

    /**
     * {@inheritDoc}
     */
    public void dropTable(Table table) throws IOException
    {
        Column[] columns = table.getAutoIncrementColumns();

        for (int idx = 0; idx < columns.length; idx++)
        {
            dropAutoIncrementTrigger(table, columns[idx]);
            dropAutoIncrementSequence(table, columns[idx]);
        }

        print("DROP TABLE ");
        printIdentifier(getTableName(table));
        print(" CASCADE CONSTRAINTS");
        printEndOfStatement();
    }

    /**
     * Creates the sequence necessary for the auto-increment of the given column.
     *
     * @param table  The table
     * @param column The column
     */
    protected void createAutoIncrementSequence(Table  table,
                                               Column column) throws IOException
    {
        print("CREATE SEQUENCE ");
        printIdentifier(getConstraintName("seq", table, column.getName(), null));
        printEndOfStatement();
    }



    /**
     * Drops the sequence used for the auto-increment of the given column.
     *
     * @param table  The table
     * @param column The column
     */
    protected void dropAutoIncrementSequence(Table  table,
                                             Column column) throws IOException
    {
        print("DROP SEQUENCE ");
        printIdentifier(getConstraintName("seq", table, column.getName(), null));
        printEndOfStatement();
    }

    /**
     * Drops the trigger used for the auto-increment of the given column.
     *
     * @param table  The table
     * @param column The column
     */
    protected void dropAutoIncrementTrigger(Table  table,
                                            Column column) throws IOException
    {
        print("DROP TRIGGER ");
        printIdentifier(getConstraintName("trg", table, column.getName(), null));
        printEndOfStatement();
    }

    /**
     * {@inheritDoc}
     */
    protected void createTemporaryTable(Database database, Table table, Map parameters) throws IOException
    {
        createTable(database, table, parameters);
    }

    /**
     * {@inheritDoc}
     */
    protected void dropTemporaryTable(Database database, Table table) throws IOException
    {
        dropTable(table);
    }

    /**
     * {@inheritDoc}
     */
    public void dropForeignKeys(Table table) throws IOException
    {
        // no need to as we drop the table with CASCASE CONSTRAINTS
    }

    /**
     * {@inheritDoc}
     */
    public void dropIndex(Table table, Index index) throws IOException
    {
        // Index names in Oracle are unique to a schema and hence Oracle does not
        // use the ON <tablename> clause
        print("DROP INDEX ");
        print(table.getSchema() + ".");
        printIdentifier(getIndexName(index));  
        printEndOfStatement();
    }



    /**
     * {@inheritDoc}
     */
    protected void writeColumnAutoIncrementStmt(Table table, Column column) throws IOException
    {
        // we're using sequences instead
    }

    /**
     * {@inheritDoc}
     */
    public String getSelectLastIdentityValues(Table table)
    {
        Column[] columns = table.getAutoIncrementColumns();

        if (columns.length > 0)
        {
            StringBuffer result = new StringBuffer();

            result.append("SELECT ");
            for (int idx = 0; idx < columns.length; idx++)
            {
                if (idx > 0)
                {
                    result.append(",");
                }
                result.append(getDelimitedIdentifier(getConstraintName("seq", table, columns[idx].getName(), null)));
                result.append(".currval");
            }
            result.append(" FROM dual");
            return result.toString();
        }
        else
        {
            return null;
        }
    }

    /**
     * {@inheritDoc}
     */
    public void addColumn(Database model, Table table, Column newColumn) throws IOException
    {
        print("ALTER TABLE ");
        printlnIdentifier(getTableName(table));
        printIndent();
        print(" ADD ");
        writeColumn(table, newColumn);
        printEndOfStatement();
        if (newColumn.isAutoIncrement())
        {
            createAutoIncrementSequence(table, newColumn);
//            createAutoIncrementTrigger(table, newColumn);
        }
    }

    /**
     * Writes the SQL to drop a column.
     *
     * @param table  The table
     * @param column The column to drop
     */
    public void dropColumn(Table table, Column column) throws IOException
    {
        if (column.isAutoIncrement())
        {
//            dropAutoIncrementTrigger(table, column);
            dropAutoIncrementSequence(table, column);
        }
        print("ALTER TABLE ");
        //printlnIdentifier(getTableName(table));
        print(getTableName(table));
        printIndent();
        print("DROP COLUMN ");
        printIdentifier(getColumnName(column));
        printEndOfStatement();
    }

    /**
     * Writes the SQL to drop the primary key of the given table.
     *
     * @param table The table
     */
    public void dropPrimaryKey(Table table) throws IOException
    {
        print("ALTER TABLE ");
        printlnIdentifier(getTableName(table));
        printIndent();
        print("DROP PRIMARY KEY");
        printEndOfStatement();
    }
    
   
    
    public void createPrimaryKey(Table table, Column[] primaryKeyColumns) throws IOException
    {
        if ((primaryKeyColumns.length > 0) && shouldGeneratePrimaryKeys(primaryKeyColumns))
        {
        	
        	String idxSpacPnm = UtilString.null2Blank(table.getIdxSpacPnm());
        	/*
        	print("CREATE UNIQUE INDEX ");
        	print(table.getSchema() + ".");        	
        	printIdentifier(getConstraintName(null, table, null, "PK"));
        	print(" ON ");
        	printIdentifier(getTableName(table));     
        	print(" ");
        	writePrimaryUniqueIdxStmt(table, primaryKeyColumns); 
        	
        	if(!idxSpacPnm.equals("")){
        	
        		print(" TABLESPACE " + idxSpacPnm);
        	}        	
        	
        	print(getPlatformInfo().getSqlCommandDelimiter());
        	
        	println();
        	println();
        	*/
            print("ALTER TABLE ");
            printlnIdentifier(getTableName(table));
            printIndent();
            print("ADD CONSTRAINT ");
            printIdentifier(getConstraintName(null, table, null, "PK"));
            print(" ");
            writePrimaryKeyStmt(table, primaryKeyColumns);
           
//            print(" USING INDEX "); 
            
        	println(getPlatformInfo().getSqlCommandDelimiter());
        	println();
        }
    }
    
    private String spaceFull(int length) {
		String txtReturn = "";
		for(int i = 0; i < length; i++) {
			txtReturn += " ";
		}
		return txtReturn;
	}
    
    public void createTableMaria(Database database, Table table, Map parameters) throws IOException
    {
        writeTableCreationStmt(database, table, parameters);
        writeMariaTableComment (table);
        //writeTableSpaceStmt (table);
        writeTableCreationStmtEnding(table, parameters);

        if (!getPlatformInfo().isPrimaryKeyEmbedded())
        {
            createPrimaryKey(table, table.getPrimaryKeyColumns());
        }
        if (!getPlatformInfo().isIndicesEmbedded())
        {
            createIndexes(table);        	
        }
        
    }

    
    protected void writeColumn(Table table, Column column) throws IOException
    {
        
        String tblChgTypCd = UtilString.null2Blank(table.getDdltbl().getTblChgTypCd()) ;       
        String nonulUpdYn  = UtilString.null2Blank(column.getNonulUpdYn());
        String regTypCd    = UtilString.null2Blank(column.getRegTypCd());
       
        //see comments in columnsDiffer about null/"" defaults
        printIdentifier(getColumnName(column));
        
        print(spaceFull(40 - getColumnName(column).length()));
        
        print(getSqlType(column));
        
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
        writeColumnDefaultValueStmt(table, column);
        writeMariaColumnComment(table, column);
        
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
    
    protected void writeMariaTableComment(Table table) throws IOException
    {
        String comment = table.getDescription();

        if (comment != null||!comment.equals(""))
        {
                print(" COMMENT ");
                print("'");
                print(comment);
                print("'");
        }
    }
    
    protected void writeMariaColumnComment(Table table, Column column) throws IOException
    {
        String comment = column.getDescription();

        if (comment != null||!comment.equals(""))
        {
                print(" COMMENT ");
                print("'");
                print(comment);
                print("'");
        }
    }
    
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
    			printIndent();
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
        			if (idx > 0)
        			{
        				print(", ");
        			}
        			printIdentifier(getColumnName(col));
        		}
        		print(")");
    			
    		}else{
    			// create [unique] index [idx_name]([column_name1, column_name2...]) ON [table_name]
    			print("ALTER TABLE ");
    			printlnIdentifier(getTableName(table));
    			print(" ADD");
//        		if (UtilString.null2Blank(table.getIdxtype()).equals("UK"))
    			if (UtilString.null2Blank(table.getIdxtype()).equals("UNIQUE"))
        		{
        			print(" UNIQUE");
        		}
        		print(" INDEX ");
        		printIdentifier(table.getSchema()+"."+table.getIdxname());
        		println("");
        		print(" (");
        		
        		for (int idx = 0; idx < table.getColumnCount(); idx++)
        		{
        			Column idxColumn = table.getColumn(idx);
        			Column      col     = table.findColumn(idxColumn.getName());
        			
        			if (col == null)
        			{
        				// would get null pointer on next line anyway, so throw exception
        				throw new WiseBizException("Invalid column '" + idxColumn.getName() + "' on index " + table.getIdxname() + " for table " + table.getName());
        			}
        			if (idx > 0)
        			{
        				print(", ");
        			}
        			printIdentifier( col.getName() + " "+col.getType() );
        		}
        		println(")");
    		}
    		
    		//파티션 사용안함
//    		setParttionIdxSpace(table);
    		//테이블 스페이스는 모르겠음
//    		if(!UtilString.null2Blank(table.getIdxSpacPnm()).equals("")){
//    			print("TABLESPACE  ");
//    			println(table.getIdxSpacPnm());
//    		}
//    		
//        	if(!StringUtils.isBlank(table.getIdxSpacAddScrt())) {
//    			println(table.getIdxSpacAddScrt());
//    		}
//        	printPartitionIndexLocal(table);

    		
    		printEndOfStatement();
    	}
    }
    
    
    /** @param table insomnia
     * @throws IOException */
	public void writeAlterColumns(Table table) throws IOException {
		for (int idx = 0; idx < table.getColumnCount(); idx++)
        {
            //printIndent();
            Column col = table.getColumn(idx);
            if(!"1".equals(col.getVrfCd())) {
            	continue;
            }

            if("D".equals(col.getRegTypCd())) {
            	//컬럼 삭제...
            	dropColumn(table, col);
            } else if ("U".equals(col.getRegTypCd()) && "Y".equals(col.getColUpdYn())){
            	//컬럼 업데이트...
            	modifyColumn(table, col);
            } else if ("C".equals(col.getRegTypCd())){
            	//컬럼 추가....
            	addColumn(table, col);
            }
        }

	}
	
	public void createComment(Table table) throws IOException {
    }

}
