package com.lk.nxt_pharma.directionsLib;


import com.lk.nxt_pharma.entity.Mapdistanceobj;
import com.lk.nxt_pharma.entity.Maptimeobj;

/**
 * Created by Vishal on 10/20/2018.
 */

public interface TaskLoadedCallback {
    void onTaskDone(Object... values);
    void onTimeTaskDone(Maptimeobj maptimeobj);
    void onDistanceTaskDone(Mapdistanceobj mapdistanceobj);


}
