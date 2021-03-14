package rw.gov.irembo.notification.controller;

import com.google.common.util.concurrent.RateLimiter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rw.gov.irembo.notification.domain.Subsciber;
import rw.gov.irembo.notification.service.AppService;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/rate")
public class AppController {
	@Autowired
	@Qualifier("normal")
	private RateLimiter normal;

	@Autowired
	@Qualifier("premium")
	private RateLimiter premium;

	@Autowired
	AppService appService;

	@PostMapping(value = "/subscriber-register")
	public ResponseEntity<?> subscribe(@RequestBody Subsciber sub) {

		String response = appService.saveSubscribution(sub);
		if (response.trim().isEmpty()) {
			return new ResponseEntity<String>("Error Occured", HttpStatus.BAD_REQUEST);
		} else {
			return new ResponseEntity<String>(response, HttpStatus.OK);
		}

	}

	@GetMapping("/handle-request")
	public ResponseEntity<?> notification(HttpServletRequest request) {
//       limiter.acquire();
		boolean check = false;
		String plan = appService.checkSubscription("fbjbnrrvttsa7");
		if (appService.countSessions("fbjbnrrvttsa7")) {
			if (plan.equalsIgnoreCase("true")) {
				System.out.println("premium");
				check = premium.tryAcquire();
			} else if (plan.equalsIgnoreCase("false")) {
				System.out.println("normal");
				 check = normal.tryAcquire();
			} else {
				return new ResponseEntity<>("subscription not found", HttpStatus.BAD_REQUEST);
			}
			if (check) {
				return new ResponseEntity<>(request.getServerName(), HttpStatus.ACCEPTED);
			} else {
				return new ResponseEntity<>(HttpStatus.TOO_MANY_REQUESTS, HttpStatus.TOO_MANY_REQUESTS);
			}
		} else {
			return new ResponseEntity<>("You have exhauseted your monthly request", HttpStatus.TOO_MANY_REQUESTS);
		}

	}

}
