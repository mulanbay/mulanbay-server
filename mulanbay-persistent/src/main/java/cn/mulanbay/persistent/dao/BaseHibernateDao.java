package cn.mulanbay.persistent.dao;

import cn.mulanbay.common.exception.PersistentException;
import cn.mulanbay.common.util.JsonUtil;
import cn.mulanbay.persistent.common.BaseException;
import cn.mulanbay.persistent.common.OPUtil;
import org.hibernate.HibernateException;
import org.hibernate.LockMode;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.NativeQuery;
import org.hibernate.query.Query;
import org.hibernate.query.internal.NativeQueryImpl;
import org.hibernate.transform.Transformers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.SessionFactoryUtils;

import java.io.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

/**
 * 数据库基本操作:Hibernate基类
 *
 * @author fenghong
 * @create 2018-01-20 21:44
 */
public class BaseHibernateDao {

	/**
	 * 默认每页数
	 */
	public static int DEFAULT_PAGE_SIZE = 20;

	private static final Logger logger = LoggerFactory.getLogger(BaseHibernateDao.class);

	/**
	 * 默认的空结果集，避免调用者空指针判断
	 */
	private static final List EMPTY_LIST = new ArrayList(0);

	private SessionFactory sessionFactory;

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	@Autowired
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	/**
	 * 根据pk删除对象
	 *
	 * @param c
	 *            对象类型
	 * @param id
	 *            主键
	 * @throws BaseException
	 */
	@SuppressWarnings("rawtypes")
	protected void removeEntity(Class c, Serializable id) throws BaseException {
		try {
			if(logger.isDebugEnabled()){
				logger.debug("removeEntity c:" + c + ",id=" + id);
			}
			Object obj = this.getSession().load(c, id);
			this.getSession().delete(obj);
		} catch (HibernateException e) {
			throw OPUtil.handleException(e);
		}
	}

	protected Session getSession() {
		return sessionFactory.getCurrentSession();
	}

	protected void flushSession(){
	    this.getSession().flush();
    }

    protected void refreshObject(Object o){
		this.getSession().refresh(o);
	}

	/**
	 * 删除对象
	 *
	 * @param o
	 * @throws BaseException
	 */
	protected void removeEntity(Object o) throws BaseException {
		if(logger.isDebugEnabled()){
			logger.debug("removeEntity o:" + JsonUtil.beanToJson(o));
		}
		getSession().delete(o);
	}

	/**
	 * 根据HQL删除对象集合（类似 from Entity as e where e.pk=*）
	 *
	 * @param hql
	 * @throws BaseException
	 */
	protected void removeEntities(String hql) throws BaseException {
		if(logger.isDebugEnabled()){
			logger.debug("removeEntities HQL:" + hql);
		}
		getSession().createQuery(hql).executeUpdate();
	}

	/**
	 * 删除对象集合
	 *
	 * @param objs
	 * @throws BaseException
	 */
	@SuppressWarnings("rawtypes")
	protected void removeEntities(Collection objs) throws BaseException {
		if(logger.isDebugEnabled()){
			logger.debug("removeEntitys objs:" + JsonUtil.beanToJson(objs));
		}
		if (objs == null) {
			throw new BaseException("持久化对象为空！");
		}
		try {
			for (Object o : objs) {
				removeEntity(o);
			}
		} catch (HibernateException he) {
			throw OPUtil.handleException(he);
		} catch (Exception e) {
			throw OPUtil.handleException(e);
		}
	}

	/**
	 * 根据pk找回对象,没找到赋予null
	 *
	 * @param c
	 *            对象类型
	 * @param id
	 *            主键
	 * @return 实体对象
	 * @throws BaseException
	 */
	@SuppressWarnings({ "rawtypes" })
	protected Object getEntityById(Class c, Serializable id)
			throws BaseException {
		if(logger.isDebugEnabled()){
			logger.debug("getEntityById c:" + c + ",id=" + id);
		}
		Object obj = null;
		try {
			obj = getSession().get(c, id);
		} catch (Exception he) {
			throw OPUtil.handleException(he);
		}
		return obj;
	}

