//global.groovy

import org.cl.*

def call(String param1, String param2, String param3){
    println '******************************'
    println 'Ejecucion de selenium pipeline'
    println '******************************'
    pipeline{
        agent any
        stages{
            stage('Pipeline'){
                steps{
                    script{
                        try {

                          
                                 echo "******************************"
                                 echo 'Ejecucion de selenium pipeline'
                                 echo '******************************'
                                echo 'Inicio'
                                echo 'String 1: ' + param1
                                echo 'String 2: ' + param2
                                echo 'String 3: ' + param3
                                

                        } catch(Exception e) {
                            error ('Ha ocurrido el siguiente error: ' + e)
                        }
                    }
                }
            }
            stage('Union'){
                 steps{
                    script{
                                echo "Union de 2 Strings: "
                    }
                 }
            }

            stage('MostrarNombre'){
                               steps{
                    script{
                                 echo "Union de 2 MostrarNombre: "
                    }
                 }
            }
        }
    }
}

return this;
