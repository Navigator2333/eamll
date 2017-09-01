package cn.suxin.serviceimpl;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import cn.suxin.common.pojo.DataGridResult;
import cn.suxin.common.utils.E3Result;
import cn.suxin.common.utils.IDUtils;
import cn.suxin.mapper.TbItemDescMapper;
import cn.suxin.mapper.TbItemMapper;
import cn.suxin.pojo.TbItem;
import cn.suxin.pojo.TbItemDesc;
import cn.suxin.pojo.TbItemExample;
import cn.suxin.pojo.TbItemExample.Criteria;
import cn.suxin.service.ItemService;

@Service
public class ItemServiceImpl implements ItemService {

	@Autowired
	private TbItemMapper tbItemMapper;
	
	@Autowired
	private TbItemDescMapper tbItemDescMapper;
	
	/**
	 * 测试用,根据id查询商品
	 */
	@Override
	public TbItem findItemById(Long itemId) {
		TbItemExample example = new TbItemExample();
		Criteria criteria = example.createCriteria();
		criteria.andIdEqualTo(itemId);
		
		List<TbItem> list = tbItemMapper.selectByExample(example);
		if (list != null && list.size() >0) {
			return list.get(0);
		}
		return null;
	}

	/**
	 * 后台商品列表分页显示
	 */
	@Override
	public DataGridResult getItemList(Integer pageNumber, Integer pageSize) {
		//设置分页信息,获取第1页，30条内容，默认查询总数count
		PageHelper.startPage(pageNumber, pageSize);
		
		//紧跟着的第一个select方法会被分页
		TbItemExample example = new TbItemExample();
		Criteria criteria = example.createCriteria();
		List<TbItem> list = tbItemMapper.selectByExample(example);
		
		//获得分页信息
		PageInfo<TbItem> pageInfo = new PageInfo<>(list);
		
		//封装数据
		DataGridResult result = new DataGridResult();
		result.setTotal(pageInfo.getTotal());
		result.setRows(list);
		
		return result;
	}
	/**
	 * 保存商品信息
	 * @return 
	 */
	@Override
	public E3Result saveItem(TbItem item, String desc) {
		Date date = new Date();
		long id = IDUtils.genItemId();
		item.setId(id);
		item.setCreated(date);
		item.setUpdated(date);
		item.setStatus((byte) 1);
		tbItemMapper.insert(item);
		
		TbItemDesc itemDesc = new TbItemDesc();
		itemDesc.setCreated(date);
		itemDesc.setUpdated(date);
		itemDesc.setItemDesc(desc);
		itemDesc.setItemId(id);
		tbItemDescMapper.insert(itemDesc);
		
		return E3Result.ok();
		
		
	}

	/**
	 * 删除商品信息
	 */
	@Override
	public E3Result deleteItem(String ids) {
		//判断非空
		if(ids!=null && StringUtils.isNotBlank(ids)){
			//把字符串分割成字符串形式的ID数组
			String[] idArray = ids.split(",");
			//遍历数组删除
			for (String idStr : idArray) {
				//将字符串转成Long
				Long id = Long.valueOf(idStr);
				//设置删除条件
				tbItemMapper.deleteByPrimaryKey(id);
				tbItemDescMapper.deleteByPrimaryKey(id);
			}
			
			return E3Result.ok();
		}
		return null;
	}

	
	/**
	 * 编辑窗口,异步加载商品描述
	 */
	@Override
	public E3Result findDesc(Long id) {
		TbItemDesc itemDesc = tbItemDescMapper.selectByPrimaryKey(id);
		
		E3Result result = E3Result.ok(itemDesc);
		
		return result;
	}

	/**
	 * 编辑商品后,提交保存
	 * @param id
	 * @return
	 */
	@Override
	public E3Result updateItem(TbItem item, String desc) {
		//确定当前时间
		Date date = new Date();
		Long itemId = item.getId();
		//封装描述数据
		TbItemDesc itemDesc = tbItemDescMapper.selectByPrimaryKey(itemId);
		itemDesc.setUpdated(date);
		itemDesc.setItemDesc(desc);
		itemDesc.setItemId(itemId);
		//封装商品信息
		TbItem itemInDB = tbItemMapper.selectByPrimaryKey(itemId);
		item.setStatus(itemInDB.getStatus());
		item.setCreated(itemInDB.getCreated());
		item.setUpdated(date);
		//执行更新操作
		tbItemDescMapper.updateByPrimaryKey(itemDesc);
		tbItemMapper.updateByPrimaryKey(item);
		//返回成功信息
		return E3Result.ok();
	}
}
