package fsm;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.Map;

public class YamlStateMachineTransitionProvider implements StateMachineTransitionProvider<String, String, Void> {
    @Override
    public Collection<? extends Transition<String, String, Void>> provide() {
        ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
        mapper.findAndRegisterModules();
        try {
            Transitions t = mapper.readValue(new File("src/main/resources/fsm.yaml"),
                    Transitions.class);
            return t.transitions;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static class Transitions {
        public Transitions() {}
        public Collection<BaseTransition<String, String, Void>> transitions;
        public void setTransitions(Collection<BaseTransition<String, String, Void>> transitions) {
            this.transitions = transitions;
        }
    }
}
