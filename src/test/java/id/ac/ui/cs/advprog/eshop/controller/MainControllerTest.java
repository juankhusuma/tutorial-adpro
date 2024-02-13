package id.ac.ui.cs.advprog.eshop.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class MainControllerTest {
    @InjectMocks
    MainController mainController = new MainController();

    @Test
    void testHomePage() {
        assertEquals(mainController.homePage(), "welcomePage");
    }

    @Test
    void testErrorPage() {
        assertEquals(mainController.errorPage(), "errorPage");
    }
}
