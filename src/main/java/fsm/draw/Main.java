package fsm.draw;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import fsm.Transition;
import fsm.YamlStateMachineTransitionProvider;
import guru.nidi.graphviz.attribute.Font;
import guru.nidi.graphviz.attribute.Label;
import guru.nidi.graphviz.attribute.Rank;
import guru.nidi.graphviz.attribute.Style;
import guru.nidi.graphviz.engine.Format;
import guru.nidi.graphviz.engine.Graphviz;
import guru.nidi.graphviz.model.Graph;
import guru.nidi.graphviz.model.Node;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static guru.nidi.graphviz.attribute.Rank.RankDir.LEFT_TO_RIGHT;
import static guru.nidi.graphviz.model.Factory.graph;
import static guru.nidi.graphviz.model.Factory.node;
import static guru.nidi.graphviz.model.Link.to;

public class Main {

    public static void main(String[] args) throws IOException {
        //inter();
        yaml();
    }

    private static void yaml() throws IOException {
        Collection<? extends Transition<String, String, Void>> t = new YamlStateMachineTransitionProvider()
                .provide();
        draw(new File("yaml.png"), t);
    }

    private static void inter() throws IOException {
        List<Transition<String,String, Void>> transitions = new AccessingAllClassesInPackage()
                .findAllClassesUsingClassLoader("fsm.transitions.inter")
                .stream()
                .filter(Transition.class::isAssignableFrom)
                .map(c -> {
                    try {
                        return (Transition<String,String, Void>)c.newInstance();
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                })
                .collect(Collectors.toList());
        draw(new File("inter.png"), transitions);
    }

    public static void draw(File file, Collection<? extends Transition<String, String, Void>> transitions) throws IOException {
        List<Node> t = transitions.stream().map(tt -> {
            /*return (node(tt.getSource().toString())
                    .link(node(tt.getDestination().toString()))
            ).with(Label.of("fdfd"));*/
            Node s = node(tt.getSource().toString());
            Node d = node(tt.getDestination().toString());
            return s.link(
                    to(d).with(Style.BOLD, Label.of(tt.getTrigger().toString())));

        }).collect(Collectors.toList());
        Graph g = graph("example1").directed()
                .graphAttr().with(Rank.dir(LEFT_TO_RIGHT))
                .nodeAttr().with(Font.name("arial"))
                .linkAttr().with("class", "link-class")
                .with(t);
       /* Graph g = graph("example1").directed()
                .graphAttr().with(Rank.dir(LEFT_TO_RIGHT))
                .nodeAttr().with(Font.name("arial"))
                .linkAttr().with("class", "link-class")
                .with(
                        node("a").with(Color.RED).link(node("b"))
                );*/
        Graphviz.fromGraph(g).height(100).render(Format.PNG).toFile(file);
    }
}
