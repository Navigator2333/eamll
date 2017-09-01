package cn.suxin.service;

import java.util.List;

import cn.suxin.common.pojo.EasyUITreeNode;
import cn.suxin.pojo.TbItemCat;

public interface ItemCatService {

	List<EasyUITreeNode> getCatList(long parentId);

	List<TbItemCat> findTreeNodeByParentId(Long parentId);



}
