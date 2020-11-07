package prezoom.controller;

import prezoom.model.CameraInfo;
import prezoom.Main;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;

/**This class is the manager to manage all the camera related functions, including movement, info, states
 * @author Zhijie Lan<p>
 * create date: 2020/11/4
 **/
public class CameraManager
{
    /**
     * the array that stores info of each state
     */
    ArrayList<CameraInfo> state_CamInfo_list = new ArrayList<>();
    /**
     * the info for the current state
     */
    public CameraInfo cur_CamInfo;

    public CameraManager()
    {
        CameraInfo cameraInfo = new CameraInfo();
        state_CamInfo_list.add(getCurrent_State(), cameraInfo);
        updateCur_CamInfo();
    }

//    public void setCamInfo(double cam_x_offset, double cam_y_offset, double zoomFactor)
//    {
//        cur_CamInfo.cam_x_offset = cam_x_offset;
//        cur_CamInfo.cam_y_offset = cam_y_offset;
//        cur_CamInfo.am_zoomFactor = zoomFactor;
//    }

    /**
     * move the camera to the given location
     * @param g2 the Graphics to paint
     * @param cam_x_offset x offset
     * @param cam_y_offset y offset
     * @param zoomFactor zoom index
     * @param prevZoomFactor previous zoom index that is used to get better effect when zooming
     */
    public void moveCamera(Graphics2D g2, double cam_x_offset, double cam_y_offset, double zoomFactor, double prevZoomFactor)
    {
        cur_CamInfo.cam_x_offset = cam_x_offset;
        cur_CamInfo.cam_y_offset = cam_y_offset;
        cur_CamInfo.cam_zoomFactor = zoomFactor;
        cur_CamInfo.cam_prevZoomFactor = prevZoomFactor;

        AffineTransform at = new AffineTransform();
        at.translate(cur_CamInfo.cam_x_offset, cur_CamInfo.cam_y_offset);
        at.scale(cur_CamInfo.cam_zoomFactor, cur_CamInfo.cam_zoomFactor);
        g2.transform(at);
    }

    /**
     * move the camera to the stored location
     * @param g2 the Graphics to paint
     */
    public void moveCamera(Graphics2D g2)
    {
        AffineTransform at = new AffineTransform();
        at.translate(cur_CamInfo.cam_x_offset, cur_CamInfo.cam_y_offset);
        at.scale(cur_CamInfo.cam_zoomFactor, cur_CamInfo.cam_zoomFactor);
        g2.transform(at);
    }

    /**
     * get the current state index
     * @return current state index
     */
    private int getCurrent_State()
    {
        return StateManager.current_State;
    }

    /**
     * get the camera info for the current state
     * @return the current camera info
     */
    public CameraInfo getCur_CamInfo()
    {
        return state_CamInfo_list.get(getCurrent_State());
    }

    /**
     * update the {@link #cur_CamInfo}
     */
    public void updateCur_CamInfo()
    {
        this.cur_CamInfo = getCur_CamInfo();
        if (Main.app != null)
        Main.app.centerCanvas.setCanvasCamera(cur_CamInfo.cam_x_offset,
                cur_CamInfo.cam_y_offset, cur_CamInfo.cam_zoomFactor, cur_CamInfo.cam_prevZoomFactor);
    }

    /**
     * insert a new camera info of a new state into {@link #state_CamInfo_list} at the current state index.
     * The new camera info will clone the previous info of the current state
     * @throws CloneNotSupportedException noting
     */
    public void insertCamState() throws CloneNotSupportedException
    {
        CameraInfo cameraInfo = new CameraInfo();

        if (!state_CamInfo_list.isEmpty())
            cameraInfo = (CameraInfo) state_CamInfo_list.get(getCurrent_State()-1).clone();

        state_CamInfo_list.add(getCurrent_State(), cameraInfo);

        // comment the update,  set it be triggered by the state manager to avoid double update
        //updateCur_CamInfo();
    }

    /**
     * delete a given state form {@link #state_CamInfo_list}
     * @param state the state to be deleted
     */
    public void deleteCamState(int state)
    {
        state_CamInfo_list.remove(state);
        // comment the update,  set it be triggered by the state manager to avoid double update
        //updateCur_CamInfo();
    }
}
