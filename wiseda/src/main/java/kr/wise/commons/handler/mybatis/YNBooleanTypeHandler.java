/**
 * 0. Project  : WISE DA 프로젝트
 *
 * 1. FileName : YNBooleanTypeHandler.java
 * 2. Package : kr.wise.commons.handler.mybatis
 * 3. Comment :
 * 4. 작성자  : insomnia
 * 5. 작성일  : 2014. 5. 21. 오전 11:19:59
 * 6. 변경이력 :
 *                    이름     : 일자          : 근거자료   : 변경내용
 *                   ------------------------------------------------------
 *                    insomnia : 2014. 5. 21. :            : 신규 개발.
 */
package kr.wise.commons.handler.mybatis;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

/**
 * <PRE>
 * 1. ClassName :
 * 2. FileName  : YNBooleanTypeHandler.java
 * 3. Package  : kr.wise.commons.handler.mybatis
 * 4. Comment  :
 * 5. 작성자   : insomnia
 * 6. 작성일   : 2014. 5. 21. 오전 11:19:59
 * </PRE>
 */
public class YNBooleanTypeHandler extends BaseTypeHandler<Boolean> {

	/** insomnia */
	@Override
	public void setNonNullParameter(PreparedStatement ps, int i,
			Boolean parameter, JdbcType jdbcType) throws SQLException {
		// TODO Auto-generated method stub

	}

	/** insomnia */
	@Override
	public Boolean getNullableResult(ResultSet rs, String columnName)
			throws SQLException {
		return "Y".equalsIgnoreCase(rs.getString(columnName));
	}

	/** insomnia */
	@Override
	public Boolean getNullableResult(ResultSet rs, int columnIndex)
			throws SQLException {
		return "Y".equalsIgnoreCase(rs.getString(columnIndex));
	}

	/** insomnia */
	@Override
	public Boolean getNullableResult(CallableStatement cs, int columnIndex)
			throws SQLException {
		return "Y".equalsIgnoreCase(cs.getString(columnIndex));
	}

}
