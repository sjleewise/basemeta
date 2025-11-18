package kr.wise.commons.test.service.impl;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import kr.wise.commons.test.service.TestMapper;
import kr.wise.commons.test.service.TestMapper2;
import kr.wise.commons.test.service.toTestService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

@Service("totestService")
//@Transactional
public class TestServiceImpl implements toTestService  {

	private final Logger log = LoggerFactory.getLogger(getClass());

	@Autowired
	private TestMapper testMapper;

	@Autowired
	private TestMapper2 testMapper2;
//
//	@Inject
//	private TestMapper3 testMapper3;

//	@Autowired
//	private SqlSessionFactory sqlSessionFactory;

//	@Inject
//	private PlatformTransactionManager transactionManager;


//	@Transactional(value="transactionManager",  propagation=Propagation.REQUIRED)
	public void testmethod1() throws Exception, SQLException, DataAccessException{

		/**
		 * Transaction을 프로그램 코드를 이용해 처리한다.
		 * mybatis-spring에서 스프링의 트랜젝션 처리가 되지않아 오류시 롤백이 안됨...
		 * 다중 insert, update 작업의 경우 transactionManager 를 DI 받아 사용한다...
		 */
//		DefaultTransactionDefinition def = new DefaultTransactionDefinition();
//		def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
//		TransactionStatus status = transactionManager.getTransaction(def);


		Map<String, String> param = new HashMap<String, String>();
		param.put("id", "test_id1");
		param.put("name", "test_name1");


//		try{

		testMapper.deleteTest(param);
		testMapper.insertTest(param);
		testMapper.insertTest1(param);
		testMapper2.insertTest(param);
//		testMapper.insertTest1(param);
//		} catch (Exception e) {
//			transactionManager.rollback(status);
//			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly() ;
//			throw e;
//		}
//			transactionManager.commit(status);
////
//		testMapper.updateTest(param);
////
//
//		testMapper.selectTest(param);

//		testMapper2.deleteTest(param);
//		testMapper2.insertTest(param);
//		testMapper2.insertTest(param);

			/*		SqlSession sqlsession =  sqlSessionFactory.openSession();

		try{
			sqlsession.getMapper(TestMapper.class).deleteTest(param);
			sqlsession.getMapper(TestMapper.class).insertTest(param);
//			sqlsession.getMapper(TestMapper.class).insertTest(param);
			sqlsession.getMapper(TestMapper.class).updateTest(param);
			sqlsession.commit();
		} catch(Exception e) {
			e.printStackTrace();

		} finally {
			sqlsession.close();

		}*/

//		testMapper3.selectTest(param);

//		List<Map> list = testMapper3.selectProc(param);

	}

}
