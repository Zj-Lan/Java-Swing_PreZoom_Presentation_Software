package prezoom.controller;

import prezoom.model.GAttributes;

import java.awt.*;
import java.util.ArrayList;

/** This class is the manager to manage the state related functions for the attributes of graphical objects.
 *  Each {@link prezoom.model.GObject} has a manager.
 * @author Zhijie Lan<p>
 * create date: 2020/11/6
 **/
public class GAttributeManager
{
    ArrayList<GAttributes> state_Attributes_list = new ArrayList<>();
    //int current_State = 0;
    /**
     * the attribute for the current state
     */
    public GAttributes cur_Attributes;

    /**
     * To construct a manager, the object's attributes will be generated and duplicated from the current state to the end state,
     * but attributes for states before the current state are set to null, which means the object only exists from the current state.
     *
     * @param x location, x
     * @param y location, y
     * @param col paint color
     * @param filled whether filled
     * @param lineWidth width of lines
     * @param width width of the object, if applicable
     * @param height height of the object, if applicable
     * @param x2 x2 of the object, if applicable
     * @param y2 y2 of the object, if applicable
     * @param visible whether visible
     */
    public GAttributeManager(double x, double y, Color col, Boolean filled, int lineWidth, int width, int height, double x2, double y2, Boolean visible)
    {
        for (int i = 0; i< StateManager.total_State_Number +1; i++)
        {
            if (i<getCurrent_State())
                state_Attributes_list.add(null);
            else
            {
                GAttributes attributes = new GAttributes(x,y,col,filled,lineWidth, width, height, x2, y2, visible);
                state_Attributes_list.add(i,attributes);
            }
        }
        updateCur_Attributes();
    }

    private int getCurrent_State()
    {
        return StateManager.current_State;
    }

    /**
     * get the object's attribute for the current state
     * @return the attribute
     */
    public GAttributes getCur_Attributes()
    {
        //return state_Attributes_map.get(getCurrent_State());
        return state_Attributes_list.get(getCurrent_State());
    }

    /**
     * update the {@link #cur_Attributes}.
     * skip if there is no attribute for the current state
     */
    public void updateCur_Attributes()
    {
        if (getCur_Attributes() != null)
            this.cur_Attributes = getCur_Attributes();
    }

    /**
     * insert a new attribute of a new state into {@link #state_Attributes_list} at the current state index.
     * The new attribute will clone the previous info of the current state
     * @throws CloneNotSupportedException nothing
     */
    public void insertAttributeState() throws CloneNotSupportedException
    {
        GAttributes attributes = new GAttributes();
        if (!state_Attributes_list.isEmpty())
        {
            if(state_Attributes_list.get(getCurrent_State()-1) == null)
                attributes = null;
            else
                attributes = (GAttributes) state_Attributes_list.get(getCurrent_State()-1).clone();
        }

        state_Attributes_list.add(getCurrent_State(), attributes);

        // comment the update,  set it be triggered by the state manager to avoid double update
        //updateCur_Attributes();
    }

    /**
     * delete the attribute of a given state
     * @param state the state to be deleted
     */
    public void deleteAttributeState(int state)
    {
        state_Attributes_list.remove(state);

        // comment the update,  set it be triggered by the state manager to avoid double update
        //updateCur_Attributes();
    }
}
