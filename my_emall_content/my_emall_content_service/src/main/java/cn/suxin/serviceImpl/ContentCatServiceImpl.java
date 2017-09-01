package cn.suxin.serviceImpl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;


import cn.suxin.common.pojo.EasyUITreeNode;
import cn.suxin.common.utils.E3Result;
import cn.suxin.mapper.TbContentCategoryMapper;
import cn.suxin.mapper.TbItemCatMapper;
import cn.suxin.pojo.TbContentCategory;
import cn.suxin.pojo.TbContentCategoryExample;
import cn.suxin.pojo.TbContentCategoryExample.Criteria;
import cn.suxin.service.ContentCatService;

@Service
public class ContentCatServiceImpl implements ContentCatService{

	@Autowired
	private TbContentCategoryMapper tbContentCategoryMapper;
	/**
	 * 分类管理列表显示
	 */
	@Override
	public List<EasyUITreeNode> findContentTreeList(Long parentId) {
		//设置查询条件,根据parentId查找出所有下一级子节点
		TbContentCategoryExample example = new TbContentCategoryExample();
		Criteria criteria = example.createCriteria();
		criteria.andParentIdEqualTo(parentId);
		List<TbContentCategory> list = tbContentCategoryMapper.selectByExample(example);
		
		//创建一个EasyUITreeNode的集合用于返回数据
		ArrayList<EasyUITreeNode> arrayList = new ArrayList<EasyUITreeNode>();
		//根据查询出的数据对EasyUITreeNode集合进行数据封装
		for (TbContentCategory tbContentCategory : list) {
			EasyUITreeNode node = new EasyUITreeNode();
			node.setId(tbContentCategory.getId());
			node.setText(tbContentCategory.getName());
			node.setState(tbContentCategory.getIsParent()?"closed":"open");
			arrayList.add(node);
		}
		
		//返回List<EasyUITreeNode>.
		return arrayList;
	}
	/**
	 * 分类管理,添加节点
	 */
	@Override
	public E3Result createContentCat(Long parentId, String name) {
		//封装节点数据
		Date date = new Date();
		TbContentCategory node = new TbContentCategory();
		node.setId(null);
		node.setIsParent(false);
		node.setName(name);
		node.setStatus(1);
		node.setCreated(date);
		node.setUpdated(date);
		node.setSortOrder(1);
		node.setParentId(parentId);
		
		//调整父节点isParent为true
		TbContentCategory parentNode = new TbContentCategory();
		parentNode.setId(parentId);
		parentNode.setIsParent(true);
		//修改父节点数据
		tbContentCategoryMapper.updateByPrimaryKeySelective(parentNode);
		
		//插入节点数据
		tbContentCategoryMapper.insert(node);
		
		return E3Result.ok();
	}
	/**
	 * 分类管理.删除节点
	 */
	@Override
	public E3Result deleteContentCat(Long id) {
		TbContentCategory node = tbContentCategoryMapper.selectByPrimaryKey(id);
		//修改节点状态 1.正常 2.删除
		node.setStatus(2);
		tbContentCategoryMapper.updateByPrimaryKey(node);
		//查询父节点下的子节点
		Long parentId = node.getParentId();
		TbContentCategoryExample example = new TbContentCategoryExample();
		Criteria criteria = example.createCriteria();
		criteria.andParentIdEqualTo(parentId);
		criteria.andStatusEqualTo(1);
		List<TbContentCategory> list = tbContentCategoryMapper.selectByExample(example);
		//判断父节点如果是叶子节点,是则调整isParent为false
		if(list!=null && list.isEmpty()){
			TbContentCategory parentNode = new TbContentCategory();
			parentNode.setId(parentId);
			parentNode.setIsParent(false);
			//修改父节点数据
			tbContentCategoryMapper.updateByPrimaryKeySelective(parentNode);
		}
		return E3Result.ok();
	}
}
