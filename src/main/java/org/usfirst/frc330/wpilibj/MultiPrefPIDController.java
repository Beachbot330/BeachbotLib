/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.usfirst.frc330.wpilibj;

import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.Preferences;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
 
/**
 *
 * @author joe
 */
public class MultiPrefPIDController extends PrefPIDController{
    String gainName;

    public MultiPrefPIDController(double p, double i, double d, PIDSource source, PIDOutput output, double period, String name)
    {
        super(p,i,d,source,output,period, name);
        this.gainName = "default";
        readPIDPref(p,i,d,0,gainName);
    }
    
    public MultiPrefPIDController(double p, double i, double d, PIDSource source, PIDOutput output, String name)
    {
        super(p,i,d,source,output,name);  
        this.gainName = "default";
        readPIDPref(p,i,d,0,gainName);
    }
    public MultiPrefPIDController(double p, double i, double d, double f, PIDSource source, PIDOutput output, double period, String name)
    {
        super(p,i,d,source,output,period, name);
        this.gainName = "default";
        readPIDPref(p,i,d,f,gainName);
    }
    
    public MultiPrefPIDController(double p, double i, double d, double f, PIDSource source, PIDOutput output, String name)
    {
        super(p,i,d,source,output,name);  
        this.gainName = "default";
        readPIDPref(p,i,d,f,gainName);
    }
    protected void savePIDPref()
    {
//        Logger.getInstance().println("savePIDPref: " + name+gainName);
        Preferences.getInstance().putDouble(name+gainName+"P", getP());
        Preferences.getInstance().putDouble(name+gainName+"I", getI());
        Preferences.getInstance().putDouble(name+gainName+"D", getD());
        Preferences.getInstance().putDouble(name+gainName+"F", getF());
//        Logger.getInstance().println("Saved PID Preferences: " + this.name);
    }

    protected void readPIDPref(double p, double i, double d, double f, String gainName) {
        String savedName = name;
        this.gainName = gainName;
        name = savedName+gainName;
//        Logger.getInstance().println("readPIDPref: " +name);
        super.readPIDPref(p, i, d, f);
        name = savedName;
        if (!gainName.equals(SmartDashboard.getString(name+"gainName", name+gainName)))
            SmartDashboard.putString(name+"gainName", name+gainName);
    }
    
    public void setGainName(String gainName)
    {
        readPIDPref(0,0,0,0,gainName);

    }
/*    private ITable table;
    
    public String getSmartDashboardType()
    {
        return "MultiPrefPIDController";
    }

    
    public void initTable(ITable table)
    {
        if(this.table!=null)
            this.table.removeTableListener(listener);
        this.table = table;
        super.initTable(table);
        if(table!=null){
            table.putString("gainName", "default");
            table.addTableListener(listener, false);
        }
    }
    
    private ITableListener listener = new ITableListener() {
                boolean prevSave = false;

                public void valueChanged(ITable table, String key, Object value, boolean isNew) {
//                    Logger.getInstance().println(key + " changed");
                    if (key.equals("gainName"))
                    {
//                        Logger.getInstance().println("prevSave: " + prevSave + "curSave: " + ((Boolean) value).booleanValue());
                        MultiPrefSendablePIDController.this.setGainName((String)value);
                    }                
                }
            };
    
    public ITable getTable() {
        return table;    
    }
    */
}
