
package bayesian;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

/**
 *
 * @author DELL
 */
public class BayesianNetwork {
  static final double IMPOSSIBLE=0.0;
  static final double CERTAIN=1.0;
  
  Distribution[]distributions;
  int[]vals;
  List<Integer>marg;
  HashMap<String,Integer>variables;
  private int query;
  int size;
  double margProbability;
  
  BayesianNetwork(Distribution[]distributions){
  this.distributions=distributions;
  size=distributions.length;
  vals=new int[size];
  Arrays.fill(vals,-1);
  marg=new ArrayList<>();
  variables=new HashMap<>();
  for(Distribution d:distributions)
    variables.put(d.name, d.id);
    
  query=-1;
  }
  
  
  public double probability(String[]arr){
    if(arr.length!=size)
      throw new IllegalArgumentException("you should add value for all random variables");
    int[]values=new int[size];
    for(int i=0;i<size;i++){
      values[i]=distributions[i].getIndexOfValue(arr[i]);
      }
    return probability(values);
    }
  
  private double probability(int[]values){
    double probability=1;
    for(int i=0;i<size;i++){
     if(values[i] != -1){ 
       probability*=calcProbability(i,values);
       }
      }
    return probability;
    }
  
  
  private double calcProbability(int variable,int[]values){
    int val=values[variable];
    Distribution dist=distributions[variable];
    
     if(!dist.isConditional)
      return (double)distributions[variable].states[val][1];
      /*return Double.parseDouble(dist.states[val][1].toString());*/
      
    int[]parentId=dist.parentsID;
    int[]parentsState=new int[parentId.length];
    
    for(int i=0;i<parentId.length;i++)
      parentsState[i]=values[parentId[i]];
    double p=dist.getProbabilityGivenThat(val,parentsState);
    return p; 
    }
  
  public List<Double>predictProba(String q,List<String>evidences){
    Arrays.fill(vals,-1);
    query=variables.get(q);
    int maxPositiveIndex=-1;
    List<Double>predictions=new ArrayList<>();
    
    for(int i=0;i<evidences.size();i+=2){
      int id=variables.get(evidences.get(i));
      int val=distributions[id].getIndexOfValue(evidences.get(i+1));
      vals[id]=val;
      }
    
    if(vals[query]!=-1){ //dealing with case where query is exist with evidences
      Distribution d=distributions[query];
      int val=vals[query];
      for(int i=0;i<d.valuesCount;i++)
        if(i==val) predictions.add(CERTAIN);
        else       predictions.add(IMPOSSIBLE);
      return predictions;
      }
    
    for(int i=0;i<vals.length;i++)
      if(vals[i] != -1)
        maxPositiveIndex=i; 
    for(int i=0;i<vals.length;i++)
      if( vals[i]==-1 && i!=query && (i<query || i<maxPositiveIndex) )
        marg.add(i);
    for(int i=0;i<distributions[query].valuesCount;i++){
      vals[query]=i;
      predictions.add( calcProbFromMargStates() );
      }
    
    
    double sum=0; //normalization
    for(int i=0;i<predictions.size();i++)
      sum+=predictions.get(i);
    for(int i=0;i<predictions.size();i++)
      predictions.set( i , predictions.get(i)/sum );
    
    
    return predictions;
    }
  
 private double calcProbFromMargStates(){
    margProbability=0;
    generativeStates(0);
    return margProbability;
    } 
  
 private void generativeStates(int n){
   for(int i=0;i<distributions[marg.get(n)].valuesCount;i++){
    vals[marg.get(n)]=i;
    if(n < marg.size()-1)
      generativeStates(n+1);
    else
      margProbability+=probability(vals);
    }
   return;
  }
 
  
  
 
  
  
  
  }

