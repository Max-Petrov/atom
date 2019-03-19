package ru.atom.chat.server;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.stream.Collectors;

@Controller
@RequestMapping("chat")
public class ChatController {
    private Queue<String> messages = new ConcurrentLinkedQueue<>();
    private Map<String, String> usersOnline = new ConcurrentHashMap<>();

    /**
     * curl -X POST -i localhost:8080/chat/login -d "name=I_AM_STUPID"
     */
    @RequestMapping(
            path = "login",
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity login(@RequestParam("name") String name) {
        ResponseEntity responseEntity = validateName(name);
        if (usersOnline.containsKey(name)) {
            responseEntity = ResponseEntity.badRequest().body("Already logged in:(");
        } else if (responseEntity == null) {
            usersOnline.put(name, name);
            messages.add("[" + name + "]: logged in");
            responseEntity = ResponseEntity.ok("You successfully logged in");
        }

        return responseEntity;
    }

    /**
     * curl -i localhost:8080/chat/online
     */
    @RequestMapping(
            path = "online",
            method = RequestMethod.GET,
            produces = MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity online() {
        String responseBody = String.join("\n", usersOnline.keySet().stream().sorted().collect(Collectors.toList()));

        return ResponseEntity.ok(responseBody);
    }

    /**
     * curl -X POST -i localhost:8080/chat/logout -d "name=I_AM_STUPID"
     */
    @RequestMapping(
            path = "logout",
            method = RequestMethod.POST,
            produces = MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity logout(@RequestParam("name") String name) {
        if (usersOnline.containsKey(name)) {
            usersOnline.remove(name);
            messages.add("[" + name + "]: logged out");
            return ResponseEntity.ok("You have successfully logged in");
        }

        return ResponseEntity.badRequest().body("This user isn't logged in yet :(");
    }

    /**
     * curl -X POST -i localhost:8080/chat/say -d "name=I_AM_STUPID&msg=Hello everyone in this chat"
     */
    @RequestMapping(
            path = "say",
            method = RequestMethod.POST,
            produces = MediaType.TEXT_PLAIN_VALUE
    )
    public ResponseEntity say(@RequestParam("name") String name, @RequestParam("msg") String msg) {
        if (!usersOnline.containsKey(name)) {
            return ResponseEntity.badRequest().body("This user isn't logged in yet :(");
        } else if (msg.length() == 0) {
            return ResponseEntity.badRequest().body("Empty message hasn't been sent :(");
        } else if (msg.length() > 50) {
            return ResponseEntity.badRequest().body("To long message hasn't been sent :(");
        }
        messages.add("[" + name + "]: " + msg);

        return ResponseEntity.ok("The message has been successfully sent");
    }


    /**
     * curl -i localhost:8080/chat/chat
     */
    @RequestMapping(
            path = "chat",
            method = RequestMethod.GET,
            produces = MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity chat() {
        String responseBody = String.join("\n", messages);

        return ResponseEntity.ok(responseBody);
    }

    /**
     * curl -X localhost:8080/chat/user/user_name
     */
    @RequestMapping(
            path = "user/{name}",
            method = RequestMethod.PUT,
            produces = MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity changeUserName(@PathVariable("name") String name, @RequestParam("name") String newName) {
        ResponseEntity responseEntity;
        if (usersOnline.containsKey(name)) {
            responseEntity = validateName(newName);
            if (responseEntity == null) {
                String userInfo = usersOnline.get(name);
                usersOnline.remove(name);
                usersOnline.put(newName, userInfo);
                usersOnline.remove(name);
                responseEntity = ResponseEntity.ok("Your name has been successfully changed!");
            }
        } else {
            responseEntity = ResponseEntity.badRequest().body("This user isn't logged in yet :(");
        }

        return responseEntity;
    }

    /**
     * curl -X localhost:8080/chat/chat
     */
    @RequestMapping(
            path = "chat",
            method = RequestMethod.DELETE,
            produces = MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity clearHistory() {
        messages.clear();

        return ResponseEntity.ok("The chat has been successfully cleared!");
    }

    private ResponseEntity validateName(String name) {
        if (name.length() < 1) {
            return ResponseEntity.badRequest().body("Too short name, sorry :(");
        }
        if (name.length() > 20) {
            return ResponseEntity.badRequest().body("Too long name, sorry :(");
        }

        return null;
    }
}
