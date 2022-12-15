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
