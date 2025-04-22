package tpo.task3;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;

public class DomainModelTest {
    private Environment env;
    private SwitchDevice sw;
    private Match match;
    private Ford ford;
    private Arthur arthur;

    @BeforeEach
    public void setUp() {
        env = new Environment();
        sw = new SwitchDevice();
        match = new Match();
        ford = new Ford();
        arthur = new Arthur();
    }

    // =========================================================================
    // Environment Tests
    // =========================================================================

    @Test
    public void testEnvironmentInitialValues() {
        assertEquals(Ambience.DARK, env.getAmbience());
        assertEquals(7, env.getSmellIntensity());
        assertEquals(5, env.getHumLevel());
        assertEquals(3, env.getShadows().size());
        for (Shadow s : env.getShadows()) {
            // При DARK-среде тень должна быть MOVING
            assertEquals(ShadowState.MOVING, s.getState());
        }
    }

    @Test
    public void testSetAmbience_DARK() {
        env.setAmbience(Ambience.DARK);
        for (Shadow s : env.getShadows()) {
            assertEquals(ShadowState.MOVING, s.getState());
        }
    }

    @Test
    public void testSetAmbience_SHADOWY() {
        env.setAmbience(Ambience.SHADOWY);
        // Для SHADOWY, если интенсивность запаха > 5, тени становятся DANCING
        for (Shadow s : env.getShadows()) {
            assertEquals(ShadowState.DANCING, s.getState());
        }
    }

    @Test
    public void testSetAmbience_DIM() {
        env.setAmbience(Ambience.DIM);
        // При DIM, при smellIntensity=7 (по умолчанию) тени становятся AGITATED
        for (Shadow s : env.getShadows()) {
            assertEquals(ShadowState.AGITATED, s.getState());
        }
    }

    @Test
    public void testSetAmbience_BRIGHT() {
        env.setAmbience(Ambience.BRIGHT);
        for (Shadow s : env.getShadows()) {
            assertEquals(ShadowState.STATIC, s.getState());
        }
    }

    @Test
    public void testSetAmbience_DAZZLING() {
        env.setAmbience(Ambience.DAZZLING);
        for (Shadow s : env.getShadows()) {
            assertEquals(ShadowState.STATIC, s.getState());
        }
    }

    @Test
    public void testUpdateShadowStates_WithChangedSmell() {
        env.setAmbience(Ambience.DIM);
        // При smellIntensity = 7 и humLevel = 5 (по умолчанию) условие (7 > 6 или 5 > 6) выполнено – AGITATED
        for (Shadow s : env.getShadows()) {
            assertEquals(ShadowState.AGITATED, s.getState());
        }
        // Изменим интенсивность запаха, чтобы условие не выполнялось
        env.setSmellIntensity(5);
        // Вручную вызываем updateShadowStates
        env.updateShadowStates();
        for (Shadow s : env.getShadows()) {
            // Теперь при DIM (и при humLevel=5, smellIntensity=5) тени переходят в MOVING
            assertEquals(ShadowState.MOVING, s.getState());
        }
    }

    @Test
    public void testUpdateShadowStates_WithChangedHum() {
        env.setAmbience(Ambience.DIM);
        env.setHumLevel(7);
        env.updateShadowStates();
        for (Shadow s : env.getShadows()) {
            assertEquals(ShadowState.AGITATED, s.getState());
        }
        env.setSmellIntensity(5);
        env.setHumLevel(5);
        env.updateShadowStates();
        for (Shadow s : env.getShadows()) {
            assertEquals(ShadowState.MOVING, s.getState());
        }
    }

    @Test
    public void testShadowySmellIntensityLow() {
        // Проверяем ветку else при SHADOWY (если smellIntensity <= 5, тени должны быть MOVING)
        env.setAmbience(Ambience.SHADOWY);
        // Устанавливаем smellIntensity меньше или равным 5,
        // чтобы проверить ветвь else (shadow.setState(MOVING))
        env.setSmellIntensity(3);
        // Явно вызываем обновление теней (в реальном коде это вызывается внутри setSmellIntensity, но для наглядности дублируем)
        env.updateShadowStates();

        for (Shadow shadow : env.getShadows()) {
            // Ожидаем, что тени перешли в MOVING, а не DANCING
            assertEquals(ShadowState.MOVING, shadow.getState());
        }
    }

