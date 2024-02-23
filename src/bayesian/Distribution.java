
package bayesian;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 *
 * @author DELL
 */
public class Distribution{
  static int idCount=0;
  Distribution[]parents;
  int[]parentsID;
  Object[][]states;
  HashMap<String,Integer>values;
  boolean isConditional=false;
  String name;
  int id;
  int valuesCount;
  int parentsCount;
  String str;
  
Distribution(Object[][]states,String name,Distribution[]parents){
  this(states,name);
  this.parents=parents;
  parentsCount=parents.length;
  parentsID=new int[parentsCount];
  for(int i=0;i<parentsCount;i++)
    parentsID[i]=parents[i].id;
  isConditional=true;
  
  }

Distribution(Object[][]states,String name){
  this.states=states;
  this.name=name;
  this.id=idCount++;
  values=new HashMap<>();
  int valueIndex=states[0].length-2;
  
  for(int i=0;i<states.length;i++){
    if(i==0){
      str=(String)states[i][valueIndex];
      values.put(str, i);
      }
    else{
      if(!states[i][valueIndex].equals(str))
        values.put((String)states[i][valueIndex], i);
      else
        break;
      }
    }
  valuesCount=values.size(); 
  }
  
 int getIndexOfValue(String s){
  return values.get(s);
  }
 
 
 double getProbabilityGivenThat(int state,int[]parentsState){
  
  if(state>=valuesCount)
    throw new IllegalArgumentException("state must be in the values of that random variable");
  
  for(int i=0;i<parentsCount;i++){
    if(parentsState[i]>=parents[i].valuesCount)
      throw new IllegalArgumentException("state must be in the values of that random variable");
    }
   
  int position=0 ,factor=valuesCount;
  position+=state;
  position+=factor*parentsState[parentsCount-1];
  for(int i=parentsCount-2;i>=0;i--){
    factor*=parents[i+1].valuesCount;
    position+=factor*parentsState[i];
    }
  
  return (double)states[position][parentsCount+1];
  }

 
 


  
}
