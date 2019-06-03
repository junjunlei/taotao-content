package com.taotao.content.service;
/**
 * 内容分类业务逻辑接口
 * @author Junjunlei
 *
 */

import java.util.List;

import com.taotao.common.pojo.EasyUITreeNode;
import com.taotao.common.pojo.TaotaoResult;

public interface ContentCategoryService {
	/**
	 * 通过节点查询id查询该节点子节点列表
	 * 
	 * @param parentId 父节点id
	 * @return
	 */
	public List<EasyUITreeNode> getContentCategoryList(Long parentId);
	/**
	 * 新增内容分类节点
	 * @param parentId  父节点id
	 * @param name  节点名称
	 * @return
	 */
	public TaotaoResult addContentCategory(Long parentId,String name);
	/**
	 * 更新节点信息
	 * @param id  当前节点id
  	 * @param name 节点名称
	 * @return
	 */
	public TaotaoResult updateContentCategory(Long id,String name);
	/**
	 * 删除节点信息
	 * @param id
	 * @return 当前节点id
	 */
	public TaotaoResult deleteContentCategory(Long id);
}
