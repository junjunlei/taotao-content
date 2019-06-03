package com.taotao.content.service;
/**
 * 内容管理业务逻辑接口
 * @author Junjunlei
 *
 */

import com.taotao.common.pojo.EasyUIDataGridResult;

public interface ContentService {
	/**
	 * 根据分类id 查询内容
	 * @param categoryId 分类id
	 * @param page 当前页
	 * @param rows 每页显示记录数
	 * @return
	 */
	public EasyUIDataGridResult getContentList(Long categoryId, Integer page, Integer rows);
}
