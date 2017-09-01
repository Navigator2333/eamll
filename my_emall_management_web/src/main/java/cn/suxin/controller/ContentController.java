package cn.suxin.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.suxin.common.utils.E3Result;
import cn.suxin.pojo.TbContent;
import cn.suxin.service.ContentService;


@Controller
public class ContentController {
	@Autowired
	private ContentService contentService;
	
	/**
	 * 展示内容列表
	 * @param categoryId
	 * @param page
	 * @param rows
	 * @return
	 */
	@RequestMapping(value="/content/query/list")
	@ResponseBody
	public Map<String, Object> findContentListPageByCatId(Long categoryId,Integer page,Integer rows) {
		Map<String,Object> map = contentService.findContentListPageByCatId(categoryId,page,rows);
		return map;
	}
	/**
	 * 内容添加功能 
	 * @param content
	 * @return
	 */
	@RequestMapping(value="/content/save")
	@ResponseBody
	public E3Result saveContent(TbContent content) {
		E3Result result = contentService.saveContent(content);
		return result;
		
	}
	/**
	 * 内容管理,删除内容
	 * @param idsStr
	 * @return
	 */
	@RequestMapping(value={"/content/delete"})
	@ResponseBody
	public E3Result deleteContent(@RequestParam(value="ids") String idsStr) {
		List<Long> ids = new ArrayList<>();
		if(StringUtils.isNotBlank(idsStr)){
			String[] idStrArray = idsStr.split(",");
			for (int i = 0; i < idStrArray.length; i++) {
				Long id = Long.valueOf(idStrArray[i]);
				ids.add(id);
			}
			E3Result result = contentService.deleteContent(ids);
			return result;
		}
		return null;
	}
	
	@RequestMapping(value={"/rest/content/edit"})
	@ResponseBody
	public E3Result editContent(TbContent content) {
		E3Result result = contentService.updateContent(content);
		
		return result;
		
	}
}