	/**
	 * 根据pk找回对象,没找到赋予null
	 *
	 * @param c
	 *            对象类型
	 * @param id
	 *            主键
	 * @return 实体对象
	 * @throws BaseException
	 */
	@SuppressWarnings({ "rawtypes" })
	protected Object loadEntityById(Class c, Serializable id)
			throws BaseException {
		if(logger.isDebugEnabled()){
			logger.debug("getEntityById c:" + c + ",id=" + id);
		}
		Object obj = null;
		try {
			obj = getSession().load(c, id);
		} catch (Exception he) {
			throw OPUtil.handleException(he);
		}
		return obj;
	}

	/**
	 * 根据pk找回对象(带锁模式),没找到抛出ObjectNotFoundException异常
	 *
	 * @param c
	 *            对象类型
	 * @param id
	 *            主键
	 * @param lockMode
	 *            锁模式
	 * @return 实体对象
	 * @throws BaseException
	 */
	@SuppressWarnings({ "rawtypes", "deprecation" })
	protected Object getEntityForUpdate(Class c, Serializable id,
			LockMode lockMode) {
		if(logger.isDebugEnabled()){
			logger.debug("getEntityById c:" + c + ",id=" + id);
		}
		Object obj;
		obj = null;
		obj = getSession().load(c, id, lockMode);

		return obj;
	}

	/**
	 * 保存对象集合
	 *
	 * @param obj
	 *            对象
	 * @throws BaseException
	 */
	protected Serializable saveEntity(Object obj) throws BaseException {
		if(logger.isDebugEnabled()){
			logger.debug("saveEntity obj:" + JsonUtil.beanToJson(obj));
		}
		if (obj == null) {
			throw new BaseException("保存失败，持久化对象为空！");
		}
		try {
			return getSession().save(obj);
		} catch (Exception e) {
			throw OPUtil.handleException(e);
		}
	}

	/**
	 * 保存对象数据
	 *
	 * @param objs
	 *            对象数据
	 * @throws BaseException
	 */
	protected void saveEntities(Object[] objs) throws BaseException {
		if(logger.isDebugEnabled()){
			logger.debug("saveEntities objs:" + JsonUtil.beanToJson(objs));
		}
		if (objs == null) {
			throw new BaseException("保存失败，持久化对象为空！");
		}
		try {
			for (Object element : objs) {
				if (element == null) {
					throw new BaseException("保存失败，持久化对象为空！");
				}
				getSession().save(element);
			}
		} catch (HibernateException he) {
			throw OPUtil.handleException(he);
		} catch (Exception e) {
			throw OPUtil.handleException(e);
		}
	}

	/**
	 * 保存和更新对象
	 *
	 * @param saveAndUpdate
	 *            需要更新和新增的对象集合
	 * @throws BaseException
	 */
	protected void saveAndUpdateEntities(Object[] saveAndUpdate)
			throws BaseException {
		try {
			if(logger.isDebugEnabled()){
				logger.debug("saveAndUpdateEntities saveAndUpdate:" + JsonUtil.beanToJson(saveAndUpdate));
			}
			if (saveAndUpdate != null) {
				for (Object element : saveAndUpdate) {
					if (element == null) {
						throw new BaseException("保存失败，持久化对象为空！");
					}
					getSession().saveOrUpdate(element);
				}
			}
		} catch (HibernateException he) {
			throw OPUtil.handleException(he);
		} catch (Exception e) {
			throw OPUtil.handleException(e);
		}
	}

	/**
	 * 保存和更新对象
	 *
	 * @param saveAndUpdate
	 *            需要更新和新增的对象集合
	 * @throws BaseException
	 */
	protected void saveAndUpdateEntity(Object saveAndUpdate)
			throws BaseException {
		try {
			if(logger.isDebugEnabled()){
				logger.debug("saveAndUpdateEntity saveAndUpdate:" + JsonUtil.beanToJson(saveAndUpdate));
			}
			if (saveAndUpdate == null) {
				throw new BaseException("保存失败，持久化对象为空！");
			}
			getSession().saveOrUpdate(saveAndUpdate);
		} catch (HibernateException he) {
			throw OPUtil.handleException(he);
		} catch (Exception e) {
			throw OPUtil.handleException(e);
		}
	}

	/**
	 * 更新对象
	 *
	 * @param obj
	 * @throws BaseException
	 */
	protected void updateEntity(Object obj) throws BaseException {
		if(logger.isDebugEnabled()){
			logger.debug("updateEntity obj:" + JsonUtil.beanToJson(obj));
		}
		updateEntities(new Object[] { obj });
	}

