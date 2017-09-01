package cn.suxin.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.suxin.common.pojo.DataGridResult;
import cn.suxin.common.utils.E3Result;
import cn.suxin.pojo.TbItem;
import cn.suxin.service.ItemService;

@Controller
public class ItemController {
	@Autowired
	private ItemService itemService;
	/**
	 * 根据ID获取商品信息(测试)
	 * @param itemId
	 * @return
	 */
	@RequestMapping("/item/{itemId}")
	@ResponseBody
	public TbItem findItemById(@PathVariable Long itemId) {
		TbItem item = itemService.findItemById(itemId);
		return item;
	}
	
	/**
	 * 查看带分页的商品列表
	 * @param page
	 * @param rows
	 * @return
	 */
	@RequestMapping("/item/list")
	@ResponseBody
	public DataGridResult getItemList(Integer page, Integer rows) {
		DataGridResult result = itemService.getItemList(page, rows);
		return result;
	}
	
	/**
	 * 添加商品信息
	 * @param page
	 * @param rows
	 * @return
	 */
	@RequestMapping(value="/item/save",method=RequestMethod.POST)
	@ResponseBody
	public E3Result saveItem(TbItem item,String desc) {
		E3Result result = itemService.saveItem(item,desc);
		
		return result;
	}
	/**
	 * 删除商品信息
	 * @param item
	 * @param desc
	 * @return
	 */
	@RequestMapping(value="/rest/item/delete",method=RequestMethod.POST)
	@ResponseBody
	public E3Result deleteItem(String ids) {
		E3Result result = itemService.deleteItem(ids);
		
		return result;
	}
	
	/**
	 * 编辑窗口,异步加载商品描述
	 * @param ids
	 * @return
	 */
	@RequestMapping(value="/rest/item/query/item/desc/{idStr}")
	@ResponseBody
	public E3Result findDesc(@PathVariable String idStr) {
		Long id = Long.valueOf(idStr);
		
		E3Result result = itemService.findDesc(id);
		
		return result;
	}
	/**
	 * 编辑商品后,提交保存
	 * @param idStr
	 * @return
	 */
	@RequestMapping(value="/rest/item/update")
	@ResponseBody
	public E3Result updateItem(TbItem item,String desc) {
		
		E3Result result = itemService.updateItem(item,desc);
		
		
		return result;
	}
	
	
	
}
