package home.todayhome.security;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/test/jwt")
public class JwtTestController {

    private final JwtService jwtService;

    @GetMapping("/encode")
    public String jwtEncode(@RequestParam("email") String userEmail){
        return jwtService.encode(userEmail);
    }

    @GetMapping("/decode")
    public Map<String, String> jwtDecode(@RequestParam("token") String jwtToken){
        return jwtService.decode(jwtToken);
    }

}
