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
	  
            
	    
	    stage ('Git leer JSON de libreria'){
		steps{
                    script{
			 echo "Stage 11111 MostrarNombre: "
			
		    }
		}
   
      	    }
	    
          

            stage('MostrarNombre'){
                               steps{
                    script{
                                 echo "Union de 22222 MostrarNombre: "
                    }
                 }
            }
        }
    }
}



return this;
