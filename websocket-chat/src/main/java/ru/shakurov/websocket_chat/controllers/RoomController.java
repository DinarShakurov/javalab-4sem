package ru.shakurov.websocket_chat.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.shakurov.websocket_chat.dto.RoomDto;
import ru.shakurov.websocket_chat.forms.RoomForm;
import ru.shakurov.websocket_chat.services.RoomService;

@Controller
@RequestMapping("/rooms")
public class RoomController {
    @Autowired
    private RoomService roomService;

    @GetMapping
    public String getRoomsPage(ModelMap modelMap) {
        modelMap.addAttribute("rooms", roomService.getRooms());
        return "rooms";
    }

    @PostMapping
    public String createRoom(RoomForm roomForm) {
        roomService.createRoom(roomForm);
        return "redirect:/rooms";
    }

    @GetMapping("/{room-id}")
    public String getRoom(ModelMap modelMap, @PathVariable("room-id") Long roomId){
        RoomDto roomDto =  roomService.getRoom(roomId);
        modelMap.addAttribute("roomDto", roomDto);
        System.out.println(roomDto);
        return "room";
    }
}
