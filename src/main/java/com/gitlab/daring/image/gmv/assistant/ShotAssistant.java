package com.gitlab.daring.image.gmv.assistant;

import com.gitlab.daring.image.command.parameter.FileParam;
import com.gitlab.daring.image.video.BaseVideoProcessor;
import net.miginfocom.swing.MigLayout;
import org.bytedeco.javacpp.opencv_core.Mat;
import org.bytedeco.javacpp.opencv_core.Size;

import javax.swing.*;
import java.io.File;

import static com.gitlab.daring.image.MainContext.mainContext;
import static com.gitlab.daring.image.swing.SwingUtils.newButton;
import static com.gitlab.daring.image.util.ImageUtils.flipMat;
import static java.awt.BorderLayout.SOUTH;
import static org.apache.commons.lang3.StringUtils.isNotBlank;
import static org.bytedeco.javacpp.opencv_imgcodecs.imread;
import static org.bytedeco.javacpp.opencv_imgcodecs.imwrite;
import static org.bytedeco.javacpp.opencv_imgproc.COLOR_GRAY2BGR;
import static org.bytedeco.javacpp.opencv_imgproc.cvtColor;

class ShotAssistant extends BaseVideoProcessor {

    static final String ConfigPath = "isb.ShotAssistant";

    final FileParam sampleFile = new FileParam(":Образец").bind(config, "sampleFile");
    final FileParam shotFile = new FileParam(":Снимок").bind(config, "shotFile");
    final boolean flipInput = config.getBoolean("flipInput");

    final TemplateBuilder templateBuilder = new TemplateBuilder(this);
    final PositionControl positionControl = new PositionControl(this);
    final DisplayBuilder displayBuilder = new DisplayBuilder(this);
    final ConfigPanel configPanel = new ConfigPanel(this);

    final Mat sampleMat = new Mat();
    final Mat templateMat = new Mat();
    final Mat displayMat = new Mat();

    final JLabel statusField = new JLabel();
    volatile boolean loadSample = true, saveSample, saveShot;
    boolean checkResult;

    //TODO introduce SaveShotAction

    public ShotAssistant() {
        super(ConfigPath);
        sampleFile.onChange(() -> loadSample = true);
        initFrame();
        configPanel.showFrame();
    }

    private void initFrame() {
        JPanel p = new JPanel(new MigLayout("fillx"));
        p.add(statusField, "growx, w 2000");
        p.add(newButton("Образец", () -> saveSample = true));
        p.add(newButton("Снимок", () -> saveShot = true));
        frame.add(p, SOUTH);
        frame.validate();
    }

    @Override
    protected void processFrame() {
        if (flipInput) flipMat(inputMat, 1);
        if (loadSample) loadSample();
        if (saveSample) saveSample();
        if (saveShot) saveShot();
        checkResult = positionControl.check(inputMat);
        displayBuilder.build(inputMat);
        if (writer.isOpened()) writer.write(displayMat);
        showImage(displayMat);
    }

    void loadSample() {
        loadSample = false;
        String file = sampleFile.v;
        if (new File(file).exists()) {
            imread(file).copyTo(sampleMat);
            applySample();
        }
    }

    void saveSample() {
        saveSample = false;
        inputMat.copyTo(sampleMat);
        String file = sampleFile.v;
        if (isNotBlank(file)) imwrite(file, sampleMat);
        applySample();
    }

    void applySample() {
        positionControl.setSample(sampleMat);
        cvtColor(positionControl.template, templateMat, COLOR_GRAY2BGR);
    }

    void saveShot() {
        saveShot = false;
        String file = shotFile.v;
        if (isNotBlank(file)) imwrite(file, inputMat);
    }

    Size getSize() {
        return size;
    }

    @Override
    public void close() throws Exception {
        super.close();
        mainContext().close();
    }

    public static void main(String[] args) throws Exception {
        try (ShotAssistant app = new ShotAssistant()) {
            app.start();
        }
    }

}