    @Test
    public void testDimSmellAndHumLow() {

        env.setAmbience(Ambience.DIM);
        // По условию if (smellIntensity > 6 || humLevel > 6) => AGITATED, иначе => MOVING
        // Устанавливаем smellIntensity = 6, humLevel = 6, чтобы НЕ выполнялось условие >6
        env.setSmellIntensity(6);
        env.setHumLevel(6);
        env.updateShadowStates();

        for (Shadow shadow : env.getShadows()) {
            // Ожидаем, что тени MOVING, а не AGITATED
            assertEquals(ShadowState.MOVING, shadow.getState());
        }
    }

    // =========================================================================
    // SwitchDevice Tests
    // =========================================================================

    @Test
    public void testSwitchDeviceToggleNormal() {
        assertFalse(sw.isOn());
        sw.toggle();
        assertTrue(sw.isOn());
        sw.toggle();
        assertFalse(sw.isOn());
    }

    @Test
    public void testSwitchDeviceMalfunction() {
        sw.setMalfunctioning(true);
        assertTrue(sw.isMalfunctioning());
        // Попытка переключения при неисправном состоянии ничего не меняет
        sw.toggle();
        assertFalse(sw.isOn());
        sw.setMalfunctioning(false);
        sw.toggle();
        assertTrue(sw.isOn());
    }

    // =========================================================================
    // Match Tests
    // =========================================================================

    @Test
    public void testMatchInitialState() {
        assertEquals(MatchState.UNLIT, match.getState());
    }

    @Test
    public void testMatchLighting() {
        match.light();
        assertEquals(MatchState.LIT, match.getState());
    }

    @Test
    public void testMatchBurnTimeUpdate() {
        match.light();
        match.updateBurnTime(3);
        assertEquals(MatchState.LIT, match.getState());
        match.updateBurnTime(3);
        assertEquals(MatchState.BURNED, match.getState());
    }

    // =========================================================================
    // Shadow Tests
    // =========================================================================

    @Test
    public void testShadowStateChange() {
        Shadow shadow = new Shadow();
        assertEquals(ShadowState.MOVING, shadow.getState());
        shadow.setState(ShadowState.DANCING);
        assertEquals(ShadowState.DANCING, shadow.getState());
    }

    // =========================================================================
    // Ford Tests
    // =========================================================================

    @Test
    public void testFordIgniteMatch_FromDark() {
        env.setAmbience(Ambience.DARK);
        assertEquals(MatchState.UNLIT, match.getState());
        ford.igniteMatch(match, env);
        assertEquals(MatchState.LIT, match.getState());
        assertEquals(Ambience.SHADOWY, env.getAmbience());
    }

    @Test
    public void testFordIgniteMatch_FromShadowy() {
        env.setAmbience(Ambience.SHADOWY);
        ford.igniteMatch(match, env);
        assertEquals(MatchState.LIT, match.getState());
        assertEquals(Ambience.DIM, env.getAmbience());
    }

    @Test
    public void testFordIgniteMatch_AlreadyLit() {
        match.light();
        env.setAmbience(Ambience.DARK);
        ford.igniteMatch(match, env);
        assertEquals(MatchState.LIT, match.getState());
        assertEquals(Ambience.DARK, env.getAmbience());
    }

    @Test
    public void testFordSearchAndToggleSwitch_FromDim() {
        env.setAmbience(Ambience.DIM);
        assertFalse(sw.isOn());
        ford.searchAndToggleSwitch(sw, env);
        // При первом вызове: найден переключатель, переключатель включается, а среда переходит в BRIGHT
        assertTrue(sw.isOn());
        assertEquals(Ambience.BRIGHT, env.getAmbience());
        // При повторном вызове переключатель выключается, а среда переходит в DIM
        ford.searchAndToggleSwitch(sw, env);
        assertFalse(sw.isOn());
        assertEquals(Ambience.DIM, env.getAmbience());
    }
    @Test
    public void testFordSearchAndToggleSwitch_FromDim2() {
        env.setAmbience(Ambience.DARK);
        assertFalse(sw.isOn());
        ford.searchAndToggleSwitch(sw, env);
    }

