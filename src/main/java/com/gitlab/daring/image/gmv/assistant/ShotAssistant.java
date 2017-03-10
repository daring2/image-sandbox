package com.gitlab.daring.image.gmv.assistant;

import com.gitlab.daring.image.command.parameter.FileParam;
import com.gitlab.daring.image.gmv.seal.SealCheckSandbox;
import com.gitlab.daring.image.video.BaseVideoProcessor;
import net.miginfocom.swing.MigLayout;
import org.bytedeco.javacpp.opencv_core.Mat;
import org.bytedeco.javacpp.opencv_core.Size;

import javax.swing.*;

import static com.gitlab.daring.image.MainContext.mainContext;
import static com.gitlab.daring.image.swing.SwingUtils.newButton;
import static com.gitlab.daring.image.util.ImageUtils.flipMat;
import static java.awt.BorderLayout.SOUTH;
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
    final SealCheckSandbox sealCheckSandbox = new SealCheckSandbox();
    final SampleAction sampleAction = new SampleAction(this);
    final ShotAction shotAction = new ShotAction(this);

    final Mat sampleMat = new Mat();
    final Mat templateMat = new Mat();
    final Mat displayMat = new Mat();

    final JLabel statusField = new JLabel();
    boolean checkResult;

    //TODO introduce SaveShotAction

    public ShotAssistant() {
        super(ConfigPath);
        initFrame();
        configPanel.showFrame();
    }

    private void initFrame() {
        JPanel p = new JPanel(new MigLayout("fillx"));
        p.add(statusField, "growx, w 2000");
        p.add(newButton("Образец", () -> sampleAction.save = true));
        p.add(newButton("Снимок", () -> shotAction.save = true));
        frame.add(p, SOUTH);
        frame.validate();
    }

    @Override
    protected void processFrame() {
        if (flipInput) flipMat(inputMat, 1);
        sampleAction.process();
        shotAction.process();
        checkResult = positionControl.check(inputMat);
        displayBuilder.build(inputMat);
        if (writer.isOpened()) writer.write(displayMat);
        showImage(displayMat);
    }

    void applySample() {
        positionControl.setSample(sampleMat);
        cvtColor(positionControl.template, templateMat, COLOR_GRAY2BGR);
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
