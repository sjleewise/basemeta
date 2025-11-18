/**
 * 0. Project  : WISE DA 프로젝트
 *
 * 1. FileName : TestDdlScript.java
 * 2. Package : kr.wise.ddl.script
 * 3. Comment :
 * 4. 작성자  : insomnia
 * 5. 작성일  : 2014. 5. 20. 오후 6:01:26
 * 6. 변경이력 :
 *                    이름     : 일자          : 근거자료   : 변경내용
 *                   ------------------------------------------------------
 *                    insomnia : 2014. 5. 20. :            : 신규 개발.
 */
package kr.wise.ddl.script;

import java.io.IOException;
import java.io.StringWriter;

import kr.wise.meta.ddl.script.model.Column;
import kr.wise.meta.ddl.script.model.Database;
import kr.wise.meta.ddl.script.model.Table;
import kr.wise.meta.ddl.script.platform.oracle.Oracle10Builder;
import kr.wise.meta.ddl.script.platform.oracle.Oracle8Builder;
import kr.wise.test.AbstractApplicationContextTest;

import org.junit.Before;
import org.junit.Test;

/**
 * <PRE>
 * 1. ClassName :
 * 2. FileName  : TestDdlScript.java
 * 3. Package  : kr.wise.ddl.script
 * 4. Comment  :
 * 5. 작성자   : insomnia
 * 6. 작성일   : 2014. 5. 20. 오후 6:01:26
 * </PRE>
 */
public class TestDdlScript extends AbstractApplicationContextTest {

	//오라클 SQL 빌더
	private Oracle8Builder sqlbuild ;

	/** sql결과 */
    private StringWriter _writer;

    private Database database;

    private Table table;

	@Before
	public void setUp() {
		_writer = new StringWriter();

		sqlbuild = new Oracle10Builder();

		sqlbuild.setWriter(_writer);

		database = new Database();

		table = new Table();

		Column col = new Column();

		col.setName("coltest1");
		col.setType("VARCHAR2");
		col.setDataLen(50);
//		col.setPrecisionRadix(50);
		col.setRequired(true);
		col.setDefaultValue("test");
		col.setPrimaryKey(true);
		col.setDescription("desc test");

		Column col2 = new Column();

		col2.setName("coltest2");
		col2.setType("DATE");
		col2.setRequired(true);
		col2.setDefaultValue("test");
		col2.setPrimaryKey(true);
//		col2.setDescription("desc date");

		Column col3 = new Column();
		col3.setName("coltest3");
		col3.setType("NUMBER");
		col3.setDataLen(5);
		col3.setDataScal(2);
		col3.setRequired(false);
		col3.setDefaultValue("1");
		col3.setPrimaryKey(false);
		col3.setDescription("desc number");

		table.addColumn(col);
		table.addColumn(col2);
		table.addColumn(col3);
		table.setName("test_table");
		table.setSchema("WDML");
		table.setDescription("desc test");


		database.addTable(table);

	}

	@Test
	public void test() throws IOException {
		sqlbuild.writeTableComment(table);
		sqlbuild.dropTable(table);
		sqlbuild.createTable(database, table);
		sqlbuild.createComment(table);

		System.out.println(_writer);

	}

}
