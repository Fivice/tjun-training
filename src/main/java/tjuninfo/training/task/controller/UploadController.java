package tjuninfo.training.task.controller;

import com.google.common.collect.Maps;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import tjuninfo.training.support.controller.BaseController;
import tjuninfo.training.task.constant.CommonReturnCode;
import tjuninfo.training.task.result.CmsResult;
import tjuninfo.training.task.util.DateUtils;
import tjuninfo.training.task.util.RandomUtils;
import tjuninfo.training.task.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.*;

/*
 * 资产管理控制器
 * @author
 * @date 2018年6月19日
 */
@Controller
@RequestMapping(value = "/upload")
public class UploadController extends BaseController {

	private String savePath;// 数据库存储路径
	private String uploadPath;// 附件的实际存储的绝对路径


	/**
	 *根据路径删除指定的目录或文件，无论存在与否
	 *@param fileUrl  要删除的目录或文件
	 *@return 删除成功返回 true，否则返回 false。
	 */
	@RequestMapping(value = "deleteFile.action", method = RequestMethod.POST)
	@ResponseBody
	public Object DeleteFolder(String fileUrl) throws IOException {
		String sPath=null;
		if(fileUrl==null || fileUrl.equals("")){
			sPath=uploadPath;
		}else{
			String image = fileUrl.substring(fileUrl.lastIndexOf("/")+1);
			sPath=uploadPath+"\\"+image;
		}
		File file = new File(sPath);
		// 判断目录或文件是否存在
		if (!file.exists()) {  // 不存在返回 false
			return new CmsResult(CommonReturnCode.SUCCESS, 1);
		} else {
			// 判断是否为文件
			if (file.isFile()) {  // 为文件时调用删除文件方法
				deleteFile(sPath);
			} else {  // 为目录时调用删除目录方法
				deleteDirectory(sPath);
			}
		}
		return new CmsResult(CommonReturnCode.SUCCESS, 1);
	}

	/**
	 * 删除单个文件
	 * @param   sPath    被删除文件的文件名
	 * @return 单个文件删除成功返回true，否则返回false
	 */
	public boolean deleteFile(String sPath) {
		boolean flag = false;
		File file = new File(sPath);
		// 路径为文件且不为空则进行删除
		if (file.isFile() && file.exists()) {
			file.delete();
			flag = true;
		}
		return flag;
	}

	/**
	 * 删除目录（文件夹）以及目录下的文件
	 * @param   sPath 被删除目录的文件路径
	 * @return  目录删除成功返回true，否则返回false
	 */
	public boolean deleteDirectory(String sPath) {
		//如果sPath不以文件分隔符结尾，自动添加文件分隔符
		if (!sPath.endsWith(File.separator)) {
			sPath = sPath + File.separator;
		}
		File dirFile = new File(sPath);
		//如果dir对应的文件不存在，或者不是一个目录，则退出
		if (!dirFile.exists() || !dirFile.isDirectory()) {
			return false;
		}
		boolean flag = true;
		//删除文件夹下的所有文件(包括子目录)
		File[] files = dirFile.listFiles();
		for (int i = 0; i < files.length; i++) {
			//删除子文件
			if (files[i].isFile()) {
				flag = deleteFile(files[i].getAbsolutePath());
				if (!flag) break;
			} //删除子目录
			else {
				flag = deleteDirectory(files[i].getAbsolutePath());
				if (!flag) break;
			}
		}
		if (!flag) return false;
		//删除当前目录
		if (dirFile.delete()) {
			return true;
		} else {
			return false;
		}
	}

