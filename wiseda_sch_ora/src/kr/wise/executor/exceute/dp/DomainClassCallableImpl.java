/**
 * 0. Project  : WISE Advisor 프로젝트
 *
 * 1. FileName : PythonExecuteCallableImpl.java
 * 2. Package : kr.wise.executor.exceute.ai
 * 3. Comment : 
 * 4. 작성자  : insomnia
 * 5. 작성일  : 2018. 1. 11. 오후 4:49:14
 * 6. 변경이력 : 
 *                    이름     : 일자          : 근거자료   : 변경내용
 *                   ------------------------------------------------------
 *                    insomnia : 2018. 1. 11. :            : 신규 개발.
 */
package kr.wise.executor.exceute.dp;

import java.util.concurrent.Callable;

import kr.wise.executor.dm.ExecutorDM;

/**
 * <PRE>
 * 1. ClassName : 
 * 2. FileName  : PythonExecuteCallableImpl.java
 * 3. Package  : kr.wise.executor.exceute.ai
 * 4. Comment  : 
 * 5. 작성자   : insomnia
 * 6. 작성일   : 2018. 1. 11. 오후 4:49:14
 * </PRE>
 */
public class DomainClassCallableImpl extends DomainClass implements Callable<Boolean> {

	/** insomnia */
	public DomainClassCallableImpl(String logId, ExecutorDM executorDM) {
		super(logId, executorDM);
	}

	/** insomnia */
	public Boolean call() throws Exception {
		return doExecute();
	}

}
