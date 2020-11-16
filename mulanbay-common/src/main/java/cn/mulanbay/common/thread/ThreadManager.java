package cn.mulanbay.common.thread;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * 线程管理类
 *
 * @see cn.mulanbay.web.servlet.SpringServlet
 * @author fenghong
 * @create 2018-01-20 21:44
 */
public class ThreadManager {

	private static final Logger logger = LoggerFactory.getLogger(ThreadManager.class);

	private List<EnhanceThread> list;

	private static ThreadManager instance = new ThreadManager();

	private ThreadManager() {
		super();
		init();
	}

	public static ThreadManager getInstance() {
		return instance;
	}

	private void init() {
		logger.debug("初始化线程管理类");
		list = Collections.synchronizedList(new LinkedList<EnhanceThread>());
		logger.debug("初始化线程管理类结束");
	}

	public void addThread(EnhanceThread thread) {
		logger.debug("添加一个线程：[" + thread.getThreadName() + "]-" + thread);
		list.add(thread);
		logger.debug("当前系统中线程总数：" + list.size());
	}

	public void removeThread(EnhanceThread thread) {
		logger.debug("移除一个线程：[" + thread.getThreadName() + "]-" + thread);
		list.remove(thread);
		logger.debug("当前系统中线程总数：" + list.size());
	}

	public void stopAll() {
		logger.debug("开始停止所有线程");
		for (EnhanceThread thread : list) {
			thread.stopThread();
		}
		logger.debug("停止所有线程结束");
	}

	public void stop(long id) {
		EnhanceThread thread = this.getThread(id);
		if(thread==null){
			logger.warn("线程id=" + id + "未找到");
			return;
		}else{
			logger.debug("开始停止线程id=" + id);
			thread.stopThread();
			logger.debug("停止线程id=" + id + "结束");
		}
	}

	/**
	 * 寻找thread
	 * @param id
	 * @return
	 */
	public EnhanceThread getThread(long id){
		for (EnhanceThread thread : list) {
			if (thread.getId() == id) {
				return thread;
			}
		}
		return null;
	}

	public long getThreadCount() {
		return list.size();
	}

	public List<EnhanceThread> getThreads(int fromIndex, int toIndex) {
		int l = list.size();
		if (toIndex > l) {
			toIndex = l;
		}
		return list.subList(fromIndex, toIndex);
	}
}
