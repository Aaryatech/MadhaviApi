package com.ats.webapi.controller.cloudkitchen;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.ats.webapi.model.Info;
import com.ats.webapi.model.cloudkitchen.ItemConfigDetail;
import com.ats.webapi.repo.cloudkitchen.ItemConfigDetailRepo;

@RestController
public class ItemConfigController {

	@Autowired
	ItemConfigDetailRepo itemConfigDetailRepo;

	@RequestMapping(value = { "/saveItemConfigurationDetails" }, method = RequestMethod.POST)
	public @ResponseBody Info saveItemConfigurationDetails(@RequestBody List<ItemConfigDetail> itemConfigDetailList) {

		Info info = new Info();

		if (itemConfigDetailList != null) {

			for (ItemConfigDetail model : itemConfigDetailList) {
				ItemConfigDetail res = itemConfigDetailRepo.save(model);
			}
			info.setError(false);
			info.setMessage("Success");

		} else {
			info.setError(true);
			info.setMessage("Failed");
		}

		return info;
	}

}