	/**
	 * 更新对象
	 *
	 * @param obj
	 * @throws BaseException
	 */
	protected void mergeEntity(Object obj) throws BaseException {
		if(logger.isDebugEnabled()){
			logger.debug("mergeEntity obj:" + JsonUtil.beanToJson(obj));
		}
		if (obj == null) {
			throw new BaseException("持久化对象为空！");
		}
		try {

			getSession().merge(obj);
		} catch (HibernateException he) {
			throw OPUtil.handleException(he);
		} catch (Exception e) {
			throw OPUtil.handleException(e);
		}
	}

	/**
	 * 更新对象集合
	 *
	 * @param objs
	 * @throws BaseException
	 */
	protected void updateEntities(Object[] objs) throws BaseException {
		if(logger.isDebugEnabled()){
			logger.debug("updateEntities objs:" + JsonUtil.beanToJson(objs));
		}
		if (objs == null) {
			throw new BaseException("持久化对象为空！");
		}
		try {
			for (Object element : objs) {
				if (element == null) {
					throw new BaseException("更新失败，持久化对象为空！");
				}
				getSession().update(element);
			}
		} catch (HibernateException he) {
			throw OPUtil.handleException(he);
		} catch (Exception e) {
			throw OPUtil.handleException(e);
		}
	}

	/**
	 * 利用hql更新
	 *
	 * @param hql
	 *            HQL语句
	 * @param objects
	 *            变量绑定参数
	 * @throws BaseException
	 */
	protected int updateEntities(String hql, Object... objects)
			throws BaseException {
		try {
			if(logger.isDebugEnabled()){
				logger.debug("updateObjs hql:" + hql);
				logger.debug("请求参数："+JsonUtil.beanToJson(objects));
			}
			Query query = getSession().createQuery(hql);
			int i = 0;
			for (Object object : objects) {
				query.setParameter(i++, object);
			}
			return query.executeUpdate();
		} catch (Exception e) {
			throw OPUtil.handleException(e);
		}
	}

	/**
	 * 利用预编译HQL查询记录总数
	 *
	 * @param hql
	 *            HQL语句
	 * @param objects
	 *            变量绑定参数
	 * @return Long 返回记录条数
	 */
	@SuppressWarnings("unchecked")
	protected Long getCount(String hql, Object... objects) throws BaseException {
		try {
			if(logger.isDebugEnabled()){
				logger.debug("getCount hql:" + hql);
				logger.debug("getCount 变量=" + JsonUtil.beanToJson(objects));
			}
			Query query = getSession().createQuery(hql);
			int i = 0;
			for (Object object : objects) {
				query.setParameter(i++, object);
			}
			List<Object> result = query.list();
			if (result == null||result.isEmpty()) {
				return 0L;
			} else {
				Object o = result.get(0);
				return Long.valueOf(o.toString()) ;
			}
		} catch (Exception e) {
			throw OPUtil.handleException(e);
		}
	}

	/**
	 * 利用预编译HQL查询，如果查询不到，返回新的ArrayList，不返回NullPoint。
	 *
	 * @param hql
	 *            SQL语句
	 * @param pageNum
	 * @param pageSize
	 * @param clazz
	 *            指定类名
	 * @param objects
	 *            变量绑定参数
	 * @return Query 返回Query
	 */
	@SuppressWarnings("rawtypes")
	protected List getEntityListWithClassHQL(String hql, int pageNum,
											 int pageSize, Class clazz, Object... objects) throws BaseException {
		try {
			if(logger.isDebugEnabled()){
				logger.debug("retrieveSQLObjs hql:" + hql);
				logger.debug("参数：page=" + pageNum + ",pageSize=" + pageSize
						+ ",变量=" + JsonUtil.beanToJson(objects));
			}
			Query query = getSession().createQuery(hql);
			query.unwrap(NativeQueryImpl.class)
					.setResultTransformer(Transformers.aliasToBean(clazz));
			int i = 0;
			for (Object object : objects) {
				query.setParameter(i++, object);
			}
			query.setFirstResult(pageSize * (pageNum - 1));
			query.setMaxResults(pageSize);
			query.setCacheable(true);
			query.setCacheRegion("frontpages");
			List result = query.list();
			if (result == null) {
				return EMPTY_LIST;
			} else {
				return result;
			}
		} catch (Exception e) {
			throw OPUtil.handleException(e);
		}

	}


