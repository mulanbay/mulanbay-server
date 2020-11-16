package cn.mulanbay.common.queue;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

/**
 * 定长队列
 * @author fenghong
 * @create 2018-01-20 21:44
 */
public class LimitQueue<E> implements Serializable {

    private static final long serialVersionUID = 3862735601034777594L;

    private int limitedSize;

    private LinkedList<E> linkedList = new LinkedList<>();

    public LimitQueue(int size) {
        this.limitedSize = size;
    }

    public void offer(E e) {
        if (linkedList.size() >= limitedSize) {
            linkedList.poll();
        }
        linkedList.offer(e);
    }

    public E get(int position) {
        return linkedList.get(position);
    }

    public E getLast() {
        return linkedList.getLast();
    }

    public E getFirst() {
        return linkedList.getFirst();
    }

    public int getLimit() {
        return limitedSize;
    }

    public void setLimitedSize(int size) {
        this.limitedSize = size;
    }

    public int size() {
        return linkedList.size();
    }

    /**
     * 获取list数据
     * @return
     */
    public List<E> getList(){
        return linkedList;
    }

    @Override
    public String toString() {
        StringBuilder buffer = new StringBuilder();
        for (int i = 0; i < linkedList.size(); i++) {
            buffer.append(linkedList.get(i));
            buffer.append(" ");
        }
        return buffer.toString();
    }

}
