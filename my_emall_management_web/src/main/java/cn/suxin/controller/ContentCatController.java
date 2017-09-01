package cn.suxin.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.suxin.common.pojo.EasyUITreeNode;
import cn.suxin.common.utils.E3Result;
import cn.suxin.service.ContentCatService;

@Controller
public class ContentCatController {
	
	@Autowired
	private ContentCatService contentCatService;

	/**
	 * 分类管理列表显示
	 * @param ParentId
	 * @return
	 */
	@RequestMapping(value = { "/content/category/list" })
	@ResponseBody
	public List<EasyUITreeNode> findContentTreeList(@RequestParam(value="id",defaultValue="0") Long ParentId) {
		
		List<EasyUITreeNode> list = contentCatService.findContentTreeList(ParentId);
		
		return list;

	}
	/**
	 * 分类管理分类添加
	 * @param parentId
	 * @param name
	 * @return
	 */
	@RequestMapping(value = { "/content/category/create" })
	@ResponseBody
	public E3Result createContentCat(Long parentId ,String name) {
		
		E3Result result = contentCatService.createContentCat(parentId,name);
		
		return result;
		
	}
	
	@RequestMapping(value = { "/content/category/delete/" })
	@ResponseBody
	public E3Result deleteContentCat(Long id) {
		
		E3Result result = contentCatService.deleteContentCat(id);
		
		return result;
		
	}
	
	
	
}
