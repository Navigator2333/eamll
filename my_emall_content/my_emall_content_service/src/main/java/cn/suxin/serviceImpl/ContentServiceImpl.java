package cn.suxin.serviceImpl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import cn.suxin.common.utils.E3Result;
import cn.suxin.common.utils.JedisClient;
import cn.suxin.common.utils.JsonUtils;
import cn.suxin.mapper.TbContentMapper;
import cn.suxin.pojo.TbContent;
import cn.suxin.pojo.TbContentExample;
import cn.suxin.pojo.TbContentExample.Criteria;
import cn.suxin.service.ContentService;

@Service
public class ContentServiceImpl implements ContentService{

	@Autowired
	private TbContentMapper tbContentMapper;
	
	@Autowired
	private JedisClient jedisClient;
	/**
	 * 内容管理,显示带分页的内容信息
	 */
	@Override
	public Map<String, Object> findContentListPageByCatId(Long categoryId, Integer page, Integer rows) {
		PageHelper.startPage(page, rows);
		
		TbContentExample example = new TbContentExample();
		Criteria criteria = example.createCriteria();
		criteria.andCategoryIdEqualTo(categoryId);
		List<TbContent> list = tbContentMapper.selectByExample(example);
		
		PageInfo<TbContent> info = new PageInfo<>(list);
		
		long total = info.getTotal();
		
		HashMap<String, Object> map = new HashMap<>();
		map.put("total", total);
		map.put("rows", list);
		
		return map;
	}
	
	
	
	
	
	/**
	 * 前台页面,显示内容信息
	 */
	@Override
	public List<TbContent> findContentListByCid(Long indexSilderCid) {
		//查询缓存
		try {
			String json = jedisClient.hget("content-info", indexSilderCid + "");
			//判断是否有结果
			if (StringUtils.isNotBlank(json)) {
				List<TbContent> list = JsonUtils.jsonToList(json, TbContent.class);
				return list;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		TbContentExample example = new TbContentExample();
		Criteria criteria = example.createCriteria();
		criteria.andCategoryIdEqualTo(indexSilderCid);
		List<TbContent> list = tbContentMapper.selectByExample(example);
		
		//把结果添加到缓存
		try {
			jedisClient.hset("content-info", indexSilderCid + "", JsonUtils.objectToJson(list));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	
	/**
	 * 删除内容
	 */
	@Override
	public E3Result deleteContent(List<Long> ids) {
		TbContentExample example = new TbContentExample();
		Criteria criteria = example.createCriteria();
		criteria.andIdIn(ids);
		int i = tbContentMapper.deleteByExample(example);
		
		for (Long id : ids) {
			jedisClient.del("content-info");
		}
		return E3Result.ok();
	}
	/**
	 * 编辑内容
	 */
	@Override
	public E3Result updateContent(TbContent content) {
		int i = tbContentMapper.updateByPrimaryKeySelective(content);
		if(i==1){
			//增删改操作后的缓存同步
			jedisClient.hdel("content-info", content.getCategoryId().toString());
			return E3Result.ok();
		}
		return null;
	}
	
	/**
	 * 保存内容信息
	 */
	@Override
	public E3Result saveContent(TbContent content) {
		int insert = tbContentMapper.insert(content);
		if(insert==1){
			//增删改操作后的缓存同步
			jedisClient.hdel("content-info", content.getCategoryId().toString());
			return E3Result.ok();
		}
		return null;
	}
	
	
}
