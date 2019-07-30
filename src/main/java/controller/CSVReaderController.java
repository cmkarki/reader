package controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/")
public class CSVReaderController {

	@Value("${upload.path}")
	private String path;

	@RequestMapping(method = RequestMethod.GET)
	public String getPage(Model model) {
		System.out.print("inside controller");
		return "csv/index";
	}

//	@RequestMapping(value = "/upload", method = RequestMethod.POST)
//	public String getContent(@RequestParam("file") MultipartFile multipartFile, RedirectAttributes redirectAttributes, Model model) {
//
//		if (multipartFile.isEmpty()) {
//			redirectAttributes.addFlashAttribute("errorMessage","no file");
//			return "redirect: csv-import";
//		}
//		String extension = FilenameUtils.getExtension(multipartFile.getOriginalFilename());
//		if (!("xlsx".equals(extension) || ("xls".equals(extension)))) {
//			redirectAttributes.addFlashAttribute("errorMessage","file not supported");
//			return "redirect: csv-import";
//		}
//		try {
//			String destination = path + File.separator + "client-import" + File.separator;
//			String fileName = "abc";
//			multipartFile.transferTo(new File(destination + fileName));
//			redirectAttributes.addFlashAttribute("fileName", fileName);
//			return "redirect:csv-import/read";
//		} catch (Exception e) {
//			redirectAttributes.addFlashAttribute("errorMessage","something went wrong");
//			return "redirect: csv-import";
//		}
//		return null;
//	}
//
//	@RequestMapping(method = RequestMethod.GET, value = "/read")
//	public String read(RedirectAttributes redirectAttributes, Model model) throws IOException {
//		if (!model.containsAttribute("fileName")) {
//			redirectAttributes.addFlashAttribute("errorMessage","no file");
//			return "redirect: csv-import";
//		}
//		Map<String, Object> modelMap = model.asMap();
//		String fileName = (String) modelMap.get("fileName");
//		if (fileName == null) {
//			redirectAttributes.addFlashAttribute("errorMessage","no file");
//			return "redirect:csv-import";
//		}
//		String extension = FilenameUtils.getExtension(fileName);
//		if (!("xlsx".equals(extension) || ("xls".equals(extension)))) {
//			redirectAttributes.addFlashAttribute("errorMessage", "file format not supported")
//			return "redirect:csv-import";
//		}
//		String destination = path + File.separator + "client-import" + File.separator;
//		Map<Integer, List<MyCell>> datas = excelPOIHelper.readExcel(destination + fileName);
//		model.addAttribute("datas", datas);
//		model.addAttribute("fileName", fileName);
//		return "csv/read";
//	}
}
