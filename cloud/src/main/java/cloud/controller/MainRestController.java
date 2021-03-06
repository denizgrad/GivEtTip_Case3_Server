package cloud.controller;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import cloud.model.entity.Rank;
import cloud.model.entity.Record;
import cloud.model.entity.RecordCoordinate;
import cloud.model.entity.User;
import cloud.model.response.Response;
import cloud.service.IRankService;
import cloud.service.IRecordCoordinateService;
import cloud.service.IRecordService;
import cloud.service.IUserService;

@RestController
@RequestMapping("/main")
public class MainRestController {
	/*
	 * @Autowired ISampleService sampleService;
	 */

	@Autowired
	IUserService userService;

	@Autowired
	IRecordService recordService;
	
	@Autowired
	IRecordCoordinateService recordCoordinateService;
	
	@Autowired
	IRankService rankService;

	/*
	 * @ResponseBody
	 * 
	 * @RequestMapping(value = "/createSample", consumes = "application/json",
	 * produces = "application/json", method = RequestMethod.POST) public
	 * ResponseEntity<Response> createSample(@RequestBody Sample sample) {
	 * sampleService.create(sample); Response resp = new Response(true,
	 * HttpStatus.OK.value(), "Success"); resp.setReturnKey(sample.getOid()); return
	 * new ResponseEntity<>(resp, HttpStatus.OK); }
	 * 
	 * @ResponseBody
	 * 
	 * @RequestMapping(value = "/listSample", produces = "application/json", method
	 * = RequestMethod.GET) public ResponseEntity<List<Sample>> test() { User u =
	 * new User(); u.setPassword("nesto"); u.setEmail("nesto"); return new
	 * ResponseEntity<>(sampleService.listAll(), HttpStatus.OK); }
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
	@RequestMapping(value = "/users/{id}/records", produces = "application/json", method = RequestMethod.GET)
	public ResponseEntity<List<Record>> getUserRecords(@PathVariable(value = "id") int id) {
		try {
			return new ResponseEntity<>(userService.getUser(id).getRecords(), HttpStatus.OK);
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
			User newUser = userService.createUser(user);
			Response resp = new Response(true, HttpStatus.CREATED.value(), "Success");
			resp.setReturnKey(Integer.toString(newUser.getId()));
			return new ResponseEntity<>(resp, HttpStatus.CREATED);
		} catch (Exception e) {
			if (userService.doesUserExists(user.getEmail())) {
				Response resp = new Response(false, HttpStatus.CONFLICT.value(),
						"User with this email already exists.");
				return new ResponseEntity<>(resp, HttpStatus.CONFLICT);
			} else {
				Response resp = new Response(false, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.toString());
				return new ResponseEntity<>(resp, HttpStatus.INTERNAL_SERVER_ERROR);
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
			Response resp = new Response(false, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.toString());
			return new ResponseEntity<>(resp, HttpStatus.INTERNAL_SERVER_ERROR);
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

	
	/* Login Rest Controller */
	@ResponseBody
	@RequestMapping(value = "/login", consumes = "application/json", produces = "application/json", method = RequestMethod.POST)
	public ResponseEntity<Response> login(@RequestBody User user) {
		try {
			int userId = userService.login(user);//if false return -1
			if (userId != -1) {
				Response resp = new Response(true, HttpStatus.OK.value(), "Success");
				resp.setReturnKey(Integer.toString(userId));
				return new ResponseEntity<>(resp, HttpStatus.OK);
			} else {
				Response resp = new Response(false, HttpStatus.UNAUTHORIZED.value(), "The password is not correct.");
				return new ResponseEntity<>(resp, HttpStatus.UNAUTHORIZED);
			}
		} catch (Exception e) {
			Response resp = new Response(false, HttpStatus.UNAUTHORIZED.value(), e.toString());
			return new ResponseEntity<>(resp, HttpStatus.UNAUTHORIZED);
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

	@RequestMapping(value = "/records", method = RequestMethod.POST, consumes = "multipart/form-data")
    public @ResponseBody ResponseEntity<Response>  createRecordMultiPart(@RequestPart("jsonFile") MultipartFile file) throws JsonParseException, JsonMappingException, IOException {
		
		ByteArrayInputStream stream = new   ByteArrayInputStream(file.getBytes());
		String myString = IOUtils.toString(stream, "UTF-8");
//		System.out.println("GOT FILE : " + myString);
		ObjectMapper mapper = new ObjectMapper();
		Record record = mapper.readValue(myString, Record.class);
		record = recordService.createRecord(record);
		Response resp = new Response(true, HttpStatus.CREATED.value(), "Success");
		resp.setReturnKey(Integer.toString(record.getId()));
		return new ResponseEntity<>(resp, HttpStatus.OK);
	}
	
//	@ResponseBody
//	@RequestMapping(value = "/records", consumes = "application/json", produces = "application/json", method = RequestMethod.POST)
	public ResponseEntity<Response> createRecord(@RequestBody Record record) {
		try {
			Record newRecord = recordService.createRecord(record);
			Response resp = new Response(true, HttpStatus.CREATED.value(), "Success");
			resp.setReturnKey(Integer.toString(newRecord.getId()));
			return new ResponseEntity<>(resp, HttpStatus.OK);
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
	
	@ResponseBody
	@RequestMapping(value = "/recordscoordinates", produces = "application/json", method = RequestMethod.GET)
	public ResponseEntity<List<RecordCoordinate>> getRecordsCoordinates() {
		try {
			return new ResponseEntity<>(recordCoordinateService.getRecordsCoordinates(), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	
	/* Ranking Rest Controller */
	@ResponseBody
	@RequestMapping(value = "/rankings", produces = "application/json", method = RequestMethod.GET)
	public ResponseEntity<List<Rank>> getRankings() {
		try {
			return new ResponseEntity<>(rankService.getRanking(), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}
