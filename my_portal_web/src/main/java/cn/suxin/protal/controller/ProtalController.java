package cn.suxin.protal.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.suxin.pojo.TbContent;
import cn.suxin.service.ContentService;

@Controller
public class ProtalController {
	
	@Value("${index.silder.cid}")
	private Long indexSilderCid;

	@Autowired
	private ContentService contentService;
	
	@RequestMapping(value={"index"})
	public String showIndex(Model mod) {
		List<TbContent> list = contentService.findContentListByCid(indexSilderCid);
		
		mod.addAttribute("ad1List",list);
		
		return "index";
		
	}
	
	
}
