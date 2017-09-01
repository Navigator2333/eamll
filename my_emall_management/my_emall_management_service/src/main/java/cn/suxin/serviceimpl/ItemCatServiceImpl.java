package cn.suxin.serviceimpl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.suxin.common.pojo.EasyUITreeNode;
import cn.suxin.mapper.TbItemCatMapper;
import cn.suxin.pojo.TbItemCat;
import cn.suxin.pojo.TbItemCatExample;
import cn.suxin.pojo.TbItemCatExample.Criteria;
import cn.suxin.service.ItemCatService;

@Service
public class ItemCatServiceImpl implements ItemCatService {

	@Autowired
	private TbItemCatMapper itemCatMapper;
	
	/**
	 * 商品列表分页显示
	 */
	@Override
	public List<EasyUITreeNode> getCatList(long parentId) {
		// 1、根据parentId查询节点列表
		TbItemCatExample example = new TbItemCatExample();
		//设置查询条件
		Criteria criteria = example.createCriteria();
		criteria.andParentIdEqualTo(parentId);
		List<TbItemCat> list = itemCatMapper.selectByExample(example);
		// 2、转换成EasyUITreeNode列表。
		List<EasyUITreeNode> resultList = new ArrayList<>();
		for (TbItemCat tbItemCat : list) {
			EasyUITreeNode node = new EasyUITreeNode();
			node.setId(tbItemCat.getId());
			node.setText(tbItemCat.getName());
			node.setState(tbItemCat.getIsParent()?"closed":"open");
			//添加到列表
			resultList.add(node);
		}
		// 3、返回。
		return resultList;
	}

	/**
	 * 商品分类树显示
	 */
	@Override
	public List<TbItemCat> findTreeNodeByParentId(Long parentId) {
		
		TbItemCatExample example = new TbItemCatExample();
		Criteria criteria = example.createCriteria();
		criteria.andParentIdEqualTo(parentId);
		
		List<TbItemCat> list = itemCatMapper.selectByExample(example);
		
		return list;
	}

}
