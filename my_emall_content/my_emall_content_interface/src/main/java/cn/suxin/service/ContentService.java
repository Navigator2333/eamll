package cn.suxin.service;

import java.util.List;
import java.util.Map;

import cn.suxin.common.utils.E3Result;
import cn.suxin.pojo.TbContent;

public interface ContentService {

	Map<String, Object> findContentListPageByCatId(Long categoryId, Integer page, Integer rows);

	E3Result saveContent(TbContent content);

	List<TbContent> findContentListByCid(Long indexSilderCid);

	E3Result deleteContent(List<Long> idArray);

	E3Result updateContent(TbContent content);

}
