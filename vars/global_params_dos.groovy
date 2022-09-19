//global.groovy

import org.cl.*
import resources.org.cl.*
	

def call(){
     
    println '******************************'
    println 'Ejecucion de selenium pipeline'
    println '******************************'
    pipeline{
        agent any
        stages{
	  
            
	    
	    stage ('global 2'){
		steps{
                    script{
			 echo "Stage_dos xxyyxx MostrarNombre: "
			
		    }
		}
   
      	    }
	    
          

            stage('global 222'){
                               steps{
                    script{
                                 echo "Union_dos xxyyxx MostrarNombre: "
                    }
                 }
            }
        }
    }
}



return this;