	/**
	 * 利用预编译SQL查询，如果查询不到，返回新的ArrayList，不返回NullPoint。
	 *
	 * @param sql
	 *            SQL语句
	 * @param pageNum
	 * @param pageSize
	 * @param clazz
	 *            指定类名
	 * @param objects
	 *            变量绑定参数
	 * @return Query 返回Query
	 */
	@SuppressWarnings("rawtypes")
	protected List getEntityListWithClassSQL(String sql, int pageNum,
			int pageSize, Class clazz, Object... objects) throws BaseException {
		try {
			if(logger.isDebugEnabled()){
				logger.debug("retrieveSQLObjs sql:" + sql);
				logger.debug("参数：page=" + pageNum + ",pageSize=" + pageSize
						+ ",变量=" + JsonUtil.beanToJson(objects));
			}
			NativeQuery query =  getSession().createSQLQuery(sql);
			query.unwrap(NativeQueryImpl.class)
					.setResultTransformer(Transformers.aliasToBean(clazz));
			int i = 0;
			for (Object object : objects) {
				query.setParameter(i++, object);
			}
			if (pageNum > 0) {
				query.setFirstResult(pageSize * (pageNum - 1));
				query.setMaxResults(pageSize);
			}
			query.setCacheable(true);
			query.setCacheRegion("frontpages");
			List result = query.list();
			if (result == null) {
				return EMPTY_LIST;
			} else {
				return result;
			}
		} catch (Exception e) {
			logger.error("执行sql异常,SQL:\n"+sql);
			throw OPUtil.handleException(e);
		}

	}

	/**
	 * 利用预编译sql查询记录总数
	 *
	 * @param sql
	 *            sql语句
	 * @param objects
	 *            变量绑定参数
	 * @return Long 返回记录条数
	 */
	@SuppressWarnings("unchecked")
	protected Long getCountSQL(String sql, Object... objects) throws BaseException {
		try {
			if(logger.isDebugEnabled()){
				logger.debug("getCount sql:" + sql);
				logger.debug("getCount 变量=" + JsonUtil.beanToJson(objects));
			}
			NativeQuery query = getSession().createSQLQuery(sql);
			int i = 0;
			for (Object object : objects) {
				query.setParameter(i++, object);
			}
			List<Object> result = query.list();
			if (result == null||result.isEmpty()) {
				return 0L;
			} else {
				Object o = result.get(0);
				return Long.valueOf(o.toString()) ;
			}
		} catch (Exception e) {
			throw OPUtil.handleException(e);
		}
	}

	/**
	 * 利用预编译SQL查询，不分页如果查询不到，返回新的ArrayList，基本是返回List<Object[]>，不返回NullPoint。 *
	 *
	 * @param sql
	 *            SQL语句
	 * @param objects
	 *            变量绑定参数
	 * @return Query 返回Query
	 */
	@SuppressWarnings("rawtypes")
	protected List getEntityListNoPageSQL(String sql, Object... objects)
			throws BaseException {
		return getEntityListSQL(sql, -1, 0, objects);

	}

	/**
	 * 利用预编译SQL查询，如果查询不到，返回新的ArrayList，基本是返回List<Object[]>，不返回NullPoint。 *
	 *
	 * @param sql
	 *            SQL语句
	 * @param page
	 *            第一页从1开始 ,小于等于0不分页
	 * @param pageSize
	 *            每页显示数量
	 * @param objects
	 *            变量绑定参数
	 * @return Query 返回Query
	 */
	@SuppressWarnings("rawtypes")
	protected List getEntityListSQL(String sql, int page, int pageSize,
			Object... objects) throws BaseException {
		try {
			if(logger.isDebugEnabled()){
				logger.debug("retrieveEntityListSQL sql:" + sql);
				logger.debug("参数：page=" + page + ",pageSize=" + pageSize + ",变量="
						+ JsonUtil.beanToJson(objects));
			}
			NativeQuery query = getSession().createSQLQuery(sql);
			int i = 0;
			for (Object object : objects) {
				query.setParameter(i++, object);
			}
			if (page > 0) {
				query.setFirstResult(pageSize * (page - 1));
				query.setMaxResults(pageSize);
			}
			// query.setCacheable(true);
			// query.setCacheRegion("frontpages");
			List result = query.list();
			if (result == null) {
				return EMPTY_LIST;
			} else {
				return result;
			}
		} catch (Exception e) {
			logger.error("getEntityListSQL error,sql:"+sql);
			logger.error("请求参数:"+JsonUtil.beanToJson(objects));
			throw OPUtil.handleException(e);
		}

	}

