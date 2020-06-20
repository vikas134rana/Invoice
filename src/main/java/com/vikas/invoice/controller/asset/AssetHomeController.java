package com.vikas.invoice.controller.asset;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class AssetHomeController {

	@RequestMapping("/asset")
	public String home() {
		return "asset_home";
	}
	
}
