package tpo.task3;

import org.junit.Test;
import static org.junit.Assert.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

public class DomainModelTest {

    private ByteArrayOutputStream captureOutput() {
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        return outContent;
    }

    @Test
    public void testIgniteMatchAction() {

        ByteArrayOutputStream outContent = captureOutput();
        Match match = new Match("Спичка");
        Switch lightSwitch = new Switch("Выключатель");
        IgniteMatchAction action = new IgniteMatchAction(match);

        action.execute();

        String output = outContent.toString();
        assertTrue(output.contains("Используется Спичка"));
        assertTrue(output.contains("Спичка используется для зажигания."));
    }

    @Test
    public void testMoveAction() {

        ByteArrayOutputStream outContent = captureOutput();
        Arthur arthur = new Arthur("Артур");
        MoveAction moveAction = new MoveAction(arthur);

        moveAction.execute();

        String output = outContent.toString();
        assertTrue(output.contains("Артур пытается встать на ноги."));
    }

    @Test
    public void testTouchAction() {
        ByteArrayOutputStream outContent = captureOutput();
        Arthur arthur = new Arthur("Артур");
        TouchAction touchAction = new TouchAction(arthur);

        touchAction.execute();

        String output = outContent.toString();
        assertTrue(output.contains("Артур ощупывает себя, пытаясь понять свое состояние."));
    }

    @Test
    public void testSensoryInputsProcessing() {
        ByteArrayOutputStream outContent = captureOutput();

        VisualInput visualInput = new VisualInput("Огромные тени мелькают вокруг");
        SmellInput smellInput = new SmellInput("Запах прелых, неидентифицируемых ароматов");
        SoundInput soundInput = new SoundInput("Низкий гул мешает сосредоточиться");

        visualInput.process();
        smellInput.process();
        soundInput.process();

        String output = outContent.toString();
        assertTrue(output.contains("Обработка визуальных стимулов: Огромные тени мелькают вокруг"));
        assertTrue(output.contains("Обработка запаха: Запах прелых, неидентифицируемых ароматов"));
        assertTrue(output.contains("Обработка звуков: Низкий гул мешает сосредоточиться"));
    }

    @Test
    public void testCharacterPerformActionAndPerceive() {
        ByteArrayOutputStream outContent = captureOutput();

        Ford ford = new Ford("Форд");
        Arthur arthur = new Arthur("Артур");

        Match match = new Match("Спичка");
        Switch lightSwitch = new Switch("Выключатель");
        IgniteMatchAction igniteAction = new IgniteMatchAction(match);
        ford.performAction(igniteAction);

        VisualInput visualInput = new VisualInput("Огромные тени мелькают вокруг");
        arthur.perceive(visualInput);

        String output = outContent.toString();
        assertTrue(output.contains("Форд начинает действие: Зажечь спичку для поиска выключателя"));
        assertTrue(output.contains("Используется Спичка"));
        assertTrue(output.contains("Обработка визуальных стимулов: Огромные тени мелькают вокруг"));
    }

    @Test
    public void testShadowInteraction() {
        ByteArrayOutputStream outContent = captureOutput();

        Shadow shadow = new Shadow("Чудовищная тень");
        shadow.interact();

        String output = outContent.toString();
        assertTrue(output.contains("Тень мелькает и движется."));
    }

    @Test
    public void testInhumanFigureInteraction() {
        ByteArrayOutputStream outContent = captureOutput();

        InhumanFigure figure = new InhumanFigure("Нечеловеческая фигура");
        figure.interact();

        String output = outContent.toString();
        assertTrue(output.contains("Нечеловеческая фигура вызывает тревогу."));
    }
}
