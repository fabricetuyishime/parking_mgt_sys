package com.example.parkingmanagementsystemsecured.controller;

import com.example.parkingmanagementsystemsecured.model.Location;
import com.example.parkingmanagementsystemsecured.model.NormalDay;
import com.example.parkingmanagementsystemsecured.model.Price;
import com.example.parkingmanagementsystemsecured.service.interfaces.LocationInterface;
import com.example.parkingmanagementsystemsecured.service.interfaces.NormalDayService;
import com.example.parkingmanagementsystemsecured.service.interfaces.UserInfoInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Controller
public class NormalDayController {
    @Autowired
    private NormalDayService service;
    @Autowired
    private UserInfoInterface userInfoInterface;
    @Autowired
    private LocationInterface locationInterface;

    @GetMapping("/listNormalDay")
    public String viewHome(Model model) {
        return findPagenited(1, "entryTime", "asc", model);
    }

    @GetMapping("/showNewNormalDayForm")
    public String showNewNormalDayForm(Model model) {
        NormalDay normalDay = new NormalDay();
        model.addAttribute("normalDay", normalDay);
        return "new_normalDay";
    }

    @PostMapping("/saveNormalDay")
    public String saveNormalDay(@ModelAttribute("normalDay") NormalDay normalDay,
                                @RequestParam(value = "user") String location
    ,Model model) {
        normalDay.setLocation(userInfoInterface.getEmail(location).get().getLocation());
        service.saveNormalDay(normalDay);
        UUID userInfoOptional = userInfoInterface.getEmail(location).get().getLocation().getId();

        Location loc = locationInterface.getLocationById(userInfoOptional);
        if (loc.getAvailableSpots()>0){
            loc.setLocationName(loc.getLocationName());
            loc.setNoOfParkingSpots(loc.getNoOfParkingSpots());
            loc.setAvailableSpots(loc.getAvailableSpots()-1);
            locationInterface.saveLocation(loc);
            return "redirect:/listNormalDay";}
        else {
            String message= "There no more parking spots";
            model.addAttribute("message",message);
            return "nothin";
        }
    }
    @PostMapping("/payNormalDay")
    public String payNormalDay(@ModelAttribute("normalDay") NormalDay normalDay,
                               @RequestParam(value = "user") String location
                               ,Model model) {

        UUID locationId = userInfoInterface.getEmail(location).get().getLocation().getId();
        Location loc = locationInterface.getLocationById(locationId);

//        if(loc.getAvailableSpots()<loc.getNoOfParkingSpots()){
        loc.setLocationName(loc.getLocationName());
        loc.setNoOfParkingSpots(loc.getNoOfParkingSpots());
        loc.setAvailableSpots(loc.getAvailableSpots()+1);
        locationInterface.saveLocation(loc);

        normalDay.setExitTime(LocalDateTime.now());
        LocalDateTime entry= normalDay.getEntryTime();
        LocalDateTime exit = LocalDateTime.now();
        Price price = new Price();
        normalDay.setAmount(price.prices(entry,exit));
        service.saveNormalDay(normalDay);

        return "redirect:/listNormalDay";


    }

    @GetMapping("/showFormForUpdateNormalDay/{id}")
    public String showFormForUpdate(@PathVariable(value = "id") UUID id, Model model) {
        Optional<NormalDay> normalDay = service.getNormalDayById(id);
        model.addAttribute("normalDay",normalDay);
        LocalDateTime entry= normalDay.get().getEntryTime();
        LocalDateTime exit = LocalDateTime.now();
        Price price = new Price();
        BigDecimal toPay = price.prices(entry,exit);
        model.addAttribute("toPay",toPay);
        return "pay_normal_day";
    }

    @GetMapping("/deleteNormalDay/{id}")
    public String deleteNormalDay(@PathVariable(value = "id") UUID id) {
        this.service.deleteNormalDayById(id);
        return "redirect:/listNormalDay";
    }

    @GetMapping("/pageNormalDay/{pageNo}")
    public String findPagenited(@PathVariable(value = "pageNo") int pageNo,
                                @RequestParam("sortField") String sortField,
                                @RequestParam("sortDir") String sortDir,
                                Model model) {

        int pageSize = 5;
        Page<NormalDay> page = service.findPaginated(pageNo, pageSize, sortField, sortDir);
        List<NormalDay> listNormalDays = page.getContent();
        model.addAttribute("currentPage", pageNo);
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("totalItems", page.getTotalElements());

        model.addAttribute("sortField", sortField);
        model.addAttribute("sortDir", sortDir);
        model.addAttribute("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");

        model.addAttribute("listNormalDays", listNormalDays);
        return "normal_day";
    }
    @GetMapping("/findCar")
    public String findDeposit(Model model){
        NormalDay normalDay = new NormalDay();
        LocalDateTime entry= normalDay.getEntryTime();

        model.addAttribute("normalDay",normalDay);
        return"findCar";

    }@GetMapping("/foundCar")
    public String CarryDeposit(@RequestParam("plate") String plate, Model model){
        NormalDay normalDay= service.findNumberPlateToPay(plate);
        LocalDateTime entry= normalDay.getEntryTime();
        LocalDateTime exit = LocalDateTime.now();
        Price price = new Price();
        BigDecimal toPay = price.prices(entry,exit);
        model.addAttribute("toPay",toPay);
        model.addAttribute("normalDay",normalDay);
        return "findCar";
    }
    @GetMapping("/pageUnpaid/{pageNo}")
    public String listUnpaid(@PathVariable(value = "pageNo") int pageNo,
                                @RequestParam("sortField") String sortField,
                                @RequestParam("sortDir") String sortDir,
                                Model model) {

        int pageSize = 5;
        Page<NormalDay> page = service.unpaid(pageNo, pageSize, sortField, sortDir);
        List<NormalDay> listNormalDays = page.getContent();
        model.addAttribute("currentPage", pageNo);
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("totalItems", page.getTotalElements());

        model.addAttribute("sortField", sortField);
        model.addAttribute("sortDir", sortDir);
        model.addAttribute("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");

        model.addAttribute("listNormalDays", listNormalDays);
        return "normal_day";
    }
    @PostMapping("/pageSearchNormalDay/{pageNo}")
    public String search(@PathVariable(value = "pageNo") int pageNo,
                         @RequestParam("name") String name,
                         @RequestParam("sortField") String sortField,
                                @RequestParam("sortDir") String sortDir,
                                Model model) {

        int pageSize = 5;
        Page<NormalDay> page = service.search(name,pageNo, pageSize, sortField, sortDir);
        List<NormalDay> listNormalDays = page.getContent();
        model.addAttribute("currentPage", pageNo);
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("totalItems", page.getTotalElements());

        model.addAttribute("sortField", sortField);
        model.addAttribute("sortDir", sortDir);
        model.addAttribute("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");

        model.addAttribute("listNormalDays", listNormalDays);
        return "normal_day";
    }
    @GetMapping("/pagePaid/{pageNo}")
    public String listpaid(@PathVariable(value = "pageNo") int pageNo,
                             @RequestParam("sortField") String sortField,
                             @RequestParam("sortDir") String sortDir,
                             Model model) {

        int pageSize = 5;
        Page<NormalDay> page = service.paid(pageNo, pageSize, sortField, sortDir);
        List<NormalDay> listNormalDays = page.getContent();
        model.addAttribute("currentPage", pageNo);
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("totalItems", page.getTotalElements());

        model.addAttribute("sortField", sortField);
        model.addAttribute("sortDir", sortDir);
        model.addAttribute("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");

        model.addAttribute("listNormalDays", listNormalDays);
        return "paid";
    }
}
