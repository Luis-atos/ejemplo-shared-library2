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
				relativeTargetDir: "/var/lib/jenkins/workspace/newmachine120"
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
			 sh 'mvn clean compile'
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
