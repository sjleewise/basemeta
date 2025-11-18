package kr.wise.meta.ddl.script.platform.informix;

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
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

import org.apache.commons.lang.StringUtils;

import kr.wise.commons.cmm.exception.WiseBizException;
import kr.wise.commons.util.UtilString;
import kr.wise.meta.ddl.script.model.Column;
import kr.wise.meta.ddl.script.model.Index;
import kr.wise.meta.ddl.script.model.Table;
import kr.wise.meta.ddl.script.platform.SqlBuilder;


/**
 * The SQL Builder for Oracle.
 *
 * @version $Revision: 893917 $
 */
public class Informix4Builder extends SqlBuilder
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
    public Informix4Builder()
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
        	throw new WiseBizException("Informix4Builder Error");
        }
    }

    public void createComment(Table table) throws IOException {
//    	writeTableCommentStmt(table);
//    	writeColCommentStmt(table);
    	println();
    }
    

    public void alterTable(Table table) throws IOException
    {
    	    	    	
    	//======테이블 커멘트 변경=======
    	String tblCmmtUpdYn = UtilString.null2Blank(table.getDdltbl().getTblCmmtUpdYn());
    	
    	if(tblCmmtUpdYn.equals("Y")) {
    		
//    		writeTableCommentStmt(table);
    	}
    	//==============================
    	
    	//컬럼 ALTER
    	writeAlterColumns(table);

    	if (!getPlatformInfo().isPrimaryKeyEmbedded())
    	{
//    		createPrimaryKey(table, table.getPrimaryKeyColumns());
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
            	
            	//커멘트 추가
//            	addColCommentStmt(table, col);
            }
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
//        		print(table.getSchema() + ".");
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
        			if (idx > 0)
        			{
        				print(", ");
        			}
        			printIdentifier( col.getName() + " "+col.getType() );
        		}
        		print(")");
    		}
    		
    		println();
    		
    		/*if(!UtilString.null2Blank(table.getIdxSpacPnm()).equals("")){
    			print("TABLESPACE  ");
    			printIdentifier(table.getIdxSpacPnm());
    		}*/

    		
    		printEndOfStatement();
    	}
    }
}