	/**
	 * 根据sql获取列表
	 *
	 * @param sql
	 *            sql
	 * @param objects
	 *            变量绑定参数
	 * @return list Object[]对象列表
	 * @throws BaseException
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	protected List execSqlQuery(String sql, Object... objects)
			throws BaseException {
		if(logger.isDebugEnabled()){
			logger.debug("execSqlQuery sql:" + sql);
			logger.debug("请求参数:"+JsonUtil.beanToJson(objects));
		}
		Connection conn = null;
		ResultSet rs = null;
		PreparedStatement pstmt = null;

		List list = new ArrayList();
		try {
			conn = getConnection();
			pstmt = conn.prepareStatement(sql);
			int i = 0;
			for (Object object : objects) {
				setObject(pstmt, ++i, object);
			}
			rs = pstmt.executeQuery();
			ResultSetMetaData resultMetaData = rs.getMetaData();
			int fieldCount = resultMetaData.getColumnCount();
			while (rs.next()) {
				if (fieldCount == 1) {
					list.add(getObject(rs, 1));
				} else {
					List<Object> l = new ArrayList<Object>();
					for (int j = 1; j <= fieldCount; j++) {
						l.add(getObject(rs, j));
					}
					list.add(l.toArray());
					l = null;
				}
			}
		} catch (Exception e) {
			throw OPUtil.handleException(e);
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
				if (pstmt != null) {
					pstmt.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				throw OPUtil.handleException(e);
			}
		}
		return list;
	}

	private void setObject(PreparedStatement pstmt, int paramIndex,
			Object paramObject) throws SQLException {
		if (paramObject != null && paramObject instanceof Date) {
			paramObject = new Timestamp(((Date) paramObject).getTime());
		}
		pstmt.setObject(paramIndex, paramObject);
	}

	/**
	 * @param rs
	 * @param indexColumn
	 * @return
	 * @throws SQLException
	 * @throws IOException
	 */
	private Object getObject(ResultSet rs, int indexColumn)
			throws SQLException, IOException {
		if (rs != null) {
			Object obj = rs.getObject(indexColumn);
			if (obj != null && obj instanceof Blob) {
				obj = toByteArray(((Blob) obj).getBinaryStream());
			}
			return obj;
		} else {
			return null;
		}
	}

	private byte[] toByteArray(InputStream input) throws IOException {
		ByteArrayOutputStream output = new ByteArrayOutputStream();
		copy(input, output);
		return output.toByteArray();
	}

	private int copy(InputStream input, OutputStream output) throws IOException {
		long count = copyLarge(input, output);
		if (count > Integer.MAX_VALUE) {
			return -1;
		}
		return (int) count;
	}

	private long copyLarge(InputStream input, OutputStream output)
			throws IOException {
		byte[] buffer = new byte[1024 * 4];
		long count = 0;
		int n = 0;
		while (-1 != (n = input.read(buffer))) {
			output.write(buffer, 0, n);
			count += n;
		}
		return count;
	}

	/**
	 * 利用预编译HQL查询，不分页。如果查询不到，返回新的ArrayList，不返回NullPoint。
	 *
	 * @param hql
	 *            HQL语句
	 * @param objects
	 *            变量绑定参数
	 * @return List 返回对象实体List
	 */
	@SuppressWarnings("rawtypes")
	protected List getEntityListNoPageHQL(String hql, Object... objects)
			throws BaseException {
		return getEntityListHQL(hql, -1, 0, objects);
	}

	/**
	 * 利用预编译HQL查询，如果查询不到，返回null，不返回NullPoint。
	 *
	 * @param hql
	 *            HQL语句
	 * @param objects
	 *            变量绑定参数
	 * @return Object 返回对象实体
	 */
	@SuppressWarnings("rawtypes")
	protected Object getEntityForOne(String hql, Object... objects)
			throws BaseException {
		List list = getEntityListHQL(hql, 1, 1, objects);
		if (list.isEmpty()) {
			return null;
		} else {
			return list.get(0);
		}
	}


