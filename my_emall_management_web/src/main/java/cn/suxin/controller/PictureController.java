package cn.suxin.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import cn.suxin.common.utils.FastDFSClient;
import cn.suxin.common.utils.JsonUtils;

@Controller
public class PictureController {
	
	@Value("${imageserver.url}")
	private String imageServerUrl;
	
	@RequestMapping(value={"/pic/upload"}, produces=MediaType.TEXT_PLAIN_VALUE + ";charset=utf-8")
	@ResponseBody
	public String name(MultipartFile uploadFile) {
		try {
			// 1）向工程中添加commons-io、commons-fileupload两个jar包。
			// 2）需要在springmvc的配置文件中配置多部件解析器MultiPartFileResolver
			// 3）在Controller的方法中添加一个参数接收上传的文件，参数的名称应该是uploadFile，类型应该是MultiPartFile
			// 4）取文件的原始名称
			String originalFilename = uploadFile.getOriginalFilename();
			// 5）取文件扩展名
			String extName = originalFilename.substring(originalFilename.lastIndexOf(".") + 1);
			// 6）使用FastDFSClient上传文件。得到一个url
			FastDFSClient fastDFSClient = new FastDFSClient("classpath:resource/client.conf");
			//上传文件
			String url = fastDFSClient.uploadFile(uploadFile.getBytes(), extName);
			// 7）把url拼装成一个完整的url
			url = imageServerUrl + url;
			// 8）返回map
			Map result = new HashMap<>();
			result.put("error", 0);
			result.put("url", url);
			return JsonUtils.objectToJson(result);
		} catch (Exception e) {
			e.printStackTrace();
			Map result = new HashMap<>();
			result.put("error", 1);
			result.put("message", "文件上传失败！");
			return JsonUtils.objectToJson(result);
		}
	}
}
