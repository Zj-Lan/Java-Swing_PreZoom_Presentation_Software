package prezoom.controller;

import org.pushingpixels.trident.api.Timeline;
import prezoom.model.AttributeMapI;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;

/**
 * The class to build the interpolation for objects
 *
 * @author Zhijie Lan<p>
 * create date: 2020/11/18<p>
 **/
public class InterpolationFactory
{
    /**
     * transverse all the fields of a class using getter and setter functions
     * to build interpolation between the previous object and the current object
     * @param preObj the previous object
     * @param curObj the current object
     */
    public static void buildInterpolation(Object preObj, Object curObj)
    {
        // return when both are null. one of them can be null to build fade in/out effect.
        if (preObj == null || curObj == null)
            return;
        else if (!(preObj instanceof AttributeMapI) || !(curObj instanceof AttributeMapI))
            return;
        else
        // when both are not null, make sure they are the same type of class
            if (preObj.getClass() != curObj.getClass())
                return;

        AttributeMapI curM = (AttributeMapI) curObj;

        Map<String, Method> cur_map = curM.validGetterMap();
        Timeline.Builder tBuilder = Timeline.builder(curObj);

        for (Map.Entry<String, Method> entry : cur_map.entrySet()) {

            try
            {
                tBuilder.addPropertyToInterpolate(entry.getKey(), entry.getValue().invoke(preObj), entry.getValue().invoke(curObj));
            } catch (IllegalAccessException | InvocationTargetException | IllegalArgumentException e)
            {
                //e.printStackTrace();
            }

        }

        Timeline timeline = tBuilder.build();
        timeline.play();

//        Timeline camTimeLine = Timeline.builder(curObj)
//                    .addPropertyToInterpolate("x", preObj != null ? preObj.getX() : 0, curObj.getX())
//                    .addPropertyToInterpolate("y", preObj != null ? preObj.getY() : 0, curObj.getY())
//                    .addPropertyToInterpolate("col", preObj != null ? preObj.getCol() : Color.white, curObj.getCol())
//                    .addPropertyToInterpolate("lineWidth", preObj != null ? preObj.getLineWidth() : 1, curObj.getLineWidth())
//                    .addPropertyToInterpolate("width", preObj != null ? preObj.getWidth() : 0, curObj.getWidth())
//                    .addPropertyToInterpolate("height", preObj != null ? preObj.getHeight() : 0, curObj.getHeight())
//                    .addPropertyToInterpolate("x2", preObj != null ? preObj.getX2() : 0, curObj.getX2())
//                    .addPropertyToInterpolate("y2", preObj != null ? preObj.getY2() : 0, curObj.getY2())
//                    .build();
//            camTimeLine.play();

    }

}
