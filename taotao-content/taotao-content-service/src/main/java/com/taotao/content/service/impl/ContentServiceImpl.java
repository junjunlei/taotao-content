package com.taotao.content.service.impl;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.taotao.common.pojo.EasyUIDataGridResult;
import com.taotao.content.service.ContentService;
import com.taotao.mapper.TbContentMapper;
import com.taotao.pojo.TbContent;
import com.taotao.pojo.TbContentExample;
import com.taotao.pojo.TbContentExample.Criteria;
@Service
public class ContentServiceImpl implements ContentService {
	@Autowired
	private TbContentMapper contentMapper;

	@Override
	public EasyUIDataGridResult getContentList(Long categoryId, Integer page, Integer rows) {
		//设置分页信息
		PageHelper.startPage(page, rows);
		//查询数据
		TbContentExample example=new TbContentExample();
		Criteria criteria = example.createCriteria();
		criteria.andCategoryIdEqualTo(categoryId);
		List<TbContent> list = contentMapper.selectByExample(example);
		//获取分页信息
		PageInfo<TbContent> pageInfo=new PageInfo<TbContent>(list);
		EasyUIDataGridResult result=new EasyUIDataGridResult();
		result.setTotal((int)pageInfo.getTotal());
		result.setRows(list);
		return result;
	}


}
