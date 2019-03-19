package ru.atom.chat.client;

import okhttp3.Response;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import ru.atom.chat.client.ChatClient;
import ru.atom.chat.server.ChatApplication;

import java.io.IOException;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {ChatApplication.class}, webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class ChatClientTest {
    private static String MY_NAME_IN_CHAT = "I_AM_STUPID";
    private static String MY_MESSAGE_TO_CHAT = "SOMEONE_KILL_ME";
    private static String MY_NEW_NAME = "I_AM_CLEVER";

    @Test
    public void login() throws IOException {
        Response response = ChatClient.login(MY_NAME_IN_CHAT);
        System.out.println("[" + response + "]");
        String body = response.body().string();
        System.out.println();
        Assert.assertTrue(response.code() == 200 || body.equals("Already logged in:("));
    }

    @Test
    public void logout() throws IOException {
        ChatClient.login(MY_NAME_IN_CHAT);
        Response response = ChatClient.logout(MY_NAME_IN_CHAT);
        System.out.println("[" + response + "]");
        Assert.assertEquals("You have successfully logged in", response.body().string());
    }

    @Test
    public void viewChat() throws IOException {
        ChatClient.login(MY_NAME_IN_CHAT);
        ChatClient.clearHistory();
        ChatClient.say(MY_NAME_IN_CHAT, MY_MESSAGE_TO_CHAT);
        Response response = ChatClient.viewChat();
        System.out.println("[" + response + "]");
        Assert.assertEquals("[I_AM_STUPID]: SOMEONE_KILL_ME", response.body().string());
    }


    @Test
    public void viewOnline() throws IOException {
        ChatClient.login(MY_NAME_IN_CHAT);
        Response response = ChatClient.viewOnline();
        System.out.println("[" + response + "]");
        Assert.assertEquals("I_AM_STUPID", response.body().string());
    }

    @Test
    public void say() throws IOException {
        ChatClient.login(MY_NAME_IN_CHAT);
        Response response = ChatClient.say(MY_NAME_IN_CHAT, MY_MESSAGE_TO_CHAT);
        System.out.println("[" + response + "]");
        Assert.assertEquals("The message has been successfully sent", response.body().string());
    }

    @Test
    public void changeUserName() throws IOException {
        ChatClient.login(MY_NAME_IN_CHAT);
        Response response = ChatClient.changeUserName(MY_NAME_IN_CHAT, MY_NEW_NAME);
        System.out.println("[" + response + "]");
        Assert.assertEquals("Your name has been successfully changed!", response.body().string());
    }

    @Test
    public void clearHistory() throws IOException {
        ChatClient.login(MY_NAME_IN_CHAT);
        Response response = ChatClient.clearHistory();
        System.out.println("[" + response + "]");
        Assert.assertEquals("The chat has been successfully cleared!", response.body().string());
    }
}