    @Test
    public void testFordSearchAndToggleSwitch_FromDim3() {
        env.setAmbience(Ambience.DARK);
        assertFalse(sw.isOn());
        ford.searchAndToggleSwitch(sw, env);
        ford.searchAndToggleSwitch(sw, env);
    }


    // тест для searchAndToggleSwitch с учетом неисправности переключателя
    @Test
    public void testFordSearchAndToggleSwitch_MalfunctionScenario() {
        // Задаем начальное условие DIM
        env.setAmbience(Ambience.DIM);
        // Первый вызов: переключатель должен быть найден и переведен в состояние ON
        ford.searchAndToggleSwitch(sw, env);
        assertTrue(sw.isOn());
        assertEquals(Ambience.BRIGHT, env.getAmbience());
        // Помечаем переключатель как неисправный
        sw.setMalfunctioning(true);
        // При вызове переключения с неисправным переключателем toggle() не изменит состояние,
        // но логика всё равно проверит и, при условии, что ambiance сейчас BRIGHT, установит DAZZLING.
        ford.searchAndToggleSwitch(sw, env);
        // Переключатель остается в ON, а среда переходит в DAZZLING
        assertTrue(sw.isOn());
        assertEquals(Ambience.DAZZLING, env.getAmbience());
        // Убираем неисправность и снова вызываем переключение – переключатель должен переключиться в OFF
        sw.setMalfunctioning(false);
        ford.searchAndToggleSwitch(sw, env);
        assertFalse(sw.isOn());
        assertEquals(Ambience.DIM, env.getAmbience());
    }

    // =========================================================================
    // Arthur Tests
    // =========================================================================

    @Test
    public void testArthurStandUp() {
        assertEquals(CharacterState.STUNNED, arthur.getState());
        arthur.standUp();
        assertEquals(CharacterState.AWAKE, arthur.getState());
        arthur.standUp();
        assertEquals(CharacterState.AWAKE, arthur.getState());
    }

    @Test
    public void testArthurPerceiveEnvironment_Disoriented() {
        env.setAmbience(Ambience.DARK);
        arthur.perceiveEnvironment(env);
        assertEquals(CharacterState.DISORIENTED, arthur.getState());
    }

    @Test
    public void testArthurPerceiveEnvironment_Confused() {
        env.setAmbience(Ambience.DIM);
        env.setSmellIntensity(5);
        arthur.perceiveEnvironment(env);
        assertEquals(CharacterState.CONFUSED, arthur.getState());
    }

    @Test
    public void testArthurPerceiveEnvironment_Determined() {
        env.setAmbience(Ambience.BRIGHT);
        arthur.perceiveEnvironment(env);
        assertEquals(CharacterState.DETERMINED, arthur.getState());
        env.setAmbience(Ambience.DAZZLING);
        arthur.perceiveEnvironment(env);
        assertEquals(CharacterState.DETERMINED, arthur.getState());
    }

    @Test
    public void testArthurPerceiveEnvironment_WithAgitatedShadows() {
        env.setAmbience(Ambience.DIM);
        // По умолчанию smellIntensity=7 → тени становятся AGITATED, что вызывает DISORIENTED
        arthur.perceiveEnvironment(env);
        assertEquals(CharacterState.DISORIENTED, arthur.getState());
    }

    // =========================================================================
    // Дополнительные тесты для методов getName() и setState() класса Character
    // =========================================================================

    @Test
    public void testCharacterGetName() {
        // Проверяем метод getName() для Ford и Arthur
        assertEquals("Ford", ford.getName());
        assertEquals("Arthur", arthur.getName());
    }

    @Test
    public void testCharacterSetState() {
        // Устанавливаем новое состояние для Ford
        ford.setState(CharacterState.INJURED);
        assertEquals(CharacterState.INJURED, ford.getState());
        // Устанавливаем другое состояние для Arthur
        arthur.setState(CharacterState.DETERMINED);
        assertEquals(CharacterState.DETERMINED, arthur.getState());
    }
}
