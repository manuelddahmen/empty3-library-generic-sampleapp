/*
 * Copyright (c) 2023. Manuel Daniel Dahmen
 *
 *
 *    Copyright 2012-2023 Manuel Daniel Dahmen
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

package one.empty3.sampleapp.tests;

import one.empty3.feature.PixM;
import one.empty3.feature.app.replace.javax.imageio.ImageIO;
import one.empty3.library.*;
import one.empty3.library.core.extra.VoronoiImageTexture;
import one.empty3.library.core.testing.TestObjetSub;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;

public class Sunset extends TestObjetSub {
    private static final int VUE_1 = 30;
    private static final int FPS = 50;
    Plane polygon = new Plane();
    Plane polygon1 = new Plane();

    public static void main(String[] args) {
        Sunset sunset = new Sunset();
        sunset.loop(true);
        sunset.setMaxFrames(VUE_1*FPS);
        new Thread(sunset).start();
    }

    @Override
    public void ginit() {
        super.ginit();
        BufferedImage read = ImageIO.read(new File("resources/dup12138.jpg"));
        VoronoiImageTexture voronoiImageTexture = new VoronoiImageTexture();
        PixM image = voronoiImageTexture.processInMemory(new PixM(read));
        try {
            polygon1.texture(new ImageTexture(new ECBufferedImage(image.getImage())));
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    @Override
    public void finit() throws Exception {
        super.finit();

        if (frame() < VUE_1 * FPS) {
            //z().setDisplayType(Representable.DISPLAY_ALL);
            z().texture(new ColorTexture(Color.BLACK));
            Plane polygon = new Plane();
            Plane polygon1 = new Plane();
            StructureMatrix<Point3D> mat = new StructureMatrix<>(2, Point.class);
            mat.setElem(new Point3D(-10d, 0d, -10d), 0, 0);
            mat.setElem(new Point3D(10d, 0d, -10d), 1, 0);
            mat.setElem(new Point3D(10d, 0d, 10d), 1, 1);
            mat.setElem(new Point3D(-10d, 0d, 10d), 0, 1);
        /*polygon.setMatrix( new Point3D[][]{
                {mat.getElem(0, 0), mat.getElem(1, 0)}, {mat.getElem(1, 0), mat.getElem(1, 1)}
        });*/

            scene().clear();

/*
            polygon.setPoints(
                    new Point3D[]{
                            mat.getElem(0, 0), mat.getElem(1, 0), mat.getElem(1, 1), mat.getElem(0, 1)
                    });
            polygon.setP0(mat.getElem(0, 0));

            polygon1.setPoints(
                    new Point3D[]{
                            mat.getElem(0, 0).plus(Point3D.Y), mat.getElem(1, 0).plus(Point3D.Y),
                            mat.getElem(1, 1).plus(Point3D.Y), mat.getElem(0, 1).plus(Point3D.Y)
                    });
*/
            Point3D[] vects = new Point3D[]{mat.getElem(0, 0), mat.getElem(0, 1), mat.getElem(1, 0)};

            StructureMatrix<Point3D>[] v = new StructureMatrix[] {
                    new StructureMatrix<Point3D>(0, Point3D.class),
                    new StructureMatrix<Point3D>(0, Point3D.class),
                    new StructureMatrix<Point3D>(0, Point3D.class)};


            v[0].setElem(vects[0]);
            v[1].setElem(vects[1]);
            v[2].setElem(vects[2]);

            polygon.setP0(v[0]);
            polygon.setvX(v[1]);
            polygon.setvY(v[2]);

            polygon.texture(new ColorTexture(new Color(0f, 0.2f, 1.0f)));

            StructureMatrix<Point3D>[] v1 = new StructureMatrix[] {
                    new StructureMatrix<Point3D>(0, Point3D.class),
                    new StructureMatrix<Point3D>(0, Point3D.class),
                    new StructureMatrix<Point3D>(0, Point3D.class)};


            v1[0].setElem(vects[0].plus(Point3D.Y));
            v1[1].setElem(vects[1].plus(Point3D.Y));
            v1[2].setElem(vects[2].plus(Point3D.Y));

            polygon1.setP0(v1[0]);
            polygon1.setvX(v1[1]);
            polygon1.setvY(v1[2]);


            polygon.setIncrU(0.003);
            polygon.setIncrU(0.003);
            polygon1.setIncrV(0.003);
            polygon1.setIncrV(0.003);

            scene().add(polygon);
            scene().add(polygon1);
            Point3D eye = new Point3D(5.0, 0.2, 5.0);
            Point3D lookAt = new Point3D(-5.0, 0.2, 5.0);
            Point3D pos = eye.plus(lookAt.moins(eye).mult(1.0*frame() / (FPS * VUE_1)));
            Camera camera = new Camera(pos, lookAt, Point3D.Y);
            camera.calculerMatrice(Point3D.Y);
            scene().cameraActive(camera);
        }

    }
}
