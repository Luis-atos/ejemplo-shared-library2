//global.groovy

import org.cl.*

def call(String param1, String param2){
    println '******************************'
    println 'Ejecución de selenium pipeline'
    println '******************************'
    pipeline{
        agent any
        stages{
            stage('Pipeline'){
                steps{
                    script{
                        try {

                           println 'Inicio'

                            stage('Inicio'){
                                 println '******************************'
                                 println 'Ejecución de selenium pipeline'
                                 println '******************************'
                                println 'Inicio'
                                println 'String 1: ' + param1
                                println 'String 2: ' + param2

                               
                            }

                            stage('Union'){
                                println 'Union de 2 Strings: '         
                            }

                            stage('MostrarNombre'){
                                println 'Nombre obtenido desde Json: '
                            }
                            stage('MostrarNombre222'){
                                println 'Nombre obtenido222 desde222 Json: '
                            }


                        } catch(Exception e) {
                            error ('Ha ocurrido el siguiente error: ' + e)
                        }
                    }
                }
            }
        }
    }
}

return this;
