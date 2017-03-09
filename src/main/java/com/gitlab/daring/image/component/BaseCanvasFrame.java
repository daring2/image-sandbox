package com.gitlab.daring.image.component;

import org.bytedeco.javacpp.opencv_core.Mat;
import org.bytedeco.javacv.CanvasFrame;
import org.bytedeco.javacv.Frame;
import org.bytedeco.javacv.OpenCVFrameConverter.ToMat;

import java.awt.*;

import static com.gitlab.daring.image.swing.SwingUtils.runInEdt;
import static com.gitlab.daring.image.util.GeometryUtils.scaleToMax;

public class BaseCanvasFrame extends CanvasFrame {

    final ToMat converter = new ToMat();
    final Dimension maxSize = new Dimension(1024, 768);

    public BaseCanvasFrame(String title) {
        super(title, 1);
        setVisible(false);
    }

    public void showMat(Mat m) {
        Frame cm = converter.convert(m);
        runInEdt(() -> showImage(cm));
    }

    @Override
    public void showImage(Image image) {
        if (!isVisible()) needInitialResize = true;
        super.showImage(image);
        if (!isVisible()) {
            resize();
            setVisible(true);
        }
    }

    public void resize() {
        Dimension d = scaleToMax(getCanvasSize(), maxSize);
        setCanvasSize(d.width, d.height);
    }

}
