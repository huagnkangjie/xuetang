package com.ziyue.xuetang.common.page;

import java.util.List;

public class Pagination<T> extends SimplePage implements java.io.Serializable, Paginable {
 
	public static final int DEF_PAGE = 1;
	
	public Pagination() {
	}

	/**
	 * 构造器
	 * 
	 * @param pageNo
	 *            页码
	 * @param pageSize
	 *            每页几条数据
	 * @param totalCount
	 *            总共几条数据
	 */
	public Pagination(int pageNo, int pageSize, int totalCount) {
		super(pageNo, pageSize, totalCount);
	}

	/**
	 * 构造器
	 * 
	 * @param pageNo
	 *            页码
	 * @param pageSize
	 *            每页几条数据
	 * @param totalCount
	 *            总共几条数据
	 * @param list
	 *            分页内容
	 */
	public Pagination(int pageNo, int pageSize, int totalCount, List<T> list) {
		super(pageNo, pageSize, totalCount);
		this.list = list;
	}

	/**
	 * 第一条数据位置
	 * 
	 * @return
	 */
	public int getFirstResult() {
		return (pageNo - 1) * pageSize;
	}

	/**
	 * 当前页的数据
	 */
	private List<T> list;

	/**
	 * 获得分页内容
	 * 
	 * @return
	 */
	public List<T> getList() {
		return list;
	}

	/**
	 * 设置分页内容
	 * 
	 * @param list
	 */
	@SuppressWarnings("unchecked")
	public void setList(List<T> list) {
		this.list = list;
	}



	/**
	 * 开始记录
	 * 
	 * @param pageNum
	 * @param pageSize
	 * @return
	 */
	public static int pageStart(int pageNo, int pageSize) {

		if (pageNo == 0)
			pageNo = 1;

		if (pageSize == 0)
			pageSize = DEF_COUNT;

		int pageStart = (pageNo - 1) * pageSize;

		return pageStart;
	}

	/**
	 * 总页数
	 * 
	 * @param totalCount
	 * @param pageSize
	 * @return
	 */
	public static int totalPage(int totalCount, int pageSize) {
		int totalPage = totalCount / pageSize;
		if (totalPage == 0 || totalCount % pageSize != 0) {
			totalPage++;
		}
		return totalPage;

	}

	static void main(String[] args) {
		// System.out.println(calcPageCount("0","5"));

		System.out.println(totalPage(6, 5));

	}
}
