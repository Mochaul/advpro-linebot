package advprog.NSFW.bot.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;


import advprog.NSFW.bot.EventTestUtil;
import com.linecorp.bot.model.event.Event;
import com.linecorp.bot.model.event.MessageEvent;
import com.linecorp.bot.model.event.message.ImageMessageContent;
import com.linecorp.bot.model.event.message.TextMessageContent;
import com.linecorp.bot.model.message.TextMessage;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.io.IOException;

@SpringBootTest(properties = "line.bot.handler.enabled=false")
@ExtendWith(SpringExtension.class)
public class NSFWControllerTest {

    static {
        System.setProperty("line.bot.channelSecret", "SECRET");
        System.setProperty("line.bot.channelToken", "TOKEN");
    }

    @Autowired
    private NSFWController nsfwController;

    @Test
    void testContextLoads() {
        assertNotNull(nsfwController);
    }

    @Test
    void testHandleTextMessageEvent() {
        MessageEvent<TextMessageContent> event =
                EventTestUtil.createDummyTextMessage("/is_sfw https://cdn.pornpics.com/pics1/2017-08-06/474907_16big.jpg");

        TextMessage reply = nsfwController.handleTextMessageEvent(event);

        assertEquals("nsfw", reply.getText());
    }

    @Test
    void testHandleTextMessageEventInput(){
        MessageEvent<TextMessageContent> event =
                EventTestUtil.createDummyTextMessage("/tes");

        TextMessage reply = nsfwController.handleTextMessageEvent(event);

        assertEquals("Inputan tidak tersedia nih, coba /is_sfw atau masukan gambar", reply.getText());
    }

    @Test
    void testHandleTextMessageEventLink(){
        MessageEvent<TextMessageContent> event =
                EventTestUtil.createDummyTextMessage("/is_sfw test");

        TextMessage reply = nsfwController.handleTextMessageEvent(event);

        assertEquals("Link yang kamu masukkan tidak benar, masukkan link yang benar ya :)", reply.getText());
    }

    @Test
    void testHandleImageMessageEvent() throws IOException {
        MessageEvent<ImageMessageContent> event =
                EventTestUtil.createDummyImageMessage();

        TextMessage reply = nsfwController.handleImageMessageEvent(event);

        assertEquals("nsfw", reply.getText());
    }

    @Test
    void testHandleDefaultMessage() {
        Event event = mock(Event.class);

        nsfwController.handleDefaultMessage(event);

        verify(event, atLeastOnce()).getSource();
        verify(event, atLeastOnce()).getTimestamp();
    }
}