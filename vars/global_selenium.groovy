//global.groovy

import org.cl.*

def call(String param1, String param2){
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

                          
                                 println '******************************'
                                 println 'Ejecucion de selenium pipeline'
                                 println '******************************'
                                println 'Inicio'
                                println 'String 1: ' + param1
                                println 'String 2: ' + param2

                        } catch(Exception e) {
                            error ('Ha ocurrido el siguiente error: ' + e)
                        }
                    }
                }
            }
            stage('Union'){
                 steps{
                    script{
                                println 'Union de 2 Strings: '
                    }
                 }
            }

            stage('MostrarNombre'){
                               steps{
                    script{
                                println 'MostrarNombre_____: '
                    }
                 }
            }
            stage('MostrarNombre222'){
                                println 'MostrarNombre222____: '
            }
        }
    }
}

return this;
