pipeline {
     agent any
    stages{
     
        stage("One"){
            steps {
            script{
            
             sleep 10
             echo 'hello'
         
          }
            }
        }
    stage("dos"){
          steps {
         echo 'dos dos'
          }
    }
    stage("tres"){
        steps{
                    script{
			
			def pathWS = pwd()
			echo " **** ${pathWS} ****** GIT JSON LIBRERIAS ****** "
			checkout([
                        $class: 'GitSCM',
                        branches: [[name: "resourcesGit"]],
                        doGenerateSubmoduleConfigurations: false,
                        extensions: [[
                            $class: 'RelativeTargetDirectory',
				relativeTargetDir: "/var/lib/jenkins/workspace/temporal"
                        ]],
                        submoduleCfg: [],
                        userRemoteConfigs: [[
                            credentialsId: 'lmunma1',
                            url: 'git@git.servdev.mdef.es:sistemas/experimentos/papelera/EXPERIMENTO_ASODEF.git'
                        ]]
                    ])
			
		    }
		}
    }
    }
}
