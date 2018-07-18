package com.example.week7challenge;

import com.cloudinary.utils.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.IOException;
import java.security.Principal;
import java.util.List;
import java.util.Map;

@Controller
public class HomeController {
    @Autowired
    CloudinaryConfig cloudc;

    @Autowired
    DayRepository dayRepository;

    @Autowired
    FruitRepository fruitRepository;

    @Autowired
    HourRepository hourRepository;

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/register", method = RequestMethod.GET)
    public String showRegistrationPage(Model model)
    {
        model.addAttribute("user", new User());
        return "registration";
    }

    @RequestMapping(value="/register", method=RequestMethod.POST)
    public String processRegistrationPage(@Valid @ModelAttribute("user") User user, BindingResult result, Model model)
    {
        model.addAttribute("user", user);
        if (result.hasErrors())
        {
            return "registration";
        }
        else
        {
            userService.saveUser(user);
            model.addAttribute("message", "User Account Successfully Created");
        }
        return "index";
    }

    @RequestMapping("/login")
    public String login()
    {
        return "login";
    }

    @RequestMapping("/")
    public String index(Model model)
    {
        model.addAttribute("days", dayRepository.findAllByOrderByDayorder());
        return "index";
    }

    @RequestMapping("/detailDay/{id}")
    public String showDayDetail(@PathVariable("id") long id, Model model)
    {
        model.addAttribute("day", dayRepository.findById(id).get());
        return "showDay";
    }

    @RequestMapping("/admin")
    public String startAdmin(Model model, HttpServletRequest request, Authentication authentication, Principal principal)
    {
        Boolean isAdmin = request.isUserInRole("ADMIN");
        Boolean isUser = request.isUserInRole("USER");
        UserDetails userDetails = (UserDetails)authentication.getPrincipal();
        String username = principal.getName();

        model.addAttribute("days", dayRepository.findAllByOrderByDayorder());
        model.addAttribute("fruits", fruitRepository.findAll());
        model.addAttribute("hours", hourRepository.findAll());
        return "adminPage";
    }
//////////////////////////////////////////////Fruit
    @GetMapping("/addnewFruit")
    public String addFruit (Model model)
    {
        model.addAttribute("days", dayRepository.findAll());
        model.addAttribute("fruit", new Fruit());
        return "fruitform";
    }

    @PostMapping("/addnewFruit")
    public String processFruit (@Valid @ModelAttribute Fruit fruit, BindingResult result,
                                @RequestParam("file")MultipartFile file, Model model)
    {
        if(result.hasErrors())
        {
            return "fruitform";
        }
        if(file.isEmpty())
        {
            fruitRepository.save(fruit);
            return "redirect:/admin";
        }
        try{
            Map uploadResult = cloudc.upload(file.getBytes(), ObjectUtils.asMap("resourcetype", "auto"));
            fruit.setImage(uploadResult.get("url").toString());
            fruitRepository.save(fruit);
        }catch (IOException e)
        {
            e.printStackTrace();
        }
        return "redirect:/admin";
    }

    @RequestMapping("/updateFruit/{id}")
    public String updateFruit(@PathVariable("id") long id, Model model)
    {
        model.addAttribute("fruit", fruitRepository.findById(id));
        model.addAttribute("days", dayRepository.findAll());
        return "fruitform";
    }


    @RequestMapping("/deleteFruit/{id}")
    public String deleteFruit(@PathVariable("id") long id)
    {
        fruitRepository.deleteById(id);
        return "redirect:/admin";
    }
///////////////////////////////////////////////////Hour
    @GetMapping("/addnewhour")
    public String addHour (Model model)
    {
        model.addAttribute("days", dayRepository.findAll());
        model.addAttribute("hour", new Hour());
        return "hourform";
    }

    @PostMapping("/addnewhour")
    public String processHour (@Valid @ModelAttribute Hour hour, BindingResult result)
    {
        if(result.hasErrors())
        {
            return "hourform";
        }

            hourRepository.save(hour);
            hour.setSchedule(hour.getStartTime(), hour.getAmOrPm1(),  hour.getAmOrPm2(), hour.getEndTime());
            hourRepository.save(hour);
        return "redirect:/admin";
    }

    @RequestMapping("/updateHour/{id}")
    public String updateHour(@PathVariable("id") long id, Model model)
    {
        model.addAttribute("hour", hourRepository.findById(id));
        return "hourform";
    }


    @RequestMapping("/deleteHour/{id}")
    public String deleteHour(@PathVariable("id") long id)
    {
        hourRepository.deleteById(id);
        return "redirect:/admin";
    }

    /////////////////////////////Day
    @GetMapping("/addnewday")
    public String addDay (Model model)
    {
        model.addAttribute("hours", hourRepository.findAll());
        model.addAttribute("fruits", fruitRepository.findAll());
        model.addAttribute("day", new Day());
        return "dayform";
    }

    @PostMapping("/addnewday")
    public String processDay (@Valid @ModelAttribute Day day, BindingResult result, Model model)
    {
        if(result.hasErrors())
        {
            model.addAttribute("hours", hourRepository.findAll());
            model.addAttribute("fruits", fruitRepository.findAll());
            return "dayform";
        }
        String dayName=day.getName();
        if(dayRepository.countByName(dayName)>0)
        {
            model.addAttribute("hours", hourRepository.findAll());
            model.addAttribute("fruits", fruitRepository.findAll());
            //System.out.println("Choose another day. The day already existed.");
            return "dayform";
        }
        dayRepository.save(day);
        day.setDayorder(day.getName());
        dayRepository.save(day);

        return "redirect:/admin";
    }

    @RequestMapping("/updateDay/{id}")
    public String updateDay(@PathVariable("id") long id, Model model)
    {
        model.addAttribute("fruits", fruitRepository.findAll());
        model.addAttribute("hours", hourRepository.findAll());
        model.addAttribute("day1", dayRepository.findById(id).get());
        model.addAttribute("day", dayRepository.findById(id));
        return "dayupdateform";
    }

    @PostMapping("/updateDay")
    public String processupdateDay(@Valid @ModelAttribute Day day, BindingResult result, Model model)
    {
        if(result.hasErrors())
        {
            model.addAttribute("hours", hourRepository.findAll());
            model.addAttribute("fruits", fruitRepository.findAll());
            return "dayform";
        }
        dayRepository.save(day);
        return "redirect:/admin";
    }

    @RequestMapping("/deleteDay/{id}")
    public String deleteDay(@PathVariable("id") long id)
    {
        dayRepository.deleteById(id);
        return "redirect:/admin";
    }

}
