package ua.kiev.prog.oauth2.loginviagoogle.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ua.kiev.prog.oauth2.loginviagoogle.dto.AccountDTO;
import ua.kiev.prog.oauth2.loginviagoogle.dto.PageCountDTO;
import ua.kiev.prog.oauth2.loginviagoogle.dto.ResultDTO;
import ua.kiev.prog.oauth2.loginviagoogle.dto.TaskDTO;
import ua.kiev.prog.oauth2.loginviagoogle.model.Task;
import ua.kiev.prog.oauth2.loginviagoogle.services.GeneralService;

import java.util.List;
import java.util.Map;

@RestController
public class MainController {

    private static final int PAGE_SIZE = 5;

    @Autowired
    private GeneralService generalService;

    @GetMapping("/account")
    public AccountDTO account(OAuth2AuthenticationToken auth) {
        Map<String, Object> attrs = auth.getPrincipal().getAttributes();

        String email = (String) attrs.get("email");
        String name = (String) attrs.get("name");
        String pictureUrl = (String) attrs.get("picture");
        if(pictureUrl==null)
            pictureUrl="http://graph.facebook.com/"+attrs.get("id")+"/picture";

        return AccountDTO.of(email, name, pictureUrl);
    }

    @GetMapping("count")
    public PageCountDTO count(OAuth2AuthenticationToken auth) {
        String email = (String) auth.getPrincipal().getAttributes().get("email");
        return PageCountDTO.of(generalService.count(email), PAGE_SIZE);
    }

    @GetMapping("tasks")
    public List<TaskDTO> tasks(OAuth2AuthenticationToken auth,
                               @RequestParam(required = false, defaultValue = "0") int page) {
        String email = (String) auth.getPrincipal().getAttributes().get("email");
        return generalService.getTasks(email,
                PageRequest.of(page, PAGE_SIZE, Sort.Direction.DESC, "id")
        );
    }

    @PostMapping("add")
    public ResponseEntity<ResultDTO> addTask(OAuth2AuthenticationToken auth,
                                             @RequestBody TaskDTO task) {
        String email = (String) auth.getPrincipal().getAttributes().get("email");
        generalService.addTask(email, task);

        return new ResponseEntity<>(new ResultDTO(), HttpStatus.OK);
    }

    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public void delete(@RequestParam(name = "toDelete[]", required = false)
                                 String[] text, OAuth2AuthenticationToken auth) {
        String email = (String) auth.getPrincipal().getAttributes().get("email");
        System.out.println(email);
        if (text != null && text.length > 0)
            generalService.deleteTasks(email, text);
    }
}
