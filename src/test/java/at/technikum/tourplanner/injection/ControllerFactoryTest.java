package at.technikum.tourplanner.injection;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class ControllerFactoryTest {

    public static final String TEST_STRING = "TEST";

    static class TestController {
        private final String testString;

        public TestController(String testString) {
            this.testString = testString;
        }

        public String getTestString() {
            return testString;
        }
    }

    static class OtherTestController {
        public OtherTestController() {
        }
    }

    private ControllerFactory controllerFactory;

    @BeforeEach
    void setUp() {
        controllerFactory = ControllerFactory.getInstance();
        controllerFactory.removeAllControllerCreators();
    }

    @Test
    void GIVEN_controllerCreator_not_exists_AND_no_default_constructor_WHEN_create_THEN_exception_thrown() {
        assertThatThrownBy(() -> controllerFactory.create(TestController.class))
                .isInstanceOf(IllegalStateException.class);
    }

    @Test
    void GIVEN_controllerCreator_exists_WHEN_create_THEN_created_object_instance_of_correct_class(){
        controllerFactory.addControllerCreator(TestController.class, () -> new TestController(TEST_STRING));

        Object object = controllerFactory.create(TestController.class);

        assertThat(object).isInstanceOf(TestController.class);
        assertThat(((TestController)object).getTestString()).isEqualTo(TEST_STRING);
    }

    @Test
    void GIVEN_controllerCreator_not_exists_AND_default_constructor_exists_WHEN_create_THEN_object_instance_of_correct_class() {
        Object object = controllerFactory.create(OtherTestController.class);

        assertThat(object).isInstanceOf(OtherTestController.class);
    }
}