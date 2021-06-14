package com.ict.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.session.SqlSession;

public class DAO {
	// MyBatis에서는 SqlSession  클래스를 이용해서 
	// mapper.xml 파일의  태그들을 사용해서 SQL를 사용한다.
	private static SqlSession ss;
	private synchronized static SqlSession getSession() {
		if(ss==null) {
			// ss = DBService.getFactory().openSession();      // select문
			// ss = DBService.getFactory().openSession(true);  // autocommit();
			
			// 트랜젝션 처리를 위해서 수동 commit()를 하자 
			ss = DBService.getFactory().openSession(false); // 수동commit()
		}
		return ss;
	}

	// MyBatis select문은 4가지고 구분된다.
	// 결과가 여러개 일때 List<VO> => getSession().selectList()
	// 결과 하나일때 VO  => getSession().selectOne()          
	// 파라미터값이 없을 때 
	// 파라미터값이 있을 때 (파라미터가 2개이상이면 무조건 VO 아니면 Map를 사용해야 한다.)
	public static List<VO> getSelectAll() {
		List<VO> list = new ArrayList<VO>();
		
		// getSession().sql명령과 같은 메소드 
		// list = 	getSession().selectList("mapper의 id"); // 파라미터가 없는 메소드
		// list = 	getSession().selectList("mapper의 id", 파라미터); // 파라미터가 없는 메소드
		list = getSession().selectList("list");
		return list;
	}

	// 상세보기
	public static VO getSelectOne(String idx) {
		VO vo = null;
		vo = getSession().selectOne("onelist", idx);
		return vo;
	}
	
	// insert 
	// getSession().insert("mapper의 id", 파라미터)
	// insert, update, delete는 openSession(false)를 했기 때문에 commit를 해야 된다.
	public static int getInsert(VO vo) {
		int result = 0;
		result = getSession().insert("insert", vo);
		ss.commit();
		return result;
	}

	

	// update
	// getSession().update("mapper의 id", 파라미터)
	// insert, update, delete는 openSession(false)를 했기 때문에 commit를 해야 된다.
	public static int getUpdate(VO vo) {
		int result = 0;
		result = getSession().update("update", vo);
		ss.commit();
		return result;
	}

	// getSession().delete("mapper의 id", 파라미터)
	// insert, update, delete는 openSession(false)를 했기 때문에 commit를 해야 된다.
	public static int getDelete(VO vo) {
		int result = 0;
		result = getSession().delete("delete", vo);
		ss.commit();
		return result;
	}
}
