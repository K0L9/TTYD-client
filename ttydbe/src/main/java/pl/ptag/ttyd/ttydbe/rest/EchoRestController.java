package pl.ptag.ttyd.ttydbe.rest;


import com.fasterxml.jackson.databind.JsonNode;
import lombok.NonNull;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/echo")
public class EchoRestController {

    @PostMapping
    public JsonNode echo(@NonNull @RequestBody JsonNode input){
        return input;
    }


}
