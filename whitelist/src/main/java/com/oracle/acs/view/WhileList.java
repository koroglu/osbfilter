package com.oracle.acs.view;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.oracle.acs.dao.WhiteListDAO;
import com.oracle.acs.model.WhiteListModel;

@RestController
public class WhileList {
	
	@Autowired
	private WhiteListDAO whiteListDAO;

	@GetMapping(path = "/list")
	public Map getIps(@RequestParam String context) {
		
		Map m = new HashMap();
		m.put("list", whiteListDAO.listIpByContext(context));
		return m;
		
	}
	
	@GetMapping(path = "/listAll")
	public Map listAll() {
		
		Map m = new HashMap();
		m.put("list", whiteListDAO.listAll());
		return m;
		
	}
	

	@GetMapping(path = "/doIHavePermission")
	public Map getIps(@RequestParam String context, @RequestParam String ipAddress) {
		Map m = new HashMap();
		m.put("success", whiteListDAO.doIHavePermission(context,ipAddress));
		return m;
		
	}
	
	
	@GetMapping(path = "/add")
	public Map add(@RequestParam String context,@RequestParam String ipAddress) {
		Map m = new HashMap();
		WhiteListModel model = new WhiteListModel(context, ipAddress);
		m.put("success", whiteListDAO.saveWhiteListModel(model));
		return m;
		
	}
	
	
	@GetMapping(path = "/remove")
	public Map remove(@RequestParam String context,@RequestParam String ipAddress) {
		Map m = new HashMap();
		List listOfIps = new ArrayList<String>();
		listOfIps.add("127.0.0.1");
		listOfIps.add("212.174.189.2");
		m.put("list", listOfIps);
		return m;
		
	}
	
	
	
	
	
	
}
