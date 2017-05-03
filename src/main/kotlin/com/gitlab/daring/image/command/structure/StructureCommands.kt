package com.gitlab.daring.image.command.structure

import com.gitlab.daring.image.command.Command
import com.gitlab.daring.image.command.CommandRegistry
import com.gitlab.daring.image.command.SimpleCommand
import org.bytedeco.javacpp.opencv_imgproc.Canny
import org.bytedeco.javacpp.opencv_imgproc.cornerHarris

object StructureCommands {

    fun register(r: CommandRegistry): Unit {
        r.register("canny", this::cannyCommand);
        r.register("cornerHarris", this::cornerHarrisCommand);
        r.register("findContours", ::FindContoursCommand);
        r.register("filterContours", ::FilterContoursCommand);
        r.register("drawContours", ::DrawContoursCommand);
        r.register("showContours", ::ShowContoursCommand);
        r.register("watershedCenter", ::WatershedCenterCommand);
        r.register("watershedMarker", ::WatershedMarkerCommand);
        r.register("pyrMeanShiftFilter", ::PyrMeanShiftFilterCommand);
        r.register("grubCutCenter", ::GrubCutCenterCommand);
        r.register("grubCutMarker", ::GrubCutMarkerCommand);
        r.register("houghCircles", ::HoughCirclesCommand);
        r.register("detectLines", ::DetectLinesCommand);
    }

    fun cannyCommand(args: Array<String>): Command {
        val c = SimpleCommand(args)
        val th1 = c.doubleParam(100.0, "0-500")
        val th2 = c.doubleParam(200.0, "0-500")
        val sp = c.intParam(1, "0-50")
        val l2g = c.boolParam(false)
        return c.withFunc { m -> Canny(m, m, th1.v, th2.v, sp.v * 2 + 1, l2g.v) }
    }

    fun cornerHarrisCommand(args: Array<String>): Command {
        val c = SimpleCommand(args)
        val b = c.intParam(2, "0-10")
        val ksp = c.intParam(1, "0-10")
        val k = c.doubleParam(4.0, "0-100")
        return c.withFunc { m, d -> cornerHarris(m, d, b.v, ksp.v * 2 + 1, k.pv) }
    }

}