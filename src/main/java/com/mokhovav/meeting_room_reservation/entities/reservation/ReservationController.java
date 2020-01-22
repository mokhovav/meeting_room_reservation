package com.mokhovav.meeting_room_reservation.entities.reservation;

import com.mokhovav.meeting_room_reservation.entities.user.User;
import com.mokhovav.meeting_room_reservation.error.CustomException;
import com.mokhovav.meeting_room_reservation.responses.DailySchedule;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

import org.slf4j.Logger;

@Controller
//@RequestMapping("/conferences")
public class ReservationController {

    final Logger logger;
    final ReservationService reservationService;
    final ReservationValid reservationValid;
    private int startWeak = 0;

    public ReservationController(ReservationService reservationService, Logger logger, ReservationValid reservationValid) {
        this.reservationService = reservationService;
        this.logger = logger;
        this.reservationValid = reservationValid;
    }

    @GetMapping
    public String conferences(@AuthenticationPrincipal User user, Model model){

        if (user != null && user.isChangePassword())
            return "redirect:/user/changePassword";

        List<DailySchedule> list = reservationService.getSchedule(startWeak);
        model.addAttribute("schedule",list);

        List<String> time = new ArrayList<>();
        for (int i = 0 ; i<= 24; i++)
            time.add(String.format("%02d",i)+":00");
        model.addAttribute("time", time);
        int temp = 0;
        model.addAttribute("temp",temp);
        return "conferences";
    }

    @GetMapping("prevWeek")
    public String prevWeek(Model model){
        startWeak--;
        return "redirect:/";//"redirect:/conferences";
    }

    @GetMapping("nextWeek")
    public String nextWeek(Model model){
        startWeak++;
        return "redirect:/";//"redirect:/conferences";
    }

    @GetMapping("addReservation")
    public String addReservation(Model model){
        model.addAttribute("isAdd", true);
        return "addreservation";
    }

    @GetMapping("showReservation/{reservationId}")
    public String showReservation(@PathVariable Long reservationId, Model model){
        Reservation reservation = reservationService.getById(reservationId);
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        DateFormat timeFormat = new SimpleDateFormat("HH:mm");

        model.addAttribute("title",reservation.getTitle());
        model.addAttribute("description", reservation.getDescription());
        model.addAttribute("date", dateFormat.format(reservation.getTimeBegin()));
        model.addAttribute("time", timeFormat.format(reservation.getTimeBegin()));
        model.addAttribute("duration", (reservation.getTimeEnd() - reservation.getTimeBegin())/60000);
        model.addAttribute("isAdd", false);
        return "addreservation";
    }

    @PostMapping("addReservation")
    public String addReservation(
            @AuthenticationPrincipal User user,
            @RequestParam String title,
            @RequestParam String description,
            @RequestParam Integer duration,
            @RequestParam String date,
            @RequestParam String time,
            Model model) {
        Reservation reservation = new Reservation();
        reservation.setTitle(title);
        reservation.setDescription(description);
        try {
            reservationService.addReservation(reservation, user, title, description, duration, date, time);
        } catch (CustomException e) {
            model.addAttribute("message", e.getMessage());
        }
        model.addAttribute("title", reservation.getTitle());
        model.addAttribute("description", reservation.getDescription());
        model.addAttribute("isAdd", true);

        return "addreservation";
    }
}
