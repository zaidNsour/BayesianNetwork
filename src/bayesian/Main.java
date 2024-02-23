
package bayesian;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 *
 * @author DELL
 */
public class Main {
  
  

  public static void main(String[] args){ 
    
  Distribution R=new Distribution(new Object[][]{ {"none",0.7},{"light",0.2},{"heavy",0.1} } ,"rain");
  

  
  Distribution M=new Distribution(new Object[][]{
    {"none","yes",0.4},
    {"none","no",0.6},
    {"light","yes",0.2},
    {"light","no",0.8},
    {"heavy","yes",0.1},
    {"heavy","no",0.9} },"maintenance",new Distribution[]{R});
  
  Distribution T=new Distribution(new Object[][]{
    {"none","yes","on time",0.8},
    {"none","yes","delayed",0.2},
    {"none","no","on time",0.9},
    {"none","no","delayed",0.1},
    {"light","yes","on time",0.6},
    {"light","yes","delayed",0.4},
    {"light","no","on time",0.7},
    {"light","no","delayed",0.3},
    {"heavy","yes","on time",0.4},  
    {"heavy","yes","delayed",0.6},
    {"heavy","no","on time",0.5},
    {"heavy","no","delayed",0.5} },"train",new Distribution[]{R,M});
  
   Distribution A=new Distribution(new Object[][]{
    {"on time","attend",0.9},
    {"on time","miss",0.1},
    {"delayed","attend",0.6},
    {"delayed","miss",0.4} },"appointment",new Distribution[]{T});
   
   BayesianNetwork network=new BayesianNetwork(new Distribution[]{R,M,T,A});
   
    /*System.out.println(network.distributions[2].getProbabilityGivenThat( 1,new int[]{2,0} ) );*/
    /*System.out.println(network.variables.get("appointment"));*/
    /*System.out.println(network.distributions[3].parentsID[0]);*/
    /*System.out.println(network.distributions[0].states[2][1]);*/
    /*System.out.println(Arrays.toString(network.vals));*/
   
    
   /* 
   double p=network.probability(new String[]{"none","no","on time","miss"});
    System.out.println(p);
   */
   
   List<Double>predictions=network.predictProba( "appointment",Arrays.asList("train","delayed") );
    System.out.println(predictions);
    System.out.println(Arrays.toString(network.vals));
    System.out.println(network.marg);
   

    
   
    
   
 
    
    
    
  }
  
}