	public String getFileName(String url) throws IOException {
		String filename = "";
		boolean isok = false;
		// 从UrlConnection中获取文件名称
		try {
			URL myURL = new URL(url);
			URLConnection conn = myURL.openConnection();
			if (conn == null) {
				return null;
			}
			Map<String, List<String>> hf = conn.getHeaderFields();
			if (hf == null) {
				return null;
			}
			Set<String> key = hf.keySet();
			if (key == null) {
				return null;
			}
			for (String skey : key) {
				List<String> values = hf.get(skey);
				for (String value : values) {
					String result;
					try {
						result = new String(value.getBytes("ISO-8859-1"), "GBK");
						int location = result.indexOf("filename");
						if (location >= 0) {
							result = result.substring(location
									+ "filename".length());
							filename = result
									.substring(result.indexOf("=") + 1);
							isok = true;
						}
					} catch (UnsupportedEncodingException e) {
						e.printStackTrace();
					}// ISO-8859-1 UTF-8 gb2312
				}
				if (isok) {
					break;
				}
			}
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return filename;
	}

	/**
	 *
	 * @param request
	 * @param file
	 * @return
	 */
	@RequestMapping(value = "easyUpload", method = RequestMethod.POST)
	@ResponseBody
	public static String easyUpload(HttpServletRequest request,MultipartFile file) {
		String url="";
		String name="";
		String basePath=request.getSession().getServletContext().getRealPath("/");
		String path="/upload/"+DateUtils.formatDateTime2(new Date())+"/";
		String uploadPath=basePath+path;
		File up = new File(uploadPath);
		if (!up.exists()) {
			up.mkdirs();
		}
		String fileName=file.getOriginalFilename();
		name = file.getOriginalFilename();
		/*url = basePath+path+fileName;*/
		url = basePath+path+fileName;
		File dfile = new File(url);
		try {
			file.transferTo(dfile);
		} catch (IOException e) {
			e.printStackTrace();
		}
		/*return path+fileName;*/
		return path+fileName;

	}

	/**
	 *
	 * @param request
	 * @param file
	 * @return
	 */
	@RequestMapping(value = "fileUpload", method = RequestMethod.POST)
	@ResponseBody
	public static Object fileUpload(HttpServletRequest request,MultipartFile file) {
		String url="";
		String name="";
		/*String basePath=request.getSession().getServletContext().getRealPath("/");*/
		/*String path="/upload/"+DateUtils.formatDateTime2(new Date())+"/";*/
		String path="/upload/studentInfo/";
		/*String uploadPath=basePath+path;*/
		String uploadPath=path;
		File up = new File(uploadPath);
		if (!up.exists()) {
			up.mkdirs();
		}

		/*String fileName=file.getOriginalFilename();*/
		String ext = StringUtils.getExt(file.getOriginalFilename());
		String fileName = String.valueOf(System.currentTimeMillis()).concat("_").concat(RandomUtils.getRandom(6)).concat(".").concat(ext);
		url = path+fileName;
		File dfile = new File(url);
		try {
			file.transferTo(dfile);
		} catch (IOException e) {
			e.printStackTrace();
		}
		String fName=path+fileName;
		return fName;

	}


	/**
	 * 删除单个文件
	 * @param   sPath    被删除文件的文件名
	 * @return 单个文件删除成功返回true，否则返回false
	 */
	public boolean deleteUploadFile(String sPath) {
		boolean flag = false;
		/*File file = new File(sPath);
		// 路径为文件且不为空则进行删除
		if (file.isFile() && file.exists()) {
			file.delete();
			flag = true;
		}*/
		return flag;
	}

	/**
	 * 测试
	 * @param request
	 * @param response
	 * @param files
	 */
	@RequestMapping(value = "uploadFiles", method = RequestMethod.POST)
	@ResponseBody
	public Object multipleCommentImageUpload(HttpServletRequest request, HttpServletResponse response, @RequestParam(value = "file", required = false)List<MultipartFile> files) {
		response.setContentType("text/html;charset=utf-8");
		String url="";
		String name="";
//		String basePath=request.getSession().getServletContext().getRealPath("/");
		String basePP="";
//		System.out.println("==============basePath="+basePath);
		String path="/upload/classInfo/";
//		String uploadPath=basePath+path;
		String uploadPath=path;
		File up = new File(uploadPath);
		if (!up.exists()) {
			up.mkdirs();
		}
		List<String> list = new ArrayList();

		String fileName="";
		String urlName="";
		for (MultipartFile file:files) {
			Map<String, Object> map = Maps.newHashMap();
			fileName=file.getOriginalFilename();
//			url = basePath+path+fileName;
			url = path+fileName;
			File dfile = new File(url);
			try {
				file.transferTo(dfile);
			} catch (IOException e) {
				e.printStackTrace();
			}
			String fName=path+fileName;
			list.add(fName);
		}


		return list;
	}


}
