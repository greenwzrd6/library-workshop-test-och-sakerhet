package se.yrgo.libraryapp.controllers;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.*;

import org.junit.jupiter.api.*;
import org.mockito.*;
import org.mockito.junit.jupiter.*;
import org.mockito.quality.*;

import se.yrgo.libraryapp.dao.*;
import se.yrgo.libraryapp.entities.*;

@MockitoSettings(strictness = Strictness.STRICT_STUBS)
public class ClassificationControllerTest {
    @Mock
    private ClassificationDao classDao;

    private ClassificationController controller;

    @BeforeEach
    void setUp() {
        controller = new ClassificationController(classDao);
    }

    @Test
    void getAvailableReturnsAvailableClassifications() {
        final List<DdsClassification> expected = List.of(
                new DdsClassification(0, "Computer science, information and general works"),
                new DdsClassification(5, "Computer programming, programs and data"),
                new DdsClassification(898, "Scandinavian"));

        when(classDao.getAvailable()).thenReturn(new ArrayList<>(expected));

        List<DdsClassification> result = controller.getAvailable();

        assertThat(result).containsExactlyElementsOf(expected);
    }

    @Test
    void getAvailableReturnsEmptyListWhenNoAvailable() {
        when(classDao.getAvailable()).thenReturn(List.of());

        assertThat(controller.getAvailable()).isEmpty();
    }
}
