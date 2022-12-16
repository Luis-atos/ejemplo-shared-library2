pipeline {
     agent any
	 tools{
	   maven "apache-maven-3.8.6"
	   jdk "jdk1.8.0_321"
	 }
    stages{
        stage("inicializa"){
		steps{
		  echo " **** variables ****** de MAVEN ****** "
		}
		}
	    stage("tres"){
        steps{
                    script{
			
			def pathWS = pwd()
			echo " **** ${pathWS} ****** GIT JSON LIBRERIAS ****** "
			checkout([
                        $class: 'GitSCM',
                        branches: [[name: "feature_selenium"]],
                        doGenerateSubmoduleConfigurations: false,
                        extensions: [[
                            $class: 'RelativeTargetDirectory',
				relativeTargetDir: "/var/lib/jenkins/workspace/pipeline_prueba"
                        ]],
                        submoduleCfg: [],
                        userRemoteConfigs: [[
                            credentialsId: 'jenkins120_GitLab',
                            url: 'git@git.servdev.mdef.es:sistemas/experimentos/papelera/pruebatest.git'
                        ]]
                    ])
			
		    }
		}
    }
        stage("One"){
            steps {
            script{
			
             sleep 2
             echo 'hello'
		         def output = sh(script: "mvn -Djdk.tls.maxCertificateChainLength=20 -Djavax.net.ssl.trustStore=/etc/pki/ca-trust/extracted/java/cacerts -Djava.net.ssl.trustStorePassword=changeit -f pom.xml clean install dependency:copy-dependencies sonar:sonar -Dsonar.login=Developer -Dsonar.password=Developer", returnStdout: true)
		         taskUrl = output.find(~"http://divindesonar.mdef.es:9000/api/ce/task\\?id=[\\w-]*")
			// sh 'mvn -Djdk.tls.maxCertificateChainLength=20 -Djavax.net.ssl.trustStore=/etc/pki/ca-trust/extracted/java/cacerts -Djava.net.ssl.trustStorePassword=changeit -f pom.xml clean install dependency:copy-dependencies sonar:sonar -Dsonar.login=Developer -Dsonar.password=Developer'
			 sleep 5
			 sh "java -cp 'target/dependency/testng-7.4.0.jar:target/dependency/jcommander-1.81.jar:target/dependency/jquery-3.5.1.jar:${env.WORKSPACE}/target/classes:target/surefire-reports/*' org.testng.TestNG ${env.WORKSPACE}/testng.xml"
          }
            }
        }
    stage("dos"){
          steps {
         echo 'dos dos'
		//  def output = sh(script: "mvn -f source/${RutaPom}pom.xml clean install ${ParametrosMaven} sonar:sonar -Dsonar.login=Developer -Dsonar.password=Developer ${paramSonar}", returnStdout: true)
		//  url = output.find(~"http://divindesonar.mdef.es:9000/api/ce/task\\?id=[\\w-]*")
		  def sonarData = readJSON text: sh(script: "curl -k -u Developer:Developer " + taskUrl, returnStdout: true)
		  echo "---->" + sonarData.toString()
          }
    }
    
    }
}

