package cn.suxin.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.suxin.common.pojo.EasyUITreeNode;
import cn.suxin.pojo.TbItemCat;
import cn.suxin.service.ItemCatService;

@Controller
public class ItemCatController {

	@Autowired
	private ItemCatService itemCatService;
	
	/**
	 * 分类树节点
	 */
	@RequestMapping(value={"/item/cat/list"})
	@ResponseBody
	public List<EasyUITreeNode> getTreeNode(@RequestParam(value="id",defaultValue="0") Long ParentId) {
		List<TbItemCat> list = itemCatService.findTreeNodeByParentId(ParentId);
		
		List<EasyUITreeNode> treeList = new ArrayList<EasyUITreeNode>();
		for (TbItemCat tbItemCat : list) {
			EasyUITreeNode node = new EasyUITreeNode();
			node.setId(tbItemCat.getId());
			node.setText(tbItemCat.getName());
			node.setState(tbItemCat.getIsParent()?"closed":"open");
			treeList.add(node);
		}
		
		return treeList;
		
	}
}
