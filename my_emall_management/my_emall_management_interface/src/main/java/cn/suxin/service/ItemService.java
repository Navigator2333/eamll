package cn.suxin.service;

import java.util.List;

import cn.suxin.common.pojo.DataGridResult;
import cn.suxin.common.utils.E3Result;
import cn.suxin.pojo.TbItem;

public interface ItemService {

	public TbItem findItemById(Long itemId);

	public DataGridResult getItemList(Integer page, Integer rows);

	public E3Result saveItem(TbItem item, String desc);

	public E3Result deleteItem(String ids);

	public E3Result findDesc(Long id);

	public E3Result updateItem(TbItem item, String desc);

}