	/**
	 * 利用预编译HQL查询，如果查询不到，返回null，不返回NullPoint。
	 *
	 * @param sql
	 *            HQL语句
	 * @param objects
	 *            变量绑定参数
	 * @return Object 返回对象实体
	 */
	@SuppressWarnings("rawtypes")
	protected Object getEntityListWithClassSQLForOne(String sql,Class clazz, Object... objects)
			throws BaseException {
		List list = this.getEntityListWithClassSQL(sql,1, 1,clazz,  objects);
		if (list.isEmpty()) {
			return null;
		} else {
			return list.get(0);
		}
	}

	/**
	 * 利用预编译HQL查询，如果查询不到，返回新的ArrayList，不返回NullPoint。
	 *
	 * @param hql
	 *            HQL语句
	 * @param page
	 *            第一页从1开始 ,小于等于0不分页
	 * @param pageSize
	 *            每页显示数量
	 * @param objects
	 *            变量绑定参数
	 * @return List 返回对象实体List
	 */
	@SuppressWarnings("rawtypes")
	protected List getEntityListHQL(String hql, int page, int pageSize,
			Object... objects) throws BaseException {
		try {
			if(logger.isDebugEnabled()){
				logger.debug("retrieveEntityListHQL hql:" + hql);
				logger.debug("参数：page=" + page + ",pageSize=" + pageSize + ",变量="
						+ JsonUtil.beanToJson(objects));
			}
			Query query = getSession().createQuery(hql);
			int i = 0;
			for (Object object : objects) {
				query.setParameter(i++, object);
			}
			if (page > 0) {
				query.setFirstResult(pageSize * (page - 1));
				query.setMaxResults(pageSize);
			}
			query.setCacheable(true);
			query.setCacheRegion("frontpages");
			List result = query.list();
			if (result == null) {
				return EMPTY_LIST;
			} else {
				return result;
			}
		} catch (Exception e) {
			throw OPUtil.handleException(e);
		}
	}

	public Connection getConnection() {
		try {
			return SessionFactoryUtils.getDataSource(getSessionFactory())
					.getConnection();
		} catch (SQLException e) {
			throw new PersistentException("获取链接失败。", e);
		}
	}

	/**
	 * 根据sql获取列表
	 *
	 * @param sql
	 *            sql
	 * @param objects
	 *            变量绑定参数
	 * @return list Object[]对象列表
	 * @throws BaseException
	 */
	protected int execSqlUpdate(String sql, Object... objects)
			throws BaseException {
		if(logger.isDebugEnabled()){
			logger.debug("execSqlUpdate sql:" + sql);
			logger.debug("请求参数：" + JsonUtil.beanToJson(objects));
		}
		Connection conn = null;
		int rs = 0;
		PreparedStatement pstmt = null;
		try {
			conn = getConnection();
			pstmt = conn.prepareStatement(sql);
			int i = 0;
			for (Object object : objects) {
				setObject(pstmt, ++i, object);
			}
			rs = pstmt.executeUpdate();
		} catch (Exception e) {
			logger.error("execSqlUpdate error:"+sql);
			throw OPUtil.handleException(e);
		} finally {
			try {
				if (pstmt != null) {
					pstmt.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				logger.error("execSqlUpdate关闭资源异常", e);
			}
		}
		return rs;
	}

	/**
	 * 通过SQL执行无返回结果的存储过程(仅限于存储过程)
	 *
	 * @param procedureName
	 * @param objects
	 */
	public void executeVoidProcedure(String procedureName, Object... objects) throws BaseException{
		if(logger.isDebugEnabled()){
			logger.debug("executeVoidProcedure procedureName:" + procedureName);
			logger.debug("请求参数：" + JsonUtil.beanToJson(objects));
		}
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			conn = getConnection();
			CallableStatement call = conn.prepareCall(procedureName);
			if (null != objects) {
				for (int i = 0; i < objects.length; i++) {
					call.setObject((i+1), objects[i]);
				}
			}
			call.executeQuery();
		} catch (Exception e) {
			throw OPUtil.handleException(e);
		} finally {
			try {
				if (pstmt != null) {
					pstmt.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				logger.error("executeVoidProcedure关闭资源异常", e);
			}
		}
	}
}
