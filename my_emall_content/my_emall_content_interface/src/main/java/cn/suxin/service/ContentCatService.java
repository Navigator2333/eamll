package cn.suxin.service;

import java.util.List;

import cn.suxin.common.pojo.EasyUITreeNode;
import cn.suxin.common.utils.E3Result;

public interface ContentCatService {

	List<EasyUITreeNode> findContentTreeList(Long parentId);

	E3Result createContentCat(Long parentId, String name);

	E3Result deleteContentCat(Long id);

}
