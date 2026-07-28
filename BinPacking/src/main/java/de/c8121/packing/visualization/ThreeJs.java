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
     * Copy all boxes which have been added so far and move them by given values.
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
                .append("""
                        <!DOCTYPE html>
                        <html>
                        <head>
                        <meta charset="utf-8" />
                        </head>
                        <body>
                        
                        <select id="cubeSelect">
                            <option>Select</option>
                        </select>
                        
                        <script type="importmap">
                        {
                            "imports": {
                                "three": "https://cdn.jsdelivr.net/npm/three@v0.185.1/build/three.module.js",
                                "three/examples/jsm/controls/OrbitControls": "https://cdn.jsdelivr.net/npm/three@0.185.1/examples/jsm/controls/OrbitControls.js",
                                "three/examples/jsm/libs/stats.module": "https://cdn.jsdelivr.net/npm/three@0.185.1/examples/jsm/libs/stats.module.js"
                            }
                        }
                        </script>
                        
                        <script type="module">
                        
                        import * as THREE from 'three';
                        import {OrbitControls} from 'three/examples/jsm/controls/OrbitControls';
                        import Stats from 'three/examples/jsm/libs/stats.module';
                        
                        const scene = new THREE.Scene();
                        
                        const camera = new THREE.PerspectiveCamera( 45, window.innerWidth / window.innerHeight, -500000, 0);
                        camera.position.z = 55000;
                        camera.position.y = 1;
                        camera.position.z = 1;
                        camera.up.set( 0, 0, 1 );
                        
                        const renderer = new THREE.WebGLRenderer();
                        renderer.setSize( window.innerWidth, window.innerHeight );
                        renderer.setClearColor( 0xeeeeee );
                        document.body.appendChild( renderer.domElement );
                        
                        const cubes = [];
                        const cubeSelect = document.getElementById("cubeSelect");
                        
                        function addCube(x, y, z, xs, ys, zs, style, selectable) {
                           const geometry = new THREE.BoxGeometry(xs, ys, zs);
                           const material = new THREE.MeshBasicMaterial(style);
                           const cube = new THREE.Mesh(geometry, material);
                           cube.position.x = x;
                           cube.position.y = y;
                           cube.position.z = z;
                        
                           scene.add(cube);
                        
                           if(selectable) {
                                cubes.push(cube);
                                cubeSelect.append(new Option(cubes.length));
                           }
                        
                           return cube;
                        }
                        
                        """);

        for (var box : this.boxes) {

            var style = this.styles.get(box);
            if (style == null)
                style = "color: 0x000000, wireframe: true";

            html.append("addCube(")
                    .append(box.x()).append(", ")
                    .append(box.y()).append(", ")
                    .append(box.z()).append(", ")
                    .append(box.xs()).append(", ")
                    .append(box.ys()).append(", ")
                    .append(box.zs()).append(", ")
                    .append("{").append(style).append("}").append(", ")
                    .append("true")
                    .append(");\n");
        }


        html.append("""
                camera.position.x = 500;
                camera.position.y = 500;
                camera.position.z = 500;
                
                let highlightCube = null;
                
                cubeSelect.addEventListener("change", (e) => {
                    const selectedIdx = e.target.value;
                    if(isNaN(selectedIdx))
                        return;
                
                    const cube = cubes[selectedIdx-1];
                    const size = cube.geometry.parameters;
                    const pos = cube.position;
                
                    if( highlightCube != null ) {
                        scene.remove(highlightCube);
                    }
                    highlightCube = addCube(pos.x, pos.y, pos.z, size.width, size.height, size.depth, {color: 'green', wireframe: true}, false);   \s
                
                    animate();
                });

                function animate() {
                    renderer.render( scene, camera );
                }
                
                var controls = new OrbitControls( camera, renderer.domElement );
                controls.addEventListener( 'change', animate );
                animate();
                </script>
                </body>
                </html>
                """);

        return html.toString();
    }

}
