//global.groovy

import org.cl.*
import resources.org.cl.*
	

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
				
		echo 'This stage does block an executor because it inherits the "agent any" from the pipline.'
				
		def dataJson = readJSON file: './resources/nombres.json'

		echo "zapData = ${dataJson}"

		def servidor1Data = dataJson.servidor1
		echo "servidor1Data = ${servidor1Data}"
            
               inputResponse = input([
              message           : 'Please confirm.',
              submitterParameter: 'submitter'
              ])
            
              echo "Input response: ${inputResponse}"
                            
                                

                        } catch(Exception e) {
                            error ('Ha ocurrido el siguiente error: ' + e)
                        }
                    }
                }
            }
	    stage ('Git librerias'){
		steps{
                    script{
			def jenkins_libs_git_url = 'https://github.com/Luis-atos/libs.git'   
			echo " ****** GIT LIBRERIAS ****** "
			// Pipeline Remote Loader Plugin
			fileLoader.withGit(jenkins_libs_git_url, 'rama1', 'null','') {  
			echo " ****** GIT DENTRO LOADER ****** "
				scm = fileLoader.load('libreria');
			}
			scm.metodo1()
			scm.metodo2()
		    }
		}
   
      	    }
	    stage ('Git leer JSON de libreria'){
		steps{
                    script{
			def jenkins_libs_JSON_git_url = 'https://github.com/Luis-atos/TareaJenkinsNew.git'
			echo " ****** GIT JSON LIBRERIAS ****** "
			checkout([
                        $class: 'GitSCM',
                        branches: [[name: "resourcesGit"]],
                        doGenerateSubmoduleConfigurations: false,
                        extensions: [[
                            $class: 'RelativeTargetDirectory',
                            relativeTargetDir: "/temporal"
                        ]],
                        submoduleCfg: [],
                        userRemoteConfigs: [[
                            credentialsId: '',
                            url: 'https://github.com/Luis-atos/TareaJenkinsNew.git'
                        ]]
                    ])
			
		    }
		}
   
      	    }
            stage('Union'){
                 steps{
                    script{
			       genpropsFileExists = fileExists "./newAppTask-0.0.1-jar-with-dependencies.jar"
	                       if (!genpropsFileExists) {
		                  error("\n********** No se ha descargado newAppTask-0.0.1.jar desde SVN **********\n")
			       }else{
				    echo "\n********** SISI descargado newAppTask-0.0.1.jar desde SVN **********\n"   
			       }
			       def java_properties_jdk_version = 'JDK8_191'
                              def jdkHome = tool java_properties_jdk_version
                                echo "Union de 2 Strings: "
                               echo "******************************"
                                echo "Stage Union y llamamos jar"
                                echo '******************************'
			    //sh ""${jdkHome}"\\Java\\jdk1.8.0_191\\bin\\java -jar  newAppTask-0.0.1.jar param1 param2 param3"
			    // sh "'${mvnHome}/bin/mvn'  verify -Dunit-tests.skip=true"
			    bat(/"${jdkHome}\bin\java" -jar newAppTask-0.0.1-jar-with-dependencies.jar ${param1} ${param2} ${param3}/)
			    // com.selenium.defensa.goolesearch
			    
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
