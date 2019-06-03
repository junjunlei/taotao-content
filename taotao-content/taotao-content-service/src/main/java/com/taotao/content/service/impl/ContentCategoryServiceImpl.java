package com.taotao.content.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.taotao.common.pojo.EasyUITreeNode;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.content.service.ContentCategoryService;
import com.taotao.mapper.TbContentCategoryMapper;
import com.taotao.pojo.TbContentCategory;
import com.taotao.pojo.TbContentCategoryExample;
import com.taotao.pojo.TbContentCategoryExample.Criteria;

/**
 * 内容分类业务逻辑实现类
 * 
 * @author Junjunlei
 *
 */
@Service
public class ContentCategoryServiceImpl implements ContentCategoryService {
	@Autowired
	private TbContentCategoryMapper contentCategoryMapper;
	@Override
	public List<EasyUITreeNode> getContentCategoryList(Long parentId) {
       //创建example
		TbContentCategoryExample example=new TbContentCategoryExample();
		//设置条件
		Criteria criteria = example.createCriteria();
		criteria.andParentIdEqualTo(parentId);
		//执行查询
		List<TbContentCategory> list = contentCategoryMapper.selectByExample(example);
		//转成EasyUITreeNode
		List<EasyUITreeNode> resultList=new ArrayList<EasyUITreeNode>();
		EasyUITreeNode node=null;
		for(TbContentCategory contentCategory:list) {
			 node=new EasyUITreeNode();
			 node.setId(contentCategory.getId());
			 node.setText(contentCategory.getName());
			 node.setState(contentCategory.getIsParent()?"closed":"open");
			 //添加到列表
			 resultList.add(node);
		}
		return resultList;
	}
	@Override
	public TaotaoResult addContentCategory(Long parentId, String name) {
		//构建对象 补全属性
		TbContentCategory contentCategory=new TbContentCategory();
		contentCategory.setParentId(parentId);
		contentCategory.setName(name);
		//排序  数字相同则按名称
		contentCategory.setSortOrder(1);
		//状态  1正常  2删除
		contentCategory.setStatus(1);
		contentCategory.setCreated(new Date());
		contentCategory.setUpdated(contentCategory.getCreated());
		//是否为父节点
		contentCategory.setIsParent(false);
		//执行插入操作
		contentCategoryMapper.insert(contentCategory);
		//判断父节点是否为父节点
		TbContentCategory parentNode = contentCategoryMapper.selectByPrimaryKey(parentId);
		if(!parentNode.getIsParent()) {
			parentNode.setIsParent(true);
			contentCategoryMapper.updateByPrimaryKey(parentNode);
		}
		return TaotaoResult.ok(contentCategory);
	}
	@Override
	public TaotaoResult updateContentCategory(Long id, String name) {
		//先查询
		TbContentCategory contentCategory = contentCategoryMapper.selectByPrimaryKey(id);
		//再更新
		contentCategory.setName(name);
		contentCategory.setUpdated(new Date());
		contentCategoryMapper.updateByPrimaryKey(contentCategory);
		return TaotaoResult.ok(contentCategory);
	}
	@Override
	public TaotaoResult deleteContentCategory(Long id) {
		TbContentCategory contentCategory = contentCategoryMapper.selectByPrimaryKey(id);
		
		//如果是父节点
		if(contentCategory.getIsParent()) {
			//不允许删除
			TaotaoResult taotaoResult=new TaotaoResult();
			taotaoResult.setStatus(201);
			return taotaoResult;
		}else {//不是父节点	
			//执行删除
			contentCategoryMapper.deleteByPrimaryKey(id);
		    //创建example
			TbContentCategoryExample example=new TbContentCategoryExample();
			//设置条件
			Criteria criteria = example.createCriteria();
			criteria.andParentIdEqualTo(contentCategory.getParentId());
			//执行查询
			List<TbContentCategory> list = contentCategoryMapper.selectByExample(example);
			if(list.size()==0) {//判断父节点是否还有子节点
				//获取父节点信息
				TbContentCategory categoryParent = contentCategoryMapper.selectByPrimaryKey(contentCategory.getParentId());
				//修改为不是父节点
				categoryParent.setIsParent(false);
				//执行更新操作
				contentCategoryMapper.updateByPrimaryKey(categoryParent);
			}
			return TaotaoResult.ok(contentCategory);
		}
	}

}
