//global.groovy

import org.cl.*

def call(String param1, String param2){

    println 'Ejecución de Pipeline'

    pipeline{
        agent any
        stages{
            stage('Pipeline'){
                steps{
                    script{
                        try {

                            def funciones   = new Funciones()

                            stage('Inicio'){
                                println 'Inicio'
                                println 'String 1: ' + param1
                                println 'String 2: ' + param2

                                gradle.call()
                            }

                            stage('Union'){
                                println 'Union de 2 Strings: ' + funciones.unirDosStrings(param1, param2)          
                            }

                            stage('MostrarNombre'){
                                println 'Nombre obtenido desde Json: ' + funciones.mostrarNombre()
                            }
                            stage('MostrarNombre222'){
                                println 'Nombre obtenido222 desde222 Json: ' + funciones.mostrarNombre()
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
