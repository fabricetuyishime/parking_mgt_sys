package com.example.parkingmanagementsystemsecured.controller;

import com.example.parkingmanagementsystemsecured.model.Location;
import com.example.parkingmanagementsystemsecured.model.UserInfo;
import com.example.parkingmanagementsystemsecured.security.UserInfoDetails;
import com.example.parkingmanagementsystemsecured.service.interfaces.LocationInterface;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Controller
public class LocationController {
    @Autowired
    private LocationInterface locationService;
    @Autowired
    private HttpServletRequest request;
    @GetMapping("/")
    public String someMethod() {

        return"navBar";
    }
    @GetMapping("/locationPage")
    public  String homePage(@ModelAttribute("location") Location location, Model model){

        return findPagenited(1, "locationName", "asc", model);
    }
    @PostMapping("/locationSave")
    public String saveLocation(@ModelAttribute("location") Location location

            , Model model){
        location.setAvailableSpots(location.getNoOfParkingSpots());
        locationService.saveLocation(location);
        return "redirect:/locationPage";

    }
    @GetMapping("/locationUpdate/{id}")

    public String updateLocation(@PathVariable(value = "id") UUID id, Model model) {
        List<Location> locationList = locationService.getAllLocation();
        model.addAttribute("locationList", locationList);
        Location location = locationService.getLocationById(id);
        model.addAttribute("location", location);


        return "location";
    }
    @GetMapping("/locationDelete/{id}")
    public String deleteLocation(@PathVariable(value = "id")UUID id, Model model){
        this.locationService.deleteLocationById(id);
        return "redirect:/locationPage";
    }

    @GetMapping("/pageLocation/{pageNo}")
    public String findPagenited(@PathVariable(value = "pageNo") int pageNo,
                                @RequestParam("sortField") String sortField,
                                @RequestParam("sortDir") String sortDir,
                                Model model) {

        int pageSize = 5;
        Page<Location> page = locationService.findPaginated(pageNo, pageSize, sortField, sortDir);
        List<Location> locationList = page.getContent();
        model.addAttribute("currentPage", pageNo);
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("totalItems", page.getTotalElements());

        model.addAttribute("sortField", sortField);
        model.addAttribute("sortDir", sortDir);
        model.addAttribute("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");

        model.addAttribute("locationList", locationList);

        Location location = new Location();
        model.addAttribute("location", location);

        return "location";
    }

    @PostMapping("/pageSearchLocation/{pageNo}")
    public String search(@PathVariable(value = "pageNo") int pageNo,
                                @RequestParam("sortField") String sortField,
                                @RequestParam("sortDir") String sortDir,
                                @RequestParam(value = "name") String name,
                                Model model) {

        int pageSize = 5;
        Page<Location> page = locationService.search( name,pageNo, pageSize, sortField, sortDir);
        List<Location> locationList = page.getContent();
        model.addAttribute("currentPage", pageNo);
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("totalItems", page.getTotalElements());

        model.addAttribute("sortField", sortField);
        model.addAttribute("sortDir", sortDir);
        model.addAttribute("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");

        model.addAttribute("locationList", locationList);

        Location location = new Location();
        model.addAttribute("location", location);

        return "location";
    }
}
