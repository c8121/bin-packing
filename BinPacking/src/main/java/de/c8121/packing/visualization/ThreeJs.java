package de.c8121.packing.visualization;

import de.c8121.packing.Box;
import de.c8121.packing.Item;
import de.c8121.packing.packers.Placement;
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
    private final Map<Box,String> styles = new HashMap<>();

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
            if( style==null)
                style = "color: 0xffff00, wireframe: true";

            html.append("material = new THREE.MeshBasicMaterial({").append(style).append("});\n");

            html.append("cube = new THREE.Mesh(geometry, material);\n")
                    .append("cube.position.x = ").append(box.x() + box.xs() / 2).append(";\n")
                    .append("cube.position.y = ").append(box.y() + box.ys() / 2).append(";\n")
                    .append("cube.position.z = ").append(box.z() + box.zs() / 2).append(";\n");

            html.append("scene.add(cube)\n");
        }

        html
                .append("camera.position.z = 5;\n");

        html
                .append("function animate() {\n")
                .append("renderer.render( scene, camera );\n")
                .append("}\n")
                .append("animate();\n");

        html
                .append("var controls = new THREE.OrbitControls( camera, renderer.domElement );\n")
                .append("controls.addEventListener( 'change', animate );\n")
        ;

        html
                .append("</script>\n")
                .append("</body>\n")
                .append("</html>\n");


        return html.toString();
    }

}
