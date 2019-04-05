package zip.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.ShaPasswordEncoder;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import zip.demo.user.CustomUser;
import zip.demo.user.UserRole;
import zip.demo.user.UserService;
import zip.demo.zip.ZipService;
import zip.demo.zip.ZipUtil;

import java.util.zip.ZipEntry;

@Controller
public class MyController {
    @Autowired
    private UserService userService;

    @Autowired
    private ZipService zipService;

    @Autowired
    private ShaPasswordEncoder passwordEncoder;

    @RequestMapping("/")
    public String index(Model model){
        User user = (User)SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal();

        String login = user.getUsername();
        CustomUser dbUser = userService.findByLogin(login);

        model.addAttribute("login", login);
        model.addAttribute("roles", user.getAuthorities());
        model.addAttribute("zips", zipService.findZipByLogin(login));

        return "index";
    }

    @RequestMapping(value = "/newuser", method = RequestMethod.POST)
    public String update(@RequestParam String login,
                         @RequestParam String password,
                         Model model) {
        String passHash = passwordEncoder.encodePassword(password, null);

        if ("".equals(login) ||
                !userService.addUser(login, passHash, UserRole.USER)) {
            model.addAttribute("exists", true);
            model.addAttribute("login", login);
            return "register";
        }

        return "redirect:/";
    }

    @RequestMapping("/login")
    public String loginPage() {
        return "login";
    }

    @RequestMapping("/register")
    public String register() {
        return "register";
    }

    @RequestMapping("/unauthorized")
    public String unauthorized(Model model){
        User user = (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        model.addAttribute("login", user.getUsername());
        return "unauthorized";
    }

    @RequestMapping(value = "/tozip", method = RequestMethod.POST)
    public String toZIP(@RequestParam("file") MultipartFile file) throws Exception {
        if (!file.isEmpty()) {
            String zipPath = file.getOriginalFilename().substring(0,file.getOriginalFilename().indexOf('.'))+".zip";
            User user = (User)SecurityContextHolder
                    .getContext()
                    .getAuthentication()
                    .getPrincipal();
            String login = user.getUsername();
            zipService.addZip(zipPath,file.getOriginalFilename(), login);
        }
        return "redirect:/";
    }

    @RequestMapping(value = "/savezip", method = RequestMethod.POST)
    public String saveZIP(@RequestParam("zipPath") String pathZip) throws Exception {
        String filePath = zipService.findFileByZip(pathZip);
        ZipUtil.toZip(filePath, pathZip);
        return "redirect:/";
    }
}
