/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MapGui;

import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.ScrollEvent;
import javafx.scene.transform.Scale;

/**
 *
 * @author acer
 */
public class Scroll extends ScrollPane {

    Group zoom;
    Scale scale;
    Node content;
    double scaleValue = 1.0;
    double change = 0.1;

    public Scroll(Node content) {
        this.content = content;
        Group contentGroup = new Group();
        zoom = new Group();
        contentGroup.getChildren().add(zoom);
        zoom.getChildren().add(content);
        setContent(contentGroup);
        scale = new Scale(scaleValue, scaleValue, 0, 0);
        zoom.getTransforms().add(scale);
        zoom.setOnScroll(new ZoomHandler());
    }

    public double getScaleValue() {
        return scaleValue;
    }

    public void Actual() {
        zoomTo(1.0);
    }

    public void zoomTo(double scaleValue) {
        this.scaleValue = scaleValue;
        scale.setX(scaleValue);
        scale.setY(scaleValue);
    }

    public void zoomActual() {
        scaleValue = 1;
        zoomTo(scaleValue);
    }

    public void Out() {
        scaleValue -= change;
        if (Double.compare(scaleValue, 0.1) < 0) {
            scaleValue = 0.1;
        }
        zoomTo(scaleValue);
    }

    public void In() {
        scaleValue += change;
        if (Double.compare(scaleValue, 10) > 0) {
            scaleValue = 10;
        }
        zoomTo(scaleValue);
    }

    public void Fit(boolean minimize) {
        double scaleX = getViewportBounds().getWidth() / getContent().getBoundsInLocal().getWidth();
        double scaleY = getViewportBounds().getHeight() / getContent().getBoundsInLocal().getHeight();
        scaleX *= scaleValue;
        scaleY *= scaleValue;
        double scale = Math.min(scaleX, scaleY);

        if (minimize) {
            if (Double.compare(scale, 1) > 0) {
                scale = 1;
            }
        }
        zoomTo(scale);
    }

    private class ZoomHandler implements EventHandler<ScrollEvent> {
        public void handle(ScrollEvent event) {
            if (event.isControlDown()) {
                if (event.getDeltaY() < 0) {
                    scaleValue -= change;
                } else {
                    scaleValue += change;
                }
                zoomTo(scaleValue);
                event.consume();
            }
        }
    }
}