***********
pipeline {
     agent any
	 tools{
	   maven "apache-maven-3.8.6"
	   jdk "jdk1.8.0_321"
	 }
    stages{
        stage("inicializa"){
		steps{
		  echo " **** variables ****** de MAVEN ****** "
		}
		}
	    stage("tres"){
        steps{
                    script{
			
			def pathWS = pwd()
			echo " **** ${pathWS} ****** GIT JSON LIBRERIAS ****** "
			checkout([
                        $class: 'GitSCM',
                        branches: [[name: "master"]],
                        doGenerateSubmoduleConfigurations: false,
                        extensions: [[
                            $class: 'RelativeTargetDirectory',
				relativeTargetDir: "/var/lib/jenkins/workspace/pipeline_prueba"
                        ]],
                        submoduleCfg: [],
                        userRemoteConfigs: [[
                            credentialsId: 'jenkins120_GitLab',
                            url: 'git@git.servdev.mdef.es:sistemas/experimentos/papelera/pruebatest.git'
                        ]]
                    ])
			
		    }
		}
    }
        stage("One"){
            steps {
            script{
			
             sleep 2
             echo 'hello'
			 sh 'mvn -Djdk.tls.maxCertificateChainLength=20 -Djavax.net.ssl.trustStore=/etc/pki/ca-trust/extracted/java/cacerts -Djava.net.ssl.trustStorePassword=changeit -f pom.xml clean install dependency:copy-dependencies sonar:sonar -Dsonar.login=Developer -Dsonar.password=Developer'
			 sleep 5
			 sh "java -cp 'target/dependency/testng-7.4.0.jar:target/dependency/jcommander-1.81.jar:target/dependency/jquery-3.5.1.jar:${env.WORKSPACE}/target/classes:target/surefire-reports/*' org.testng.TestNG ${env.WORKSPACE}/testng.xml"
          }
            }
        }
    stage("dos"){
          steps {
         echo 'dos dos'
          }
    }
    
    }
}
*************
pipeline {
     agent any
	 tools{
	   maven "apache-maven-3.8.6"
	   jdk "jdk1.8.0_321"
	 }
    stages{
        stage("inicializa"){
		steps{
		  echo " **** variables ****** de MAVEN ****** "
		}
		}
	    stage("tres"){
        steps{
                    script{
			
			def pathWS = pwd()
			echo " **** ${pathWS} ****** GIT JSON LIBRERIAS ****** "
			checkout([
                        $class: 'GitSCM',
                        branches: [[name: "master"]],
                        doGenerateSubmoduleConfigurations: false,
                        extensions: [[
                            $class: 'RelativeTargetDirectory',
				relativeTargetDir: "/var/lib/jenkins/workspace/pipeline_prueba"
                        ]],
                        submoduleCfg: [],
                        userRemoteConfigs: [[
                            credentialsId: 'jenkins120_GitLab',
                            url: 'git@git.servdev.mdef.es:sistemas/experimentos/papelera/newmachine120.git'
                        ]]
                    ])
			
		    }
		}
    }
        stage("One"){
            steps {
            script{
			
             sleep 2
             echo 'hello'
			 sh 'mvn -Djdk.tls.maxCertificateChainLength=20 -Djavax.net.ssl.trustStore=/etc/pki/ca-trust/extracted/java/cacerts -Djava.net.ssl.trustStorePassword=changeit -f pom.xml clean compile sonar:sonar -Dsonar.login=Developer -Dsonar.password=Developer'
          }
            }
        }
    stage("dos"){
          steps {
         echo 'dos dos'
          }
    }
    
    }
}
*******
pipeline {
     agent any
	 tools{
	   maven "apache-maven-3.8.6"
	   jdk "jdk1.8.0_321"
	 }
    stages{
        stage("inicializa"){
		steps{
		  echo " **** variables ****** de MAVEN ****** "
		}
		}
	    stage("tres"){
        steps{
                    script{
			
			def pathWS = pwd()
			echo " **** ${pathWS} ****** GIT JSON LIBRERIAS ****** "
			checkout([
                        $class: 'GitSCM',
                        branches: [[name: "master"]],
                        doGenerateSubmoduleConfigurations: false,
                        extensions: [[
                            $class: 'RelativeTargetDirectory',
				relativeTargetDir: "/var/lib/jenkins/workspace/pipeline_prueba"
                        ]],
                        submoduleCfg: [],
                        userRemoteConfigs: [[
                            credentialsId: 'jenkins120_GitLab',
                            url: 'git@git.servdev.mdef.es:sistemas/experimentos/papelera/pruebatest.git'
                        ]]
                    ])
			
		    }
		}
    }
        stage("One"){
            steps {
            script{
			
             sleep 2
             echo 'hello'
			 sh 'mvn -Djdk.tls.maxCertificateChainLength=20 -Djavax.net.ssl.trustStore=/etc/pki/ca-trust/extracted/java/cacerts -Djava.net.ssl.trustStorePassword=changeit -f pom.xml clean compile'
          }
            }
        }
    stage("dos"){
          steps {
         echo 'dos dos'
          }
    }
    
    }
}
