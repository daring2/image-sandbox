package com.gitlab.daring.sandbox.image.assistant;

import com.gitlab.daring.sandbox.image.common.BaseComponent;
import com.gitlab.daring.sandbox.image.processor.ImageProcessor;
import org.bytedeco.javacpp.opencv_core.Mat;

import javax.annotation.concurrent.NotThreadSafe;

import static com.gitlab.daring.sandbox.image.processor.ImageProcessorRegistry.parseProcList;
import static com.gitlab.daring.sandbox.image.util.ImageUtils.convertToGrey;

@NotThreadSafe
class TemplateBuilder extends BaseComponent {

	final String buildScript = config.getString("buildScript");
	final ImageProcessor proc = parseProcList(buildScript);

	TemplateBuilder(ShotAssistant a) {
		super(a.config.getConfig("template"));
	}

	Mat build(Mat inputMat) {
		Mat m = new Mat();
		convertToGrey(inputMat, m);
		return proc.process(m);
	}

}
