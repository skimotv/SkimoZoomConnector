package tv.skimo.connector.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import tv.skimo.connector.lib.JGet;
import tv.skimo.connector.lib.StorageFileNotFoundException;
import tv.skimo.connector.services.StorageService;

import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;


@Controller
public class SkimoConnectorController {

	private final StorageService storageService;
	
    private static final Logger log=LoggerFactory.getLogger(SkimoConnectorController.class);
	

	@Autowired
	public SkimoConnectorController(StorageService storageService)
	{
		this.storageService = storageService;
	}

	@GetMapping("/myskimo")
	public String listUploadedFiles(Model model) throws IOException 
	{
		List<String> filesList =  storageService.loadAll().map(
				path -> MvcUriComponentsBuilder.fromMethodName(SkimoConnectorController.class,
						"serveFile", path.getFileName().toString()).build().toUri().toString())
				.collect(Collectors.toList());

	    ArrayList<String> urlList = new ArrayList<String>();
		for (int i = 0; i < filesList.size(); i++)
		{
			String st = filesList.get(i);
			String st2 = st.replace("files", "upload-dir");
			urlList.add(st2);
		}
		if(urlList.size() ==0)
		{
			filesList = new ArrayList<String>();
			urlList = new ArrayList<String>();
			filesList.add("No entries");
			urlList.add("No entries");
			model.addAttribute("files",filesList);
			model.addAttribute("urls",urlList);
		}
		else
		{
			model.addAttribute("files",filesList);
			model.addAttribute("urls",urlList);
		}
		return "uploadForm";
	}
	
	public OAuth2User getCurrentUser() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		return ((OAuth2AuthenticationToken)auth).getPrincipal();
	}


	@GetMapping("/upload-dir/{filename:.+}")
	@ResponseBody
	public ResponseEntity<Resource> serveFile(@PathVariable String filename) 
	{ 
		Resource file = storageService.loadAsResource(filename);
		return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,
				"attachment; filename=\"" + file.getFilename() + "\"").body(file);
	}

	@PostMapping("/myskimo")
	@ResponseBody
	public String handleFileUpload(@RequestParam("file") MultipartFile file,
			RedirectAttributes redirectAttributes) 
	{
		OAuth2User user = getCurrentUser();
		String email = (String) user.getAttributes().get("email");
	    log.info("email is " + email);
	      
		String accName = "/";
		
		storageService.store(file,accName);

		String retVal = "myskimo";
		return(retVal);			
	}

	@PostMapping("/getAsset")
	@ResponseBody
	public ModelAndView getAsset(@RequestParam Map<String,String> reqPar) 
	{		
		String url = reqPar.get("recording");
		
		log.info("URL is " + url);
		JGet.go(url, "upload-dir/source.mp4", log);

		return new ModelAndView("redirect:/myskimo");
	}

	@ExceptionHandler(StorageFileNotFoundException.class)
	public ResponseEntity<?> handleStorageFileNotFound(StorageFileNotFoundException exc) 
	{
		return ResponseEntity.notFound().build();
	}

}
