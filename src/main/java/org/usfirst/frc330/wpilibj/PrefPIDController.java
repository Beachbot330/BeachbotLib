/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.usfirst.frc330.wpilibj;

import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.Preferences;

/*
 * $Log: PrefSendablePIDController.java,v $
 * Revision 1.4  2013-02-25 03:35:24  jross
 * Fix saving in MultiPrefSendablePIDController, remove debug statements
 *
 * Revision 1.3  2013-02-25 02:08:54  jross
 * Change to use MultiPrefSendablePIDController
 *
 * Revision 1.2  2013-02-18 04:35:28  jross
 * remove debug print
 *
 * Revision 1.1  2013-02-16 04:54:57  jross
 * rename package to org.usfirst.frc330.wpilibj to match other packages
 *
 * Revision 1.1  2013-02-09 22:41:52  jross
 * import BeachbotPID classes
 *
 * Revision 1.1  2012-12-28 03:49:46  jross
 * Import from 2012 Java Beta Project
 *
 * Revision 1.6  2012-12-20 04:14:31  jross
 * Update for 12/19 beta release
 *
 * Revision 1.5  2012-11-14 05:06:10  jross
 * remove debug statements
 *
 * Revision 1.4  2012-10-13 18:54:51  jross
 * Remove debug print statement
 *
 * Revision 1.3  2012-10-13 18:53:03  jross
 * it works!
 *
 * Revision 1.2  2012-10-06 23:51:44  jross
 * Changes for 2013 beta library
 *
 * Revision 1.5  2012-10-05 01:48:18  jross
 * fixes from 10/4
 *
 * Revision 1.4  2012-10-03 04:08:09  jross
 * Working getTable
 *
 * Revision 1.3  2012-10-02 03:30:24  jross
 * Add comment describing class
 *
 * Revision 1.2  2012-10-02 03:07:41  jross
 * protect from multiple calls to getTable
 *
 * Revision 1.1  2012-10-01 03:54:24  jross
 * Extend Sendable PID Controller to save PID constants using Preferences class.
 *
 */

/**
 * PrefSendablePIDController extends SendablePIDController to save PID Constants
 * using the Preferences class.
 * 
 * @author Joe Ross
 */
public class PrefPIDController extends PIDController{
    
    String name;

    public PrefPIDController(double p, double i, double d, double f, PIDSource source, PIDOutput output, double period, String name) {
        super(p, i, d, f, source, output, period);
        this.name = name;
        readPIDPref(p,i,d,f);
    }


    public PrefPIDController(double p, double i, double d, double f, PIDSource source, PIDOutput output, String name) {
        super(p, i, d, f, source, output);
        this.name = name;
        readPIDPref(p,i,d,f);
    }
    
    public PrefPIDController(double p, double i, double d, PIDSource source, PIDOutput output, double period, String name)
    {
        super(p,i,d,source,output,period);
        this.name = name;
        readPIDPref(p,i,d, 0);
    }
    
    public PrefPIDController(double p, double i, double d, PIDSource source, PIDOutput output, String name)
    {
        super(p,i,d,source,output);  
        this.name = name;
        readPIDPref(p,i,d,0);
    }
    
    protected void readPIDPref(double p, double i, double d, double f)
    {
        if (Preferences.getInstance().containsKey(name + "P"))
        {
            
            p = Preferences.getInstance().getDouble(name + "P", p);
//            Logger.getInstance().println(name+"P exists. P=" + p);
        }
        else 
        {
            Preferences.getInstance().putDouble(name+"P", p);
//            Logger.getInstance().println(name+"P does not exist.");
        }
        
        if (Preferences.getInstance().containsKey(name + "I"))
        {
            i = Preferences.getInstance().getDouble(name + "I", i);
        }
        else 
        {
            Preferences.getInstance().putDouble(name+"I", i);
        } 
        
        if (Preferences.getInstance().containsKey(name + "D"))
        {
            d = Preferences.getInstance().getDouble(name + "D", d);
        }
        else 
        {
            Preferences.getInstance().putDouble(name+"D", d);
        }
        
        if (Preferences.getInstance().containsKey(name + "F"))
        {
            f = Preferences.getInstance().getDouble(name + "F", f);
        }
        else 
        {
            Preferences.getInstance().putDouble(name+"F", f);
        }
        
        setPID(p,i,d, f);      
    }
    
    protected void savePIDPref()
    {
        Preferences.getInstance().putDouble(name+"P", getP());
        Preferences.getInstance().putDouble(name+"I", getI());
        Preferences.getInstance().putDouble(name+"D", getD());
        Preferences.getInstance().putDouble(name+"F", getF());
//        Logger.getInstance().println("Saved PID Preferences: " + this.name);
    }
    
    public String getGainName() {
        return name;
    }
    
//    private ITable table;
//    
///*    public String getSmartDashboardType()
//    {
//        return "PrefPIDController";
//    }
//*/    
//    
//    public void initTable(ITable table)
//    {
//        if(this.table!=null)
//            this.table.removeTableListener(listener);
//        this.table = table;
//        super.initTable(table);
//        if(table!=null){
//            table.putBoolean("save", false);
//            table.addTableListener(listener, false);
//        }
//    }
//    
//    private ITableListener listener = new ITableListener() {
//                boolean prevSave = false;
//
//                public void valueChanged(ITable table, String key, Object value, boolean isNew) {
////                    Logger.getInstance().println(key + " changed");
//                    if (key.equals("save"))
//                    {
////                        Logger.getInstance().println("prevSave: " + prevSave + "curSave: " + ((Boolean) value).booleanValue());
//                        if (((Boolean) value).booleanValue())
//                        {
//                            if (prevSave == false)
//                            {
//                                PrefSendablePIDController.this.savePIDPref();
//                            }
//                            prevSave = true;
//                        }    
//                        else
//                            prevSave = false;
//                    }                
//                }
//            };
//    
//    public ITable getTable() {
//        return table;    
//    }
}
