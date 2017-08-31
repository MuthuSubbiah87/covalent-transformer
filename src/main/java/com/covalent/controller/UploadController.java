package com.covalent.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.covalent.models.FileModel;
import com.covalent.service.FilesProcessService;
import com.covalent.service.WSPusher;

import java.io.IOException;
import java.util.concurrent.CompletableFuture;

@Controller
public class UploadController {
	
	private final FilesProcessService filesProcessService;

	@Autowired
	UploadController(FilesProcessService service) {
		this.filesProcessService = service;
	}
	

	@GetMapping("/")
	public String index() {
		return "upload";
	}
//	
//	@GetMapping("/uploadStatus")
//	public String uploadStatus() {
//		return "uplodStatus";
//	}
	
	@PostMapping("/upload")
	public String singleFileUpload(@RequestParam("file") MultipartFile file, RedirectAttributes redirectAttributes) throws InterruptedException {
		if (file.isEmpty()) {
			redirectAttributes.addFlashAttribute("message", "Please select a file to upload");
			return "redirect:/status";
		}
		try {
			CompletableFuture<Iterable<FileModel>> future = filesProcessService.processTheFile(file);
			future.thenApply(this::websocketNotify);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "redirect:/status";
	}
	
	@Autowired
	private WSPusher pusher;
	
	private Iterable<FileModel> websocketNotify(Iterable<FileModel> filemodels) {
		//pusher.websocketNotify(filemodels);
		return filemodels;
	}

}