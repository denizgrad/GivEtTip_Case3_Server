package cloud.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import cloud.model.entity.Record;
import cloud.model.entity.Sample;
import cloud.model.entity.User;
import cloud.model.response.Response;
import cloud.service.IRecordService;
import cloud.service.ISampleService;
import cloud.service.IUserService;

@RestController
@RequestMapping("/main")
public class MainRestController {
	/* @Autowired
	ISampleService sampleService; */

	@Autowired
	IUserService userService;

	@Autowired
	IRecordService recordService;

	/*
	@ResponseBody
	@RequestMapping(value = "/createSample", consumes = "application/json", produces = "application/json", method = RequestMethod.POST)
	public ResponseEntity<Response> createSample(@RequestBody Sample sample) {
		sampleService.create(sample);
		Response resp = new Response(true, HttpStatus.OK.value(), "Success");
		resp.setReturnKey(sample.getOid());
		return new ResponseEntity<>(resp, HttpStatus.OK);
	}

	@ResponseBody
	@RequestMapping(value = "/listSample", produces = "application/json", method = RequestMethod.GET)
	public ResponseEntity<List<Sample>> test() {
		User u = new User();
		u.setPassword("nesto");
		u.setEmail("nesto");
		return new ResponseEntity<>(sampleService.listAll(), HttpStatus.OK);
	}
	*/

	/* User Rest Controller */
	@ResponseBody
	@RequestMapping(value = "/users", produces = "application/json", method = RequestMethod.GET)
	public ResponseEntity<List<User>> getUsers() {
		try {
			return new ResponseEntity<>(userService.getUsers(), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@ResponseBody
	@RequestMapping(value = "/users/{id}", produces = "application/json", method = RequestMethod.GET)
	public ResponseEntity<User> getUser(@PathVariable(value = "id") int id) {
		try {
			return new ResponseEntity<>(userService.getUser(id), HttpStatus.OK);
		} catch (RuntimeException ex) {
			return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@ResponseBody
	@RequestMapping(value = "/users", consumes = "application/json", produces = "application/json", method = RequestMethod.POST)
	public ResponseEntity<Response> createUser(@RequestBody User user) {
		try {
			userService.createUser(user);
			Response resp = new Response(true, HttpStatus.CREATED.value(), "Success");
			resp.setReturnKey(Integer.toString(user.getId()));
			return new ResponseEntity<>(resp, HttpStatus.CREATED);
		} catch (Exception e) {
			if (userService.doesUserExists(user.getEmail())) {
				Response resp = new Response(false, HttpStatus.CONFLICT.value(),
						"User with this email already exists.");
				return new ResponseEntity<>(resp, HttpStatus.CONFLICT);
			} else {
				return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
			}
		}
	}

	@ResponseBody
	@RequestMapping(value = "/users", consumes = "application/json", produces = "application/json", method = RequestMethod.PUT)
	public ResponseEntity<Response> updateUser(@RequestBody User user) {
		try {
			userService.updateUser(user);
			Response resp = new Response(true, HttpStatus.OK.value(), "Success");
			resp.setReturnKey(Integer.toString(user.getId()));
			return new ResponseEntity<>(resp, HttpStatus.OK);
		} catch (RuntimeException ex) {
			return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@ResponseBody
	@RequestMapping(value = "/users/{id}", produces = "application/json", method = RequestMethod.DELETE)
	public ResponseEntity<Response> deleteUser(@PathVariable(value = "id") int id) {
		try {
			userService.deleteUser(id);
			Response resp = new Response(true, HttpStatus.OK.value(), "Success");
			resp.setReturnKey(Integer.toString(id));
			return new ResponseEntity<>(resp, HttpStatus.OK);
		} catch (RuntimeException ex) {
			return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	/* Record Rest Controller */
	@ResponseBody
	@RequestMapping(value = "/records", produces = "application/json", method = RequestMethod.GET)
	public ResponseEntity<List<Record>> getRecords() {
		try {
			return new ResponseEntity<>(recordService.getRecords(), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@ResponseBody
	@RequestMapping(value = "/records/{id}", produces = "application/json", method = RequestMethod.GET)
	public ResponseEntity<Record> getRecord(@PathVariable(value = "id") int id) {
		try {
			return new ResponseEntity<>(recordService.getRecord(id), HttpStatus.OK);
		} catch (RuntimeException ex) {
			return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@ResponseBody
	@RequestMapping(value = "/records", consumes = "application/json", produces = "application/json", method = RequestMethod.POST)
	public ResponseEntity<Response> createRecord(@RequestBody Record record) {
		try {
			recordService.createRecord(record);
			Response resp = new Response(true, HttpStatus.CREATED.value(), "Success");
			resp.setReturnKey(Integer.toString(record.getId()));
			return new ResponseEntity<>(resp, HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}	

	@ResponseBody
	@RequestMapping(value = "/records", consumes = "application/json", produces = "application/json", method = RequestMethod.PUT)
	public ResponseEntity<Response> updateRecord(@RequestBody Record record) {
		try {
			recordService.updateRecord(record);
			Response resp = new Response(true, HttpStatus.OK.value(), "Success");
			resp.setReturnKey(Integer.toString(record.getId()));
			return new ResponseEntity<>(resp, HttpStatus.OK);
		} catch (RuntimeException ex) {
			return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@ResponseBody
	@RequestMapping(value = "/records/{id}", produces = "application/json", method = RequestMethod.DELETE)
	public ResponseEntity<Response> deleteRecord(@PathVariable(value = "id") int id) {
		try {
			recordService.deleteRecord(id);
			Response resp = new Response(true, HttpStatus.OK.value(), "Success");
			resp.setReturnKey(Integer.toString(id));
			return new ResponseEntity<>(resp, HttpStatus.OK);
		} catch (RuntimeException ex) {
			return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}
