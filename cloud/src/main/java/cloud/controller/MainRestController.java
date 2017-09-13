package cloud.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import cloud.model.entity.Sample;
import cloud.model.response.Response;
import cloud.service.ISampleService;


@RestController
@RequestMapping("/main")
public class MainRestController {
	@Autowired
	ISampleService sampleService;
	
	@ResponseBody
	@RequestMapping(value = "/createSample",  consumes = "application/json", produces = "application/json", method = RequestMethod.POST)
	public ResponseEntity<Response> createSample(@RequestBody Sample sample) {
		sampleService.create(sample);
		Response resp = new Response(true, HttpStatus.OK.value(), "Success");
		
		resp.setReturnKey(sample.getOid());
		
		return new ResponseEntity<>(resp, HttpStatus.OK);
	}
	
	@ResponseBody
	@RequestMapping(value = "/listSample", produces = "application/json", method = RequestMethod.GET)
	public ResponseEntity<List<Sample>> test() {
		return new ResponseEntity<>(sampleService.listAll(), HttpStatus.OK);
	}

}
