package de.c8121.packing.visualization;

import de.c8121.packing.Box;
import de.c8121.packing.util.BasicBox;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ThreeJs {

    private final List<Box> boxes = new ArrayList<>();
    private final Map<Box, String> styles = new HashMap<>();

    /**
     *
     */
    public void add(final Box box) {
        this.boxes.add(box);
    }

    /**
     * @param style Example: "color: 0x00ff00, wireframe: false, opacity: 0.25, transparent: true"
     */
    public void setStyle(final Box box, final String style) {
        this.styles.put(box, style);
    }


    /**
     * Copy all boxes which have been added to far and move them by given values.
     */
    public void copyTo(final int x, final int y, final int z) {

        var copies = new ArrayList<Box>();

        for (var box : this.boxes) {

            var copy = new BasicBox(box);
            copy.moveBy(x, y, z);
            copies.add(copy);

            var style = this.styles.get(box);
            if (style != null)
                this.styles.put(copy, style);
        }

        this.boxes.addAll(copies);
    }


    /**
     *
     */
    public void writeHtml(final File file) throws IOException {
        FileUtils.write(file, this.createHtml(), StandardCharsets.UTF_8);
    }

    /**
     *
     */
    public String createHtml() {

        var html = new StringBuilder();

        html
                .append("<!DOCTYPE html>\n")
                .append("<html>\n")
                .append("<head>\n")
                .append("<meta charset=\"utf-8\" />\n")
                .append("</head>\n")
                .append("<body>\n")
                .append("<script src=\"https://threejs.org/build/three.js\"></script>\n")
                .append("<script src=\"https://threejs.org/examples/js/controls/OrbitControls.js\"></script>\n")
                .append("<script>\n");

        html

                .append("const scene = new THREE.Scene();\n")
                .append("const camera = new THREE.PerspectiveCamera( 45, window.innerWidth / window.innerHeight, -500000, 0);\n")
                .append("camera.position.z = 55000;\n")
                .append("camera.position.y = 1;\n")
                .append("camera.position.z = 1;\n")
                .append("camera.up.set( 0, 0, 1 );\n")
                .append("const renderer = new THREE.WebGLRenderer();\n")
                .append("renderer.setSize( window.innerWidth, window.innerHeight );\n")
                .append("renderer.setClearColor( 0xeeeeee );\n")
                .append("document.body.appendChild( renderer.domElement );\n");

        html
                .append("let material;\n")
                .append("let geometry;\n")
                .append("let cube;\n");

        for (var box : this.boxes) {

            html.append("geometry = new THREE.BoxGeometry(")
                    .append(box.xs()).append(", ")
                    .append(box.ys()).append(",")
                    .append(box.zs())
                    .append(");\n");

            var style = this.styles.get(box);
            if (style == null)
                style = "color: 0x000000, wireframe: true";

            html.append("material = new THREE.MeshBasicMaterial({").append(style).append("});\n");

            html.append("cube = new THREE.Mesh(geometry, material);\n")
                    .append("cube.position.x = ").append(box.x()).append(";\n")
                    .append("cube.position.y = ").append(box.y()).append(";\n")
                    .append("cube.position.z = ").append(box.z()).append(";\n");

            html.append("scene.add(cube)\n");
        }

        html
                .append("camera.position.x = 500;\n")
                .append("camera.position.y = 500;\n")
                .append("camera.position.z = 500;\n");

        html
                .append("function animate() {\n")
                .append("renderer.render( scene, camera );\n")
                .append("}\n");

        html
                .append("var controls = new THREE.OrbitControls( camera, renderer.domElement );\n")
                .append("controls.addEventListener( 'change', animate );\n")
                .append("animate();\n");

        html
                .append("</script>\n")
                .append("</body>\n")
                .append("</html>\n");


        return html.toString();
    }

}
