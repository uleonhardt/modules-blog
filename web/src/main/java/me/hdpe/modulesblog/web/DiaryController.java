package me.hdpe.modulesblog.web;


import me.hdpe.modulesblog.diary.api.DiaryService;

import me.hdpe.modulesblog.spring.Exported;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class DiaryController {

	private DiaryService service;

	DiaryController(DiaryService service) {
		this.service = service;
	}
	
	@GetMapping(path = "/diary")
	@ResponseBody
	public String[] getWeek() {
		return service.getDiaryEvents(7);
	}
}
