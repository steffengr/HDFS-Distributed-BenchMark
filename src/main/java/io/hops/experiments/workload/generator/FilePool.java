/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package io.hops.experiments.workload.generator;

/**
 *
 * @author salman
 */
public interface FilePool {     
    
  public String getDirToCreate();
  
  public String getFileToCreate();
  
  public void fileCreationFailed(String file);

  public String getFileToRead();
  
  public String getFileToStat();
  
  public String getDirToStat();
  
  public String getPathToChangePermissions();
  
  public String getFileToRename();
  
  public void fileRenamed(String from, String to);
  
  public String getFileToDelete();
}
