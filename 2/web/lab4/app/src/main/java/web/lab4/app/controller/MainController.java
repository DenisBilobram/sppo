package web.lab4.app.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import jakarta.validation.ValidationException;
import web.lab4.app.controller.requests.AppRequestDto;
import web.lab4.app.model.AppRequest;
import web.lab4.app.service.AppRequestService;

@RestController
@RequestMapping("api/main")
public class MainController {

    @Autowired
    AppRequestService appRequestService;

    @GetMapping("requests")
    public ResponseEntity<List<AppRequestDto>> requestsHistoryData(@AuthenticationPrincipal Jwt jwt) {

        String username = jwt.getClaimAsString("preferred_username");

        return new ResponseEntity<>(appRequestService.getAppRequestsForUser(username).stream().map(request -> new AppRequestDto(request)).toList(), HttpStatus.OK);

    }

    @PostMapping("requests")
    public ResponseEntity<AppRequestDto> createRequest(@Valid @RequestBody AppRequestDto requestDto, BindingResult bindingResult, @AuthenticationPrincipal Jwt jwt) throws ValidationException {

        String username = jwt.getClaimAsString("preferred_username");

        if (bindingResult.hasErrors()) {
            throw new ValidationException(bindingResult.getFieldError().getDefaultMessage());
        }

        requestDto.setResult(calcResult(requestDto));

        AppRequest appRequest = appRequestService.saveRequest(requestDto.getX(), requestDto.getY(), requestDto.getR(), requestDto.getResult(), username);
        return new ResponseEntity<>(new AppRequestDto(appRequest), HttpStatus.CREATED);

    }

    @DeleteMapping("requests")
    public ResponseEntity<?> clearRequests(@AuthenticationPrincipal Jwt jwt) {

        String username = jwt.getClaimAsString("preferred_username");

        appRequestService.clearRequests(username);
        return ResponseEntity.ok().build();

    }

    public boolean calcResult(AppRequestDto requestDto) {

        Double x = requestDto.getX();
        Double y = requestDto.getY();
        Double r = requestDto.getR();
        if (((x >= 0 && y >= 0) && (y <= -x/2 + r/2)) || ((x <= 0 && y <= 0) && (Math.sqrt(x*x + y*y) <= r/2)) || ((x >= 0 && y <= 0) && (x <= r && y >= -r/2))) {
            return true;
        } else {
            return false;
        }

    }

}
